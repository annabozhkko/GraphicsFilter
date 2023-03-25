package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Аня
public class Dither implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private ArrayList<Integer> palette = new ArrayList<>();
    private int quantizationNumberRed, quantizationNumberGreen, quantizationNumberBlue;

    private double[][][] matrices = {{
        {0, 2}, {3, 1}},

        {{0, 8, 2, 10},
        {12, 4, 14, 6},
        {3, 11, 1, 9},
        {15, 7, 13, 5}},

        {{0, 32, 8, 40, 2, 34, 10, 42},
        {48, 16, 56, 24, 50, 18, 58, 26},
        {12, 44, 4, 36, 14, 46, 6, 38},
        {60, 28, 52, 20, 62, 30, 54, 22},
        {3, 35, 11, 43, 1, 33, 9, 41},
        {51, 19, 59, 27, 49, 17, 57, 25},
        {15, 47, 7, 39, 13, 45, 5, 37},
        {63, 31, 55, 23, 61, 29, 53, 21}},

        {{0, 192, 48, 240, 12, 204, 60, 252, 3, 195, 51, 243, 15, 207, 63, 255},
        {128, 64, 176, 112, 140, 76, 188, 124, 131, 67, 179, 115, 143, 79, 191, 127},
        {32, 224, 16, 208, 44, 236, 28, 220, 35, 227, 19, 211, 47, 239, 31, 223},
        {160, 96, 144, 80, 172, 108, 156, 92, 163, 99, 147, 83, 175, 111, 159, 95},
        {8, 200, 56, 248, 4, 196, 52, 244, 11, 203, 59, 251, 7, 199, 55, 247},
        {136, 72, 184, 120, 132, 68, 180, 116, 139, 75, 187, 123, 135, 71, 183, 119},
        {40, 232, 24, 216, 36, 228, 20, 212, 43, 235, 27, 219, 39, 231, 23, 215},
        {168, 104, 152, 88, 164, 100, 148, 84, 171, 107, 155, 91, 167, 103, 151, 87},
        {2, 194, 50, 242, 14, 206, 62, 254, 1, 193, 49, 241, 13, 205, 61, 253},
        {130, 66, 178, 114, 142, 78, 190, 126, 129, 65, 177, 113, 141, 77, 189, 125},
        {34, 226, 18, 210, 46, 238, 30, 222, 33, 225, 17, 209, 45, 237, 29, 221},
        {162, 98, 146, 82, 174, 110, 158, 94, 161, 97, 145, 81, 173, 109, 157, 93},
        {10, 202, 58, 250, 6, 198, 54, 246, 9, 201, 57, 249, 5, 197, 53, 245},
        {138, 74, 186, 122, 134, 70, 182, 118, 137, 73, 185, 121, 133, 69, 181, 117},
        {42, 234, 26, 218, 38, 230, 22, 214, 41, 233, 25, 217, 37, 229, 21, 213},
        {170, 106, 154, 90, 166, 102, 150, 86, 169, 105, 153, 89, 165, 101, 149, 85}}};

    public Dither(){
        parameters.add(new Parameter("Quantization number red", 2, 128));
        parameters.add(new Parameter("Quantization number green", 2, 128));
        parameters.add(new Parameter("Quantization number blue", 2, 128));
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        quantizationNumberRed = (int)parameters.get(0).getValue();
        quantizationNumberGreen = (int)parameters.get(1).getValue();
        quantizationNumberBlue = (int)parameters.get(2).getValue();

        createPalette();

        double[][] matrixRed = getMatrix(quantizationNumberRed);
        double[][] matrixGreen = getMatrix(quantizationNumberGreen);
        double[][] matrixBlue = getMatrix(quantizationNumberBlue);

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                int rgb = image.getRGB(x, y);
                int R = (rgb >> 16) & 0xff;
                int G = (rgb >> 8) & 0xff;
                int B =  rgb & 0xff;

                double newR = R + (255. / (quantizationNumberRed - 1)) * (matrixRed[x % quantizationNumberRed][y % quantizationNumberRed] / 255 - 1./2);
                double newG = G + (255. / (quantizationNumberGreen - 1)) * (matrixGreen[x % quantizationNumberGreen][y % quantizationNumberGreen] / 255 - 1./2);
                double newB = B + (255. / (quantizationNumberBlue - 1)) * (matrixBlue[x % quantizationNumberBlue][y % quantizationNumberBlue] / 255 - 1./2);

                int newRGB = getNearestColor(newR, newG, newB);

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

    private void createPalette(){
        palette.clear();
        int[] valuesRed = new int[quantizationNumberRed];
        int step = 255 / (quantizationNumberRed - 1);
        for(int i = 0; i < quantizationNumberRed; ++i){
            valuesRed[i] = i * step;
        }

        int[] valuesGreen = new int[quantizationNumberGreen];
        step = 255 / (quantizationNumberGreen - 1);
        for(int i = 0; i < quantizationNumberGreen; ++i){
            valuesGreen[i] = i * step;
        }

        int[] valuesBlue = new int[quantizationNumberBlue];
        step = 255 / (quantizationNumberBlue - 1);
        for(int i = 0; i < quantizationNumberBlue; ++i){
            valuesBlue[i] = i * step;
        }

        for(int i : valuesRed){
            for(int j : valuesGreen){
                for(int k : valuesBlue){
                    palette.add((255 << 24) | (i << 16) | (j << 8) | k );
                }
            }
        }
    }

    private double[][] getMatrix(int quantizationNumber){
        for(int i = 1; i <= 4; ++i){
            int sizeMatrix = (int)Math.pow(2, i);
            if(sizeMatrix * sizeMatrix >= 256 / (quantizationNumber - 1)){  //36
                return matrices[i - 1];
            }
        }
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
