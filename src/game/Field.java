package game;

public class Field {

    private Board board;
    private int x, y;
    private boolean open, mine, flag;
    private int mines;

    public Field(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }

    public void open() {
        if (open) return;
        open = true;

        //Calculate number of surrounding mines
        int count = 0;
        for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) {
            Field field = board.getField(x+i, y+j);
            if (field != null && field.getMine()) count++;
        }
        mines = count;

        //Open surrounding fields if possible
        if (mines == 0) {
            for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) {
                Field field = board.getField(x+i, y+j);
                if (field != null && !field.getMine() && !field.getOpen()) {
                    field.open();
                }
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getMines() {
        return mines;
    }

}