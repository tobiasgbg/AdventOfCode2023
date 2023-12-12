def isSymbol(character) {
    character != '.' && !Character.isDigit(character as char)
}

def isAdjacentToSymbol(schematic, x, y) {
    for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
            if (dx == 0 && dy == 0) continue
            int nx = x + dx
            int ny = y + dy
            if (nx >= 0 && ny >= 0 && nx < schematic[0].length() && ny < schematic.size()) {
                if (isSymbol(schematic[ny][nx] as char)) return true
            }
        }
    }
    return false
}

def sumPartNumbers(schematic) {
    int sum = 0
    schematic.eachWithIndex { line, y ->
        line.eachWithIndex { character, x ->
            if (Character.isDigit(character as char)) {
                StringBuilder numberStr = new StringBuilder()
                numberStr.append(character)
                // Check for multi-digit numbers
                int i = 1
                while (x + i < line.length() && Character.isDigit(line[x + i] as char)) {
                    numberStr.append(line[x + i])
                    i++
                }
                if (isAdjacentToSymbol(schematic, x, y)) {
                    sum += Integer.parseInt(numberStr.toString())
                }
                x += i - 1 // Skip the rest of the number
            }
        }
    }
    return sum
}

def fileContents = new File('../../../input/day3.txt').text.split('\n')

println "Sum of part numbers: ${sumPartNumbers(fileContents)}"
