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

--- Part Two ---
The galaxies are much older (and thus much farther apart) than the researcher initially estimated.

Now, instead of the expansion you did before, make each empty row or column one million times larger. That is, each empty row should be replaced with 1000000 empty rows, and each empty column should be replaced with 1000000 empty columns.

(In the example above, if each empty row or column were merely 10 times larger, the sum of the shortest paths between every pair of galaxies would be 1030. If each empty row or column were merely 100 times larger, the sum of the shortest paths between every pair of galaxies would be 8410. However, your universe will need to expand far beyond these values.)

Starting with the same initial image, expand the universe according to these new rules, then find the length of the shortest path between every pair of galaxies. What is the sum of these lengths?
 */

class Universe {
    List<List<Character>> coordinates = []
    List<Galaxy> galaxies = []
    List<GalaxyPair> galaxyPairs = []

    Universe(String input) {
        List<String> lines = input.split("\\r\\n|\\n|\\r")
        for (int row = 0; row < lines.size(); row++) {
            coordinates.add([])
            for (int column = 0; column < lines[row].size(); column++) {
                this.coordinates[row][column] = (lines[row][column] as Character)
                if (lines[row][column] == "#") {
                    Galaxy galaxy = new Galaxy(row, column)
                    this.galaxies.add(galaxy)
                }
            }
        }

         for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                GalaxyPair galaxyPair = new GalaxyPair(galaxies[i], galaxies[j])
                galaxyPairs.add(galaxyPair)
            }
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

    def expand(int multiplier = 2) {
        // Find empty rows and columns first
        List<Integer> emptyRows = []
        List<Integer> emptyColumns = []

        for (int row = 0; row < coordinates.size(); row++) {
            if (isRowEmpty(row)) {
                emptyRows.add(row)
            }
        }

        for (int column = 0; column < coordinates[0].size(); column++) {
            if (isColumnEmpty(column)) {
                emptyColumns.add(column)
            }
        }

        // Adjust galaxy positions based on how many empty rows/columns are before them
        // Each empty row/column expands from 1 to multiplier units, so we add (multiplier - 1) extra units
        for (Galaxy galaxy : galaxies) {
            // Count how many empty rows are before this galaxy
            int emptyRowsBefore = emptyRows.count { it < galaxy.row }
            galaxy.row += emptyRowsBefore * (multiplier - 1)

            // Count how many empty columns are before this galaxy
            int emptyColumnsBefore = emptyColumns.count { it < galaxy.column }
            galaxy.column += emptyColumnsBefore * (multiplier - 1)
        }
    }

    long getSumOfShortestPaths() {
        long sum = 0
        for (galaxyPair in this.galaxyPairs) {
            sum += galaxyPair.shortestDistance()
        }
        sum
    }
}

class Galaxy {
    int row
    int column

    Galaxy(int row, int column) {
        this.row = row
        this.column = column
    }

    int shortestDistanceTo(Galaxy targetGalaxy) {
        // Manhattan distance: sum of absolute differences in row and column
        Math.abs(this.row - targetGalaxy.row) + Math.abs(this.column - targetGalaxy.column)
    }
}

class GalaxyPair {
    Galaxy galaxy1
    Galaxy galaxy2

    GalaxyPair(Galaxy galaxy1, Galaxy galaxy2) {
        this.galaxy1 = galaxy1
        this.galaxy2 = galaxy2
    }

    int shortestDistance() {
        galaxy1.shortestDistanceTo(galaxy2)
    }
}


static void main(String[] args) {
    try {
        String filePath = "../../../input/day11.txt"
        File file = new File(filePath)
        String input = file.text

        // Part 1: Expand by 2x (default)
        Universe universe1 = new Universe(input)
        universe1.expand()
        long sumOfPaths1 = universe1.getSumOfShortestPaths()
        println("Part 1: ${sumOfPaths1}")

        // Part 2: Expand by 1,000,000x
        Universe universe2 = new Universe(input)
        universe2.expand(1000000)
        long sumOfPaths2 = universe2.getSumOfShortestPaths()
        println("Part 2: ${sumOfPaths2}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
