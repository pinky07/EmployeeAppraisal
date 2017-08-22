package com.gft.employeeappraisal.builder.helper;

import java.util.Arrays;

public class Counter {

    private int counter[];
    private int base;

    public Counter(int size, int base) {
        this.counter = new int[size];
        this.base = base;
    }

    public void increase() {
        for (int i = counter.length - 1; 0 <= i; i--) {
            if (counter[i] + 1 < base) {
                counter[i]++;
                i = -1;
            }
        }
    }

    public int get(int index) {
        return this.counter[index];
    }

    public int[] get() {
        return Arrays.copyOf(this.counter, this.counter.length);
    }
}
