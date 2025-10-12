/*
--- Day 14: Parabolic Reflector Dish ---
You reach the place where all of the mirrors were pointing: a massive parabolic reflector dish attached to the side of another large mountain.

The dish is made up of many small mirrors, but while the mirrors themselves are roughly in the shape of a parabolic reflector dish, each individual mirror seems to be pointing in slightly the wrong direction. If the dish is meant to focus light, all it's doing right now is sending it in a vague direction.

This system must be what provides the energy for the lava! If you focus the reflector dish, maybe you can go where it's pointing and use the light to fix the lava production.

Upon closer inspection, the individual mirrors each appear to be connected via an elaborate system of ropes and pulleys to a large metal platform below the dish. The platform is covered in large rocks of various shapes. Depending on their position, the weight of the rocks deforms the platform, and the shape of the platform controls which ropes move and ultimately the focus of the dish.

In short: if you move the rocks, you can focus the dish. The platform even has a control panel on the side that lets you tilt it in one of four directions! The rounded rocks (O) will roll when the platform is tilted, while the cube-shaped rocks (#) will stay in place. You note the positions of all of the empty spaces (.) and rocks (your puzzle input). For example:

O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....
Start by tilting the lever so all of the rocks will slide north as far as they will go:

OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#....
You notice that the support beams along the north side of the platform are damaged; to ensure the platform doesn't collapse, you should calculate the total load on the north support beams.

The amount of load caused by a single rounded rock (O) is equal to the number of rows from the rock to the south edge of the platform, including the row the rock is on. (Cube-shaped rocks (#) don't contribute to load.) So, the amount of load caused by each rock in each row is as follows:

OOOO.#.O.. 10
OO..#....#  9
OO..O##..O  8
O..#.OO...  7
........#.  6
..#....#.#  5
..O..#.O.O  4
..O.......  3
#....###..  2
#....#....  1
The total load is the sum of the load caused by all of the rounded rocks. In this example, the total load is 136.

Tilt the platform so that the rounded rocks all roll north. Afterward, what is the total load on the north support beams?

--- Part Two ---
The parabolic reflector dish deforms, but not in a way that focuses the beam. To do that, you'll need to move the rocks to the edges of the platform. Fortunately, a button on the side of the control panel labeled "spin cycle" attempts to do just that!

Each cycle tilts the platform four times so that the rounded rocks roll north, then west, then south, then east. After each tilt, the rounded rocks roll as far as they can before the platform tilts in the next direction. After one cycle, the platform will have finished rolling the rounded rocks in those four directions in that order.

Here's what happens in the example above after each of the first few cycles:

After 1 cycle:
.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#....

After 2 cycles:
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#..OO###..
#.OOO#...O

After 3 cycles:
.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#...O###.O
#.OOO#...O
This process should work if you leave it running long enough, but you're still worried about the north support beams. To make sure they'll survive for a while, you need to calculate the total load on the north support beams after 1000000000 cycles.

In the above example, after 1000000000 cycles, the total load on the north support beams is 64.

Run the spin cycle for 1000000000 cycles. Afterward, what is the total load on the north support beams?
 */

class ParabolicReflectorDish {
    List<String> lines = []

    ParabolicReflectorDish(String input) {
        this.lines = input.split("\\r\\n|\\n|\\r")
    }

    private def tiltNorthInternal(List<List<Character>> grid) {
        boolean changed = true
        while (changed) {
            changed = false
            for (int row = 1; row < grid.size(); row++) {
                for (int column = 0; column < grid[row].size(); column++) {
                    if (grid[row][column] == 'O' && grid[row-1][column] == '.') {
                        grid[row-1][column] = 'O'
                        grid[row][column] = '.'
                        changed = true
                    }
                }
            }
        }
    }

    private List<List<Character>> rotateClockwise(List<List<Character>> grid) {
        int rows = grid.size()
        int cols = grid[0].size()
        List<List<Character>> rotated = []
        for (int col = 0; col < cols; col++) {
            List<Character> newRow = []
            for (int row = rows - 1; row >= 0; row--) {
                newRow.add(grid[row][col])
            }
            rotated.add(newRow)
        }
        return rotated
    }

    private List<List<Character>> rotateCounterClockwise(List<List<Character>> grid) {
        int rows = grid.size()
        int cols = grid[0].size()
        List<List<Character>> rotated = []
        for (int col = cols - 1; col >= 0; col--) {
            List<Character> newRow = []
            for (int row = 0; row < rows; row++) {
                newRow.add(grid[row][col])
            }
            rotated.add(newRow)
        }
        return rotated
    }

    def spinCycle() {
        tiltNorth()
        tiltWest()
        tiltSouth()
        tiltEast()
    }

    def spinCycles(long n) {
        Map<String, Long> seenStates = [:]
        long cycleStart = -1
        long cycleLength = -1

        for (long i = 0; i < n; i++) {
            String state = toString()

            if (seenStates.containsKey(state)) {
                cycleStart = seenStates[state]
                cycleLength = i - cycleStart
                break
            }

            seenStates[state] = i
            spinCycle()
        }

        // If we found a cycle, calculate where we'd be after n cycles
        if (cycleStart != -1) {
            long remaining = (n - cycleStart) % cycleLength

            // We're currently at position cycleStart + cycleLength
            // We need to run 'remaining' more cycles from cycleStart
            // So we need to run (remaining - cycleLength) more from current position
            // But since remaining < cycleLength, we actually need to go back
            // Easier: just find the state at cycleStart + remaining in our map
            String targetState = seenStates.find { it.value == cycleStart + remaining }?.key

            if (targetState != null) {
                this.lines = targetState.split('\n').toList()
            }
        }
    }

    def tiltNorth() {
        List<List<Character>> grid = lines.collect { it.toList() }
        tiltNorthInternal(grid)
        lines = grid.collect { it.join() }
    }

    def tiltWest() {
        List<List<Character>> grid = lines.collect { it.toList() }
        grid = rotateClockwise(grid)
        tiltNorthInternal(grid)
        grid = rotateCounterClockwise(grid)
        lines = grid.collect { it.join() }
    }

    def tiltSouth() {
        List<List<Character>> grid = lines.collect { it.toList() }
        grid = rotateClockwise(rotateClockwise(grid))
        tiltNorthInternal(grid)
        grid = rotateCounterClockwise(rotateCounterClockwise(grid))
        lines = grid.collect { it.join() }
    }

    def tiltEast() {
        List<List<Character>> grid = lines.collect { it.toList() }
        grid = rotateCounterClockwise(grid)
        tiltNorthInternal(grid)
        grid = rotateClockwise(grid)
        lines = grid.collect { it.join() }
    }

    Integer calculateLoad() {
        Integer load = 0
        for (int row = 0; row < lines.size(); row++) {
            for (int column = 0; column < lines[row].size(); column++) {
                if (lines[row][column] == 'O') {
                    load += lines.size() - row
                }
            }
        }
        load
    }

    @Override
    String toString() {
        lines.join('\n')
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day14.txt"
        File file = new File(filePath)
        String input = file.text

        // Part 1
        ParabolicReflectorDish dish1 = new ParabolicReflectorDish(input)
        dish1.tiltNorth()
        Integer load1 = dish1.calculateLoad()
        println("Part 1: ${load1}")

        // Part 2
        ParabolicReflectorDish dish2 = new ParabolicReflectorDish(input)
        dish2.spinCycles(1_000_000_000)
        Integer load2 = dish2.calculateLoad()
        println("Part 2: ${load2}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
