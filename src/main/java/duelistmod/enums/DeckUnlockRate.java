package duelistmod.enums;

import java.util.HashMap;
import java.util.LinkedHashSet;

public enum DeckUnlockRate {
    NORMAL("Normal"),
    DOUBLE("2x as Fast"),
    TRIPLE("3x as Fast"),
    SIX("6x as Fast"),
    HALF("Half as Fast"),
    QUARTER("Quarter as Fast"),
    DOUBLE_DUELIST("2x Duelist Score"),
    TRIPLE_DUELIST("3x Duelist Score"),
    SIX_DUELIST("6x Duelist Score"),
    HALF_DUELIST("Half Duelist Score"),
    QUARTER_DUELIST("Quarter Duelist Score"),
    NO_DUELIST("No extra Duelist points");

    private final String displayText;

    public static final HashMap<Integer, DeckUnlockRate> menuMapping;
    public static final HashMap<String, DeckUnlockRate> displayNameMapping;
    public static final LinkedHashSet<String> displayNames;


    DeckUnlockRate(String displayText) {
        this.displayText = displayText;
    }

    public String displayText() { return this.displayText; }

    static {
        menuMapping = new HashMap<>();
        displayNameMapping = new HashMap<>();
        displayNames = new LinkedHashSet<>();
        int counter = 0;
        for (DeckUnlockRate rate : DeckUnlockRate.values()) {
            menuMapping.put(counter, rate);
            displayNameMapping.put(rate.displayText(), rate);
            displayNames.add(rate.displayText());
            counter++;
        }
    }
}
