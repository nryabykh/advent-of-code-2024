package com.adventofcode;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Day05 implements Day {

    Map<Integer, Set<Integer>> next = new HashMap<>();
    Map<Integer, Set<Integer>> prev = new HashMap<>();

    List<List<Integer>> updates = new ArrayList<>();
    List<List<Integer>> fails = new ArrayList<>();

    public static void main(String... args) throws IOException {
        Day solver = new Day05();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {

        parseLines(lines);

        System.out.printf("Part One: %d\n", solvePartOne());
        System.out.printf("Part Two: %d\n", solvePartTwo());

    }

    private void parseLines(List<String> lines) {
        boolean isUpdate = false;
        for (String line : lines) {
            if (line.isEmpty()) {
               isUpdate = true;
               continue;
            }

            if (isUpdate) {
                List<Integer> current = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                updates.add(current);
            } else {
                String[] split = line.split("\\|");
                int first = Integer.parseInt(split[0]);
                int second = Integer.parseInt(split[1]);

                if (next.containsKey(first)) {
                    next.get(first).add(second);
                } else {
                    next.put(first, Sets.newHashSet(second));
                }
                if (prev.containsKey(second)) {
                    prev.get(second).add(first);
                } else {
                    prev.put(second, Sets.newHashSet(first));
                }
            }
        }

    }

    private long solvePartOne() {
        long result = 0;
        for (List<Integer> update : updates) {
            boolean isFail = false;
            for (int i = 0; i < update.size(); i++) {
                int current = update.get(i);

                List<Integer> before = update.subList(0, i);
                List<Integer> after = update.subList(i + 1, update.size());

                boolean afterNotEmpty = !after.isEmpty();
                boolean beforeNotEmpty = !before.isEmpty();
                boolean allAfterCorrect = !afterNotEmpty || next.getOrDefault(current, new HashSet<>()).containsAll(after);
                boolean allBeforeCorrect = !beforeNotEmpty || prev.getOrDefault(current, new HashSet<>()).containsAll(before);

                if (!(allAfterCorrect && allBeforeCorrect)) {
                    isFail = true;
                }
            }

            if (!isFail) {
                result += update.get(update.size() / 2);
            } else {
                fails.add(update);
            }
        }

        return result;
    }

    private long solvePartTwo() {
        int result = 0;
        for (List<Integer> fail : fails) {
            for (int i = 0; i < fail.size(); i++) {
                int current = fail.get(i);
                int middle = fail.size() / 2;

                if (prev.containsKey(current) && next.containsKey(current)) {
                    List<Integer> others = Stream.concat(
                            fail.subList(0, i).stream(),
                            fail.subList(i + 1, fail.size()).stream()
                    ).toList();
                    Set<Integer> intersectionPrev = new HashSet<>(prev.get(current));
                    Set<Integer> intersectionNext = new HashSet<>(next.get(current));
                    intersectionPrev.retainAll(others);
                    intersectionNext.retainAll(others);
                    if ((intersectionPrev.size() == middle) && (intersectionNext.size() == middle)) {
                        result += current;
                        break;
                    }
                }
            }
        }

        return result;
    }

}
