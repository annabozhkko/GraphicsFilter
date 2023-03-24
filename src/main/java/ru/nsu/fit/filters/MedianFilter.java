package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class MedianFilter implements Filter{
    @Override
    public BufferedImage execute(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int count = 0; count < 10; ++count) {
            for (int x = 0; x < image.getWidth(); ++x) {
                for (int y = 0; y < image.getHeight(); ++y) {
                    double[] R = new double[25];
                    double[] G = new double[25];
                    double[] B = new double[25];
                    int k = 0;
                    for (int i = x - 2; i <= x + 2; ++i) {
                        for (int j = y - 2; j <= y + 2; ++j) {
                            if (i >= 0 && i < image.getWidth() && j >= 0 && j < image.getHeight()) {
                                int rgb = image.getRGB(i, j);
                                R[k] = (rgb >> 16) & 0xff;
                                G[k] = (rgb >> 8) & 0xff;
                                B[k] = rgb & 0xff;
                                k++;
                            }
                        }
                    }
                    Arrays.sort(R);
                    Arrays.sort(G);
                    Arrays.sort(B);

                    newImage.setRGB(x, y, (255 << 24) | ((int) R[12] << 16) | ((int) G[12] << 8) | (int) B[12]);
                }
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
