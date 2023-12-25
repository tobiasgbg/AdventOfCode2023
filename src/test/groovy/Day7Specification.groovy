import spock.lang.Specification

class Day7Specification extends Specification {

    def "get hand count"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)

        expect:
        camelCards.hands.size() == 5
    }

    def "sort hands 1"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)
        camelCards.hands.sort()

        expect:
        camelCards.hands[4].cards == "QQQJA"
    }

    def "sort hands 2"() {
        given:
        String input = """32T3K 765
5555K 28
5555T 684
111JA 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)
        camelCards.hands.sort()

        expect:
        camelCards.hands[4].cards == "5555K"
    }

    def "sort hands 3"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)
        camelCards.hands.sort()

        expect:
        camelCards.hands[0].cards == "32T3K"
    }

    def "get value 1"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)

        expect:
        camelCards.hands[0].getValue() == 5
    }

    def "get value 2"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)

        expect:
        camelCards.hands[4].getValue() == 7
    }

    def "get winnings"() {
        given:
        String input = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483"""
        CamelCards camelCards = new CamelCards(input)

        expect:
        camelCards.getWinnings() == 6440
    }
}
