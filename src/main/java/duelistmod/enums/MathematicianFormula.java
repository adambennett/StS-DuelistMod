package duelistmod.enums;

import duelistmod.dto.TwoNums;
import duelistmod.interfaces.MathematicianFunction;

import java.util.concurrent.ThreadLocalRandom;

public enum MathematicianFormula {
    X_SQUARED((x, y, m) -> (int)Math.pow(x, 2), "X^2", true, false),
    TWO_X_SQUARED((x, y, m) -> (int)Math.pow(x, 2) * 2, "2(X^2)", true, false),
    X_PLUS_Y((x, y, m) -> (x + y),"X + Y", true, true),
    X_PLUS_Y_TIMES_M((x, y, m) -> (x + y) * m,"(X + Y) * !M!", true, true),
    X_PLUS_Y_TIMES_2((x, y, m) -> (x + y) * 2,"2(X + Y)", true, true),
    X_PLUS_Y_TIMES_3((x, y, m) -> (x + y) * 3,"3(X + Y)", true, true),
    X_TIMES_Y((x, y, m) -> x * y,"X * Y", true, true),
    X_TIMES_Y_TIMES_M((x, y, m) -> x * y * m,"X * Y * !M!", true, true),
    X_TIMES_Y_PLUS_M((x, y, m) -> (x * y) + m,"(X * Y) + !M!", true, true),
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
    SIX_M_PLUS_3_Y((x, y, m) -> (6 * m) + (3 * y), "(6 * !M!) + (3Y)", false, true),
    SIX_M_PLUS_3_X((x, y, m) -> (6 * m) + (3 * x), "(6 * !M!) + (3X)", true, false),
    FIVE_X((x, y, m) -> x * 5, "5X", true, false),
    RANDOM_BETWEEN_X_Y((x, y, m) -> {
        TwoNums nums = getLowHigh(x, y);
        if (nums.low() == 0 && nums.high() == 0) {
            return 0;
        }
        return ThreadLocalRandom.current().nextInt(nums.low(), nums.high() + 1);
    }, "", true, true);
    
    private final MathematicianFunction function;
    private final String formulaDescription;
    private final boolean isX;
    private final boolean isY;

    private static TwoNums getLowHigh(int x, int y) {
        int low = x;
        int high = y;
        if (low > high) {
            int t = low;
            low = high;
            high = t;
        }
        if (low < 0) {
            low = 0;
        }
        if (high <= 0) {
            return new TwoNums(0, 0);
        }
        return new TwoNums(low, high);
    }
    
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
    
    public String getDescription(int x, int y, int m, String defaultDescription) {
        if (this == RANDOM_BETWEEN_X_Y) {
            boolean xLow = true;
            TwoNums nums = getLowHigh(x, y);
            if (nums.low() != x) {
                xLow = false;
            }
            String base = "Deal a random amount of damage between ";
            return xLow
                    ? base + "X and Y"
                    : base + "Y and X";
        }
        return this.formulaDescription != null
                ? "Deal " + this.formulaDescription + " damage." + this.getDamageDescription(x, y, m)
                : defaultDescription;
    }
    
    private String getDamageDescription(int x, int y, int m) {
        if (this == RANDOM_BETWEEN_X_Y) {
            return "";
        }
        return " NL (Deal " + this.getDamage(x, y, m) + " damage)";
    }
    
}
