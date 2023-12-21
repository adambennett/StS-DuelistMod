package duelistmod.enums;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.DuelistMod;
import duelistmod.dto.MonsterTypeConfigData;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

import java.util.HashMap;

@SuppressWarnings("SpellCheckingInspection")
public enum MonsterType {
    AQUA(Tags.AQUA, "Aqua", true),
    BEAST(Tags.BEAST, "Beast", "DivineBeast", true),
    BUG(Tags.BUG, "Bug", "Insect", false),
    DINOSAUR(Tags.DINOSAUR, "Dinosaur", true),
    DRAGON(Tags.DRAGON, "Dragon", true),
    FIEND(Tags.FIEND, "Fiend", true),
    GIANT(Tags.GIANT, "Giant", "DivineBeast", false),
    INSECT(Tags.INSECT, "Insect", true),
    MACHINE(Tags.MACHINE, "Machine", true),
    MAGNET(Tags.MAGNET, "Magnet", "Psychic", false),
    MEGATYPE(Tags.MEGATYPED, "Megatype", "Thunder", true),
    NATURIA(Tags.NATURIA, "Naturia", "Reptile", true),
    OJAMA(Tags.OJAMA, "Ojama", "SeaSerpent", false),
    PLANT(Tags.PLANT, "Plant", true),
    PREDAPLANT(Tags.PREDAPLANT, "Predaplant", "Plant", true),
    ROCK(Tags.ROCK, "Rock", true),
    ROSE(Tags.ROSE, "Rose", "Plant", true),
    SPELLCASTER(Tags.SPELLCASTER, "Spellcaster", true),
    SPIDER(Tags.SPIDER, "Spider", "Insect", true),
    SUPERHEAVY(Tags.SUPERHEAVY, "Superheavy", "Warrior", true),
    TOON_POOL(Tags.TOON_POOL, "Toon", "Pyro", true),
    WARRIOR(Tags.WARRIOR, "Warrior", true),
    WYRM(Tags.WYRM, "Wyrm", true),
    ZOMBIE(Tags.ZOMBIE, "Zombie", true);

    private final AbstractCard.CardTags tag;
    private final String displayText;
    private final Texture configImg;
    private final boolean major;
    public static final HashMap<Integer, MonsterType> menuMapping;
    public static final HashMap<MonsterType, Integer> menuMappingReverse;

    MonsterType(AbstractCard.CardTags tag, String displayText, boolean major) {
        this(tag, displayText, displayText, major);
    }

