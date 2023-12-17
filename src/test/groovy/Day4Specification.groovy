import spock.lang.Specification

class Day4Specification extends Specification {

    def "check worth"() {
        given:
        Card card = new Card("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")

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

    def "get total numbers of cards 1"() {
        given:
        Card card = new Card("Card 1: 1 2 3 4 5 | 83 86  6 31 17  9 48 53")

        expect:
        Day4Raffle.getTotalCards([card]) == 1
    }

    def "get total numbers of cards 2"() {
        given:
        Card card1 = new Card("Card 1: 1 2 3 4 6 | 83 86  6 31 17  9 48 53")
        Card card2 = new Card("Card 1: 1 2 3 4 5 | 83 86  6 31 17  9 48 53")

        expect:
        Day4Raffle.getTotalCards([card1, card2]) == 3
    }
}
