package ru.nsu.fit.bozhko.tools;

import java.awt.*;

public class RGB {
    private double r, g, b, a;

    public RGB(Color color){
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        a = color.getAlpha();
    }

    public RGB(double r, double g, double b, double a){
        this.r = Math.max(0, Math.min(r, 255));
        this.g = Math.max(0, Math.min(g, 255));
        this.b = Math.max(0, Math.min(b, 255));;
        this.a = a;
    }

    public RGB(int rgb){
        a =  (rgb >> 24) & 0xff;
        r = (rgb >> 16) & 0xff;
        g = (rgb >> 8) & 0xff;
        b = rgb & 0xff;
    }

    public double getB() {
        return b;
    }

    public double getG() {
        return g;
    }

    public double getR() {
        return r;
    }

    public double getA() {
        return a;
    }

    public Color toColor() {
        return new Color((int) r, (int) g, (int) b);
    }

    public int toInt(){
        return ((int)a << 24) | ((int)r << 16) | ((int)g << 8) | (int)b;
    }
}
