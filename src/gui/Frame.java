package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame {

    private String osName = System.getProperty("os.name").toLowerCase();
    
    public Frame(Panel panel) {
        super("Soosweeper");
        System.setProperty("apple.awt.application.name", getName());

        setResizable(true);
        setSize(GUI.width, GUI.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png")));
        setLocationRelativeTo(null);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                GUI.width = e.getComponent().getWidth() - (osName.contains("windows") ? 14 : 0);
                GUI.height = e.getComponent().getHeight() - (osName.contains("mac") ? 19 : 37);
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                GUI.mousePressed = true;
                GUI.mouseButton = e.getButton();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                GUI.mousePressed = false;
                GUI.mouseClickCount = 0;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            private void updateMouseVariables() {
                GUI.mouseX = MouseInfo.getPointerInfo().getLocation().getX()-GUI.displayX;
                GUI.mouseY = MouseInfo.getPointerInfo().getLocation().getY()-GUI.displayY;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                updateMouseVariables();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateMouseVariables();
            }
        });

        add(panel);
        pack();
        setVisible(true);
    }

}
