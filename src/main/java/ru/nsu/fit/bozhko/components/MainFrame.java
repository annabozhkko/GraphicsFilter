package ru.nsu.fit.bozhko.components;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        setPreferredSize(new Dimension(640, 480));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GraphicsPanel gpanel = new GraphicsPanel(640, 480);

        JScrollPane scrollPane = new JScrollPane(gpanel);
        gpanel.setScrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        Menu menu = new Menu(gpanel);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        Toolbar toolbar = new Toolbar(gpanel);
        add(toolbar, BorderLayout.PAGE_START);

        setVisible(true);
        pack();
    }
}
