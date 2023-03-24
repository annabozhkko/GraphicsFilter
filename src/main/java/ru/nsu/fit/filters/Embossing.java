package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Embossing implements Filter{
     @Override
    public BufferedImage execute(BufferedImage image){
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
                            int rgb = image.getRGB(x + u, y + v);
                            sumR += matrix[u + 1][v + 1] * ((rgb >> 16) & 0xff);
                            sumG += matrix[u + 1][v + 1] * ((rgb >> 8) & 0xff);
                            sumB += matrix[u + 1][v + 1] * (rgb & 0xff);
                        }
                    }
                }
                sumR += 128;
                sumG += 128;
                sumB += 128;
                newImage.setRGB(x, y, (255 << 24) | ((int)sumR << 16) | ((int)sumG << 8) | (int)sumB);
            }
        }

        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
