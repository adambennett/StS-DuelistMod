package duelistmod.enums;

import com.badlogic.gdx.graphics.Color;

public enum BadlogicColors {

    BLACK(Color.BLACK),
    BLUE(Color.BLUE),
    BROWN(Color.BROWN),
    CHARTREUSE(Color.CHARTREUSE),
    CLEAR(Color.CLEAR),
    CORAL(Color.CORAL),
    CYAN(Color.CYAN),
    DARK_GRAY(Color.DARK_GRAY),
    FIREBRICK(Color.FIREBRICK),
    FOREST(Color.FOREST),
    GOLD(Color.GOLD),
    GOLDENROD(Color.GOLDENROD),
    GRAY(Color.GRAY),
    GREEN(Color.GREEN),
    LIGHT_GRAY(Color.LIGHT_GRAY),
    LIME(Color.LIME),
    MAGENTA(Color.MAGENTA),
    MAROON(Color.MAROON),
    NAVY(Color.NAVY),
    OLIVE(Color.OLIVE),
    ORANGE(Color.ORANGE),
    PINK(Color.PINK),
    PURPLE(Color.PURPLE),
    RED(Color.RED),
    ROYAL(Color.ROYAL),
    SALMON(Color.SALMON),
    SCARLET(Color.SCARLET),
    SKY(Color.SKY),
    SLATE(Color.SLATE),
    TAN(Color.TAN),
    TEAL(Color.TEAL),
    VIOLET(Color.VIOLET),
    WHITE(Color.WHITE),
    YELLOW(Color.YELLOW);
    //CUSTOM();

    private final Color color;

    BadlogicColors() {
        this(null);
    }

    BadlogicColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
