/*
--- Day 16: The Floor Will Be Lava ---
With the beam of light completely focused somewhere, the reindeer leads you deeper still into the Lava Production Facility. At some point, you realize that the steel facility walls have been replaced with cave, and the doorways are just cave, and the floor is cave, and you're pretty sure this is actually just a giant cave.

Finally, as you approach what must be the heart of the mountain, you see a bright light in a cavern up ahead. There, you discover that the beam of light you so carefully focused is emerging from the cavern wall closest to the facility and pouring all of its energy into a contraption on the opposite side.

Upon closer inspection, the contraption appears to be a flat, two-dimensional square grid containing empty space (.), mirrors (/ and \), and splitters (| and -).

The contraption is aligned so that most of the beam bounces around the grid, but each tile on the grid converts some of the beam's light into heat to melt the rock in the cavern.

You note the layout of the contraption (your puzzle input). For example:

.|...\....
|.-.\.....
.....|-...
........|.
..........
.........\
..../.\\..
.-.-/..|..
.|....-|.\
..//.|....
The beam enters in the top-left corner from the left and heading to the right. Then, its behavior depends on what it encounters as it moves:

If the beam encounters empty space (.), it continues in the same direction.
If the beam encounters a mirror (/ or \), the beam is reflected 90 degrees depending on the angle of the mirror. For instance, a rightward-moving beam that encounters a / mirror would continue upward in the mirror's column, while a rightward-moving beam that encounters a \ mirror would continue downward from the mirror's column.
If the beam encounters the pointy end of a splitter (| or -), the beam passes through the splitter as if the splitter were empty space. For instance, a rightward-moving beam that encounters a - splitter would continue in the same direction.
If the beam encounters the flat side of a splitter (| or -), the beam is split into two beams going in each of the two directions the splitter's pointy ends are pointing. For instance, a rightward-moving beam that encounters a | splitter would split into two beams: one that continues upward from the splitter's column and one that continues downward from the splitter's column.
Beams do not interact with other beams; a tile can have many beams passing through it at the same time. A tile is energized if that tile has at least one beam pass through it, reflect in it, or split in it.

In the above example, here is how the beam of light bounces around the contraption:

>|<<<\....
|v-.\^....
.v...|->>>
.v...v^.|.
.v...v^...
.v...v^..\
.v../2\\..
<->-/vv|..
.|<<<2-|.\
.v//.|.v..
Beams are only shown on empty tiles; arrows indicate the direction of the beams. If a tile contains beams moving in multiple directions, the number of distinct directions is shown instead. Here is the same diagram but instead only showing whether a tile is energized (#) or not (.):

######....
.#...#....
.#...#####
.#...##...
.#...##...
.#...##...
.#..####..
########..
.#######..
.#...#.#..
Ultimately, in this example, 46 tiles become energized.

The light isn't energizing enough tiles to produce lava; to debug the contraption, you need to start by analyzing the current situation. With the beam starting in the top-left heading right, how many tiles end up being energized?
 */

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

class Contraption {
    List<String> grid = []
    List<Beam> beams = []

    Contraption(String input) {
        this.grid = input.split('\n').collect { it.trim() }
        Beam beam = new Beam(0,-1, Direction.RIGHT)
        beams.add(beam)
    }

    Integer countEnergizedTiles() {
        Set<String> visited = new HashSet<>()  // Track "row,col,direction" to detect cycles
        Set<String> energized = new HashSet<>() // Track "row,col" for energized tiles
        Queue<Beam> queue = new LinkedList<>()
        queue.add(new Beam(0, -1, Direction.RIGHT))

        while (!queue.isEmpty()) {
            Beam beam = queue.poll()
            beam.move()

            // Check bounds
            if (beam.row < 0 || beam.row >= grid.size() ||
                beam.column < 0 || beam.column >= grid[0].length()) {
                continue
            }

            // Check for cycles
            String state = "${beam.row},${beam.column},${beam.direction}"
            if (visited.contains(state)) {
                continue
            }
            visited.add(state)

            // Mark as energized
            energized.add("${beam.row},${beam.column}")

            // Get current tile
            char tile = grid[beam.row].charAt(beam.column)

            // Process tile based on beam direction and tile type
            switch(tile) {
                case '.':
                    // Empty space - continue in same direction
                    queue.add(new Beam(beam.row, beam.column, beam.direction))
                    break

                case '/':
                    // Mirror - reflect beam
                    Direction newDir = null
                    switch(beam.direction) {
                        case Direction.RIGHT: newDir = Direction.UP; break
                        case Direction.LEFT: newDir = Direction.DOWN; break
                        case Direction.UP: newDir = Direction.RIGHT; break
                        case Direction.DOWN: newDir = Direction.LEFT; break
                    }
                    queue.add(new Beam(beam.row, beam.column, newDir))
                    break

                case '\\':
                    // Mirror - reflect beam
                    Direction newDir2 = null
                    switch(beam.direction) {
                        case Direction.RIGHT: newDir2 = Direction.DOWN; break
                        case Direction.LEFT: newDir2 = Direction.UP; break
                        case Direction.UP: newDir2 = Direction.LEFT; break
                        case Direction.DOWN: newDir2 = Direction.RIGHT; break
                    }
                    queue.add(new Beam(beam.row, beam.column, newDir2))
                    break

                case '|':
                    // Vertical splitter
                    if (beam.direction == Direction.LEFT || beam.direction == Direction.RIGHT) {
                        // Split into up and down
                        queue.add(new Beam(beam.row, beam.column, Direction.UP))
                        queue.add(new Beam(beam.row, beam.column, Direction.DOWN))
                    } else {
                        // Continue in same direction (up or down)
                        queue.add(new Beam(beam.row, beam.column, beam.direction))
                    }
                    break

                case '-':
                    // Horizontal splitter
                    if (beam.direction == Direction.UP || beam.direction == Direction.DOWN) {
                        // Split into left and right
                        queue.add(new Beam(beam.row, beam.column, Direction.LEFT))
                        queue.add(new Beam(beam.row, beam.column, Direction.RIGHT))
                    } else {
                        // Continue in same direction (left or right)
                        queue.add(new Beam(beam.row, beam.column, beam.direction))
                    }
                    break
            }
        }

        return energized.size()
    }
}

class Beam {
    Integer row = 0
    Integer column = 0
    Direction direction
    Boolean active = false

    Beam (Integer row, Integer column, Direction direction, Boolean active = true){
        this.row = row
        this.column = column
        this.direction = direction
        this.active = active
    }

    def move() {
        switch(direction) {
            case Direction.UP:
                row--
                break
            case Direction.DOWN:
                row++
                break
            case Direction.LEFT:
                column--
                break
            case Direction.RIGHT:
                column++
                break
        }
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day16.txt"
        File file = new File(filePath)
        String input = file.text.trim()

        Contraption contraption = new Contraption(input)
        Integer result = contraption.countEnergizedTiles()

        println("Part 1: ${result}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
