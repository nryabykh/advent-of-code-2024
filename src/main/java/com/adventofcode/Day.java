package com.adventofcode;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;

public interface Day {

    public default List<String> getLines(String... args) throws IOException {
        String dayNumber = args[0];
        boolean isTest = args.length >= 2 && args[1].equals("test");

        String inputFile = String.format("day%s%s.txt", dayNumber, isTest ? "_test" : "");
        return Resources.readLines(ClassLoader.getSystemResource(inputFile), Charsets.UTF_8);
    }

    public void run(List<String> lines);

}
