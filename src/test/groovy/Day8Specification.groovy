import spock.lang.Specification

class Day8Specification extends Specification {

    def "create wasteland get nodes"() {
        given:
        String input = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""
        Wasteland wasteland = new Wasteland(input)

        expect:
        wasteland.nodes.size() == 7
    }

    def "create wasteland get directions"() {
        given:
        String input = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""
        Wasteland wasteland = new Wasteland(input)

        expect:
        wasteland.directions.size() == 2
    }

    def "go to goal number of steps 1"() {
        given:
        String input = """RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)"""
        Wasteland wasteland = new Wasteland(input)

        expect:
        wasteland.getNoStepsToGoal() == 2
    }

    def "go to goal number of steps 2"() {
        given:
        String input = """LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""
        Wasteland wasteland = new Wasteland(input)

        expect:
        wasteland.getNoStepsToGoal() == 6
    }

    def "go to goal number of steps as ghost"() {
        given:
        String input = """LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""
        Wasteland wasteland = new Wasteland(input, true)

        expect:
        wasteland.getNoStepsToGoal() == 6
    }
}
