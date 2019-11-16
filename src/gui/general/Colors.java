package gui.general;

import java.awt.*;

public class Colors {

    public static final Color FIELD_CLOSE, FIELD_OPEN, HOVER, BORDER, MINE, FLAG;
    private static final Color[] numberColors = new Color[8];

    static {
        FIELD_CLOSE = new Color(70, 100, 210);
        FIELD_OPEN = new Color(128, 128, 128);
        HOVER = new Color(255, 255, 255, 32);
        BORDER = new Color(64, 64, 64);
        MINE = new Color(20, 20, 20);
        FLAG = new Color(210, 50, 10);

        numberColors[0] = new Color(0, 0, 255);
        numberColors[1] = new Color(0, 128, 0);
        numberColors[2] = new Color(255, 0, 0);
        numberColors[3] = new Color(0, 0, 128);
        numberColors[4] = new Color(128, 0, 0);
        numberColors[5] = new Color(0, 128, 128);
        numberColors[6] = new Color(0, 0, 0);
        numberColors[7] = new Color(64, 64, 64);
    }

    public static Color getNumberColor(int number) {
        if (number < 1 || number > 8) return Color.black;
        return numberColors[number-1];
    }

}
