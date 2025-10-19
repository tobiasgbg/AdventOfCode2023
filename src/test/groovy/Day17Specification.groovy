import spock.lang.Specification

class Day17Specification extends Specification {

    static final String EXAMPLE_INPUT = '''2413432311323
3215453535623
3255245654254
3446585845452
4546657867536
1438598798454
4457876987766
3637877979653
4654967986887
4564679986453
1224686865563
2546548887735
4322674655533'''

    def "crucible parses grid correctly"() {
        given:
        ClumsyCrucible crucible = new ClumsyCrucible(EXAMPLE_INPUT)

        expect:
        crucible.grid.size() == 13
        crucible.grid[0].size() == 13
        crucible.grid[0][0] == 2
        crucible.grid[0][1] == 4
    }

    def "small grid minimum heat loss"() {
        given:
        def input = '''19
99'''
        ClumsyCrucible crucible = new ClumsyCrucible(input)

        expect:
        crucible.findMinimumHeatLoss() == 18  // Right then Down: 9 + 9 = 18
    }

    def "example input has minimum heat loss of 102"() {
        given:
        ClumsyCrucible crucible = new ClumsyCrucible(EXAMPLE_INPUT)

        expect:
        crucible.findMinimumHeatLoss() == 102
    }

    def "cannot move more than 3 blocks in same direction"() {
        given:
        def input = '''1111
9991
9991
1111'''
        ClumsyCrucible crucible = new ClumsyCrucible(input, true)  // Enable debug

        expect:
        // Must turn after 3 blocks, can't go straight across top
        crucible.findMinimumHeatLoss() > 4
    }

    def "can turn left or right"() {
        given:
        def input = '''191
191
111'''
        ClumsyCrucible crucible = new ClumsyCrucible(input)

        expect:
        // Should be able to navigate turning
        crucible.findMinimumHeatLoss() == 4  // Down, Down, Right, Right
    }
}
