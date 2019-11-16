package gui.elements;

import gui.GUI;
import gui.general.Colors;
import gui.general.Maths;
import gui.general.Strokes;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Fields extends Element {

    private int rows, cols, mines;
    private ArrayList<Field> fields = new ArrayList<>();
    private Field hoverField;
    private double scale;

    private Line2D.Double line = new Line2D.Double();

    public Fields() {
        init(10, 15, 30);
    }

    public void init(int rows, int cols, int mines) {
        fields.clear();
        hoverField = null;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        w = cols * scale;
        h = rows * scale;

        for (int j = 0; j < rows; j++) for (int i = 0; i < cols; i++) {
            fields.add(new Field(i, j));
        }

        while (mines > 0) {
            int i = Maths.randomInt(0, cols-1);
            int j = Maths.randomInt(0, rows-1);
            Field field = getField(i, j);
            if (!field.getMine()) {
                field.setMine(true);
                mines--;
            }
        }
    }

    @Override
    public void display(Graphics2D g) {
        scale -= scale * GUI.mouseScroll/200d;
        scale = Maths.bound(scale, 0.75, 1.5);
        w = 40*scale*cols;
        h = 40*scale*rows;
        setPos(GUI.width/2d-w/2d, GUI.height/2d-h/2d);
        super.display(g);
        if (!hover()) hoverField = null;
        for (Field field : fields) if (field.hover()) hoverField = field;
        for (int i = fields.size()-1; i >= 0; i--) {
            Field field = fields.get(i);
            field.setPos(x+field.getI()*(w/cols), y+field.getJ()*(h/rows));
            field.setDim(w/cols, h/rows);
            field.display(g);
        }

        g.setStroke(Strokes.BORDER);
        g.setColor(Colors.BORDER);
        for (int X = 0; X < cols+1; X++) {
            line.setLine(x+X*w/cols, y, x+X*w/cols, y+h);
            g.draw(line);
        }
        for (int Y = 0; Y < rows+1; Y++) {
            line.setLine(x, y+Y*h/rows, x+w, y+Y*h/rows);
            g.draw(line);
        }
    }

    public Field getField(int i, int j) {
        if (i < 0 || i >= cols || j < 0 || j >= rows) return null;
        return fields.get(j*cols+i);
    }

    public Field getHoverField() {
        return hoverField;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

}
