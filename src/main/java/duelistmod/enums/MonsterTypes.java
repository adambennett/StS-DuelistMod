package duelistmod.enums;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public enum MonsterTypes {
    AQUA(Tags.AQUA, "Aqua"),
    BUG(Tags.BUG, "Bug", "Insect"),
    DINOSAUR(Tags.DINOSAUR, "Dinosaur"),
    DRAGON(Tags.DRAGON, "Dragon"),
    FIEND(Tags.FIEND, "Fiend"),
    GIANT(Tags.GIANT, "Giant", "DivineBeast"),
    INSECT(Tags.INSECT, "Insect"),
    MACHINE(Tags.MACHINE, "Machine"),
    MAGNET(Tags.MAGNET, "Magnet", "Psychic"),
    MEGATYPE(Tags.MEGATYPED, "Megatype", "Thunder"),
    NATURIA(Tags.NATURIA, "Naturia", "Reptile"),
    OJAMA(Tags.OJAMA, "Ojama", "SeaSerpent"),
    PLANT(Tags.PLANT, "Plant"),
    PREDAPLANT(Tags.PREDAPLANT, "Predaplant", "Plant"),
    ROCK(Tags.ROCK, "Rock"),
    ROSE(Tags.ROSE, "Rose", "Plant"),
    SPELLCASTER(Tags.SPELLCASTER, "Spellcaster"),
    SPIDER(Tags.SPIDER, "Spider", "Insect"),
    SUPERHEAVY(Tags.SUPERHEAVY, "Superheavy", "Warrior"),
    TOON_POOL(Tags.TOON_POOL, "Toon", "Pyro"),
    WARRIOR(Tags.WARRIOR, "Warrior"),
    WYRM(Tags.WYRM, "Wyrm"),
    ZOMBIE(Tags.ZOMBIE, "Zombie");

    private final AbstractCard.CardTags tag;
    private final String displayText;
    private final Texture configImg;
    public static final HashMap<Integer, MonsterTypes> menuMapping;
    public static final HashMap<MonsterTypes, Integer> menuMappingReverse;

    MonsterTypes(AbstractCard.CardTags tag, String displayText) {
        this(tag, displayText, displayText);
    }

    MonsterTypes(AbstractCard.CardTags tag, String displayText, String configImgKey) {
        this.tag = tag;
        this.displayText = displayText;
        Texture t = null;
        try {
            t = new Texture(DuelistMod.makeTypeIconPath(configImgKey+".png"));
        } catch (Exception ex) {
            Util.logError("Error loading img for Monster Type: " + this, ex);
        }
        this.configImg = t;
    }

    public AbstractCard.CardTags tag() { return this.tag; }

    public String displayText() { return this.displayText; }

    public Texture configImg() { return this.configImg; }

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
