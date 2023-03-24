package ru.nsu.fit;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GraphicsPanel extends JPanel implements MouseInputListener {
    private BufferedImage filterImage;
    private BufferedImage originalImage;
    private int width, height;
    private int realWidthImage, realHeightImage;
    private int screenWidth, screenHeight;

    private boolean isFilter = false;
    private boolean isRealRegime = true;
    private boolean parameter = false;
    private Point mousePoint;
    private Object regimeInterpolation = RenderingHints.VALUE_INTERPOLATION_BILINEAR;

    public GraphicsPanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        addMouseListener(this);
        addComponentListener(new GraphicsPanel.ResizeListener());
        setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(getBackground().getRGB()), 5),
                        BorderFactory.createDashedBorder(Color.BLACK, 4, 4)
                )
        );

        // слежение за мышкой
        addMouseMotionListener(this);
    }
    
    static class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
           e.getComponent().setSize(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));
            super.componentResized(e);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        screenHeight = g.getClipBounds().getSize().height;
        screenWidth = g.getClipBounds().getSize().width;

        super.paintComponent(g);
        BufferedImage currentImg = (isFilter) ? filterImage : originalImage;
        var w = g.getClipBounds().getSize().width;
        var h = g.getClipBounds().getSize().height;
        g.drawImage(currentImg, 6, 6, width, height, null);
        setPreferredSize(new Dimension(w, h));
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

    public void setParameter(boolean parameter) {
        this.parameter = parameter;
    }

    public boolean isFilter() {
        return isFilter;
    }

    public void setRegimeInterpolation(Object regimeInterpolation) {
        this.regimeInterpolation = regimeInterpolation;
        //RenderingHints.VALUE_INTERPOLATION_BILINEAR
        //RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
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
        new InterpolationFrame(this);
        if(isRealRegime){
            double widthFrac = (double) width / screenWidth;
            double heightFrac = (double) height / screenHeight;
            double frac = Math.max(widthFrac, heightFrac);

            int dstWidth = (int)(width / frac);
            int dstHeight = (int)(height / frac);

            BufferedImage newOriginalImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g1 = newOriginalImage.createGraphics();
            g1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
            g1.drawImage(originalImage, 0, 0, dstWidth, dstHeight, 0, 0, width, height, null);
            originalImage = newOriginalImage;

            BufferedImage newFilterImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newFilterImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
            g2.drawImage(filterImage, 0, 0, dstWidth, dstHeight, 0, 0, width, height, null);
            filterImage = newFilterImage;

            setPreferredSize(new Dimension(dstWidth, dstHeight));
            width = dstWidth;
            height = dstHeight;
        }

        else{
            BufferedImage newOriginalImage = new BufferedImage(realWidthImage, realHeightImage, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g1 = newOriginalImage.createGraphics();
            g1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
            g1.drawImage(originalImage, 0, 0, realWidthImage, realHeightImage, 0, 0, width, height, null);
            originalImage = newOriginalImage;

            BufferedImage newFilterImage = new BufferedImage(realWidthImage, realHeightImage, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newFilterImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
            g2.drawImage(filterImage, 0, 0, realWidthImage, realHeightImage, 0, 0, width, height, null);
            filterImage = newFilterImage;

            width = realWidthImage;
            height = realHeightImage;
            setPreferredSize(new Dimension(width, height));
        }

        isRealRegime = !isRealRegime;
        repaint();
    }
}
