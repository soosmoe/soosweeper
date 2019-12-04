package main;

import gui.Panel;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static Panel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            panel = new Panel();
            panel.start();
        });
    }

    public static Panel getPanel() {
        return panel;
    }

}
