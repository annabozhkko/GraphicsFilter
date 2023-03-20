package ru.nsu.fit.bozhko.components;

import ru.nsu.fit.bozhko.tools.*;

import javax.swing.*;

public class Toolbar extends JToolBar {
    private final ButtonGroup group = new ButtonGroup();
    FloydSteinberg floydSteinberg = new FloydSteinberg();
    Dither dither = new Dither();
    GaussianBlur gaussianBlur = new GaussianBlur();
    Embossing embossing = new Embossing();

    public Toolbar(GraphicsPanel panel){
        JRadioButton saveButton = new JRadioButton();
        saveButton.setIcon(new ImageIcon("src/main/resources/save.png"));
        saveButton.addActionListener(e -> new SaveFile(panel));
        saveButton.setToolTipText("Save");
        add(saveButton);
        group.add(saveButton);

        JRadioButton openFileButton = new JRadioButton();
        openFileButton.setIcon(new ImageIcon("src/main/resources/open.png"));
        openFileButton.addActionListener(e -> new OpenFile(panel));
        openFileButton.setToolTipText("Open file");
        add(openFileButton);
        group.add(openFileButton);

        JRadioButton filterButton = new JRadioButton();
        filterButton.setIcon(new ImageIcon("src/main/resources/open.png"));
        filterButton.addActionListener(e -> {
            ParametersFrame parametersFrame = new ParametersFrame(gaussianBlur, panel.getImage());
            panel.repaint();
        });
        filterButton.setToolTipText("Filter");
        add(filterButton);
        group.add(filterButton);

        JRadioButton aboutButton = new JRadioButton();
        aboutButton.setIcon(new ImageIcon("src/main/resources/about.gif"));
        aboutButton.addActionListener(e -> new AboutFrame());
        aboutButton.setToolTipText("About program");
        add(aboutButton);
        group.add(aboutButton);

        JRadioButton exitButton = new JRadioButton();
        exitButton.setIcon(new ImageIcon("src/main/resources/exit.gif"));
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setToolTipText("Exit");
        add(exitButton);
        group.add(exitButton);
    }
}
