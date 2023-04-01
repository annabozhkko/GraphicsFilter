package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GaussianBlur implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private double [][] matrix;
    private int sizeMatrix;
    private int sigma = 2;

    public GaussianBlur(){
        parameters.add(new Parameter("Size matrix", 3, 11));
        parameters.get(0).setStep(2);
    }

    @Override
    public BufferedImage execute(BufferedImage image){
        sizeMatrix = (int)parameters.get(0).getValue();

        double coef;
        if(sizeMatrix == 3){
            matrix = new double[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
            coef = 1/16.;
        }
        if(sizeMatrix == 5){
            matrix = new double[][]
                    {{1, 2, 3, 2, 1},
                    {2, 4, 5, 4, 2},
                    {3, 5, 6, 5, 3},
                    {2, 4, 5, 4, 2},
                    {1, 2, 3, 2, 1}};
            coef = 1/74.;
        }
        else{
            return averageValueFilter(image);
        }

        int r = (sizeMatrix - 1) / 2;

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                double sumR = 0;
                double sumG = 0;
                double sumB = 0;
                for(int u = -r; u <= r; ++u){
                    for(int v = -r; v <= r; ++v){
                        int rgb;
                        if(x + u >= 0 && x + u < image.getWidth() && y + v >= 0 && y + v < image.getHeight()) {
                            rgb = image.getRGB(x + u, y + v);
                        }
                        else {
                            rgb = image.getRGB(x, y);
                        }
                        sumR += coef * matrix[u + r][v + r] * ((rgb >> 16) & 0xff);
                        sumG += coef * matrix[u + r][v + r] * ((rgb >> 8) & 0xff);
                        sumB += coef * matrix[u + r][v + r] * (rgb & 0xff);
                    }
                }
                newImage.setRGB(x, y, (255 << 24) | ((int)sumR << 16) | ((int)sumG << 8) | (int)sumB);
            }
        }

        return newImage;
    }

    private BufferedImage averageValueFilter(BufferedImage image){
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int r = (sizeMatrix - 1) / 2;

        for(int x = 0; x < image.getWidth(); ++x){
            for(int y = 0; y < image.getHeight(); ++y){
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for(int u = -r; u <= r; ++u){
                    for(int v = -r; v <= r; ++v){
                        int pixelX = x + u;
                        int pixelY = y + v;

                        int rgb;
                        if (pixelX >= 0 && pixelX < image.getWidth() && pixelY >= 0 && pixelY < image.getHeight()) {
                            rgb = image.getRGB(pixelX, pixelY);
                        }
                        else {
                            rgb = image.getRGB(x, y);
                        }
                        sumRed += (rgb >> 16) & 0xFF;
                        sumGreen += (rgb >> 8) & 0xFF;
                        sumBlue += rgb & 0xFF;
                    }
                }

                int avgRed = sumRed / (sizeMatrix * sizeMatrix);
                int avgGreen = sumGreen / (sizeMatrix * sizeMatrix);
                int avgBlue = sumBlue / (sizeMatrix * sizeMatrix);

                newImage.setRGB(x, y, (255 << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue);
            }
        }
        return newImage;
    }

    private void createGaussian(){
        for(int i = -sizeMatrix; i <= sizeMatrix; ++i){
            for(int j = -sizeMatrix; j <= sizeMatrix; ++j){
                matrix[i + sizeMatrix][j + sizeMatrix] = Math.exp(-(i * i + j * j) /( 2. * sigma * sigma)) / (2 * sigma * sigma);
            }
        }
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}

