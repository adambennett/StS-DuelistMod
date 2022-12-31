package duelistmod.enums;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.variables.Tags;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public enum MonsterTypes {
    AQUA(Tags.AQUA, "Aqua"),
    DRAGON(Tags.DRAGON, "Dragon"),
    FIEND(Tags.FIEND, "Fiend"),
    INSECT(Tags.INSECT, "Insect"),
    MACHINE(Tags.MACHINE, "Machine"),
    MEGATYPE(Tags.MEGATYPED, "Megatype"),
    NATURIA(Tags.NATURIA, "Naturia"),
    PLANT(Tags.PLANT, "Plant"),
    PREDAPLANT(Tags.PREDAPLANT, "Predaplant"),
    SPELLCASTER(Tags.SPELLCASTER, "Spellcaster"),
    SUPERHEAVY(Tags.SUPERHEAVY, "Superheavy"),
    TOON_POOL(Tags.TOON_POOL, "Toon"),
    ZOMBIE(Tags.ZOMBIE, "Zombie"),
    WARRIOR(Tags.WARRIOR, "Warrior"),
    ROCK(Tags.ROCK, "Rock"),
    WYRM(Tags.WYRM, "Wyrm"),
    DINOSAUR(Tags.DINOSAUR, "Dinosaur");

    private final AbstractCard.CardTags tag;
    private final String displayText;
    public static final HashMap<Integer, MonsterTypes> menuMapping;
    public static final HashMap<MonsterTypes, Integer> menuMappingReverse;


    MonsterTypes(AbstractCard.CardTags tag, String displayText) {
        this.tag = tag;
        this.displayText = displayText;
    }

    public AbstractCard.CardTags tag() { return this.tag; }

    public String displayText() { return this.displayText; }

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (MonsterTypes type : MonsterTypes.values()) {
            menuMapping.put(counter, type);
            menuMappingReverse.put(type, counter);
            counter++;
        }
    }
}
