package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Аня
public class FloydSteinberg implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public FloydSteinberg(){
        parameters.add(new Parameter("Quantization number red", 2, 128));
        parameters.add(new Parameter("Quantization number green", 2, 128));
        parameters.add(new Parameter("Quantization number blue", 2, 128));
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);

        int quantizationNumberRed = (int)parameters.get(0).getValue();
        int quantizationNumberGreen = (int)parameters.get(1).getValue();
        int quantizationNumberBlue = (int)parameters.get(2).getValue();

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int oldRGB = newImage.getRGB(x, y);
                int R = (oldRGB >> 16) & 0xff;
                int G = (oldRGB >> 8) & 0xff;
                int B = oldRGB & 0xff;

                int newR = getNearestColor(R, 255 / (quantizationNumberRed - 1));
                int newG = getNearestColor(G, 255 / (quantizationNumberGreen - 1));
                int newB = getNearestColor(B, 255 / (quantizationNumberBlue - 1));
                newImage.setRGB(x, y, (255 << 24) | (newR << 16) | (newG << 8) | newB);

                double eR = R - newR;
                double eG = G - newG;
                double eB = B - newB;

                if(x < width - 1){
                    int rgb = newImage.getRGB(x + 1, y);
                    double r = Math.max(0, Math.min(((rgb >> 16) & 0xff) + eR * 7 / 16, 255));
                    double g = Math.max(0, Math.min(((rgb >> 8) & 0xff) + eG * 7 / 16, 255));
                    double b = Math.max(0, Math.min((rgb & 0xff) + eB * 7 / 16, 255));
                    newImage.setRGB(x + 1, y, (255 << 24) | ((int)r << 16) | ((int)g << 8) | (int)b);
                }

                if(x > 0 && y < height - 1){
                    int rgb = newImage.getRGB(x - 1, y + 1);
                    double r = Math.max(0, Math.min(((rgb >> 16) & 0xff) + eR * 3 / 16, 255));
                    double g = Math.max(0, Math.min(((rgb >> 8) & 0xff) + eG  * 3 / 16, 255));
                    double b = Math.max(0, Math.min((rgb & 0xff) + eB * 3 / 16, 255));
                    newImage.setRGB(x - 1, y + 1, (255 << 24) | ((int)r << 16) | ((int)g << 8) | (int)b);
                }

                if(y < height - 1){
                    int rgb = newImage.getRGB(x, y + 1);
                    double r = Math.max(0, Math.min(((rgb >> 16) & 0xff) + eR * 5 / 16, 255));
                    double g = Math.max(0, Math.min(((rgb >> 8) & 0xff) + eG * 5 / 16, 255));
                    double b = Math.max(0, Math.min((rgb & 0xff) + eB * 5 / 16, 255));
                    newImage.setRGB(x, y + 1, (255 << 24) | ((int)r << 16) | ((int)g << 8) | (int)b);
                }

                if(x < width - 1 && y < height - 1){
                    int rgb = newImage.getRGB(x + 1, y + 1);
                    double r = Math.max(0, Math.min(((rgb >> 16) & 0xff) + eR / 16, 255));
                    double g = Math.max(0, Math.min(((rgb >> 8) & 0xff) + eG / 16, 255));
                    double b = Math.max(0, Math.min((rgb & 0xff) + eB / 16, 255));
                    newImage.setRGB(x + 1, y + 1, (255 << 24) | ((int)r << 16) | ((int)g << 8) | (int)b);
                }
            }
        }
        return newImage;
    }

    private int getNearestColor(double color, int quantizationNumber){
        color = (int)(Math.round(color / quantizationNumber) * quantizationNumber);
        return (int)Math.max(0, Math.min(color, 255));
    }


    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}

