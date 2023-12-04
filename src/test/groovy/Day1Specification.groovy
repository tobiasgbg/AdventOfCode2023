import spock.lang.Specification

class Day1Specification extends Specification {

    def "sum calibration values for document with same first and last digit"() {
        given:
        def document = ["a1b1", "c2d2"]

        expect:
        Day1Part1Trebuchet.sumCalibrationValues(document) == 33
    }
}