package ru.nsu.fit.menuCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuComponentCreator {
    JToolBar toolBar;
    JMenuBar menuBar;

    MenuComponentCreator(JMenuBar menuBar, JToolBar toolBar){
        this.menuBar = menuBar;
        this.toolBar = toolBar;
    }
    // menu

    // добавление пунктов главного меню
    public JMenu createJMenu(String title){
        JMenu menu = new JMenu(title);
        menuBar.add(menu);
        return menu;
    }

    // добавление остаьных пунктов меню
    public JMenuItem createJMenuItem(String title, String tooltip, String icon, ActionListener action, JMenu menu){
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setToolTipText(tooltip);
        menuItem.setIcon(new ImageIcon(icon));
        menuItem.addActionListener(action);
        menu.add(menuItem);
        return menuItem;
    }

    // without action
    // нужно использовать когда для пункта меню есть соответствующий пункт toolBar
    // ActionListener добавляется сразу пункту меню и кнопке  toolBar в отдельном конструкторе
    public JMenuItem createJMenuItem(String title, String tooltip, String icon, JMenu menu){
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setToolTipText(tooltip);
        menuItem.setIcon(new ImageIcon(icon));
        menu.add(menuItem);
        return menuItem;
    }

    // создание обычного меню RadioButton, должно быть безз иконки, иначе не видно какой пункт меню выбран
    public JRadioButtonMenuItem createRadioButtonMenuItem(String title, String tooltip, ButtonGroup group, JMenu menu){
        JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem(title, false);
        jRadioButtonMenuItem.setToolTipText(tooltip);
        group.add(jRadioButtonMenuItem);
        menu.add(jRadioButtonMenuItem);
        return jRadioButtonMenuItem;
    }

    // toolbar
    public JButton createJButton(String tooltip, String icon){
        JButton toolBarButton = new JButton();
        toolBarButton.setToolTipText(tooltip);
        toolBarButton.setIcon(new ImageIcon(icon));
        toolBar.add(toolBarButton);
        return toolBarButton;
    }

    // залипающая кнопка
    public JToggleButton createJToggleButton(String tooltip, String icon, ButtonGroup group){
        JToggleButton toolBarToggleButton = new JToggleButton();
        toolBarToggleButton.setToolTipText(tooltip);
        toolBarToggleButton.setIcon(new ImageIcon(icon));
        group.add(toolBarToggleButton);
        toolBar.add(toolBarToggleButton);
        return toolBarToggleButton;
    }

    // связь между меню и toolBar (при выборе пункта из главного меню, соответстующая кнопка toolBar также выбрирается)
    public void syncToolBarAndMenu(JRadioButtonMenuItem menu, JToggleButton button){
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setSelected(menu.isSelected());
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setSelected(button.isSelected());
            }
        });

    }

    // в конструкторах JRadioButtonMenuItem, JToggleButton не было установдения слушателя тк
    // его можно добавлять сразу к пункту меню и кнопке toolBar потому что они связаны, лучше рарботать с ними в одном месте
    public void setAction(JRadioButtonMenuItem menu, JToggleButton button, ActionListener action){
        menu.addActionListener(action);
        button.addActionListener(action);
    }

    public void setAction(JMenuItem menu, JButton button, ActionListener action){
        menu.addActionListener(action);
        button.addActionListener(action);
    }






}
