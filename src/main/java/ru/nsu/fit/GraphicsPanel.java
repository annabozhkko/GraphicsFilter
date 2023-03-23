package ru.nsu.fit;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GraphicsPanel extends JPanel implements MouseInputListener {
    public BufferedImage filterImage;
    private BufferedImage originalImage;
    private int width, height;
    private int realWidthImage, realHeightImage;

    public boolean isFilter = false;
    public boolean parameter = false;
    private Point mousePoint;

    public GraphicsPanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

       /* image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                image.setRGB(i, j, Color.WHITE.getRGB());
            }
        }*/

        addMouseListener(this);

        // слежение за мышкой
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        BufferedImage currentImg = (isFilter) ? filterImage : originalImage;
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(currentImg, 0, 0 , width, height, this);
        g2D.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //setFilter(filterImage);
        isFilter = !isFilter;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        mousePoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point dragEventPoint = e.getPoint();
        JViewport viewport = (JViewport) this.getParent();
        Point viewPos = viewport.getViewPosition();
        int maxViewPosX = this.getWidth() - viewport.getWidth();
        int maxViewPosY = this.getHeight() - viewport.getHeight();

        if(this.getWidth() > viewport.getWidth()) {
            viewPos.x -= dragEventPoint.x - mousePoint.x;

            if(viewPos.x < 0) {
                viewPos.x = 0;
                mousePoint.x = dragEventPoint.x;
            }

            if(viewPos.x > maxViewPosX) {
                viewPos.x = maxViewPosX;
                mousePoint.x = dragEventPoint.x;
            }
        }

        if(this.getHeight() > viewport.getHeight()) {
            viewPos.y -= dragEventPoint.y - mousePoint.y;

            if(viewPos.y < 0) {
                viewPos.y = 0;
                mousePoint.y = dragEventPoint.y;
            }

            if(viewPos.y > maxViewPosY) {
                viewPos.y = maxViewPosY;
                mousePoint.y = dragEventPoint.y;
            }
        }
        viewport.setViewPosition(viewPos);
    }

    public void setFilter(BufferedImage newImg){
        filterImage = newImg;
        if(parameter)// если параметров нет
            isFilter = true;
        else
            isFilter = !isFilter;
        repaint();
    }

    public void setImage(Image openImage){
        width = openImage.getWidth(this);
        height = openImage.getHeight(this);
        realWidthImage = width;
        realHeightImage = height;

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(width, height));

        originalImage = newImage;
        filterImage = newImage;

        Graphics2D g = originalImage.createGraphics();
        g.drawImage(openImage, 0, 0,  openImage.getWidth(this), openImage.getHeight(this),this);

        this.revalidate();

        repaint();
    }

    public BufferedImage getOriginalImage(){
        return originalImage;
    }
    public BufferedImage getFilterImage(){ return filterImage; }

    public void fitToScreen(){

    }
}