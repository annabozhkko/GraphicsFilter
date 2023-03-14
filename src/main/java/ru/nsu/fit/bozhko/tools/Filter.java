package ru.nsu.fit.bozhko.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Filter {
    public void blackWhite(BufferedImage image){
        for(int i = 0; i < image.getWidth(); ++i){
            for(int j = 0; j < image.getHeight(); ++j){
                Color color = new Color(image.getRGB(i, j));
                int R = color.getRed();
                int G = color.getGreen();
                int B = color.getBlue();
                Color newColor = new Color((int)(0.299 * R), (int)(0.587 * G), (int)(0.114 * B));
                image.setRGB(i, j, newColor.getRGB());
            }
        }
    }

}
