package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Аня
public class Dither implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private ArrayList<RGB> palette = new ArrayList<>();

    @Override
    public void execute(BufferedImage image) {
        for(int i = 0; i < 2; ++i){
            for(int j  = 0; j < 2; ++j){
                for(int k = 0 ; k < 2; ++k){
                    palette.add(new RGB(i * 255, j * 255, k * 255, 255));
                }
            }
        }

        double[][] matrix = {{0, 32, 8, 40, 2, 34, 10, 42}, {48, 16, 56, 24, 50, 18, 58, 26},
                {12, 44, 4, 36, 14, 46, 6, 38}, {60, 28, 52, 20, 62, 30, 54, 22},
                {3, 35, 11, 43, 1, 33, 9, 41}, {51, 19, 59, 27, 49, 17, 57, 25},
                {15, 47, 7, 39, 13, 45, 5, 37}, {63, 31, 55, 23, 61, 29, 53, 21}};


        for(int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                Color color = new Color(image.getRGB(x, y));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();

                int i = x % 8;
                int j = y % 8;
/*
                int newR = (R > matrix[i][j]) ? 255 : 0;
                int newG = (G > matrix[i][j]) ? 255 : 0;
                int newB = (B > matrix[i][j]) ? 255 : 0;

 */
                double r = 1;

                double newR = R + r * (matrix[i][j] - 1./2); // >= 128 ? 255 : 0;
                double newG = G + r * (matrix[i][j] - 1./2); // >= 128 ? 255 : 0;
                double newB = B + r * (matrix[i][j] - 1./2); // >= 128 ? 255 : 0;

                RGB rgb = new RGB(newR, newG, newB, 255);
                RGB newRGB = getNearestColor(rgb);

                //Color newColor = new Color((int)newR, (int)newG, (int)newB);
                image.setRGB(x, y, newRGB.toInt());
            }
        }
    }

    public double diff(RGB color1, RGB color2) {
        double rdiff = color1.getR() - color2.getR();
        double gdiff = color1.getG() - color2.getG();
        double bdiff = color1.getB() - color2.getB();
        return rdiff * rdiff + gdiff * gdiff + bdiff * bdiff;
    }

    private RGB getNearestColor(RGB color){
        RGB resultColor = palette.get(0);
        double minDiff = diff(color, palette.get(0));

        for (int i = 1; i < palette.size(); ++i){
            double newDiff = diff(color, palette.get(i));
            if(minDiff > newDiff){
                minDiff = newDiff;
                resultColor = palette.get(i);
            }
        }

        return resultColor;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
