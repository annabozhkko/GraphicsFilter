package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class Watercolorization implements Filter{
    @Override
    public void execute(BufferedImage image) {
        MedianFilter medianFilter = new MedianFilter();
        medianFilter.execute(image);
        // фильтр резкости
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
