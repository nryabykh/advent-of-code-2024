package com.adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Task(long target, List<Integer> numbers){ }

public class Day07 implements Day {

    List<Task> tasks = new ArrayList<>();

    public static void main(String... args) throws IOException {
        Day solver = new Day07();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {

        parseLines(lines);

        System.out.printf("Part One: %d\n", solvePartOne());
        System.out.printf("Part Two: %d\n", solvePartTwo());

    }

    private long solvePartOne() {
        long result = 0;
        for (Task task : tasks) {
            if (getNext(0, task.target(), task.numbers(), false)) {
                result += task.target();
            }
        }
        return result;
    }

    private long solvePartTwo() {
        long result = 0;
        for (Task task : tasks) {
            if (getNext(0, task.target(), task.numbers(), true)) {
                result += task.target();
            }
        }
        return result;
    }

    private boolean getNext(long currentResult, long target, List<Integer> nextNumbers, boolean useConcat) {
        if (nextNumbers.isEmpty()) {
            return currentResult == target;
        }

        if (currentResult > target) {
            return false;
        }

        int nextNumber = nextNumbers.getFirst();
        long sumResult = currentResult + nextNumber;
        long multiplicationResult = currentResult * nextNumber;

        long m = (long) Math.pow(10, Integer.toString(nextNumber).length());
        long concatResult = currentResult * m + nextNumber;

        List<Integer> tailNumbers = nextNumbers.subList(1, nextNumbers.size());

        boolean common = getNext(sumResult, target, tailNumbers, useConcat) || getNext(multiplicationResult, target, tailNumbers,useConcat);

        return useConcat ? common || getNext(concatResult, target, tailNumbers, useConcat) : common;
    }

    private void parseLines(List<String> lines) {
        for (String line : lines) {
            tasks.add(parseLine(line));
        }

    }

    private Task parseLine(String line) {
        long target = Long.parseLong(line.split(":")[0]);

        String regex = " (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        List<Integer> numbers = new ArrayList<>();
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(1)));
        }

        return new Task(target, numbers);
    }

}
