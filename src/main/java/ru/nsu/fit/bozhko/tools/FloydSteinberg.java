package ru.nsu.fit.bozhko.tools;

import ru.nsu.fit.bozhko.components.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Аня
public class FloydSteinberg implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private ArrayList<RGB> palette = new ArrayList<>();

    @Override
    public void execute(BufferedImage image) {
        int[] values = {0, 128, 255};
        for(int i : values){
            for(int j : values){
                for(int k : values){
                    palette.add(new RGB(i, j, k, 255));
                }
            }
        }

        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                RGB oldRGB = new RGB(image.getRGB(x, y));
                RGB newRGB = getNearestColor(oldRGB);

                image.setRGB(x, y, newRGB.toInt());

                RGB e = new RGB(oldRGB.getR() - newRGB.getR(), oldRGB.getG() - newRGB.getG(), oldRGB.getB() - newRGB.getB(),
                        255);

                if(x < image.getWidth() - 1){
                    RGB neighbourOldRGB = new RGB(image.getRGB(x + 1, y));
                    RGB neighbourNewRGB = new RGB(neighbourOldRGB.getR() + e.getR() * 7 / 16,
                            neighbourOldRGB.getG() + e.getG() * 7 / 16, neighbourOldRGB.getB() + e.getB() * 7 / 16, 255);
                    image.setRGB(x + 1, y, neighbourNewRGB.toInt());
                }

                if(x > 0 && y < image.getHeight() - 1){
                    RGB neighbourOldRGB = new RGB(image.getRGB(x - 1, y + 1));
                    RGB neighbourNewRGB = new RGB(neighbourOldRGB.getR() + e.getR() * 3 / 16,
                            neighbourOldRGB.getG() + e.getG() * 3 / 16, neighbourOldRGB.getB() + e.getB() * 3 / 16, 255);
                    image.setRGB(x - 1, y + 1, neighbourNewRGB.toInt());
                }

                if(y < image.getHeight() - 1){
                    RGB neighbourOldRGB = new RGB(image.getRGB(x, y + 1));
                    RGB neighbourNewRGB = new RGB(neighbourOldRGB.getR() + e.getR() * 5 / 16,
                            neighbourOldRGB.getG() + e.getG() * 5 / 16, neighbourOldRGB.getB() + e.getB() * 5 / 16, 255);
                    image.setRGB(x, y + 1, neighbourNewRGB.toInt());
                }

                if(x < image.getWidth() - 1 && y < image.getHeight() - 1){
                    RGB neighbourOldRGB = new RGB(image.getRGB(x + 1, y + 1));
                    RGB neighbourNewRGB = new RGB(neighbourOldRGB.getR() + e.getR() / 16,
                            neighbourOldRGB.getG() + e.getG() / 16, neighbourOldRGB.getB() + e.getB() / 16, 255);
                    image.setRGB(x + 1, y + 1, neighbourNewRGB.toInt());
                }
            }
        }
    }

    public double diff(RGB color1, RGB color2) {
        double rdiff = color1.getR() - color2.getR();
        double gdiff = color1.getG() - color2.getG();
        double bdiff = color1.getB() - color2.getB();
        return rdiff * rdiff + gdiff * gdiff + bdiff * bdiff;
    }

    private RGB getNearestColor(RGB color){
        RGB resultColor = palette.get(0);
        double minDiff = diff(color, palette.get(0));

        for (int i = 1; i < palette.size(); ++i){
            double newDiff = diff(color, palette.get(i));
            if(minDiff > newDiff){
                minDiff = newDiff;
                resultColor = palette.get(i);
            }
        }

        return resultColor;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}
