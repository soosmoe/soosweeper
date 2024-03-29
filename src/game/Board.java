package game;

import Connection.Connection;
import util.Maths;

import java.util.ArrayList;

public class Board {

    private int width, height, mines;
    private ArrayList<Field> fields = new ArrayList<>();
    public Connection con;

    public void init(int width, int height, int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
        this.con = new Connection();
        //Prepare fields
        System.out.println("a");
        System.out.print(con.getGameState());
        switch(con.getGameState()) {
            case 0:
                load();
                break;
            case 1:
                createNewGame();
                break;
            case 2:
                init(width, height, mines);
                break;
        }
    }

    public void createNewGame() {
        con.setGameState(2);
        fields.clear();
        con.clear();
        for (int y = 0; y < height; y++) for (int x = 0; x < width; x++) {
            fields.add(new Field(this, x, y));
            con.saveInit(fields.get(fields.size()-1));
        }

        //Distribute mines on board at random
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) indices.add(i);
        for (int i = 0; i < mines; i++) {
            int random = Maths.randomInt(0, indices.size()-1);
            int index = indices.get(random);
            fields.get(index).setMine(true);
            indices.remove(random);
            con.setMine(fields.get(index));
        }
        con.setGameState(0);
    }

    public void save() {
        //con.saveToDB(fields);
    }

    public void load() {
        this.fields = con.getFromDB(this);
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

    public Field getField(int index) {
        return fields.get(index);
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public Field getField(int x, int y) {
        if(x<0||y<0) return null;
        if(x>=width||y>=height)return null;
        int index = y * width + x;
        if (index >= width * height || index < 0||index >= fields.size()) return null;
        return fields.get(index);
    }

}
