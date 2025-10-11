import spock.lang.Specification

class Day13Specification extends Specification {

    static final String EXAMPLE_INPUT = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""

    def "sum of reflections is 405"() {
        given:
        PointOfIncidence poi = new PointOfIncidence(EXAMPLE_INPUT)

        expect:
        poi.summarize() == 405
    }

    def "first pattern has vertical reflection at column 5"() {
        given:
        PointOfIncidence poi = new PointOfIncidence(EXAMPLE_INPUT)

        expect:
        poi.getPattern(0).findVerticalReflection() == 5
    }

    def "second pattern has horizontal reflection at row 4"() {
        given:
        PointOfIncidence poi = new PointOfIncidence(EXAMPLE_INPUT)

        expect:
        poi.getPattern(1).findHorizontalReflection() == 4
    }
}
