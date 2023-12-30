import spock.lang.Specification

class Day9Specification extends Specification {

    def "get histories count"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.histories.size() == 3
    }

    def "get history values"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.histories[0].values == [0,3,6,9,12,15]
    }

    def "get extrapolated value 1"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.histories[0].getNextValue() == 18
    }

    def "get extrapolated value 2"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.histories[1].getNextValue() == 28
    }

    def "get extrapolated value 3"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.histories[2].getNextValue() == 68
    }

    def "get sum"() {
        given:
        String input = """0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45"""
        Oasis oasis = new Oasis(input)

        expect:
        oasis.getSum() == 114
    }
}
