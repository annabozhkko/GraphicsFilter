package ru.nsu.fit.parametersFrame;

import ru.nsu.fit.GraphicsPanel;
import ru.nsu.fit.filters.Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ParametersFrame {
    private List<Parameter> parameters;
    private JPanel panel = new JPanel();

    public ParametersFrame(Filter filter, GraphicsPanel gPanel){
        parameters = filter.getParameters();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (parameters == null){
            try {
                gPanel.setFilter(filter);
            }catch (NullPointerException ignored){}
            return;
        }

        for(Parameter parameter : parameters){
            JPanel oneLineParameters = new JPanel(new FlowLayout());
            JTextField editBox = new JTextField();
            JSlider slider = new JSlider((int)parameter.getMin(),(int)parameter.getMax());
            slider.setMinorTickSpacing((int)parameter.getStep());
            slider.setSnapToTicks(true);

            editBox.setPreferredSize(new Dimension(100, 30));

            editBox.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(editBox.getText().equals(""))
                        return;
                    double value = Double.parseDouble(editBox.getText());
                    slider.setValue((int)value);
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

            oneLineParameters.add(slider);
            oneLineParameters.add(editBox);
            oneLineParameters.add(new JLabel(parameter.getName()));
            panel.add(oneLineParameters);
        }

        int option = JOptionPane.showOptionDialog(null, panel, "Enter the parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            try {
                gPanel.setFilter(filter);
            }   catch (NullPointerException ignored){}
        }
    }
}
