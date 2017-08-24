package com.gft.employeeappraisal.builder.helper;

/**
 * Generates unique GFT identifiers for testing
 */
public class GftIdentifierGenerator {

    private static final char chars[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '0',
            '-', '+', '=', '(', ')', '?', '<', '>', '*', '$'};
    private static final int SIZE = 4;

    private static GftIdentifierGenerator instance;

    private Counter counter = new Counter(SIZE, chars.length);

    private GftIdentifierGenerator() {
    }

    public static String next() {
        if (instance == null) {
            instance = new GftIdentifierGenerator();
        }
        return instance.getNext();
    }

    private synchronized String getNext() {
        String next = "";
        for (int j = SIZE - 1; 0 <= j; j--) {
            next += chars[counter.get(j)];
        }
        counter.increase();
        return next;
    }
}