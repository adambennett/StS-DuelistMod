package duelistmod.enums;

import java.util.HashMap;

public enum SpecialSparksStrategy {
    RANDOM_WEIGHTED,
    RANDOM,
    GOLDEN,
    BLOOD,
    MAGIC,
    STORM,
    DARK;

    public static final HashMap<SpecialSparksStrategy, Integer> menuMapping;
    public static final HashMap<Integer, SpecialSparksStrategy> menuMappingReverse;

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (SpecialSparksStrategy model : SpecialSparksStrategy.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            counter++;
        }
    }
}
