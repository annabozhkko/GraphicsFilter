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
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
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
        return closeHoles(newImage);
    }

    private BufferedImage closeHoles(BufferedImage image) {
        var white = new Color(255, 255, 255);
        for (int x = 1; x<image.getWidth()-1; x++) {
            for (int y = 1; y<image.getHeight()-1; y++) {
                //System.out.println(image.getRGB(x,y));
                if (image.getRGB(x,y) == 0){
                    //System.out.println("+");
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    var rgb = image.getRGB(x-1,y-1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x,y-1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x+1,y-1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x-1,y);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x+1,y-1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x-1,y+1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x,y+1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    rgb = image.getRGB(x+1,y+1);
                    r+=getRed(rgb);
                    g+=getGreen(rgb);
                    b+=getBlue(rgb);
                    r = (int) r/8;
                    g = (int) g/8;
                    b = (int) b/8;
                    if (!(r==0 && b==0 && g==0)){
                        var setrgb = composeColor(r, g, b);
                        image.setRGB(x, y, setrgb);
                    }
                }
            }
        }
        return image;
    }

    public static int composeColor(int r, int g, int b) {
        return ((0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
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

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
