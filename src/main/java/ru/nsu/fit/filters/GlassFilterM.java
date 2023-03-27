package ru.nsu.fit.filters;

import ru.nsu.fit.parametersFrame.Parameter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlassFilterM implements Filter{
    private List<Parameter> parameters = new ArrayList<>();
    private Random random = new Random();

    public GlassFilterM(){
        parameters.add(new Parameter("Scale", 3, 30));
    }

    @Override
    public BufferedImage execute(BufferedImage img) {
        BufferedImage glassImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int neighborColRandomIdx = getNeighbor(i, img.getWidth());
                int neighborRowRandomIdx = getNeighbor(j, img.getHeight());
                glassImg.setRGB(i, j, img.getRGB(i + neighborColRandomIdx, j+ neighborRowRandomIdx));

            }
        }
        return glassImg;
    }

    private int getNeighbor(int currentIdx, int maxIdx){
        int scale = (int)parameters.get(0).getValue();
        int bound = scale * 2 + 1;
        int randomNeighborIdx = random.nextInt(bound) - scale; // от -scale до scale
        if(currentIdx + randomNeighborIdx > maxIdx - 1 || currentIdx + randomNeighborIdx < 0)
            return getNeighbor(currentIdx, maxIdx);
        else
            return randomNeighborIdx;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }
}

