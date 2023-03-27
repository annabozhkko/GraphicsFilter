package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class Clarity implements Filter{
    @Override
    public BufferedImage execute(BufferedImage image) {
        //int [][]matrix = new int[][]{{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
        int [][]matrix = new int[][]{{0, 0, 0}, {0, 100, 0}, {0, 0, 0}};
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                /*
                double sumR = 0;
                double sumG = 0;
                double sumB = 0;
                for(int u = -1; u <= 1; ++u){
                    for(int v = -1; v <= 1; ++v){
                        int rgb;
                        if(x + u >= 0 && x + u < image.getWidth() && y + v >= 0 && y + v < image.getHeight()) {
                            rgb = image.getRGB(x + u, y + v);
                        }
                        else{
                            rgb = image.getRGB(x, y);
                        }
                        sumR += matrix[u + 1][v + 1] * ((rgb >> 16) & 0xff);
                        sumG += matrix[u + 1][v + 1] * ((rgb >> 8) & 0xff);
                        sumB += matrix[u + 1][v + 1] * (rgb & 0xff);
                    }
                }

                sumR = Math.max(0, Math.min(sumR, 255));
                sumG = Math.max(0, Math.min(sumG, 255));
                sumB = Math.max(0, Math.min(sumB, 255));
                 */
                int rgb = image.getRGB(x, y);
                int r = ((rgb >> 16) & 0xff) - matrix[x % 3][y % 3];
                int g = ((rgb >> 8) & 0xff) - matrix[x % 3][y % 3];
                int b = (rgb & 0xff) - matrix[x % 3][y % 3];

                r = Math.max(r, 0);
                g = Math.max(g, 0);
                b = Math.max(b, 0);

                newImage.setRGB(x, y, (255 << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
