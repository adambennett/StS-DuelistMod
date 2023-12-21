package duelistmod.enums;

import java.util.HashMap;
import java.util.LinkedHashSet;

public enum SpecialSparksStrategy {

    RANDOM("Random (Default)"),
    RANDOM_WEIGHTED("Random - Weighted"),
    GOLDEN("Golden Sparks"),
    BLOOD("Blood Sparks"),
    MAGIC("Magic Sparks"),
    STORM("Storm Sparks"),
    DARK("Dark Sparks");

    private final String displayName;
    public static final HashMap<SpecialSparksStrategy, Integer> menuMapping;
    public static final HashMap<Integer, SpecialSparksStrategy> menuMappingReverse;
    public static final HashMap<String, SpecialSparksStrategy> displayNameMapping;
    public static final LinkedHashSet<String> displayNames;

    SpecialSparksStrategy(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        displayNameMapping = new HashMap<>();
        displayNames = new LinkedHashSet<>();
        int counter = 0;
        for (SpecialSparksStrategy model : SpecialSparksStrategy.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            displayNameMapping.put(model.displayName(), model);
            displayNames.add(model.displayName());
            counter++;
        }
    }
}
