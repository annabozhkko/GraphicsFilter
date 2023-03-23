package ru.nsu.fit.filters;

import ru.nsu.fit.filters.Filter;
import ru.nsu.fit.parametersFrame.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class BlackWhite implements Filter{

    @Override
    public BufferedImage execute(BufferedImage image) {

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x<image.getWidth(); x++) {
            for (int y = 0; y<image.getHeight(); y++) {
                // получаем (x, y) пиксель
                int pixel = (image.getRGB(x, y));
                // получаем компоненты цветов пикселя
                float R = (float)((pixel & 0x00FF0000) >> 16); // красный
                float G = (float)((pixel & 0x0000FF00) >> 8); // зеленый
                float B = (float)(pixel & 0x000000FF); // синий
                // делаем цвет черно-белым (оттенки серого) - находим среднее арифметическое
                R = G = B = (R + G + B) / 3.0f;
                // собираем новый пиксель по частям (по каналам)
                int newPixel = 0xFF000000 | ((int)R << 16) | ((int)G << 8) | ((int)B);
                // добавляем его в Bitmap нового изображения
                newImage.setRGB(x, y, newPixel);
            }
        }
        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return null;
    }
}
