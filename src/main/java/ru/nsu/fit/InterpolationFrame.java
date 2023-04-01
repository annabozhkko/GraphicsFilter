package ru.nsu.fit;

import javax.swing.*;
import java.awt.*;

public class InterpolationFrame extends JDialog {
    InterpolationFrame(GraphicsPanel panel){
        setPreferredSize(new Dimension(300, 200));
        setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel gpanel = new JPanel();
        add(gpanel);

        JButton bilinear = new JButton("Bilinear");
        bilinear.setPreferredSize(new Dimension(150, 30));
        bilinear.addActionListener(e -> {
            panel.setRegimeInterpolation(RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            dispose();
        });

        JButton nearestNeighbor = new JButton("Nearest neighbor");
        nearestNeighbor.setPreferredSize(new Dimension(150, 30));
        nearestNeighbor.addActionListener(e -> {
            panel.setRegimeInterpolation(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            dispose();
        });

        JButton bicubic = new JButton("Bicubic");
        bicubic.setPreferredSize(new Dimension(150, 30));
        bicubic.addActionListener(e -> {
            panel.setRegimeInterpolation(RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            dispose();
        });

        gpanel.add(bilinear);
        gpanel.add(nearestNeighbor);
        gpanel.add(bicubic);

        pack();
        setVisible(true);
    }
}
