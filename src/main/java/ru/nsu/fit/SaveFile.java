package ru.nsu.fit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveFile extends FileDialog {
    public SaveFile(GraphicsPanel panel){
        super(new Frame(), "Save file", SAVE);
        setFile("*.png");
        setVisible(true);

        if(getFile() != null){
            File file = new File(getDirectory() + getFile());
            try {
                file.createNewFile();
                ImageIO.write(panel.getFilterImage(), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
