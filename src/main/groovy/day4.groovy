class Card {
    List<Integer> haveNumbers
    List<Integer> winNumbers

    Card(List<Integer> haveNumbers, List<Integer> winNumbers) {
        this.haveNumbers = haveNumbers
        this.winNumbers = winNumbers
    }

    Card(String line) {
        List<String> parts = line.split(":")[1].split('\\|').findAll(it -> it != "")
        this.haveNumbers = parts[0].trim().split("\\s+").collect { it.trim().toInteger() }
        this.winNumbers = parts[1].trim().split("\\s+").collect { it.trim().toInteger() }
    }
}

class Day4Raffle {
    static int getWorth(Card card) {
        def winningNumbers = card.haveNumbers.findAll {it in card.winNumbers}
        1 * Math.pow(2, winningNumbers.size()- 1)
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

        int totalWorth = 0
        for (line in lines) {
            Card card = new Card(line)
            totalWorth += Day4Raffle.getWorth(card)
        }

        println("Total worth: ${totalWorth}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}