package ru.nsu.fit.filters;

import java.awt.image.BufferedImage;
import ru.nsu.fit.parametersFrame.*;

import java.util.ArrayList;
import java.util.List;

public class Rotate implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public Rotate(){
        parameters.add(new Parameter("Angle", -180, 180));
    }


    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        //var result = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        double angle = parameters.get(0).getValue();
        var sin = Math.abs(Math.sin(Math.toRadians(angle)));
        var cos = Math.abs(Math.cos(Math.toRadians(angle)));
        var w = originalImage.getWidth();
        var h = originalImage.getHeight();
        var newW = (int) Math.floor(w * cos + h * sin);
        var newH = (int) Math.floor(h * cos + w * sin);
        var result = new BufferedImage(newW, newH, originalImage.getType());
        var g = result.createGraphics();
        g.translate((newW - w) / 2, (newH - h) / 2);
        g.rotate(Math.toRadians(angle), (int)(w / 2), (int)(h / 2));
        g.drawRenderedImage(originalImage, null);
        g.dispose();
        return result;
    }


    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
