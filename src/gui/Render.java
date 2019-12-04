package gui;

import game.Board;
import game.Field;
import util.Colors;
import util.Fonts;
import util.Maths;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Render {

    private Board board;

    private double previousMouseX, previousMouseY;
    private double scale, xOff, yOff;

    private BasicStroke stroke = new BasicStroke(1);
    private Rectangle2D.Double rect = new Rectangle2D.Double();
    private Ellipse2D.Double ellipse = new Ellipse2D.Double();
    private Path2D.Double path = new Path2D.Double();
    private Line2D.Double line = new Line2D.Double();

    public Render(Board board) {
        this.board = board;
        scale = 40;
        xOff = GUI.width / 2d - board.getWidth() * scale / 2d;
        yOff = GUI.height / 2d - board.getHeight() * scale / 2d;
    }

    public void update() {
        //System.out.println(xOff + " " + yOff);
        if (GUI.mousePressed && GUI.mouseButton == 2) {
            xOff += GUI.mouseX - previousMouseX;
            yOff += GUI.mouseY - previousMouseY;
        }
        if (GUI.keyPressed && GUI.key == ' ') {
            xOff = GUI.width / 2d - board.getWidth() * scale / 2d;
            yOff = GUI.height / 2d - board.getHeight() * scale / 2d;
        }
        scale = Maths.bound(scale - scale * GUI.mouseScroll * 0.005, 4, 80);

        previousMouseX = GUI.mouseX;
        previousMouseY = GUI.mouseY;
        if(GUI.mousePressed)board.createNewGame();
    }
    private boolean finished(){
        for(Field f:board.getFields()) {
            if(f.getOpen()==false&&f.getFlag()==false)return false;
            if(f.getFlag()&&f.getMine()==false)return false;
        }
        return true;
    }
    public void board(Graphics2D g) {
        //Use mouse coordinates to find hover field
        board.load();
        if(finished()) {
            System.out.println("win");
            board.createNewGame();
        }

        Field hoverField = null;
        for (int x = 0; x < board.getWidth(); x++) for (int y = 0; y < board.getHeight(); y++) {
            double X = x * scale + xOff;
            double Y = y * scale + yOff;
            if (GUI.mouseX >= X && GUI.mouseX <= X + scale && GUI.mouseY >= Y && GUI.mouseY <= Y + scale) {
                hoverField = board.getField(x, y);
            }
        }

        BasicStroke mineStroke = new BasicStroke((int)Math.round(scale*.1), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g.setStroke(stroke);
        g.setFont(Fonts.DEFAULT.deriveFont((float)scale*.8f));

        for (int x = 0; x < board.getWidth(); x++) for (int y = 0; y < board.getHeight(); y++) {
            double X = x * scale + xOff;
            double Y = y * scale + yOff;
            Field field = board.getField(x, y);
            rect.setRect(X, Y, scale, scale);

            if (field.getOpen()) {
                //field open
                g.setColor(Colors.FIELD_OPEN);
                g.fill(rect);
                if (field.getMine()) {
                    //mine circle
                    g.setColor(Colors.MINE);
                    ellipse.setFrame(X+scale*.25, Y+scale*.25, scale*.5, scale*.5);
                    g.fill(ellipse);
                    g.setStroke(mineStroke);
                    //mine spikes
                    for (int i = 0, numOfLines = 4; i < numOfLines; i++) {
                        double r = scale*.275;
                        double rot = Maths.map(i, 0, numOfLines, 0, Math.PI);
                        line.setLine(X+scale/2+Math.sin(rot)*r, Y+scale/2+Math.cos(rot)*r,
                                X+scale/2+Math.sin(rot+Math.PI)*r, Y+scale/2+Math.cos(rot+Math.PI)*r);
                        g.draw(line);
                    }
                } else if (field.getMines() > 0) {
                    //number of surrounding mines
                    g.setColor(Colors.getNumberColor(field.getMines()));
                    String n = "" + field.getMines();
                    g.drawString(n, (float)(X+scale*.5f-g.getFontMetrics().stringWidth(n)*.5f),
                            (float)(Y+scale-g.getFontMetrics().getHeight()*.2f));
                }
            } else {
                //field close
                g.setStroke(stroke);
                g.setColor(Colors.FIELD_CLOSE);
                g.fill(rect);
                g.setColor(Colors.FIELD_CLOSE.brighter());
                line.setLine(X+2, Y+2, X+scale-2, Y+2);
                g.draw(line);
                line.setLine(X+2, Y+2, X+2, Y+scale-2);
                g.draw(line);
                if (field.getFlag()) {
                    //flag
                    path.reset();
                    path.moveTo(X+scale*.45, Y+scale*.25);
                    path.lineTo(X+scale*.7, Y+scale*.375);
                    path.lineTo(X+scale*.45, Y+scale*.5);
                    path.lineTo(X+scale*.45, Y+scale*.25);
                    g.setColor(Colors.FLAG);
                    g.fill(path);
                    g.setColor(Color.black);
                    g.draw(path);
                    line.setLine(X+scale*.45, Y+scale*.25, X+scale*.45, Y+scale*.75);
                    g.draw(line);
                    line.setLine(X+scale*.3, Y+scale*.75, X+scale*.7, Y+scale*.75);
                    g.draw(line);
                }
            }
            if (field == hoverField) {
                //field hover highlight
                rect.setRect(X, Y, scale, scale);
                g.setColor(Colors.HOVER);
                g.fill(rect);
                //mouse interaction
                if (GUI.mouseClickCount == 1) {
                    //left click
                    if (GUI.mouseButton == 1 && !field.getFlag()) {
                        field.open();
                    }
                    //right click
                    if (GUI.mouseButton == 3) {
                        field.setFlag(!field.getFlag());
                    }
                }
            }
        }
        //grid
        g.setStroke(stroke);
        g.setColor(Colors.BORDER);
        for (int x = 0; x < board.getWidth()+1; x++) {
            double X = x * scale + xOff;
            line.setLine(X, yOff, X, yOff + board.getHeight() * scale);
            g.draw(line);
        }
        for (int y = 0; y < board.getHeight()+1; y++) {
            double Y = y * scale + yOff;
            line.setLine(xOff, Y, xOff + board.getWidth() * scale, Y);
            g.draw(line);
        }

    }

}
