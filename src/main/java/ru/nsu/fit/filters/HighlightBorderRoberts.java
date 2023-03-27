package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HighlightBorderRoberts implements  Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public HighlightBorderRoberts(){
        parameters.add(new Parameter("binarization parameter", 50, 1020));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage highlightBorderImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int binarizationParameter = (int) parameters.get(0).getValue();
        int[] robertsX = {1, 0, 0, -1};
        int[] robertsY = {0, 1, -1, 0};
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int gxRed = 0, gxGreen = 0, gxBlue = 0;
                int gyRed = 0, gyGreen = 0, gyBlue = 0;
                int redComponent = 0;
                int greenComponent = 0;
                int blueComponent = 0;
                for (int k = 0; k < 2; k++) {
                    for (int m = 0; m < 2; m++) {
                        int x = i + k - 1;
                        int y = j + m - 1;
                        int color;
                        if ((x >= 0 && x < img.getWidth()) && (y >= 0 && y < img.getHeight()))
                            color = img.getRGB(x, y);
                        else
                            color = img.getRGB(i,j);
                        redComponent = (color >> 16) & 0xff;
                        greenComponent = (color >> 8) & 0xff;
                        blueComponent = color & 0xff;
                        gxRed += redComponent * robertsX[k * 2 + m];
                        gyRed += redComponent * robertsY[k * 2 + m];
                        gxGreen += greenComponent * robertsX[k * 2 + m];
                        gyGreen += greenComponent * robertsY[k * 2 + m];
                        gxBlue += blueComponent * robertsX[k * 2 + m];
                        gyBlue += blueComponent * robertsY[k * 2 + m];
                    }
                }
                redComponent = (Math.abs(gxRed) + Math.abs(gyRed) > binarizationParameter) ? 255 : 0;
                greenComponent = (Math.abs(gxGreen) + Math.abs(gyGreen) > binarizationParameter) ? 255 : 0;
                blueComponent = (Math.abs(gxBlue) + Math.abs(gyBlue) > binarizationParameter) ? 255 : 0;

                int newColor = (255 << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                highlightBorderImg.setRGB(i, j, newColor);
            }
        }
        return highlightBorderImg;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
