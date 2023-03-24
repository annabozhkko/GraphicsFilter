package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HighlightBorderSobel implements Filter {
    private List<Parameter> parameters = new ArrayList<>();

    public HighlightBorderSobel(){
        parameters.add(new Parameter("binarization parameter", 50, 1020));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage highlightBorderImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int binarizationParameter = (int) parameters.get(0).getValue();

        int[] sobelX = {-1, 0, 1, -2, 0, 2, -1, 0, 1};
        int[] sobelY = {1, 2, 1, 0, 0, 0, -1, -2, -1};
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight() ; j++) {
                int gxRed = 0, gxGreen = 0, gxBlue = 0;
                int gyRed = 0, gyGreen = 0, gyBlue = 0;
                int redComponent = 0;
                int greenComponent = 0;
                int blueComponent = 0;
                for (int k = 0; k < 3; k++) {
                    for (int m = 0; m < 3; m++) {
                        int x = i + k - 1;
                        int y = j + m - 1;
                        /*if(y < 0)  // edge conditions
                            y = (-1)*y + 1;
                        else if(y >= img.getWidth())
                            y = img.getWidth() - y;
                        if(x < 0)  // edge conditions
                            x = (-1)*x - 1;
                        else if(x >= img.getHeight())
                            x = img.getHeight() - x;

                        int color = img.getRGB(x, y);
                        redComponent = (color >> 16) & 0xff;
                        greenComponent = (color >> 8) & 0xff;
                        blueComponent = color & 0xff;
                        gxRed += redComponent * sobelX[k * 3 + m];
                        gyRed += redComponent * sobelY[k * 3 + m];
                        gxGreen += greenComponent * sobelX[k * 3 + m];
                        gyGreen += greenComponent * sobelY[k * 3 + m];
                        gxBlue += blueComponent * sobelX[k * 3 + m];
                        gyBlue += blueComponent * sobelY[k * 3 + m];*/
                        // логически все правильно, роме того что границы пока что просто выкидываюютя
                        // нужно проверить границы и позвоможно перевести картинку сначала в чб
                        if ((x >= 0 && x < img.getWidth()) && (y >= 0 && y < img.getHeight())){
                            int color = img.getRGB(x, y);
                            redComponent = (color >> 16) & 0xff;
                            greenComponent = (color >> 8) & 0xff;
                            blueComponent = color & 0xff;
                            gxRed += redComponent * sobelX[k * 3 + m];
                            gyRed += redComponent * sobelY[k * 3 + m];
                            gxGreen += greenComponent * sobelX[k * 3 + m];
                            gyGreen += greenComponent * sobelY[k * 3 + m];
                            gxBlue += blueComponent * sobelX[k * 3 + m];
                            gyBlue += blueComponent * sobelY[k * 3 + m];
                        }
                    }
                }
                redComponent = (Math.abs(gxRed) + Math.abs(gyRed) > binarizationParameter) ? 255 : 0;
                greenComponent = (Math.abs(gxGreen) + Math.abs(gyGreen) > binarizationParameter) ? 255 : 0;
                blueComponent = (Math.abs(gxBlue) + Math.abs(gyBlue) > binarizationParameter) ? 255 : 0;

                int newColor = (255 << 24) | (redComponent << 16) | (greenComponent << 8) | blueComponent;
                highlightBorderImg.setRGB(i, j, newColor);
            }
        }
        return highlightBorderImg;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
