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

    public Field(int x, int y, boolean mine, boolean open, boolean flag, Board board) {
        this.x = x;
        this.y = y;
        this.mine = mine;
        this.open = open;
        this.flag = flag;
        this.board = board;
        if(open) {
            int count = 0;
            for (int i = -1; i <= 1; i++) for (int j = -1; j <= 1; j++) {
                Field field = board.getField(x+i, y+j);
                if (field != null && field.getMine()) count++;
            }
            this.mines = count;
        }
    }

    public void open() {
        if (open||flag) return;
        open = true;
        if(mine)board.con.setGameState(1);
        board.con.saveToDB(this);
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
                Field field = board.getField(x + i, y + j);
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
        board.con.saveToDB(this);
    }

    public int getMines() {
        return mines;
    }

}
