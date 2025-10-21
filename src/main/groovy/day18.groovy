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

--- Part Two ---
The Elves were right to be concerned; the planned lagoon would be much too small.

After a few minutes, someone realizes what happened; someone swapped the color and instruction parameters when producing the dig plan. They don't have time to fix the bug; one of them asks if you can extract the correct instructions from the hexadecimal codes.

Each hexadecimal code is six hexadecimal digits long. The first five hexadecimal digits encode the distance in meters as a five-digit hexadecimal number. The last hexadecimal digit encodes the direction to dig: 0 means R, 1 means D, 2 means L, and 3 means U.

So, in the above example, the hexadecimal codes can be converted into the true instructions:

#70c710 = R 461937
#0dc571 = D 56407
#5713f0 = R 356671
#d2c081 = D 863240
#59c680 = R 367720
#411b91 = D 266681
#8ceee2 = L 577262
#caa173 = U 829975
#1b58a2 = L 112010
#caa171 = D 829975
#7807d2 = L 491645
#a77fa3 = U 686074
#015232 = L 5411
#7a21e3 = U 500254
Digging out this loop and its interior produces a lagoon that can hold an impressive 952408144115 cubic meters of lava.

Convert the hexadecimal color codes into the correct instructions; if the Elves follow this new dig plan, how many cubic meters of lava could the lagoon hold?

 */

class LagoonPosition {
    long row
    long col

    LagoonPosition(long row, long col) {
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
    long distance
    String color

    DigInstruction(String direction, long distance, String color) {
        this.direction = direction
        this.distance = distance
        this.color = color
    }
}

class LavaductLagoon {
    List<DigInstruction> instructions = []

    LavaductLagoon(String input, Boolean swapParameters = false) {
        this.instructions = input.split('\n')
            .findAll { it.trim() } // Filter out empty lines
            .collect { line ->
                def parts = line.trim().split(' ')
                String direction = ""
                long distance = 0
                String color = ""
                if (!swapParameters) {
                    direction = parts[0]
                    distance = parts[1] as Integer
                    color = parts[2].replaceAll(/[()]/, '')
                } else {
                    String instructionString = parts[2].replaceAll(/[#()]/, '')
                    String directionNumber = instructionString[5]
                    if (directionNumber == "0") {
                        direction = "R"
                    } else if (directionNumber == "1") {
                        direction = "D"
                    } else if (directionNumber == "2") {
                        direction = "L"
                    } else if (directionNumber == "3") {
                        direction = "U"
                    }
                    distance = Long.parseLong(instructionString.substring(0, 5), 16)
                }
                new DigInstruction(direction, distance, color)
            }
    }

    List<LagoonPosition> getLoop() {
        List<LagoonPosition> loop = []
        long currentRow = 0
        long currentCol = 0
        loop.add(new LagoonPosition(currentRow, currentCol))

        for (DigInstruction instruction in instructions) {
            if (instruction.direction == "L") {
                currentCol -= instruction.distance
            } else if (instruction.direction == "R") {
                currentCol += instruction.distance
            } else if (instruction.direction == "U") {
                currentRow -= instruction.distance
            } else if (instruction.direction == "D") {
                currentRow += instruction.distance
            }
            loop.add(new LagoonPosition(currentRow, currentCol))
        }
        return loop
    }

    static def getAreaOfLoop(def loop) {
        def diagonalSum = getDiagonalSum(loop)
        def diagonalSumReverse = getDiagonalSum(loop,true)
        def subtracted = diagonalSumReverse - diagonalSum
        // Area is the absolute value of half the difference
        Math.abs(subtracted) / 2 as Long
    }

    static def getDiagonalSum(def loop, def reverse = false) {
        long result = 0
        for (int i = 0; i < loop.size(); i += 1) {
            int nextIndex = (int) ((i + 1) % loop.size()) // Ensure loop closure
            result += reverse ? loop[i].col * loop[nextIndex].row : loop[i].row * loop[nextIndex].col
        }
        result
    }

    long calculateLagoonVolume() {
        List<LagoonPosition> loop = getLoop()
        long area = getAreaOfLoop(loop)
        long perimeter = instructions.sum { it.distance }

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

        LavaductLagoon lagoon2 = new LavaductLagoon(input, true)
        long result2 = lagoon2.calculateLagoonVolume()

        println("Part 2: ${result2}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
