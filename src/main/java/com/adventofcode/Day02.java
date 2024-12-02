package com.adventofcode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day02 implements Day {

    public static void main(String... args) throws IOException {
        Day solver = new Day02();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {

        System.out.printf("Part One: %s%n", solvePartOne(lines));
        System.out.printf("Part Two: %s%n", solvePartTwo(lines));

    }

    private Integer solvePartOne(@NotNull List<String> lines) {
        int result = 0;

        for (String line : lines) {
            List<Integer> diffs = new ArrayList<>();
            List<Integer> lineInt = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();

            for (int i = 0; i < lineInt.size() - 1; i++) {
                diffs.add(lineInt.get(i + 1) - lineInt.get(i));
            }

            Integer first = diffs.getFirst();

            if (diffs.stream().filter(i -> i * first <= 0 || Math.abs(i) > 3).toList().isEmpty()) {
                result += 1;
            }
        }

        return result;
    }

    private Integer solvePartTwo(@NotNull List<String> lines) {
        int result = 0;

        for (String line : lines) {
            List<Integer> lineInt = new ArrayList<>(Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .toList());

            if (isCorrect(getDiffs(lineInt))) {
                result += 1;
            } else {
                for (int j = 0; j < lineInt.size(); j++) {

                    int size = lineInt.size();

                    List<Integer> reducedLineInt = Stream.concat(
                            lineInt.subList(0, j).stream(),
                            lineInt.subList(j + 1, size).stream()
                    ).toList();

                    if (isCorrect(getDiffs(reducedLineInt))) {
                        result += 1;
                        break;
                    }
                }
            }
        }

        return result;
    }

    @NotNull
    private List<Integer> getDiffs(@NotNull List<Integer> lineInt) {

        List<Integer> diffs = new ArrayList<>();

        for (int i = 0; i < lineInt.size() - 1; i++) {
            diffs.add(lineInt.get(i + 1) - lineInt.get(i));
        }

        return diffs;
    }

    @NotNull
    private Boolean isCorrect(@NotNull List<Integer> diffs) {
        Integer first = diffs.getFirst();
        return diffs.stream().filter(i -> i * first <= 0 || Math.abs(i) > 3).toList().isEmpty();
    }
}
