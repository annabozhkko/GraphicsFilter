package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class MedianFilter implements Filter{
    @Override
    public void execute(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                double[] R = new double[25];
                double[] G = new double[25];
                double[] B = new double[25];
                int k = 0;
                for(int i = x - 2; i <= x + 2; ++i){
                    for(int j = y - 2; j <= y + 2; ++j){
                        if(i >= 0 && i < image.getWidth() && j >= 0 && j < image.getHeight()){
                            RGB rgb = new RGB(image.getRGB(i, j));
                            R[k] = rgb.getR();
                            G[k] = rgb.getG();
                            B[k] = rgb.getB();
                            k++;
                        }
                    }
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);

                RGB newRGB = new RGB(R[12], G[12], B[12], 255);
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
