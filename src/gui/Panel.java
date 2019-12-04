package gui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel {

    private Frame frame = new Frame(this);
    private Game game = new Game();

    @Override
    protected void paintComponent(Graphics G) {
        super.paintComponent(G);
        Graphics2D g = (Graphics2D) G;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g.setBackground(Color.lightGray);
        g.clearRect(0, 0, GUI.width, GUI.height);

        game.display(g);
    }

    public void start() {
        new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.displayX = getLocationOnScreen().getX();
                GUI.displayY = getLocationOnScreen().getY();
                repaint();
                if (GUI.mousePressed) GUI.mouseClickCount++;
                GUI.frameCount++;
                GUI.mouseScroll *= 0.75;
                if (Math.abs(GUI.mouseScroll) < 0.001) GUI.mouseScroll = 0;

                if (!frame.isFocused()) {
                    GUI.mousePressed = false;
                    GUI.mouseClickCount = 0;
                }
            }
        }).start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.width, GUI.height);
    }

}
