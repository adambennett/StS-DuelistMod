package duelistmod;

import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.*;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import duelistmod.actions.common.*;
import duelistmod.cards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.events.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.potions.*;
import duelistmod.powers.*;
import duelistmod.powers.incomplete.OutriggerExtensionPower;
import duelistmod.relics.*;
import duelistmod.ui.CombatIconViewer;
import duelistmod.variables.*;



@SpireInitializer @SuppressWarnings("unused")
public class DuelistMod 
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, OnPlayerDamagedSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber,
PreMonsterTurnSubscriber, PostDungeonUpdateSubscriber, StartActSubscriber, PostObtainCardSubscriber
{
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());
	public static final String MOD_ID_PREFIX = "theDuelist:";
	
	// Member fields
	private static String modName = "Duelist Mod";
	private static String modAuthor = "Nyoxide";
	private static String modDescription = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	private static String modID = "duelistmod";
	private static ArrayList<String> cardSets = new ArrayList<String>();
	static ArrayList<StarterDeck> starterDeckList = new ArrayList<StarterDeck>();
	static ArrayList<DuelistCard> deckToStartWith = new ArrayList<DuelistCard>();
	static ArrayList<AbstractCard> basicCards = new ArrayList<AbstractCard>();
	static ArrayList<DuelistCard> standardDeck = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> orbCards = new ArrayList<DuelistCard>();
	static Map<CardTags, StarterDeck> deckTagMap = new HashMap<CardTags, StarterDeck>();
	public static int setIndex = 0;
	private static final int SETS = 7;
	private static int DECKS = 20;
	static int cardCount = 75;
	static CardTags chosenDeckTag = Tags.STANDARD_DECK;
	static int randomDeckSmallSize = 10;
	static int randomDeckBigSize = 15;
	private static int saver = 0;
	private static ArrayList<IncrementDiscardSubscriber> incrementDiscardSubscribers;
	
	
	// Global Fields
	
	// Config Settings
	public static ArrayList<String> randomizedBtnStrings = new ArrayList<String>();
	public static final String PROP_TOON_BTN = "toonBtnBool";
	public static final String PROP_EXODIA_BTN = "exodiaBtnBool";
	public static final String PROP_OJAMA_BTN = "ojamaBtnBool";
	public static final String PROP_CREATOR_BTN = "creatorBtnBool";
	public static final String PROP_OLD_CHAR = "oldCharacter";
	public static final String PROP_SET = "setIndex";
	public static final String PROP_DECK = "deckIndex";
	public static final String PROP_CARDS = "cardCount";
	public static final String PROP_MODE = "modeIndex";
	public static final String PROP_RANDOMIZED_CARDS = "randomizedCardsIndex";
	public static final String PROP_RANDOMIZED_COSTS = "randomizedCostssIndex";
	public static final String PROP_RANDOMIZED_TRIBUTES = "randomizedTributesIndex";
	public static final String PROP_RANDOMIZED_SUMMONS = "randomizedSummonsIndex";
	public static final String PROP_MAX_SUMMONS = "lastMaxSummons";
	public static final String PROP_RESUMMON_DMG = "resummonDeckDamage";
	public static final String PROP_CHALLENGE = "challengeMode";
	public static final String PROP_UNLOCK = "unlockAllDecks";
	public static final String PROP_FLIP = "flipCardTags";
	public static final String PROP_RESET = "resetProg";
	public static final String PROP_DEBUG = "debug";
	public static final String PROP_NO_CHANGE_COST = "noCostChanges";
	public static final String PROP_ONLY_DEC_COST = "onlyCostDecreases";
	public static final String PROP_NO_CHANGE_TRIB = "noTributeChanges";
	public static final String PROP_ONLY_DEC_TRIB = "onlyTributeDecreases";
	public static final String PROP_NO_CHANGE_SUMM = "noSummonChanges";
	public static final String PROP_ONLY_INC_SUMM = "onlySummonIncreases";
	public static final String PROP_R_ETHEREAL = "randomizeEthereal";
	public static final String PROP_R_EXHAUST = "randomizeExhaust";
	public static final String PROP_ALWAYS_UPGRADE = "alwaysUpgrade";
	public static final String PROP_NEVER_UPGRADE = "neverUpgrade";
	public static final String PROP_BASE_GAME_CARDS = "baseGameCards";
	public static String seenString = "";
	public static String characterModel = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String defaultChar = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String oldChar = "duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml";
	public static Properties duelistDefaults = new Properties();
	public static boolean toonBtnBool = false;
	public static boolean exodiaBtnBool = false;
	public static boolean ojamaBtnBool = false;
	public static boolean creatorBtnBool = false;
	public static boolean oldCharacter = false;
	public static boolean challengeMode = false;
	public static boolean unlockAllDecks = false;
	public static boolean flipCardTags = false;
	public static boolean noCostChanges = false;
	public static boolean onlyCostDecreases = false;
	public static boolean noTributeChanges = false;
	public static boolean onlyTributeDecreases = false;
	public static boolean noSummonChanges = false;
	public static boolean onlySummonIncreases = false;
	public static boolean alwaysUpgrade = false;
	public static boolean neverUpgrade = false;
	public static boolean randomizeExhaust = true;
	public static boolean randomizeEthereal = true;
	public static boolean baseGameCards = false;
	public static ArrayList<Boolean> genericConfigBools = new ArrayList<Boolean>();
	public static int magnetSlider = 50;
	public static String etherealForCardText = "";
	public static String exhaustForCardText = "";
	public static String powerGainCardText = "";
	public static String toonWorldString = "";
	public static String needSummonsString = "";
	public static String tribString = "";
	public static String featherPhoCantUseString = "";
	public static String featherPhoCantUseStringB = "";
	public static String nutrientZString = "";
	public static String purgeString = "";
	public static String magnetString = "";	
	public static String aquaDeckString = "";
	public static String creatorDeckString = "";
	public static String dragonDeckString = "";
	public static String exodiaDeckString = "";
	public static String fiendDeckString = "";
	public static String generationDeckString = "";
	public static String healDeckString = "";
	public static String incDeckString = "";
	public static String machineDeckString = "";
	public static String magnetDeckString = "";
	public static String natureDeckString = "";
	public static String ojamaDeckString = "";
	public static String orbDeckString = "";
	public static String randomBigDeckString = "";
	public static String randomSmallDeckString = "";
	public static String resummonDeckString = "";
	public static String spellcasterDeckString = "";
	public static String standardDeckString = "";
	public static String toonDeckString = "";
	public static String zombieDeckString = "";
	public static String deckUnlockString = "";
	public static String deckUnlockStringB = "";	
	public static String monsterTagString = "";
	public static String spellTagString = "";
	public static String trapTagString = "";
	public static String tokenTagString = "";
	public static String typeTagString = "";
	public static String orbTagString = "";	
	public static String exodiaAlmostAllString = "";
	public static String exodiaBothArmsString = "";
	public static String exodiaBothLegsString = "";
	public static String exodiaLeftArmString = "";
	public static String exodiaRightArmString = "";
	public static String exodiaLeftLegString = "";
	public static String exodiaRightLegString = "";	
	
	// Maps and Lists
	public static HashMap<String, DuelistCard> summonMap = new HashMap<String, DuelistCard>();
	public static HashMap<String, AbstractPower> buffMap = new HashMap<String, AbstractPower>();
	public static HashMap<String, AbstractOrb> invertStringMap = new HashMap<String, AbstractOrb>();
	public static HashMap<String, StarterDeck> starterDeckNamesMap = new HashMap<String, StarterDeck>();
	public static HashMap<CardTags, String> typeCardMap_ID = new HashMap<CardTags, String>();
	public static HashMap<CardTags, String> typeCardMap_IMG = new HashMap<CardTags, String>();
	public static HashMap<CardTags, String> typeCardMap_NAME = new HashMap<CardTags, String>();
	public static HashMap<String, CardTags> typeCardMap_NameToString = new HashMap<String, CardTags>();
	public static HashMap<CardTags, String> typeCardMap_DESC = new HashMap<CardTags, String>();
	public static HashMap<CardTags, Integer> monsterTypeTributeSynergyFunctionMap = new HashMap<CardTags, Integer>();
	public static final HashMap<Integer, Texture> characterPortraits = new HashMap<>();
	public static Map<String, DuelistCard> orbCardMap = new HashMap<String, DuelistCard>();
	public static ArrayList<DuelistCard> myCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> rareCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uncommonCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> commonCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> nonRareCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> curses = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> monstersThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> monstersThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> spellsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> spellsThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> trapsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> trapsThisRun = new ArrayList<DuelistCard>();	
	public static ArrayList<DuelistCard> uniqueMonstersThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uniqueMonstersThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uniqueSpellsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uniqueSpellsThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uniqueTrapsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> uniqueTrapsThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<AbstractCard> tinFluteCards = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> coloredCards = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> rareCardInPool = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<AbstractPower>();
	public static ArrayList<String> spellsPlayedCombatNames = new ArrayList<String>();
	public static ArrayList<String> spellsPlayedRunNames = new ArrayList<String>();
	public static ArrayList<String> trapsPlayedCombatNames = new ArrayList<String>();
	public static ArrayList<String> trapsPlayedRunNames = new ArrayList<String>();
	public static ArrayList<String> monstersPlayedCombatNames = new ArrayList<String>();
	public static ArrayList<String> monstersPlayedRunNames = new ArrayList<String>();
	public static ArrayList<String> startingDecks = new ArrayList<String>();
	public static ArrayList<String> randomBuffStrings = new ArrayList<String>();
	public static ArrayList<String> invertableOrbNames = new ArrayList<String>();
	public static ArrayList<AbstractCard> archetypeCards = new ArrayList<AbstractCard>();
	public static ArrayList<CardTags> monsterTypes = new ArrayList<CardTags>();
	public static ArrayList<CardTags> summonedTypesThisTurn = new ArrayList<CardTags>();
	public static ArrayList<AbstractRelic> duelistRelicsForTombEvent = new ArrayList<AbstractRelic>();
	
	// Global Flags
	public static boolean machineArtifactFlipper = false;
	public static boolean toonWorldTemp = false;
	public static boolean resetProg = false;
	public static boolean checkTrap = false;
	public static boolean checkUO = false;
	public static boolean gotFirePot = false;
	public static boolean gotBeastStr = false;
	public static boolean gotMimicLv1 = false;
	public static boolean gotMimicLv3 = false;
	public static boolean trigFirePot = false;
	public static boolean trigBeastStr = false;
	public static boolean trigMimicLv1 = false;
	public static boolean trigMimicLv3 = false;
	public static boolean immortalInDiscard = false;
	public static boolean upgradedMimicLv3 = false;
	public static boolean giveUpgradedMimicLv3 = false;
	public static boolean ultimateOfferingTrig = false;
	public static boolean playedOneCardThisCombat = false;
	public static boolean hasCardRewardRelic = false;
	public static boolean shouldFill = true;
	public static boolean dragonRelicBFlipper = false;
	public static boolean playedSpellThisTurn = false;
	public static boolean kuribohrnFlipper = false;
	public static boolean hasUpgradeBuffRelic = false;
	public static boolean hasShopBuffRelic = false;
	public static boolean hasPuzzle = true;
	public static boolean hadFrozenEye = false;
	public static boolean gotFrozenEyeFromBigEye = false;
	public static boolean isConspire = Loader.isModLoaded("conspire");
	public static boolean isReplay = Loader.isModLoaded("ReplayTheSpireMod");

	// Numbers
	public static int lowNoBuffs = 3;
	public static int highNoBuffs = 6;
	public static int deckArchetypePoolCheck = 10;
	public static int lastMaxSummons = 5;
	public static int defaultMaxSummons = 5;
	//public static int toonDamage = 7;
	public static int tribCombatCount = 0;
	public static int tribTurnCount = 0;
	public static int tribRunCount = 0;
	public static int spellCombatCount = 0;
	public static int summonCombatCount = 0;
	public static int summonTurnCount = 0;
	public static int trapCombatCount = 0;
	public static int spellRunCount = 0;	
	public static int summonRunCount = 0;
	public static int trapRunCount = 0;
	public static int swordsPlayed = 0;
	public static int cardsToDraw = 5;
	public static int resummonDeckDamage = 1;
	public static int deckIndex = 0;
	public static int modeIndex = 0;
	public static int randomizedCardsIndex = 0;
	public static int randomizedCostsIndex = 0;
	public static int randomizedTributesIndex = 0;
	public static int randomizedSummonsIndex = 0;
	public static int normalSelectDeck = -1;
	public static int dragonStr = 1;
	public static int toonVuln = 1;
	public static int insectPoisonDmg = 3;
	public static int plantConstricted = 4;
	public static int predaplantThorns = 1;
	public static final int baseInsectPoison = 3;
	public static int spellcasterBlk = 5;
	public static int fiendDraw = 1;
	public static int aquaLowCostRoll = 1;
	public static int aquaHighCostRoll = 4;
	public static int aquaInc = 1;
	public static int aquaTidalBoost = 1;
	public static int superheavyDex = 1;
	public static int naturiaDmg = 1;
	public static int machineArt = 1;
	public static int beastStrSummons = 0;
	public static int zombieResummonBlock = 5;
	public static int mimic1Copies = 0;
	public static int extraCardsFromRandomArch = 25;
	public static int archRoll1 = -1;
	public static int archRoll2 = -1;
	public static int gravAxeStr = -99;
	public static int poisonAppliedThisCombat = 0;
	public static int zombiesResummonedThisCombat = 0;
	public static int zombiesResummonedThisRun = 0;
	public static int explosiveDmgLow = 1;
	public static int explosiveDmgHigh = 3;
	public static int spellcasterBlockOnAttack = 5;
	public static int rewardCardsReachedEnd = 0;
	public static int spellcasterRandomOrbsChanneled = 0;
	public static int currentSpellcasterOrbChance = 25;
	public static int summonLastCombatCount = 0;
	public static int tributeLastCombatCount = 0;
	
	// Other
	public static TheDuelist duelistChar;
	public static StarterDeck currentDeck;
	public static CombatIconViewer combatIconViewer;
	public static CardTags lastTagSummoned = Tags.DRAGON;
	
	// Config Menu
	public static float yPos = 760.0f;
	public static float xLabPos = 360.0f;
	public static float xLArrow = 800.0f;
	public static float xRArrow = 1500.0f;
	public static float xSelection = 900.0f;
	public static float xSecondCol = 490.0f;
	public static UIStrings Config_UI_String;
	public static int pageNumber = 1;
	public static int highestPage = 3;
	public static String pageString;
	public static ModPanel settingsPanel;
	public static ModPanel settingsPanelRandomization;
	public static ModLabel cardLabelTxt;
	public static ModLabeledToggleButton toonBtn;
	public static ModLabeledToggleButton creatorBtn;
	public static ModLabeledToggleButton exodiaBtn;
	public static ModLabeledToggleButton ojamaBtn;
	public static ModLabeledToggleButton unlockBtn;
	public static ModLabeledToggleButton oldCharBtn;
	public static ModLabeledToggleButton challengeBtn;
	public static ModLabeledToggleButton flipBtn;
	public static ModLabeledToggleButton noChangeBtnCost;
	public static ModLabeledToggleButton onlyDecBtnCost;
	public static ModLabeledToggleButton noChangeBtnTrib;
	public static ModLabeledToggleButton onlyDecBtnTrib;
	public static ModLabeledToggleButton noChangeBtnSumm;
	public static ModLabeledToggleButton onlyIncBtnSumm;
	public static ModLabeledToggleButton alwaysUpgradeBtn;
	public static ModLabeledToggleButton neverUpgradeBtn;
	public static ModLabeledToggleButton etherealBtn;
	public static ModLabeledToggleButton exhaustBtn;
	public static ModLabeledToggleButton debugBtn;
	public static ModLabeledToggleButton allowBaseGameCardsBtn;
	public static ModLabel setSelectLabelTxt;
	public static ModLabel setSelectColorTxt;
	public static ModButton setSelectLeftBtn;
	public static ModButton setSelectRightBtn;
	public static ModLabel setSelectLabelTxtB;
	public static ModLabel setSelectColorTxtB;
	public static ModButton setSelectLeftBtnB;
	public static ModButton setSelectRightBtnB;
	
	
	// Global Character Stats
	public static int energyPerTurn = 3;
	public static int startHP = 80;
	public static int maxHP = 80;
	public static int startGold = 99;
	public static int cardDraw = 5;
	public static int orbSlots = 3;
	
	// Turn off for Workshop releases, just prints out stuff and adds debug cards/tokens to game
	public static boolean debug = false;			// print statements only, used in mod option panel
	public static boolean debugMsg = false;			// for secret msg
	public static final boolean addTokens = true;	// adds debug tokens to library
	public static final boolean printSQL = false;	// toggles SQL db formatted info print
	public static final boolean fullDebug = false;	// actually modifies char stats, cards in compendium, starting max summons, etc

	// =============== INPUT TEXTURE LOCATION =================

	// Animations atlas and JSON files
	//public static final String THE_DEFAULT_SKELETON_ATLAS = "char/defaultCharacter/skeleton.atlas";
	//public static final String THE_DEFAULT_SKELETON_JSON = "char/defaultCharacter/skeleton.json";

	// =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return makePath("cards/" + resourcePath);
    }

    public static String makeRelicPath(String resourcePath) {
        return makePath("relics/" + resourcePath);
    }

    public static String makeRelicOutlinePath(String resourcePath) {
    	return makePath("relics/outline/" + resourcePath);
    }

    public static String makeOrbPath(String resourcePath) {
        return makePath("orbs/" + resourcePath);
    }

    public static String makePowerPath(String resourcePath) {
        return makePath("powers/" + resourcePath);
    }

    public static String makeEventPath(String resourcePath) {
        return makePath("events/" + resourcePath);
    }
    
    public static String makeIconPath(String resourcePath) {
        return makePath("icons/" + resourcePath);
    }

    // =============== /MAKE IMAGE PATHS/ =================


	// =============== /INPUT TEXTURE LOCATION/ =================

	// =============== IMAGE PATHS =================
	/**
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	public static final String makePath(String resource) {
		return Strings.DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
	}

	// =============== /IMAGE PATHS/ =================

	// =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

	public DuelistMod() {
		logger.info("Subscribe to BaseMod hooks");
		BaseMod.subscribe(this);

		logger.info("Done subscribing");
		logger.info("Creating the color " + AbstractCardEnum.DUELIST.toString());
		logger.info("Creating the color " + AbstractCardEnum.DUELIST_MONSTERS.toString());
		logger.info("Creating the color " + AbstractCardEnum.DUELIST_SPELLS.toString());
		logger.info("Creating the color " + AbstractCardEnum.DUELIST_TRAPS.toString());

		// Register Default Gray
		/*
		BaseMod.addColor(AbstractCardEnum.DUELIST_MONSTERS, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
				DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(ATTACK_DEFAULT_GRAY),
				makePath(SKILL_DEFAULT_GRAY), makePath(POWER_DEFAULT_GRAY),
				makePath(ENERGY_ORB_DEFAULT_GRAY), makePath(ATTACK_DEFAULT_GRAY_PORTRAIT),
				makePath(SKILL_DEFAULT_GRAY_PORTRAIT), makePath(POWER_DEFAULT_GRAY_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));
				*/
		
		// Register purple for Traps
		BaseMod.addColor(AbstractCardEnum.DUELIST_TRAPS, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE,
				Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, makePath(Strings.ATTACK_DEFAULT_PURPLE),
				makePath(Strings.SKILL_DEFAULT_PURPLE), makePath(Strings.POWER_DEFAULT_PURPLE),
				makePath(Strings.ENERGY_ORB_DEFAULT_PURPLE), makePath(Strings.ATTACK_DEFAULT_PURPLE_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_PURPLE_PORTRAIT), makePath(Strings.POWER_DEFAULT_PURPLE_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_PURPLE_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_PURPLE));
		
		// Register green for Spells
		BaseMod.addColor(AbstractCardEnum.DUELIST_SPELLS, Colors.DEFAULT_GREEN, Colors.DEFAULT_GREEN, Colors.DEFAULT_GREEN,
				Colors.DEFAULT_GREEN, Colors.DEFAULT_GREEN, Colors.DEFAULT_GREEN, Colors.DEFAULT_GREEN, makePath(Strings.ATTACK_DEFAULT_GREEN),
				makePath(Strings.SKILL_DEFAULT_GREEN), makePath(Strings.POWER_DEFAULT_GREEN),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN), makePath(Strings.ATTACK_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_GREEN_PORTRAIT), makePath(Strings.POWER_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_GREEN));
		
		// Register yellow for Monsters
		BaseMod.addColor(AbstractCardEnum.DUELIST_MONSTERS, Colors.DEFAULT_YELLOW, Colors.DEFAULT_YELLOW, Colors.DEFAULT_YELLOW,
				Colors.DEFAULT_YELLOW, Colors.DEFAULT_YELLOW, Colors.DEFAULT_YELLOW, Colors.DEFAULT_YELLOW, makePath(Strings.ATTACK_DEFAULT_YELLOW),
				makePath(Strings.SKILL_DEFAULT_YELLOW), makePath(Strings.POWER_DEFAULT_YELLOW),
				makePath(Strings.ENERGY_ORB_DEFAULT_YELLOW), makePath(Strings.ATTACK_DEFAULT_YELLOW_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_YELLOW_PORTRAIT), makePath(Strings.POWER_DEFAULT_YELLOW_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_YELLOW_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_YELLOW));
		
		// Register blue for Tokens
		BaseMod.addColor(AbstractCardEnum.DUELIST, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE,
				Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, makePath(Strings.ATTACK_DEFAULT_BLUE),
				makePath(Strings.SKILL_DEFAULT_BLUE), makePath(Strings.POWER_DEFAULT_BLUE),
				makePath(Strings.ENERGY_ORB_DEFAULT_BLUE), makePath(Strings.ATTACK_DEFAULT_BLUE_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_BLUE_PORTRAIT), makePath(Strings.POWER_DEFAULT_BLUE_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_BLUE_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_BLUE));
		
		// Register red for Red Medicine buff options
		BaseMod.addColor(AbstractCardEnum.DUELIST_SPECIAL, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE,
				Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, makePath(Strings.ATTACK_DEFAULT_RED),
				makePath(Strings.SKILL_DEFAULT_RED), makePath(Strings.POWER_DEFAULT_RED),
				makePath(Strings.ENERGY_ORB_DEFAULT_RED), makePath(Strings.ATTACK_DEFAULT_RED_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_RED_PORTRAIT), makePath(Strings.POWER_DEFAULT_RED_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_RED_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_RED));

		logger.info("Done creating the color");
		
		logger.info("Setting up or loading the settings config file");
		duelistDefaults.setProperty(PROP_TOON_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_EXODIA_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_OJAMA_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_CREATOR_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_OLD_CHAR, "FALSE");
		duelistDefaults.setProperty(PROP_SET, "0");
		duelistDefaults.setProperty(PROP_DECK, "0");
		duelistDefaults.setProperty(PROP_CARDS, "200");
		duelistDefaults.setProperty(PROP_MAX_SUMMONS, "5");
		duelistDefaults.setProperty(PROP_RESUMMON_DMG, "1");
		duelistDefaults.setProperty(PROP_CHALLENGE, "FALSE");
		duelistDefaults.setProperty(PROP_UNLOCK, "FALSE");
		duelistDefaults.setProperty(PROP_FLIP, "FALSE");
		duelistDefaults.setProperty(PROP_RESET, "FALSE");
		duelistDefaults.setProperty(PROP_DEBUG, "FALSE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_COST, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_DEC_COST, "FALSE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_TRIB, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_DEC_TRIB, "FALSE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_SUMM, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_INC_SUMM, "FALSE");
		duelistDefaults.setProperty(PROP_R_ETHEREAL, "TRUE");
		duelistDefaults.setProperty(PROP_R_EXHAUST, "TRUE");
		duelistDefaults.setProperty(PROP_ALWAYS_UPGRADE, "FALSE");
		duelistDefaults.setProperty(PROP_NEVER_UPGRADE, "FALSE");
		duelistDefaults.setProperty(PROP_BASE_GAME_CARDS, "FALSE");
		
		monsterTypes.add(Tags.AQUA);		typeCardMap_ID.put(Tags.AQUA, makeID("AquaTypeCard"));					typeCardMap_IMG.put(Tags.AQUA, makePath(Strings.ISLAND_TURTLE));
		monsterTypes.add(Tags.DRAGON);		typeCardMap_ID.put(Tags.DRAGON, makeID("DragonTypeCard"));				typeCardMap_IMG.put(Tags.DRAGON, makePath(Strings.BABY_DRAGON));	
		monsterTypes.add(Tags.FIEND);		typeCardMap_ID.put(Tags.FIEND, makeID("FiendTypeCard"));				typeCardMap_IMG.put(Tags.FIEND, makeCardPath("GrossGhost.png"));	
		monsterTypes.add(Tags.INSECT);		typeCardMap_ID.put(Tags.INSECT, makeID("InsectTypeCard"));				typeCardMap_IMG.put(Tags.INSECT, makePath(Strings.BASIC_INSECT));	
		monsterTypes.add(Tags.MACHINE);		typeCardMap_ID.put(Tags.MACHINE, makeID("MachineTypeCard"));			typeCardMap_IMG.put(Tags.MACHINE, makeCardPath("YellowGadget.png"));	
		monsterTypes.add(Tags.NATURIA);		typeCardMap_ID.put(Tags.NATURIA, makeID("NaturiaTypeCard"));			typeCardMap_IMG.put(Tags.NATURIA, makePath(Strings.NATURIA_HORNEEDLE));
		monsterTypes.add(Tags.PLANT);		typeCardMap_ID.put(Tags.PLANT, makeID("PlantTypeCard"));				typeCardMap_IMG.put(Tags.PLANT, makePath(Strings.FIREGRASS));	
		monsterTypes.add(Tags.PREDAPLANT);	typeCardMap_ID.put(Tags.PREDAPLANT, makeID("PredaplantTypeCard"));		typeCardMap_IMG.put(Tags.PREDAPLANT, makePath(Strings.PREDA_TOKEN));	
		monsterTypes.add(Tags.SPELLCASTER);	typeCardMap_ID.put(Tags.SPELLCASTER, makeID("SpellcasterTypeCard"));	typeCardMap_IMG.put(Tags.SPELLCASTER, makeCardPath("SpellcasterToken.png"));	
		monsterTypes.add(Tags.SUPERHEAVY);	typeCardMap_ID.put(Tags.SUPERHEAVY, makeID("SuperheavyTypeCard"));		typeCardMap_IMG.put(Tags.SUPERHEAVY, makePath(Strings.SUPERHEAVY_SCALES));	
		monsterTypes.add(Tags.TOON);		typeCardMap_ID.put(Tags.TOON, makeID("ToonTypeCard"));					typeCardMap_IMG.put(Tags.TOON, makePath(Strings.TOON_GOBLIN_ATTACK));	
		monsterTypes.add(Tags.ZOMBIE);		typeCardMap_ID.put(Tags.ZOMBIE, makeID("ZombieTypeCard"));				typeCardMap_IMG.put(Tags.ZOMBIE, makePath(Strings.ARMORED_ZOMBIE));	

		// Setup map to find which tribute synergy function to run based on a monster card's current tags
		// Map simply holds each monster type cardtag with an integer value to use with a switch statement
		// Integer values should be in the same order as monster types are added to the array above, 0-11
		int counter = 0; for (CardTags t : monsterTypes) { monsterTypeTributeSynergyFunctionMap.put(t, counter); counter++; }
		
		cardSets.add("Standard"); 
		cardSets.add("Basic + Deck Archetype");
		cardSets.add("Basic Only");
		cardSets.add("Basic + 1 Random Archetype");
		cardSets.add("Basic + 2 Random Archetypes");
		cardSets.add("Basic + Deck + 2 Random Archetypes");
		cardSets.add("Always ALL Cards");

		
		int save = 0;
		StarterDeck regularDeck = new StarterDeck(Tags.STANDARD_DECK, "Standard Deck (10 cards)", save, "Standard Deck", true); starterDeckList.add(regularDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck dragDeck = new StarterDeck(Tags.DRAGON_DECK, "Dragon Deck (10 cards)", save, "Dragon Deck", false); starterDeckList.add(dragDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck natDeck = new StarterDeck(Tags.NATURE_DECK, "Nature Deck (10 cards)", save, "Nature Deck", false); starterDeckList.add(natDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck spellcDeck = new StarterDeck(Tags.SPELLCASTER_DECK, "Spellcaster Deck (10 cards)", save, "Spellcaster Deck", false); starterDeckList.add(spellcDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck toonDeck = new StarterDeck(Tags.TOON_DECK, "Toon Deck (10 cards)", save, "Toon Deck", false); starterDeckList.add(toonDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck zombieDeck = new StarterDeck(Tags.ZOMBIE_DECK, "Zombie Deck (10 cards)", save, "Zombie Deck", false); starterDeckList.add(zombieDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck aquaDeck = new StarterDeck(Tags.AQUA_DECK, "Aqua Deck (10 cards)", save, "Aqua Deck", false); starterDeckList.add(aquaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck fiendDeck = new StarterDeck(Tags.FIEND_DECK, "Fiend Deck (10 cards)", save, "Fiend Deck", false); starterDeckList.add(fiendDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck machineDeck = new StarterDeck(Tags.MACHINE_DECK, "Machine Deck (10 cards)", save, "Machine Deck", false); starterDeckList.add(machineDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck magnetDeck = new StarterDeck(Tags.MAGNET_DECK, "Superheavy Deck (10 cards)", save, "Superheavy Deck", false); starterDeckList.add(magnetDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck creaDeck = new StarterDeck(Tags.CREATOR_DECK, "Creator Deck (10 cards)", save, "Creator Deck", true); starterDeckList.add(creaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ojDeck = new StarterDeck(Tags.OJAMA_DECK, "Ojama Deck (12 cards)", save, "Ojama Deck", true); starterDeckList.add(ojDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck gDeck = new StarterDeck(Tags.GENERATION_DECK, "Generation Deck (16 cards)", save, "Generation Deck", true); starterDeckList.add(gDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck oDeck = new StarterDeck(Tags.ORB_DECK, "Orb Deck (12 cards)", save, "Orb Deck", true); starterDeckList.add(oDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck resDeck = new StarterDeck(Tags.RESUMMON_DECK, "Resummon Deck (10 cards)", save, "Resummon Deck", true); starterDeckList.add(resDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck incDeck = new StarterDeck(Tags.INCREMENT_DECK, "Increment Deck (14 cards)", save, "Increment Deck", true); starterDeckList.add(incDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck exodiaDeck = new StarterDeck(Tags.EXODIA_DECK, "Exodia Deck (60 cards)", save, "Exodia Deck", true); starterDeckList.add(exodiaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck hpDeck = new StarterDeck(Tags.HEAL_DECK, "Heal Deck (12 cards)", save, "Heal Deck", true); starterDeckList.add(hpDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran1Deck = new StarterDeck(Tags.RANDOM_DECK_SMALL, "Random Deck (10 cards)", save, "Random Deck (Small)", true); starterDeckList.add(ran1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran2Deck = new StarterDeck(Tags.RANDOM_DECK_BIG, "Random Deck (15 cards)", save, "Random Deck (Big)", true); starterDeckList.add(ran2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		
		for (StarterDeck d : starterDeckList) { startingDecks.add(d.getName()); starterDeckNamesMap.put(d.getSimpleName(), d); }
		DECKS = starterDeckList.size();
		currentDeck = regularDeck;
		try 
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
            config.load();
            toonBtnBool = config.getBool(PROP_TOON_BTN);
            exodiaBtnBool = config.getBool(PROP_EXODIA_BTN);
            ojamaBtnBool = config.getBool(PROP_OJAMA_BTN);
            creatorBtnBool = config.getBool(PROP_CREATOR_BTN);
            oldCharacter = config.getBool(PROP_OLD_CHAR);
            challengeMode = config.getBool(PROP_CHALLENGE);
            unlockAllDecks = config.getBool(PROP_UNLOCK);
            flipCardTags = config.getBool(PROP_FLIP);
            resetProg = config.getBool(PROP_RESET);
            setIndex = config.getInt(PROP_SET);
            cardCount = config.getInt(PROP_CARDS);
            deckIndex = config.getInt(PROP_DECK);
            debug = config.getBool(PROP_DEBUG);
            noCostChanges = config.getBool(PROP_NO_CHANGE_COST);
            onlyCostDecreases = config.getBool(PROP_ONLY_DEC_COST);
            noTributeChanges = config.getBool(PROP_NO_CHANGE_TRIB);
            onlyTributeDecreases = config.getBool(PROP_ONLY_DEC_TRIB);
            noSummonChanges = config.getBool(PROP_NO_CHANGE_SUMM);
            onlySummonIncreases = config.getBool(PROP_ONLY_INC_SUMM);
            alwaysUpgrade = config.getBool(PROP_ALWAYS_UPGRADE);
            neverUpgrade = config.getBool(PROP_NEVER_UPGRADE);
            randomizeEthereal = config.getBool(PROP_R_ETHEREAL);
            randomizeExhaust = config.getBool(PROP_R_EXHAUST);
            chosenDeckTag = StarterDeckSetup.findDeckTag(deckIndex);
            lastMaxSummons = config.getInt(PROP_MAX_SUMMONS);
            resummonDeckDamage = config.getInt(PROP_RESUMMON_DMG);
            baseGameCards = config.getBool(PROP_BASE_GAME_CARDS);
        } catch (Exception e) { e.printStackTrace(); }
		
		if (fullDebug)
		{
			energyPerTurn = 100;
			startHP = 1800;
			maxHP = 1800;
			startGold = 999;
			cardDraw = 1;
			orbSlots = 3;
		}

		logger.info("Done setting up or loading the settings config file");
	}


	public static void initialize() {
		logger.info("Initializing Duelist Mod");
		DuelistMod defaultmod = new DuelistMod();
		incrementDiscardSubscribers = new ArrayList<>();
		logger.info("Duelist Mod Initialized");
	}

	// ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

	

	// =============== LOAD THE CHARACTER =================

	@Override
	public void receiveEditCharacters() 
	{
		// Yugi Moto
		duelistChar = new TheDuelist("the Duelist", TheDuelistEnum.THE_DUELIST);
		BaseMod.addCharacter(duelistChar,makePath(Strings.THE_DEFAULT_BUTTON), makePath(Strings.THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_DUELIST);

		receiveEditPotions();

		logger.info("Done editing characters");

	}

	// =============== /LOAD THE CHARACTER/ =================


	// =============== POST-INITIALIZE =================


	@Override
	public void receivePostInitialize() 
	{	
		// MOD OPTIONS PANEL
		logger.info("Loading badge image and mod options");
		String loc = Localization.localize();
		Texture badgeTexture = new Texture(makePath(Strings.BADGE_IMAGE));
		Config_UI_String = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");
		setupExtraConfigStrings();
		settingsPanel = new ModPanel();
		configPanelSetup();
		BaseMod.registerModBadge(badgeTexture, modName, modAuthor, modDescription, settingsPanel);
		combatIconViewer = new CombatIconViewer();
		
		// Events
		//BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, com.megacrit.cardcrawl.dungeons.Exordium.ID);  	// Act 1
		//BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, com.megacrit.cardcrawl.dungeons.TheCity.ID); 		// Act 2 
		//BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, com.megacrit.cardcrawl.dungeons.TheBeyond.ID);  	// Act 3
		//BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class, com.megacrit.cardcrawl.dungeons.TheEnding.ID); 	// Act 4
		//BaseMod.addEvent(StrangeSmithEvent.ID, StrangeSmithEvent.class);													// Any
		BaseMod.addEvent(MillenniumItems.ID, MillenniumItems.class);
		BaseMod.addEvent(AknamkanonTomb.ID, AknamkanonTomb.class, TheBeyond.ID);
		
		if (printSQL)
		{
			logger.info("START SQL METRICS PRINT");
			Debug.outputSQLListsForMetrics();
			logger.info("END SQL METRICS PRINT");
		}
		
		logger.info("Done loading badge Image and mod options");
	}
	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		logger.info("Beginning to edit potions");

		ArrayList<AbstractPotion> pots = new ArrayList<AbstractPotion>();
		pots.add(new MillenniumElixir());
		pots.add(new SealedPack());
		pots.add(new SealedPackB());
		pots.add(new SealedPackC());
		pots.add(new SealedPackD());
		pots.add(new SealedPackE());
		pots.add(new OrbBottle());
		pots.add(new AirBottle());
		pots.add(new BlackBottle());
		pots.add(new DragonOrbBottle());
		pots.add(new DragonOrbPlusBottle());
		pots.add(new EarthBottle());
		pots.add(new FireBottle());
		pots.add(new GadgetBottle());
		pots.add(new GlitchBottle());
		pots.add(new MetalBottle());
		pots.add(new MonsterOrbBottle());
		pots.add(new SandBottle());
		pots.add(new StormBottle());
		pots.add(new SummonerBottle());
		pots.add(new ExtraOrbsBottle());
		pots.add(new TributeBottle());
		pots.add(new BigTributeBottle());		
		pots.add(new BigOrbBottle());
		pots.add(new DestructPotionPot());
		pots.add(new DestructPotionPotB());
		pots.add(new JoeyJuice());
		pots.add(new MudBottle());
		pots.add(new SteamBottle());
		pots.add(new BufferBottle());
		for (AbstractPotion p : pots){ BaseMod.addPotion(p.getClass(), Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, p.ID, TheDuelistEnum.THE_DUELIST); }
		
		// Class Specific Potion. If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDuelistEnum.THE_DUELIST")
		//BaseMod.addPotion(MillenniumElixir.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, MillenniumElixir.POTION_ID, TheDuelistEnum.THE_DUELIST);
		//BaseMod.addPotion(SealedPack.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, SealedPack.POTION_ID, TheDuelistEnum.THE_DUELIST);
		//BaseMod.addPotion(SealedPackB.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, SealedPackB.POTION_ID, TheDuelistEnum.THE_DUELIST);
		//BaseMod.addPotion(OrbBottle.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, OrbBottle.POTION_ID, TheDuelistEnum.THE_DUELIST);
		//BaseMod.addPotion(AirBottle.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, AirBottle.POTION_ID, TheDuelistEnum.THE_DUELIST);

		logger.info("Done editing potions");
	}

	// ================ /ADD POTIONS/ ===================


	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		logger.info("Adding relics");

		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		BaseMod.addRelicToCustomPool(new MillenniumPuzzle(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumEye(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumRing(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumKey(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumRod(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumCoin(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ResummonBranch(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new AeroRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicA(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicB(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicC(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicD(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicE(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new InversionRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new InversionEvokeRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new InsectRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new NaturiaRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MachineToken(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new DragonRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SummonAnchor(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SpellcasterToken(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SpellcasterOrb(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new AquaRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new AquaRelicB(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new NatureRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ZombieRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new DragonRelicB(), AbstractCardEnum.DUELIST);
		//BaseMod.addRelicToCustomPool(new UpgradeBuffRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ShopRelicRarityRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumScale(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MachineTokenB(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumNecklace(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MillenniumToken(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new DragonRelicC(), AbstractCardEnum.DUELIST);
		//BaseMod.addRelicToCustomPool(new RandomTributeMonsterRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new YugiMirror(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicF(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicG(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CardRewardRelicH(), AbstractCardEnum.DUELIST);
		//BaseMod.addRelicToCustomPool(new TributeEggRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ZombieResummonBuffRelic(), AbstractCardEnum.DUELIST);		
		BaseMod.addRelicToCustomPool(new StoneExxod(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new GiftAnubis(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ToonRelic(), AbstractCardEnum.DUELIST);
		
		// Base Game Shared relics
		BaseMod.addRelicToCustomPool(new GoldPlatedCables(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Inserter(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new NuclearBattery(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new DataDisk(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SymbioticVirus(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new EmotionChip(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RunicCapacitor(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RedSkull(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new PaperFrog(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SelfFormingClay(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ChampionsBelt(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CharonsAshes(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MagicFlower(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new MarkOfPain(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RunicCube(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Brimstone(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SneckoSkull(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new PaperCrane(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new TheSpecimen(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Tingsha(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ToughBandages(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new HoveringKite(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new WristBlade(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new TwistedFunnel(), AbstractCardEnum.DUELIST);
		
		//AbstractDungeon.shopRelicPool.remove(PrismaticShard.ID);
		
		UnlockTracker.markRelicAsSeen(MillenniumRing.ID);
		UnlockTracker.markRelicAsSeen(MillenniumKey.ID);
		UnlockTracker.markRelicAsSeen(MillenniumRod.ID);
		UnlockTracker.markRelicAsSeen(MillenniumCoin.ID);
		UnlockTracker.markRelicAsSeen(ResummonBranch.ID);
		UnlockTracker.markRelicAsSeen(AeroRelic.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicA.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicB.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicC.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicD.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicE.ID);
		UnlockTracker.markRelicAsSeen(InversionRelic.ID);
		UnlockTracker.markRelicAsSeen(InversionEvokeRelic.ID);
		UnlockTracker.markRelicAsSeen(InsectRelic.ID);
		UnlockTracker.markRelicAsSeen(NaturiaRelic.ID);
		UnlockTracker.markRelicAsSeen(MachineToken.ID);
		UnlockTracker.markRelicAsSeen(StoneExxod.ID);
		UnlockTracker.markRelicAsSeen(GiftAnubis.ID);
		UnlockTracker.markRelicAsSeen(DragonRelic.ID);
		UnlockTracker.markRelicAsSeen(SummonAnchor.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterToken.ID);
		UnlockTracker.markRelicAsSeen(SpellcasterOrb.ID);
		UnlockTracker.markRelicAsSeen(AquaRelic.ID);
		UnlockTracker.markRelicAsSeen(AquaRelicB.ID);
		UnlockTracker.markRelicAsSeen(NatureRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieRelic.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicB.ID);
		//UnlockTracker.markRelicAsSeen(UpgradeBuffRelic.ID);
		UnlockTracker.markRelicAsSeen(ShopRelicRarityRelic.ID);
		UnlockTracker.markRelicAsSeen(MillenniumScale.ID);
		UnlockTracker.markRelicAsSeen(MachineTokenB.ID);
		UnlockTracker.markRelicAsSeen(MillenniumNecklace.ID);
		UnlockTracker.markRelicAsSeen(MillenniumToken.ID);
		UnlockTracker.markRelicAsSeen(DragonRelicC.ID);
	//	UnlockTracker.markRelicAsSeen(RandomTributeMonsterRelic.ID);
		UnlockTracker.markRelicAsSeen(YugiMirror.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicF.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicG.ID);
		UnlockTracker.markRelicAsSeen(CardRewardRelicH.ID);
		//UnlockTracker.markRelicAsSeen(TributeEggRelic.ID);
		UnlockTracker.markRelicAsSeen(ZombieResummonBuffRelic.ID);
		UnlockTracker.markRelicAsSeen(ToonRelic.ID);
		
		
		//duelistRelicsForTombEvent.add(new MillenniumEye());
		//duelistRelicsForTombEvent.add(new MillenniumRing());
		//duelistRelicsForTombEvent.add(new MillenniumKey());
		//duelistRelicsForTombEvent.add(new MillenniumRod());
		//duelistRelicsForTombEvent.add(new MillenniumCoin());
		//duelistRelicsForTombEvent.add(new ResummonBranch());
		duelistRelicsForTombEvent.add(new AeroRelic());
		duelistRelicsForTombEvent.add(new CardRewardRelicA());
		duelistRelicsForTombEvent.add(new CardRewardRelicB());
		duelistRelicsForTombEvent.add(new CardRewardRelicC());
		duelistRelicsForTombEvent.add(new CardRewardRelicD());
		duelistRelicsForTombEvent.add(new CardRewardRelicE());
		duelistRelicsForTombEvent.add(new InversionRelic());
		duelistRelicsForTombEvent.add(new InversionEvokeRelic());
		duelistRelicsForTombEvent.add(new InsectRelic());
		duelistRelicsForTombEvent.add(new NaturiaRelic());
		duelistRelicsForTombEvent.add(new MachineToken());
		duelistRelicsForTombEvent.add(new DragonRelic());
		duelistRelicsForTombEvent.add(new SummonAnchor());
		duelistRelicsForTombEvent.add(new SpellcasterToken());
		duelistRelicsForTombEvent.add(new SpellcasterOrb());
		duelistRelicsForTombEvent.add(new AquaRelic());
		duelistRelicsForTombEvent.add(new AquaRelicB());
		duelistRelicsForTombEvent.add(new NatureRelic());
		duelistRelicsForTombEvent.add(new ZombieRelic());
		duelistRelicsForTombEvent.add(new DragonRelicB());
		duelistRelicsForTombEvent.add(new ShopRelicRarityRelic());
		duelistRelicsForTombEvent.add(new StoneExxod());
		//duelistRelicsForTombEvent.add(new MillenniumScale());
		duelistRelicsForTombEvent.add(new MachineTokenB());		
		//duelistRelicsForTombEvent.add(new MillenniumToken());
		duelistRelicsForTombEvent.add(new DragonRelicC());
		//duelistRelicsForTombEvent.add(new RandomTributeMonsterRelic());
		duelistRelicsForTombEvent.add(new YugiMirror());
		duelistRelicsForTombEvent.add(new CardRewardRelicF());
		duelistRelicsForTombEvent.add(new CardRewardRelicG());
		duelistRelicsForTombEvent.add(new CardRewardRelicH());
		//duelistRelicsForTombEvent.add(new TributeEggRelic());
		duelistRelicsForTombEvent.add(new ZombieResummonBuffRelic());
		duelistRelicsForTombEvent.add(new ToonRelic());
		//duelistRelics.add(new MillenniumNecklace()); // Millennium Item event only relic

		// This adds a relic to the Shared pool. Every character can find this relic.
		BaseMod.addRelic(new MillenniumPuzzleShared(), RelicType.SHARED);

		logger.info("Done adding relics!");
	}

	// ================ /ADD RELICS/ ===================

	// ================ ADD CARDS ===================

	@Override
	public void receiveEditCards() 
	{
		// ================ VARIABLES ===================
		logger.info("adding variables");
		BaseMod.addDynamicVariable(new TributeMagicNumber());
		BaseMod.addDynamicVariable(new SummonMagicNumber());
		BaseMod.addDynamicVariable(new SecondMagicNumber());
		logger.info("done adding variables");

		// ================ ORB CARDS ===================
		logger.info("adding orb cards to array for orb modal");
		DuelistCardLibrary.setupOrbCards();
		logger.info("done adding orb cards to array");
		
		// ================ PRIVATE LIBRARY SETUP ===================
		logger.info("adding all cards to myCards array");
		DuelistCardLibrary.setupMyCards();
		logger.info("done adding all cards to myCards array");

		// ================ STARTER DECKS ===================
		logger.info("filling up starting decks");
		StarterDeckSetup.initStartDeckArrays();
		StarterDeckSetup.initStarterDeckPool();
		logger.info("starting deck set as: " + chosenDeckTag.name());
		
		// ================ SUMMON MAP ===================
		logger.info("filling summonMap");
		DuelistCardLibrary.fillSummonMap(myCards);
		logger.info("done filling summonMap");

		// ================ METRICS HELPER ===================
		if (DuelistMod.debug)
		{
			
			logger.info("checking for non-basic, non-archetype cards");
			PoolHelpers.printNonDeckCards();
			logger.info("done checking for non-basic, non-archetype cards");
		}
		
		// ================ COMPENDIUM SETUP ===================
		logger.info("begin checking config options and adding cards to the game");
		DuelistCardLibrary.addCardsToGame();
		logger.info("done adding cards to the game");
		
		// ================ COLORED CARDS ===================
		logger.info("filling colored cards array to use for reward pool");
		PoolHelpers.fillColoredCards();
		logger.info("done filling colored cards");
		logger.info("done receiveEditCards()");
	}

	// ================ /ADD CARDS/ ===================



	// ================ LOAD THE TEXT ===================

	@Override
	public void receiveEditStrings() {
		logger.info("Beginning to edit strings");

		String loc = Localization.localize();
		
		// Card Strings
		BaseMod.loadCustomStringsFile(CardStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-Card-Strings.json");

		// UI Strings
		BaseMod.loadCustomStringsFile(UIStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-UI-Strings.json");
		
		// Power Strings
		BaseMod.loadCustomStringsFile(PowerStrings.class,"duelistModResources/localization/" + loc + "/DuelistMod-Power-Strings.json");

		// Relic Strings
		BaseMod.loadCustomStringsFile(RelicStrings.class,"duelistModResources/localization/" + loc + "/DuelistMod-Relic-Strings.json");

		// Potion Strings
		BaseMod.loadCustomStringsFile(PotionStrings.class,"duelistModResources/localization/" + loc + "/DuelistMod-Potion-Strings.json");

		// Orb Strings
		BaseMod.loadCustomStringsFile(OrbStrings.class,"duelistModResources/localization/" + loc + "/DuelistMod-Orb-Strings.json");
		
		// Character Strings
		BaseMod.loadCustomStringsFile(CharacterStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-Character-Strings.json");
		
		// Event Strings
		BaseMod.loadCustomStringsFile(EventStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-Event-Strings.json");
		

		logger.info("Done editing strings");
	}

	// ================ /LOAD THE TEXT/ ===================

	// ================ LOAD THE KEYWORDS ===================

	@Override
	public void receiveEditKeywords() 
	{
		String loc = Localization.localize();
		Gson gson = new Gson();
        String json = Gdx.files.internal("duelistModResources/localization/" + loc + "/DuelistMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
            	if (keyword != null)
            	{
	            	logger.info("Adding keyword: " + keyword.PROPER_NAME + " | " + keyword.NAMES[0]);
	                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            	}
            }
        }
	}

	// ================ /LOAD THE KEYWORDS/ ===================    

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return "theDuelist:" + idText;
	}
	
	public static String getModID() {
        return modID;
    }
	
	public static boolean isToken(AbstractCard c)
	{
		if (c.hasTag(Tags.TOKEN)) { return true; }
		else { return false; }
	}

	public static boolean isMonster(AbstractCard c)
	{
		if (c.hasTag(Tags.MONSTER)) { return true; }
		else { return false; }
	}

	public static boolean isSpell(AbstractCard c)
	{
		if (c.hasTag(Tags.SPELL)) { return true; }
		else { return false; }
	}

	public static boolean isTrap(AbstractCard c)
	{
		if (c.hasTag(Tags.TRAP)) { return true; }
		else { return false; }
	}
	
	public static boolean isArchetype(AbstractCard c)
	{
		if (c.hasTag(Tags.ARCHETYPE)) { return true; }
		else { return false; }
	}
	
	public static boolean isOrbCard(AbstractCard c)
	{
		if (c.hasTag(Tags.ORB_CARD)) { return true; }
		else { return false; }
	}
	
	public static boolean playingChallengeSpire()
	{
		if (Utilities.isCustomModActive("challengethespire:Bronze Difficulty") || Utilities.isCustomModActive("challengethespire:Silver Difficulty") || Utilities.isCustomModActive("challengethespire:Gold Difficulty") || Utilities.isCustomModActive("challengethespire:Platinum Difficulty") )
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	public static int getChallengeDiffIndex()
	{
		if (Utilities.isCustomModActive("challengethespire:Bronze Difficulty")) { return 1; }		
		else if (Utilities.isCustomModActive("challengethespire:Silver Difficulty")) { return 2; }		
		else if (Utilities.isCustomModActive("challengethespire:Gold Difficulty")) { return 3; }	
		else if (Utilities.isCustomModActive("challengethespire:Platinum Difficulty")) { return 4; }
		else { return -1; }
	}
	
	private static <T> void subscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
		if (clazz.isInstance(sub)) {
			list.add(clazz.cast(sub));
		}
	}

	public static void subscribe(ISubscriber sub) {
		subscribeIfInstance(incrementDiscardSubscribers, sub, IncrementDiscardSubscriber.class);
	}

	private static <T> void unsubscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
		if (clazz.isInstance(sub)) {
			list.remove(clazz.cast(sub));
		}
	}

	public static void unsubscribe(ISubscriber sub) {
		unsubscribeIfInstance(incrementDiscardSubscribers, sub, IncrementDiscardSubscriber.class);
	}
    
	// HOOKS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void incrementDiscardHook(boolean endOfTurn) {
		if (endOfTurn) {
			return;
		}

		boolean bNullExist = false;
		for (IncrementDiscardSubscriber sub : incrementDiscardSubscribers) {
			if (sub != null) {
				sub.receiveIncrementDiscard();
			} else {
				bNullExist = true;
			}
		}

		// Powers don't have an universal hook for when they are destroyed, so we have to clean our list
		if (bNullExist) {
			incrementDiscardSubscribers.removeAll(Collections.singleton(null));
		}
	}
	
	@Override
	public void receiveOnBattleStart(AbstractRoom arg0) 
	{
		//if (!AbstractDungeon.player.hasRelic(NaturiaRelic.ID)) { naturiaDmg = 1; }
		
		if (gotFirePot)
		{
			trigFirePot = true;
			AbstractPotion firePot = new FirePotion();
	    	AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(firePot));
		}
		
		if (gotBeastStr)
		{
			DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, beastStrSummons));
			if (debug) { logger.info("got beast fangs strength buff flag, strength value is: " + beastStrSummons); }
			trigBeastStr = true;
		}
		
		if (gotMimicLv3)
		{			
			if (upgradedMimicLv3) { DuelistCard.draw(4); }
			else { DuelistCard.draw(5); }
			trigMimicLv3 = true;
		}
		else if (gotMimicLv1)
		{			
			for (int i = 0; i < mimic1Copies; i++)
			{
				DuelistCard mimic = new DarkMimicLv3();
				if (giveUpgradedMimicLv3) { mimic.upgrade(); }
				DuelistCard.addCardToHand(mimic);
			}			
			
			trigMimicLv1 = true;
		}

		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				((DuelistCard)c).startBattleReset();
			}
		}
		BuffHelper.resetBuffPool();
		lastMaxSummons = defaultMaxSummons;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		poisonAppliedThisCombat = 0;
		zombiesResummonedThisCombat = 0;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) 
	{
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				((DuelistCard) c).postBattleReset();
			}
		}
		monstersThisCombat = new ArrayList<DuelistCard>();
		spellsThisCombat = new ArrayList<DuelistCard>();
		trapsThisCombat = new ArrayList<DuelistCard>();
		spellsPlayedCombatNames = new ArrayList<String>();
		monstersPlayedCombatNames = new ArrayList<String>();
		uniqueSpellsThisCombat = new ArrayList<DuelistCard>();
		uniqueMonstersThisCombat = new ArrayList<DuelistCard>();
		if (UnlockTracker.getUnlockLevel(TheDuelistEnum.THE_DUELIST) > 0) 
		{ 
			UnlockTracker.unlockProgress.putInteger(TheDuelistEnum.THE_DUELIST.toString() + "UnlockLevel", 0);
			SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_COMBAT);
			SaveAndContinue.save(saveFile);
			logger.info("unlock level was greater than 0, reset to 0");
		}
		playedOneCardThisCombat = false;
		lastMaxSummons = defaultMaxSummons;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonLastCombatCount = summonCombatCount;
		tributeLastCombatCount = tribCombatCount;
		summonCombatCount = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		if (trigFirePot && gotFirePot) { gotFirePot = false; trigFirePot = false; }
		if (trigBeastStr && gotBeastStr) { gotBeastStr = false; trigBeastStr = false; }
		if (trigMimicLv1 && gotMimicLv1) { gotMimicLv1 = false; trigMimicLv1 = false; }
		if (trigMimicLv3 && gotMimicLv3) { gotMimicLv3 = false; trigMimicLv3 = false; }
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt(PROP_RESUMMON_DMG, resummonDeckDamage);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int receiveOnPlayerDamaged(int arg0, DamageInfo arg1) 
	{
		return arg0;
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) 
	{
		if (power != null)
		{
			if (power.ID.equals(PoisonPower.POWER_ID))
			{
				poisonAppliedThisCombat+=power.amount;
				if (debug) { logger.info("Incremented poisonAppliedThisCombat by: " + power.amount + ", new value: " + poisonAppliedThisCombat); }
			}
		}
		if (debug)
		{
			if (power != null && target != null && source != null)
			{
				logger.info("Power Applied: " + power.name + " - Target: " + target.name + " - Source: " + source.name);
				if (power.ID.equals("Strength"))
				{
					logger.info("Caught Strength application!");
				}
			}
			else if (power != null)
			{
				logger.info("Power Applied: " + power.name + " - Target: " + "null" + " - Source: " + "null");
				if (power.ID.equals("Strength"))
				{
					logger.info("Caught Strength application!");
				}
			}
			else
			{
				logger.info("Power Applied: " + "null" + " - Target: " + "null" + " - Source: " + "null");
			}
		}
	}

	@Override
	public void receivePowersModified() 
	{
		for (AbstractOrb o : AbstractDungeon.player.orbs)
		{
			if (o instanceof DuelistOrb)
			{
				DuelistOrb oOrb = (DuelistOrb) o;
				if (oOrb instanceof Smoke)
				{
					oOrb.checkFocus(true);
				}
				else
				{
					oOrb.checkFocus(false);
				}
			}
		}
		
		if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID) && debug)
		{
			if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount != gravAxeStr && AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID))
			{
				logger.info("Got the wrong value for gravAxeStr == player.strength.amount, Player Strength: " + AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount + " - gravAxeStr: " + gravAxeStr);
			}
		}
	}

	@Override
	public void receivePostDeath() 
	{
		// reset saved colored cards array so when I try to load it will find nothing and instead fill colored cards again manually
		logger.info("does this happen when you win?");
		coloredCards = new ArrayList<AbstractCard>();
		archRoll1 = -1;
		archRoll2 = -1;
		shouldFill = true;
		monstersThisRun = new ArrayList<DuelistCard>();
		monstersThisCombat = new ArrayList<DuelistCard>();
		spellsThisRun = new ArrayList<DuelistCard>();
		spellsThisCombat = new ArrayList<DuelistCard>();
		trapsThisRun = new ArrayList<DuelistCard>();
		trapsThisCombat = new ArrayList<DuelistCard>();
		uniqueMonstersThisRun = new ArrayList<DuelistCard>();
		uniqueMonstersThisCombat = new ArrayList<DuelistCard>();
		uniqueSpellsThisRun = new ArrayList<DuelistCard>();
		uniqueSpellsThisCombat = new ArrayList<DuelistCard>();
		uniqueTrapsThisRun = new ArrayList<DuelistCard>();
		uniqueTrapsThisCombat = new ArrayList<DuelistCard>();
		spellsPlayedCombatNames = new ArrayList<String>();
		monstersPlayedCombatNames = new ArrayList<String>();
		monstersPlayedRunNames = new ArrayList<String>();
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		summonLastCombatCount = 0;
		spellRunCount = 0;
		trapRunCount = 0;
		summonRunCount = 0;
		tribRunCount = 0;
		tribCombatCount = 0;
		tributeLastCombatCount = 0;
		zombiesResummonedThisRun = 0;
		dragonStr = 1;
		toonVuln = 1;
		machineArt = 1;
		zombieResummonBlock = 5;
		spellcasterBlockOnAttack = 5;
		explosiveDmgLow = 1;
		explosiveDmgHigh = 3;
		insectPoisonDmg = baseInsectPoison;
		naturiaDmg = 1;
		AbstractPlayer.customMods = new ArrayList<String>();
		defaultMaxSummons = 5;
		lastMaxSummons = 5;
		swordsPlayed = 0;
		hasUpgradeBuffRelic = false;
		hasShopBuffRelic = false;
		hadFrozenEye = false;
		gotFrozenEyeFromBigEye = false;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt(PROP_RESUMMON_DMG, 1);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// Create metrics object and run it
		customMetrics metrics = new customMetrics();
        
        Thread t = new Thread(metrics);
        t.setName("Metrics");
        t.start();
	}

	@Override
	public void receiveCardUsed(AbstractCard arg0) 
	{
		playedOneCardThisCombat = true;
		logger.info("Card Used: " + arg0.name);
		if (arg0.type.equals(CardType.ATTACK))
		{
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				boolean isOnlySpellcasters = instance.isEveryMonsterCheck(Tags.SPELLCASTER, false);
				if (isOnlySpellcasters)
				{
					DuelistCard.staticBlock(spellcasterBlockOnAttack);
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c instanceof GiantOrc)
				{					
					if (c.cost > 0)
					{
						c.modifyCostForCombat(-c.magicNumber);
			    		c.isCostModified = true;
					}
				}				
			}
			
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{
				if (c instanceof GiantOrc)
				{					
					if (c.cost > 0)
					{
						c.modifyCostForCombat(-c.magicNumber);
			    		c.isCostModified = true;
					}
				}
			}
		}
		
		if (arg0.type.equals(CardType.SKILL))
		{
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{				
				if (c instanceof EarthGiant)
				{					
					DuelistCard dC = (DuelistCard)c;
					if (dC.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, -dC.magicNumber, true));
					}
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{				
				if (c instanceof EarthGiant)
				{					
					DuelistCard dC = (DuelistCard)c;
					if (dC.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, -dC.magicNumber, true));
					}
				}
			}
		}
		
		if (arg0.type.equals(CardType.POWER))
		{
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{				
				if (c instanceof GiantTrapHole)
				{					
					c.modifyCostForCombat(-c.magicNumber);
					c.isCostModified = true;
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{				
				if (c instanceof GiantTrapHole)
				{					
					c.modifyCostForCombat(-c.magicNumber);
					c.isCostModified = true;
				}
			}
		}
		
		if (arg0.hasTag(Tags.SPELL) && arg0 instanceof DuelistCard)
		{
			spellCombatCount++;
			spellRunCount++;
			playedSpellThisTurn = true;
			spellsThisCombat.add((DuelistCard) arg0.makeStatEquivalentCopy());
			spellsThisRun.add((DuelistCard) arg0.makeStatEquivalentCopy());
			if (!spellsPlayedCombatNames.contains(arg0.originalName))
			{
				spellsPlayedCombatNames.add(arg0.originalName);
				uniqueSpellsThisCombat.add((DuelistCard) arg0);
			}
			logger.info("incremented spellsThisCombat, new value: " + spellCombatCount);
			logger.info("incremented spellRunCombat, new value: " + spellRunCount);
			logger.info("added " + arg0.originalName + " to spellsThisCombat, spellsThisRun");
		}
		
		if (arg0.hasTag(Tags.MONSTER) && arg0 instanceof DuelistCard)
		{
			
			// Check for monsters with >2 summons for Splash orbs
			DuelistCard duelistArg0 = (DuelistCard)arg0;
			if (duelistArg0.summons > 2) { DuelistCard.checkSplash(); }
	
			monstersThisCombat.add((DuelistCard) arg0.makeStatEquivalentCopy());
			monstersThisRun.add((DuelistCard) arg0.makeStatEquivalentCopy());
			if (!monstersPlayedCombatNames.contains(arg0.originalName))
			{
				uniqueMonstersThisCombat.add((DuelistCard)arg0);
				monstersPlayedCombatNames.add(arg0.originalName);
			}
			
			if (!monstersPlayedRunNames.contains(arg0.originalName))
			{
				uniqueMonstersThisRun.add((DuelistCard)arg0);
				monstersPlayedRunNames.add(arg0.originalName);
			}
			//logger.info("incremented summonsThisCombat, new value: " + summonCombatCount);
			//logger.info("incremented summonRunCount, new value: " + summonRunCount);
			logger.info("added " + arg0.originalName + " to monstersThisCombat, monstersThisRun");
		
			if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID))
			{
				DuelistCard dC = (DuelistCard)arg0;
				if (dC.tributes < 1) { DuelistCard.summon(AbstractDungeon.player, 1, (DuelistCard)arg0); }
			}
			
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c instanceof GiantRex)
				{
					GiantRex gr = (GiantRex)c;
					if (gr.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(gr, -gr.magicNumber, true));
					}
				}
				
				if ((c instanceof ChaosAncientGearGiant) && arg0.hasTag(Tags.MACHINE))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, -dC.magicNumber, true));
					}
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{
				if (c instanceof GiantRex)
				{
					GiantRex gr = (GiantRex)c;
					if (gr.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(gr, -gr.magicNumber, true));
					}
				}
				
				if ((c instanceof ChaosAncientGearGiant) && arg0.hasTag(Tags.MACHINE))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.tributes > 0)
					{
						AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, -dC.magicNumber, true));
					}
				}
			}
		}
		
		if (arg0.hasTag(Tags.TRAP) && arg0 instanceof DuelistCard)
		{
			trapCombatCount++;
			trapRunCount++;
			trapsThisCombat.add((DuelistCard) arg0.makeStatEquivalentCopy());
			trapsThisRun.add((DuelistCard) arg0.makeStatEquivalentCopy());
			logger.info("incremented trapsThisCombat, new value: " + trapCombatCount);
			logger.info("incremented spellRunCombat, new value: " + trapRunCount);
			logger.info("added " + arg0.originalName + " to trapsThisCombat, trapsThisRun");
		}
	}

	@Override
	public void receivePostCreateStartingDeck(PlayerClass arg0, CardGroup arg1) 
	{
		boolean badMods = false;
		ArrayList<String> badModNames = new ArrayList<String>();
		badModNames.add("Insanity");
		badModNames.add("Draft");
		badModNames.add("SealedDeck");
		badModNames.add("Shiny");	
		badModNames.add("Chimera");
		for (String s : AbstractPlayer.customMods) 
		{ 
			if (badModNames.contains(s))
			{ 
				badMods = true; 
			} 
		}
		if (!badMods)
		{
			if (arg0.name().equals("THE_DUELIST"))
			{
				StarterDeckSetup.setupStartDecksB();
				ArrayList<AbstractCard> startingDeck = new ArrayList<AbstractCard>();
				startingDeck.addAll(deckToStartWith);
				if (startingDeck.size() > 0)
				{
					CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
					for (AbstractCard c : startingDeck) 
					{ 
						newStartGroup.addToRandomSpot(c); 
					}
					if (AbstractDungeon.ascensionLevel >= 10) 
					{
						newStartGroup.addToRandomSpot(new AscendersBane());
						UnlockTracker.markCardAsSeen("AscendersBane");
					}
					arg1.initializeDeck(newStartGroup);
					arg1.sortAlphabetically(true);
					lastTagSummoned = StarterDeckSetup.getCurrentDeck().getCardTag();
					if (lastTagSummoned == null) { lastTagSummoned = Tags.DRAGON; if (debug) { logger.info("starter deck has no associated card tag, so lastTagSummoned is reset to default value of DRAGON");}}
					if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck"))
					{
						if (debug) { DuelistMod.logger.info("Current Deck on receivePostCreateStartingDeck() was the Exodia Deck. Making cards soulbound..."); }
						for (AbstractCard c : arg1.group)
						{
							if (c instanceof DuelistCard)
							{
								DuelistCard dc = (DuelistCard)c;
								dc.makeSoulbound(true);
								dc.rawDescription = Strings.exodiaSoulbound + dc.rawDescription;
								dc.initializeDescription();
							}
						}
					}
				}
			}
		}
		else
		{
			logger.info("found bad mods");
		}
	}
	
	@Override
	public void receiveRelicGet(AbstractRelic arg0) 
	{
		
	}
	
	@Override
	public void receivePostDraw(AbstractCard drawnCard) 
	{
		boolean hasSmokeOrb = false;
		boolean hasSplashOrb = false;
		boolean hasLavaOrb = false;
		boolean hasFireOrb = false;
		Smoke smoke = new Smoke();
		//Lava lava = new Lava(1);
		//Splash splash = new Splash();
		FireOrb fire = new FireOrb();
		
		for (AbstractOrb orb : AbstractDungeon.player.orbs)
		{
			if (orb.name.equals(smoke.name) && orb instanceof DuelistOrb)
			{
				hasSmokeOrb = true;
				smoke = (Smoke) orb;
				if (debug) { logger.info("found a Smoke orb, set flag");  }
			}
			
			/*if (orb.name.equals(lava.name) && orb instanceof DuelistOrb)
			{
				hasLavaOrb = true;
				lava = (Lava) orb;
				if (debug) { logger.info("found a Lava orb, set flag");  }
			}*/
			
			if (orb.name.equals(fire.name) && orb instanceof DuelistOrb)
			{
				hasFireOrb = true;
				fire = (FireOrb) orb;
				if (debug) { logger.info("found a Fire orb, set flag");  }
			}
			
			/*if (orb.name.equals(splash.name) && orb instanceof DuelistOrb)
			{
				hasSplashOrb = true;
				splash = (Splash) orb;
				if (debug) { logger.info("found a Splash orb, set flag");  }
			}*/			
		}
	
		if (drawnCard.hasTag(Tags.MONSTER))
		{
			if (hasSmokeOrb) { smoke.triggerPassiveEffect((DuelistCard)drawnCard); }
			//if (hasSplashOrb) { splash.triggerPassiveEffect((DuelistCard)drawnCard); }
			//if (hasLavaOrb) { lava.triggerPassiveEffect((DuelistCard)drawnCard); }
			if (hasFireOrb) { fire.triggerPassiveEffect((DuelistCard)drawnCard); }
			
			// Underdog - Draw monster = draw 1 card
			if (AbstractDungeon.player.hasPower(HeartUnderdogPower.POWER_ID))
			{
				int handSize = AbstractDungeon.player.hand.group.size();
				if (handSize < BaseMod.MAX_HAND_SIZE)
				{
					DuelistCard.draw(1);
				}
			}
			
			if (AbstractDungeon.player.hasPower(DrillBarnaclePower.POWER_ID) && drawnCard.hasTag(Tags.AQUA))
			{
				DuelistCard.damageAllEnemiesThornsNormal(AbstractDungeon.player.getPower(DrillBarnaclePower.POWER_ID).amount);
			}
		}
		
		if (AbstractDungeon.player.hasPower(OutriggerExtensionPower.POWER_ID) && drawnCard.hasTag(Tags.MACHINE))
		{
			int magicBuffAmt = AbstractDungeon.player.getPower(OutriggerExtensionPower.POWER_ID).amount;
			ArrayList<AbstractCard> handSkills = new ArrayList<AbstractCard>();
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
	    	{
	    		if (c.type.equals(CardType.SKILL) && c.magicNumber > 0)
	    		{
	    			handSkills.add(c);
	    			if (debug) { logger.info("Outrigger Extension added " + c.originalName + " to handSkills array"); }
	    		}
	    	}
	    	if (handSkills.size() > 0) 
	    	{ 
	    		AbstractCard buffedCard = handSkills.get(AbstractDungeon.cardRandomRng.random(handSkills.size() - 1)); 
	    		AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(buffedCard, magicBuffAmt)); 
	    	}	    	
		}
		

		// Underspell - Draw spell = copy it
		if (AbstractDungeon.player.hasPower(HeartUnderspellPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (drawnCard.hasTag(Tags.SPELL) && handSize < 10)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(drawnCard.makeStatEquivalentCopy(), drawnCard.upgraded, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
		}
		
		// Undertrap - Draw trap = gain 3 HP
		if (AbstractDungeon.player.hasPower(HeartUndertrapPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (drawnCard.hasTag(Tags.TRAP))
			{
				DuelistCard.heal(AbstractDungeon.player, 3);
			}
		}
		
		// Undertribute - Draw tribute monster = Summon 1
		if (AbstractDungeon.player.hasPower(HeartUndertributePower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (drawnCard instanceof DuelistCard && drawnCard.hasTag(Tags.MONSTER))
			{
				DuelistCard ref = (DuelistCard) drawnCard;
				if (ref.tributes > 0)
				{
					DuelistCard.powerSummon(AbstractDungeon.player, 1, "Underdog Token", false);
				}
			}
		}
		
		
	}
	
	@Override
	public void receivePostDungeonInitialize() 
	{
		logger.info("dungeon initialize hook");
		if (debug) { logger.info("Playing Challenge the Spire: " + playingChallengeSpire()); }
	}
	

	@Override
	public void receivePostDungeonUpdate() 
	{
		
		
	}

	@Override
	public void receiveCustomModeMods(List<CustomMod> arg0) 
	{
		
	}
	
	@Override
	public int receiveOnPlayerLoseBlock(int arg0) 
	{
		return arg0;
	}
	
	@Override
	public boolean receivePreMonsterTurn(AbstractMonster arg0) 
	{
		playedSpellThisTurn = false;
		immortalInDiscard = false;
		AbstractPlayer p = AbstractDungeon.player;
		summonedTypesThisTurn = new ArrayList<CardTags>();
		kuribohrnFlipper = false;
		
		// Fix tributes & summons that were modified for turn only
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}
		
		for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}
		
		
		// Check to maybe print secret message
		if (summonTurnCount > 2)
		{
			int msgRoll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (debugMsg)
			{				
				AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.getRandomMonster(), "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
			}
			else
			{
				if (msgRoll <= 2)
				{
					AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.getRandomMonster(), "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
				}
			}
		}
		
		summonTurnCount = 0;
		tribTurnCount = 0;
		// Mirror Force Helper
		if (p.hasPower(MirrorForcePower.POWER_ID) && p.currentBlock > 0)
		{
			MirrorForcePower instance = (MirrorForcePower) AbstractDungeon.player.getPower(MirrorForcePower.POWER_ID);
			instance.PLAYER_BLOCK = p.currentBlock;
			if (debug) { logger.info("set mirror force power block to: " + p.currentBlock + "."); }
		}
		return true;
	}
	
	@Override
	public void receiveStartAct()
	{
		if (!shouldFill && AbstractDungeon.actNum == 1)
		{
			if (debug) { logger.info("Started act and should fill was false. Act #1! So we reset everything!!"); }
			coloredCards = new ArrayList<AbstractCard>();
			archRoll1 = -1;
			archRoll2 = -1;
			shouldFill = true;
			monstersThisRun = new ArrayList<DuelistCard>();
			monstersThisCombat = new ArrayList<DuelistCard>();
			spellsThisRun = new ArrayList<DuelistCard>();
			spellsThisCombat = new ArrayList<DuelistCard>();
			trapsThisRun = new ArrayList<DuelistCard>();
			trapsThisCombat = new ArrayList<DuelistCard>();
			uniqueMonstersThisRun = new ArrayList<DuelistCard>();
			uniqueMonstersThisCombat = new ArrayList<DuelistCard>();
			uniqueSpellsThisRun = new ArrayList<DuelistCard>();
			uniqueSpellsThisCombat = new ArrayList<DuelistCard>();
			uniqueTrapsThisRun = new ArrayList<DuelistCard>();
			uniqueTrapsThisCombat = new ArrayList<DuelistCard>();
			spellsPlayedCombatNames = new ArrayList<String>();
			monstersPlayedCombatNames = new ArrayList<String>();
			monstersPlayedRunNames = new ArrayList<String>();
			spellCombatCount = 0;
			trapCombatCount = 0;
			summonCombatCount = 0;
			summonLastCombatCount = 0;
			tributeLastCombatCount = 0;
			spellRunCount = 0;
			trapRunCount = 0;
			tribRunCount = 0;
			tribCombatCount = 0;
			summonRunCount = 0;
			dragonStr = 1;
			toonVuln = 1;
			machineArt = 1;
			zombieResummonBlock = 5;
			spellcasterBlockOnAttack = 5;
			explosiveDmgLow = 1;
			explosiveDmgHigh = 3;
			insectPoisonDmg = baseInsectPoison;
			naturiaDmg = 1;
			zombiesResummonedThisRun = 0;
			AbstractPlayer.customMods = new ArrayList<String>();
			swordsPlayed = 0;
			hasUpgradeBuffRelic = false;
			hasShopBuffRelic = false;
			hadFrozenEye = false;
			gotFrozenEyeFromBigEye = false;
			CardCrawlGame.dungeon.initializeCardPools();
		}
		else if (!shouldFill)
		{
			shouldFill = true;
			if (debug) { logger.info("Started act and should fill was false. Act was not #1, so we only reset shouldFill so the pool would be filled with different cards from any random archetypes, or unchanged if there are none."); }
		}
		logger.info("triggered start act sub");
		
	}
	
	
	
	// CONFIG MENU SETUP -------------------------------------------------------------------------------------------------------------------------------------- //
	
	// Line breakers
	private void cbLB() { yPos-=45; }
	private void genericLB(float lb) { yPos-=lb; }

	private void configPanelSetup()
	{
		ArrayList<IUIElement> settingElements = new ArrayList<IUIElement>();
		String cardsString = Config_UI_String.TEXT[5];
		String toonString = Config_UI_String.TEXT[0];
		String creatorString = Config_UI_String.TEXT[11];
		String exodiaString = Config_UI_String.TEXT[1];
		String ojamaString = Config_UI_String.TEXT[2];
		String unlockString = Config_UI_String.TEXT[8];
		String oldCharString = Config_UI_String.TEXT[12];
		String challengeString = Config_UI_String.TEXT[7];
		String flipString = Config_UI_String.TEXT[9];
		String debugString = Config_UI_String.TEXT[10];
		String setString = Config_UI_String.TEXT[4];
		String deckString = Config_UI_String.TEXT[3];
		String noCostChange = Config_UI_String.TEXT[14];
		String onlyCostDecrease = Config_UI_String.TEXT[15];
		String noTributeChange = Config_UI_String.TEXT[16];
		String onlyTributeDecrease = Config_UI_String.TEXT[17];
		String noSummonChange = Config_UI_String.TEXT[18];
		String onlySummonDecrease = Config_UI_String.TEXT[19];
		String alw = Config_UI_String.TEXT[20];
		String nev = Config_UI_String.TEXT[21];
		String eth = Config_UI_String.TEXT[22];
		String exh = Config_UI_String.TEXT[23];
		randomizedBtnStrings.add(noCostChange);
		randomizedBtnStrings.add(onlyCostDecrease);
		randomizedBtnStrings.add(noTributeChange);
		randomizedBtnStrings.add(onlyTributeDecrease);
		randomizedBtnStrings.add(noSummonChange);
		randomizedBtnStrings.add(onlySummonDecrease);
		randomizedBtnStrings.add(alw);
		randomizedBtnStrings.add(nev);
		randomizedBtnStrings.add(eth);
		randomizedBtnStrings.add(exh);

		// Card Count Label
		cardLabelTxt = new ModLabel(cardsString + cardCount, xLabPos - 10, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(cardLabelTxt);
		genericLB(50.0f);
		// END Card Count Label

		// Remove Toons
		toonBtn = new ModLabeledToggleButton(toonString,xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, toonBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			toonBtnBool = button.enabled;
			shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_TOON_BTN, toonBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Remove Toons

		// Check Remove Creator
		creatorBtn = new ModLabeledToggleButton(creatorString, xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, creatorBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			creatorBtnBool = button.enabled;
			shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CREATOR_BTN, creatorBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();
		// END Remove Creator

		// Remove Exodia
		exodiaBtn = new ModLabeledToggleButton(exodiaString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, exodiaBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			exodiaBtnBool = button.enabled;
			shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_EXODIA_BTN, exodiaBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Remove Exodia

		// Check Remove Ojama
		ojamaBtn = new ModLabeledToggleButton(ojamaString, xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, ojamaBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			ojamaBtnBool = button.enabled;
			shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_OJAMA_BTN, ojamaBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Remove Ojama

		// Unlock all decks
		unlockBtn = new ModLabeledToggleButton(unlockString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, unlockAllDecks, settingsPanel, (label) -> {}, (button) -> 
		{
			unlockAllDecks = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_UNLOCK, unlockAllDecks);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Unlock all decks

		// Switch to old character model
		oldCharBtn = new ModLabeledToggleButton(oldCharString, xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, oldCharacter, settingsPanel, (label) -> {}, (button) -> 
		{
			oldCharacter = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_OLD_CHAR, oldCharacter);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetDuelist();
		});
		cbLB();
		// END Switch to old character model

		// Challenge Mode
		challengeBtn = new ModLabeledToggleButton(challengeString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, challengeMode, settingsPanel, (label) -> {}, (button) -> 
		{
			challengeMode = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CHALLENGE, challengeMode);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Challenge Mode

		// Flip card tags
		flipBtn = new ModLabeledToggleButton(flipString, xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, flipCardTags, settingsPanel, (label) -> {}, (button) -> 
		{
			flipCardTags = button.enabled;
			if (flipCardTags) { flipBtn.text.text = Config_UI_String.TEXT[13]; }
			else { flipBtn.text.text = Config_UI_String.TEXT[9]; }
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_FLIP, flipCardTags);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		if (flipCardTags) { flipBtn.text.text = Config_UI_String.TEXT[13]; }
		else { flipBtn.text.text = Config_UI_String.TEXT[9]; }
		cbLB();
		// END Flip card tags

		// Add 2 randomization buttons
		noChangeBtnCost = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, noCostChanges, settingsPanel, (label) -> {}, (button) -> 
		{
			noCostChanges =  button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_NO_CHANGE_COST, noCostChanges);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		saver++;
		
		onlyDecBtnCost = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onlyCostDecreases, settingsPanel, (label) -> {}, (button) -> 
		{
			onlyCostDecreases = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ONLY_DEC_COST, onlyCostDecreases);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB(); saver++;
		// END Add 2 Randomziation Buttons
		
		// Add 2 randomization buttons
		noChangeBtnTrib = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, noTributeChanges, settingsPanel, (label) -> {}, (button) -> 
		{
			noTributeChanges = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_NO_CHANGE_TRIB, noTributeChanges);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		saver++;
		
		onlyDecBtnTrib = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onlyTributeDecreases, settingsPanel, (label) -> {}, (button) -> 
		{
			onlyTributeDecreases = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ONLY_DEC_TRIB, onlyTributeDecreases);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB(); saver++;
		// END Add 2 Randomziation Buttons
		
		// Add 2 randomization buttons
		noChangeBtnSumm = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, noSummonChanges, settingsPanel, (label) -> {}, (button) -> 
		{
			noSummonChanges = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_NO_CHANGE_SUMM, noSummonChanges);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		saver++;
		
		onlyIncBtnSumm = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, onlySummonIncreases, settingsPanel, (label) -> {}, (button) -> 
		{
			onlySummonIncreases = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ONLY_INC_SUMM, onlySummonIncreases);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB(); saver++;
		// END Add 2 Randomziation Buttons
		
		// Add 2 randomization buttons
		alwaysUpgradeBtn = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, alwaysUpgrade, settingsPanel, (label) -> {}, (button) -> 
		{
			alwaysUpgrade = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ALWAYS_UPGRADE, alwaysUpgrade);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		saver++;
		
		neverUpgradeBtn = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, neverUpgrade, settingsPanel, (label) -> {}, (button) -> 
		{
			neverUpgrade = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_NEVER_UPGRADE, neverUpgrade);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB(); saver++;
		// END Add 2 Randomziation Buttons
		
		// Add 2 randomization buttons
		etherealBtn = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, randomizeEthereal, settingsPanel, (label) -> {}, (button) -> 
		{
			randomizeEthereal = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_R_ETHEREAL, randomizeEthereal);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		saver++;
		
		exhaustBtn = new ModLabeledToggleButton(randomizedBtnStrings.get(saver), xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, randomizeExhaust, settingsPanel, (label) -> {}, (button) -> 
		{
			randomizeExhaust = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_R_EXHAUST, randomizeExhaust);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB(); saver++;
		// END Add 2 Randomziation Buttons
		
		// Check Box DEBUG
		allowBaseGameCardsBtn = new ModLabeledToggleButton(Strings.allowBaseGameCards,xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, baseGameCards, settingsPanel, (label) -> {}, (button) -> 
		{
			baseGameCards = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_BASE_GAME_CARDS, baseGameCards);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		//settingsPanel.addUIElement(debugBtn);
		// END Check Box DEBUG
		
		// Check Box DEBUG
		debugBtn = new ModLabeledToggleButton(debugString,xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, debug, settingsPanel, (label) -> {}, (button) -> 
		{
			debug = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_DEBUG, debug);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		//settingsPanel.addUIElement(debugBtn);
		genericLB(40);
		// END Check Box DEBUG


		// Set Size Selector
		setSelectLabelTxt = new ModLabel(setString,xLabPos, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(setSelectLabelTxt);
		setSelectColorTxt = new ModLabel(cardSets.get(setIndex),xSelection, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(setSelectColorTxt);
		genericLB(15.0f);
		setSelectLeftBtn = new ModButton(xLArrow, yPos, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
			if (setIndex == 0) { setIndex = SETS - 1; }
			else { setIndex--; }
			if (setIndex < 0) { setIndex = 0; }
			setSelectColorTxt.text = cardSets.get(setIndex);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt(PROP_SET, setIndex);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		//settingsPanel.addUIElement(setSelectLeftBtn);
		setSelectRightBtn = new ModButton(xRArrow, yPos, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
			setIndex = (setIndex+1)%SETS;
			setSelectColorTxt.text = cardSets.get(setIndex);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt(PROP_SET, setIndex);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		//settingsPanel.addUIElement(setSelectRightBtn);
		genericLB(40.0f);
		// END Set Size Selector

		// Starting Deck Selector
		setSelectLabelTxtB = new ModLabel(deckString, xLabPos, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(setSelectLabelTxtB);
		setSelectColorTxtB = new ModLabel(startingDecks.get(deckIndex),xSelection, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(setSelectColorTxtB);
		genericLB(15.0f);
		setSelectLeftBtnB = new ModButton(xLArrow, yPos, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{

			if (deckIndex == 0) { deckIndex = DECKS - 1; }
			else { deckIndex--; }
			if (deckIndex < 0) { deckIndex = 0; }
			setSelectColorTxtB.text = startingDecks.get(deckIndex);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt(PROP_DECK, deckIndex);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			StarterDeckSetup.resetStarterDeck();

		});
		//settingsPanel.addUIElement(setSelectLeftBtnB);
		setSelectRightBtnB = new ModButton(xRArrow, yPos, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{

			deckIndex = (deckIndex+1)%DECKS;
			setSelectColorTxtB.text = startingDecks.get(deckIndex);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt(PROP_DECK, deckIndex);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			StarterDeckSetup.resetStarterDeck();

		});
		// Starting Deck Selector
		
		settingsPanel.addUIElement(cardLabelTxt);
		settingsPanel.addUIElement(toonBtn);
		settingsPanel.addUIElement(creatorBtn);
		settingsPanel.addUIElement(exodiaBtn);
		settingsPanel.addUIElement(ojamaBtn);
		settingsPanel.addUIElement(unlockBtn);
		settingsPanel.addUIElement(oldCharBtn);
		settingsPanel.addUIElement(challengeBtn);
		settingsPanel.addUIElement(flipBtn);
		settingsPanel.addUIElement(noChangeBtnCost);
		settingsPanel.addUIElement(onlyDecBtnCost);
		settingsPanel.addUIElement(noChangeBtnTrib);
		settingsPanel.addUIElement(onlyDecBtnTrib);
		settingsPanel.addUIElement(noChangeBtnSumm);
		settingsPanel.addUIElement(onlyIncBtnSumm);
		settingsPanel.addUIElement(alwaysUpgradeBtn);
		settingsPanel.addUIElement(neverUpgradeBtn);
		settingsPanel.addUIElement(etherealBtn);
		settingsPanel.addUIElement(exhaustBtn);
		settingsPanel.addUIElement(allowBaseGameCardsBtn);
		settingsPanel.addUIElement(debugBtn);
		settingsPanel.addUIElement(setSelectLabelTxt);
		settingsPanel.addUIElement(setSelectColorTxt);
		settingsPanel.addUIElement(setSelectLeftBtn);
		settingsPanel.addUIElement(setSelectRightBtn);
		settingsPanel.addUIElement(setSelectLabelTxtB);
		settingsPanel.addUIElement(setSelectColorTxtB);
		settingsPanel.addUIElement(setSelectLeftBtnB);
		settingsPanel.addUIElement(setSelectRightBtnB);


	}
	
	private void setupExtraConfigStrings()
	{
		etherealForCardText = Config_UI_String.TEXT[24];
		exhaustForCardText = Config_UI_String.TEXT[25];
		powerGainCardText = Config_UI_String.TEXT[26];
		toonWorldString = Config_UI_String.TEXT[27];
		needSummonsString = Config_UI_String.TEXT[28];
		tribString = Config_UI_String.TEXT[29];
		modName = Config_UI_String.TEXT[30];
		modAuthor = Config_UI_String.TEXT[31];
		modDescription = Config_UI_String.TEXT[32];
		featherPhoCantUseString = Config_UI_String.TEXT[33];
		featherPhoCantUseStringB = Config_UI_String.TEXT[34];
		nutrientZString = Config_UI_String.TEXT[35];
		purgeString = Config_UI_String.TEXT[36];
		magnetString = Config_UI_String.TEXT[37];		
		aquaDeckString = Config_UI_String.TEXT[38];
		creatorDeckString = Config_UI_String.TEXT[39];
		dragonDeckString = Config_UI_String.TEXT[40];
		exodiaDeckString = Config_UI_String.TEXT[41];
		fiendDeckString = Config_UI_String.TEXT[42];
		generationDeckString = Config_UI_String.TEXT[43];
		healDeckString = Config_UI_String.TEXT[44];
		incDeckString = Config_UI_String.TEXT[45];
		machineDeckString = Config_UI_String.TEXT[46];
		magnetDeckString = Config_UI_String.TEXT[47];
		natureDeckString = Config_UI_String.TEXT[48];
		ojamaDeckString = Config_UI_String.TEXT[49];
		orbDeckString = Config_UI_String.TEXT[50];
		randomBigDeckString = Config_UI_String.TEXT[51];
		randomSmallDeckString = Config_UI_String.TEXT[52];
		resummonDeckString = Config_UI_String.TEXT[53];
		spellcasterDeckString = Config_UI_String.TEXT[54];
		standardDeckString = Config_UI_String.TEXT[55];
		toonDeckString = Config_UI_String.TEXT[56];
		zombieDeckString = Config_UI_String.TEXT[57];
		deckUnlockString = Config_UI_String.TEXT[58];
		deckUnlockStringB = Config_UI_String.TEXT[59];
		monsterTagString = Config_UI_String.TEXT[60];
		spellTagString = Config_UI_String.TEXT[61];
		trapTagString = Config_UI_String.TEXT[62];
		tokenTagString = Config_UI_String.TEXT[63];
		typeTagString = Config_UI_String.TEXT[64];
		orbTagString = Config_UI_String.TEXT[65];		
		exodiaAlmostAllString = Config_UI_String.TEXT[66];
		exodiaBothLegsString = Config_UI_String.TEXT[67];
		exodiaLeftArmString = Config_UI_String.TEXT[68];
		exodiaRightArmString = Config_UI_String.TEXT[69];
		exodiaBothArmsString = Config_UI_String.TEXT[70];
		exodiaLeftLegString = Config_UI_String.TEXT[71];
		exodiaRightLegString = Config_UI_String.TEXT[72];		
		Strings.configChooseString = Config_UI_String.TEXT[73];
		Strings.configAddCardHandString = Config_UI_String.TEXT[74];
		Strings.configAddCardHandPluralString = Config_UI_String.TEXT[75];
		Strings.configResummonRandomlyString = Config_UI_String.TEXT[76];
		Strings.configResummonRandomlyPluralString = Config_UI_String.TEXT[77];
		Strings.configResummonRandomlyTargetString = Config_UI_String.TEXT[78];
		Strings.configResummonRandomlyTargetPluralString = Config_UI_String.TEXT[79];
		Strings.configCardPlayString = Config_UI_String.TEXT[80];
		Strings.configCardPlayPluralString = Config_UI_String.TEXT[81];
		Strings.configCardPlayTargetString = Config_UI_String.TEXT[82];
		Strings.configCardPlayTargetPluralString = Config_UI_String.TEXT[83];
		Strings.configChooseAString = Config_UI_String.TEXT[84];
		Strings.configOrAString = Config_UI_String.TEXT[85];
		Strings.configToAddToYourHandString = Config_UI_String.TEXT[86];
		Strings.configSOrString = Config_UI_String.TEXT[87];
		Strings.configToAddToYourHandPluralString = Config_UI_String.TEXT[88];
		Strings.configBuffToGainString = Config_UI_String.TEXT[89];
		Strings.configBuffToGainPluralString = Config_UI_String.TEXT[90];	
		Strings.configDraw1Card = Config_UI_String.TEXT[91];
		Strings.configDraw2Cards = Config_UI_String.TEXT[92];
		Strings.configLose5HP = Config_UI_String.TEXT[93];
		Strings.configApply2RandomDebuffs = Config_UI_String.TEXT[94];
		Strings.configApply1RandomDebuff = Config_UI_String.TEXT[95];
		Strings.configAddRandomTrap = Config_UI_String.TEXT[96];
		Strings.configAddRandomMonster = Config_UI_String.TEXT[97];
		Strings.configAddRandomEtherealDuelist = Config_UI_String.TEXT[98];
		Strings.configAddRandomSpellcaster = Config_UI_String.TEXT[99];
		Strings.configGain10HP = Config_UI_String.TEXT[100];
		Strings.configGain5HP = Config_UI_String.TEXT[101];
		Strings.configGain15Block = Config_UI_String.TEXT[102];
		Strings.configGain10Block = Config_UI_String.TEXT[103];
		Strings.configGain5Block = Config_UI_String.TEXT[104];
		Strings.configGainGoldA = Config_UI_String.TEXT[105];
		Strings.configGainGoldB = Config_UI_String.TEXT[106];
		Strings.configGainArtifact = Config_UI_String.TEXT[107];
		Strings.configGainEnergy = Config_UI_String.TEXT[108];
		Strings.configGain2Energies = Config_UI_String.TEXT[109];
		Strings.configSummon = Config_UI_String.TEXT[110];
		Strings.configSummon2 = Config_UI_String.TEXT[111];
		Strings.configIncrement = Config_UI_String.TEXT[112];
		Strings.configIncrement2 = Config_UI_String.TEXT[113];
		Strings.configOrbSlots = Config_UI_String.TEXT[114];
		Strings.configOjamania = Config_UI_String.TEXT[115];
		Strings.configChannel = Config_UI_String.TEXT[116];
		Strings.configLose1HP = Config_UI_String.TEXT[117];
		Strings.configSummonsIconText = Config_UI_String.TEXT[118];		
		Strings.configFailedIncActionText = Config_UI_String.TEXT[119];
		Strings.configFailedSummonActionText = Config_UI_String.TEXT[120];
		Strings.configFailedTribActionText = Config_UI_String.TEXT[121];
		Strings.configGain1MAXHPText = Config_UI_String.TEXT[122];
		Strings.configGreedShardA = Config_UI_String.TEXT[123];
		Strings.configGreedShardB = Config_UI_String.TEXT[124];		
		Strings.configWingedTextB = Config_UI_String.TEXT[125];
		Strings.configRainbowJarA = Config_UI_String.TEXT[126];
		Strings.configRainbowJarB = Config_UI_String.TEXT[127];
		Strings.configWingedTextA = Config_UI_String.TEXT[128];
		Strings.configGreedShardC = Config_UI_String.TEXT[129];	
		Strings.configYamiFormA = Config_UI_String.TEXT[130];	
		Strings.configYamiFormB = Config_UI_String.TEXT[131];	
		Strings.configRainbow = Config_UI_String.TEXT[132];	
		Strings.configRainbowB = Config_UI_String.TEXT[133];
		Strings.powerGain0Text = Config_UI_String.TEXT[134];
		Strings.allowBaseGameCards = Config_UI_String.TEXT[135];
		Strings.exodiaSoulbound = Config_UI_String.TEXT[136];
	}
	
	private void addRandomized(String property1, String property2, boolean btnBool1, boolean btnBool2)
	{
		
	}
	
	private void resetDuelist() 
	{
		if (oldCharacter) { characterModel = oldChar; }
		else { characterModel = defaultChar; }
	}

	@Override
	public void receivePostObtainCard(AbstractCard card) 
	{
		if (card instanceof OnObtainEffect)
		{
			((OnObtainEffect) card).onObtain();
		}			
	}
}
