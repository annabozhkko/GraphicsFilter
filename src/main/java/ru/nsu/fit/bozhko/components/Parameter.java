package ru.nsu.fit.bozhko.components;

public class Parameter {
    private int value;
    private String name;
    private int min, max;

    public Parameter(String name, int initValue, int endValue){
        this.name = name;
        value = initValue;
        min = initValue;
        max = endValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
