import spock.lang.Specification

class Day6Specification extends Specification {

    def "get races"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races.size() == 3
    }

    def "get time"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[0].time == 7
    }

    def "get sum 1"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.getSum() == 288
    }

    def "get sum 2"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input, false)

        expect:
        competition.getSum() == 71503
    }

    def "get solutions 1"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[0].getNoSolutions() == 4
    }

    def "get solutions 2"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[1].getNoSolutions() == 8
    }

    def "get solutions 3"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[2].getNoSolutions() == 9
    }

    def "get distance 1"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[0].getDistance(0) == 0
    }

    def "get distance 2"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[0].getDistance(1) == 6
    }

    def "get distance 3"() {
        given:
        String input = """Time:      7  15   30
Distance:  9  40  200"""
        Competition competition = new Competition(input)

        expect:
        competition.races[0].getDistance(6) == 6
    }
}
