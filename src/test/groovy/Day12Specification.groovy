import spock.lang.Specification

class Day12Specification extends Specification {

    static final String EXAMPLE_INPUT = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1"""

    def "sum of arrangement counts is 21"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        true // Replace with actual test when implemented
    }

    def "first row arrangement counts"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        hotSprings.getHotSpringsRow(0).countArrangements() == 1
    }

    def "second row arrangement count"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        hotSprings.getHotSpringsRow(1).countArrangements() == 4
    }

    def "last row arrangement count"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        hotSprings.getHotSpringsRow(5).countArrangements() == 10
    }

    def "get first row"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        hotSprings.getHotSpringsRow(0).row == "???.### 1,1,3"
    }

    def "unfold"() {
        given:
        HotSprings hotSprings = new HotSprings(EXAMPLE_INPUT)

        expect:
        hotSprings.getHotSpringsRow(0).unfold().row == "???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3"
    }
}
