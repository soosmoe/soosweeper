package gui;

import conn.Connector;
import main.Main;

public class Field {

    private int x, y;
    private boolean mine, open, flag;
    private int mines;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void open(int width, boolean byPlayer) {
        open = true;
        if (byPlayer) Connector.update("Fields", y*width+x, "OpenState", true);

        mines = countSurroundingMines();
        if (mines == 0 && byPlayer) {
            for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) {
                Field field = Main.getPanel().getBoard().getField(x+i, y+j);
                if (field != null) if (!field.getMine()) {
                    if(!field.open) field.open(width, byPlayer);
                }
            }
        }
    }

    private int countSurroundingMines() {
        int count = 0;
        for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) {
            Field field = Main.getPanel().getBoard().getField(x+i, y+j);
            if (field != null && field.getMine()) count++;
        }
        return count;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(int width, boolean flag) {
        this.flag = flag;
    }

    public int getMines() {
        return mines;
    }

}
