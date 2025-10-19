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

    ClumsyCrucible(String input) {
        this.grid = input.split('\n').collect { line ->
            line.trim().toList().collect { it as String as Integer }
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

    Integer findMinimumHeatLoss() {
      PriorityQueue<CrucibleNode> queue = new PriorityQueue<>()
      Set<State> visited = new HashSet<>()
      Map<State, Integer> distances = new HashMap<>()

      // Start with two initial states: going RIGHT or DOWN
      queue.add(new CrucibleNode(0, 0, CrucibleDirection.RIGHT, 0, 0))
      queue.add(new CrucibleNode(0, 0, CrucibleDirection.DOWN, 0, 0))

      while (!queue.isEmpty()) {
          CrucibleNode current = queue.poll()

          // Reached destination?
          if (current.row == grid.size()-1 && current.col == grid[0].size()-1) {
              return current.heatLoss
          }

          // Skip if visited
          State state = new State(current.row, current.col, current.direction, current.consecutiveSteps)
          if (visited.contains(state)) {
            continue
          }

          visited.add(state)

          // Continue straight (only if we haven't moved 3 times in same direction)
          if (current.consecutiveSteps < 3) {
            int newRow = current.row + current.direction.dr
            int newCol = current.col + current.direction.dc
            if (inBounds(newRow, newCol)) {
                queue.add(new CrucibleNode(newRow, newCol, current.direction, current.consecutiveSteps + 1, current.heatLoss + grid[newRow][newCol]))
            }
          }

          // Turn left or right (always allowed, resets consecutive steps to 1)
          for (CrucibleDirection newDir : [turnLeft(current.direction), turnRight(current.direction)]) {
              int newRow = current.row + newDir.dr
              int newCol = current.col + newDir.dc
              if (inBounds(newRow, newCol)) {
                  queue.add(new CrucibleNode(newRow, newCol, newDir, 1, current.heatLoss + grid[newRow][newCol]))
              }
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

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
