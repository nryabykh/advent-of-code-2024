package com.adventofcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

record Point(int x, int y) {
}

record PointWithDirection(int x, int y, Direction direction) {
}

public class Day06 implements Day {

    private Set<Point> blocks = new HashSet<>();
    private Set<Point> visited = new HashSet<>();
    private Set<PointWithDirection> visitedWithDirection = new HashSet<>();
    private Point startPoint;
    private Direction startDirection;

    int sizeX = 0;
    int sizeY = 0;

    private final HashMap<Direction, Direction> turns = new HashMap<>() {{
        put(Direction.UP, Direction.RIGHT);
        put(Direction.RIGHT, Direction.DOWN);
        put(Direction.DOWN, Direction.LEFT);
        put(Direction.LEFT, Direction.UP);
    }};

    public static void main(String... args) throws IOException {
        Day solver = new Day06();
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
                if ("^><v".indexOf(c) != -1) {
                    startPoint = new Point(j, i);
                    startDirection = switch (c) {
                        case '^' -> Direction.UP;
                        case 'v' -> Direction.DOWN;
                        case '>' -> Direction.RIGHT;
                        case '<' -> Direction.LEFT;
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    };
                }
                if (c == '#') {
                    blocks.add(new Point(j, i));
                }
            }
        }

    }

    private long solvePartOne() {

        Point currentPoint = startPoint;
        Direction currentDirection = startDirection;

        while (!isOut(currentPoint)) {
            visited.add(currentPoint);

            Point nextPoint = getNext(currentPoint, currentDirection);
            if (blocks.contains(nextPoint)) {
                currentDirection = turns.get(currentDirection);
            } else {
                currentPoint = nextPoint;
            }
        }

        return visited.size();
    }

    private long solvePartTwo() {
        int cnt = 0;

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                Point newBlock = new Point(j, i);
                if (blocks.contains(newBlock)) {
                    continue;
                }

                blocks.add(newBlock);
                visitedWithDirection = new HashSet<>();
                PointWithDirection currentPointWithDirection =
                        new PointWithDirection(startPoint.x(), startPoint.y(), startDirection);

                while (!isOut(currentPointWithDirection)) {
                    if (isCycle(currentPointWithDirection)) {
                        cnt += 1;
                        break;
                    }

                    visitedWithDirection.add(currentPointWithDirection);

                    PointWithDirection nextPointWithDirection = getNext(currentPointWithDirection);
                    if (blocks.contains(new Point(nextPointWithDirection.x(), nextPointWithDirection.y()))) {
                        currentPointWithDirection = new PointWithDirection(
                                currentPointWithDirection.x(),
                                currentPointWithDirection.y(),
                                turns.get(currentPointWithDirection.direction())
                        );
                    } else {
                        currentPointWithDirection = nextPointWithDirection;
                    }
                }

                blocks.remove(newBlock);
            }
        }

        return cnt;
    }

    private Point getNext(Point current, Direction direction) throws IllegalArgumentException {

        return switch (direction) {
            case UP -> new Point(current.x(), current.y() - 1);
            case DOWN -> new Point(current.x(), current.y() + 1);
            case LEFT -> new Point(current.x() - 1, current.y());
            case RIGHT -> new Point(current.x() + 1, current.y());
        };
    }

    private PointWithDirection getNext(PointWithDirection current) throws IllegalArgumentException {
        return switch (current.direction()) {
            case UP -> new PointWithDirection(current.x(), current.y() - 1, current.direction());
            case DOWN -> new PointWithDirection(current.x(), current.y() + 1, current.direction());
            case LEFT -> new PointWithDirection(current.x() - 1, current.y(), current.direction());
            case RIGHT -> new PointWithDirection(current.x() + 1, current.y(), current.direction());
        };
    }

    private boolean isOut(Point current) {
        return current.x() < 0 || current.y() < 0 || current.x() >= sizeX || current.y() >= sizeY;
    }

    private boolean isOut(PointWithDirection current) {
        return current.x() < 0 || current.y() < 0 || current.x() >= sizeX || current.y() >= sizeY;
    }

    private boolean isCycle(PointWithDirection current) {
        return visitedWithDirection.contains(current);
    }
}
