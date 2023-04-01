package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class MedianFilter implements Filter{
    private int sizeMatrix, r;
    private int width, height;
    private BufferedImage image, newImage;

    public MedianFilter(int sizeMatrix){
        this.sizeMatrix = sizeMatrix;
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        r = (sizeMatrix - 1) / 2;
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Thread thread1 = new Thread(() -> {
            parallelFilter(0, (width % 2 == 0) ? width / 2 : width / 2 + 1);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            parallelFilter((width % 2 == 0) ? width / 2 : width / 2 + 1, width);
        });
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return newImage;
    }

    private void parallelFilter(int widthStart, int widthEnd){
        int[] R = new int[sizeMatrix * sizeMatrix];
        int[] G = new int[sizeMatrix * sizeMatrix];
        int[] B = new int[sizeMatrix * sizeMatrix];

        for (int x = widthStart; x < widthEnd; ++x) {
            for (int y = 0; y < height; ++y) {
                int k = 0;
                for (int i = x - r; i <= x + r; ++i) {
                    for (int j = y - r; j <= y + r; ++j) {
                        if (i >= 0 && i < width && j >= 0 && j < height) {
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
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
