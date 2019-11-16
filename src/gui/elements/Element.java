package gui.elements;

import gui.GUI;

import java.awt.*;

public class Element {

    protected double x, y, w, h;

    public void display(Graphics2D g) {
        if (hover()) onHover();
        if (click()) onClick();
    }

    protected boolean hover() {
        return GUI.mouseX >= x && GUI.mouseX <= x+w && GUI.mouseY >= y && GUI.mouseY <= y+h;
    }

    protected final boolean click() {
        return hover() && GUI.mouseClickCount == 1;
    }

    public void onHover() {}
    public void onClick() {}

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setDim(double w, double h) {
        this.w = w;
        this.h = h;
    }

}
