package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.List;

public class Watercolorization implements Filter{
    @Override
    public BufferedImage execute(BufferedImage image) {
        MedianFilter medianFilter = new MedianFilter(5);
        SharpeningFilter sharpeningFilter = new SharpeningFilter();

        BufferedImage newImage = medianFilter.execute(image);
        return sharpeningFilter.execute(newImage);
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
