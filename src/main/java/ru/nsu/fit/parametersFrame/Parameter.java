package ru.nsu.fit.parametersFrame;

public class Parameter {
    private double value;
    private String name;
    private double min, max;

    public Parameter(String name, double initValue, double endValue){
        this.name = name;
        value = initValue;
        min = initValue;
        max = endValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
