package gui.elements;

import gui.GUI;
import gui.general.Colors;
import gui.general.Fonts;
import gui.general.Maths;
import gui.general.Strokes;
import main.Main;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Field extends Element {

    private int i, j;
    private boolean mine, open, flag;
    private int mines;

    //Field
    private Rectangle2D.Double rect = new Rectangle2D.Double();
    private Line2D.Double highlightLine = new Line2D.Double();

    //Mine
    private Ellipse2D.Double ellipse = new Ellipse2D.Double();
    private Line2D.Double[] lines = new Line2D.Double[4];

    //Flag
    private Path2D.Double path = new Path2D.Double();
    private Line2D.Double flagLine = new Line2D.Double();

    public Field(int i, int j) {
        this.i = i;
        this.j = j;
        for (int a = 0; a < lines.length; a++) lines[a] = new Line2D.Double();
    }

    @Override
    public void onClick() {
        if (GUI.mouseButton == 1 && Elements.getFields().getHoverField() == this) open();
        if (GUI.mouseButton == 3) flag = !flag;
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

    public void open() {
        if (open) return;
        open = true;
        mines = getMines();
        if (!mine) {
            openNext(i-1, j);
            openNext(i+1, j);
            openNext(i, j-1);
            openNext(i, j+1);
        }
    }

    private void openNext(int I, int J) {
        Field field = Elements.getFields().getField(I, J);
        if (field != null) {
            if (!field.getMine()) field.open();
        }
    }

    private int getMines() {
        int mines = 0;
        for (int X = -1; X <= 1; X++) for (int Y = -1; Y <= 1; Y++) {
            Field field = Elements.getFields().getField(i+X, j+Y);
            if (field == null) continue;
            if (field.getMine()) mines++;
        }
        return mines;
    }

    @Override
    public void display(Graphics2D g) {
        super.display(g);
        rect.setRect(x, y, w, h);
        g.setColor(open ? Colors.FIELD_OPEN : Colors.FIELD_CLOSE);
        g.fill(rect);
        if (!open) {
            g.setStroke(Strokes.BORDER);
            g.setColor(Colors.FIELD_CLOSE.brighter());
            highlightLine.setLine(x + 2, y + 2, x + w - 2, y + 2);
            g.draw(highlightLine);
            highlightLine.setLine(x + 2, y + 2, x + 2, y + h - 2);
            g.draw(highlightLine);
            if (flag) {
                path.reset();
                path.moveTo(x+w*.45, y+h*.25);
                path.lineTo(x+w*.7, y+h*.375);
                path.lineTo(x+w*.45, y+h*.5);
                path.lineTo(x+w*.45, y+h*.25);
                g.setColor(Colors.FLAG);
                g.fill(path);
                g.setColor(Colors.MINE);
                g.setStroke(new BasicStroke((int)Math.round(w*.05), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(path);
                flagLine.setLine(x+w*.45, y+h*.25, x+w*.45, y+h*.75);
                g.draw(flagLine);
                flagLine.setLine(x+w*.3, y+h*.75, x+w*.7, y+h*.75);
                g.draw(flagLine);
            }
        }
        if (Elements.getFields().getHoverField() == this) {
            g.setColor(Colors.HOVER);
            g.fill(rect);
        }
        if (open && mine) {
            double radius = w*.3;
            ellipse.setFrame(x+w/2-radius, y+h/2-radius, radius*2, radius*2);
            g.setColor(Colors.MINE);
            g.fill(ellipse);
            g.setStroke(new BasicStroke((int)Math.round(radius/3d), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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
        if (open && !mine && mines > 0) {
            float fontSize = (float)(w/1.5d);
            g.setFont(Fonts.DEFAULT.deriveFont(fontSize));
            g.setColor(Colors.BORDER);
            g.drawString("" + mines, (float)(x+w/2-g.getFontMetrics().stringWidth("" + mines)/2d)+fontSize*.05f,
                    (float)(y+h-g.getFontMetrics().getHeight()*.3)+fontSize*.05f);
            g.setColor(Colors.getNumberColor(mines));
            g.drawString("" + mines, (float)(x+w/2-g.getFontMetrics().stringWidth("" + mines)/2d),
                    (float)(y+h-g.getFontMetrics().getHeight()*.3));
        }
    }

}
