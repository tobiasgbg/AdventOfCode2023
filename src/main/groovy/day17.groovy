/*
--- Day 17: Clumsy Crucible ---
The lava starts flowing rapidly once the Lava Production Facility is operational. As you leave, the reindeer offers you a parachute, allowing you to quickly reach Gear Island.

As you descend, your bird's-eye view of Gear Island reveals why you had trouble finding anyone on your way up: half of Gear Island is empty, but the half below you is a giant factory city!

You land near the gradually-filling pool of lava at the base of your new lavafall. Lavaducts will eventually carry the lava throughout the city, but to make use of it immediately, Elves are loading it into large crucibles on wheels.

The crucibles are top-heavy and pushed by hand. Unfortunately, the crucibles become very difficult to steer at high speeds, and so it can be hard to go in a straight line for very long.

To get Desert Island the machine parts it needs as soon as possible, you'll need to find the best way to get the crucible from the lava pool to the machine parts factory. To do this, you need to minimize heat loss while choosing a route that doesn't require the crucible to go in a straight line for too long.

Fortunately, the Elves here have a map (your puzzle input) that uses traffic patterns, ambient temperature, and hundreds of other parameters to calculate exactly how much heat loss can be expected for a crucible entering any particular city block.

For example:

2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533
Each city block is marked by a single digit that represents the amount of heat loss if the crucible enters that block. The starting point, the lava pool, is the top-left city block; the destination, the machine parts factory, is the bottom-right city block. (Because you already start in the top-left block, you don't incur that block's heat loss unless you leave that block and then return to it.)

Because it is difficult to keep the top-heavy crucible going in a straight line for very long, it can move at most three blocks in a single direction before it must turn 90 degrees left or right. The crucible also can't reverse direction; after entering each city block, it may only turn left, continue straight, or turn right.

One way to minimize heat loss is this path:

2>>34^>>>1323
32v>>>35v5623
32552456v>>54
3446585845v52
4546657867v>6
14385987984v4
44578769877v6
36378779796v>
465496798688v
456467998645v
12246868655<v
25465488877v5
43226746555v>
This path never moves more than three consecutive blocks in the same direction and incurs a heat loss of only 102.

Directing the crucible from the lava pool to the machine parts factory, but not moving more than three consecutive blocks in the same direction, what is the least heat loss it can incur?

--- Part Two ---
The crucibles of lava simply aren't large enough to provide an adequate supply of lava to the machine parts factory. Instead, the Elves are going to upgrade to ultra crucibles.

Ultra crucibles are even more difficult to steer than normal crucibles. Not only do they have trouble going in a straight line, but they also have trouble turning!

Once an ultra crucible starts moving in a direction, it needs to move a minimum of four blocks in that direction before it can turn (or even before it can stop at the end). However, it will eventually start to get wobbly: an ultra crucible can move a maximum of ten consecutive blocks without turning.

In the above example, an ultra crucible could follow this path to minimize heat loss:

2>>>>>>>>1323
32154535v5623
32552456v4254
34465858v5452
45466578v>>>>
143859879845v
445787698776v
363787797965v
465496798688v
456467998645v
122468686556v
254654888773v
432267465553v
In the above example, an ultra crucible would incur the minimum possible heat loss of 94.

Here's another example:

111111111111
999999999991
999999999991
999999999991
999999999991
Sadly, an ultra crucible would need to take an unfortunate path like this one:

1>>>>>>>1111
9999999v9991
9999999v9991
9999999v9991
9999999v>>>>
This route causes the ultra crucible to incur the minimum possible heat loss of 71.

Directing the ultra crucible from the lava pool to the machine parts factory, what is the least heat loss it can incur?

 */

enum CrucibleDirection {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1)

    final int dr, dc
    CrucibleDirection(int dr, int dc) {
        this.dr = dr
        this.dc = dc
    }
}

class ClumsyCrucible {
    List<List<Integer>> grid = []
    boolean debug = false

    ClumsyCrucible(String input, boolean debug = false) {
        this.grid = input.split('\n').collect { line ->
            line.trim().toList().collect { it as String as Integer }
        }
        this.debug = debug

        if (debug) {
            println("\n=== Grid (${grid.size()}x${grid[0].size()}) ===")
            grid.eachWithIndex { row, idx ->
                println("${idx}: ${row.join(' ')}")
            }
            println()
        }
    }

    boolean inBounds(int row, int col) {
      return row >= 0 && row < grid.size() && col >= 0 && col < grid[0].size()
    }

    CrucibleDirection turnLeft(CrucibleDirection dir) {
      switch(dir) {
          case CrucibleDirection.UP: return CrucibleDirection.LEFT
          case CrucibleDirection.LEFT: return CrucibleDirection.DOWN
          case CrucibleDirection.DOWN: return CrucibleDirection.RIGHT
          case CrucibleDirection.RIGHT: return CrucibleDirection.UP
      }
    }

