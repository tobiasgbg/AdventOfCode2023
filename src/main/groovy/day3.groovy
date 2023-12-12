import groovy.io.FileType

/*
--- Day 3: Gear Ratios ---
        You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.

        It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.

"Aaah!"

You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.

The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.

        The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)

Here is an example engine schematic:

        467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
        In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.

        Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?

*/

class Day3Gondola {
    static int getSum(String input) {
        int totalSum = 0;
        List<String> lines = input.split('\n');

        for (int row = 0; row <= lines.size() - 1; row++) {
            String number = "";
            Boolean valid = false;
            for (int column = 0; column <= lines[row].length() - 1; column++) {
                String character = lines[row][column];
                if (character.isNumber()) {
                    number += character;
                    if (isValid(row, column, lines) && !valid) {
                        valid = true;
                    }
                } else {
                    if (valid) {
                        totalSum += (number as Integer);
                        println(number)
                        valid = false;
                    }
                    number = "";
                }
            }
            if (!number.isEmpty() && valid) {
                totalSum += number as Integer;
            }
        }
        return totalSum;
    }

    static boolean isValid(int row, int column, List<String> lines) {
        List<Integer> positions = [-1, 0, 1]
        for (rowOffset in positions) {
            for (columnOffset in positions) {
                def ch = "."
                    if (row + rowOffset < 0)
                        continue
                    else if (column + columnOffset < 0)
                        continue
                    else if (row + rowOffset >= lines.size())
                        continue
                    else if (column + columnOffset >= lines[row + rowOffset].size())
                        continue
                    ch = lines[row + rowOffset][column + columnOffset]
                if (!ch.isNumber() && ch != ".")
                    return true
            }
        }
        false
    }
}

static void main(String[] args) {
    try {
        String fileContents = new File('../../../input/day3.txt').getText('UTF-8')
        def sum = Day3Gondola.getSum(fileContents)
        println("Sum: ${sum}")
    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}