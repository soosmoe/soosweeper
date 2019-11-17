package gui;

import util.Maths;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private Frame frame;
    private Render render;
    private Board board;

    private double xoff, yoff, scale;

    public Panel() {
        frame = new Frame(this);
        render = new Render();
        board = new Board();
        scale = 40;
    }

    public Board getBoard() {
        return board;
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

        if (board != null && board.getFields() != null) if (board.getFields().size() > 0) {
            render.board(g, board, xoff+GUI.width/2d-board.getWidth()*scale/2d,
                    yoff+GUI.height/2d-board.getHeight()*scale/2d, scale);
        }

        if (GUI.mousePressed && GUI.mouseButton == 2) {
            xoff -= .02 * (GUI.mouseX - GUI.width/2d);
            yoff -= .02 * (GUI.mouseY - GUI.height/2d);
        }
        if (GUI.keyPressed && GUI.key == ' ') {
            xoff = 0;
            yoff = 0;
        }

        scale -= scale * GUI.mouseScroll/200d;
        scale = Maths.bound(scale, 10, 80);
    }

    public void start() {
        //board.init(10, 10, 20);
        board.init();

        while (isVisible()) {
            long startTime = System.currentTimeMillis();

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
