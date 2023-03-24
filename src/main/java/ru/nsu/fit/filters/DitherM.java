package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DitherM implements Filter {
    private List<Parameter> parameters = new ArrayList<>();

    public DitherM() {
        parameters.add(new Parameter("binarization parameter", 50, 1020));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage ditherImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);




        return ditherImg;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

}