package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FSdithering implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public FSdithering(){
        parameters.add(new Parameter("quantization level Red", 2, 256));
        parameters.add(new Parameter("quantization level Green", 2, 256));
        parameters.add(new Parameter("quantization level Blue", 2, 256));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        int qLevelRed = (int) parameters.get(0).getValue();
        int qLevelGreen = (int) parameters.get(1).getValue();
        int qLevelBlue = (int) parameters.get(2).getValue();
        BufferedImage FSditheringImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        return FSditheringImg;
    }
    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
