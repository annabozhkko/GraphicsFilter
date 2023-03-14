package ru.nsu.fit.bozhko.components;

import ru.nsu.fit.bozhko.tools.Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static java.lang.Math.max;

public class GraphicsPanel extends JPanel implements MouseListener {
    private BufferedImage image;
    private Filter filter = new Filter();
    private int width, height;

    public GraphicsPanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                image.setRGB(i, j, Color.WHITE.getRGB());
            }
        }

        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(image, 0, 0 , width, height, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void setImage(Image openImage){
        if(openImage.getWidth(this) > width || openImage.getHeight(this) > height){
            width = max(width, openImage.getWidth(this));
            height = max(height, openImage.getHeight(this));

            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            setPreferredSize(new Dimension(width, height));
            image = newImage;
        }
        Graphics2D g = image.createGraphics();
        g.drawImage(openImage, 0, 0,  openImage.getWidth(this), openImage.getHeight(this),this);

        repaint();
    }

    public BufferedImage getImage(){
        return image;
    }
}
