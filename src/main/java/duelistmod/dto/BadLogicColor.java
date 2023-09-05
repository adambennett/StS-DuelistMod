package duelistmod.dto;

import com.badlogic.gdx.graphics.Color;
import duelistmod.enums.BadlogicColors;

public class BadLogicColor {

    private final Color color;

    public BadLogicColor(BadlogicColors enumCreation) {

        this.color = enumCreation.getColor();
    }

    public BadLogicColor(float r, float g, float b, float a) {

        this.color = new Color(r, g, b, a);
    }

    public Color getColor() {
        return color;
    }
}
