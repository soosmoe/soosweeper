package gui.elements;

import gui.GUI;
import gui.general.Colors;
import gui.general.Maths;
import gui.general.Strokes;
import main.Main;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Fields extends Element {

    private int rows, cols, mines;
    private ArrayList<Field> fields = new ArrayList<>();
    private Field hoverField;
    private double scale;

    private Line2D.Double line = new Line2D.Double();

    public Fields() {

    }

    public void init() {
        List<String> s = Main.getConnecter().read("Moves", 3);
        fields.clear();
        hoverField = null;
        this.rows = Integer.parseInt(s.get(0));
        this.cols = Integer.parseInt(s.get(1));
        System.out.println(rows + "   " + cols);
        this.mines = Main.getConnecter().read("Bombs", 2).size();
        w = cols * scale;
        h = rows * scale;

        for (int j = 0; j < rows; j++) for (int i = 0; i < cols; i++) {
            fields.add(new Field(i, j));
        }

        List<String> b = Main.getConnecter().read("Bombs", 2);
        for (int i = 0; i < b.size()-1; i += 2) {
            getField(Integer.parseInt(b.get(i)), Integer.parseInt(b.get(i+1))).setMine(true);
        }

        for (int i = 3; i < s.size()-2; i += 3) {
            if (Integer.parseInt(s.get(i+2)) == 1) {
                Field f = getField(Integer.parseInt(s.get(i)), Integer.parseInt(s.get(i+1)));
                if (f.getMine()) Main.gameOver();
                f.open();
            }
            if (Integer.parseInt(s.get(i+2)) == 3) {
                Field f = getField(Integer.parseInt(s.get(i)), Integer.parseInt(s.get(i+1)));
                f.setFlag(!f.getFlag());
            }
        }
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

        ArrayList<Double> availableIndices = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            availableIndices.add((double) i);
        }

        while (mines > 0) {
            double index = availableIndices.get(Maths.randomInt(0, availableIndices.size() - 1));
            Field field = fields.get((int) index);
            Main.getConnecter().write("Bombs", field.getI() + ", " + field.getJ());
            field.setMine(true);
            mines--;
            availableIndices.remove(index);
        }

        Main.getConnecter().write("Moves", rows + ", " + cols + ", 0");
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

        if (GUI.frameCount % 60 == 0) {
            List<String> s = Main.getConnecter().read("Moves", 3);
            for (int i = 3; i < s.size() - 2; i += 3) {
                if (Integer.parseInt(s.get(i + 2)) == 1) {
                    Field f = getField(Integer.parseInt(s.get(i)), Integer.parseInt(s.get(i + 1)));
                    if (f.getMine()) Main.gameOver();
                    f.open();
                }
                if (Integer.parseInt(s.get(i + 2)) == 3) {
                    Field f = getField(Integer.parseInt(s.get(i)), Integer.parseInt(s.get(i + 1)));
                    f.setFlag(!f.getFlag());
                }
            }
        }

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
