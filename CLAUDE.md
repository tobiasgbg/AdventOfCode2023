# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Advent of Code 2023 solutions repository using Groovy with Gradle. Each day's puzzle has its own implementation file and corresponding Spock test specifications.

## Build and Test Commands

**Build the project:**
```bash
./gradlew build
```

**Run all tests:**
```bash
./gradlew test
```

**Run a specific day's test:**
```bash
./gradlew test --tests Day10Specification
./gradlew test --tests "Day10Specification.get steps to furthest point 1"
```

**Run a single day's solution (from src/main/groovy/):**
```bash
groovy src/main/groovy/day1part1.groovy
```

## Architecture and Structure

### File Organization

- **src/main/groovy/**: Solution implementations
  - Each file is named `day{N}.groovy` or `day{N}part{1|2}.groovy`
  - Files contain both the solution class and a `main()` method to run against actual input
  - Each file includes the full problem description from Advent of Code as a comment header

- **src/test/groovy/**: Spock test specifications
  - Named `Day{N}Specification.groovy` following Spock conventions
  - Tests use example inputs from the problem descriptions
  - Tests verify individual components and final solutions

- **input/**: Puzzle input files
  - Named `day{N}.txt`
  - Solutions read these files using relative paths like `../../../input/day1.txt`

### Solution Pattern

Each day follows this general structure:

1. **Problem description** as multi-line comment at the top
2. **Solution class** containing the core logic (e.g., `Day1Trebuchet`, `PipeMaze`)
3. **Static main method** that:
   - Reads input from `../../../input/day{N}.txt`
   - Instantiates the solution class
   - Calls solution methods
   - Prints results

### Testing Strategy

- Spock framework (Groovy testing) with BDD-style test names
- Tests use the example inputs provided in the Advent of Code problem descriptions
- Test granularity: break down complex problems into smaller testable methods
- Tests verify both intermediate steps and final answers

### Common Implementation Patterns

**Day 10 (Pipe Maze)** demonstrates advanced patterns:
- 2D grid navigation with coordinate tracking
- Loop detection and traversal
- Geometric algorithms (Shoelace formula for polygon area)
- State machine for direction changes

**Day 1 (Trebuchet)** demonstrates string processing patterns:
- Substring matching for both digits and word representations
- Handling overlapping matches (e.g., "twone" contains both "two" and "one")

## Development Workflow

When implementing a new day's solution:

1. Create `src/main/groovy/day{N}.groovy` with problem description
2. Create `src/test/groovy/Day{N}Specification.groovy` with example test cases
3. Implement solution class with descriptive method names
4. Add main method that reads from `input/day{N}.txt`
5. Run tests to verify against examples before running on actual input
