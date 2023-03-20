package ru.nsu.fit.bozhko.components;

import ru.nsu.fit.bozhko.tools.Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class ParametersFrame extends JDialog {
    private List<Parameter> parameters;
    private JPanel panel = new JPanel();

    public ParametersFrame(Filter filter, BufferedImage image){
        parameters = filter.getParameters();

        if (parameters == null){
            filter.execute(image);
            return;
        }

        setPreferredSize(new Dimension(450, 100 * parameters.size()));
        setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(panel);

        for(Parameter parameter : parameters){
            JTextField editBox = new JTextField();
            JSlider slider = new JSlider(parameter.getMin(), parameter.getMax());

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
            slider.setValue(parameter.getValue());
            editBox.setText(Integer.toString(parameter.getValue()));

            panel.add(slider);
            panel.add(editBox);
            panel.add(new JLabel(parameter.getName()));
        }

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        cancelButton.setPreferredSize(new Dimension(100, 30));

        JButton okButton = new JButton("Ok");
        okButton.setPreferredSize(new Dimension(100, 30));

        okButton.addActionListener(e -> {
            filter.execute(image);
            dispose();
        });

        panel.add(cancelButton);
        panel.add(okButton);

        pack();
        setVisible(true);
    }
}
