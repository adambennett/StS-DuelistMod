package duelistmod.enums;

import java.util.HashMap;

public enum MenuCardRarity {

    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare");

    private final String display;

    MenuCardRarity(String display) {
        this.display = display;
    }

    public String display() { return display; }

    public static final HashMap<MenuCardRarity, Integer> menuMapping;
    public static final HashMap<Integer, MenuCardRarity> menuMappingReverse;

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (MenuCardRarity model : MenuCardRarity.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            counter++;
        }
    }
}
