/*
--- Day 11: Cosmic Expansion ---
You continue following signs for "Hot Springs" and eventually come across an observatory. The Elf within turns out to be a researcher studying cosmic expansion using the giant telescope here.

He doesn't know anything about the missing machine parts; he's only visiting for this research project. However, he confirms that the hot springs are the next-closest area likely to have people; he'll even take you straight there once he's done with today's observation analysis.

Maybe you can help him with the analysis to speed things up?

The researcher has collected a bunch of data and compiled the data into a single giant image (your puzzle input). The image includes empty space (.) and galaxies (#). For example:

...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....
The researcher is trying to figure out the sum of the lengths of the shortest path between every pair of galaxies. However, there's a catch: the universe expanded in the time it took the light from those galaxies to reach the observatory.

Due to something involving gravitational effects, only some space expands. In fact, the result is that any rows or columns that contain no galaxies should all actually be twice as big.

In the above example, three columns and two rows contain no galaxies:

   v  v  v
 ...#......
 .......#..
 #.........
>..........<
 ......#...
 .#........
 .........#
>..........<
 .......#..
 #...#.....
   ^  ^  ^
These rows and columns need to be twice as big; the result of cosmic expansion therefore looks like this:

....#........
.........#...
#............
.............
.............
........#....
.#...........
............#
.............
.............
.........#...
#....#.......
Equipped with this expanded universe, the shortest path between every pair of galaxies can be found. It can help to assign every galaxy a unique number:

....1........
.........2...
3............
.............
.............
........4....
.5...........
............6
.............
.............
.........7...
8....9.......
In these 9 galaxies, there are 36 pairs. Only count each pair once; order within the pair doesn't matter. For each pair, find any shortest path between the two galaxies using only steps that move up, down, left, or right exactly one . or # at a time. (The shortest path between two galaxies is allowed to pass through another galaxy.)

For example, here is one of the shortest paths between galaxies 5 and 9:

....1........
.........2...
3............
.............
.............
........4....
.5...........
.##.........6
..##.........
...##........
....##...7...
8....9.......
This path has length 9 because it takes a minimum of nine steps to get from galaxy 5 to galaxy 9 (the eight locations marked # plus the step onto galaxy 9 itself). Here are some other example shortest path lengths:

Between galaxy 1 and galaxy 7: 15
Between galaxy 3 and galaxy 6: 17
Between galaxy 8 and galaxy 9: 5
In this example, after expanding the universe, the sum of the shortest path between all 36 pairs of galaxies is 374.

Expand the universe, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?
 */

class Galaxy {
    List<List<Character>> coordinates = []

    Galaxy(String input) {
        List<String> lines = input.split("\\r\\n|\\n|\\r")
        for (int row = 0; row < lines.size(); row++) {
            coordinates.add([])
            for (int column = 0; column < lines[row].size(); column++)
                this.coordinates[row][column] = (lines[row][column] as Character)
        }
    }

    boolean isRowEmpty(int row) {
        for (int column = 0; column < coordinates[row].size(); column++) {
            if (coordinates[row][column] != '.') {
                return false
            }
        }
        true
    }

    boolean isColumnEmpty(int column) {
        for (int row = 0; row < coordinates.size(); row++) {
            if (coordinates[row][column] != '.') {
                return false
            }
        }
        true
    }

    def expand() {
        // First, expand rows (going backwards to avoid index shifting issues)
        for (int row = coordinates.size() - 1; row >= 0; row--) {
            if (isRowEmpty(row)) {
                // Insert a duplicate empty row
                def emptyRow = []
                for (int col = 0; col < coordinates[row].size(); col++) {
                    emptyRow.add('.' as Character)
                }
                coordinates.add(row + 1, emptyRow)
            }
        }

        // Then, expand columns (going backwards to avoid index shifting issues)
        for (int column = coordinates[0].size() - 1; column >= 0; column--) {
            if (isColumnEmpty(column)) {
                // Insert a '.' in each row at this column position
                for (int row = 0; row < coordinates.size(); row++) {
                    coordinates[row].add(column + 1, '.' as Character)
                }
            }
        }
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day11.txt"
        File file = new File(filePath)

        // TODO: Parse input and solve

        println("Part 1: [NOT IMPLEMENTED]")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
