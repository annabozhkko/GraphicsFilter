package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Rotation implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public Rotation(){
        parameters.add(new Parameter("Angle", -180, 180));
    }

    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        double angle = parameters.get(0).getValue();
        var sin = Math.abs(Math.sin(Math.toRadians(angle)));
        var cos = Math.abs(Math.cos(Math.toRadians(angle)));
        var w = originalImage.getWidth();
        var h = originalImage.getHeight();

        var newW = (int) Math.floor(w * cos + h * sin);
        var newH = (int) Math.floor(h * cos + w * sin);
        var result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        for (int y = 1; y < h; y++) {
            for (int x = 1; x < w; x++) {
                var newX = (int) Math.floor(x * cos + y * sin);
                var newY = (int) Math.floor(y * cos + x * sin);
                var rgb = originalImage.getRGB(x,y);
                result.setRGB(newW-newX, newH-newY, rgb);
            }
        }
        return result;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
