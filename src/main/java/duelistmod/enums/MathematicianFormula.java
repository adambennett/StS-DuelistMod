package duelistmod.enums;

import duelistmod.dto.TwoNums;
import duelistmod.helpers.Util;
import duelistmod.interfaces.MathematicianFunction;

import java.util.concurrent.ThreadLocalRandom;

public enum MathematicianFormula {
    X_SQUARED((x, y, m) -> (int)Math.pow(x, 2), "X^2", true, false),
    TWO_X_SQUARED((x, y, m) -> (int)Math.pow(x, 2) * 2, "2(X^2)", true, false),
    X_PLUS_Y((x, y, m) -> (x + y),"X + Y", true, true),
    X_PLUS_Y_TIMES_M((x, y, m) -> (x + y) * m,"(X + Y) x !M!", true, true),
    X_PLUS_Y_TIMES_2((x, y, m) -> (x + y) * 2,"2(X + Y)", true, true),
    X_PLUS_Y_TIMES_3((x, y, m) -> (x + y) * 3,"3(X + Y)", true, true),
    X_TIMES_Y((x, y, m) -> x * y,"X x Y", true, true),
    X_TIMES_Y_TIMES_M((x, y, m) -> x * y * m,"X x Y x !M!", true, true),
    X_TIMES_Y_PLUS_M((x, y, m) -> (x * y) + m,"(X x Y) + !M!", true, true),
    X_PLUS_M((x, y, m) -> x + m, "X + !M!", true, false),
    Y_PLUS_M((x, y, m) -> y + m, "Y + !M!", false, true),
    TWO_Y_SQUARED((x, y, m) -> (int)Math.pow(y * 2, 2), "2(Y^2)", false, true),
    Y_SQUARED((x, y, m) -> (int)Math.pow(y, 2), "Y^2", false, true),
    Y_CUBED((x, y, m) -> (int)Math.pow(y, 3), "Y^3", false, true),
    TWO_X_PLUS_3_Y((x, y, m) -> (2 * x) + (3 * y), "(2X) + (3Y)", true, true),
    TWO_Y_PLUS_3_X((x, y, m) -> (2 * y) + (3 * x), "(2Y) + (3X)", true, true),
    THREE_Y_PLUS_3_X((x, y, m) -> (3 * y) + (3 * x), "(3Y) + (3X)", true, true),
    X_PLUS_Y_PLUS_M((x, y, m) -> x + y + m, "X + Y + !M!", true, true),
    X_CUBED((x, y, m) -> (int)Math.pow(x, 3), "X^3", true, false),
    FOUR_X((x, y, m) -> x * 4, "4X", true, false),
    FOUR_Y((x, y, m) -> y * 4, "4Y", false, true),
    SIX_M_PLUS_3_Y((x, y, m) -> (6 * m) + (3 * y), "(6 x !M!) + (3Y)", false, true),
    SIX_M_PLUS_3_X((x, y, m) -> (6 * m) + (3 * x), "(6 x !M!) + (3X)", true, false),
    FIVE_X((x, y, m) -> x * 5, "5X", true, false),
    RANDOM_BETWEEN_X_Y((x, y, m) -> {
        TwoNums nums = Util.getLowHigh(x, y);
        if (nums.low() == 0 && nums.high() == 0) {
            return 0;
        }
        return ThreadLocalRandom.current().nextInt(nums.low(), nums.high() + 1);
    }, "", true, true);
    
    private final MathematicianFunction function;
    private final String formulaDescription;
    private final boolean isX;
    private final boolean isY;

    MathematicianFormula(MathematicianFunction function, String formulaDescription, boolean isX, boolean isY) {
        this.function = function;
        this.formulaDescription = formulaDescription;
        this.isX = isX;
        this.isY = isY;
    }
    
    public int getDamage(int x, int y, int m) {
        return this.function != null ? this.function.compute(x, y, m) : 0;
    }

    public boolean isX() { return this.isX; }
    public boolean isY() { return this.isY; }
    
    public String getDescription(int x, int y, String defaultDescription, boolean upgraded) {
        String cardDesc = "Spellcaster NL Tribute Y NL ";
        String exhaust = " NL Exhaust.";
        if (this == RANDOM_BETWEEN_X_Y) {
            boolean xLow = true;
            TwoNums nums = Util.getLowHigh(x, y);
            if (nums.low() != x) {
                xLow = false;
            }
            String base = "Deal a random amount of damage between ";
            String output = xLow
                    ? base + "X and Y"
                    : base + "Y and X";
            return upgraded ? output : output + exhaust;
        }
        String output = this.formulaDescription != null
                ? "Deal " + this.formulaDescription + " damage." + this.getDamageDescription()
                : defaultDescription;
        output = cardDesc + output;
        return upgraded ? output : output + exhaust;
    }
    
    private String getDamageDescription() {
        if (this == RANDOM_BETWEEN_X_Y) {
            return "";
        }
        return " NL (Deal !D! damage)";
    }
    
}
