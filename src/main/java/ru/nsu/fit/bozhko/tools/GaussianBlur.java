package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GaussianBlur implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private double [][] matrix;
    private int sizeMatrix;

    public GaussianBlur(){
        parameters.add(new Parameter("Size matrix", 1, 5));
    }

    @Override
    public void execute(BufferedImage image){
        sizeMatrix = parameters.get(0).getValue();
        matrix = new double[sizeMatrix * 2 + 1][sizeMatrix * 2 + 1];
        createGaussian();
        //matrix = new double[][]{{0, 1./6, 0}, {1./6, 2./6, 1./6}, {0, 1./6, 0}};
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                double sumR = 0;
                double sumG = 0;
                double sumB = 0;
                for(int u = -sizeMatrix; u <= sizeMatrix; ++u){
                    for(int v = -sizeMatrix; v <= sizeMatrix; ++v){
                        if(x + u >= 0 && x + u < image.getWidth() && y + v >= 0 && y + v < image.getHeight()) {
                            RGB rgb = new RGB(image.getRGB(x + u, y + v));
                            sumR += matrix[u + sizeMatrix][v + sizeMatrix] * rgb.getR();
                            sumG += matrix[u + sizeMatrix][v + sizeMatrix] * rgb.getG();
                            sumB += matrix[u + sizeMatrix][v + sizeMatrix] * rgb.getB();
                        }
                    }
                }
                RGB newRGB = new RGB(sumR, sumG, sumB, 255);
                newImage.setRGB(x, y, newRGB.toInt());
            }
        }

        Graphics2D g = image.createGraphics();
        g.drawImage(newImage, 0, 0,  image.getWidth(), image.getHeight(),null);
    }

    private void createGaussian(){
        for(int i = -sizeMatrix; i <= sizeMatrix; ++i){
            for(int j = -sizeMatrix; j <= sizeMatrix; ++j){
                matrix[i + sizeMatrix][j + sizeMatrix] = Math.exp(-(i * i + j * j) / 2.) / (2 * Math.PI);
            }
        }
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}

