import spock.lang.Specification

class Day10Specification extends Specification {

    def "get coordinates rows"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.coordinates.size() * pipeMaze.coordinates[0].size() == 25
    }

    def "get at pos"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.coordinates[3][1] == 'L'
    }

    def "get start pos row"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.getStartingPosition().row == 1
    }

    def "get start pos column"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.getStartingPosition().column == 1
    }

    def "get next pos row"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.getNextPosition(1, 1, 'D' as Character).row == 3
    }

    def "get next pos column"() {
        given:
        String input = """.....
.S-7.
.|.|.
.L-J.
....."""
        PipeMaze pipeMaze = new PipeMaze(input)

        expect:
        pipeMaze.getNextPosition(1, 1, 'D' as Character).row == 3
    }
}