    MonsterType(AbstractCard.CardTags tag, String displayText, String configImgKey, boolean major) {
        this.tag = tag;
        this.displayText = displayText;
        this.major = major;
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

    public boolean major() { return this.major; }

    public static final String aquaSummonKey = "Summon Increase per Tribute";
    public static final int aquaDefaultSummon = 1;
    public static final String beastIncKey = "Increment Bonus on Summon";
    public static final int beastDefaultInc = 1;
    public static final String beastFangLossKey = "Fang Loss at Turn Start";
    public static final int beastDefaultFangLoss = 5;
    public static final String bugNumberKey = "Number of Bugs to Trigger";
    public static final String bugTempHpKey = "Temporary HP Gain Amount";
    public static final String bugResetKey = "Effect Resets";
    public static final int bugDefaultNumber = 2;
    public static final int bugDefaultTempHp = 5;
    public static final boolean bugDefaultReset = false;
    public static final String dragonScalesKey = "Every X DragonScales";
    public static final String dragonBlkDmgKey = "Increase Block/Damage By";
    public static final String dragonTributeScalesKey = "DragonScales per Tribute";
    public static final int dragonDefaultScales = 6;
    public static final int dragonDefaultBlkDmg = 1;
    public static final int dragonDefaultTributeScales = 2;
    public static final String fiendCardsKey = "Cards to Fetch per Tribute";
    public static final int fiendDefaultCards = 1;
    public static final String insectPosionKey = "Posion per Tribute";
    public static final int insectDefaultPoison = 1;
    public static final String naturiaVinesDmgKey = "Bonus Vines Damage";
    public static final String naturiaLeavesAmtKey = "Leaves to Trigger";
    public static final int naturiaDefaultVinesDmg = 0;
    public static final int naturiaDefaultLeavesAmt = 5;
    public static final String naturiaLeavesActionKey = "Whenever you gain Leaves";
    public static final String naturiaVinesActionKey = "Whenever you gain Vines";
    public static final String naturiaDefaultLeavesAction = VinesLeavesMod.DO_NOTHING.displayText();
    public static final String naturiaDefaultVinesAction = VinesLeavesMod.DO_NOTHING.displayText();
    public static final String machineArtifactsKey = "Artifacts per Tribute";
    public static final int machineDefaultArtifacts = 1;
    public static final String magnetDeckKey = "Add random Magnet to starting deck";
    public static final boolean magnetDefaultDeck = false;
    public static final String magnetSuperKey = "Allow adding random Super Magnet";
    public static final boolean magnetDefaultSuper = false;
    public static final String plantConstrictedKey = "Constricted per Tribute";
    public static final int plantDefaultConstricted = 1;
    public static final String predaplantThornsKey = "Thorns per Tribute";
    public static final int predaplantDefaultThorns = 1;
    public static final String rockBlockKey = "Rock Block";
    public static final int rockDefaultBlock = 2;
    public static final String spiderNumberKey = "Number of Spiders to Trigger";
    public static final String spiderTempHpKey = "Temporary HP Gain Amount";
    public static final String spiderResetKey = "Effect Resets";
    public static final int spiderDefaultNumber = 3;
    public static final int spiderDefaultTempHp = 7;
    public static final boolean spiderDefaultReset = false;
    public static final String spellcasterBlockKey = "Block on Attack";
    public static final int spellcasterDefaultBlock = 4;
    public static final String superheavyDexKey = "Dexterity per Tribute";
    public static final int superheavyDefaultDex = 1;
    public static final String toonVulnKey = "Vulnerable per Tribute";
    public static final int toonDefaultVuln = 1;
    public static final String warriorEnableKey = "Enabled Warrior Tribute Effect";
    public static final boolean warriorDefaultEnable = true;
    public static final String warriorNumTributesKey = "Number of Tributes to Trigger Effect";
    public static final int warriorDefaultNumTributes = 1;
    public static final String warriorTriggersPerCombatKey = "Number of effect triggers per combat";
    public static final int warriorDefaultTriggersPerCombat = 1;
    public static final String zombieSoulsKey = "Souls per Tribute";
    public static final int zombieDefaultSouls = 1;
    public static final String zombieVampireEffectKey = "Vampire Play Effect";
    public static final String zombieVampireNumKey = "Number of Vampires to Trigger";
    public static final boolean zombieVampireDefaultEffect = true;
    public static final int zombieVampireDefaultNum = 10;
    public static final String zombieMayakashiEffectKey = "Mayakashi Play Effect";
    public static final String zombieMayakashiNumKey = "Number of Mayakashi to Trigger";
    public static final boolean zombieMayakashiDefaultEffect = true;
    public static final int zombieMayakashiDefaultNum = 3;
    public static final String zombieVendreadEffectKey = "Vendread Play Effect";
    public static final String zombieVendreadNumKey = "Number of Vendread to Trigger";
    public static final boolean zombieVendreadDefaultEffect = true;
    public static final int zombieVendreadDefaultNum = 5;
    public static final String zombieShiranuiEffectKey = "Shiranui Play Effect";
    public static final String zombieShiranuiNumKey = "Number of Shiranui to Trigger";
    public static final boolean zombieShiranuiDefaultEffect = true;
    public static final int zombieShiranuiDefaultNum = 5;
    public static final String zombieGhostrickEffectKey = "Ghostrick Play Effect";
    public static final String zombieGhostrickNumKey = "Number of Ghostrick to Trigger";
    public static final boolean zombieGhostrickDefaultEffect = true;
    public static final int zombieGhostrickDefaultNum = 10;


    public MonsterTypeConfigData getDefaultConfig() {
        MonsterTypeConfigData config = new MonsterTypeConfigData();
        switch (this) {
            default: return config;
            case AQUA:
                config.put(aquaSummonKey, aquaDefaultSummon);
                return config;
            case BEAST:
                config.put(beastIncKey, beastDefaultInc);
                config.put(beastFangLossKey, beastDefaultFangLoss);
                return config;
            case BUG:
                config.put(bugNumberKey, bugDefaultNumber);
                config.put(bugTempHpKey, bugDefaultTempHp);
                config.put(bugResetKey, bugDefaultReset);
                return config;
            case DRAGON:
                config.put(dragonScalesKey, dragonDefaultScales);
                config.put(dragonBlkDmgKey, dragonDefaultBlkDmg);
                config.put(dragonTributeScalesKey, dragonDefaultTributeScales);
                return config;
            case FIEND:
                config.put(fiendCardsKey, fiendDefaultCards);
                return config;
            case INSECT:
                config.put(insectPosionKey, insectDefaultPoison);
                return config;
            case MACHINE:
                config.put(machineArtifactsKey, machineDefaultArtifacts);
                return config;
            case MAGNET:
                config.put(magnetDeckKey, magnetDefaultDeck);
                config.put(magnetSuperKey, magnetDefaultSuper);
                return config;
            case NATURIA:
                config.put(naturiaLeavesActionKey, naturiaDefaultLeavesAction);
                config.put(naturiaVinesActionKey, naturiaDefaultVinesAction);
                config.put(naturiaLeavesAmtKey, naturiaDefaultLeavesAmt);
                config.put(naturiaVinesDmgKey, naturiaDefaultVinesDmg);
                return config;
            case PLANT:
                config.put(plantConstrictedKey, plantDefaultConstricted);
                return config;
            case PREDAPLANT:
                config.put(predaplantThornsKey, predaplantDefaultThorns);
                return config;
            case ROCK:
                config.put(rockBlockKey, rockDefaultBlock);
                return config;
            case SPELLCASTER:
                config.put(spellcasterBlockKey, spellcasterDefaultBlock);
                return config;
            case SPIDER:
                config.put(spiderNumberKey, spiderDefaultNumber);
                config.put(spiderTempHpKey, spiderDefaultTempHp);
                config.put(spiderResetKey, spiderDefaultReset);
                return config;
            case SUPERHEAVY:
                config.put(superheavyDexKey, superheavyDefaultDex);
                return config;
            case TOON_POOL:
                config.put(toonVulnKey, toonDefaultVuln);
                return config;
            case WARRIOR:
                config.put(warriorEnableKey, warriorDefaultEnable);
                config.put(warriorTriggersPerCombatKey, warriorDefaultTriggersPerCombat);
                config.put(warriorNumTributesKey, warriorDefaultNumTributes);
                return config;
            case ZOMBIE:
                config.put(zombieSoulsKey, zombieDefaultSouls);
                config.put(zombieVampireEffectKey, zombieVampireDefaultEffect);
                config.put(zombieVampireNumKey, zombieVampireDefaultNum);
                config.put(zombieMayakashiEffectKey, zombieMayakashiDefaultEffect);
                config.put(zombieMayakashiNumKey, zombieMayakashiDefaultNum);
                config.put(zombieVendreadEffectKey, zombieVendreadDefaultEffect);
                config.put(zombieVendreadNumKey, zombieVendreadDefaultNum);
                config.put(zombieShiranuiEffectKey, zombieShiranuiDefaultEffect);
                config.put(zombieShiranuiNumKey, zombieShiranuiDefaultNum);
                config.put(zombieGhostrickEffectKey, zombieGhostrickDefaultEffect);
                config.put(zombieGhostrickNumKey, zombieGhostrickDefaultNum);
                return config;
            case DINOSAUR:
            case GIANT:
            case MEGATYPE:
            case OJAMA:
            case ROSE:
            case WYRM:
                return config;
        }
    }

    public MonsterTypeConfigData getActiveConfig() {
        return DuelistMod.persistentDuelistData.MonsterTypeConfigurations.getTypeConfigurations().getOrDefault(this, this.getDefaultConfig());
    }

    public void updateConfigSettings(MonsterTypeConfigData data) {
        DuelistMod.persistentDuelistData.MonsterTypeConfigurations.getTypeConfigurations().put(this, data);
        DuelistMod.configSettingsLoader.save();
    }

    public Object getConfig(String key, Object defaultVal) {
        return this.getActiveConfig().getProperties().getOrDefault(key, this.getDefaultConfig().getProperties().getOrDefault(key, defaultVal));
    }

    public Object getDefValue(String key) {
        return this.getDefaultConfig().getProperties().get(key);
    }
    

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        int counter = 0;
        for (MonsterType type : MonsterType.values()) {
            menuMapping.put(counter, type);
            menuMappingReverse.put(type, counter);
            counter++;
        }
    }
}
