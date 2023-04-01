package ru.nsu.fit;

import javax.swing.*;
import java.awt.*;
import ru.nsu.fit.menuCreator.MenuCreator;

public class MainFrame extends JFrame {
    public JMenuBar menuBar;
    public JToolBar toolBar;

    public GraphicsPanel gPanel;
    public MainFrame(){
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(640, 480));
        setTitle("Filter");

        //чтобы изображение было по центру любого экрана
        // Toolkit.getDefaultToolkit() - получение размера экрана в пикселях
        // .getScreenSize() - возвращает размеры диспея
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getPreferredSize().width / 2,
                screenSize.height / 2 - getPreferredSize().height / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //панель
        gPanel = new GraphicsPanel(640, 480);
        JScrollPane scrollPane = new JScrollPane(gPanel);
        add(scrollPane, BorderLayout.CENTER);
        gPanel.setScrollPane(scrollPane);

        // создание меню
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);

        MenuCreator menuCreator = new MenuCreator(this);
        menuCreator.createMenu();

        pack();
        setVisible(true);
    }
}
