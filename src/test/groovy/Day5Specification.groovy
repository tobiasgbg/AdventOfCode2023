import spock.lang.Specification

class Day5Specification extends Specification {

    def "get almanac seeds"() {
        given:
        String input = """seeds: 79 14 55 13
seed-to-soil map:
50 98 2
52 50 48"""
        Almanac almanac = new Almanac(input)

        expect:
        almanac.seeds.containsAll([79L, 14L, 55L, 13L])
    }

    def "get destinationRangeStart"() {
        given:
        String input = """seeds: 79 14 55 13
seed-to-soil map:
50 98 2
52 50 48"""
        Almanac almanac = new Almanac(input)

        expect:
        almanac.categories[0].seedMaps[0].destinationRangeStart == 50
    }

    def "get sourceRangeStart"() {
        given:
        String input = """seeds: 79 14 55 13
seed-to-soil map:
50 98 2
52 50 48"""
        Almanac almanac = new Almanac(input)

        expect:
        almanac.categories[0].seedMaps[0].sourceRangeStart == 98
    }

    def "get rangeLength"() {
        given:
        String input = """seeds: 79 14 55 13
seed-to-soil map:
50 98 2
52 50 48"""
        Almanac almanac = new Almanac(input)

        expect:
        almanac.categories[0].seedMaps[0].rangeLength == 2
    }

    def "get location number for category"() {
        given:
        Long seed = 79
        String input = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""
        Almanac almanac = new Almanac(input)

        expect:
        Day5Almanac.getLocationNumber(seed, almanac.categories[0]) == 81
    }

    def "get location number"() {
        given:
        Long seed = 79
        String input = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""
        Almanac almanac = new Almanac(input)
        Day5Almanac day5Almanac = new Day5Almanac()

        expect:
        day5Almanac.getLocationNumber(seed, almanac.categories) == 82
    }

    def "get lowest location number"() {
        given:
        String input = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""
        Almanac almanac = new Almanac(input)
        Day5Almanac day5Almanac = new Day5Almanac()

        expect:
        day5Almanac.getLowestLocationNumber(almanac) == 35
    }

    def "get lowest location number range"() {
        given:
        String input = """seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4"""
        Almanac almanac = new Almanac(input)
        Day5Almanac day5Almanac = new Day5Almanac()

        expect:
        day5Almanac.getLowestLocationNumberRange(almanac) == 46
    }
}
