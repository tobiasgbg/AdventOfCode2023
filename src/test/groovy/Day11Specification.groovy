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
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)

        expect:
        galaxy.coordinates.size() * galaxy.coordinates[0].size() == 100
    }

    def "is row empty true"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)

        expect:
        galaxy.isRowEmpty(3)  // Row 3 is ".........."
    }

    def "is row empty false"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)

        expect:
        !galaxy.isRowEmpty(0)
    }

    def "is column empty true"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)

        expect:
        galaxy.isColumnEmpty(2)
    }

    def "is column empty false"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)

        expect:
        !galaxy.isColumnEmpty(0)
    }

    def "get size expanded row count"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)
        galaxy.expand()

        when:
        def actualRows = galaxy.coordinates.size()

        println "Actual rows: $actualRows (expected: 12)"

        then:
        actualRows == 12
    }

        def "get size expanded column count"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)
        galaxy.expand()

        when:
        def actualCols = galaxy.coordinates[0].size()

        println "Actual cols: $actualCols (expected: 13)"

        then:
        actualCols == 13
    }
}
