package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class MedianFilter implements Filter{
    private int sizeMatrix;

    public MedianFilter(int sizeMatrix){
        this.sizeMatrix = sizeMatrix;
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        int r = (sizeMatrix - 1) / 2;
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int[] R = new int[sizeMatrix * sizeMatrix];
        int[] G = new int[sizeMatrix * sizeMatrix];
        int[] B = new int[sizeMatrix * sizeMatrix];

        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                int k = 0;
                for (int i = x - r; i <= x + r; ++i) {
                    for (int j = y - r; j <= y + r; ++j) {
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

                newImage.setRGB(x, y, (255 << 24) | (R[(sizeMatrix * sizeMatrix - 1) / 2] << 16) |
                        (G[(sizeMatrix * sizeMatrix - 1) / 2] << 8) | B[(sizeMatrix * sizeMatrix - 1) / 2]);
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
