package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class Watercolorization {
  //  @Override
    public void execute(BufferedImage image) {
        MedianFilter medianFilter = new MedianFilter();
        medianFilter.execute(image);
        // фильтр резкости
    }

  //  @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
