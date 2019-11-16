package gui.elements;

import java.awt.*;
import java.util.ArrayList;

public class Elements {

    private static ArrayList<Element> elements = new ArrayList<>();
    private static Fields fields;

    static {
        elements.add(fields = new Fields());
    }

    public static void display(Graphics2D g) {
        for (Element e : elements) e.display(g);
    }

    public static Fields getFields() {
        return fields;
    }

}
