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

    def "get size expanded"() {
        given:
        Galaxy galaxy = new Galaxy(EXAMPLE_INPUT)
        galaxy.expand()

        when:
        def actualRows = galaxy.coordinates.size()
        def actualCols = galaxy.coordinates[0].size()
        def actualSize = actualRows * actualCols

        println "Actual rows: $actualRows (expected: 12)"
        println "Actual cols: $actualCols (expected: 13)"
        println "Actual size: $actualSize (expected: ${13 * 12})"

        then:
        actualRows == 12
        actualCols == 13
        actualSize == 13 * 12
    }
}
