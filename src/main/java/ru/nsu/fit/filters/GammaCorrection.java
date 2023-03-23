package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GammaCorrection implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public GammaCorrection(){
        parameters.add(new Parameter("Gamma", 0.1, 10));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        double gamma = parameters.get(0).getValue();
        BufferedImage gammaImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int color = img.getRGB(i, j);
                int alphaComponent = (color >> 24) & 0xff;
                int redComponent = (color >> 16) & 0xff;
                int greenComponent = (color >> 8) & 0xff;
                int blueComponent = color & 0xff;
                //gamma correction
                redComponent = (int) (Math.pow(redComponent / 255.0, gamma) * 255);
                greenComponent = (int) (Math.pow(greenComponent / 255.0, gamma) * 255);
                blueComponent = (int) (Math.pow(blueComponent / 255.0, gamma) * 255);
                int newColor = (alphaComponent << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                gammaImg.setRGB(i, j, newColor);
            }
        }
        return gammaImg;
    }
    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
