package duelistmod.enums;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.variables.Tags;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public enum MonsterTypes {
    AQUA(Tags.AQUA, "Aqua"),
    BUG(Tags.BUG, "Bug"),
    DINOSAUR(Tags.DINOSAUR, "Dinosaur"),
    DRAGON(Tags.DRAGON, "Dragon"),
    FIEND(Tags.FIEND, "Fiend"),
    GIANT(Tags.GIANT, "Giant"),
    INSECT(Tags.INSECT, "Insect"),
    MACHINE(Tags.MACHINE, "Machine"),
    MAGNET(Tags.MAGNET, "Magnet"),
    MEGATYPE(Tags.MEGATYPED, "Megatype"),
    NATURIA(Tags.NATURIA, "Naturia"),
    OJAMA(Tags.OJAMA, "Ojama"),
    PLANT(Tags.PLANT, "Plant"),
    PREDAPLANT(Tags.PREDAPLANT, "Predaplant"),
    ROCK(Tags.ROCK, "Rock"),
    ROSE(Tags.ROSE, "Rose"),
    SPELLCASTER(Tags.SPELLCASTER, "Spellcaster"),
    SPIDER(Tags.SPIDER, "Spider"),
    SUPERHEAVY(Tags.SUPERHEAVY, "Superheavy"),
    TOON_POOL(Tags.TOON_POOL, "Toon"),
    WARRIOR(Tags.WARRIOR, "Warrior"),
    WYRM(Tags.WYRM, "Wyrm"),
    ZOMBIE(Tags.ZOMBIE, "Zombie");

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
