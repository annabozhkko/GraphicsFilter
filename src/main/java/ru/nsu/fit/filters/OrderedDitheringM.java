package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OrderedDitheringM implements Filter {
    private List<Parameter> parameters = new ArrayList<>();

    private int[] matrix_2 = {0, 2,
                              3, 1};

    private int[] matrix_4 = {0, 8, 2, 10,
                              12, 4, 14, 6,
                              3, 11, 1, 9,
                              15, 7, 13, 5};

    private int[] matrix_8 = {0, 32, 8, 40, 2, 34, 10, 42,
                              48, 16, 56, 24, 50, 18, 58, 26,
                              12, 44, 4, 36, 14, 46, 6, 38,
                              60, 28, 52, 20, 62, 30, 54, 22,
                              3, 35, 11, 43, 1, 33, 9, 41,
                              51, 19, 59, 27, 49, 17, 57, 25,
                              15, 47, 7, 39, 13, 45, 5, 37,
                              63, 31, 55, 23, 61, 29, 53, 21};

    private int[] matrix_16 = {0, 192, 48, 240, 12, 204, 60, 252, 3, 195, 51, 243, 15, 207, 63, 255,
                              128, 64, 176, 112, 140, 76, 188, 124, 131, 67, 179, 115, 143, 79, 191, 127,
                              32, 224, 16, 208, 44, 236, 28, 220, 35, 227, 19, 211, 47, 239, 31, 223,
                              160, 96, 144, 80, 172, 108, 156, 92, 163, 99, 147, 83, 175, 111, 159, 9,
                              8, 200, 56, 248, 4, 196, 52, 244, 11, 203, 59, 251, 7, 199, 55, 247,
                              136, 72, 184, 120, 132, 68, 180, 116, 139, 75, 187, 123, 135, 71, 183, 119,
                              40, 232, 24, 216, 36, 228, 20, 212, 43, 235, 27, 219, 39, 231, 23, 215,
                              168, 104, 152, 88, 164, 100, 148, 84, 171, 107, 155, 91, 167, 103, 151, 87,
                              2, 194, 50, 242, 14, 206, 62, 254, 1, 193, 49, 241, 13, 205, 61, 253,
                              130, 66, 178, 114, 142, 78, 190, 126, 129, 65, 177, 113, 141, 77, 189, 125,
                              34, 226, 18, 210, 46, 238, 30, 222, 33, 225, 17, 209, 45, 237, 29, 221,
                              162, 98, 146, 82, 174, 110, 158, 94, 161, 97, 145, 81, 173, 109, 157, 93,
                              10, 202, 58, 250, 6, 198, 54, 246, 9, 201, 57, 249, 5, 197, 53, 245,
                              138, 74, 186, 122, 134, 70, 182, 118, 137, 73, 185, 121, 133, 69, 181, 117,
                              42, 234, 26, 218, 38, 230, 22, 214, 41, 233, 25, 217, 37, 229, 21, 213,
                              170, 106, 154, 90, 166, 102, 150, 86, 169, 105, 153, 89, 165, 101, 149, 85};


    public OrderedDitheringM() {
        parameters.add(new Parameter("quantization level Red", 2, 256));
        parameters.add(new Parameter("quantization level Green", 2, 256));
        parameters.add(new Parameter("quantization level Blue", 2, 256));
    }

    private int[] thresholdMap(int threshold){
        if(2 >= threshold)
            return matrix_2;
        else if(4 >= threshold)
            return matrix_4;
        else if(8 >= threshold)
            return matrix_8;
        else
            return matrix_16;
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage ditherImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int qLevelRed = (int) parameters.get(0).getValue();
        int qLevelGreen = (int) parameters.get(1).getValue();
        int qLevelBlue = (int) parameters.get(2).getValue();
        //число квантования это число оттенков одного цвета
        //от 2 до 256
        //для каждого цвета R G B нужно сделать свою палитру
        //мы будем искать в этой палитре ближайший цвет от полученного
        //....
        //выбираем матрицу для каждого цвета
        int[]  thresholdMapRed = thresholdMap(256 / qLevelRed);
        int[]  thresholdMapGreen = thresholdMap(256 / qLevelGreen);
        int[]  thresholdMapBlue = thresholdMap(256 / qLevelBlue);
        for (int i = 0; i < img.getWidth(); i++){
            for (int j = 0; j < img.getHeight(); j++) {
                int color = img.getRGB(i, j);
                int alphaComponent = (color >> 24) & 0xff;
                int redComponent = (color >> 16) & 0xff;
                int greenComponent = (color >> 8) & 0xff;
                int blueComponent = color & 0xff;

                redComponent = (int)(redComponent + qLevelRed * (thresholdMapRed[i%thresholdMapRed.length * thresholdMapRed.length + j % thresholdMapRed.length] - 0.5));
                greenComponent = (int)(greenComponent + qLevelGreen * (thresholdMapGreen[i%thresholdMapGreen.length * thresholdMapGreen.length + j % thresholdMapGreen.length] - 0.5));
                blueComponent = (int)(blueComponent + qLevelBlue * (thresholdMapBlue[i%thresholdMapBlue.length * thresholdMapBlue.length + j % thresholdMapBlue.length] - 0.5));

                int newColor = (alphaComponent << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                ditherImg.setRGB(i, j, newColor);
            }
        }
        return ditherImg;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

}