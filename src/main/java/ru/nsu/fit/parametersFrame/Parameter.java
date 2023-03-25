package ru.nsu.fit.parametersFrame;

public class Parameter {
    private double value;
    private String name;
    private double min, max;
    private double step = 1;

    public Parameter(String name, double initValue, double endValue){
        this.name = name;
        value = initValue;
        min = initValue;
        max = endValue;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
