package duelistmod.enums;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.AquaToken;
import duelistmod.cards.other.tokens.BonanzaToken;
import duelistmod.cards.other.tokens.DragonToken;
import duelistmod.cards.other.tokens.ExodiaToken;
import duelistmod.cards.other.tokens.FiendToken;
import duelistmod.cards.other.tokens.InsectToken;
import duelistmod.cards.other.tokens.MachineToken;
import duelistmod.cards.other.tokens.MegatypeToken;
import duelistmod.cards.other.tokens.NatureToken;
import duelistmod.cards.other.tokens.PlantToken;
import duelistmod.cards.other.tokens.PuzzleToken;
import duelistmod.cards.other.tokens.SpellcasterToken;
import duelistmod.cards.other.tokens.StanceToken;
import duelistmod.cards.other.tokens.ToonToken;
import duelistmod.cards.other.tokens.ZombieToken;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.builders.PuzzleConfigDataBuilder;
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
import duelistmod.helpers.poolhelpers.OjamaPool;
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
import duelistmod.interfaces.CardPoolMapper;
import duelistmod.relics.MillenniumSymbol;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public enum StartingDecks {

    STANDARD("standard", "Standard Deck", "Standard", null, Tags.STANDARD_DECK, StandardPool::deck, StandardPool::basic, false, false),
    DRAGON("dragon", "Dragon Deck", "Dragon", Tags.DRAGON, Tags.DRAGON_DECK, DragonPool::deck, DragonPool::basic, false, false),
    SPELLCASTER("spellcaster", "Spellcaster Deck", "Spellcaster", Tags.SPELLCASTER, Tags.SPELLCASTER_DECK, SpellcasterPool::deck, SpellcasterPool::basic, false, false),
    AQUA("aqua", "Aqua Deck", "Aqua", Tags.AQUA, Tags.AQUA_DECK, AquaPool::deck, AquaPool::basic, false, false),
    FIEND("fiend", "Fiend Deck", "Fiend", Tags.FIEND, Tags.FIEND_DECK, FiendPool::deck, FiendPool::basic, false, false),
    ZOMBIE("zombie", "Zombie Deck", "Zombie", Tags.ZOMBIE, Tags.ZOMBIE_DECK, ZombiePool::deck, ZombiePool::basic, false, false),
    MACHINE("machine", "Machine Deck", "Machine", Tags.MACHINE, Tags.MACHINE_DECK, MachinePool::deck, MachinePool::basic, false, false),
    INSECT("insect", "Insect Deck", "Insect", Tags.INSECT, Tags.INSECT_DECK, InsectPool::deck, InsectPool::basic, false, false),
    PLANT("plant", "Plant Deck", "Plant", Tags.PLANT, Tags.PLANT_DECK, PlantPool::deck, PlantPool::basic, false, false),
    NATURIA("naturia", "Naturia Deck", "Naturia", Tags.NATURIA, Tags.NATURIA_DECK, NaturiaPool::deck, NaturiaPool::basic, false, false,Tags.INSECT, Tags.PLANT, Tags.PREDAPLANT),
    WARRIOR("warrior", "Warrior Deck", "Warrior", Tags.WARRIOR, Tags.WARRIOR_DECK, WarriorPool::deck, WarriorPool::basic, false, false, Tags.SUPERHEAVY),
    MEGATYPE("megatype", "Megatype Deck", "Megatype", Tags.MEGATYPED, Tags.MEGATYPE_DECK, MegatypePool::deck, MegatypePool::basic, false, false),
    INCREMENT("increment", "Increment Deck", "Increment", null, Tags.INCREMENT_DECK, IncrementPool::deck, IncrementPool::basic, false, false),
    CREATOR("creator", "Creator Deck", "Creator", null, Tags.CREATOR_DECK, CreatorPool::deck, CreatorPool::basic, false, false),
    TOON("toon", "Toon Deck", "Toon", Tags.TOON_POOL, Tags.TOON_DECK, ToonPool::deck, ToonPool::basic, false, false),
    OJAMA("ojama", "Ojama Deck", "Ojama", Tags.OJAMA, Tags.OJAMA_DECK, OjamaPool::deck, OjamaPool::basic, false, false),
    EXODIA("exodia", "Exodia Deck", "Exodia", Tags.EXODIA, Tags.EXODIA_DECK, ExodiaPool::deck, ExodiaPool::basic, false, false),
    ASCENDED_I("a1", "Ascended I", "Ascended I", null, Tags.ASCENDED_ONE_DECK, AscendedOnePool::deck, AscendedOnePool::basic, false, false),
    ASCENDED_II("a2", "Ascended II", "Ascended II", null, Tags.ASCENDED_TWO_DECK, AscendedTwoPool::deck, AscendedTwoPool::basic, false, false),
    ASCENDED_III("a3", "Ascended III", "Ascended III", null, Tags.ASCENDED_THREE_DECK, AscendedThreePool::deck, AscendedThreePool::basic, !DuelistMod.isAscendedDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV, false),
    PHARAOH_I("p1", "Pharaoh I", "Pharaoh I", null, Tags.PHARAOH_ONE_DECK, PharaohPool::pharaohOne, PharaohPool::basic, !DuelistMod.isPharaohDeckOneUnlocked && DuelistMod.modMode != Mode.DEV, false),
    PHARAOH_II("p2", "Pharaoh II", "Pharaoh II", null, Tags.PHARAOH_TWO_DECK, PharaohPool::pharaohTwo, PharaohPool::basic, !DuelistMod.isPharaohDeckTwoUnlocked && DuelistMod.modMode != Mode.DEV, false),
    PHARAOH_III("p3", "Pharaoh III", "Pharaoh III", null, Tags.PHARAOH_THREE_DECK, PharaohPool::pharaohThree, PharaohPool::basic, !DuelistMod.isPharaohDeckThreeUnlocked && DuelistMod.modMode != Mode.DEV, false),
    PHARAOH_IV("p4", "Pharaoh IV", "Pharaoh IV", null, Tags.PHARAOH_FOUR_DECK, PharaohPool::pharaohFour, PharaohPool::basic, !DuelistMod.isPharaohDeckFourUnlocked && DuelistMod.modMode != Mode.DEV, false),
    PHARAOH_V("p5", "Pharaoh V", "Pharaoh V", null, Tags.PHARAOH_FIVE_DECK, PharaohPool::pharaohFive, PharaohPool::basic, !DuelistMod.isPharaohDeckFiveUnlocked && DuelistMod.modMode != Mode.DEV, false),
    RANDOM_SMALL("rds", "Random Deck (Small)", "Random - Small", null, Tags.RANDOM_DECK_SMALL, RandomSmallPool::deck, RandomSmallPool::basic, false, false),
    RANDOM_BIG("rdb", "Random Deck (Big)", "Random - Big", null, Tags.RANDOM_DECK_BIG, RandomBigPool::deck, RandomBigPool::basic, false, false),
    RANDOM_UPGRADE("rdu", "Upgrade Deck", "Upgrade", null, Tags.RANDOM_DECK_UPGRADE, RandomUpgradePool::deck, RandomUpgradePool::basic, false, false),
    METRONOME("metronome", "Metronome Deck", "Metronome", Tags.METRONOME, Tags.METRONOME_DECK, RandomMetronomePool::deck, RandomMetronomePool::basic, false, false);

    private int portraitIndex;
    private boolean showDeckHoverDescription;
    private final boolean isPermanentlyLocked;
    private boolean isHidden;
    private final Integer unlockLevel;
    private final CardTags primaryType;
    private final CardTags startingDeckTag;
    private final String deckId;
    private final String deckName;
    private final String displayName;
    private String deckHoverDescription;
    private final List<CardTags> allTypes;
    private final CardPoolLoader coloredPool;
    private final CardPoolLoader basicPool;
    private ArrayList<AbstractCard> startingDeck;
    private DuelistConfigurationData configMenu;

    private Integer coloredSize;
    private Integer basicSize;
    private Integer startingDeckSize;
    private String selectScreenHeader;
    private String puzzleDescription;
    private PuzzleConfigData configurations;
    private CardPoolMapper coloredPoolMapper;
    private CardPoolMapper basicPoolMapper;
    private CardPoolMapper startingDeckMapper;

    public static final LinkedHashMap<String, Integer> unlockOrderInfo;
    public static final ArrayList<StartingDecks> nonHidden;
    public static final LinkedHashMap<String, DuelistCard> tokenMap;

    StartingDecks(String deckId, String deckName, String displayName, CardTags primaryType, CardTags startingDeckTag, CardPoolLoader coloredPool, CardPoolLoader basicPool, boolean permaLocked, boolean isHidden, CardTags ... allTypes) {
        this.deckId = deckId;
        this.startingDeckTag = startingDeckTag;
        this.unlockLevel = generateUnlockLevel();
        this.isPermanentlyLocked = permaLocked;
        this.primaryType = primaryType;
        this.deckName = deckName;
        this.displayName = displayName;
        this.coloredPool = coloredPool;
        this.basicPool = basicPool;
        this.allTypes = allTypes != null ? new ArrayList<>(Arrays.asList(allTypes)) : new ArrayList<>();
        this.allTypes.add(primaryType);
    }

    public static void refreshStartingDecksData() {
        HashMap<StartingDecks, ArrayList<AbstractCard>> decks = new HashMap<>();
        ArrayList<DuelistCard> toSort = DuelistCardLibrary.getAllDuelistTokens();
        toSort.sort(Comparator.comparing(a -> a.name));
        for (DuelistCard token : toSort) {
            tokenMap.put(token.cardID, token);
        }
        for (DuelistCard card : DuelistMod.myCards) {
            for (StartingDecks deck : StartingDecks.values()) {
                if (card.hasTag(deck.startingDeckTag)) {
                    int copies = card.startingCopies.getOrDefault(deck, 1);
                    for (int i = 0; i < copies; i++) {
                        decks.compute(deck, (k, v) -> {
                            if (v == null) {
                                ArrayList<AbstractCard> l = new ArrayList<>();
                                l.add(card.makeCopy());
                                return l;
                            } else {
                                v.add(card.makeCopy());
                                return v;
                            }
                        });
                    }
                }
            }
        }

        for (StartingDecks deck : StartingDecks.values()) {
            if (decks.containsKey(deck)) {
                deck.startingDeck = decks.get(deck);
                deck.setupStartingDeckInfo();
            }
            deck.postConstructionSetup();
        }
    }

    private void postConstructionSetup() {
        this.portraitIndex = generatePortraitIndex();
        this.configurations = getActiveConfig();
        this.configMenu = generateConfigMenu();
        this.puzzleDescription = generatePuzzleDescription();
        this.deckHoverDescription = generateDeckHoverDescription();
        this.showDeckHoverDescription = this.deckHoverDescription != null && !this.deckHoverDescription.equals("");
    }

    private void setupStartingDeckInfo() {
        this.startingDeckMapper = new CardPoolMapper(this.startingDeck);
        this.startingDeckSize = this.startingDeckMapper.size();
        this.selectScreenHeader = generateSelectScreenHeader();
    }

    public PuzzleConfigData getDefaultPuzzleConfig() {
        PuzzleConfigDataBuilder builder = new PuzzleConfigDataBuilder();
        builder = builder.setDeck(this.deckName);
        builder = builder.setTokensToSummon(1);
        builder = builder.setTokenType(new PuzzleToken().cardID);
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
                builder = builder.setTokenType(new DragonToken().cardID);
                break;
            case NATURIA:
                builder = builder.setStartingLeaves(1);
                builder = builder.setStartingVines(1);
                builder = builder.setTokenType(new NatureToken().cardID);
                break;
            case SPELLCASTER:
                builder = builder.setTokenType(new SpellcasterToken().cardID);
                builder = builder.setChannelShadow(true);
                break;
            case TOON:
                builder = builder.setTokenType(new ToonToken().cardID);
                builder = builder.setApplyToonWorld(true);
                break;
            case ZOMBIE:
                builder = builder.setTokenType(new ZombieToken().cardID);
                builder = builder.setChannelShadow(true);
                break;
            case AQUA:
                builder = builder.setTokenType(new AquaToken().cardID);
                builder = builder.setOverflowDrawPile(true);
                builder = builder.setDrawPileCardsToOverflow(1);
                break;
            case FIEND:
                builder = builder.setTokenType(new FiendToken().cardID);
                builder = builder.setDamageBoost(true);
                break;
            case MACHINE:
                builder = builder.setTokenType(new MachineToken().cardID);
                builder = builder.setRandomTokenToHand(true);
                break;
            case WARRIOR:
                builder = builder.setTokenType(new StanceToken().cardID);
                builder = builder.setGainVigor(true);
                builder = builder.setGainBlur(true);
                builder = builder.setBlurToGain(2);
                builder = builder.setVigorToGain(3);
                break;
            case INSECT:
                builder = builder.setTokenType(new InsectToken().cardID);
                builder = builder.setAddBixi(true);
                break;
            case PLANT:
                builder = builder.setTokenType(new PlantToken().cardID);
                builder = builder.setApplyConstricted(true);
                break;
            case MEGATYPE:
                builder = builder.setTokenType(new MegatypeToken().cardID);
                builder = builder.setAddMonsterToHand(true);
                break;
            case INCREMENT:
                builder = builder.setTokenType(new PuzzleToken().cardID);
                builder = builder.setIncrement(true);
                builder = builder.setAmountToIncrementMatchesAct(true);
                builder = builder.setAmountToIncrement(0);
                break;
            case CREATOR:
                break;
            case OJAMA:
                builder = builder.setTokenType(new BonanzaToken().cardID);
                builder = builder.setGainRandomBuff(true);
                break;
            case EXODIA:
                builder = builder.setTokenType(new ExodiaToken().cardID);
                builder = builder.setApplySoulbound(true);
                builder = builder.setDrawExodiaHead(true);
                break;
            case ASCENDED_I:
                builder = builder.setTokenType(new MegatypeToken().cardID);
                break;
            case ASCENDED_II:
                builder = builder.setTokenType(new MegatypeToken().cardID);
                builder = builder.setChannelShadow(true);
                break;
            case ASCENDED_III:
                builder = builder.setTokenType(new MegatypeToken().cardID);
                break;
            case PHARAOH_I:
            case PHARAOH_II:
            case PHARAOH_III:
            case PHARAOH_IV:
            case PHARAOH_V:
                builder = builder.setTokenType(new PuzzleToken().cardID);
                builder = builder.setPharaohEffectDisabled(false);
                break;
            case RANDOM_SMALL:
            case RANDOM_BIG:              
            case RANDOM_UPGRADE:        
            case METRONOME:
                builder = builder.setTokenType(new PuzzleToken().cardID);
                break;
        }
        return builder.createPuzzleConfigData();
    }

    private void updateConfigSettings(PuzzleConfigData data) {
        DuelistMod.puzzleConfigSettingsMap.put(this.deckId, data);
        this.updateConfigurations(data);
        try
        {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            String puzzleConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.puzzleConfigSettingsMap);
            config.setString("puzzleConfigSettingsMap", puzzleConfigMap);
            config.save();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private ArrayList<DuelistDropdown> tokenTypeSelector(ArrayList<IUIElement> settingElements) {
        PuzzleConfigData configOnLoad = getActiveConfig();
        PuzzleConfigData defaultConfig = getDefaultPuzzleConfig();
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
            PuzzleConfigData data = DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, this.getDefaultPuzzleConfig());
            data.setTokenType(getTokenIdInSelectionList(s));
            this.updateConfigSettings(data);
        });
        tokenTypeSelector.setSelectedIndex(tokenIndex);

        ArrayList<String> tokensToSummonOptions = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            tokensToSummonOptions.add(i+"");
        }
        tooltip = "The number of tokens to be #ySummoned by the #yMillennium #yPuzzle at the start of combat. Set to " + this.getDefaultPuzzleConfig().getTokensToSummon() + " by default.";
        DuelistDropdown tokenNumberSelector = new DuelistDropdown(tooltip, tokensToSummonOptions, Settings.scale * (DuelistMod.xLabPos + 490 + 300), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            PuzzleConfigData data = DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, this.getDefaultPuzzleConfig());
            data.setTokensToSummon(i);
            this.updateConfigSettings(data);
        });
        tokenNumberSelector.setSelectedIndex(configOnLoad.getTokensToSummon());

        dropdowns.add(tokenNumberSelector);
        dropdowns.add(tokenTypeSelector);
        return dropdowns;
    }

    private DuelistConfigurationData generateConfigMenu() {
        if (this.isHidden) {
            return null;
        }
        RESET_Y(); LINEBREAK();LINEBREAK();LINEBREAK();LINEBREAK();
        String tooltip = "";
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        PuzzleConfigData configOnLoad = getActiveConfig();
        PuzzleConfigData defaultConfig = getDefaultPuzzleConfig();

        ArrayList<DuelistDropdown> tokenTypeSelectors = tokenTypeSelector(settingElements);

        LINEBREAK(45);

        switch (this) {
            case STANDARD:
                //settingElements.add(new ModLabel("No deck-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                break;
            case DRAGON:
                tooltip = "When enabled, the #yMillennium #yPuzzle will allow you to choose special #yDragon effects at the start of combat. Enabled by default.";
                settingElements.add(new DuelistLabeledToggleButton("Start of Combat Effects", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !configOnLoad.getEffectsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) -> {
                    PuzzleConfigData data = DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, this.getDefaultPuzzleConfig());
                    data.setEffectsDisabled(!button.enabled);
                    this.updateConfigSettings(data);
                }));
                LINEBREAK();

                settingElements.add(new ModLabel("Effects to Choose", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> choicesOptions = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    choicesOptions.add(i+"");
                }
                tooltip = "The number of special effects to choose at the start of combat. Set to #b" + defaultConfig.getEffectsChoices() + " by default.";
                DuelistDropdown effectsChoicesSelector = new DuelistDropdown(tooltip, choicesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, this.getDefaultPuzzleConfig());
                    data.setEffectsChoices(i);
                    this.updateConfigSettings(data);
                });
                effectsChoicesSelector.setSelectedIndex(configOnLoad.getEffectsChoices());

                LINEBREAK();

                settingElements.add(new ModLabel("Effects to Remove", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
                ArrayList<String> removeOptions = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    removeOptions.add(i+"");
                }
                tooltip = "Removes a random set of effects from the list of selections each combat to prevent always offering the best options. NL Set to #b" + defaultConfig.getEffectsToRemove() + " by default.";
                DuelistDropdown removeOptionsSelector = new DuelistDropdown(tooltip, removeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
                    PuzzleConfigData data = DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, this.getDefaultPuzzleConfig());
                    data.setEffectsToRemove(i);
                    this.updateConfigSettings(data);
                });
                removeOptionsSelector.setSelectedIndex(configOnLoad.getEffectsToRemove());

                settingElements.add(removeOptionsSelector);
                settingElements.add(effectsChoicesSelector);
                break;
            case NATURIA:
                break;
            case SPELLCASTER:
                break;
            case TOON:
                break;
            case ZOMBIE:
                break;
            case AQUA:
                break;
            case FIEND:
                break;
            case MACHINE:
                break;
            case WARRIOR:
                break;
            case INSECT:
                break;
            case PLANT:
                break;
            case MEGATYPE:
                break;
            case INCREMENT:
                break;
            case CREATOR:
                break;
            case OJAMA:
                break;
            case EXODIA:
                break;
            case ASCENDED_I:
                break;
            case ASCENDED_II:
                break;
            case ASCENDED_III:
                break;
            case PHARAOH_I:
                break;
            case PHARAOH_II:
                break;
            case PHARAOH_III:
                break;
            case PHARAOH_IV:
                break;
            case PHARAOH_V:
                break;
            case RANDOM_SMALL:
                break;
            case RANDOM_BIG:
                break;
            case RANDOM_UPGRADE:
                break;
            case METRONOME:
                break;
        }

        settingElements.addAll(tokenTypeSelectors);
        return new DuelistConfigurationData(this.displayName, settingElements, this);
    }

    public String generatePuzzleDescription() {
        PuzzleConfigData c = this.configurations;
        String defaultDesc = "All #yMillennium #yPuzzle effects are currently #rdisabled.";
        boolean randomBlocking = c.getGainRandomBlock() != null && c.getGainRandomBlock() && c.getRandomBlockHigh() > 0;
        boolean blurring = c.getGainBlur() != null && c.getGainBlur() && c.getBlurToGain() > 0;
        boolean summoning = c.getTokensToSummon() > 0;
        String base = "At the start of combat, ";
        String s = c.getTokensToSummon() != 1 ? "s" : "";
        String tokenName = getTokenFromID(c.getTokenType()).name;
        switch (this) {
            case STANDARD:
                if (!randomBlocking && !blurring && !summoning) {
                    return defaultDesc;
                }
                if (summoning) {
                    base += "#ySummon #b" + c.getTokensToSummon() + " " + tokenName;
                    if (c.getTokensToSummon() > 1) {
                        base += "s";
                    }
                }
                if (!randomBlocking && !blurring) {
                    return base + ".";
                }
                if (randomBlocking) {
                    if (summoning) {
                        base += ", ";
                    }
                    if (!blurring) {
                        base += "and ";
                    }
                    base += "gain a random amount (" + c.getRandomBlockLow() + " - " + c.getRandomBlockHigh() + ") of #yBlock";
                    if (!blurring) {
                        return base + ".";
                    }
                }
                if (summoning || randomBlocking) {
                    base += ", and ";
                }
                return base + "gain #b" + c.getBlurToGain() + " #yBlur.";
            case DRAGON:
                boolean anyPuzzleEffects = !c.getEffectsDisabled() && c.getEffectsToRemove() < 6 && c.getEffectsChoices() > 0;
                if (!summoning && !anyPuzzleEffects) {
                    return defaultDesc;
                }
                if (summoning && !anyPuzzleEffects) {
                    return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + ".";
                }
                if (!summoning) {
                    int choices = 6 - c.getEffectsToRemove();
                    if (AbstractDungeon.player != null && AbstractDungeon.player.relics != null) {
                        if (AbstractDungeon.player.hasRelic(MillenniumSymbol.ID)) {
                            choices++;
                        }
                    }
                    if (choices > 6) {
                        choices = 6;
                    }
                    if (choices <= 0) {
                        return defaultDesc;
                    }
                    if (choices == c.getEffectsChoices()) {
                        return base + "trigger #b" + choices + " additional effects.";
                    }
                    return base + "choose #b" + c.getEffectsChoices() + " of #b" + choices + " additional effect" + (choices != 1 ? "s." : ".");
                }
                int choices = 6 - c.getEffectsToRemove();
                if (choices == c.getEffectsChoices()) {
                    return "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + " and trigger #b" + choices + " additional effect" + (choices != 1 ? "s." : ".");
                }
                return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + " and choose #b" + c.getEffectsChoices() + " of #b" + choices + " additional effect" + (c.getEffectsChoices() != 1 ? "s." : ".");
            case NATURIA:
                boolean anyVinesLeaves = c.getStartingLeaves() > 0 || c.getStartingVines() > 0;
                boolean vines = c.getStartingVines() > 0;
                boolean leaves = c.getStartingLeaves() > 0;
                if (!summoning && !anyVinesLeaves) {
                    return defaultDesc;
                }
                if (summoning && !anyVinesLeaves) {
                    return base + "#ySummon #b" + c.getTokensToSummon() +  " " + tokenName + "" + s + ".";
                }
                String vS = c.getStartingVines() != 1 ? "s" : "";
                String lS = c.getStartingLeaves() != 1 ? "Leaves" : "Leaf";
                if (!summoning) {
                    if (vines && leaves) {
                        return base + "gain #b" + c.getStartingVines() + " #yVine" + vS + " and #b" + c.getStartingLeaves() + " #y" + lS + ".";
                    } else if (vines) {
                        return base + "gain #b" + c.getStartingVines() + " #yVine" + vS + ".";
                    } else {
                        return base + "gain #b" + c.getStartingLeaves() + " #y" + lS + ".";
                    }
                }
                return base + "#ySummon #b" + c.getTokensToSummon() + " " + c.getTokenType() + s + ", and gain #b" + c.getStartingVines() + " #yVine" + vS + " and #b" + c.getStartingLeaves() + " #y" + lS + ".";
            case SPELLCASTER:
                boolean channelShadow = c.getChannelShadow() != null && c.getChannelShadow();
                if (channelShadow && c.getTokensToSummon() > 0) {
                    return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + " and have a #b" + DuelistMod.currentSpellcasterOrbChance + "% chance to #yChannel a random Orb.";
                } else if (channelShadow) {
                    return base + "have a #b" + DuelistMod.currentSpellcasterOrbChance + "% chance to #yChannel a random Orb.";
                } else if (c.getTokensToSummon() > 0) {
                    return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + ".";
                }
                return defaultDesc;
            case TOON:
                if (c.getApplyToonWorld() && c.getTokensToSummon() > 0) {
                    return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + " and gain #yToon #yWorld.";
                } else if (c.getApplyToonWorld()) {
                    return base + "gain #yToon #yWorld.";
                } else if (c.getTokensToSummon() > 0) {
                    return base + "#ySummon #b" + c.getTokensToSummon() + " " + tokenName + s + ".";
                }
                return defaultDesc;
            case ZOMBIE:
                break;
            case AQUA:
                break;
            case FIEND:
                break;
            case MACHINE:
                break;
            case WARRIOR:
                break;
            case INSECT:
                break;
            case PLANT:
                break;
            case MEGATYPE:
                break;
            case INCREMENT:
                break;
            case CREATOR:
                break;
            case OJAMA:
                break;
            case EXODIA:
                break;
            case ASCENDED_I:
                break;
            case ASCENDED_II:
                break;
            case ASCENDED_III:
                break;
            case PHARAOH_I:
                break;
            case PHARAOH_II:
                break;
            case PHARAOH_III:
                break;
            case PHARAOH_IV:
                break;
            case PHARAOH_V:
                break;
            case RANDOM_SMALL:
                break;
            case RANDOM_BIG:
                break;
            case RANDOM_UPGRADE:
                break;
            case METRONOME:
                break;
        }
        return "At the start of each combat, trigger an effect depending on your chosen starting deck.";
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
                //return "The default starting deck for The Duelist. The card pool mainly consists of Spells, with very few monster tools. Use this deck to get a little more familiar with the mechanics of DuelistMod and how they interact with the base game. Enjoy!";
                break;
            case DRAGON:
                break;
            case NATURIA:
                break;
            case SPELLCASTER:
                break;
            case TOON:
                break;
            case ZOMBIE:
                break;
            case AQUA:
                break;
            case FIEND:
                break;
            case MACHINE:
                break;
            case WARRIOR:
                break;
            case INSECT:
                break;
            case PLANT:
                break;
            case MEGATYPE:
                break;
            case INCREMENT:
                break;
            case CREATOR:
                break;
            case OJAMA:
                break;
            case EXODIA:
                break;
            case ASCENDED_I:
                return "Defeat the Heart on Ascension 10+";
            case ASCENDED_II:
                return "Defeat the Heart on Ascension 15+";
            case ASCENDED_III:
                return "Deck currently unavailable! Defeat the Heart on Ascension 20+";
            case PHARAOH_I:
                return "Deck currently unavailable! Defeat the Heart on Challenge 1+ and defeat the Heart with all three Ascended decks.";
            case PHARAOH_II:
                return "Deck currently unavailable! Defeat the Heart on Challenge 5+ and defeat the Heart with all three Ascended decks.";
            case PHARAOH_III:
                return "Deck currently unavailable! Defeat the Heart on Challenge 10+ and defeat the Heart with all three Ascended decks.";
            case PHARAOH_IV:
                return "Deck currently unavailable! Defeat the Heart on Challenge 20 and defeat the Heart with all three Ascended decks.";
            case PHARAOH_V:
                return "Deck currently unavailable! Defeat the Heart while on Challenge 20 and Ascension 20, and also defeat the Heart with all four other Pharaoh decks.";
            case RANDOM_SMALL:
                break;
            case RANDOM_BIG:
                break;
            case RANDOM_UPGRADE:
            case METRONOME:
                return "Defeat the Heart with a Random Deck";
        }
        return null;
    }

    private String generateSelectScreenHeader() {
        if (this.isPermanentlyLocked) {
            return "Locked";
        } else if (this.unlockLevel != null && this.unlockLevel < DuelistMod.duelistScore) {
            return "Unlocks at " + unlockLevel +  " Total Score (" + DuelistMod.duelistScore +  ")";
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
            case PLANT:
            case NATURIA:
                return 2;
            default:
                return 0;
        }
    }

    public PuzzleConfigData getActiveConfig() {
        return DuelistMod.puzzleConfigSettingsMap.getOrDefault(this.deckId, getDefaultPuzzleConfig());
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
            this.coloredPoolMapper = new CardPoolMapper(this.coloredPool);
            this.coloredSize = this.coloredPoolMapper.size();
        }
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

    public ArrayList<AbstractCard> coloredPool() {
        this.loadColoredPool();
        return this.coloredPool.pool();
    }

    public ArrayList<AbstractCard> basicPool() {
        this.loadBasicPool();
        return this.basicPool.pool();
    }

    public ArrayList<AbstractCard> startingDeck() {
        return this.startingDeck;
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

    public CardTags getPrimaryType() {
        return primaryType;
    }

    public String getDeckName() {
        return deckName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<CardTags> getAllTypes() {
        return allTypes;
    }

    public PuzzleConfigData getConfigurations() {
        return configurations;
    }

    public CardTags getStartingDeckTag() {
        return startingDeckTag;
    }

    public DuelistConfigurationData getConfigMenu() {
        this.configMenu = generateConfigMenu();
        return this.configMenu;
    }

    public String getPuzzleDescription() {
        return puzzleDescription;
    }

    public String getDeckHoverDescription() {
        return deckHoverDescription;
    }

    public boolean isShowDeckHoverDescription() {
        return showDeckHoverDescription;
    }

    public String getSelectScreenHeader() {
        return selectScreenHeader;
    }

    public Integer getStartingDeckSize() {
        return startingDeckSize;
    }

    public Integer getUnlockLevel() {
        return unlockLevel;
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
        return TheDuelist.GetCharacterPortrait(this.portraitIndex);
    }

    private void LINEBREAK() { DuelistMod.linebreak(); }

    private void LINEBREAK(int extra) {
        DuelistMod.linebreak(extra);
    }

    private void RESET_Y() {
        DuelistMod.yPos = DuelistMod.startingYPos;
    }

    public void updateConfigurations(PuzzleConfigData newConfiguration) {
        this.configurations = newConfiguration;
        this.puzzleDescription = generatePuzzleDescription();
    }

    static {
        unlockOrderInfo = new LinkedHashMap<>();
        tokenMap = new LinkedHashMap<>();
        nonHidden = new ArrayList<>();
        for (StartingDecks deck : StartingDecks.values()) {
            if (deck.unlockLevel == null) break;
            if (deck != STANDARD) {
                unlockOrderInfo.put(deck.deckName, deck.unlockLevel);
            }
        }
        for (StartingDecks deck : StartingDecks.values()) {
            if (!deck.isHidden()) {
                nonHidden.add(deck);
            }
        }
    }
}
