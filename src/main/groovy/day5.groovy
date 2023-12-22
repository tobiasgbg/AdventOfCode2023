/*--- Day 5: If You Give A Seed A Fertilizer ---
        You take the boat and find the gardener right where you were told he would be: managing a giant "garden" that looks more to you like a farm.

        "A water source? Island Island is the water source!" You point out that Snow Island isn't receiving any water.

"Oh, we had to stop the water because we ran out of sand to filter it with! Can't make snow with dirty water. Don't worry, I'm sure we'll get more sand soon; we only turned off the water a few days... weeks... oh no." His face sinks into a look of horrified realization.

        "I've been so busy making sure everyone here has food that I completely forgot to check why we stopped getting more sand! There's a ferry leaving soon that is headed over in that direction - it's much faster than your boat. Could you please go check it out?"

You barely have time to agree to this request when he brings up another. "While you wait for the ferry, maybe you can help us with our food production problem. The latest Island Island Almanac just arrived and we're having trouble making sense of it."

The almanac (your puzzle input) lists all of the seeds that need to be planted. It also lists what type of soil to use with each kind of seed, what type of fertilizer to use with each kind of soil, what type of water to use with each kind of fertilizer, and so on. Every type of seed, soil, fertilizer and so on is identified with a number, but numbers are reused by each category - that is, soil 123 and fertilizer 123 aren't necessarily related to each other.

For example:

seeds: 79 14 55 13

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
56 93 4
The almanac starts by listing which seeds need to be planted: seeds 79, 14, 55, and 13.

The rest of the almanac contains a list of maps which describe how to convert numbers from a source category into numbers in a destination category. That is, the section that starts with seed-to-soil map: describes how to convert a seed number (the source) to a soil number (the destination). This lets the gardener and his team know which soil to use with which seeds, which water to use with which fertilizer, and so on.

        Rather than list every source number and its corresponding destination number one by one, the maps describe entire ranges of numbers that can be converted. Each line within a map contains three numbers: the destination range start, the source range start, and the range length.

Consider again the example seed-to-soil map:

50 98 2
52 50 48
The first line has a destination range start of 50, a source range start of 98, and a range length of 2. This line means that the source range starts at 98 and contains two values: 98 and 99. The destination range is the same length, but it starts at 50, so its two values are 50 and 51. With this information, you know that seed number 98 corresponds to soil number 50 and that seed number 99 corresponds to soil number 51.

        The second line means that the source range starts at 50 and contains 48 values: 50, 51, ..., 96, 97. This corresponds to a destination range starting at 52 and also containing 48 values: 52, 53, ..., 98, 99. So, seed number 53 corresponds to soil number 55.

        Any source numbers that aren't mapped correspond to the same destination number. So, seed number 10 corresponds to soil number 10.

So, the entire list of seed numbers and their corresponding soil numbers looks like this:

        seed  soil
0     0
1     1
...   ...
48    48
49    49
50    52
51    53
...   ...
96    98
97    99
98    50
99    51
With this map, you can look up the soil number required for each initial seed number:

        Seed number 79 corresponds to soil number 81.
Seed number 14 corresponds to soil number 14.
        Seed number 55 corresponds to soil number 57.
Seed number 13 corresponds to soil number 13.
        The gardener and his team want to get started as soon as possible, so they'd like to know the closest location that needs a seed. Using these maps, find the lowest location number that corresponds to any of the initial seeds. To do this, you'll need to convert each seed number through other categories until you can find its corresponding location number. In this example, the corresponding types are:

Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
Seed 55, soil 57, fertilizer 57, water 53, light 46, temperature 82, humidity 82, location 86.
Seed 13, soil 13, fertilizer 52, water 41, light 34, temperature 34, humidity 35, location 35.
So, the lowest location number in this example is 35.

What is the lowest location number that corresponds to any of the initial seed numbers?


 */

class Almanac {
    List<Long> seeds = []
    List<Category> categories = []

    void sortAllMappings() {
        for (Category category : categories) {
            category.sortSeedMaps();
        }
    }

    Almanac(String input) {
        List<String> lines = input.split("\\r\\n|\\n|\\r")
        this.seeds = lines[0].split(":")[1].trim().split(" ").collect { it.toLong() }
        List<SeedMap> seedMaps = []
        Category category = null
        lines.tail().each { line ->
            if (line != "" && line[0].isNumber())
                category.seedMaps.add(new SeedMap(line))
            else if (line != "" && !line[0].isNumber()) {
                category = new Category()
                this.categories.add(category)
            }
        }
    }
}

class Interval {
    Long start;
    Long end;

    Interval(Long start, Long end) {
        this.start = start;
        this.end = end;
    }
}

class RangeMapper {
    Almanac almanac;

    RangeMapper(Almanac almanac) {
        this.almanac = almanac;
        almanac.sortAllMappings();
    }

    Long getLowestLocationNumber() {
        List<Interval> ranges = seedRangesToInterval();
        for (Category category : almanac.categories) {
            ranges = mapRangesThroughCategory(ranges, category);
        }
        return findLowestLocationNumber(ranges);
    }

    List<Interval> seedRangesToInterval() {
        List<Interval> intervals = []
        for (int i = 0; i < almanac.seeds.size(); i += 2) {
            Long rangeStart = almanac.seeds[i];
            Long rangeLength = almanac.seeds[i + 1];
            Long rangeEnd = rangeStart + rangeLength;

            intervals.add(new Interval(rangeStart, rangeEnd));
        }
        return intervals;
    }

    static List<Interval> mapRangesThroughCategory(List<Interval> seedRanges, Category category) {
        List<Interval> mappedRanges = new ArrayList<>();

        for (Interval seedRange : seedRanges) {
            List<Interval> partialMappedRanges = mapSingleRangeThroughCategory(seedRange, category);
            mappedRanges.addAll(partialMappedRanges);
        }

        return mappedRanges;
    }

