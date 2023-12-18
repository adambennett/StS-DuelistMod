package duelistmod.enums;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.BaseGameHelper;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ColorlessShopSource {

    BASIC("Basic Pool", (t) -> DuelistMod.duelColorlessCards.stream().anyMatch(c -> c.cardID.equals(t.cardID))),
    BASIC_COLORLESS("Basic Pool and Colorless Pool", (t) -> true),
    COLORLESS("Colorless Pool", (t) -> t.color == AbstractCard.CardColor.COLORLESS),
    COLORED("Colored Pool", (t) -> TheDuelist.cardPool.group.stream().anyMatch(c -> c.cardID.equals(t.cardID))),
    TOKEN("Tokens", (t) -> t.hasTag(Tags.TOKEN), true),
    ALL_DUELIST("Duelist Cards"),
    BASE_GAME("Base Game"),
    BASE_GAME_NO_COLORLESS("Base Game (No Colorless)"),
    ALL_DUELIST_BASE_GAME("Base Game and Duelist Cards"),
    ALL_DUELIST_BASE_GAME_NO_COLORLESS("Base Game and Duelist Cards (No Colorless)"),
    MONSTERS("Monsters", (t) -> t.hasTag(Tags.MONSTER)),
    SPELLS("Spells", (t) -> t.hasTag(Tags.SPELL)),
    TRAPS("Traps", (t) -> t.hasTag(Tags.TRAP)),
    ATTACK("Attacks", (t) -> t.type == AbstractCard.CardType.ATTACK),
    SKILL("Skills", (t) -> t.type == AbstractCard.CardType.SKILL),
    POWER("Powers", (t) -> t.type == AbstractCard.CardType.POWER),
    MULTI_TARGET("Multi-Target", (t) -> t.target == AbstractCard.CardTarget.ALL_ENEMY || t.target == AbstractCard.CardTarget.ALL || t.target == AbstractCard.CardTarget.SELF_AND_ENEMY),
    SELF_TARGET("Self-Target", (t) -> t.target == AbstractCard.CardTarget.SELF),
    MAGIC("Magic Number", (t) -> DuelistMod.magicNumberCards.containsKey(t.cardID)),
    TRIBUTE("Tribute", (t) -> DuelistMod.tributeCards.containsKey(t.cardID)),
    TRIBUTE_MONSTER("Tribute Monster", (t) -> DuelistMod.tributeCards.containsKey(t.cardID) && t.hasTag(Tags.MONSTER)),
    SUMMON("Summon", (t) -> DuelistMod.summonCards.containsKey(t.cardID)),
    DRAGON("Dragon", (t) -> t.hasTag(Tags.DRAGON)),
    BEAST("Beast", (t) -> t.hasTag(Tags.BEAST)),
    MACHINE("Machine", (t) -> t.hasTag(Tags.MACHINE)),
    ROCK("Rock", (t) -> t.hasTag(Tags.ROCK)),
    MEGATYPE("Megatype", (t) -> t.hasTag(Tags.MEGATYPED)),
    ORB_CARD("Orb Card", (t) -> t.hasTag(Tags.ORB_CARD), true),
    METRONOME("Metronome", (t) -> t.hasTag(Tags.METRONOME)),
    POT("Pot Card", (t) -> t.hasTag(Tags.POT)),
    ALL_CARDS("All Cards", (t) -> CardLibrary.getAllCards().stream().anyMatch(c -> c.cardID.equals(t.cardID)));

    private final String display;
    private Predicate<AbstractCard> test;
    private final boolean isSpecialRarityOnly;
    private ArrayList<AbstractCard> loadedList;
    private static final Set<String> baseGameCardIdsNoColorless = new HashSet<>();
    private static final Set<String> baseGameCardIds = new HashSet<>();
    private static final Set<String> myCardsIds = new HashSet<>();

    ColorlessShopSource(String display) {
        this(display, null, false);
    }

    ColorlessShopSource(String display, Predicate<AbstractCard> test) {
        this(display, test, false);
    }

    ColorlessShopSource(String display, Predicate<AbstractCard> test, boolean isSpecialRarityOnly) {
        this.display = display;
        this.test = test;
        this.isSpecialRarityOnly = isSpecialRarityOnly;
    }

    public String display() { return display; }
    public boolean test(AbstractCard card) {
        return this.test != null && this.test.test(card);
    }
    public boolean isSpecialRarityOnly() { return this.isSpecialRarityOnly; }
    public ArrayList<AbstractCard> startingList() {
        if (this.loadedList == null) {
            this.loadedList = new ArrayList<>(this.loadStartingList());
        }
        if (this.test == null && baseGameCardIdsNoColorless.isEmpty()) {
            this.test = setTestAfterConstruction();
            ArrayList<AbstractCard> baseGameCards = BaseGameHelper.getAllBaseGameCards(true);
            for (AbstractCard c : baseGameCards) {
                if (c.color != AbstractCard.CardColor.COLORLESS) {
                    baseGameCardIdsNoColorless.add(c.cardID);
                }
                baseGameCardIds.add(c.cardID);
            }
            for (DuelistCard dc : DuelistMod.myCards) {
                myCardsIds.add(dc.cardID);
            }
        }
        return this.loadedList;
    }

    private List<? extends AbstractCard> loadStartingList() {
        switch (this) {
            case ALL_CARDS:
                return CardLibrary.getAllCards();
            case BASIC:
                return DuelistMod.duelColorlessCards;
            case COLORLESS:
                return BaseGameHelper.getAllColorlessCards();
            case COLORED:
                return TheDuelist.cardPool.group;
            case POT:
            case ALL_DUELIST:
                return DuelistMod.myCards;
            case ALL_DUELIST_BASE_GAME:
                return Stream.concat(DuelistMod.myCards.stream(), BaseGameHelper.getAllBaseGameCards(true).stream()).collect(Collectors.toList());
            case ALL_DUELIST_BASE_GAME_NO_COLORLESS:
                return Stream.concat(DuelistMod.myCards.stream(), BaseGameHelper.getAllBaseGameCards(false).stream()).collect(Collectors.toList());
            case BASE_GAME:
                return BaseGameHelper.getAllBaseGameCards(true);
            case BASE_GAME_NO_COLORLESS:
                return BaseGameHelper.getAllBaseGameCards(false);
            case TOKEN:
                return DuelistCardLibrary.getAllDuelistTokens();
            case ORB_CARD:
                return DuelistMod.orbCards;
            case METRONOME:
                return DuelistMod.metronomes;
            case MULTI_TARGET:
            case SELF_TARGET:
            case MONSTERS:
            case SPELLS:
            case TRAPS:
            case ATTACK:
            case SKILL:
            case POWER:
            case MAGIC:
            case TRIBUTE:
            case SUMMON:
            case DRAGON:
            case TRIBUTE_MONSTER:
            case MACHINE:
            case MEGATYPE:
            case ROCK:
                return Stream.concat(TheDuelist.cardPool.group.stream(), AbstractDungeon.colorlessCardPool.group.stream()).filter(this::test).collect(Collectors.toList());
            default:
                return AbstractDungeon.colorlessCardPool.group;
        }
    }

    private Predicate<AbstractCard> setTestAfterConstruction() {
        if (this.test != null) {
            return this.test;
        }
        switch (this) {
            case BASE_GAME:
                return (t) -> baseGameCardIds.stream().anyMatch(c -> c.equals(t.cardID));
            case BASE_GAME_NO_COLORLESS:
                return (t) -> baseGameCardIdsNoColorless.stream().anyMatch(c -> c.equals(t.cardID));
            case ALL_DUELIST_BASE_GAME:
                return (t) -> Stream.concat(baseGameCardIds.stream(), myCardsIds.stream()).anyMatch(c -> c.equals(t.cardID));
            case ALL_DUELIST_BASE_GAME_NO_COLORLESS:
                return (t) -> Stream.concat(baseGameCardIdsNoColorless.stream(), myCardsIds.stream()).anyMatch(c -> c.equals(t.cardID));
            case ALL_DUELIST:
                return (t) -> myCardsIds.stream().anyMatch(c -> c.equals(t.cardID));
        }
        return null;
    }

    public static final HashMap<ColorlessShopSource, Integer> menuMapping;
    public static final HashMap<Integer, ColorlessShopSource> menuMappingReverse;
    public static final HashMap<String, ColorlessShopSource> displayNameMapping;
    public static final LinkedHashSet<String> displayNames;

    static {
        menuMapping = new HashMap<>();
        menuMappingReverse = new HashMap<>();
        displayNameMapping = new HashMap<>();
        displayNames = new LinkedHashSet<>();
        int counter = 0;
        for (ColorlessShopSource model : ColorlessShopSource.values()) {
            menuMapping.put(model, counter);
            menuMappingReverse.put(counter, model);
            displayNameMapping.put(model.display(), model);
            displayNames.add(model.display());
            counter++;
        }
    }
}
