package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class Negative implements Filter{
    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage negativeImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int color = img.getRGB(i, j);
                int alphaComponent = (color >> 24) & 0xff;
                int redComponent = 255 - (color >> 16) & 0xff;
                int greenComponent = 255 - (color >> 8) & 0xff;
                int blueComponent = 255 - color & 0xff;
                int newColor = (alphaComponent << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                negativeImg.setRGB(i, j, newColor);
            }
        }
        return negativeImg;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }

}
