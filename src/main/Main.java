package main;

import conn.Connecter;
import gui.Panel;
import gui.elements.Elements;
import gui.elements.Fields;

import javax.swing.*;

public class Main {

    private static String userName;
    private static Panel panel;
    private static Connecter c = new Connecter("jdbc:mysql://mysql01.manitu.net","u38937", "V2ZDudBWdT69");

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestUserName();
        if (userName == null) return;
        if (JOptionPane.showConfirmDialog(null, "Neues Spiel?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            c.delete("Bombs");
            c.delete("Moves");
            Elements.getFields().init(15, 10, 30);
        } else {
            Elements.getFields().init();
        }
        panel = new Panel();
        panel.start();
    }

    public static Connecter getConnecter() {
        return c;
    }

    private static void requestUserName() {
        String option = JOptionPane.showInputDialog(null, "Username: ", "Soosweeper", JOptionPane.QUESTION_MESSAGE);
        if (option == null) return;
        if (!option.isEmpty()) userName = option;
        else requestUserName();
    }

    public static String getUserName() {
        return userName;
    }

    public static void gameOver() {
        Fields fields = Elements.getFields();
        JSpinner s1 = new JSpinner(new SpinnerNumberModel(fields.getCols(), 1, 420, 1));
        JSpinner s2 = new JSpinner(new SpinnerNumberModel(fields.getRows(), 1, 420, 1));
        JSpinner s3 = new JSpinner(new SpinnerNumberModel(fields.getMines(), 0, 176400, 1));
        Object[] options = {"Breite: ", s1, "Höhe: ", s2, "Minen: ", s3};

        int option = JOptionPane.showConfirmDialog(null, options, "Game Over", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.CANCEL_OPTION) System.exit(0);

        int cols = (Integer)s1.getValue();
        int rows = (Integer)s2.getValue();
        int mines = (Integer)s3.getValue();
        if (mines >= cols*rows) {
            JOptionPane.showMessageDialog(null, "Zu viele Minen!");
            gameOver();
        }
        getConnecter().delete("Bombs");
        getConnecter().delete("Moves");
        fields.init(rows, cols, mines);
}

}
