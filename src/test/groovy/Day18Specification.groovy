import spock.lang.Specification

class Day18Specification extends Specification {

    static final String EXAMPLE_INPUT = '''R 6 (#70c710)
D 5 (#0dc571)
L 2 (#5713f0)
D 2 (#d2c081)
R 2 (#59c680)
D 2 (#411b91)
L 5 (#8ceee2)
U 2 (#caa173)
L 1 (#1b58a2)
U 2 (#caa171)
R 2 (#7807d2)
U 3 (#a77fa3)
L 2 (#015232)
U 2 (#7a21e3)'''

    def "lagoon parses dig instructions correctly"() {
        given:
        LavaductLagoon lagoon = new LavaductLagoon(EXAMPLE_INPUT)

        expect:
        lagoon.instructions.size() == 14
        lagoon.instructions[0].direction == 'R'
        lagoon.instructions[0].distance == 6
        lagoon.instructions[0].color == '#70c710'
        lagoon.instructions[1].direction == 'D'
        lagoon.instructions[1].distance == 5
        lagoon.instructions[1].color == '#0dc571'
    }

    def "example input calculates lagoon volume of 62"() {
        given:
        LavaductLagoon lagoon = new LavaductLagoon(EXAMPLE_INPUT)

        expect:
        lagoon.calculateLagoonVolume() == 62
    }

    def "example input calculates lagoon volume of 952408144115"() {
        given:
        LavaductLagoon lagoon = new LavaductLagoon(EXAMPLE_INPUT, true)

        expect:
        lagoon.calculateLagoonVolume() == 952408144115
    }

    def "simple square lagoon"() {
        given:
        def input = '''R 2 (#000000)
D 2 (#000000)
L 2 (#000000)
U 2 (#000000)'''
        LavaductLagoon lagoon = new LavaductLagoon(input)

        expect:
        // 3x3 square = 9 cubic meters
        lagoon.calculateLagoonVolume() == 9
    }

     def "simple square lagoon loop"() {
        given:
        def input = '''R 2 (#000000)
D 2 (#000000)
L 2 (#000000)
U 2 (#000000)'''
        LavaductLagoon lagoon = new LavaductLagoon(input)
        List<LagoonPosition> expected = []
        // Only vertices (corners) are stored now
        expected.add(new LagoonPosition(0,0))  // start
        expected.add(new LagoonPosition(0,2))  // after R 2
        expected.add(new LagoonPosition(2,2))  // after D 2
        expected.add(new LagoonPosition(2,0))  // after L 2
        expected.add(new LagoonPosition(0,0))  // after U 2 (back to start)

        expect:
        lagoon.getLoop() == expected
    }

    def "convert instruction"() {
        given:
        LavaductLagoon lagoon = new LavaductLagoon(EXAMPLE_INPUT, true)

        expect:
        lagoon.instructions[0].direction == 'R'
        lagoon.instructions[0].distance == 461937
    }

    def "simple rectangle lagoon"() {
        given:
        def input = '''R 3 (#000000)
D 1 (#000000)
L 3 (#000000)
U 1 (#000000)'''
        LavaductLagoon lagoon = new LavaductLagoon(input)

        expect:
        // 4x2 rectangle = 8 cubic meters
        lagoon.calculateLagoonVolume() == 8
    }
}
