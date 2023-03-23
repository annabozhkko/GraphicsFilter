package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HighlightBorder implements  Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public HighlightBorder(){
        parameters.add(new Parameter("binarization parameter", 1, 255));
    }
    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage highlightBorderImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int binarizationParameter = (int) parameters.get(0).getValue();
        int[] robertsX = {1, 0, 0, -1};
        int[] robertsY = {0, 1, -1, 0};
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                int gxRed = 0, gxGreen = 0, gxBlue = 0;
                int gyRed = 0, gyGreen = 0, gyBlue = 0;
                int alphaComponent = 0;
                int redComponent = 0;
                int greenComponent = 0;
                int blueComponent = 0;
                for (int k = 0; k < 2; k++) {
                    for (int m = 0; m < 2; m++) {
                        int color = img.getRGB(i + k, j + m);
                        alphaComponent = (color >> 24) & 0xff;
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
                redComponent = (Math.abs(gxRed) + Math.abs(gyRed) > binarizationParameter) ? redComponent : 0;
                greenComponent = (Math.abs(gxGreen) + Math.abs(gyGreen) > binarizationParameter) ? greenComponent : 0;
                blueComponent = (Math.abs(gxBlue) + Math.abs(gyBlue) > binarizationParameter) ? blueComponent : 0;

                int newColor = (alphaComponent << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                highlightBorderImg.setRGB(i, j, newColor);
            }
        }
        return highlightBorderImg;
    }

    public void executeSobel(BufferedImage img) {
        BufferedImage highlightBorderImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int binarizationParameter = (int) parameters.get(0).getValue();

        int[] robertsX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
        int[] robertsY = {1, 2, 1, 0, 0, 0, -1, -2, -1};
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight() ; j++) {
                int gxRed = 0, gxGreen = 0, gxBlue = 0;
                int gyRed = 0, gyGreen = 0, gyBlue = 0;
                int alphaComponent = 0;
                int redComponent = 0;
                int greenComponent = 0;
                int blueComponent = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int m = -1; m <= 1; m++) {
                        /*if(i + k > img.getWidth() || i + k < img.getWidth() || j + m > img.getHeight() || j + m < img.getHeight()){
                            System.out.println("continue");
                            System.out.println(i +" | "+ k + " | " + j+ " | " + m);
                            continue;
                        }
                        System.out.println("oooo");*/
                        //if(y = j + m < 0)

                        int color = img.getRGB(i + k, j + m);
                        alphaComponent = (color >> 24) & 0xff;
                        redComponent = (color >> 16) & 0xff;
                        greenComponent = (color >> 8) & 0xff;
                        blueComponent = color & 0xff;
                        gxRed += redComponent * robertsX[k * 3 + m];
                        gyRed += redComponent * robertsY[k * 3 + m];
                        gxGreen += greenComponent * robertsX[k * 3 + m];
                        gyGreen += greenComponent * robertsY[k * 3 + m];
                        gxBlue += blueComponent * robertsX[k * 3 + m];
                        gyBlue += blueComponent * robertsY[k * 3 + m];
                    }
                }
                redComponent = (Math.abs(gxRed) + Math.abs(gyRed) > binarizationParameter) ? redComponent : 0;
                greenComponent = (Math.abs(gxGreen) + Math.abs(gyGreen) > binarizationParameter) ? greenComponent : 0;
                blueComponent = (Math.abs(gxBlue) + Math.abs(gyBlue) > binarizationParameter) ? blueComponent : 0;
                /*if(redComponent > 255 || greenComponent > 255 || blueComponent > 255)
                    System.out.println(redComponent + " " + greenComponent + " " + blueComponent);*/

                int newColor = (alphaComponent << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                highlightBorderImg.setRGB(i, j, newColor);
            }
        }
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
