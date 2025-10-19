/*
--- Day 18: Lavaduct Lagoon ---
Thanks to your efforts, the machine parts factory is one of the first factories up and running since the lavafall came back. However, to catch up with the large backlog of parts requests, the factory will also need a large supply of lava for a while; the Elves have already started creating a large lagoon nearby for this purpose.

However, they aren't sure the lagoon will be big enough; they've asked you to take a look at the dig plan (your puzzle input). For example:

R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)
The digger starts in a 1 meter cube hole in the ground. They then dig the specified number of meters up (U), down (D), left (L), or right (R), clearing full 1 meter cubes as they go. The directions are given as seen from above, so if "up" were north, then "right" would be east, and so on. Each trench is also listed with the color that the edge of the trench should be painted as an RGB hexadecimal color code.

When viewed from above, the above example dig plan would result in the following loop of trench (#) having been dug out from otherwise ground-level terrain (.):

#######
#.....#
###...#
..#...#
..#...#
###.###
#...#..
##..###
.#....#
.######
At this point, the trench could contain 38 cubic meters of lava. However, this is just the edge of the lagoon; the next step is to dig out the interior so that it is one meter deep as well:

#######
#######
#######
..#####
..#####
#######
#####..
#######
.######
.######
Now, the lagoon can contain a much more respectable 62 cubic meters of lava. While the interior is dug out, the edges are also painted according to the color codes in the dig plan.

The Elves are concerned the lagoon won't be large enough; if they follow their dig plan, how many cubic meters of lava could it hold?

 */

class LagoonPosition {
    int row
    int col

    LagoonPosition(int row, int col) {
        this.row = row
        this.col = col
    }

    @Override
    boolean equals(Object other) {
        if (!(other instanceof LagoonPosition)) return false
        LagoonPosition c = (LagoonPosition) other
        return row == c.row && col == c.col
    }

    @Override
    int hashCode() {
        return Objects.hash(row, col)
    }

    @Override
    String toString() {
        return "(${row},${col})"
    }
}

class DigInstruction {
    String direction
    int distance
    String color

    DigInstruction(String direction, int distance, String color) {
        this.direction = direction
        this.distance = distance
        this.color = color
    }
}

class LavaductLagoon {
    List<DigInstruction> instructions = []

    LavaductLagoon(String input) {
        this.instructions = input.split('\n')
            .findAll { it.trim() } // Filter out empty lines
            .collect { line ->
                def parts = line.trim().split(' ')
                new DigInstruction(parts[0], parts[1] as Integer, parts[2].replaceAll(/[()]/, ''))
            }
    }

    List<LagoonPosition> getLoop() {
        List<LagoonPosition> loop = []
        int currentRow = 0
        int currentCol = 0

        for (DigInstruction instruction in instructions) {
            for (int steps = 0; steps < instruction.distance; steps++) {
                loop.add(new LagoonPosition(currentRow, currentCol))
                if (instruction.direction == "L") {
                    currentCol--
                } else if (instruction.direction == "R") {
                    currentCol++
                } else if (instruction.direction == "U") {
                    currentRow--
                } else if (instruction.direction == "D") {
                    currentRow++
                }
            }
        }
        return loop
    }

    static def getAreaOfLoop(def loop) {
        def diagonalSum = getDiagonalSum(loop)
        def diagonalSumReverse = getDiagonalSum(loop,true)
        def subtracted = diagonalSumReverse - diagonalSum
        Math.abs(subtracted) / 2 as Integer // Area is the absolute value of half the difference
    }

    static def getDiagonalSum(def loop, def reverse = false) {
        def result = 0
        for (int i = 0; i < loop.size(); i += 1) {
            int nextIndex = (int) ((i + 1) % loop.size()) // Ensure loop closure
            result += reverse ? loop[i].col * loop[nextIndex].row : loop[i].row * loop[nextIndex].col
        }
        result
    }

    long calculateLagoonVolume() {
        List<LagoonPosition> loop = getLoop()
        long area = getAreaOfLoop(loop)
        long perimeter = loop.size()

        // Pick's theorem: Total = Area + Perimeter/2 + 1
        // This accounts for both interior points and boundary points
        return area + (perimeter / 2) + 1
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day18.txt"
        File file = new File(filePath)
        String input = file.text.trim()

        LavaductLagoon lagoon = new LavaductLagoon(input)
        long result = lagoon.calculateLagoonVolume()

        println("Part 1: ${result}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
