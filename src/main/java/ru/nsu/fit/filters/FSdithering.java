package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FSdithering implements Filter {
    private List<Parameter> parameters = new ArrayList<>();
    private ArrayList<Integer> palette = new ArrayList<>();

    public FSdithering() {
        parameters.add(new Parameter("quantization level Red", 2, 128));
        parameters.add(new Parameter("quantization level Green", 2, 128));
        parameters.add(new Parameter("quantization level Blue", 2, 128));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage ditheringImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = ditheringImg.createGraphics();
        g2.drawImage(img,0 ,0,null);

        int qLevelRed = (int) parameters.get(0).getValue();
        int qLevelGreen = (int) parameters.get(1).getValue();
        int qLevelBlue = (int) parameters.get(2).getValue();

        int spreadSpaceRed = getSpreadSpace(qLevelRed);
        int spreadSpaceGreen = getSpreadSpace(qLevelGreen);
        int spreadSpaceBlue = getSpreadSpace(qLevelBlue);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int color = ditheringImg.getRGB(i, j);
                int alphaComponent = (color >> 24) & 0xff;
                int redComponent = (color >> 16) & 0xff;
                int greenComponent = (color >> 8) & 0xff;
                int blueComponent = color & 0xff;
                // находим блиайший цвет в палитре
                int newRedComponent = getNearestColor(redComponent, spreadSpaceRed);
                int newGreenComponent = getNearestColor(greenComponent, spreadSpaceGreen);
                int newBlueComponent = getNearestColor(blueComponent, spreadSpaceBlue);
                // устанавливаем новый цвет
                int newColor = (alphaComponent << 24) | (newRedComponent << 16) | (newGreenComponent << 8) | newBlueComponent;
                ditheringImg.setRGB(i, j, newColor);
                // считаем ошибку
                double errorRed = (double) redComponent - newRedComponent;
                double errorGreen = (double) greenComponent - newGreenComponent;
                double errorBlue = (double) blueComponent - newBlueComponent;

                // распостраняем ошибку
                if (i + 1 < img.getWidth()) {
                    int errorPropColor = ditheringImg.getRGB(i + 1, j);
                    int newErrorPropColor = getErrorPropagationColor(errorPropColor, errorRed, errorGreen, errorBlue, 7);
                    ditheringImg.setRGB(i + 1, j, newErrorPropColor);
                }
                if (j + 1 < img.getHeight()) {
                    int errorPropColor = ditheringImg.getRGB(i, j + 1);
                    int newErrorPropColor = getErrorPropagationColor(errorPropColor, errorRed, errorGreen, errorBlue, 5);
                    ditheringImg.setRGB(i, j + 1, newErrorPropColor);
                }
                if (j + 1 < img.getHeight() && i + 1 < img.getWidth()) {
                    int errorPropColor = ditheringImg.getRGB(i + 1, j + 1);
                    int newErrorPropColor = getErrorPropagationColor(errorPropColor, errorRed, errorGreen, errorBlue, 1);
                    ditheringImg.setRGB(i + 1, j + 1, newErrorPropColor);
                }
                if (j + 1 < img.getHeight() && i - 1 >= 0) {
                    int errorPropColor = ditheringImg.getRGB(i - 1, j + 1);
                    int newErrorPropColor = getErrorPropagationColor(errorPropColor, errorRed, errorGreen, errorBlue, 3);
                    ditheringImg.setRGB(i - 1, j + 1, newErrorPropColor);
                }

            }
        }
        return ditheringImg;
    }

    private int getErrorPropagationColor(int color, double errorRed, double errorGreen, double errorBlue, int quantizationError) {
        int alphaErrorProp = (color >> 24) & 0xff;
        int errorPropRed = getErrorPropagationColorComponent((color >> 16) & 0xff, errorRed, quantizationError);
        int errorPropGreen = getErrorPropagationColorComponent((color >> 8) & 0xff, errorGreen, quantizationError);
        int errorPropBlue = getErrorPropagationColorComponent((color & 0xff), errorBlue, quantizationError);
        return (alphaErrorProp << 24) | (errorPropRed << 16) | (errorPropGreen << 8) | errorPropBlue;
    }

    private int getErrorPropagationColorComponent(int color, double errorColor, int quantizationError) {
        return normalization((int) (color + errorColor * quantizationError / 16));
    }

    private int normalization(int value) {
        if (value > 255) {
            return 255;
        }
        return Math.max(value, 0);
    }

    private int getNearestColor(int color, int spreadSpace) {
        return normalization((int) (Math.round((double) color / spreadSpace) * spreadSpace));
    }

    private int getSpreadSpace(int qLevel) {
        return 256 / (qLevel - 1);
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
