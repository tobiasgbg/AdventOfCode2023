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
}
