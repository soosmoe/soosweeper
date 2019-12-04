package game;

import gui.Render;

import java.awt.*;

public class Game {

    private Board board;
    private Render render;

    public Game() {
        board = new Board();
        board.init(100, 100, 200);
        render = new Render(board);
    }

    public void display(Graphics2D g) {
        render.update();
        render.board(g);
    }

}
