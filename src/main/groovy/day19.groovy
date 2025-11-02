/*
--- Day 19: Aplenty ---
The Elves of Gear Island are thankful for your help and send you on your way. They even have a hang glider that someone stole from Desert Island; since you're already going that direction, it would help them a lot if you would use it to get down there and return it to them.

As you reach the bottom of the relentless avalanche of machine parts, you discover that they're already forming a formidable heap. Don't worry, though - a group of Elves is already here organizing the parts, and they have a system.

To start, each part is rated in each of four categories:

x: Extremely cool looking
m: Musical (it makes a noise when you hit it)
a: Aerodynamic
s: Shiny
Then, each part is sent through a series of workflows that will ultimately accept or reject the part. Each workflow has a name and contains a list of rules; each rule specifies a condition and where to send the part if the condition is true. The first rule that matches the part being considered is applied immediately, and the part moves on to the destination described by the rule. (The last rule in each workflow has no condition and always applies if reached.)

Consider the workflow ex{x>10:one,m<20:two,a>30:R,A}. This workflow is named ex and contains four rules. If workflow ex were considering a specific part, it would perform the following steps in order:

Rule "x>10:one": If the part's x is more than 10, send the part to the workflow named one.
Rule "m<20:two": Otherwise, if the part's m is less than 20, send the part to the workflow named two.
Rule "a>30:R": Otherwise, if the part's a is more than 30, the part is immediately rejected (R).
Rule "A": Otherwise, because no other rules matched the part, the part is immediately accepted (A).
If a part is sent to another workflow, it immediately switches to the start of that workflow instead and never returns. If a part is accepted (sent to A) or rejected (sent to R), the part immediately stops any further processing.

The system works, but it's not keeping up with the torrent of weird metal shapes. The Elves ask if you can help sort a few parts and give you the list of workflows and some part ratings (your puzzle input). For example:

px{a<2006:qkq,m>2090:A,rfg}
pv{a>1716:R,A}
lnx{m>1548:A,A}
rfg{s<537:gd,x>2440:R,A}
qs{s>3448:A,lnx}
qkq{x<1416:A,crn}
crn{x>2662:A,R}
in{s<1351:px,qqz}
qqz{s>2770:qs,m<1801:hdj,R}
gd{a>3333:R,R}
hdj{m>838:A,pv}

{x=787,m=2655,a=1222,s=2876}
{x=1679,m=44,a=2067,s=496}
{x=2036,m=264,a=79,s=2244}
{x=2461,m=1339,a=466,s=291}
{x=2127,m=1623,a=2188,s=1013}
The workflows are listed first, followed by a blank line, then the ratings of the parts the Elves would like you to sort. All parts begin in the workflow named in. In this example, the five listed parts go through the following workflows:

{x=787,m=2655,a=1222,s=2876}: in -> qqz -> qs -> lnx -> A
{x=1679,m=44,a=2067,s=496}: in -> px -> rfg -> gd -> R
{x=2036,m=264,a=79,s=2244}: in -> qqz -> hdj -> pv -> A
{x=2461,m=1339,a=466,s=291}: in -> px -> qkq -> crn -> R
{x=2127,m=1623,a=2188,s=1013}: in -> px -> rfg -> A
Ultimately, three parts are accepted. Adding up the x, m, a, and s rating for each of the accepted parts gives 7540 for the part with x=787, 4623 for the part with x=2036, and 6951 for the part with x=2127. Adding all of the ratings for all of the accepted parts gives the sum total of 19114.

Sort through all of the parts you've been given; what do you get if you add together all of the rating numbers for all of the parts that ultimately get accepted?

 */

@groovy.transform.Canonical
class Part {
    int x, m, a, s

    int getTotalRating() {
        x + m + a + s
    }
}

@groovy.transform.Canonical
class Rule {
    String condition    // e.g., "x>10", "m<20", or null for default rule
    String destination  // workflow name, "A" (accept), or "R" (reject)

    boolean matches(Part part) {
        if (!condition) return true  // Default rule always matches

        def matcher = condition =~ /(\w)([<>])(\d+)/
        def (property, operator, value) = [matcher[0][1], matcher[0][2], matcher[0][3] as int]
        def partValue = part."${property}"

        return operator == '<' ? partValue < value : partValue > value
    }
}

@groovy.transform.Canonical
class Workflow {
    String name
    List<Rule> rules
}

class Aplenty {
    Map<String, Workflow> workflows = [:]
    List<Part> parts = []

    Aplenty(String input) {
        def sections = input.split(/\r?\n\r?\n/)  // Handle both Unix and Windows line endings
        parseWorkflows(sections[0])
        parseParts(sections[1])
    }

    private void parseWorkflows(String workflowSection) {
        workflows = workflowSection.readLines()
            .findAll { it.trim() }  // Filter out empty lines
            .collectEntries { line ->
                def (name, rulesText) = (line =~ /(\w+)\{(.+)\}/)[0][1..2]
                def rules = rulesText.split(',').collect { ruleText ->
                    def parts = ruleText.split(':')
                    parts.size() == 2
                        ? new Rule(parts[0], parts[1])  // Conditional rule
                        : new Rule(null, parts[0])       // Default rule
                }
                [(name): new Workflow(name, rules)]
            }
    }

    private void parseParts(String partSection) {
        parts = partSection.readLines().collect { line ->
            def matcher = line =~ /(\w)=(\d+)/
            def values = matcher.collect { it }.collectEntries { match ->
                [(match[1]): match[2] as int]
            }
            new Part(values.x, values.m, values.a, values.s)
        }
    }

    boolean isAccepted(Part part, Workflow workflow = null) {
        workflow = workflow ?: workflows["in"]

        def matchingRule = workflow.rules.find { it.matches(part) }

        switch(matchingRule.destination) {
            case "A": return true
            case "R": return false
            default: return isAccepted(part, workflows[matchingRule.destination])
        }
    }

    int sumAcceptedRatings() {
        int sum = 0
        for (part in parts) {
            if (isAccepted(part)) {
                sum += part.getTotalRating()
            }
        }

        return sum
    }
}

static void main(String[] args) {
    try {
        String filePath = "../../../input/day19.txt"
        File file = new File(filePath)
        String input = file.text.trim()

        Aplenty aplenty = new Aplenty(input)
        int result = aplenty.sumAcceptedRatings()

        println("Part 1: ${result}")

    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}
