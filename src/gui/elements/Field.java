package gui.elements;

import gui.general.Colors;
import gui.general.Maths;
import gui.general.Strokes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Field extends Element {

    private int i, j;
    private boolean mine, open;

    private Rectangle2D.Double rect = new Rectangle2D.Double();
    private Ellipse2D.Double ellipse = new Ellipse2D.Double();
    private Line2D.Double[] lines = new Line2D.Double[4];

    public Field(int i, int j) {
        this.i = i;
        this.j = j;
        for (int a = 0; a < lines.length; a++) lines[a] = new Line2D.Double();
    }

    @Override
    public void onClick() {
        open = true;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean getMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public void display(Graphics2D g) {
        super.display(g);
        rect.setRect(x, y, w, h);
        g.setColor(open ? Colors.FIELD_OPEN : Colors.FIELD_CLOSE);
        g.fill(rect);
        if (hover()) {
            g.setColor(Colors.HOVER);
            g.fill(rect);
        }
        if (open && mine) {
            double radius = w*.33333;
            ellipse.setFrame(x+w/2-radius, y+h/2-radius, radius*2, radius*2);
            g.setColor(Colors.MINE);
            g.fill(ellipse);
            g.setStroke(Strokes.MINE);
            for (int i = 0; i < lines.length; i++) {
                Line2D.Double line = lines[i];
                double rotation = Maths.map(i, 0, lines.length, 0, Math.PI);
                double x1 = x + w/2 + Math.sin(rotation) * w * .34;
                double y1 = y + h/2 + Math.cos(rotation) * h * .34;
                double x2 = x + w/2 + Math.sin(rotation+Math.PI) * w * .34;
                double y2 = y + h/2 + Math.cos(rotation+Math.PI) * h * .34;
                line.setLine(x1, y1, x2, y2);
                g.draw(line);
            }
        }
        g.setColor(Colors.BORDER);
        g.setStroke(Strokes.BORDER);
        g.draw(rect);
    }
}
