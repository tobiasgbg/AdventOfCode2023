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

    def "galaxy count"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        universe.galaxies.size() == 9
    }

    def "galaxy pair count"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)

        expect:
        universe.galaxyPairs.size() == 36
    }

    def "galaxy pairs sum"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)
        universe.expand()

        expect:
        universe.getSumOfShortestPaths() == 374
    }

    def "galaxy pairs sum large expansion"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)
        universe.expand(10)

        expect:
        universe.getSumOfShortestPaths() == 1030
    }

    def "galaxy pairs sum very large expansion"() {
        given:
        Universe universe = new Universe(EXAMPLE_INPUT)
        universe.expand(100)

        expect:
        universe.getSumOfShortestPaths() == 8410
    }

    def "shortestDistance"() {
        given:
        Galaxy galaxy1 = new Galaxy(1,3)
        Galaxy galaxy2 = new Galaxy(1,2)

        expect:
        galaxy1.shortestDistanceTo(galaxy2) == 1
    }

    def "shortestDistance pair"() {
        given:
        Galaxy galaxy1 = new Galaxy(1,3)
        Galaxy galaxy2 = new Galaxy(1,2)
        GalaxyPair galaxyPair = new GalaxyPair(galaxy1, galaxy2)

        expect:
        galaxyPair.shortestDistance() == 1
    }
}
