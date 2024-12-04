package com.adventofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day04 implements Day {

    int sizeX = 0;
    int sizeY = 0;

    List<char[]> asChars = new ArrayList<>();

    public static void main(String... args) throws IOException {
        Day solver = new Day04();
        solver.run(solver.getLines(args));
    }

    @Override
    public void run(List<String> lines) {

        parseMap(lines);

        System.out.printf("Part One: %d\n", solvePartOne());
        System.out.printf("Part Two: %d\n", solvePartTwo());

    }

    private void parseMap(List<String> lines) {
        sizeX = lines.getFirst().length();
        sizeY = lines.size();

        for (String line : lines) {
            asChars.add(line.toCharArray());
        }
    }

    private long solvePartOne() {
        int cnt = 0;

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {

                if (asChars.get(i)[j] != 'X') {
                    continue;
                }

                Boolean e = check(i, j + 1, 'M') && check(i, j + 2, 'A') && check(i, j + 3, 'S');
                Boolean se = check(i + 1, j + 1, 'M') && check(i + 2, j + 2, 'A') && check(i + 3, j + 3, 'S');
                Boolean s = check(i + 1, j, 'M') && check(i + 2, j, 'A') && check(i + 3, j, 'S');
                Boolean sw = check(i + 1, j - 1, 'M') && check(i + 2, j - 2, 'A') && check(i + 3, j - 3, 'S');
                Boolean w = check(i, j - 1, 'M') && check(i, j - 2, 'A') && check(i, j - 3, 'S');
                Boolean nw = check(i - 1, j - 1, 'M') && check(i - 2, j - 2, 'A') && check(i - 3, j - 3, 'S');
                Boolean n = check(i - 1, j, 'M') && check(i - 2, j, 'A') && check(i - 3, j, 'S');
                Boolean ne = check(i - 1, j + 1, 'M') && check(i - 2, j + 2, 'A') && check(i - 3, j + 3, 'S');

                cnt += Stream.of(e, se, s, sw, w, nw, n, ne)
                        .map(b -> b ? 1 : 0)
                        .reduce(0, Integer::sum);
            }
        }

        return cnt;
    }

    private long solvePartTwo() {
        int cnt = 0;

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {

                if (asChars.get(i)[j] != 'A') {
                    continue;
                }

                Boolean nwSe = check(i - 1, j - 1, 'M') && check(i + 1, j + 1, 'S');
                Boolean seNw = check(i + 1, j + 1, 'M') && check(i - 1, j - 1, 'S');
                Boolean swNe = check(i + 1, j - 1, 'M') && check(i - 1, j + 1, 'S');
                Boolean neSw = check(i - 1, j + 1, 'M') && check(i + 1, j - 1, 'S');

                if ((nwSe || seNw) && (swNe || neSw)) {
                    cnt += 1;
                }

            }
        }

        return cnt;
    }

    private Boolean check(int x, int y, char letter) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY && asChars.get(x)[y] == letter;
    }
}
