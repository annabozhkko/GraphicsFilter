package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Rotation implements Filter{
    private List<Parameter> parameters = new ArrayList<>();

    public Rotation(){
        parameters.add(new Parameter("Angle", -180, 180));
    }

    @Override
    public BufferedImage execute(BufferedImage originalImage) {
        double angle = parameters.get(0).getValue();
        /*
        var sin = Math.abs(Math.sin(Math.toRadians(angle)));
        var cos = Math.abs(Math.cos(Math.toRadians(angle)));
        var w = originalImage.getWidth();
        var h = originalImage.getHeight();

        var newW = (int) Math.floor(w * cos + h * sin);
        var newH = (int) Math.floor(h * cos + w * sin);
        var result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        for (int y = 1; y < h; y++) {
            for (int x = 1; x < w; x++) {
                var newX = (int) Math.floor(x * cos + y * sin);
                var newY = (int) Math.floor(y * cos + x * sin);
                var rgb = originalImage.getRGB(x,y);
                result.setRGB(newW-newX, newH-newY, rgb);
            }
        }
        return result;

         */

        // Вычислить размеры нового изображения
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();
        double radianAngle = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radianAngle));
        double cos = Math.abs(Math.cos(radianAngle));
        int newW = (int) (h * sin + w * cos);
        int newH = (int) (h * cos + w * sin);

        // Создать новое изображение и заполнить его черным цветом
        BufferedImage newImage = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < newW; ++i) {
            for (int j = 0; j < newH; ++j) {
                newImage.setRGB(i, j, Color.WHITE.getRGB());
            }
        }

        // Выполнить поворот по пикселям
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int newX = (int) ((x - w/2) * Math.cos(radianAngle) + (y - h/2) * Math.sin(radianAngle) + newW/2);
                int newY = (int) (-(x - w/2) * Math.sin(radianAngle) + (y - h/2) * Math.cos(radianAngle) + newH/2);
                if (newX >= 0 && newX < newW && newY >= 0 && newY < newH) {
                    int rgb = originalImage.getRGB(x, y);
                    newImage.setRGB(newX, newY, rgb);
                }
            }
        }

        for (int x = 0; x < newW; x++) {
            for (int y = 0; y < newH; y++) {
                if(newImage.getRGB(x, y) != Color.WHITE.getRGB()){
                    continue;
                }
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                int countPixels = 0;

                for(int u = -1; u <= 1; ++u){
                    for(int v = -1; v <= 1; ++v){
                        if(u == 0 && v == 0){
                            continue;
                        }
                        int pixelX = x + u;
                        int pixelY = y + v;

                        if (pixelX >= 0 && pixelX < newImage.getWidth() && pixelY >= 0 && pixelY < newImage.getHeight()) {
                            int rgb = newImage.getRGB(pixelX, pixelY);
                            sumRed += (rgb >> 16) & 0xFF;
                            sumGreen += (rgb >> 8) & 0xFF;
                            sumBlue += rgb & 0xFF;
                            //System.out.println(rgb);
                            countPixels++;
                        }
                    }
                }

                int avgRed = sumRed / countPixels;
                int avgGreen = sumGreen / countPixels;
                int avgBlue = sumBlue / countPixels;

                //System.out.println(avgRed + " " + avgGreen + " " + avgBlue);

                newImage.setRGB(x, y, (255 << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue);
            }
        }


        return newImage;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
