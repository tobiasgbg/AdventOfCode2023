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
}
