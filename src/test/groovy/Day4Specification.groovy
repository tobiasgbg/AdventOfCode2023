import spock.lang.Specification

class Day4Specification extends Specification {

    def "check worth"() {
        given:
        def haveNumbers = [41, 48, 83, 86, 17]
        def winNumbers = [83, 86, 6, 31, 17, 9, 48, 53]
        Card card = new Card(haveNumbers, winNumbers)

        expect:
        Day4Raffle.getWorth(card) == 8
    }

    def "get card have numbers"() {
        given:
        Card card = new Card("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")

        expect:
        card.haveNumbers.containsAll([41, 48, 83, 86, 17])
    }

    def "get card win numbers"() {
        given:
        Card card = new Card("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")

        expect:
        card.winNumbers.containsAll([83, 86, 6, 31, 17, 9, 48, 53])
    }
}
