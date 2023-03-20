package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Embossing implements Filter{
    @Override
    public void execute(BufferedImage image){
        int [][]matrix = new int[][]{{0, 1, 0}, {-1, 0, 1}, {0, -1, 0}};

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                double sumR = 0;
                double sumG = 0;
                double sumB = 0;
                for(int u = -1; u <= 1; ++u){
                    for(int v = -1; v <= 1; ++v){
                        if(x + u >= 0 && x + u < image.getWidth() && y + v >= 0 && y + v < image.getHeight()) {
                            RGB rgb = new RGB(image.getRGB(x + u, y + v));
                            sumR += matrix[u + 1][v + 1] * rgb.getR();
                            sumG += matrix[u + 1][v + 1] * rgb.getG();
                            sumB += matrix[u + 1][v + 1] * rgb.getB();
                        }
                    }
                }
                sumR += 128;
                sumG += 128;
                sumB += 128;
                RGB newRGB = new RGB(sumR, sumG, sumB, 255);
                newImage.setRGB(x, y, newRGB.toInt());
            }
        }

        Graphics2D g = image.createGraphics();
        g.drawImage(newImage, 0, 0,  image.getWidth(), image.getHeight(),null);

    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
