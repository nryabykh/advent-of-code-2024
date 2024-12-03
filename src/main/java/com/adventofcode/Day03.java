package com.adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 implements Day {

    public static void main(String... args) throws IOException {
        Day solver = new Day03();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {
        System.out.printf("Part One: %d\n", solvePartOne(lines));
        System.out.printf("Part Two: %d\n", solvePartTwo(lines));

    }

    private Matcher findAllMuls(String line) {
        String regexMul = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regexMul);

        return pattern.matcher(line);
    }

    private long solvePartOne(List<String> lines) {
        long result = 0;

        for (String line : lines) {
            Matcher matcher = findAllMuls(line);

            while (matcher.find()) {
                result += Long.parseLong(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }

        }

        return result;
    }

    private long solvePartTwo(List<String> lines) {

        String totalLine = String.join("", lines);
        String splitRegex = "(?=(do\\(\\)|don't\\(\\)))";

        List<String> doExpressions = Arrays.stream(totalLine.split(splitRegex))
            .filter(s -> !s.startsWith("don't()"))
            .toList();

        return solvePartOne(doExpressions);
    }

}
