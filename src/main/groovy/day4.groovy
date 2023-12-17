class Card {
    Integer number
    List<Integer> haveNumbers
    List<Integer> winNumbers

    Card(String line) {
        List<String> parts = line.split(":")
        this.number = parts[0].split(" ").findAll(it -> it != "")[1] as Integer
        parts = parts[1].split('\\|').findAll(it -> it != "")
        this.haveNumbers = parts[0].trim().split("\\s+").collect { it.trim().toInteger() }
        this.winNumbers = parts[1].trim().split("\\s+").collect { it.trim().toInteger() }
    }
}

class Day4Raffle {
    static int getWorth(Card card) {
        def winningNumbers = card.haveNumbers.findAll {it in card.winNumbers}
        1 * Math.pow(2, winningNumbers.size() - 1)
    }
    static int getTotalCards(List<Card> cards) {
        List<Card> originalCards = cards.collect()
        int totalCards = originalCards.size()
        for (int i = 0; i < cards.size(); i++) {
            def winningNumbers = cards[i].haveNumbers.findAll {it in cards[i].winNumbers}
            totalCards += winningNumbers.size()
            for (int j = cards[i].number; j < cards[i].number + winningNumbers.size(); j++)
                cards.add(originalCards[j])
        }
        totalCards
    }
}

static void main(String[] args) {
    try {
        // Specify the file path
        String filePath = "../../../input/day4.txt"

        // Create a File object
        File file = new File(filePath)

        // Read file contents into a List of Strings
        List<String> lines = file.readLines()

        List<Card> cards = []
        for (line in lines) {
            cards.add(new Card(line))
        }

        int totalWorth = 0
        for (card in cards)
            totalWorth += Day4Raffle.getWorth(card)

        println("Total worth: ${totalWorth}")

        int totalCards = Day4Raffle.getTotalCards(cards)

        println("Total cards: ${totalCards}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}