package gui;

import main.Main;

import javax.swing.*;

public class Interact {

    public static void newGameDialog() {
        Board board = Main.getPanel().getBoard();
        JSpinner s1 = new JSpinner(new SpinnerNumberModel(board.getWidth(), 2, 100, 1));
        JSpinner s2 = new JSpinner(new SpinnerNumberModel(board.getHeight(), 2, 100, 1));
        JSpinner s3 = new JSpinner(new SpinnerNumberModel(board.getMines(), 1, 10000, 1));
        Object[] options = {"Breite", s1, "Höhe", s2, "Minen", s3};

        int option = JOptionPane.showConfirmDialog(null, options, "New Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.CANCEL_OPTION) System.exit(0);

        int width = (Integer)s1.getValue();
        int height = (Integer)s2.getValue();
        int mines = (Integer)s3.getValue();
        if (mines >= width*height) {
            JOptionPane.showMessageDialog(null, new StringBuilder("Zu viele Minen! ").append(mines).append(" von ").append(width*height));
            newGameDialog();
        }

        board.init(width, height, mines);
    }

    public static void loadGame() {
        Board board = Main.getPanel().getBoard();

    }
}
