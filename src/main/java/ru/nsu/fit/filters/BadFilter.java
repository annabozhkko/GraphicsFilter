package ru.nsu.fit.filters;

import java.awt.image.BufferedImage;
import ru.nsu.fit.parametersFrame.*;
import java.util.List;

public class BadFilter implements Filter{
    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        BufferedImage result = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);
                float R = (float)((rgb & 0x00FF0000) >> 16); // красный
                float G = (float)((rgb & 0x0000FF00) >> 8); // зеленый
                float B = (float)(rgb & 0x000000FF); // синий
                /*var newR = (int) (0.393 * R + 0.769 * G + 0.189 * B);
                newR = (newR>255)? 255:Math.max(newR, 0);
                var newG = (int) (0.349 * R + 0.686 * G + 0.168 * B);
                newG = (newG>255)? 255:Math.max(newG, 0);
                var newB = (int) (0.272 * R + 0.534 * G + 0.131 * B);
                newB = (newB>255)? 255:Math.max(newB, 0);*/
                int newPixel = 0xFF000000 | ((int)(R*0.937) << 16) | ((int)(G*0.784) << 8) | ((int)(B*0.772));
                result.setRGB(x, y,newPixel);
            }
        }
        return result;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
