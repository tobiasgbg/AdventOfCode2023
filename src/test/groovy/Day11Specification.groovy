import spock.lang.Specification

class Day11Specification extends Specification {

    static final String EXAMPLE_INPUT = """...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#....."""

    def "get size"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        universe.coordinates.size() * universe.coordinates[0].size() == 100
    }

    def "is row empty true"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        universe.isRowEmpty(3)  // Row 3 is ".........."
    }

    def "is row empty false"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        !universe.isRowEmpty(0)
    }

    def "is column empty true"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        universe.isColumnEmpty(2)
    }

    def "is column empty false"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        !universe.isColumnEmpty(0)
    }

    def "get size expanded row count"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)
        universe.expand()

        when:
        def actualRows = universe.coordinates.size()

        println "Actual rows: $actualRows (expected: 12)"

        then:
        actualRows == 12
    }

    def "get size expanded column count"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)
        universe.expand()

        when:
        def actualCols = universe.coordinates[0].size()

        println "Actual cols: $actualCols (expected: 13)"

        then:
        actualCols == 13
    }

    def "galaxy row"() {
        given:
        Galaxy galaxy = new Galaxy(1,3)

        expect:
        galaxy.row == 1
    }

    def "galaxy column"() {
        given:
        Galaxy galaxy = new Galaxy(1,3)

        expect:
        galaxy.column == 3
    }
}
