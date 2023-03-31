package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FSA implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public FSA() {
        parameters.add(new Parameter("quantization level Red", 2, 128));
        parameters.add(new Parameter("quantization level Green", 2, 128));
        parameters.add(new Parameter("quantization level Blue", 2, 128));
    }

    public static int findClosestColor(int color, int paletteSize) {
        var stepSize = 256 / (paletteSize-1);
        var result = Math.round((double) color / stepSize) * stepSize;
        return (int)((result>255)? 255: Math.max(result, 0));
    }

    public static int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public static int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public static int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    public static int getColor(int rgb, int index) {
        if (index == 0) return getRed(rgb);
        if (index == 1) return getGreen(rgb);
        if (index == 2) return getBlue(rgb);
        return -1;
    }

    public static int composeColor(int r, int g, int b) {
        return ((0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    public static int setColor(int rgb, int index, int color) {
        if (index == 0) return composeColor(color, getGreen(rgb), getBlue(rgb));
        if (index == 1) return composeColor(getRed(rgb), color, getBlue(rgb));
        if (index == 2) return composeColor(getRed(rgb), getGreen(rgb), color);
        return -1;
    }

    @Override
    public BufferedImage execute(BufferedImage original) {
        var img = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        var colors = new double[img.getWidth()][img.getHeight()][3];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                var rgb = original.getRGB(x, y);
                var setRgb = 0;
                for (int i = 0; i < 3; i++) {
                    var bValue = getColor(rgb, i);
                    var currentColor = bValue + colors[x][y][i];
                    var newColor = findClosestColor((int) currentColor,(int)parameters.get(i).getValue());
                    setRgb = setColor(setRgb, i, newColor);
                    var e = currentColor - newColor;
                    if (x + 1 < original.getWidth()) {
                        colors[x+1][y][i] += 7. * e / 16;
                    }
                    if (x - 1 >= 0 && y + 1 < original.getHeight()) {
                        colors[x-1][y+1][i] += 3. * e / 16;
                    }
                    if (y + 1 < original.getHeight()) {
                        colors[x][y+1][i] += 5. * e / 16;
                    }
                    if (x + 1 < original.getWidth() && y + 1 < original.getHeight()) {
                        colors[x+1][y+1][i] += e / 16;
                    }
                }
                img.setRGB(x, y, setRgb);
            }
        }
        return img;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
