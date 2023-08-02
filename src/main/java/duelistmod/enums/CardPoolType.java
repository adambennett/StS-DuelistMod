package duelistmod.enums;

import java.util.HashMap;

public enum CardPoolType {

    DECK_BASIC_DEFAULT("Deck + Basic (Default)", true, true, 0),
    DECK_ONLY("Deck Only", false, true, 0),
    BASIC_ONLY("Basic Only", true, false, 0),
    DECK_BASIC_1_RANDOM("Deck + Basic + 1 Random Deck", true, true, 1),
    DECK_1_RANDOM("Deck + 1 Random Deck", false, true, 1),
    BASIC_1_RANDOM("Basic + 1 Random Deck", true, false, 1),
    DECK_BASIC_2_RANDOM("Basic + Deck + 2 Random Decks", true, true, 2),
    TWO_RANDOM("2 Random Decks", false, false, 2),
    DECK_2_RANDOM("Deck + 2 Random Decks", false, true, 2),
    ALL_CARDS("Always ALL Cards", false, false, 0);

    private final String display;
    private final boolean includesBasic;
    private final boolean includesDeck;
    private final int randomDecks;

    CardPoolType(String display, boolean includesBasic, boolean includesDeck, int randomDecks) {
        this.display = display;
        this.includesBasic = includesBasic;
        this.includesDeck = includesDeck;
        this.randomDecks = randomDecks;
    }

    public String getDisplay() { return display; }
    public boolean includesBasic() { return includesBasic; }
    public boolean includesDeck() { return includesDeck; }
    public int getRandomDecks() { return randomDecks; }

    public static final HashMap<CardPoolType, Integer> menuMapping;
    public static final HashMap<Integer, CardPoolType> menuMappingReverse;

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (CardPoolType model : CardPoolType.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            counter++;
        }
    }
}
