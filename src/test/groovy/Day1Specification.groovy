import spock.lang.Specification

class Day1Specification extends Specification {
    def "sum calibration values for a standard document"() {
        given:
        def document = ["1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet"]
        
        expect:
        Day1Part1.sum_calibration_values(document) == 142
    }

    def "sum calibration values for an empty document"() {
        given:
        def document = []

        expect:
        Day1Part1.sum_calibration_values(document) == 0
    }

    def "sum calibration values for a document with no digits"() {
        given:
        def document = ["abc", "defg", "hijk"]

        expect:
        Day1Part1.sum_calibration_values(document) == 0
    }

    def "sum calibration values for document with single digit lines"() {
        given:
        def document = ["a1", "b2", "c3"]

        expect:
        Day1Part1.sum_calibration_values(document) == 66
    }

    def "sum calibration values for document with same first and last digit"() {
        given:
        def document = ["a1b1", "c2d2"]

        expect:
        Day1Part1.sum_calibration_values(document) == 33
    }
}
