package duelistmod.enums;

import java.util.HashMap;
import java.util.LinkedHashSet;

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
    public static final HashMap<String, MenuCardRarity> displayNameMapping;
    public static final LinkedHashSet<String> displayNames;

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        displayNameMapping = new HashMap<>();
        displayNames = new LinkedHashSet<>();
        int counter = 0;
        for (MenuCardRarity model : MenuCardRarity.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            displayNameMapping.put(model.display(), model);
            displayNames.add(model.display());
            counter++;
        }
    }
}
