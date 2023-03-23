package ru.nsu.fit.menuCreator;

import ru.nsu.fit.*;
import ru.nsu.fit.AboutFrame;

import ru.nsu.fit.filters.*;
import ru.nsu.fit.parametersFrame.ParametersFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCreator {
    MainFrame mainFrame;

    //filters
    private Negative negative  = new Negative();
    private GammaCorrection gamma = new GammaCorrection();
    private HighlightBorder highlightBorder = new HighlightBorder();

    public MenuCreator(MainFrame frame) {
        mainFrame = frame;
    }

    public void createMenu() {
        MenuComponentCreator componentCreator = new MenuComponentCreator(mainFrame.menuBar, mainFrame.toolBar);

        JMenu file = componentCreator.createJMenu("File");

        JMenuItem exit = componentCreator.createJMenuItem("Exit", "Exit application", "src/main/resources/exit.gif", file);
        JButton toolBarExit = componentCreator.createJButton("Exit application", "src/main/resources/exit.gif");

        componentCreator.setAction(exit, toolBarExit,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                });

        JMenuItem about = componentCreator.createJMenuItem("About", "About program", "src/main/resources/about.gif", file);
        JButton toolBarAbout = componentCreator.createJButton("About program", "src/main/resources/about.gif");

        componentCreator.setAction(about, toolBarAbout,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AboutFrame(mainFrame);
                    }
                });

        JMenuItem save = componentCreator.createJMenuItem("Save", "saveFile","src/main/resources/save.png", file);
        JButton toolBarSave = componentCreator.createJButton("Save file", "src/main/resources/save.png");

        componentCreator.setAction(save, toolBarSave,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new SaveFile(mainFrame.gPanel);
                    }
                });

        JMenuItem open = componentCreator.createJMenuItem("Open", "openFile","src/main/resources/open.png", file);
        JButton toolBarOpen = componentCreator.createJButton("Open file", "src/main/resources/open.png");

        componentCreator.setAction(open, toolBarOpen,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new OpenFile(mainFrame.gPanel);
                    }
                });

        JMenu filter = componentCreator.createJMenu("Filter");

        // группы нужны только для инструментов. Для пунктов меню, для которых критически важно чтобы только один
        // элемент был выбран. (например одновременно нельзя выбрать два филтра, поэттому все фильтры находятся в группе)
        ButtonGroup menuGroupFilter = new ButtonGroup();
        ButtonGroup toolBarGroupFilter = new ButtonGroup();

        JRadioButtonMenuItem invertFilter = componentCreator.createRadioButtonMenuItem("invert filter", "apply negative filter", menuGroupFilter, filter);
        JToggleButton toolBarInvertFilter = componentCreator.createJToggleButton("apply negative filter", "src/main/resources/invertFilter.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(invertFilter, toolBarInvertFilter);

        componentCreator.setAction(invertFilter, toolBarInvertFilter,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(negative, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem gammaFilter = componentCreator.createRadioButtonMenuItem("gamma filter", "apply gamma correction filter", menuGroupFilter, filter);
        JToggleButton toolBarGammaFilter = componentCreator.createJToggleButton("apply gamma correction filter", "src/main/resources/gammaFilter.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(gammaFilter, toolBarGammaFilter);

        componentCreator.setAction(gammaFilter, toolBarGammaFilter,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(gamma, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem highlightBorderRobertsFilter = componentCreator.createRadioButtonMenuItem("highlight border Roberts filter", "apply highlight border filter using the Roberts operator", menuGroupFilter, filter);
        JToggleButton toolBarHighlightBorderRobertsFilter = componentCreator.createJToggleButton("apply highlight border filter using the Roberts operator", "src/main/resources/highlightRobertsFilter.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(highlightBorderRobertsFilter, toolBarHighlightBorderRobertsFilter);

        componentCreator.setAction(highlightBorderRobertsFilter, toolBarHighlightBorderRobertsFilter,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                         new ParametersFrame(highlightBorder, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

    }

}
