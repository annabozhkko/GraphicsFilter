package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Filter {
    public void execute(BufferedImage image);
    public List<Parameter> getParameters();
}
