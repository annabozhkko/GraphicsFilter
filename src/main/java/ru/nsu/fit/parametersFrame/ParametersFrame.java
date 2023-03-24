package ru.nsu.fit.parametersFrame;

import ru.nsu.fit.GraphicsPanel;
import ru.nsu.fit.filters.Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class ParametersFrame {
    private List<Parameter> parameters;
    private JPanel panel = new JPanel();
    private BufferedImage filterImage;

    public ParametersFrame(Filter filter, BufferedImage originalImage, GraphicsPanel gPanel){
        parameters = filter.getParameters();

        if (parameters == null){
            gPanel.setParameter(false);
            gPanel.setFilter(filter.execute(originalImage));
            return;
        }

        for(Parameter parameter : parameters){
            JTextField editBox = new JTextField();
            JSlider slider = new JSlider((int)parameter.getMin(),(int) parameter.getMax());

            editBox.setPreferredSize(new Dimension(100, 30));

            editBox.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(editBox.getText().equals(""))
                        return;
                    int value = Integer.parseInt(editBox.getText());
                    // проверка
                    /*if(value < 0 || value > 240){
                        new ErrorFrame();
                    }
                    else {
                        slider.setValue(value);
                    }
                     */
                    slider.setValue(value);
                    parameter.setValue(value);
                }
            });

            slider.addChangeListener(e -> {
                JSlider source = (JSlider)e.getSource();
                editBox.setText(Integer.toString(source.getValue()));
                parameter.setValue(source.getValue());
            });
            slider.setValue((int)parameter.getValue());
            editBox.setText(Integer.toString((int)parameter.getValue()));

            panel.add(slider);
            panel.add(editBox);
            panel.add(new JLabel(parameter.getName()));
        }

        int option = JOptionPane.showOptionDialog(null, panel, "Enter the parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            gPanel.setParameter(true);
            gPanel.setFilter(filter.execute(originalImage));
        }
    }
}
