package com.adventofcode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class Day01 implements Day {

    public static void main(String... args) throws IOException {
        Day solver = new Day01();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {

        List<List<Integer>> values = parse(lines);

        List<Integer> first = values.getFirst();
        List<Integer> last = values.getLast();

        System.out.printf("Part One: %s%n", solvePartOne(first, last));
        System.out.printf("Part Two: %s%n", solvePartTwo(first, last));
    }

    @NotNull
    private List<List<Integer>> parse(@NotNull List<String> lines) {

        List<Integer> first = new ArrayList<>(List.of());
        List<Integer> second = new ArrayList<>(List.of());

        for (String line : lines) {
            String[] values = line.split(" {3}");
            first.add(Integer.parseInt(values[0]));
            second.add(Integer.parseInt(values[1]));
        }

        return List.of(first, second);
    }

    @NotNull
    private Integer solvePartOne(@NotNull List<Integer> first, @NotNull List<Integer> last) {
        first.sort(Integer::compareTo);
        last.sort(Integer::compareTo);

        return IntStream.range(0, first.size())
                .map(i -> abs(first.get(i) - last.get(i)))
                .sum();
    }

    private Long solvePartTwo(List<Integer> first, List<Integer> last) {

        Map<Integer, Long> firstCounted = first.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        Map<Integer, Long> lastCounted = last.stream()
                .collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        long result = 0;

        for (Map.Entry<Integer, Long> entry : firstCounted.entrySet()) {
            if (lastCounted.containsKey(entry.getKey())) {
                result += (entry.getValue() * entry.getKey() * lastCounted.get(entry.getKey()));
            }
        }

        return result;

    }
}