    CrucibleDirection turnRight(CrucibleDirection dir) {
      switch(dir) {
          case CrucibleDirection.UP: return CrucibleDirection.RIGHT
          case CrucibleDirection.RIGHT: return CrucibleDirection.DOWN
          case CrucibleDirection.DOWN: return CrucibleDirection.LEFT
          case CrucibleDirection.LEFT: return CrucibleDirection.UP
      }
    }

    Integer findMinimumHeatLoss(boolean ultraCrucible = false) {
      int minSteps = ultraCrucible ? 4 : 0
      int maxSteps = ultraCrucible ? 10 : 3

      PriorityQueue<CrucibleNode> queue = new PriorityQueue<>()
      Set<State> visited = new HashSet<>()
      Map<State, Integer> distances = new HashMap<>()

      if (debug) println("=== Starting Dijkstra's Algorithm ===")

      // Start with two initial states: going RIGHT or DOWN
      queue.add(new CrucibleNode(0, 0, CrucibleDirection.RIGHT, 0, 0))
      queue.add(new CrucibleNode(0, 0, CrucibleDirection.DOWN, 0, 0))

      int iteration = 0
      while (!queue.isEmpty()) {
          CrucibleNode current = queue.poll()
          iteration++

          if (debug) {
              println("\n[${iteration}] Processing: (${current.row},${current.col}) ${current.direction} steps=${current.consecutiveSteps} heat=${current.heatLoss}")
          }

          // Reached destination?
          if (current.row == grid.size()-1 && current.col == grid[0].size()-1 
                && current.consecutiveSteps >= minSteps) {
              if (debug) println(">>> DESTINATION REACHED! Total heat loss: ${current.heatLoss}")
              return current.heatLoss
          }

          // Skip if visited
          State state = new State(current.row, current.col, current.direction, current.consecutiveSteps)
          if (visited.contains(state)) {
            if (debug) println("    (already visited, skipping)")
            continue
          }

          visited.add(state)

          int neighborsAdded = 0

          // Continue straight (only if we haven't exceeded maxSteps)
          if (current.consecutiveSteps < maxSteps) {
            int newRow = current.row + current.direction.dr
            int newCol = current.col + current.direction.dc
            if (inBounds(newRow, newCol)) {
                int newHeat = current.heatLoss + grid[newRow][newCol]
                queue.add(new CrucibleNode(newRow, newCol, current.direction, current.consecutiveSteps + 1, newHeat))
                if (debug) println("    → Continue ${current.direction}: (${newRow},${newCol}) heat=${newHeat}")
                neighborsAdded++
            }
          } else {
              if (debug) println("    ✗ Cannot continue straight (already ${maxSteps} steps)")
          }

          // Turn left or right (only if we've moved at least minSteps blocks)
          if (current.consecutiveSteps >= minSteps) {
              for (CrucibleDirection newDir : [turnLeft(current.direction), turnRight(current.direction)]) {
                  int newRow = current.row + newDir.dr
                  int newCol = current.col + newDir.dc
                  if (inBounds(newRow, newCol)) {
                      int newHeat = current.heatLoss + grid[newRow][newCol]
                      queue.add(new CrucibleNode(newRow, newCol, newDir, 1, newHeat))
                      if (debug) println("    → Turn to ${newDir}: (${newRow},${newCol}) heat=${newHeat}")
                      neighborsAdded++
                  }
              }
          } else if (debug && ultraCrucible) {
              println("    ✗ Cannot turn yet (only ${current.consecutiveSteps} steps, need ${minSteps})")
          }

          if (debug && neighborsAdded == 0) {
              println("    (dead end)")
          }
    }

    return -1  // No path found
  }
}

class State {

    int row
    int col
    CrucibleDirection direction
    int consecutiveSteps

    State(int row, int col, CrucibleDirection direction, int consecutiveSteps = 0) {
        this.row = row
        this.col = col
        this.direction = direction
        this.consecutiveSteps = consecutiveSteps
    }

    @Override
    boolean equals(Object other) {
        if (!(other instanceof State)) return false
        State s = (State) other
        return row == s.row && col == s.col && direction == s.direction && consecutiveSteps == s.consecutiveSteps
    }

    @Override
    int hashCode() {
        return Objects.hash(row, col, direction, consecutiveSteps)
    }
}

class CrucibleNode implements Comparable<CrucibleNode> {
    int row, col
    CrucibleDirection direction
    int consecutiveSteps
    int heatLoss

    CrucibleNode(int row, int col, CrucibleDirection direction, int consecutiveSteps, int heatLoss) {
      this.row = row
      this.col = col
      this.direction = direction
      this.consecutiveSteps = consecutiveSteps
      this.heatLoss = heatLoss
    }

    @Override
    int compareTo(CrucibleNode other) {
        return this.heatLoss - other.heatLoss  // Min-heap
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day17.txt"
        File file = new File(filePath)
        String input = file.text.trim()

        ClumsyCrucible crucible = new ClumsyCrucible(input)
        Integer result = crucible.findMinimumHeatLoss()

        println("Part 1: ${result}")

        Integer result2 = crucible.findMinimumHeatLoss(true)

        println("Part 2: ${result2}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
