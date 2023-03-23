package ru.nsu.fit.filters;

import java.awt.image.BufferedImage;
import ru.nsu.fit.parametersFrame.*;
import java.util.List;

public class SharpeningFilter implements Filter{
    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final double[][] filter = {
                {0, -1 ,0},
                {-1, 5, -1},
                {0, -1 , 0}
        };

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++){
                double rSum = 0;
                double gSum = 0;
                double bSum = 0;
                for (int dX = 0; dX < filter.length; dX++) {
                    for (int dY = 0; dY < filter.length; dY++) {
                        int X = x + dX - filter.length / 2;
                        int Y = y + dY - filter.length / 2;
                        if ((X >= 0 && X < originalImage.getWidth()) && (Y >= 0 && Y < originalImage.getHeight())){
                            int rgb = originalImage.getRGB(X, Y);
                            float r = (float)((rgb & 0x00FF0000) >> 16); // красный
                            float g = (float)((rgb & 0x0000FF00) >> 8); // зеленый
                            float b = (float)(rgb & 0x000000FF); // синий
                            rSum += r * filter[dX][dY];
                            gSum += g * filter[dX][dY];
                            bSum += b * filter[dX][dY];
                        }
                    }
                }
                rSum = (rSum>255)? 255: Math.max(rSum, 0);
                gSum = (gSum>255)? 255: Math.max(gSum, 0);
                bSum = (bSum>255)? 255: Math.max(bSum, 0);
                var newRGB = 0xFF000000 | ((int)rSum << 16) | ((int)gSum << 8) | ((int)bSum);
                newImage.setRGB(x,y, newRGB);
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
