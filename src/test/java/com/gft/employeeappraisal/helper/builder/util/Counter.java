package com.gft.employeeappraisal.helper.builder.util;

public class Counter {

    private final int base;
    private volatile int counter;

    public Counter(int size, int base) {
        this.counter = 0;
        this.base = base;
    }

    public void increase() {
        this.counter++;
    }

    public int get(int index) {
        int pow2index = (int) Math.pow((double) base, (double) index);
        int pow2indexPlus1 = (int) Math.pow((double) base, (double) index + 1);
        return (this.counter % pow2indexPlus1) / pow2index;
    }
}
