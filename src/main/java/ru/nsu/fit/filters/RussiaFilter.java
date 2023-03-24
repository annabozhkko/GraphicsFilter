package ru.nsu.fit.filters;

import java.awt.image.BufferedImage;
import ru.nsu.fit.parametersFrame.*;
import java.util.List;

public class RussiaFilter implements Filter{
    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        BufferedImage newImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);
                float r = (float)((rgb & 0x00FF0000) >> 16); // красный
                float g = (float)((rgb & 0x0000FF00) >> 8); // зеленый
                float b = (float)(rgb & 0x000000FF); // синий
                r = g = b = (int) (0.900 * r + 0.50 * g + 0.50 * b);
                int newPixel = 0xFF000000 | ((int)r << 16) | ((int)g << 8) | ((int)b);
                newImage.setRGB(x, y, newPixel);
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
