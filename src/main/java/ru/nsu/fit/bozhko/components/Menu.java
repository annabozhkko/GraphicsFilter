package ru.nsu.fit.bozhko.components;

import javax.swing.*;

public class Menu extends JMenu {
    public Menu(GraphicsPanel panel){
        super("Menu");
        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem saveItem = new JRadioButtonMenuItem("Save file");
        saveItem.addActionListener(e -> new SaveFile(panel));
        add(saveItem);
        group.add(saveItem);

        JRadioButtonMenuItem openFileItem = new JRadioButtonMenuItem("Open file");
        openFileItem.addActionListener(e -> new OpenFile(panel));
        add(openFileItem);
        group.add(openFileItem);

        JRadioButtonMenuItem aboutItem = new JRadioButtonMenuItem("About program");
        aboutItem.addActionListener(e -> new AboutFrame());
        add(aboutItem);
        group.add(aboutItem);

        JRadioButtonMenuItem exitItem = new JRadioButtonMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        add(exitItem);
        group.add(aboutItem);
    }
}
