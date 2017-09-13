package com.gft.employeeappraisal.helper.builder.util;

/**
 * Generates unique GFT identifiers for testing
 */
public class GftIdentifierGenerator {

    private static final char chars[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '0',
            '-', '+', '=', '(', ')', '?', '<', '>', '*', '$'};
    private static final int SIZE = 4;

    private static GftIdentifierGenerator instance;

    private final Counter counter = new Counter(SIZE, chars.length);

    private GftIdentifierGenerator() {
    }

    public static String next() {
        if (instance == null) {
            instance = new GftIdentifierGenerator();
        }
        return instance.getNext();
    }

    private synchronized String getNext() {
        StringBuilder next = new StringBuilder();
        for (int j = SIZE - 1; 0 <= j; j--) {
            next.append(chars[counter.get(j)]);
        }
        counter.increase();
        return next.toString();
    }
}
