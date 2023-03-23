package ru.nsu.fit;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AboutFrame extends JFrame {
    String data;
    MainFrame mainFrame;

    public AboutFrame(MainFrame mainFrame) {
        try {
            this.mainFrame = mainFrame;
            Path path = Paths.get("src/main/resources/about.txt");
            data = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        onAbout();
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(mainFrame, this.getText(), "About program", JOptionPane.INFORMATION_MESSAGE);
    }

    String getText(){
        return data;
    }
}
