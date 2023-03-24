package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

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
    public BufferedImage execute(BufferedImage image){
        sizeMatrix = (int)parameters.get(0).getValue();
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
                        // края !!!
                        if(x + u >= 0 && x + u < image.getWidth() && y + v >= 0 && y + v < image.getHeight()) {
                            int rgb = image.getRGB(x + u, y + v);
                            sumR += matrix[u + sizeMatrix][v + sizeMatrix] * ((rgb >> 16) & 0xff);
                            sumG += matrix[u + sizeMatrix][v + sizeMatrix] * ((rgb >> 8) & 0xff);
                            sumB += matrix[u + sizeMatrix][v + sizeMatrix] * (rgb & 0xff);
                        }
                    }
                }
                newImage.setRGB(x, y, (255 << 24) | ((int)sumR << 16) | ((int)sumG << 8) | (int)sumB);
            }
        }

        return newImage;
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

