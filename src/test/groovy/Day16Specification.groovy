import spock.lang.Specification

class Day16Specification extends Specification {

    static final String EXAMPLE_INPUT = '''.|...\\....
|.-.\\.....
.....|-...
........|.
..........
.........\\
..../.\\\\..
.-.-/..|..
.|....-|.\\
..//.|....'''

    def "contraption parses grid correctly"() {
        given:
        Contraption contraption = new Contraption(EXAMPLE_INPUT)

        expect:
        contraption.grid.size() == 10
        contraption.grid[0] == ".|...\\...."
    }

    def "simple vertical line energizes 4 tiles"() {
        given:
        def input = '''.|.
...
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles() == 4  // (0,0), (0,1), (1,1), (2,1)
    }

    def "beam reflects off / mirror"() {
        given:
        def input = '''...
./.
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles() == 3
    }

    def "beam reflects off \\ mirror"() {
        given:
        def input = '''.\\
..
..'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles() == 4  // (0,0), (0,1), (1,1), (2,1)
    }

    def "beam splits on | going right"() {
        given:
        def input = '''.|.
...
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles() == 4  // Same as "simple vertical line"
    }

    def "beam passes through - going right"() {
        given:
        def input = '''.--.'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles() == 4
    }

    def "example input energizes 46 tiles"() {
        given:
        
        Contraption contraption = new Contraption(EXAMPLE_INPUT)

        expect:
        contraption.countEnergizedTiles() == 46
    }

    // Part 2 Tests

    def "beam starting from top row heading down"() {
        given:
        Contraption contraption = new Contraption(EXAMPLE_INPUT)

        expect:
        contraption.countEnergizedTiles(0, 3, Direction.DOWN) == 51
    }

    def "beam starting from bottom row heading up"() {
        given:
        def input = '''...
./.
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles(2, 0, Direction.UP) == 2  // (1,0) and (0,0)
    }

    def "beam starting from right column heading left"() {
        given:
        def input = '''...
./.
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.countEnergizedTiles(0, 3, Direction.LEFT) == 3
    }

    def "beam starting from left column heading right"() {
        given:
        Contraption contraption = new Contraption(EXAMPLE_INPUT)

        expect:
        contraption.countEnergizedTiles(0, -1, Direction.RIGHT) == 46
    }

    def "find maximum energized tiles from any starting position"() {
        given:
        Contraption contraption = new Contraption(EXAMPLE_INPUT)

        expect:
        contraption.findMaxEnergizedTiles() == 51
    }

    def "small grid maximum energized tiles"() {
        given:
        def input = '''.|.
...
...'''
        Contraption contraption = new Contraption(input)

        expect:
        contraption.findMaxEnergizedTiles() >= 4
    }
}
