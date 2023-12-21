package duelistmod.enums;

import java.util.HashMap;
import java.util.LinkedHashSet;

public enum CharacterModel {
    ANIM_YUGI("Yugi"),
    ANIM_KAIBA("Kaiba"),
    STATIC_KAIBA("Kaiba (Static)"),
    STATIC_YUGI_NEW("Yugi (Static)"),
    STATIC_YUGI_OLD("Yugi (Original)");

    private final String displayName;
    public static final HashMap<CharacterModel, Integer> menuMapping;
    public static final HashMap<Integer, CharacterModel> menuMappingReverse;
    public static final HashMap<String, CharacterModel> displayNameMapping;
    public static final LinkedHashSet<String> displayNames;

    CharacterModel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isYugi() {
        return !(this == ANIM_KAIBA || this == STATIC_KAIBA);
    }

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        displayNameMapping = new HashMap<>();
        displayNames = new LinkedHashSet<>();
        int counter = 0;
        for (CharacterModel model : CharacterModel.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            displayNameMapping.put(model.getDisplayName(), model);
            displayNames.add(model.getDisplayName());
            counter++;
        }
    }
}
