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
    private HighlightBorderRoberts highlightBorderRoberts = new HighlightBorderRoberts();
    private Dither dither1 = new Dither();
    private FloydSteinberg floydSteinberg1 = new FloydSteinberg();
    private Embossing embossing = new Embossing();
    private GaussianBlur gaussianBlur = new GaussianBlur();
    private Watercolorization watercolorization = new Watercolorization();
    private Rotate rotate = new Rotate();
    private BlackWhite blackWhite = new BlackWhite();
    private SharpeningFilter sharpeningFilter = new SharpeningFilter();
    private RussiaFilter russiaFilter = new RussiaFilter();

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
                         new ParametersFrame(highlightBorderRoberts, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemDither1 = componentCreator.createRadioButtonMenuItem("OD dither Anna", "OD dither Anna", menuGroupFilter, filter);
        JToggleButton toolBarDither1 = componentCreator.createJToggleButton("OD dither Anna", "src/main/resources/dither.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemDither1, toolBarInvertFilter);

        componentCreator.setAction(itemDither1, toolBarDither1,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(dither1, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemFloydSteinberg1 = componentCreator.createRadioButtonMenuItem("FS dither Anna", "FS dither Anna", menuGroupFilter, filter);
        JToggleButton toolBarFloydSteinberg1 = componentCreator.createJToggleButton("FS dither Anna", "src/main/resources/dither.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemFloydSteinberg1, toolBarFloydSteinberg1);

        componentCreator.setAction(itemFloydSteinberg1, toolBarFloydSteinberg1,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(floydSteinberg1, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemEmbossing = componentCreator.createRadioButtonMenuItem("Embossing", "Embossing", menuGroupFilter, filter);
        JToggleButton toolBarEmbossing = componentCreator.createJToggleButton("Embossing", "src/main/resources/embossing.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemEmbossing, toolBarEmbossing);

        componentCreator.setAction(itemEmbossing, toolBarEmbossing,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(embossing, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemBlur = componentCreator.createRadioButtonMenuItem("Gaussian blur", "Gaussian blur", menuGroupFilter, filter);
        JToggleButton toolBarBlur = componentCreator.createJToggleButton("Gaussian blur", "src/main/resources/blur.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemBlur, toolBarBlur);

        componentCreator.setAction(itemBlur, toolBarBlur,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(gaussianBlur, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemWatercolorization = componentCreator.createRadioButtonMenuItem("Water-colorization", "Water-colorization", menuGroupFilter, filter);
        JToggleButton toolBarWatercolorization = componentCreator.createJToggleButton("Water-colorization", "src/main/resources/watercolorization.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemWatercolorization, toolBarWatercolorization);

        componentCreator.setAction(itemWatercolorization, toolBarWatercolorization,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(watercolorization, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });

        JRadioButtonMenuItem itemRotate = componentCreator.createRadioButtonMenuItem("Rotate", "Rotate", menuGroupFilter, filter);
        JToggleButton toolBarRotate = componentCreator.createJToggleButton("Rotate", "src/main/resources/watercolorization.png", toolBarGroupFilter);
        componentCreator.syncToolBarAndMenu(itemRotate, toolBarRotate);

        componentCreator.setAction(itemRotate, toolBarRotate,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ParametersFrame(rotate, mainFrame.gPanel.getOriginalImage(), mainFrame.gPanel);
                    }
                });


        JMenuItem regime = componentCreator.createJMenuItem("Fit to screen", "Fit to screen", "src/main/resources/regime.png", file);
        JButton toolBarRegime = componentCreator.createJButton("Fit to screen", "src/main/resources/regime.png");

        componentCreator.setAction(regime, toolBarRegime,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainFrame.gPanel.fitToScreen();
                    }
                });

    }

}
