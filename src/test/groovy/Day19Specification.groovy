import spock.lang.Specification

class Day19Specification extends Specification {

    static final String EXAMPLE_INPUT = '''px{a<2006:qkq,m>2090:A,rfg}
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
{x=2127,m=1623,a=2188,s=1013}'''

    def "parses workflows correctly"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)

        expect:
        aplenty.workflows.size() == 11
        aplenty.workflows['in'] != null
        aplenty.workflows['px'] != null
    }

    def "parses parts correctly"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)

        expect:
        aplenty.parts.size() == 5
        aplenty.parts[0].x == 787
        aplenty.parts[0].m == 2655
        aplenty.parts[0].a == 1222
        aplenty.parts[0].s == 2876
    }

    def "part total rating calculation"() {
        given:
        Part part = new Part(787, 2655, 1222, 2876)

        expect:
        part.totalRating == 7540
    }

    def "first part is accepted"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)
        Part part = aplenty.parts[0]  // {x=787,m=2655,a=1222,s=2876}

        expect:
        aplenty.isAccepted(part)
    }

    def "second part is rejected"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)
        Part part = aplenty.parts[1]  // {x=1679,m=44,a=2067,s=496}

        expect:
        !aplenty.isAccepted(part)
    }

    def "third part is accepted"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)
        Part part = aplenty.parts[2]  // {x=2036,m=264,a=79,s=2244}

        expect:
        aplenty.isAccepted(part)
    }

    def "fourth part is rejected"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)
        Part part = aplenty.parts[3]  // {x=2461,m=1339,a=466,s=291}

        expect:
        !aplenty.isAccepted(part)
    }

    def "fifth part is accepted"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)
        Part part = aplenty.parts[4]  // {x=2127,m=1623,a=2188,s=1013}

        expect:
        aplenty.isAccepted(part)
    }

    def "example input sum of accepted ratings is 19114"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)

        expect:
        aplenty.sumAcceptedRatings() == 19114
    }

    def "example input distinct combinations is 167409079868000"() {
        given:
        Aplenty aplenty = new Aplenty(EXAMPLE_INPUT)

        expect:
        aplenty.countAcceptedCombinations() == 167409079868000L
    }
}
