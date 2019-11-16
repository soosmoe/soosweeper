package gui;

import gui.elements.Elements;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private Frame frame;
    private Elements elements;

    public Panel() {
        frame = new Frame(this);
        elements = new Elements();
    }

    @Override
    public void paint(Graphics G) {
        super.paint(G);
        Graphics2D g = (Graphics2D) G;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g.setBackground(Color.lightGray);
        g.clearRect(0, 0, GUI.width, GUI.height);

        if (elements != null) elements.display(g);
    }

    public void start() {
        while (isVisible()) {
            long startTime = System.currentTimeMillis();

            GUI.displayX = getLocationOnScreen().getX();
            GUI.displayY = getLocationOnScreen().getY();
            repaint();
            if (GUI.mousePressed) GUI.mouseClickCount++;
            GUI.frameCount++;

            if (!frame.isFocused()) {
                GUI.mousePressed = false;
                GUI.mouseClickCount = 0;
            }

            long takenTime = System.currentTimeMillis() - startTime;
            long sleepTime = (1000/60 - takenTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.width, GUI.height);
    }

}
