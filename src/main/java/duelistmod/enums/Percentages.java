package duelistmod.enums;

import java.util.HashMap;

public enum Percentages {
    ZERO("0%", 0),
    FIVE("5%", 5),
    TEN("10%", 10),
    FIFTEEN("15%", 15),
    TWENTY("20%", 20),
    TWENTY_FIVE("25%", 25),
    THIRTY("30%", 30),
    THIRTY_FIVE("35%", 35),
    FORTY("40%", 40),
    FORTY_FIVE("45%", 45),
    FIFTY("50%", 50),
    FIFTY_FIVE("55%", 55),
    SIXTY("60%", 60),
    SIXTY_FIVE("65%", 65),
    SEVENTY("70%", 70),
    SEVENTY_FIVE("75%", 75),
    EIGHTY("80%", 80),
    EIGHT_FIVE("85%", 85),
    NINETY("90%", 90),
    NINETY_FIVE("95%", 95),
    HUNDRED("100%", 100);

    private final String displayName;
    private final int value;
    public static final HashMap<Integer, Percentages> menuMapping;
    public static final HashMap<Percentages, Integer> menuMappingReverse;

    Percentages(String displayName, int value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String displayName() { return this.displayName; }

    public int value() { return this.value; }

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (Percentages type : Percentages.values()) {
            menuMapping.put(counter, type);
            menuMappingReverse.put(type, counter);
            counter++;
        }
    }

}
