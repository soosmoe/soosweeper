package gui.elements;

import gui.general.Maths;

import java.awt.*;
import java.util.ArrayList;

public class Fields extends Element {

    private int rows, cols;
    private ArrayList<Field> fields = new ArrayList<>();

    public Fields() {
        init(10, 10, 30);
    }

    public void init(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        fields.clear();

        for (int i = 0; i < cols; i++) for (int j = 0; j < rows; j++) {
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
        super.display(g);
        for (Field field : fields) {
            field.setPos(x+field.getI()*(w/cols), y+field.getJ()*(h/rows));
            field.setDim(w/cols, h/rows);
            field.display(g);
        }
    }

    public Field getField(int i, int j) {
        return fields.get(j*rows+i);
    }

}
