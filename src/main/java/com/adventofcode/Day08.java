package com.adventofcode;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day08 implements Day {

    record Point(int x, int y) {
    }

    Map<Character, List<Point>> points = new HashMap<>();
    Set<Point> antennas = new HashSet<>();
    Set<Point> antinodes = new HashSet<>();
    int sizeX = 0;
    int sizeY = 0;

    public static void main(String... args) throws IOException {
        Day solver = new Day08();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {
        parseLines(lines);

        System.out.printf("Part One: %d\n", solvePartOne());
        System.out.printf("Part Two: %d\n", solvePartTwo());

    }

    private void parseLines(List<String> lines) {
        sizeX = lines.getFirst().length();
        sizeY = lines.size();

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                char c = lines.get(i).charAt(j);

                if (c == '.') { continue; }

                Point current = new Point(j, i);

                if (points.containsKey(c)) {
                    points.get(c).add(current);
                } else {
                    points.put(c, new ArrayList<Point>(List.of(current)));
                }

                antennas.add(current);
            }
        }

    }

    private long solvePartOne() {
        return calcAntinodes(this::getNearestAntinodes);
    }

    private long solvePartTwo() {
        return calcAntinodes(this::getAllAntinodes);
    }

    private long calcAntinodes(BiFunction<Point, Point, List<Point>> func) {
        antinodes.clear();

        for (char c : points.keySet()) {
            for (int i = 0; i < points.get(c).size(); i++) {
                for (int j = i + 1; j < points.get(c).size(); j++) {
                    Point first = points.get(c).get(i);
                    Point second = points.get(c).get(j);

                    for (Point p : func.apply(first, second)) {
                        if (!antinodes.contains(p)) {
                            antinodes.add(p);
                        }
                    }
                }
            }
        }
        return antinodes.size();
    }

    private List<Point> getNearestAntinodes(Point first, Point second) {
        int diffX = second.x - first.x;
        int diffY = second.y - first.y;

        return Stream.of(new Point(first.x - diffX, first.y - diffY), new Point(second.x + diffX, second.y + diffY))
                .filter(p -> p.x >= 0 && p.x < sizeX && p.y >= 0 && p.y < sizeY)
                .toList();
    }

    private List<Point> getAllAntinodes(Point first, Point second) {
        int diffX = second.x - first.x;
        int diffY = second.y - first.y;

        int steps = Math.max(
                (int) Math.ceil((float) sizeX / diffX),
                (int) Math.ceil((float) sizeY / diffY)
        );

        List<Point> currentAntinodes = new ArrayList<Point>();

        for (int i = 0; i < steps; i++) {
            currentAntinodes.add(new Point(first.x - diffX * i, first.y - diffY * i));
            currentAntinodes.add(new Point(first.x + diffX * i, first.y + diffY * i));
        }

        return currentAntinodes.stream().filter(p -> p.x >= 0 && p.x < sizeX && p.y >= 0 && p.y < sizeY).toList();
    }


}
