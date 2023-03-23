package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Filter {
    public BufferedImage execute(BufferedImage originalImage);
    public List<Parameter> getParameters();
}
