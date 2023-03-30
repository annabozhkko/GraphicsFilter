package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VignetteFilter implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public VignetteFilter(){
        parameters.add(new Parameter("Vignette size", 1, 10));
    }

    @Override
    public BufferedImage execute(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        double maxDistance = Math.sqrt(centerX * centerX + centerY * centerY);
        double strength = parameters.get(0).getValue();

        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                double distance = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
                double vignette = 1 - (distance / maxDistance) * strength / 10;

                r *= vignette;
                g *= vignette;
                b *= vignette;

                int filteredRgb = (r << 16) | (g << 8) | b;
                filteredImage.setRGB(x, y, filteredRgb);
            }
        }
        return filteredImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
