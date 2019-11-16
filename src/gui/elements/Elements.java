package gui.elements;

import gui.GUI;

import java.awt.*;
import java.util.ArrayList;

public class Elements {

    private ArrayList<Element> elements = new ArrayList<>();

    private Fields fields;

    public Elements() {
        elements.add(fields = new Fields());
    }

    public void display(Graphics2D g) {
        boolean largerWidth = GUI.width > GUI.height;
        int fieldsSize = largerWidth ? GUI.height : GUI.width;
        fields.setPos(largerWidth ? GUI.width/2d-fieldsSize/2d : 0, largerWidth ? 0 : GUI.height/2d-fieldsSize/2d);
        fields.setDim(fieldsSize, fieldsSize);

        for (Element e : elements) e.display(g);
    }

}
