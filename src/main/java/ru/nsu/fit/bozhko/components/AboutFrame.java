package ru.nsu.fit.bozhko.components;

import javax.swing.*;

public class AboutFrame extends JFrame {
    public AboutFrame(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea("text");

        JPanel aboutPanel = new JPanel();
        aboutPanel.add(area);
        add(aboutPanel);

        setVisible(true);
        setResizable(true);
        pack();
    }
}
