package duelistmod.enums;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistCardWithAltVersions;
import duelistmod.cards.DarkCreator;
import duelistmod.cards.TheCreator;
import duelistmod.cards.other.tokens.PuzzleToken;
import duelistmod.cards.other.tokens.Token;
import duelistmod.cards.pools.dragons.BlueEyes;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.StartingDeckStats;
import duelistmod.dto.TwoNums;
import duelistmod.dto.builders.PuzzleConfigDataBuilder;
import duelistmod.helpers.PuzzleHelper;
import duelistmod.helpers.Util;
import duelistmod.helpers.poolhelpers.AquaPool;
import duelistmod.helpers.poolhelpers.AscendedOnePool;
import duelistmod.helpers.poolhelpers.AscendedThreePool;
import duelistmod.helpers.poolhelpers.AscendedTwoPool;
import duelistmod.helpers.poolhelpers.CreatorPool;
import duelistmod.helpers.poolhelpers.DragonPool;
import duelistmod.helpers.poolhelpers.ExodiaPool;
import duelistmod.helpers.poolhelpers.FiendPool;
import duelistmod.helpers.poolhelpers.IncrementPool;
import duelistmod.helpers.poolhelpers.InsectPool;
import duelistmod.helpers.poolhelpers.MachinePool;
import duelistmod.helpers.poolhelpers.MegatypePool;
import duelistmod.helpers.poolhelpers.NaturiaPool;
import duelistmod.helpers.poolhelpers.BeastPool;
import duelistmod.helpers.poolhelpers.PharaohPool;
import duelistmod.helpers.poolhelpers.PlantPool;
import duelistmod.helpers.poolhelpers.RandomBigPool;
import duelistmod.helpers.poolhelpers.RandomMetronomePool;
import duelistmod.helpers.poolhelpers.RandomSmallPool;
import duelistmod.helpers.poolhelpers.RandomUpgradePool;
import duelistmod.helpers.poolhelpers.SpellcasterPool;
import duelistmod.helpers.poolhelpers.StandardPool;
import duelistmod.helpers.poolhelpers.ToonPool;
import duelistmod.helpers.poolhelpers.WarriorPool;
import duelistmod.helpers.poolhelpers.ZombiePool;
import duelistmod.interfaces.CardPoolLoader;
import duelistmod.helpers.CardPoolMapper;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.MillenniumPuzzle;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public enum StartingDeck {

    STANDARD("standard", "Standard Deck", "Standard", null, Tags.STANDARD_DECK, StandardPool::deck, StandardPool::basic, false, false),
    DRAGON("dragon", "Dragon Deck", "Dragon", Tags.DRAGON, Tags.DRAGON_DECK, DragonPool::deck, DragonPool::basic, false, false),
    SPELLCASTER("spellcaster", "Spellcaster Deck", "Spellcaster", Tags.SPELLCASTER, Tags.SPELLCASTER_DECK, SpellcasterPool::deck, SpellcasterPool::basic, false, false),
    AQUA("aqua", "Aqua Deck", "Aqua", Tags.AQUA, Tags.AQUA_DECK, AquaPool::deck, AquaPool::basic, false, false),
    FIEND("fiend", "Fiend Deck", "Fiend", Tags.FIEND, Tags.FIEND_DECK, FiendPool::deck, FiendPool::basic, false, false),
    ZOMBIE("zombie", "Zombie Deck", "Zombie", Tags.ZOMBIE, Tags.ZOMBIE_DECK, ZombiePool::deck, ZombiePool::basic, false, false),
    MACHINE("machine", "Machine Deck", "Machine", Tags.MACHINE, Tags.MACHINE_DECK, MachinePool::deck, MachinePool::basic, false, false),
    BEAST("beast", "Beast Deck", "Beast", Tags.BEAST, Tags.BEAST_DECK, BeastPool::deck, BeastPool::basic, false, false),
    INSECT("insect", "Insect Deck", "Insect", Tags.INSECT, Tags.INSECT_DECK, InsectPool::deck, InsectPool::basic, false, false),
    PLANT("plant", "Plant Deck", "Plant", Tags.PLANT, Tags.PLANT_DECK, PlantPool::deck, PlantPool::basic, false, false),
    NATURIA("naturia", "Naturia Deck", "Naturia", Tags.NATURIA, Tags.NATURIA_DECK, NaturiaPool::deck, NaturiaPool::basic, false, false,Tags.INSECT, Tags.PLANT, Tags.PREDAPLANT),
    WARRIOR("warrior", "Warrior Deck", "Warrior", Tags.WARRIOR, Tags.WARRIOR_DECK, WarriorPool::deck, WarriorPool::basic, false, false, Tags.SUPERHEAVY),
    TOON("toon", "Toon Deck", "Toon", Tags.TOON_POOL, Tags.TOON_DECK, ToonPool::deck, ToonPool::basic, false, false),
    MEGATYPE("megatype", "Megatype Deck", "Megatype", Tags.MEGATYPED, Tags.MEGATYPE_DECK, MegatypePool::deck, MegatypePool::basic, false, false),
    INCREMENT("increment", "Increment Deck", "Increment", null, Tags.INCREMENT_DECK, IncrementPool::deck, IncrementPool::basic, false, false),
    CREATOR("creator", "Creator Deck", "Creator", null, Tags.CREATOR_DECK, CreatorPool::deck, CreatorPool::basic, false, false),
    EXODIA("exodia", "Exodia Deck", "Exodia", Tags.EXODIA, Tags.EXODIA_DECK, ExodiaPool::deck, ExodiaPool::basic, false, false),
    ASCENDED_I("a1", "Ascended I", "Ascended I", null, Tags.ASCENDED_ONE_DECK, AscendedOnePool::deck, AscendedOnePool::basic, false, false),
    ASCENDED_II("a2", "Ascended II", "Ascended II", null, Tags.ASCENDED_TWO_DECK, AscendedTwoPool::deck, AscendedTwoPool::basic, false, false),
    ASCENDED_III("a3", "Ascended III", "Ascended III", null, Tags.ASCENDED_THREE_DECK, AscendedThreePool::deck, AscendedThreePool::basic, true, false),
    PHARAOH_I("p1", "Pharaoh I", "Pharaoh I", null, Tags.PHARAOH_ONE_DECK, PharaohPool::pharaohOne, PharaohPool::basicOne, true, false),
    PHARAOH_II("p2", "Pharaoh II", "Pharaoh II", null, Tags.PHARAOH_TWO_DECK, PharaohPool::pharaohTwo, PharaohPool::basicTwo, true, false),
    PHARAOH_III("p3", "Pharaoh III", "Pharaoh III", null, Tags.PHARAOH_THREE_DECK, PharaohPool::pharaohThree, PharaohPool::basicThree, true, false),
    PHARAOH_IV("p4", "Pharaoh IV", "Pharaoh IV", null, Tags.PHARAOH_FOUR_DECK, PharaohPool::pharaohFour, PharaohPool::basicFour, true, false),
    PHARAOH_V("p5", "Pharaoh V", "Pharaoh V", null, Tags.PHARAOH_FIVE_DECK, PharaohPool::pharaohFive, PharaohPool::basicFive, true, false),
    RANDOM_SMALL("rds", "Random Deck (Small)", "Random - Small", null, Tags.RANDOM_DECK_SMALL, RandomSmallPool::deck, RandomSmallPool::basic, false, false),
    RANDOM_BIG("rdb", "Random Deck (Big)", "Random - Big", null, Tags.RANDOM_DECK_BIG, RandomBigPool::deck, RandomBigPool::basic, false, false),
    RANDOM_UPGRADE("rdu", "Upgrade Deck", "Upgrade", null, Tags.RANDOM_DECK_UPGRADE, RandomUpgradePool::deck, RandomUpgradePool::basic, false, false),
    METRONOME("metronome", "Metronome Deck", "Metronome", Tags.METRONOME, Tags.METRONOME_DECK, RandomMetronomePool::deck, RandomMetronomePool::basic, false, false);

    private final boolean isPermanentlyLocked;
    private final Integer unlockLevel;
    private final CardTags primaryType;
    private final CardTags startingDeckTag;
    private final String deckId;
    private final String deckName;
    private final String displayName;
    private final List<CardTags> allTypes;
    private final CardPoolLoader coloredPool;
    private final CardPoolLoader basicPool;
    private CardPoolLoader liveColoredPool;
    private final HashSet<String> startingDeckIds = new HashSet<>();

    private boolean isHidden;
    private Integer coloredSize;
    private Integer basicSize;
    private Integer startingDeckSize;
    private ArrayList<AbstractCard> startingDeck;
    private CardPoolMapper coloredPoolMapper;
    private CardPoolMapper basicPoolMapper;
    private CardPoolMapper startingDeckMapper;

    public static final LinkedHashMap<String, Integer> unlockOrderInfo;
    public static final ArrayList<StartingDeck> nonHidden;
    public static final LinkedHashMap<String, DuelistCard> tokenMap;

    public static StartingDeck currentDeck = STANDARD;
    private static int currentDeckIndex = 0;
    private final static List<StartingDeck> selectScreenList;

    StartingDeck(String deckId, String deckName, String displayName, CardTags primaryType, CardTags startingDeckTag, CardPoolLoader coloredPool, CardPoolLoader basicPool, boolean permanentlyLocked, boolean isHidden, CardTags ... allTypes) {
        this.deckId = deckId;
        this.startingDeckTag = startingDeckTag;
        this.unlockLevel = generateUnlockLevel();
        this.isPermanentlyLocked = permanentlyLocked;
        this.primaryType = primaryType;
        this.deckName = deckName;
        this.displayName = displayName;
        this.coloredPool = coloredPool;
        this.liveColoredPool = coloredPool;
        this.basicPool = basicPool;
        this.isHidden = isHidden;
        this.allTypes = allTypes != null ? new ArrayList<>(Arrays.asList(allTypes)) : new ArrayList<>();
        this.allTypes.add(primaryType);
    }

    public static void nextDeck(CharacterSelectScreen selectScreen) {
        currentDeckIndex++;
        if (currentDeckIndex >= selectScreenList.size()) {
            currentDeckIndex = 0;
        }
        setCurrentDeck(currentDeckIndex, selectScreen);
    }

    public static void lastDeck(CharacterSelectScreen selectScreen) {
        currentDeckIndex--;
        if (currentDeckIndex < 0) {
            currentDeckIndex = selectScreenList.size() - 1;
        }
        setCurrentDeck(currentDeckIndex, selectScreen);
    }

    public static void loadDeck(CharacterSelectScreen screen) {
        loadDeck(currentDeckIndex, screen);
    }

    public static void loadDeck(int ordinal) {
        loadDeck(ordinal, null);
    }

    private static void loadDeck(int ordinal, CharacterSelectScreen screen) {
        currentDeck = StartingDeck.values()[ordinal];
        refreshSelectScreen(screen);
    }

    private static void setCurrentDeck(int index, CharacterSelectScreen selectScreen) {
        currentDeck = selectScreenList.get(index);
        if (selectScreen != null) {
            selectScreen.bgCharImg = currentDeck.getPortrait();
            Util.updateCharacterSelectScreenPuzzleDescription();
            Util.updateSelectScreenRelicList();
        }
    }

    public static void refreshSelectScreen(CharacterSelectScreen selectScreen) {
        selectScreenList.clear();
        boolean foundOldDeck = false;
        for (StartingDeck deck : StartingDeck.values()) {
            if (!deck.isHidden()) {
                selectScreenList.add(deck);
            }
        }
        int counter = 0;
        for (StartingDeck deck : selectScreenList) {
            if (deck == currentDeck) {
                foundOldDeck = true;
                currentDeckIndex = counter;
                break;
            }
            counter++;
        }
        if (!foundOldDeck) {
            currentDeckIndex = 0;
        }
        setCurrentDeck(currentDeckIndex, selectScreen);
    }

    public static void refreshStartingDecksData() {
        HashMap<StartingDeck, ArrayList<AbstractCard>> decks = new HashMap<>();
        ArrayList<DuelistCard> toSort = DuelistCardLibrary.getAllDuelistTokens();
        toSort.sort(Comparator.comparing(a -> a.name));
        for (DuelistCard token : toSort) {
            tokenMap.put(token.cardID, token);
        }
        for (DuelistCard card : DuelistMod.myCards) {
            for (StartingDeck deck : StartingDeck.values()) {
                if (card.hasTag(deck.startingDeckTag)) {
                    int copies = card.startingCopies.getOrDefault(deck, 1);
                    for (int i = 0; i < copies; i++) {
                        AbstractCard cardCopy = card instanceof DuelistCardWithAltVersions
                                ? ((DuelistCardWithAltVersions)card).getSpecialVersion(deck, null)
                                : card.makeCopy();
                        decks.compute(deck, (k, v) -> {
                            if (v == null) {
                                ArrayList<AbstractCard> l = new ArrayList<>();
                                l.add(cardCopy);
                                return l;
                            } else {
                                v.add(cardCopy);
                                return v;
                            }
                        });
                    }
                }
            }
        }

        for (StartingDeck deck : StartingDeck.values()) {
            if (decks.containsKey(deck)) {
                deck.setStartingDeck(decks.get(deck));
                deck.setupStartingDeckInfo();
            }
        }
    }

    private void toggleHidden(CharacterSelectScreen selectScreen) {
        this.isHidden = !this.isHidden;
        StartingDeck.refreshSelectScreen(selectScreen);
    }

    private void setupStartingDeckInfo() {
        this.startingDeckMapper = new CardPoolMapper(this.startingDeck);
        this.startingDeckSize = this.startingDeckMapper.size();
    }

    public PuzzleConfigData getDefaultPuzzleConfig() {
        PuzzleConfigDataBuilder builder = new PuzzleConfigDataBuilder();
        builder = builder.setDeck(this.deckName);
        builder = builder.setTokensToSummon(1);
        builder = builder.setTokenType("theDuelist:PuzzleToken");
        switch (this) {
            case STANDARD:
                builder = builder.setBlurToGain(1);
                builder = builder.setGainBlur(true);
                builder = builder.setRandomBlockLow(0);
                builder = builder.setRandomBlockHigh(10);
                builder = builder.setGainRandomBlock(true);
                break;
            case DRAGON:
                builder = builder.setEffectsChoices(1);
                builder = builder.setEffectsToRemove(2);
                builder = builder.setEffectsDisabled(false);
                builder = builder.setTokenType("theDuelist:DragonToken");
                break;
            case NATURIA:
                builder = builder.setStartingLeaves(1);
                builder = builder.setStartingVines(1);
                builder = builder.setTokenType("theDuelist:NatureToken");
                break;
            case SPELLCASTER:
                builder = builder.setTokenType("theDuelist:SpellcasterToken");
                builder = builder.setChannelShadow(true);
                break;
            case TOON:
                builder = builder.setTokenType("theDuelist:ToonToken");
                builder = builder.setApplyToonWorld(true);
                break;
            case ZOMBIE:
                builder = builder.setTokenType("theDuelist:ZombieToken");
                builder = builder.setChannelShadow(true);
                break;
            case AQUA:
                builder = builder.setTokenType("theDuelist:AquaToken");
                builder = builder.setOverflowDrawPile(true);
                builder = builder.setDrawPileCardsToOverflow(1);
                break;
            case FIEND:
                builder = builder.setTokenType("theDuelist:FiendToken");
                builder = builder.setDamageBoost(true);
                break;
            case MACHINE:
                builder = builder.setTokenType("theDuelist:MachineToken");
                builder = builder.setRandomTokenToHand(true);
                builder = builder.setRandomTokenAmount(1);
                break;
            case WARRIOR:
                builder = builder.setTokenType("theDuelist:StanceToken");
                builder = builder.setGainVigor(true);
                builder = builder.setGainBlur(true);
                builder = builder.setBlurToGain(2);
                builder = builder.setVigorToGain(3);
                break;
            case INSECT:
                builder = builder.setTokenType("theDuelist:InsectToken");
                builder = builder.setAddBixi(true);
                break;
            case PLANT:
                builder = builder.setTokenType("theDuelist:PlantToken");
                builder = builder.setApplyConstricted(true);
                builder = builder.setConstrictedAmount(2);
                break;
            case MEGATYPE:
                builder = builder.setTokenType("theDuelist:MegatypeToken");
                builder = builder.setAddMonsterToHand(true);
                builder = builder.setRandomMonstersToAdd(1);
                break;
            case CREATOR:
                break;
            case INCREMENT:
                builder = builder.setTokenType("theDuelist:PuzzleToken");
                builder = builder.setIncrement(true);
                builder = builder.setAmountToIncrementMatchesAct(true);
                builder = builder.setAmountToIncrement(0);
                break;
            case BEAST:
                builder = builder.setTokenType("theDuelist:BeastToken");
                builder.setFangTriggerEffect(true);
                builder.setFangsToGain(3);
                builder.setAmountOfBeastsToTrigger(10);
                break;
            case EXODIA:
                builder = builder.setTokenType("theDuelist:ExodiaToken");
                builder = builder.setApplySoulbound(true);
                builder = builder.setDrawExodiaHead(true);
                builder = builder.setCannotObtainCards(true);
                break;
            case ASCENDED_I:
            case ASCENDED_III:
                builder = builder.setTokenType("theDuelist:MegatypeToken");
                break;
            case ASCENDED_II:
                builder = builder.setTokenType("theDuelist:MegatypeToken");
                builder = builder.setChannelShadow(true);
                break;
            case PHARAOH_I:
                builder = builder.setTokenType("theDuelist:MegatypeToken");
                builder = builder.setPharaohEffectDisabled(false);
                builder.setPharaohAmount1(1);
                builder.setPharaohPercent(Percentage.TWENTY_FIVE.displayName());
                break;
            case PHARAOH_II:
                builder = builder.setTokenType("theDuelist:SuperheavyToken");
                builder = builder.setPharaohEffectDisabled(false);
                builder.setPharaohAmount1(1);
                builder.setPharaohPercent(Percentage.TWENTY_FIVE.displayName());
                break;
            case PHARAOH_III:
                builder = builder.setTokenType("theDuelist:OrbToken");
                builder = builder.setPharaohEffectDisabled(false);
                builder.setPharaohAmount1(1);
                builder.setPharaohPercent(Percentage.TWENTY_FIVE.displayName());
                break;
            case PHARAOH_IV:
                builder = builder.setTokenType("theDuelist:StanceToken");
                builder = builder.setPharaohEffectDisabled(false);
                builder.setPharaohAmount1(1);
                builder.setPharaohPercent(Percentage.TWENTY_FIVE.displayName());
                break;
            case PHARAOH_V:
                builder = builder.setTokenType("theDuelist:PuzzleToken");
                builder = builder.setPharaohEffectDisabled(false);
                builder.setPharaohAmount1(3);
                builder.setPharaohAmount2(3);
                break;
            case RANDOM_SMALL:
            case RANDOM_BIG:              
            case RANDOM_UPGRADE:        
            case METRONOME:
                builder = builder.setTokenType("theDuelist:PuzzleToken");
                builder = builder.setRandomSummonTokensLowEnd(1);
                builder = builder.setRandomSummonTokensHighEnd(3);
                break;
        }
        return builder.createPuzzleConfigData();
    }

    public void updateConfigSettings(PuzzleConfigData data) {
        DuelistMod.persistentDuelistData.PuzzleConfigurations.getPuzzleConfigurations().put(this.deckId, data);
        Util.updateCharacterSelectScreenPuzzleDescription();
        if (AbstractDungeon.player != null && AbstractDungeon.player.relics != null && AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID)) {
            MillenniumPuzzle puzzle = (MillenniumPuzzle) AbstractDungeon.player.getRelic(MillenniumPuzzle.ID);
            puzzle.getDeckDesc();
        }
        DuelistMod.configSettingsLoader.save();
    }

    private ArrayList<DuelistDropdown> tokenTypeSelector(ArrayList<IUIElement> settingElements) {
        PuzzleConfigData configOnLoad = this.getActiveConfig();
        PuzzleConfigData defaultConfig = this.getDefaultPuzzleConfig();
        ArrayList<DuelistDropdown> dropdowns = new ArrayList<>();

        String tokenName = getTokenFromID(defaultConfig.getTokenType()).name;
        int tokenIndex = 0;
        for (Map.Entry<String, DuelistCard> token : tokenMap.entrySet()) {
            if (token.getKey().equals(configOnLoad.getTokenType())) {
                break;
            }
            tokenIndex++;
        }
        tokenIndex = tokenIndex > tokenMap.size() ? 0 : tokenIndex;

        settingElements.add(new ModLabel("Puzzle Token Type", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tokenOptions = new ArrayList<>();
        for (Map.Entry<String, DuelistCard> token : tokenMap.entrySet()) { tokenOptions.add(getTokenFromID(token.getKey()).name); }
        String tooltip = "The type of token to be #ySummoned by the #yMillennium #yPuzzle at the start of combat. Set to " + tokenName + " by default.";
        DuelistDropdown tokenTypeSelector = new DuelistDropdown(tooltip, tokenOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            PuzzleConfigData data = this.getActiveConfig();
            data.setTokenType(getTokenIdInSelectionList(s));
            this.updateConfigSettings(data);
        });
        tokenTypeSelector.setSelectedIndex(tokenIndex);

        DuelistDropdown tokenNumberSelector;
        DuelistDropdown tokenNumberSelectorB = null;
        ArrayList<String> tokensToSummonOptions = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            tokensToSummonOptions.add(String.valueOf(i));
        }
        if (this == RANDOM_SMALL || this == RANDOM_BIG || this == RANDOM_UPGRADE || this == METRONOME) {
            tooltip = "The low end of the random number of tokens to be #ySummoned by the #yMillennium #yPuzzle at the start of combat. Set to " + this.getDefaultPuzzleConfig().getRandomSummonTokensLowEnd() + " by default.";
            tokenNumberSelector = new DuelistDropdown(tooltip, tokensToSummonOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 300), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                PuzzleConfigData data = this.getActiveConfig();
                data.setRandomSummonTokensLowEnd(i);
                this.updateConfigSettings(data);
            });
            tokenNumberSelector.setSelectedIndex(configOnLoad.getRandomSummonTokensLowEnd());

            tooltip = "The high end of the random number of tokens to be #ySummoned by the #yMillennium #yPuzzle at the start of combat. Set to " + this.getDefaultPuzzleConfig().getRandomSummonTokensHighEnd() + " by default.";
            tokenNumberSelectorB = new DuelistDropdown(tooltip, tokensToSummonOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 300 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                PuzzleConfigData data = this.getActiveConfig();
                data.setRandomSummonTokensHighEnd(i);
                this.updateConfigSettings(data);
            });
            tokenNumberSelectorB.setSelectedIndex(configOnLoad.getRandomSummonTokensHighEnd());
        } else {
            tooltip = "The number of tokens to be #ySummoned by the #yMillennium #yPuzzle at the start of combat. Set to " + this.getDefaultPuzzleConfig().getTokensToSummon() + " by default.";
            tokenNumberSelector = new DuelistDropdown(tooltip, tokensToSummonOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 300), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                PuzzleConfigData data = this.getActiveConfig();
                data.setTokensToSummon(i);
                this.updateConfigSettings(data);
            });
            tokenNumberSelector.setSelectedIndex(configOnLoad.getTokensToSummon());
        }

        if (tokenNumberSelectorB != null) {
            dropdowns.add(tokenNumberSelectorB);
        }
        dropdowns.add(tokenNumberSelector);
        dropdowns.add(tokenTypeSelector);
        return dropdowns;
    }

    private DuelistConfigurationData generateConfigMenu() {
        if (this.isHidden) {
            return null;
        }
        RESET_Y(); LINEBREAK();LINEBREAK();LINEBREAK();LINEBREAK();
        String tooltip;
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        PuzzleConfigData configOnLoad = getActiveConfig();
        PuzzleConfigData defaultConfig = getDefaultPuzzleConfig();

        ArrayList<DuelistDropdown> tokenTypeSelectors = tokenTypeSelector(settingElements);

        LINEBREAK();
        LINEBREAK();

        switch (this) {
            case DRAGON:
                tooltip = "When disabled, the #yMillennium #yPuzzle start-of-combat effects will be disabled. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Start of Combat Effects", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !configOnLoad.getEffectsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setEffectsDisabled(!button.enabled);
                    this.updateConfigSettings(data);
                }));

                LINEBREAK();

                settingElements.add(new ModLabel("Effects to Choose", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> choicesOptions = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    choicesOptions.add(String.valueOf(i));
                }
                tooltip = "The number of special effects to choose at the start of combat. Set to #b" + defaultConfig.getEffectsChoices() + " by default.";
                DuelistDropdown effectsChoicesSelector = new DuelistDropdown(tooltip, choicesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setEffectsChoices(i);
                    this.updateConfigSettings(data);
                });
                effectsChoicesSelector.setSelectedIndex(configOnLoad.getEffectsChoices());

                LINEBREAK();

                settingElements.add(new ModLabel("Effects to Remove", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> removeOptions = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    removeOptions.add(String.valueOf(i));
                }
                tooltip = "Removes a random set of effects from the list of selections each combat to prevent always offering the best options. NL Set to #b" + defaultConfig.getEffectsToRemove() + " by default.";
                DuelistDropdown removeOptionsSelector = new DuelistDropdown(tooltip, removeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setEffectsToRemove(i);
                    this.updateConfigSettings(data);
                });
                removeOptionsSelector.setSelectedIndex(configOnLoad.getEffectsToRemove());

                settingElements.add(removeOptionsSelector);
                settingElements.add(effectsChoicesSelector);
                break;
            case NATURIA:
                settingElements.add(new ModLabel("Starting Leaves", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> leavesOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    leavesOptions.add(String.valueOf(i));
                }
                tooltip = "The number of #yLeaves granted at the start of combat. Set to #b" + defaultConfig.getStartingLeaves() + " by default.";
                DuelistDropdown leavesSelector = new DuelistDropdown(tooltip, leavesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setStartingLeaves(i);
                    this.updateConfigSettings(data);
                });
                leavesSelector.setSelectedIndex(configOnLoad.getStartingLeaves());

                LINEBREAK();

                settingElements.add(new ModLabel("Starting Vines", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> vinesOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    vinesOptions.add(String.valueOf(i));
                }
                tooltip = "The number of #yVines granted at the start of combat. Set to #b" + defaultConfig.getStartingVines() + " by default.";
                DuelistDropdown vinesSelector = new DuelistDropdown(tooltip, vinesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setStartingVines(i);
                    this.updateConfigSettings(data);
                });
                vinesSelector.setSelectedIndex(configOnLoad.getStartingVines());

                settingElements.add(vinesSelector);
                settingElements.add(leavesSelector);
                break;
            case AQUA:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not #yOverflow any cards at the start of combat. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Overflow Cards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getOverflowDrawPile(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setOverflowDrawPile(button.enabled);
                    this.updateConfigSettings(data);
                }));

                LINEBREAK();

                settingElements.add(new ModLabel("Number of Cards to Overflow", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> overflowOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    overflowOptions.add(String.valueOf(i));
                }
                tooltip = "The number of cards you may choose to #yOverflow at the start of each combat. Set to #b" + defaultConfig.getDrawPileCardsToOverflow() + " by default.";
                DuelistDropdown overflowSelector = new DuelistDropdown(tooltip, overflowOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setDrawPileCardsToOverflow(i);
                    this.updateConfigSettings(data);
                });
                overflowSelector.setSelectedIndex(configOnLoad.getDrawPileCardsToOverflow());
                settingElements.add(overflowSelector);
                break;
            case MACHINE:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not add any #yTokens to your hand at the start of combat. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Random Tokens", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getRandomTokenToHand(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setRandomTokenToHand(button.enabled);
                    this.updateConfigSettings(data);
                }));

                LINEBREAK();

                settingElements.add(new ModLabel("Number of Tokens", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> tokenOptions = new ArrayList<>();
                for (int i = 0; i < 11; i++) {
                    tokenOptions.add(String.valueOf(i));
                }
                tooltip = "The number of random #yTokens to add to your hand at the start of combat. Set to #b" + defaultConfig.getRandomTokenAmount() + " by default.";
                DuelistDropdown tokenSelector = new DuelistDropdown(tooltip, tokenOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setRandomTokenAmount(i);
                    this.updateConfigSettings(data);
                });
                tokenSelector.setSelectedIndex(configOnLoad.getRandomTokenAmount());
                settingElements.add(tokenSelector);
                break;
            case PLANT:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not apply #yConstricted at the start of combat. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Constrict Enemies", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getApplyConstricted(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setApplyConstricted(button.enabled);
                    this.updateConfigSettings(data);
                }));

                LINEBREAK();

                settingElements.add(new ModLabel("Constricted Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> constrictOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    constrictOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of #yConstricted to apply to enemies at the start of combat. Set to #b" + defaultConfig.getConstrictedAmount() + " by default.";
                DuelistDropdown constrictSelector = new DuelistDropdown(tooltip, constrictOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setConstrictedAmount(i);
                    this.updateConfigSettings(data);
                });
                constrictSelector.setSelectedIndex(configOnLoad.getConstrictedAmount());
                settingElements.add(constrictSelector);
                break;
            case MEGATYPE:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not add random #yMonsters to your hand at the start of combat. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Random Monsters", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getAddMonsterToHand(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setAddMonsterToHand(button.enabled);
                    this.updateConfigSettings(data);
                }));

                LINEBREAK();

                settingElements.add(new ModLabel("Random Monsters", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> monsterOptions = new ArrayList<>();
                for (int i = 0; i < 11; i++) {
                    monsterOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of random #yMonsters added to your hand at the start of combat. Set to #b" + defaultConfig.getRandomMonstersToAdd() + " by default.";
                DuelistDropdown monsterSelector = new DuelistDropdown(tooltip, monsterOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setRandomMonstersToAdd(i);
                    this.updateConfigSettings(data);
                });
                monsterSelector.setSelectedIndex(configOnLoad.getRandomMonstersToAdd());
                settingElements.add(monsterSelector);
                break;
            case INCREMENT:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not trigger any #yIncrement actions. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Increment", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getIncrement(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setIncrement(button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                tooltip = "When enabled, the #yMillennium #yPuzzle will grant #b1 #yMax #ySummon for each Act. For example, in Act 3, at the start of each combat you would #yIncrement #b3. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Increment with Act", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getAmountToIncrementMatchesAct(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setAmountToIncrementMatchesAct(button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                settingElements.add(new ModLabel("Bonus Increment", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> bonusIncOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    bonusIncOptions.add(String.valueOf(i));
                }
                tooltip = "Extra #yIncrement amount to apply at the start of each combat. Stacks with #yIncrementing by Act number. Set to #b" + defaultConfig.getAmountToIncrement() + " by default.";
                DuelistDropdown bonusIncSelector = new DuelistDropdown(tooltip, bonusIncOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setAmountToIncrement(i);
                    this.updateConfigSettings(data);
                });
                bonusIncSelector.setSelectedIndex(configOnLoad.getAmountToIncrement());
                settingElements.add(bonusIncSelector);
                break;
            case BEAST:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not trigger the #yFang gain effect. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Trigger", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getFangTriggerEffect(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setFangTriggerEffect(button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                settingElements.add(new ModLabel("Number of Beast Effects to Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> beastOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    beastOptions.add(String.valueOf(i));
                }
                tooltip = "Number of times you must trigger the #yBeast #yIncrement effect before the #yFang gain effect is triggered. Set to #b" + defaultConfig.getAmountOfBeastsToTrigger() + " by default.";
                DuelistDropdown beastAmtSelector = new DuelistDropdown(tooltip, beastOptions, Settings.scale * (DuelistMod.xLabPos + 530), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setAmountOfBeastsToTrigger(i);
                    this.updateConfigSettings(data);
                });
                beastAmtSelector.setSelectedIndex(configOnLoad.getAmountOfBeastsToTrigger());
                LINEBREAK();

                settingElements.add(new ModLabel("Number of Fangs to Gain", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> fangOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    fangOptions.add(String.valueOf(i));
                }
                tooltip = "Number of #yFangs to gain when the effect is triggered. Set to #b" + defaultConfig.getFangsToGain() + " by default.";
                DuelistDropdown fangAmtSelector = new DuelistDropdown(tooltip, fangOptions, Settings.scale * (DuelistMod.xLabPos + 530), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setFangsToGain(i);
                    this.updateConfigSettings(data);
                });
                fangAmtSelector.setSelectedIndex(configOnLoad.getFangsToGain());

                settingElements.add(fangAmtSelector);
                settingElements.add(beastAmtSelector);
                break;
            case EXODIA:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not apply #ySoulbound to your deck. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Soulbound", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getApplySoulbound(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setApplySoulbound(button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                tooltip = "When disabled, the #yMillennium #yPuzzle will not prevent you from obtaining cards. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Cannot obtain cards", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getCannotObtainCards(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setCannotObtainCards(button.enabled);
                    Util.updateSelectScreenRelicList();
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                tooltip = "When disabled, the #yMillennium #yPuzzle will not automatically draw the #yHead #yof #yExodia. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Draw Exodia Head", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getDrawExodiaHead(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setDrawExodiaHead(button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();
                break;
            case WARRIOR:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant #yBlur. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Gain Blur", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getGainBlur(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setGainBlur(button.enabled);
                    this.updateConfigSettings(data);
                }));

                ArrayList<String> secondBlurOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    secondBlurOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of #yBlur to gain at the start of combat. Set to #b" + defaultConfig.getBlurToGain() + " by default.";
                DuelistDropdown secondBlurSelector = new DuelistDropdown(tooltip, secondBlurOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setBlurToGain(i);
                    this.updateConfigSettings(data);
                });
                secondBlurSelector.setSelectedIndex(configOnLoad.getBlurToGain());
                LINEBREAK();

                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant #yVigor. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Gain Vigor", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getGainVigor(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setGainVigor(button.enabled);
                    this.updateConfigSettings(data);
                }));

                ArrayList<String> vigorOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    vigorOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of #yVigor to gain at the start of combat. Set to #b" + defaultConfig.getVigorToGain() + " by default.";
                DuelistDropdown vigorSelector = new DuelistDropdown(tooltip, vigorOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setVigorToGain(i);
                    this.updateConfigSettings(data);
                });
                vigorSelector.setSelectedIndex(configOnLoad.getVigorToGain());
                LINEBREAK();

                settingElements.add(vigorSelector);
                settingElements.add(secondBlurSelector);
                break;
            case STANDARD:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant #yBlur. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Gain Blur", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getGainBlur(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setGainBlur(button.enabled);
                    this.updateConfigSettings(data);
                }));

                ArrayList<String> blurOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    blurOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of #yBlur to gain at the start of combat. Set to #b" + defaultConfig.getBlurToGain() + " by default.";
                DuelistDropdown blurSelector = new DuelistDropdown(tooltip, blurOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setBlurToGain(i);
                    this.updateConfigSettings(data);
                });
                blurSelector.setSelectedIndex(configOnLoad.getBlurToGain());

                LINEBREAK();

                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant #yBlock. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Gain Block", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getGainRandomBlock(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setGainRandomBlock(button.enabled);
                    this.updateConfigSettings(data);
                }));

                ArrayList<String> randomBlockLowOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    randomBlockLowOptions.add(String.valueOf(i));
                }
                tooltip = "The low end of the random amount of #yBlock gained at the start of combat. Set to #b" + defaultConfig.getRandomBlockLow() + " by default.";
                DuelistDropdown randomBlockLowSelector = new DuelistDropdown(tooltip, randomBlockLowOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setRandomBlockLow(i);
                    this.updateConfigSettings(data);
                });
                randomBlockLowSelector.setSelectedIndex(configOnLoad.getRandomBlockLow());

                ArrayList<String> randomBlockHighOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    randomBlockHighOptions.add(String.valueOf(i));
                }
                tooltip = "The high end of the random amount of #yBlock gained at the start of combat. Set to #b" + defaultConfig.getRandomBlockHigh() + " by default.";
                DuelistDropdown randomBlockHighSelector = new DuelistDropdown(tooltip, randomBlockHighOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setRandomBlockHigh(i);
                    this.updateConfigSettings(data);
                });
                randomBlockHighSelector.setSelectedIndex(configOnLoad.getRandomBlockHigh());

                settingElements.add(randomBlockHighSelector);
                settingElements.add(randomBlockLowSelector);
                settingElements.add(blurSelector);
                break;
            case ASCENDED_II:
            case ZOMBIE:
            case SPELLCASTER:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not #yChannel any orbs. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Channel Orbs", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getChannelShadow(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setChannelShadow(button.enabled);
                    this.updateConfigSettings(data);
                }));
                break;
            case TOON:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant #yToon #yWorld. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Toon World", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getApplyToonWorld(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setApplyToonWorld(button.enabled);
                    this.updateConfigSettings(data);
                }));
                break;
            case FIEND:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not grant any damage bonuses. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Tribute Damage Bonus", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getDamageBoost(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setDamageBoost(button.enabled);
                    this.updateConfigSettings(data);
                }));
                break;
            case INSECT:
                tooltip = "When disabled, the #yMillennium #yPuzzle will not add #yBixi to your hand. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Bixi", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, configOnLoad.getAddBixi(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setAddBixi(button.enabled);
                    this.updateConfigSettings(data);
                }));
                break;
            case PHARAOH_I:
                DuelistLabeledToggleButton effectCheckbox1 = addPharaohEffectCheckbox(configOnLoad);
                settingElements.add(effectCheckbox1);

                ArrayList<String> p1Options = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p1Options.add(String.valueOf(i));
                }
                tooltip = "The amount of #yStrength to gain when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt1(1) + " by default.";
                DuelistDropdown p1Selector = new DuelistDropdown(tooltip, p1Options, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount1(i);
                    this.updateConfigSettings(data);
                });
                p1Selector.setSelected(configOnLoad.getPharaohAmt1(1)+"");

                ArrayList<String> p1ChanceOptions = new ArrayList<>();
                for (Percentage percentage : Percentage.values()) {
                    p1ChanceOptions.add(percentage.displayName());
                }
                tooltip = "The chance the effect will trigger when a non-Duelist card is played. Set to #b" + defaultConfig.getPharaohPercentage() + " by default.";
                DuelistDropdown p1ChanceSelector = new DuelistDropdown(tooltip, p1ChanceOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohPercentage(s);
                    this.updateConfigSettings(data);
                });
                p1ChanceSelector.setSelected(configOnLoad.getPharaohPercentage());

                settingElements.add(p1ChanceSelector);
                settingElements.add(p1Selector);
                break;
            case PHARAOH_II:
                DuelistLabeledToggleButton effectCheckbox2 = addPharaohEffectCheckbox(configOnLoad);
                settingElements.add(effectCheckbox2);

                ArrayList<String> p2Options = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p2Options.add(String.valueOf(i));
                }
                tooltip = "The number of cards to draw when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt1(2) + " by default.";
                DuelistDropdown p2Selector = new DuelistDropdown(tooltip, p2Options, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount1(i);
                    this.updateConfigSettings(data);
                });
                p2Selector.setSelected(configOnLoad.getPharaohAmt1(2)+"");

                ArrayList<String> p2ChanceOptions = new ArrayList<>();
                for (Percentage percentage : Percentage.values()) {
                    p2ChanceOptions.add(percentage.displayName());
                }
                tooltip = "The chance the effect will trigger when a non-Duelist card is played. Set to #b" + defaultConfig.getPharaohPercentage() + " by default.";
                DuelistDropdown p2ChanceSelector = new DuelistDropdown(tooltip, p2ChanceOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohPercentage(s);
                    this.updateConfigSettings(data);
                });
                p2ChanceSelector.setSelected(configOnLoad.getPharaohPercentage());

                settingElements.add(p2ChanceSelector);
                settingElements.add(p2Selector);
                break;
            case PHARAOH_III:
                DuelistLabeledToggleButton effectCheckbox3 = addPharaohEffectCheckbox(configOnLoad);
                settingElements.add(effectCheckbox3);

                ArrayList<String> p3Options = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p3Options.add(String.valueOf(i));
                }
                tooltip = "The number of #yLightning to #yChannel when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt1(3) + " by default.";
                DuelistDropdown p3Selector = new DuelistDropdown(tooltip, p3Options, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount1(i);
                    this.updateConfigSettings(data);
                });
                p3Selector.setSelected(configOnLoad.getPharaohAmt1(3)+"");

                ArrayList<String> p3ChanceOptions = new ArrayList<>();
                for (Percentage percentage : Percentage.values()) {
                    p3ChanceOptions.add(percentage.displayName());
                }
                tooltip = "The chance the effect will trigger when a non-Duelist card is played. Set to #b" + defaultConfig.getPharaohPercentage() + " by default.";
                DuelistDropdown p3ChanceSelector = new DuelistDropdown(tooltip, p3ChanceOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohPercentage(s);
                    this.updateConfigSettings(data);
                });
                p3ChanceSelector.setSelected(configOnLoad.getPharaohPercentage());

                settingElements.add(p3ChanceSelector);
                settingElements.add(p3Selector);
                break;
            case PHARAOH_IV:
                DuelistLabeledToggleButton effectCheckbox4 = addPharaohEffectCheckbox(configOnLoad);
                settingElements.add(effectCheckbox4);

                ArrayList<String> p4Options = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p4Options.add(String.valueOf(i));
                }
                tooltip = "The amount of #yStrength to gain when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt1(4) + " by default.";
                DuelistDropdown p4Selector = new DuelistDropdown(tooltip, p4Options, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount1(i);
                    this.updateConfigSettings(data);
                });
                p4Selector.setSelected(configOnLoad.getPharaohAmt1(4)+"");

                ArrayList<String> p4ChanceOptions = new ArrayList<>();
                for (Percentage percentage : Percentage.values()) {
                    p4ChanceOptions.add(percentage.displayName());
                }
                tooltip = "The chance the effect will trigger when a non-Duelist card is played. Set to #b" + defaultConfig.getPharaohPercentage() + " by default.";
                DuelistDropdown p4ChanceSelector = new DuelistDropdown(tooltip, p4ChanceOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohPercentage(s);
                    this.updateConfigSettings(data);
                });
                p4ChanceSelector.setSelected(configOnLoad.getPharaohPercentage());

                settingElements.add(p4ChanceSelector);
                settingElements.add(p4Selector);
                break;
            case PHARAOH_V:
                DuelistLabeledToggleButton effectCheckbox5 = addPharaohEffectCheckbox(configOnLoad);
                settingElements.add(effectCheckbox5);

                ArrayList<String> p5Options = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p5Options.add(String.valueOf(i));
                }
                tooltip = "The amount of #yBlock to gain when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt1(5) + " by default.";
                DuelistDropdown p5Selector = new DuelistDropdown(tooltip, p5Options, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount1(i);
                    this.updateConfigSettings(data);
                });
                p5Selector.setSelected(configOnLoad.getPharaohAmt1(5)+"");

                ArrayList<String> p5ChanceOptions = new ArrayList<>();
                for (int i = 0; i < 1001; i++) {
                    p5ChanceOptions.add(String.valueOf(i));
                }
                tooltip = "The amount of damage to deal when the effect is triggered. Set to #b" + defaultConfig.getPharaohAmt2() + " by default.";
                DuelistDropdown p5ChanceSelector = new DuelistDropdown(tooltip, p5ChanceOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = this.getActiveConfig();
                    data.setPharaohAmount2(i);
                    this.updateConfigSettings(data);
                });
                p5ChanceSelector.setSelected(configOnLoad.getPharaohAmt2()+"");

                settingElements.add(p5ChanceSelector);
                settingElements.add(p5Selector);
                break;
        }

        settingElements.addAll(tokenTypeSelectors);
        return new DuelistConfigurationData(this.displayName, settingElements, this);
    }

    private DuelistLabeledToggleButton addPharaohEffectCheckbox(PuzzleConfigData configOnLoad) {
        String tooltip = "When disabled, the #yMillennium #yPuzzle will not trigger the #yPharaoh specific effect. Enabled by default. NL NL " + this.getDeckName() + ": " + pharaohEffect();
        return new DuelistLabeledToggleButton("Pharaoh Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !configOnLoad.getPharaohEffectDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
            PuzzleConfigData data = this.getActiveConfig();
            data.setPharaohEffectDisabled(!button.enabled);
            this.updateConfigSettings(data);
        });
    }

    public String getChallengeDescription() {
        switch (this) {
            case STANDARD:
                return this.getDisplayName() + ": #b50% chance to randomize the cost of Spells when drawn.";
            case DRAGON:
                return this.getDisplayName() + ": #yDragon tribute synergy effect only triggers #b50% of the time.";
            case SPELLCASTER:
                return this.getDisplayName() + ": start combat with #b2 orb slots.";
            case AQUA:
                return this.getDisplayName() + ": #yAqua tribute synergy effect only triggers #b50% of the time.";
            case FIEND:
                return this.getDisplayName() + ": When triggered, the #yFiend tribute synergy effect increases the cost of all cards in discard by #b1 for the turn.";
            case ZOMBIE:
                return this.getDisplayName() + ": The maximum amount of cards you may choose to #yRevive from is decreased from #b5 to #b3.";
            case MACHINE:
                return this.getDisplayName() + ": #rExplosive #rTokens always have a #b10% chance to damage you, and do not summon Bomb Casings when #yDetonated.";
            case INSECT:
                return this.getDisplayName() + ": #yInsect tribute synergy effect applies #yPoison to a random enemy instead of all enemies.";
            case NATURIA:
                return this.getDisplayName() + ": Enemy resistance to #yVines is increased.";
            case INCREMENT:
                return this.getDisplayName() + ": Whenever you #yIncrement, take #b1 damage.";
            case BEAST:
                return this.getDisplayName() + ": #yApex cards are only free to play on the first #b2 turns of combat.";
            case TOON:
                return this.getDisplayName() + ": #yToon #yWorld always has a damage cap #b2 points higher than normal.";
            case PLANT:
            case WARRIOR:
            case MEGATYPE:
            case CREATOR:
            case EXODIA:
            case ASCENDED_I:
            case ASCENDED_II:
            case ASCENDED_III:
            case PHARAOH_I:
            case PHARAOH_II:
            case PHARAOH_III:
            case PHARAOH_IV:
            case PHARAOH_V:
            case RANDOM_SMALL:
            case RANDOM_BIG:
            case RANDOM_UPGRADE:
            case METRONOME:
            default:
                return this.getDisplayName() + ": No deck-specific Challenge effect is implemented yet.";
        }
    }

    public String generatePuzzleDescription(Boolean hasMillenniumSymbol) {
        PuzzleConfigData config = this.getActiveConfig();
        String defaultDesc = "All #yMillennium #yPuzzle effects for this deck are #rdisabled.";
        boolean typedTokens = Util.getChallengeLevel() < 0;
        boolean blurring = config.getGainBlur() != null && config.getGainBlur() && config.getBlurToGain() > 0;
        boolean summoning = config.getTokensToSummon() > 0;
        boolean bonus = hasMillenniumSymbol == null ? PuzzleHelper.isBonusEffects() : hasMillenniumSymbol;
        boolean weakEffects = PuzzleHelper.isWeakEffects();
        boolean effectsEnabled = PuzzleHelper.isEffectsEnabled();
        boolean explosiveTokens = Util.getChallengeLevel() > 8 && Util.getChallengeLevel() < 16;
        boolean supeExplosive = Util.getChallengeLevel() > 15;
        String base = "At the start of combat, ";
        String s = config.getTokensToSummon() != 1 ? "s" : "";
        String tokenName = "#y" + getTokenFromID(config.getTokenType()).name.replaceAll("\\s", " #y");
        if (explosiveTokens) {
            tokenName = "#rExplosive #rToken";
        } else if (supeExplosive) {
            tokenName = "#rSuper #rExplosive #rToken";
        } else if (!typedTokens) {
            tokenName = "#yPuzzle #yToken";
        }
        String summonTxt = "#ySummon #b" + config.getTokensToSummon() + " " + tokenName + s;

        if (!effectsEnabled && summoning) {
            return base + summonTxt + ".";
        } else if (!effectsEnabled) {
            return defaultDesc;
        }

        switch (this) {
            case STANDARD:
                boolean randomBlocking = config.getGainRandomBlock() != null && config.getGainRandomBlock() && config.getRandomBlockHigh() > 0;
                int blurAmount = blurring ? config.getBlurToGain() + (bonus ? 1 : 0) : 0;
                boolean blurLoc = blurAmount > 0;
                if (!randomBlocking && !blurLoc && !summoning) {
                    return defaultDesc;
                }
                if (summoning) {
                    base += "#ySummon #b" + config.getTokensToSummon() + " " + tokenName;
                    if (config.getTokensToSummon() > 1) {
                        base += "s";
                    }
                }
                if (!randomBlocking && !blurLoc) {
                    return base + ".";
                }
                if (randomBlocking) {
                    if (summoning) {
                        base += ", ";
                    }
                    if (!blurLoc) {
                        base += "and ";
                    }
                    int low = config.getRandomBlockLow();
                    int high = config.getRandomBlockHigh();
                    TwoNums highLow = Util.getLowHigh(low, high);
                    int high2 = highLow.high() - (weakEffects ? 2 : 0);
                    if (high2 > 0 && highLow.low() > -1) {
                        base += "gain a random amount (" + highLow.low() + " - " + high2 + ") of #yBlock";
                    }
                    if (!blurLoc) {
                        return base + ".";
                    }
                }
                if (summoning || randomBlocking) {
                    base += ", and ";
                }
                return base + "gain #b" + blurAmount + " #yBlur.";
            case DRAGON:
                boolean anyPuzzleEffects = !config.getEffectsDisabled() && config.getEffectsToRemove() < 6 && config.getEffectsChoices() > 0;
                if (!summoning && !anyPuzzleEffects) {
                    return defaultDesc;
                }
                if (summoning && !anyPuzzleEffects) {
                    return base + summonTxt + ".";
                }
                if (!summoning) {
                    int choices = 6 - config.getEffectsToRemove();
                    if (bonus) {
                        choices++;
                    }
                    if (weakEffects) {
                        choices--;
                    }
                    if (choices > 6) {
                        choices = 6;
                    }
                    if (choices <= 0) {
                        return defaultDesc;
                    }
                    if (choices == config.getEffectsChoices()) {
                        return base + "trigger #b" + choices + " additional effects.";
                    }
                    return base + "choose #b" + config.getEffectsChoices() + " of #b" + choices + " additional effect" + (choices != 1 ? "s." : ".");
                }
                int choices = 6 - config.getEffectsToRemove();
                if (bonus) {
                    choices++;
                }
                if (weakEffects) {
                    choices--;
                }
                if (choices <= 0) {
                    return base + summonTxt + ".";
                }
                if (choices == config.getEffectsChoices()) {
                    return summonTxt + " and trigger #b" + choices + " additional effect" + (choices != 1 ? "s." : ".");
                }
                return base + summonTxt + " and choose #b" + config.getEffectsChoices() + " of #b" + choices + " additional effect" + (choices != 1 ? "s." : ".");
            case NATURIA:
                int vinesAmt = !weakEffects ? config.getStartingVines() + (bonus ? 2 : 0) : 0;
                int leavesAmt = config.getStartingLeaves() + (bonus ? 2 : 0);
                boolean anyVinesLeaves = leavesAmt > 0 || vinesAmt > 0;
                boolean vines = vinesAmt > 0;
                boolean leaves = leavesAmt > 0;
                if (!summoning && !anyVinesLeaves) {
                    return defaultDesc;
                }
                if (summoning && !anyVinesLeaves) {
                    return base + summonTxt + ".";
                }
                String vS = vinesAmt != 1 ? "s" : "";
                String lS = leavesAmt != 1 ? "Leaves" : "Leaf";
                if (!summoning) {
                    if (vines && leaves) {
                        return base + "gain #b" + vinesAmt + " #yVine" + vS + " and #b" + leavesAmt + " #y" + lS + ".";
                    } else if (vines) {
                        return base + "gain #b" + vinesAmt + " #yVine" + vS + ".";
                    } else {
                        return base + "gain #b" + leavesAmt + " #y" + lS + ".";
                    }
                }
                return base + summonTxt + ", and gain #b" + config.getStartingVines() + " #yVine" + vS + " and #b" + config.getStartingLeaves() + " #y" + lS + ".";
            case SPELLCASTER:
                boolean channelShadow = config.getChannelShadow() != null && config.getChannelShadow();
                if (channelShadow && summoning) {
                    return base + summonTxt + " and have a #b" + DuelistMod.currentSpellcasterOrbChance + "% chance to #yChannel a random Orb.";
                } else if (channelShadow) {
                    return base + "have a #b" + DuelistMod.currentSpellcasterOrbChance + "% chance to #yChannel a random Orb.";
                } else if (summoning) {
                    return base + summonTxt + ".";
                }
                return defaultDesc;
            case TOON:
                String tw = bonus ? "#yToon #yKingdom." : "#yToon #yWorld.";
                if (config.getApplyToonWorld() && summoning) {
                    return base + summonTxt + " and gain " + tw;
                } else if (config.getApplyToonWorld()) {
                    return base + "gain " + tw;
                } else if (summoning) {
                    return base + summonTxt + ".";
                }
                return defaultDesc;
            case ZOMBIE:
                boolean cs = config.getChannelShadow() != null && config.getChannelShadow();
                if (cs && summoning) {
                    return base + summonTxt + " and #yChannel a Shadow Orb.";
                } else if (cs) {
                    return base + "#yChannel a Shadow Orb.";
                } else if (summoning) {
                    return base + summonTxt + ".";
                }
                return defaultDesc;
            case AQUA:
                int overflowCards = config.getDrawPileCardsToOverflow();
                boolean overflowing = config.getOverflowDrawPile() && overflowCards > 0;
                String twice = bonus ? " twice." : ".";
                String overflowTxt = overflowCards == 1 ? "a card in your draw pile" + twice : "#b" + overflowCards + " cards in your draw pile" + twice;
                if (summoning && overflowing) {
                    return base + summonTxt + " and #yOverflow " + overflowTxt;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (overflowing) {
                    return base + "#yOverflow " + overflowTxt;
                }
                return defaultDesc;
            case FIEND:
                boolean damageBoost = config.getDamageBoost();
                String howMuch = weakEffects ? "#rhalf that much" : "that much";
                String dmgBoostTxt = "sum up the total #yTribute cost of all monsters in your draw pile and increase the damage of a random monster in you draw pile by " + howMuch + " for the rest of combat.";
                if (summoning && damageBoost) {
                    return base + summonTxt + ", and " + dmgBoostTxt;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (damageBoost) {
                    return base + dmgBoostTxt;
                }
                return defaultDesc;
            case MACHINE:
                int size = config.getRandomTokenAmount() + (weakEffects ? 2 : 4);
                String baseToken = weakEffects ? "have a #b50% chance to add a random #yToken to your hand." : "add a random #yToken to your hand.";
                if (bonus) {
                    baseToken = "choose #b" + config.getRandomTokenAmount() + " of #b" + size + " random #yTokens to add to your hand.";
                }
                if (summoning && config.getRandomTokenToHand()) {
                    return base + summonTxt + ", and " + baseToken;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (config.getRandomTokenToHand()) {
                    return base + baseToken;
                }
                return defaultDesc;
            case WARRIOR:
                int vigorAmt = (config.getVigorToGain() != null ? config.getVigorToGain() : 0) - (weakEffects ? 1 : 0);
                boolean vigor = config.getGainVigor() != null && config.getGainVigor() && vigorAmt > 0;
                int blurAmt = blurring ? config.getBlurToGain() - (weakEffects ? 1 : 0) : 0;
                boolean blurLocal = blurAmt > 0;
                int lowBlock = 0;
                int highBlock = bonus ? 10 - (weakEffects ? 4 : 0) : 0;
                String randomBlock = bonus ? " Gain a random amount #b(" + lowBlock + "-" + highBlock + ") of #yBlock." : "";
                if (summoning && blurLocal && vigor) {
                    return base + summonTxt +
                            ", gain #b" + vigorAmt + " #yVigor, and gain #b" + blurAmt + " #yBlur." + randomBlock;
                } else if (summoning && blurLocal) {
                    return base + summonTxt +
                            ", and gain #b" + blurAmt + " #yBlur." + randomBlock;
                } else if (summoning && vigor) {
                    return base + summonTxt +
                            ", and gain #b" + vigorAmt + " #yVigor." + randomBlock;
                } else if (vigor && blurLocal) {
                    return base + "gain #b" + vigorAmt + " #yVigor, and gain #b" + blurAmt + " #yBlur." + randomBlock;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (vigor) {
                    return base + "gain #b" + vigorAmt + " #yVigor." + randomBlock;
                } else if (blurLocal) {
                    return base + "gain #b" + blurAmt + " #yBlur." + randomBlock;
                }
                return defaultDesc;
            case INSECT:
                String bixi = bonus ? "an #yUpgraded copy of #yBixi": "#yBixi";
                if (summoning && config.getAddBixi()) {
                    return base + summonTxt + ", and add " + bixi + " to your hand.";
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (config.getRandomTokenToHand()) {
                    return base + "add " + bixi + " to your hand.";
                }
                return defaultDesc;
            case PLANT:
                int constr = config.getConstrictedAmount() != null ? config.getConstrictedAmount() - (weakEffects ? 1 : 0) : 0;
                if (bonus) {
                    constr += 2;
                }
                boolean constricting = config.getApplyConstricted() && constr > 0;
                if (summoning && constricting) {
                    return base + summonTxt + ", and apply #b" + constr + " #yConstricted to ALL enemies.";
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (constricting) {
                    return base + "apply #b" + constr + " #yConstricted to ALL enemies.";
                }
                return defaultDesc;
            case MEGATYPE:
                boolean addingRandomMonster = config.getAddMonsterToHand() && config.getRandomMonstersToAdd() > 0;
                String costChange = weakEffects ? "" : config.getRandomMonstersToAdd() == 1 ? " It costs #b0 on the first turn." : " They cost #b0 on the first turn.";
                String monsterSource;
                if (bonus) {
                    int sz = config.getRandomMonstersToAdd() + (weakEffects ? 1 : 2);
                    monsterSource = "choose #b" + config.getRandomMonstersToAdd() + " of #b" + sz + " random monsters to add to your hand.";
                } else if (config.getRandomMonstersToAdd() == 1) {
                    monsterSource = "add a random monster to your hand.";
                } else {
                    monsterSource = "add #b" + config.getRandomMonstersToAdd() + " random monsters to your hand.";
                }
                String monTxt = monsterSource + costChange;
                if (summoning && addingRandomMonster) {
                    return base + summonTxt + ", and " + monTxt;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (addingRandomMonster) {
                    return base + monTxt;
                }
                return defaultDesc;
            case INCREMENT:
                int bonusInc = config.getAmountToIncrement() != null ? config.getAmountToIncrement() : 0;
                int incrementAmt = config.getAmountToIncrementMatchesAct() != null && config.getAmountToIncrementMatchesAct() ? AbstractDungeon.actNum + bonusInc : bonusInc;
                if (weakEffects) {
                    incrementAmt--;
                }
                String incTxt = incrementAmt == 0 && config.getAmountToIncrementMatchesAct() != null && config.getAmountToIncrementMatchesAct() ? "#yIncrement #b1 for each Act." : incrementAmt > 0 ? "#yIncrement #b" + incrementAmt + "." : null;
                boolean incrementing = incTxt != null;
                if (summoning && incrementing) {
                    return base + summonTxt + " and " + incTxt;
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (incrementing) {
                    return base + incTxt;
                }
                return defaultDesc;
            case BEAST:
                boolean triggering = config.getFangTriggerEffect();
                boolean sTrigger = config.getAmountOfBeastsToTrigger() != 1;
                boolean sFang = config.getFangsToGain() != 1;
                int gain = config.getFangsToGain() + (bonus ? 2 : 0);
                String triggerS = sTrigger ? "s" : "";
                String fangS = sFang ? "s." : ".";
                String triggerString = "Whenever you trigger the #yBeast #yIncrement effect #b" + config.getAmountOfBeastsToTrigger() + " time" + triggerS + ", gain #b" + gain + " #yFang" + fangS;
                String summonPrefix = base + summonTxt + ".";
                if (summoning && triggering) {
                    return summonPrefix + " " + triggerString;
                } else if (summoning) {
                    return summonPrefix;
                } else if (triggering) {
                    return triggerString;
                }
                return defaultDesc;
            case CREATOR:
                if (summoning) {
                    return "At the start of combat, " + summonTxt + " and add a random combination of #yRandomized copies of #bJinzo, #bTrap #bHole and #bUltimate #bOffering to your hand.";
                }
                return "At the start of combat, add a random combination of #yRandomized copies of #bJinzo, #bTrap #bHole and #bUltimate #bOffering to your hand.";
            case EXODIA:
                boolean cannotObtainCards = config.getCannotObtainCards() != null && config.getCannotObtainCards();
                boolean soulBound = config.getApplySoulbound() != null && config.getApplySoulbound();
                boolean drawHead = config.getDrawExodiaHead() != null && config.getDrawExodiaHead();
                if (summoning && cannotObtainCards && soulBound && drawHead) {
                    return base + summonTxt + ". NL All cards in your starter deck have #ySoulbound. You cannot obtain any cards. At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (summoning && cannotObtainCards && soulBound) {
                    return base + summonTxt + ". NL All cards in your starter deck have #ySoulbound. You cannot obtain any cards.";
                }
                else if (summoning && cannotObtainCards && drawHead) {
                    return base + summonTxt + ". NL You cannot obtain any cards. NL At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (summoning && soulBound && drawHead) {
                    return base + summonTxt + ". NL All cards in your starter deck have #ySoulbound. At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (cannotObtainCards && soulBound && drawHead) {
                    return "All cards in your starter deck have #ySoulbound. You cannot obtain any cards. At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (summoning && cannotObtainCards) {
                    return base + summonTxt + ". You cannot obtain any cards.";
                }
                else if (summoning && soulBound) {
                    return base + summonTxt + ". All cards in your starter deck have #ySoulbound.";
                }
                else if (summoning && drawHead) {
                    return base + summonTxt + ". At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (cannotObtainCards && soulBound) {
                    return "All cards in your starter deck have #ySoulbound. You cannot obtain any cards.";
                }
                else if (cannotObtainCards && drawHead) {
                    return "You cannot obtain any cards. At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (soulBound && drawHead) {
                    return "All cards in your starter deck have #ySoulbound. At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                else if (summoning) {
                    return base + summonTxt + ".";
                }
                else if (cannotObtainCards) {
                    return "You cannot obtain any cards.";
                }
                else if (soulBound) {
                    return "All cards in your starter deck have #ySoulbound.";
                }
                else if (drawHead) {
                    return "At the start of each turn, draw the #yHead #yof #yExodia.";
                }
                return defaultDesc;
            case ASCENDED_I:
                if (summoning) {
                    return base + summonTxt + ".";
                }
                return defaultDesc;
            case ASCENDED_II:
                boolean channel = config.getChannelShadow() != null && config.getChannelShadow();
                if (summoning && channel) {
                    return base + summonTxt + " have a #b50% chance to #yChannel a #yShadow orb.";
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (channel) {
                    return base + "have a #b50% chance to #yChannel a #yShadow orb.";
                }
                return defaultDesc;
            case ASCENDED_III:
                return defaultDesc;
            case PHARAOH_I:
            case PHARAOH_II:
            case PHARAOH_III:
            case PHARAOH_IV:
            case PHARAOH_V:
                boolean ph = config.getPharaohEffectDisabled() != null && config.getPharaohEffectDisabled();
                if (summoning && !ph) {
                    return base + summonTxt + ". " + pharaohEffect();
                } else if (summoning) {
                    return base + summonTxt + ".";
                } else if (!ph) {
                    return pharaohEffect();
                }
                return defaultDesc;
            case RANDOM_SMALL:
            case RANDOM_BIG:
            case RANDOM_UPGRADE:
            case METRONOME:
                int low = config.getRandomSummonTokensLowEnd();
                int high = config.getRandomSummonTokensHighEnd();
                TwoNums highLow = Util.getLowHigh(low, high);
                if (highLow.high() > 0) {
                    int newLow = highLow.low() - (weakEffects ? 1 : 0);
                    int newHigh = highLow.high() - (weakEffects ? 1 : 0);
                    if (newHigh >= 1 && newLow >= 0) {
                        if (newHigh == newLow) {
                            return base + "#ySummon #b" + newHigh + " " + tokenName + (newHigh == 1 ? "" : "s") + ".";
                        } else {
                            return base + "#ySummon a random amount #b(" + newLow + "-" + newHigh + ") of " + tokenName + "s" + ".";
                        }
                    }
                }
                return defaultDesc;
        }
        return "At the start of each combat, trigger an effect depending on your chosen starting deck.";
    }

    private String pharaohEffect() {
        switch (this) {
            case PHARAOH_I:
                return "Whenever you play a non-Duelist card, have a #b25% chance to gain #b1 #yStrength.";
            case PHARAOH_II:
                return "Whenever you play a non-Duelist card, have a #b25% chance to draw #b1 card.";
            case PHARAOH_III:
                return "Whenever you play a non-Duelist card, have a #b25% chance to #yChannel a #yLightning.";
            case PHARAOH_IV:
                return "Whenever you play a non-Duelist card, have a #b25% chance to gain #b1 #yMantra.";
            case PHARAOH_V:
                return "Whenever you #yTribute with matching monster types, gain #b3 #yBlock and deal #b3 damage to a random enemy.";
            default:
                return "";
        }
    }

    public static DuelistCard getTokenFromID(String tokenId) {
        return (DuelistCard)tokenMap.getOrDefault(tokenId, new PuzzleToken()).makeCopy();
    }

    public static String getTokenIdInSelectionList(String tokenName) {
        for (Map.Entry<String, DuelistCard> entry : tokenMap.entrySet()) {
            DuelistCard c = getTokenFromID(entry.getKey());
            if (c.name.equals(tokenName)) {
                return entry.getValue().cardID;
            }
        }
        return "theDuelist:PuzzleToken";
    }

    private String generateDeckHoverDescription() {
        switch (this) {
            case STANDARD:
            case DRAGON:
            case NATURIA:
            case SPELLCASTER:
            case TOON:
            case ZOMBIE:
            case AQUA:
            case FIEND:
            case MACHINE:
            case WARRIOR:
            case INSECT:
            case PLANT:
            case MEGATYPE:
            case CREATOR:
            case INCREMENT:
            case BEAST:
            case EXODIA:
            case RANDOM_SMALL:
            case RANDOM_BIG:
                break;
            case ASCENDED_I:
                return "Defeat the Heart on Ascension 5+";
            case ASCENDED_II:
                return "Defeat the Heart on Ascension 10+";
            case ASCENDED_III:
                return "Defeat the Heart on Ascension 15+";
            case PHARAOH_I:
                return "Clear Act 3 on Challenge 1+";
            case PHARAOH_II:
                return "Clear Act 3 with Pharaoh I on Ascension 5+";
            case PHARAOH_III:
                return "Clear Act 3 with any Pharaoh Deck on Ascension 10+";
            case PHARAOH_IV:
                return "Clear Act 3 with any Pharaoh Deck on Ascension 15+";
            case PHARAOH_V:
                return "Clear Act 3 with all other Pharaoh Decks on Ascension 15+";
            case RANDOM_UPGRADE:
            case METRONOME:
                return "Clear Act 3 with Random Deck (Small) or Random Deck (Big)";
        }
        return null;
    }

    public String generateSelectScreenHeader() {
        if (this.isPermanentlyLocked) {
            return "Not yet implemented";
        } else if (this.unlockLevel != null && this.unlockLevel > DuelistMod.duelistScore) {
            return "Unlocks at " + unlockLevel +  " Total Score (" + DuelistMod.duelistScore +  ")";
        }
        if (this == RANDOM_SMALL || this == RANDOM_UPGRADE) {
            return DuelistMod.randomDeckSmallSize + " cards";
        } else if (this == RANDOM_BIG) {
            return DuelistMod.randomDeckBigSize + " cards";
        }
        return this.startingDeckSize + " cards";
    }

    public int generatePortraitIndex() {
        switch (this) {
            case DRAGON:
                return 1;
            case SPELLCASTER:
                return 3;
            case AQUA:
                return 6;
            case FIEND:
                return 7;
            case ZOMBIE:
                return 5;
            case MACHINE:
                return 8;
            case WARRIOR:
                return 9;
            case TOON:
                return 4;
            case INSECT:
                return 11;
            case PLANT:
                return 10;
            case NATURIA:
                return 2;
            case MEGATYPE:
                return 13;
            case BEAST:
                return 16;
            case EXODIA:
                return 17;
            default:
                return 0;
        }
    }

    public PuzzleConfigData getActiveConfig() {
        return DuelistMod.persistentDuelistData.PuzzleConfigurations.getPuzzleConfigurations().getOrDefault(this.deckId, getDefaultPuzzleConfig());
    }

    private Integer generateUnlockLevel() {
        switch (this.ordinal()) {
            case 0: return 0;
            case 1: return 500;
            case 2: return 1_500;
            case 3: return 2_500;
            case 4: return 3_500;
            case 5: return 5_000;
            case 6: return 6_500;
            case 7: return 8_000;
            case 8: return 10_000;
            case 9: return 12_000;
            case 10: return 14_000;
            case 11: return 16_000;
            case 12: return 20_000;
            case 13: return 25_000;
            case 14: return 27_500;
            case 15: return 30_000;
            case 16: return 35_000;
            default: return null;
        }
    }

    private void loadColoredPool() {
        if (this.coloredPoolMapper == null) {
            this.resetColoredPool();
        }
    }

    public void addToColoredPool(ArrayList<AbstractCard> cardsToAdd) {
        ArrayList<AbstractCard> pool = this.coloredPool.pool();
        pool.addAll(cardsToAdd);
        this.liveColoredPool = () -> pool;
        this.coloredPoolMapper = new CardPoolMapper(pool);
        this.coloredSize = this.coloredPoolMapper.size();
    }

    public void forceSetColoredPool(ArrayList<AbstractCard> newPool) {
        this.liveColoredPool = () -> newPool;
        this.coloredPoolMapper = new CardPoolMapper(newPool);
        this.coloredSize = this.coloredPoolMapper.size();
    }

    public void resetColoredPool() {
        this.liveColoredPool = this.coloredPool;
        this.coloredPoolMapper = new CardPoolMapper(this.coloredPool);
        this.coloredSize = this.coloredPoolMapper.size();
    }

    private void loadBasicPool() {
        if (this.basicPoolMapper == null) {
            this.basicPoolMapper = new CardPoolMapper(this.basicPool);
            this.basicSize = this.basicPoolMapper.size();
        }
    }

    public Integer getColoredSize() {
        this.loadColoredPool();
        return coloredSize;
    }

    public Integer getBasicSize() {
        this.loadBasicPool();
        return basicSize;
    }

    public ArrayList<AbstractCard> coloredPoolCopies() {
        this.loadColoredPool();
        ArrayList<AbstractCard> copies = new ArrayList<>();
        for (AbstractCard c : this.liveColoredPool.pool()) {
            copies.add(c.makeCopy());
        }
        return copies;
    }

    public ArrayList<AbstractCard> basicPoolCopies() {
        this.loadBasicPool();
        ArrayList<AbstractCard> copies = new ArrayList<>();
        for (AbstractCard c : this.basicPool.pool()) {
            copies.add(c.makeCopy());
        }
        return copies;
    }

    public ArrayList<AbstractCard> startingDeck() {
        if (this.startingDeck == null) {
            ArrayList<AbstractCard> fake = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                fake.add(new BlueEyes());
            }
            return fake;
        }
        return this.startingDeck;
    }

    public ArrayList<AbstractCard> startingDeckCopies() {
        ArrayList<AbstractCard> copies = new ArrayList<>();
        for (AbstractCard c : this.startingDeck) {
            copies.add(c.makeCopy());
        }
        return copies;
    }

    public int cardCopies(String cardId, PoolType poolType) {
        if (poolType == null) return 0;
        switch (poolType) {
            case COLORED:
                this.loadColoredPool();
                return this.coloredPoolMapper.getNumberOfCopies(cardId);
            case BASIC:
                this.loadBasicPool();
                return this.basicPoolMapper.getNumberOfCopies(cardId);
            case STARTER:
                return this.startingDeckMapper.getNumberOfCopies(cardId);
            default:
                return 0;
        }
    }

    public int cardCopies(AbstractCard card, PoolType poolType) {
        if (poolType == null) return 0;
        switch (poolType) {
            case COLORED:
                this.loadColoredPool();
                return this.coloredPoolMapper.getNumberOfCopies(card);
            case BASIC:
                this.loadBasicPool();
                return this.basicPoolMapper.getNumberOfCopies(card);
            case STARTER:
                return this.startingDeckMapper.getNumberOfCopies(card);
            default:
                return 0;
        }
    }

    public String getDeckName() {
        return deckName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CardTags getStartingDeckTag() {
        return startingDeckTag;
    }

    public CardTags getPrimaryType() {
        return primaryType;
    }

    public List<CardTags> getAllTypes() {
        return allTypes;
    }

    public DuelistConfigurationData getConfigMenu() {
        return generateConfigMenu();
    }

    public String getDeckHoverDescription() {
        return generateDeckHoverDescription();
    }

    public Integer getUnlockLevel() {
        return unlockLevel != null ? unlockLevel : 0;
    }

    public void setStartingDeck(ArrayList<AbstractCard> startingDeck) {
        this.startingDeck = startingDeck;
        this.startingDeckIds.clear();
        for (AbstractCard card : startingDeck) {
            this.startingDeckIds.add(card.cardID);
        }
    }

    public boolean isCardInStartingDeck(String cardId) {
        return this.startingDeckIds.contains(cardId);
    }

    public boolean isPermanentlyLocked() {
        return isPermanentlyLocked;
    }

    public String getDeckId() {
        return deckId;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void show() {
        this.isHidden = false;
    }

    public void hide() {
        this.isHidden = true;
    }

    public Texture getPortrait() {
        return TheDuelist.GetCharacterPortrait(this.generatePortraitIndex());
    }

    private void LINEBREAK() { DuelistMod.linebreak(); }

    @SuppressWarnings("unused")
    private void LINEBREAK(int extra) {
        DuelistMod.linebreak(extra);
    }

    private void RESET_Y() {
        DuelistMod.yPos = DuelistMod.startingYPos;
    }

    public boolean isLocked() {
        if (this.isPermanentlyLocked) return true;
        if (DuelistMod.persistentDuelistData.GameplaySettings.getUnlockAllDecks()) return false;

        boolean anyDeckUnlockProgressDeck = Util.deckIs(RANDOM_UPGRADE.getDeckName(), METRONOME.getDeckName(), ASCENDED_I.getDeckName(), ASCENDED_II.getDeckName(), ASCENDED_III.getDeckName(), PHARAOH_I.getDeckName(), PHARAOH_II.getDeckName(), PHARAOH_III.getDeckName(), PHARAOH_IV.getDeckName(), PHARAOH_V.getDeckName());
        return anyDeckUnlockProgressDeck
                ? !DuelistMod.checkDeckUnlockProgressForDeck(this)
                : this.getUnlockLevel() > DuelistMod.duelistScore;
    }

    public static LoadoutUnlockOrderInfo getNextUnlockDeckAndScore(int currentScore) {
        String firstUnlock = null;
        Integer secondUnlock = null;
        for (Map.Entry<String, Integer> entry : StartingDeck.unlockOrderInfo.entrySet()) {
            if (firstUnlock == null) {
                firstUnlock = entry.getKey();
                continue;
            }
            secondUnlock = entry.getValue();
            break;
        }

        if (currentScore < StartingDeck.unlockOrderInfo.get(firstUnlock)) {
            return new LoadoutUnlockOrderInfo(firstUnlock, StartingDeck.unlockOrderInfo.get(firstUnlock), secondUnlock);
        }

        LoadoutUnlockOrderInfo ret = null;
        for (Map.Entry<String, Integer> entry : StartingDeck.unlockOrderInfo.entrySet()) {
            if (ret != null) {
                ret.setNextCost(entry.getValue());
                return ret;
            }
            if (entry.getValue() > currentScore) {
                ret = new LoadoutUnlockOrderInfo(entry.getKey(), entry.getValue());
            }
        }
        return ret == null ? new LoadoutUnlockOrderInfo("ALL DECKS UNLOCKED", currentScore) : ret;
    }

    private static boolean randomUpgradeDeckChecker(int lastRoll, boolean arcane) {
        if (arcane) {
            int rollCheck = 1;
            int maxChance = 15;
            if (lastRoll > (maxChance - rollCheck+1)) {
                return false;
            }
            int roll = ThreadLocalRandom.current().nextInt(1, maxChance);
            return roll > (rollCheck + lastRoll);
        } else {
            int rollCheck = 2;
            int maxChance = 10;
            if (lastRoll > (maxChance - rollCheck+1)) {
                return false;
            }
            int roll = ThreadLocalRandom.current().nextInt(1, maxChance);
            return roll > (rollCheck + lastRoll);
        }
    }

    private static ArrayList<AbstractCard> getRandomDeckCardPossibilities() {
        ArrayList<AbstractCard> newRandomCardList = new ArrayList<>();
        for (AbstractCard c : DuelistMod.myCards) {
            if (!c.hasTag(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) {
                boolean toonCard = c.hasTag(Tags.TOON_POOL);
                boolean ojamaCard = c.hasTag(Tags.OJAMA);
                boolean exodiaCard = c.hasTag(Tags.EXODIA);
                boolean creatorCard = (c instanceof TheCreator || c instanceof DarkCreator);
                if (toonCard && !DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveToons()) {
                    newRandomCardList.add(c.makeCopy());
                } else if (ojamaCard && !DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveOjama()) {
                    newRandomCardList.add(c.makeCopy());
                } else if (exodiaCard && !DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveExodia()) {
                    newRandomCardList.add(c.makeCopy());
                } else if (creatorCard && !DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveCreator()) {
                    newRandomCardList.add(c.makeCopy());
                } else if (!toonCard && !creatorCard && !ojamaCard && !exodiaCard) {
                    newRandomCardList.add(c.makeCopy());
                }
            }
        }
        return newRandomCardList;
    }

    private static AbstractCard getRandomCardFromRandomSet(ArrayList<AbstractCard> toPullFrom) {
        ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
        for (AbstractCard card : toPullFrom) {
            if (card instanceof DuelistCard && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) {
                dragonGroup.add(card.makeCopy());
            }
        }
        if (dragonGroup.size() > 0) {
            return dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
        } else {
            return new Token();
        }
    }

    public static ArrayList<AbstractCard> getStartingCardsForRandomDeck() {
        ArrayList<AbstractCard> output = new ArrayList<>();
        int cards = StartingDeck.currentDeck == StartingDeck.RANDOM_BIG ? 15 : 10;
        ArrayList<AbstractCard> randomSet = getRandomDeckCardPossibilities();
        switch (StartingDeck.currentDeck) {
            case RANDOM_SMALL:
            case RANDOM_BIG:
            case RANDOM_UPGRADE:
                int lastRoll = 0;
                for (int i = 0; i < cards; i++) {
                    AbstractCard card = getRandomCardFromRandomSet(randomSet);
                    if (StartingDeck.currentDeck == StartingDeck.RANDOM_UPGRADE) {
                        card.upgrade();
                        while (randomUpgradeDeckChecker(lastRoll, card.hasTag(Tags.ARCANE)) && card.canUpgrade()) {
                            card.upgrade(); lastRoll++;
                        }
                    }
                    output.add(card);
                    lastRoll = 0;
                }
                StartingDeck.currentDeck.setStartingDeck(output);
                return output;
            default: return new ArrayList<>();
        }
    }

    public int getDeckScore() {
        PuzzleConfigData config = this.getActiveConfig();
        if (config != null) {
            StartingDeckStats stats = config.getStats();
            if (stats != null) {
                return stats.getScore() != null ? stats.getScore() : 0;
            }
        }
        return 0;
    }

    static {
        unlockOrderInfo = new LinkedHashMap<>();
        tokenMap = new LinkedHashMap<>();
        nonHidden = new ArrayList<>();
        selectScreenList = new ArrayList<>();
        refreshSelectScreen(null);
        for (StartingDeck deck : StartingDeck.values()) {
            if (deck.unlockLevel == null) break;
            if (deck != STANDARD) {
                unlockOrderInfo.put(deck.deckName, deck.unlockLevel);
            }
        }
        for (StartingDeck deck : StartingDeck.values()) {
            if (!deck.isHidden()) {
                nonHidden.add(deck);
            }
        }
    }
}
