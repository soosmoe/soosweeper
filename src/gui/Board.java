package gui;

import conn.Connector;
import util.Maths;

import java.util.ArrayList;

public class Board {

    private int width, height, mines;
    private ArrayList<Field> fields = new ArrayList<>();

    public void init(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        Connector.clear("Board");
        Connector.clear("Fields");
        Connector.insert("Board", width, height);

        fields.clear();
        for (int j = 0; j < height; j++) for (int i = 0; i < width; i++) {
            fields.add(new Field(i, j));
        }

        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) indices.add(i);
        for (int i = 0; i < mines; i++) {
            int random = Maths.randomInt(0, indices.size()-1);
            int index = indices.get(random);
            fields.get(index).setMine(true);
            indices.remove(random);
        }

        for (Field field : fields) {
            Connector.insertField(width, field);
        }
    }

    public void init() {
        width = Integer.parseInt(Connector.read("Board").get(0));
        height = Integer.parseInt(Connector.read("Board").get(1));
        mines = 0;

        fields.clear();
        for (int j = 0; j < height; j++) for (int i = 0; i < width; i++) {
            Field field = Connector.readField(width, i, j);
            if (field.getMine()) mines++;
            fields.add(field);
        }

        for (Field field : fields) {
            if (field.getOpen()) field.open(width);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public Field getField(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return null;
        return fields.get(y * width + x);
    }

}