    static List<Interval> mapSingleRangeThroughCategory(Interval seedRange, Category category) {
        List<Interval> partialMappedRanges = new ArrayList<>();

        for (SeedMap seedMap : category.seedMaps) {
            if (overlaps(seedRange, seedMap)) {
                Interval overlappingRange = getOverlappingRange(seedRange, seedMap);
                Interval mappedRange = mapRange(overlappingRange, seedMap);
                partialMappedRanges.add(mappedRange);

                seedRange = updateRemainingRange(seedRange, overlappingRange);
                if (seedRange == null) {
                    break; // Fully mapped, break out of the loop
                }
            }
        }

        // If there is any unmapped part left in the seed range
        if (seedRange != null) {
            partialMappedRanges.add(seedRange); // Add it as is
        }

        return partialMappedRanges;
    }

    static boolean overlaps(Interval range, SeedMap seedMap) {
        return range.start < seedMap.sourceRangeStart + seedMap.rangeLength &&
                range.end > seedMap.sourceRangeStart;
    }

    static Interval getOverlappingRange(Interval range, SeedMap seedMap) {
        Long start = Math.max(range.start, seedMap.sourceRangeStart);
        Long end = Math.min(range.end, seedMap.sourceRangeStart + seedMap.rangeLength);
        return new Interval(start, end);
    }

    static Interval mapRange(Interval range, SeedMap seedMap) {
        Long offset = range.start - seedMap.sourceRangeStart;
        Long mappedStart = seedMap.destinationRangeStart + offset;
        Long mappedEnd = mappedStart + (range.end - range.start);
        return new Interval(mappedStart, mappedEnd);
    }

    static Interval updateRemainingRange(Interval originalRange, Interval mappedRange) {
        if (originalRange.end > mappedRange.end) {
            return new Interval(mappedRange.end, originalRange.end);
        }
        return null; // Indicates the original range is fully mapped
    }

    static Long findLowestLocationNumber(List<Interval> ranges) {
        if (ranges == null || ranges.isEmpty()) {
            return null; // or an appropriate default value or throw an exception
        }

        Long lowestLocationNumber = Long.MAX_VALUE;
        for (Interval range : ranges) {
            if (range.start < lowestLocationNumber) {
                lowestLocationNumber = range.start;
            }
        }

        return lowestLocationNumber;
    }
}


class Category {
    List<SeedMap> seedMaps = []

    void sortSeedMaps() {
        Collections.sort(seedMaps, new Comparator<SeedMap>() {
            @Override
            int compare(SeedMap map1, SeedMap map2) {
                return map1.sourceRangeStart <=> map2.sourceRangeStart;
            }
        });
    }
}

class SeedMap {
    Long destinationRangeStart
    Long sourceRangeStart
    Long rangeLength

    SeedMap(String line) {
        List<String> parts = line.split(" ")
        this.destinationRangeStart = parts[0] as Long
        this.sourceRangeStart = parts[1] as Long
        this.rangeLength = parts[2] as Long
    }
}

class Day5Almanac {
    static Long getLowestLocationNumber(Almanac almanac) {
        Long result = Long.MAX_VALUE
        for (Long seed in almanac.seeds) {
            Long location = getLocationNumber(seed, almanac.categories)
            if (location < result) {
                println("Lowest location number now for seed ${seed}: ${location}")
                result = location
            }
        }
        result
    }

    static Long getLowestLocationNumberRange(Almanac almanac) {
        Long result = Long.MAX_VALUE
        for (int i = 0; ; i++) {
            Long location = getLocationNumber(i, almanac.categories.reverse(), true)
            for (int j = 0; j < almanac.seeds.size() - 1; j+=2) {
                if (location in almanac.seeds[j]..almanac.seeds[j]+almanac.seeds[j+1]) {
                    return i
                }
            }
        }
        result
    }

    static Long getLocationNumber(Long seed, List<Category> categories, boolean reverse = false) {
        Long result = seed
        categories.each {category ->
            result = getLocationNumber(result, category, reverse)
        }

        result
    }

    static Long getLocationNumber(Long seed, Category category, boolean reverse = false) {
        for (SeedMap seedMap : category.seedMaps) {

            Long sourceRangeStart = reverse ? seedMap.destinationRangeStart : seedMap.sourceRangeStart
            Long destinationRangeStart = reverse ? seedMap.sourceRangeStart : seedMap.destinationRangeStart

            // Check if the seed is within the source range
            if (seed >= sourceRangeStart && seed < sourceRangeStart + seedMap.rangeLength) {
                // Calculate the offset from the start of the source range
                Long offset = seed - sourceRangeStart;

                // Apply the same offset to the destination range
                return destinationRangeStart + offset;
            }
        }
        // If the seed is not in any map, it maps to itself
        return seed;
    }
}

static void main(String[] args) {
    try {
        // Specify the file path
        String filePath = "../../../input/day5.txt"

        // Create a File object
        File file = new File(filePath)

        Almanac almanac = new Almanac(file.text)

        Long lowestLocationNumber = Day5Almanac.getLowestLocationNumber(almanac)

        println("Lowest location number: ${lowestLocationNumber}")

        RangeMapper rangeMapper = new RangeMapper(almanac)

        Long lowestLocationNumberRange = rangeMapper.getLowestLocationNumber()

        println("Lowest location number range: ${lowestLocationNumberRange}")


    } catch (FileNotFoundException e) {
        println("File not found: " + e.message)
    } catch (IOException e) {
        println("Error reading file: " + e.message)
    }
}