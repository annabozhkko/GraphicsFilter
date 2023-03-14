package ru.nsu.fit.bozhko.components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class OpenFile extends JFrame {
    public OpenFile(GraphicsPanel graphicsPanel){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            graphicsPanel.setImage(new ImageIcon(selectedFile.getAbsolutePath()).getImage());
        }
    }
}