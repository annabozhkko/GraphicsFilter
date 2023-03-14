package ru.nsu.fit.bozhko.components;

import javax.swing.*;

public class ErrorFrame extends JFrame {
    public ErrorFrame(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea("Некорректно введенные значения\n\n" +
                "Диапазон допустимых значений:\n" + "Толщина линии: 1-10\n" +
                "Радиус штампа: 0-240\n" + "Угол поворота штампа: 0-360\n");

        JPanel aboutPanel = new JPanel();
        aboutPanel.add(area);
        add(aboutPanel);

        setVisible(true);
        setResizable(true);
        pack();
    }
}
