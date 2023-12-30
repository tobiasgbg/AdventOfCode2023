/*
--- Day 8: Haunted Wasteland ---
You're still riding a camel across Desert Island when you spot a sandstorm quickly approaching. When you turn to warn the Elf, she disappears before your eyes! To be fair, she had just finished warning you about ghosts a few minutes ago.

One of the camel's pouches is labeled "maps" - sure enough, it's full of documents (your puzzle input) about how to navigate the desert. At least, you're pretty sure that's what they are; one of the documents contains a list of left/right instructions, and the rest of the documents seem to describe some kind of network of labeled nodes.

It seems like you're meant to use the left/right instructions to navigate the network. Perhaps if you have the camel follow the same instructions, you can escape the haunted wasteland!

After examining the maps for a bit, two nodes stick out: AAA and ZZZ. You feel like AAA is where you are now, and you have to follow the left/right instructions until you reach ZZZ.

This format defines each node of the network individually. For example:

RL

AAA = (BBB, CCC)
BBB = (DDD, EEE)
CCC = (ZZZ, GGG)
DDD = (DDD, DDD)
EEE = (EEE, EEE)
GGG = (GGG, GGG)
ZZZ = (ZZZ, ZZZ)
Starting with AAA, you need to look up the next element based on the next left/right instruction in your input. In this example, start with AAA and go right (R) by choosing the right element of AAA, CCC. Then, L means to choose the left element of CCC, ZZZ. By following the left/right instructions, you reach ZZZ in 2 steps.

Of course, you might not find ZZZ right away. If you run out of left/right instructions, repeat the whole sequence of instructions as necessary: RL really means RLRLRLRLRLRLRLRL... and so on. For example, here is a situation that takes 6 steps to reach ZZZ:

LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)

Starting at AAA, follow the left/right instructions. How many steps are required to reach ZZZ?
 */

class Wasteland {
    String directions = ""
    HashMap<String, Node> nodes = [:]
    def isGhost = false

    Wasteland(String input, def isGhost = false) {
        this.isGhost = isGhost
        List<String> lines = input.split("\\r\\n|\\n|\\r")
        directions = lines.first()
        for (line in lines.subList(2, lines.size())) {
            def pattern = ~/(.+) = \((.+), (.+)\)/
            def matcher = pattern.matcher(line)

            if (matcher.find()) {
                def position = matcher.group(1)
                def left = matcher.group(2)
                def right = matcher.group(3)

                nodes.put(position, new Node(left, right))
            } else {
                println "No match found"
            }
        }
    }

    def getNoStepsToGoal() {

        String start = isGhost ? "A" : "AAA"
        def startNodes = nodes.keySet().findAll { key -> key.endsWith(start) }
        List<Integer> distances = []

        for (startNode in startNodes) {
            Integer distance = getNoStepsToGoal(startNode)
            distances.add(distance)
        }

        lcmOfList(distances)
    }

    def getNoStepsToGoal(String node) {
        boolean atGoal = false
        def currentNode = node
        def steps = 0
        while (!atGoal) {
            def direction = directions[steps % directions.size()]

            if (direction == "L") {
                currentNode = nodes[currentNode].left
            } else if (direction == "R") {
                currentNode = nodes[currentNode].right
            }

            if (isGhost && currentNode.endsWith("Z"))
                atGoal = true
            else if (currentNode == "ZZZ")
                atGoal = true

            steps++
        }
        steps
    }

    static def gcd(BigDecimal a, BigDecimal b) {
        while (b != 0) {
            def temp = b
            b = a.remainder(b)
            a = temp
        }
        return a
    }

    static def lcm(a, b) {
        return (a * b) / gcd(a, b)
    }

    static def lcmOfList(numbers) {
        def lcmResult = numbers[0]
        numbers.each { n ->
            lcmResult = lcm(lcmResult, n)
        }
        return lcmResult
    }
}

class Node {
    def left = ""
    def right = ""

    Node (def left, def right) {
        this.left = left
        this.right = right
    }
}

static void main(String[] args) {
    try {
        // Specify the file path
        String filePath = "../../../input/day8.txt"

        // Create a File object
        File file = new File(filePath)

        Wasteland wasteland = new Wasteland(file.text)

        def stepsToGoal = wasteland.getNoStepsToGoal()

        println("Number of steps to goal: ${stepsToGoal}")

        Wasteland wastelandAsGhost = new Wasteland(file.text, true)

        def stepsToGoalAsGhost = wastelandAsGhost.getNoStepsToGoal()

        println("Number of steps to goal as ghost: ${stepsToGoalAsGhost}")


    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}