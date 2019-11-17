package main;

import gui.Panel;

import javax.swing.*;

public class Main {

    private static Panel panel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel = new Panel();
        panel.start();
    }

    public static Panel getPanel() {
        return panel;
    }

}
