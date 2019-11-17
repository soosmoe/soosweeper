package gui;

import util.Colors;
import util.Fonts;
import util.Maths;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Render {

    private BasicStroke defaultStroke = new BasicStroke(1);
    private Rectangle2D.Double rect = new Rectangle2D.Double();
    private Ellipse2D.Double ellipse = new Ellipse2D.Double();
    private Path2D.Double path = new Path2D.Double();
    private Line2D.Double line = new Line2D.Double();

    public void board(Graphics2D g, Board board, double xoff, double yoff, double scale) {
        Field hoverField = null;
        for (Field field : board.getFields()) {
            double x = field.getX()*scale+xoff;
            double y = field.getY()*scale+yoff;
            if (GUI.mouseX >= x && GUI.mouseX <= x+scale && GUI.mouseY >= y && GUI.mouseY <= y+scale) {
                hoverField = field;
            }
        }

        BasicStroke mineStroke = new BasicStroke((int)Math.round(scale*.1), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g.setStroke(defaultStroke);
        g.setFont(Fonts.DEFAULT.deriveFont((float)scale*.8f));

        for (int i = 0; i < board.getWidth(); i++) for (int j = 0; j < board.getHeight(); j++) {
            Field field = board.getField(i, j);
            double x = i*scale+xoff;
            double y = j*scale+yoff;
            rect.setRect(x, y, scale, scale);
            boolean hover = field == hoverField;

            if (field.getOpen()) {
                //field open
                g.setColor(Colors.FIELD_OPEN);
                g.fill(rect);
                if (field.getMine()) {
                    //mine circle
                    g.setColor(Colors.MINE);
                    ellipse.setFrame(x+scale*.25, y+scale*.25, scale*.5, scale*.5);
                    g.fill(ellipse);
                    g.setStroke(mineStroke);
                    //mine spikes
                    for (int I = 0, numOfLines = 4; I < numOfLines; I++) {
                        double r = scale*.275;
                        double rot = Maths.map(I, 0, numOfLines, 0, Math.PI);
                        line.setLine(x+scale/2+Math.sin(rot)*r, y+scale/2+Math.cos(rot)*r,
                                     x+scale/2+Math.sin(rot+Math.PI)*r, y+scale/2+Math.cos(rot+Math.PI)*r);
                        g.draw(line);
                    }
                } else if (field.getMines() > 0) {
                    //neighbor mine count number
                    g.setColor(Colors.getNumberColor(field.getMines()));
                    String n = "" + field.getMines();
                    g.drawString(n, (float)(x+scale*.5f-g.getFontMetrics().stringWidth(n)*.5f),
                            (float)(y+scale-g.getFontMetrics().getHeight()*.2f));
                }
            } else {
                //field close
                g.setStroke(defaultStroke);
                g.setColor(Colors.FIELD_CLOSE);
                g.fill(rect);
                g.setColor(Colors.FIELD_CLOSE.brighter());
                line.setLine(x+2, y+2, x+scale-2, y+2);
                g.draw(line);
                line.setLine(x+2, y+2, x+2, y+scale-2);
                g.draw(line);
                if (field.getFlag()) {
                    //Flag
                    path.reset();
                    path.moveTo(x+scale*.45, y+scale*.25);
                    path.lineTo(x+scale*.7, y+scale*.375);
                    path.lineTo(x+scale*.45, y+scale*.5);
                    path.lineTo(x+scale*.45, y+scale*.25);
                    g.setColor(Colors.FLAG);
                    g.fill(path);
                    g.setColor(Color.black);
                    g.draw(path);
                    line.setLine(x+scale*.45, y+scale*.25, x+scale*.45, y+scale*.75);
                    g.draw(line);
                    line.setLine(x+scale*.3, y+scale*.75, x+scale*.7, y+scale*.75);
                    g.draw(line);
                }
            }
            if (hover) {
                rect.setRect(x, y, scale, scale);
                g.setColor(Colors.HOVER);
                g.fill(rect);
                if (GUI.mouseClickCount == 1) {
                    if (GUI.mouseButton == 1 && !field.getFlag()) {
                        field.open(board.getWidth(), true);
                    }
                    if (GUI.mouseButton == 3) {
                        field.setFlag(board.getWidth(), !field.getFlag());
                    }
                }
            }
        }
        //grid
        g.setStroke(defaultStroke);
        g.setColor(Colors.BORDER);
        for (int i = 0; i < board.getWidth()+1; i++) {
            double x = i*scale+xoff;
            line.setLine(x, yoff, x, yoff+board.getHeight()*scale);
            g.draw(line);
        }
        for (int j = 0; j < board.getHeight()+1; j++) {
            double y = j*scale+yoff;
            line.setLine(xoff, y, xoff+board.getWidth()*scale, y);
            g.draw(line);
        }
    }
}
