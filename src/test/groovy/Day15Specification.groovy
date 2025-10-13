import spock.lang.Specification

class Day15Specification extends Specification {

    static final String EXAMPLE_INPUT = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    def "HASH algorithm on 'HASH' returns 52"() {
        given:
        LensLibrary library = new LensLibrary()

        expect:
        library.hash("HASH") == 52
    }

    def "HASH algorithm on individual steps"() {
        given:
        LensLibrary library = new LensLibrary()

        expect:
        library.hash("rn=1") == 30
        library.hash("cm-") == 253
        library.hash("qp=3") == 97
        library.hash("cm=2") == 47
        library.hash("qp-") == 14
        library.hash("pc=4") == 180
        library.hash("ot=9") == 9
        library.hash("ab=5") == 197
        library.hash("pc-") == 48
        library.hash("pc=6") == 214
        library.hash("ot=7") == 231
    }

    def "sum of HASH results is 1320"() {
        given:
        LensLibrary library = new LensLibrary(EXAMPLE_INPUT)

        expect:
        library.sumOfHashResults() == 1320
    }

    def "after rn=1, box 0 contains rn with focal length 1"() {
        given:
        LensLibrary library = new LensLibrary("rn=1")

        when:
        library.processSteps()

        then:
        library.getBox(0).size() == 1
        library.getBox(0)[0].label == "rn"
        library.getBox(0)[0].focalLength == 1
    }

    def "dash operation removes lens from box"() {
        given:
        LensLibrary library = new LensLibrary("rn=1,cm=2,cm-")

        when:
        library.processSteps()

        then:
        library.getBox(0).size() == 1
        library.getBox(0)[0].label == "rn"
    }

    def "equals operation replaces existing lens with same label"() {
        given:
        LensLibrary library = new LensLibrary("ot=9,ab=5,pc=4,pc-,ot=7")

        when:
        library.processSteps()
        def box3 = library.getBox(3)
        def otLens = box3.lenses.find { it.label == "ot" }

        then:
        otLens != null
        otLens.focalLength == 7
    }

    def "focusing power calculation"() {
        given:
        LensLibrary library = new LensLibrary(EXAMPLE_INPUT)

        when:
        library.processSteps()

        then:
        library.calculateFocusingPower() == 145
    }
}
