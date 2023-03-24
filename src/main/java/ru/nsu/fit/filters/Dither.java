package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Аня
public class Dither implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private ArrayList<Integer> palette = new ArrayList<>();

    public Dither(){
        parameters.add(new Parameter("Quantization number", 2, 128));
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        for(int i = 0; i < 2; ++i){
            for(int j  = 0; j < 2; ++j){
                for(int k = 0 ; k < 2; ++k){
                    palette.add((255 << 24) | (i * 255 << 16) | (j * 255 << 8) | k * 255);
                }
            }
        }

        double[][] matrix = {{0, 32, 8, 40, 2, 34, 10, 42}, {48, 16, 56, 24, 50, 18, 58, 26},
                {12, 44, 4, 36, 14, 46, 6, 38}, {60, 28, 52, 20, 62, 30, 54, 22},
                {3, 35, 11, 43, 1, 33, 9, 41}, {51, 19, 59, 27, 49, 17, 57, 25},
                {15, 47, 7, 39, 13, 45, 5, 37}, {63, 31, 55, 23, 61, 29, 53, 21}};

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                int rgb = image.getRGB(x, y);
                int R = (rgb >> 16) & 0xff;
                int G = (rgb >> 8) & 0xff;
                int B =  rgb & 0xff;

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

                int newRGB = getNearestColor(newR, newG, newB);

                //Color newColor = new Color((int)newR, (int)newG, (int)newB);
                newImage.setRGB(x, y, newRGB);
            }
        }
        return newImage;
    }

    public double diff(double R1, double G1, double B1, double R2, double G2, double B2) {
        double rdiff = R1 - R2;
        double gdiff = G1 - G2;
        double bdiff = B1 - B2;
        return rdiff * rdiff + gdiff * gdiff + bdiff * bdiff;
    }

    private int getNearestColor(double R, double G, double B){
        int resultColor = palette.get(0);
        double minDiff = diff(R, G, B, (resultColor >> 16) & 0xff, (resultColor >> 8) & 0xff,
                resultColor & 0xff);

        for (int i = 1; i < palette.size(); ++i){
            double newDiff = diff(R, G, B, (palette.get(i) >> 16) & 0xff, (palette.get(i) >> 8) & 0xff,
                    palette.get(i) & 0xff);
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
