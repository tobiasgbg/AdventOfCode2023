import spock.lang.Specification

class Day14Specification extends Specification {

    static final String EXAMPLE_INPUT = """O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#...."""

    static final String TILTED_NORTH = """OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#...."""

    def "total load after tilting north is 136"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.tiltNorth()

        expect:
        dish.calculateLoad() == 136
    }

    def "tilt north moves rocks correctly"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.tiltNorth()

        expect:
        dish.toString() == TILTED_NORTH
    }

    def "spin cycle 1 produces correct pattern"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.spinCycle()

        expect:
        dish.toString() == """.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#...."""
    }

    def "spin cycle 2 produces correct pattern"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.spinCycle()
        dish.spinCycle()

        expect:
        dish.toString() == """.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#..OO###..
#.OOO#...O"""
    }

    def "spin cycle 3 produces correct pattern"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.spinCycle()
        dish.spinCycle()
        dish.spinCycle()

        expect:
        dish.toString() == """.....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#...O###.O
#.OOO#...O"""
    }

    def "total load after 1000000000 cycles is 64"() {
        given:
        ParabolicReflectorDish dish = new ParabolicReflectorDish(EXAMPLE_INPUT)
        dish.spinCycles(1000000000)

        expect:
        dish.calculateLoad() == 64
    }
}
