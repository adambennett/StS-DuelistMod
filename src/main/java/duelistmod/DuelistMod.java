package duelistmod;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import basemod.eventUtil.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.rewards.*;
import duelistmod.enums.*;
import duelistmod.metrics.*;
import duelistmod.ui.*;
import duelistmod.variables.Colors;
import infinitespire.quests.*;
import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.audio.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.saveAndContinue.*;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.interfaces.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.*;
import duelistmod.cards.*;
import duelistmod.cards.curses.DuelistAscender;
import duelistmod.cards.incomplete.RevivalRose;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.machine.ChaosAncientGearGiant;
import duelistmod.characters.TheDuelist;
import duelistmod.events.*;
import duelistmod.helpers.*;
import duelistmod.helpers.crossover.*;
import duelistmod.interfaces.*;
import duelistmod.monsters.*;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.potions.*;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.incomplete.*;
import duelistmod.relics.*;
import duelistmod.relics.ElectricToken;
import duelistmod.relics.MachineToken;
import duelistmod.relics.SpellcasterToken;
import duelistmod.rewards.BoosterPack;
import duelistmod.speedster.mechanics.AbstractSpeedTime;
import duelistmod.variables.*;

@SpireInitializer 
public class DuelistMod 
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber,
PreMonsterTurnSubscriber, PostDungeonUpdateSubscriber, StartActSubscriber, PostObtainCardSubscriber, PotionGetSubscriber, StartGameSubscriber,
PostUpdateSubscriber, RenderSubscriber, PostRenderSubscriber, PreRenderSubscriber
{
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());
	
	// Member fields
	public static String version = "v3.481.17";
	public static Mode modMode = Mode.PROD;
	public static String trueVersion = version.substring(1);
	private static String modName = "Duelist Mod";
	private static String modAuthor = "Nyoxide";
	private static String modDescription = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	private static final String modID = "duelistmod";
	private static final ArrayList<String> cardSets = new ArrayList<>();
	private static final int SETS = 10;
	private static int saver = 0;
	private static ArrayList<IncrementDiscardSubscriber> incrementDiscardSubscribers;
	public static ChallengeIcon topPanelChallengeIcon;

	// Global Fields
	// Config Settings
	public static BonusDeckUnlockHelper bonusUnlockHelper;
	public static ArrayList<String> randomizedBtnStrings = new ArrayList<>();
	public static String lastCardPoolFullList = "";
	public static String saveSlotA = "";
	public static String saveSlotB = "";
	public static String saveSlotC = "";
	public static final String PROP_TOON_BTN = "toonBtnBool";
	public static final String PROP_EXODIA_BTN = "exodiaBtnBool";
	public static final String PROP_OJAMA_BTN = "ojamaBtnBool";
	public static final String PROP_CREATOR_BTN = "creatorBtnBool";
	public static final String PROP_OLD_CHAR = "oldCharacter";
	public static final String PROP_SET = "setIndex";
	public static final String PROP_DECK = "deckIndex";
	public static final String PROP_CARDS = "cardCount";
	public static final String PROP_MAX_SUMMONS = "lastMaxSummons";
	public static final String PROP_RESUMMON_DMG = "resummonDeckDamage";
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
	public static final String PROP_WISEMAN = "gotWisemanHaunted";
	public static final String PROP_FORCE_PUZZLE = "forcePuzzleSummons";
	public static final String PROP_ALLOW_BOOSTERS = "allowBoosters";
	public static final String PROP_ALWAYS_BOOSTERS = "alwaysBoosters";
	public static final String PROP_REMOVE_CARD_REWARDS = "removeCardRewards";
	public static final String PROP_SMALL_BASIC = "smallBasicSet";
	public static final String PROP_DUELIST_MONSTERS = "duelistMonsters";
	public static final String PROP_DUELIST_CURSES = "duelistCurses";
	public static final String PROP_CARD_REWARD_RELIC = "hasCardRewardRelic";
	public static final String PROP_BOOSTER_REWARD_RELIC = "hasBoosterRewardRelic";
	public static final String PROP_SHOP_DUPE_RELIC = "hasShopDupeRelic";
	public static final String PROP_ADD_ORB_POTIONS = "addOrbPotions";
	public static final String PROP_ADD_ACT_FOUR = "actFourEnabled";
	public static final String PROP_PLAY_KAIBA = "playAsKaiba";
	public static final String PROP_MONSTER_IS_KAIBA = "monsterIsKaiba";
	public static final String PROP_LAST_CARD_POOL = "lastCardPoolFullList";	
	public static final String PROP_SAVE_SLOT_A = "saveSlotA";	
	public static final String PROP_SAVE_SLOT_B = "saveSlotB";	
	public static final String PROP_SAVE_SLOT_C = "saveSlotC";	
	public static final String PROP_ALLOW_CARD_POOL_RELICS = "allowCardPoolRelics";
	public static final String PROP_MONSTERS_RUN = "loadedUniqueMonstersThisRunList";	
	public static final String PROP_SPELLS_RUN = "loadedSpellsThisRunList";	
	public static final String PROP_TRAPS_RUN = "loadedTrapsThisRunList";
	public static String characterModel = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String yugiChar = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String oldYugiChar = "duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml";
	public static final String kaibaPlayerModel = "duelistModResources/images/char/duelistCharacterUpdate/KaibaPlayer.scml";
	public static String kaibaEnemyModel = "KaibaModel2";
	public static Properties duelistDefaults = new Properties();
	public static boolean allowLocaleUpload = true;
	public static boolean toonBtnBool = false;
	public static boolean exodiaBtnBool = false;
	public static boolean ojamaBtnBool = false;
	public static boolean creatorBtnBool = false;
	public static boolean oldCharacter = false;
	public static boolean playAsKaiba = false;
	public static boolean challengeLevel20 = false;
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
	public static boolean randomizeExhaust = false;
	public static boolean randomizeEthereal = false;
	public static boolean baseGameCards = false;
	public static boolean gotWisemanHaunted = false;
	public static boolean forcePuzzleSummons = false;
	public static boolean allowBoosters = false;
	public static boolean alwaysBoosters = false;
	public static boolean removeCardRewards = false;
	public static boolean smallBasicSet = false;
	public static boolean duelistMonsters = false;
	public static boolean duelistCurses = false;
	public static boolean quicktimeEventsAllowed = false;
	public static boolean addOrbPotions = false;
	public static boolean playedBug = false;
	public static boolean playedSecondBug = false;
	public static boolean playedSpider = false;
	public static boolean playedSecondSpider = false;
	public static boolean playedThirdSpider = false;
	public static boolean monsterIsKaiba = true;
	public static boolean playingChallenge = false;
	public static boolean playedVampireThisTurn = false;
	public static boolean badBoosterSituation = false;
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
	public static String selectedDeck = "";
	public static String firstRandomDeck = "";
	public static String secondRandomDeck = "";
	public static String loadedUniqueMonstersThisRunList = "";
	public static String loadedSpellsThisRunList = "";
	public static String loadedTrapsThisRunList = "";
	public static String entombedCardsThisRunList = "";
	public static String entombedCustomCardProperites = "";
	public static String battleEntombedList = "";
	
	// Maps and Lists
	public static final HashMap<Integer, Texture> characterPortraits = new HashMap<>();
	public static HashMap<String, DuelistCard> summonMap = new HashMap<>();
	public static HashMap<String, AbstractPower> buffMap = new HashMap<>();
	public static HashMap<String, AbstractOrb> invertStringMap = new HashMap<>();
	public static HashMap<String, StarterDeck> starterDeckNamesMap = new HashMap<>();
	public static HashMap<String, CardTags> typeCardMap_NameToString = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_ID = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_IMG = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_NAME = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_DESC = new HashMap<>();
	public static HashMap<CardTags, Integer> monsterTypeTributeSynergyFunctionMap = new HashMap<>();
	public static Map<String, DuelistCard> orbCardMap = new HashMap<>();
	public static Map<CardTags, StarterDeck> deckTagMap = new HashMap<>();
	public static Map<String, AbstractCard> mapForCardPoolSave = new HashMap<>();
	public static Map<String, AbstractCard> mapForRunCardsLoading = new HashMap<>();
	public static Map<String, AbstractCard> uniqueSpellsThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueMonstersThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueTrapsThisRunMap = new HashMap<>();
	public static Map<String, AbstractPotion> duelistPotionMap = new HashMap<>();
	public static Map<String, String> magicNumberCards = new HashMap<>();
	public static Map<String, String> summonCards = new HashMap<>();
	public static Map<String, String> tributeCards = new HashMap<>();
	public static Map<String, String> dungeonCardPool = new HashMap<>();
	public static Map<String, String> totallyRandomCardMap = new HashMap<>();

	// Tier Scores -  (Pool)     (CardId)     (Act)   (Score)
	public static Map<String, Map<String, Map<Integer, Integer>>> cardTierScores = new HashMap<>();
	public static List<String> secondaryTierScorePools = new ArrayList<>();

	public static ArrayList<BoosterPack> currentBoosters = new ArrayList<>();
	public static ArrayList<DuelistCard> deckToStartWith = new ArrayList<>();
	public static ArrayList<DuelistCard> standardDeck = new ArrayList<>();
	public static ArrayList<DuelistCard> orbCards = new ArrayList<>();
	public static ArrayList<DuelistCard> myCards = new ArrayList<>();
	public static ArrayList<DuelistCard> myNamelessCards = new ArrayList<>();
	public static ArrayList<DuelistCard> myStatusCards = new ArrayList<>();
	public static ArrayList<DuelistCard> rareCards = new ArrayList<>();
	public static ArrayList<DuelistCard> rareNonPowers = new ArrayList<>();
	public static ArrayList<DuelistCard> nonPowers = new ArrayList<>();
	public static ArrayList<DuelistCard> allPowers = new ArrayList<>();
	public static ArrayList<DuelistCard> merchantPendantPowers = new ArrayList<>();
	public static ArrayList<DuelistCard> uncommonCards = new ArrayList<>();
	public static ArrayList<DuelistCard> commonCards = new ArrayList<>();
	public static ArrayList<DuelistCard> nonRareCards = new ArrayList<>();
	public static ArrayList<DuelistCard> curses = new ArrayList<>();
	public static ArrayList<DuelistCard> uniqueMonstersThisRun = new ArrayList<>();
	public static ArrayList<DuelistCard> uniqueSpellsThisCombat = new ArrayList<>();
	public static ArrayList<DuelistCard> uniqueSpellsThisRun = new ArrayList<>();
	public static ArrayList<AbstractCard> entombedCards = new ArrayList<>();
	public static ArrayList<AbstractCard> entombedCardsCombat = new ArrayList<>();
	public static ArrayList<DuelistCard> uniqueTrapsThisRun = new ArrayList<>();
	public static ArrayList<AbstractCard> arcaneCards = new ArrayList<>();
	public static ArrayList<AbstractCard> toReplacePoolWith = new ArrayList<>();
	public static ArrayList<AbstractCard> uniqueSkillsThisCombat = new ArrayList<>();
	public static ArrayList<AbstractCard> metronomes = new ArrayList<>();
	public static ArrayList<AbstractCard> cardsForRandomDecks = new ArrayList<>();
	public static ArrayList<AbstractCard> coloredCards = new ArrayList<>();
	public static ArrayList<AbstractCard> duelColorlessCards = new ArrayList<>();
	public static ArrayList<AbstractCard> rareCardInPool = new ArrayList<>();
	public static ArrayList<AbstractCard> metronomeResummonsThisCombat = new ArrayList<>();
	public static ArrayList<AbstractCard> holidayNonDeckCards = new ArrayList<>();
	public static ArrayList<AbstractCard> totallyRandomCardList = new ArrayList<>();
	public static ArrayList<AbstractCard> waterHazardCards = new ArrayList<>();
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<>();
	public static ArrayList<AbstractPotion> allDuelistPotions = new ArrayList<>();
	public static ArrayList<AbstractRelic> duelistRelicsForTombEvent = new ArrayList<>();
	public static ArrayList<String> skillsPlayedCombatNames = new ArrayList<>();
	public static ArrayList<String> spellsPlayedCombatNames = new ArrayList<>();
	public static ArrayList<String> monstersPlayedCombatNames = new ArrayList<>();
	public static ArrayList<String> monstersPlayedRunNames = new ArrayList<>();
	public static ArrayList<String> randomBuffStrings = new ArrayList<>();
	public static ArrayList<String> invertableOrbNames = new ArrayList<>();
	public static ArrayList<String> godsPlayedNames = new ArrayList<>();
	public static ArrayList<String> orbPotionIDs = new ArrayList<>();
	public static ArrayList<CardTags> monsterTypes = new ArrayList<>();
	public static ArrayList<CardTags> summonedTypesThisTurn = new ArrayList<>();
	public static ArrayList<StarterDeck> starterDeckList = new ArrayList<>();
	public static AbstractCard holidayDeckCard; 
	public static boolean addingHolidayCard = false;
	
	// Global Flags
	public static boolean machineArtifactFlipper = false;
	public static boolean resetProg = false;
	public static boolean checkTrap = false;
	public static boolean checkUO = false;
	public static boolean gotMimicLv3 = false;
	public static boolean upgradedMimicLv3 = false;
	public static boolean playedOneCardThisCombat = false;
	public static boolean hasCardRewardRelic = false;
	public static boolean hasBoosterRewardRelic = false;
	public static boolean hasShopDupeRelic = false;
	public static boolean shouldReplacePool = false;
	public static boolean replacingOnUpdate = false;
	public static boolean replacedCardPool = false;
	public static boolean relicReplacement = false;
	public static boolean selectingForRelics = false;
	public static boolean selectingCardPoolOptions = false;
	public static boolean dragonRelicBFlipper = false;
	public static boolean playedSpellThisTurn = false;
	public static boolean kuribohrnFlipper = false;
	public static boolean hasUpgradeBuffRelic = false;
	public static boolean hasShopBuffRelic = false;
	public static boolean hasPuzzle = true;
	public static boolean hadFrozenEye = false;
	public static boolean gotFrozenEyeFromBigEye = false;	
	public static boolean spellcasterDidChannel = false;
	public static boolean warriorTribThisCombat = false;
	public static boolean wyrmTribThisCombat = false;
	public static boolean wasEliteCombat = false;	
	public static boolean wasBossCombat = false;
	public static boolean mirrorLadybug = false;
	public static boolean poolIsCustomized = false;
	public static boolean allowCardPoolRelics = true;
	public static boolean wasViewingSummonCards = false;
	public static boolean wasViewingSelectScreen = false;
	public static boolean isConspire = Loader.isModLoaded("conspire");
	public static boolean isReplay = Loader.isModLoaded("ReplayTheSpireMod");
	public static boolean isHubris = Loader.isModLoaded("hubris");
	public static boolean isDisciple = Loader.isModLoaded("chronomuncher");
	public static boolean isClockwork = Loader.isModLoaded("ClockworkMod");
	public static boolean isGatherer = Loader.isModLoaded("gatherermod");
	public static boolean isInfiniteSpire = Loader.isModLoaded("infinitespire");	
	public static boolean isAnimator = Loader.isModLoaded("eatyourbeetsvg-theanimator");	
	public static boolean isGifTheSpire = Loader.isModLoaded("GifTheSpireLib");	
	public static boolean isAscendedDeckOneUnlocked = false;
	public static boolean isAscendedDeckTwoUnlocked = false;
	public static boolean isAscendedDeckThreeUnlocked = false;
	public static boolean isPharaohDeckOneUnlocked = false;
	public static boolean isPharaohDeckTwoUnlocked = false;
	public static boolean isPharaohDeckThreeUnlocked = false;
	public static boolean isPharaohDeckFourUnlocked = false;
	public static boolean isPharaohDeckFiveUnlocked = false;
	public static boolean isExtraRandomDecksUnlocked = false;
	public static boolean lastDeckViewWasCustomScreen = false;
	public static boolean addedAquaSet = false;
	public static boolean addedDragonSet = false;
	public static boolean addedFiendSet = false;
	public static boolean addedIncrementSet = false;
	public static boolean addedInsectSet = false;
	public static boolean addedMachineSet = false;
	public static boolean addedNaturiaSet = false;
	public static boolean addedPlantSet = false;
	public static boolean addedSpellcasterSet = false;
	public static boolean addedStandardSet = false;
	public static boolean addedWarriorSet = false;
	public static boolean addedZombieSet = false;
	public static boolean addedRockSet = false;
	public static boolean addedOjamaSet = false;
	public static boolean addedToonSet = false;
	public static boolean addedDinoSet = false;
	public static boolean addedArcaneSet = false;
	public static boolean addedRandomCards = false;
	public static boolean addedRedSet = false;
	public static boolean addedBlueSet = false;
	public static boolean addedGreenSet = false;
	public static boolean addedPurpleSet = false;
	public static boolean allowDuelistEvents = true;
	public static boolean addedHalloweenCards = false;
	public static boolean addedBirthdayCards = false;
	public static boolean addedXmasCards = false;
	public static boolean addedWeedCards = false;
	public static boolean neverChangedBirthday = true;
	public static boolean checkedCardPool = false;
	public static boolean overflowedThisTurn = false;
	public static boolean overflowedLastTurn = false;
	public static boolean bookEclipseThisCombat = false;
	public static boolean boosterDeath = false;
	public static boolean isSettingsUp = false;
	
	// Numbers
	public static int duelistScore = 1;
	public static final int baseInsectPoison = 1;
	public static int randomDeckSmallSize = 10;
	public static int randomDeckBigSize = 15;
	public static int cardCount = 75;
	public static int setIndex = 0;
	public static int lowNoBuffs = 3;
	public static int highNoBuffs = 6;
	public static int lastMaxSummons = 5;
	public static int defaultMaxSummons = 5;
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
	public static int resummonDeckDamage = 1;
	public static int deckIndex = 0;	// MARKERBOY
	public static int normalSelectDeck = -1;
	public static int dragonStr = 2;
	public static int toonVuln = 1;
	public static int insectPoisonDmg = 1;
	public static int plantConstricted = 2;
	public static int predaplantThorns = 1;	
	public static int fiendDraw = 1;
	public static int aquaInc = 1;
	public static int superheavyDex = 1;
	public static int naturiaVines = 1;
	public static int naturiaLeaves = 1;
	public static int machineArt = 1;
	public static int rockBlock = 2;
	public static int zombieResummonBlock = 6;
	public static int extraCardsFromRandomArch = 25;
	public static int archRoll1 = -1;
	public static int archRoll2 = -1;	
	public static int bugTempHP = 5;
	public static int spiderTempHP = 7;	
	public static int gravAxeStr = -99;
	public static int poisonAppliedThisCombat = 0;
	public static int zombiesResummonedThisCombat = 0;
	public static int zombiesResummonedThisRun = 0;
	public static final int explosiveDamageLowDefault = 2;
	public static final int explosiveDamageHighDefault = 6;
	public static int explosiveDmgLow = explosiveDamageLowDefault;
	public static int explosiveDmgHigh = explosiveDamageHighDefault;
	public static int superExplodgeMultLow = 3;
	public static int superExplodgeMultHigh = 4;	
	public static int spellcasterBlockOnAttack = 4;
	public static int spellcasterRandomOrbsChanneled = 0;
	public static int currentSpellcasterOrbChance = 25;
	public static int summonLastCombatCount = 0;
	public static int tributeLastCombatCount = 0;
	public static int godsPlayedForBonus = 0;
	public static int lastPackRoll = 0;
	public static int lastFiendBonus = 0;
	public static int lastTurnHP = -1;
	public static int secondLastTurnHP = -1;
	public static int spectralDamageMult = 2;	
	public static int spellsObtained = 0;
	public static int trapsObtained = 0;
	public static int monstersObtained = 0;	
	public static int synergyTributesRan = 0;
	public static int highestMaxSummonsObtained = 5;
	public static int resummonsThisRun = 0;
	public static int warriorTribEffectsPerCombat = 1;
	public static int warriorTribEffectsTriggeredThisCombat = 0;
	public static int namelessTombMagicMod = 5;
	public static int namelessTombPowerMod = 8;
	public static int namelessTombGoldMod = 20;
	public static int challengeLevel = 0;
	public static int birthdayMonth = 1;
	public static int birthdayDay = 1;
	public static int tokensThisCombat = 0;
	public static int sevenCompletedsThisCombat = 0;
	public static int overflowsThisCombat = 0;
	public static int currentZombieSouls = 0;
	public static int defaultStartZombieSouls = 3;
	public static int vampiresPlayed = 0;
	public static int mayakashiPlayed = 0;
	public static int vendreadPlayed = 0;
	public static int shiranuiPlayed = 0;
	public static int ghostrickPlayed = 0;
	public static int corpsesEntombed = 0;
	
	
	// Other
	public static TheDuelist duelistChar;
	public static StarterDeck currentDeck;
	public static CombatIconViewer combatIconViewer;
	public static CardTags lastTagSummoned = Tags.ALL;
	public static CardTags chosenDeckTag = Tags.STANDARD_DECK;
	public static CardTags chosenRockSunriseTag = Tags.DUMMY_TAG;
	public static AbstractCard lastCardPlayed;
	public static AbstractCard secondLastCardPlayed;
	public static AbstractCard lastPlantPlayed;
	public static AbstractCard secondLastPlantPlayed;
	public static AbstractCard lastGhostrickPlayed;
	public static AbstractCard secondLastGhostrickPlayed;
	public static AbstractCard lastCardDrawn;
	public static AbstractCard secondLastCardDrawn;
	public static AbstractCard lastCardObtained;
	public static AbstractCard lastCardResummoned;
	public static AbstractCard firstCardInGraveThisCombat;
	public static AbstractCard battleFusionMonster;
	public static AbstractCard firstCardResummonedThisCombat;	
	public static AbstractCard firstMonsterResummonedThisCombat;	
	public static AbstractSpeedTime speedScreen;
	
	
	// Config Menu
	public static float yPos = 760.0f;
	public static float xLabPos = 360.0f;
	public static float xLArrow = 800.0f;
	public static float xRArrow = 1500.0f;
	public static float xSelection = 900.0f;
	public static float xSecondCol = 490.0f;
	public static float xThirdCol = 475.0f;
	public static UIStrings Config_UI_String;
	public static ModPanel settingsPanel;
	public static ModLabel cardLabelTxt;
	public static ModLabeledToggleButton toonBtn;
	public static ModLabeledToggleButton creatorBtn;
	public static ModLabeledToggleButton exodiaBtn;
	public static ModLabeledToggleButton ojamaBtn;
	public static ModLabeledToggleButton unlockBtn;
	public static ModLabeledToggleButton oldCharBtn;
	public static ModLabeledToggleButton kaibaCharBtn;
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
	public static ModLabeledToggleButton forcePuzzleBtn;
	public static ModLabeledToggleButton allowBoostersBtn;
	public static ModLabeledToggleButton alwaysBoostersBtn;
	public static ModLabeledToggleButton removeCardRewardsBtn;
	public static ModLabeledToggleButton lightBasicBtn;
	public static ModLabeledToggleButton noDuelistMonstersBtn;
	public static ModLabeledToggleButton noDuelistCursesBtn;
	public static ModLabeledToggleButton addOrbPotionsBtn;
	public static ModLabeledToggleButton quickTimeBtn;
	public static ModLabeledToggleButton cardPoolRelicsBtn;
	public static ModLabeledToggleButton allowDuelistEventsBtn;
	public static ModLabeledToggleButton allowLocaleBtn;
	public static ModLabel setSelectLabelTxt;
	public static ModLabel setSelectColorTxt;
	public static ModButton setSelectLeftBtn;
	public static ModButton setSelectRightBtn;
	public static ModLabel birthdayLabel;
	public static ModLabel birthdayMonthLabel;
	public static ModLabel birthdayDayLabel;
	public static ModLabel birthdayMonthTxt;
	public static ModLabel birthdayDayTxt;		
	public static ModButton birthdayMonthRightBtn;
	public static ModButton birthdayMonthLeftBtn;
	public static ModButton birthdayDayRightBtn;
	public static ModButton birthdayDayLeftBtn;

	

	// Global Character Stats
	public static int energyPerTurn = 3;
	public static int startHP = 80;
	public static int maxHP = 80;
	public static int startGold = 125;
	public static int cardDraw = 5;
	public static int orbSlots = 3;
	
	
	// Turn off for Workshop releases, just prints out stuff and adds debug cards/tokens to game
	public static boolean debug = false;				// print statements only, used in mod option panel
	public static boolean debugMsg = false;				// for secret msg
	public static final boolean addTokens = false;		// adds debug tokens to library
	public static boolean allowBonusDeckUnlocks = true;	// turn bonus deck unlocks (Ascended/Pharaoh Decks) on
	public static boolean allowChallengeMode = true;	// turn challenge mode options on the selection screen on/off

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

    public static String makeRelicOutlinePath(String resourcePath) { return makePath("relics/outline/" + resourcePath); }

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
    
    public static String makeCharAudioPath(String resourcePath)
    {
    	return makeAudioPath("char/" + resourcePath);
    }
    
    public static String makeOrbAudioPath(String resourcePath)
    {
    	return makeAudioPath("orbs/" + resourcePath);
    }
    
    public static String makeAnimatedPath(String resourcePath) {
        return makePath("animated/" + resourcePath);
    }

    public static String makeSpeedsterPath(String resourcePath) {
        return makePath("ui/speedster/" + resourcePath);
    }
    // =============== /MAKE IMAGE PATHS/ =================


	// =============== /INPUT TEXTURE LOCATION/ =================

	// =============== IMAGE PATHS =================
	/**
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	public static String makePath(String resource) {
		return Strings.DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
	}
	
	public static String makeAudioPath(String resource)
	{
		return Strings.DEFAULT_MOD_AUDIO_FOLDER + "/" + resource;
	}

	// =============== /IMAGE PATHS/ =================

	// =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

	public DuelistMod() {
		logger.info("Subscribe to BaseMod hooks");
		BaseMod.subscribe(this);
		logger.info("Done subscribing");

		// Register purple for Traps
		BaseMod.addColor(AbstractCardEnum.DUELIST_TRAPS, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE,
				Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, makePath(Strings.ATTACK_DEFAULT_PURPLE),
				makePath(Strings.SKILL_DEFAULT_PURPLE), makePath(Strings.POWER_DEFAULT_PURPLE),
				makePath(Strings.ENERGY_ORB_DEFAULT_PURPLE), makePath(Strings.ATTACK_DEFAULT_PURPLE_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_PURPLE_PORTRAIT), makePath(Strings.POWER_DEFAULT_PURPLE_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_PURPLE_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_PURPLE));
		
		// Register green for Spells
		BaseMod.addColor(AbstractCardEnum.DUELIST_SPELLS, Colors.CARD_GREEN, Colors.CARD_GREEN, Colors.CARD_GREEN,
				Colors.CARD_GREEN, Colors.CARD_GREEN, Colors.CARD_GREEN, Colors.CARD_GREEN, makePath(Strings.ATTACK_DEFAULT_GREEN),
				makePath(Strings.SKILL_DEFAULT_GREEN), makePath(Strings.POWER_DEFAULT_GREEN),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN), makePath(Strings.ATTACK_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_GREEN_PORTRAIT), makePath(Strings.POWER_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_GREEN));
		
		// Register yellow for Monsters
		BaseMod.addColor(AbstractCardEnum.DUELIST_MONSTERS, Colors.CARD_YELLOW, Colors.CARD_YELLOW, Colors.CARD_YELLOW,
				Colors.CARD_YELLOW, Colors.CARD_YELLOW, Colors.CARD_YELLOW, Colors.CARD_YELLOW, makePath(Strings.ATTACK_DEFAULT_YELLOW),
				makePath(Strings.SKILL_DEFAULT_YELLOW), makePath(Strings.POWER_DEFAULT_YELLOW),
				makePath(Strings.ENERGY_ORB_DEFAULT_YELLOW), makePath(Strings.ATTACK_DEFAULT_YELLOW_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_YELLOW_PORTRAIT), makePath(Strings.POWER_DEFAULT_YELLOW_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_YELLOW_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_YELLOW));
		
		// Register blue for Tokens
		BaseMod.addColor(AbstractCardEnum.DUELIST, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE,
				Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, makePath(Strings.ATTACK_DEFAULT_BLUE),
				makePath(Strings.SKILL_DEFAULT_BLUE), makePath(Strings.POWER_DEFAULT_BLUE),
				makePath(Strings.ENERGY_ORB_DEFAULT_BLUE), makePath(Strings.ATTACK_DEFAULT_BLUE_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_BLUE_PORTRAIT), makePath(Strings.POWER_DEFAULT_BLUE_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_BLUE_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_BLUE));
		
		// Register red for Red Medicine buff options
		BaseMod.addColor(AbstractCardEnum.DUELIST_SPECIAL, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE,
				Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, makePath(Strings.ATTACK_DEFAULT_RED),
				makePath(Strings.SKILL_DEFAULT_RED), makePath(Strings.POWER_DEFAULT_RED),
				makePath(Strings.ENERGY_ORB_DEFAULT_RED), makePath(Strings.ATTACK_DEFAULT_RED_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_RED_PORTRAIT), makePath(Strings.POWER_DEFAULT_RED_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_RED_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_RED));

		// Register CRC color for custom cards from Book of Life
		BaseMod.addColor(AbstractCardEnum.DUELIST_CRC, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE,
				Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, Colors.CARD_PURPLE, makePath(Strings.ATTACK_DEFAULT_CRC),
				makePath(Strings.SKILL_DEFAULT_CRC), makePath(Strings.POWER_DEFAULT_CRC),
				makePath(Strings.ENERGY_ORB_DEFAULT_CRC), makePath(Strings.ATTACK_DEFAULT_CRC_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_CRC_PORTRAIT), makePath(Strings.POWER_DEFAULT_CRC_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_CRC_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_CRC));

		duelistDefaults.setProperty(PROP_TOON_BTN, "TRUE");
		duelistDefaults.setProperty(PROP_EXODIA_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_OJAMA_BTN, "TRUE");
		duelistDefaults.setProperty(PROP_CREATOR_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_OLD_CHAR, "FALSE");
		duelistDefaults.setProperty(PROP_SET, "0");
		duelistDefaults.setProperty(PROP_DECK, "0");
		duelistDefaults.setProperty(PROP_CARDS, "200");
		duelistDefaults.setProperty(PROP_MAX_SUMMONS, "5");
		duelistDefaults.setProperty(PROP_RESUMMON_DMG, "1");
		duelistDefaults.setProperty(PROP_UNLOCK, "FALSE");
		duelistDefaults.setProperty(PROP_FLIP, "FALSE");
		duelistDefaults.setProperty(PROP_RESET, "FALSE");
		duelistDefaults.setProperty(PROP_DEBUG, "FALSE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_COST, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_DEC_COST, "TRUE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_TRIB, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_DEC_TRIB, "FALSE");
		duelistDefaults.setProperty(PROP_NO_CHANGE_SUMM, "FALSE");
		duelistDefaults.setProperty(PROP_ONLY_INC_SUMM, "FALSE");
		duelistDefaults.setProperty(PROP_R_ETHEREAL, "TRUE");
		duelistDefaults.setProperty(PROP_R_EXHAUST, "TRUE");
		duelistDefaults.setProperty(PROP_ALWAYS_UPGRADE, "FALSE");
		duelistDefaults.setProperty(PROP_NEVER_UPGRADE, "FALSE");
		duelistDefaults.setProperty(PROP_BASE_GAME_CARDS, "FALSE");
		duelistDefaults.setProperty(PROP_WISEMAN, "FALSE");
		duelistDefaults.setProperty(PROP_FORCE_PUZZLE, "FALSE");
		duelistDefaults.setProperty(PROP_ALLOW_BOOSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_ALWAYS_BOOSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_REMOVE_CARD_REWARDS, "TRUE");
		duelistDefaults.setProperty(PROP_SMALL_BASIC, "FALSE");
		duelistDefaults.setProperty(PROP_DUELIST_MONSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_DUELIST_CURSES, "TRUE");
		duelistDefaults.setProperty(PROP_CARD_REWARD_RELIC, "FALSE");
		duelistDefaults.setProperty(PROP_BOOSTER_REWARD_RELIC, "FALSE");
		duelistDefaults.setProperty(PROP_SHOP_DUPE_RELIC, "FALSE");
		duelistDefaults.setProperty(PROP_ADD_ORB_POTIONS, "TRUE");
		duelistDefaults.setProperty(PROP_ADD_ACT_FOUR, "TRUE");
		duelistDefaults.setProperty(PROP_PLAY_KAIBA, "FALSE");
		duelistDefaults.setProperty(PROP_MONSTER_IS_KAIBA, "TRUE");
		duelistDefaults.setProperty(PROP_LAST_CARD_POOL, "");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_A, "");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_B, "");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_C, "");
		duelistDefaults.setProperty(PROP_ALLOW_CARD_POOL_RELICS, "TRUE");
		duelistDefaults.setProperty(PROP_MONSTERS_RUN, "");
		duelistDefaults.setProperty(PROP_SPELLS_RUN, "");
		duelistDefaults.setProperty(PROP_TRAPS_RUN, "");
		duelistDefaults.setProperty("allowDuelistEvents", "TRUE");
		duelistDefaults.setProperty("playingChallenge", "FALSE");
		duelistDefaults.setProperty("currentChallengeLevel", "0");
		duelistDefaults.setProperty("birthdayMonth", "1");
		duelistDefaults.setProperty("birthdayDay", "1");
		duelistDefaults.setProperty("neverChangedBirthday", "TRUE");
		duelistDefaults.setProperty("fullCardPool", "~");
		duelistDefaults.setProperty("entombed", "");
		duelistDefaults.setProperty("quicktimeEventsAllowed", "FALSE");
		duelistDefaults.setProperty("entombedCustomCardProperites", "");
		duelistDefaults.setProperty("corpsesEntombed", "0");
		duelistDefaults.setProperty("allowLocaleUpload", "TRUE");
		duelistDefaults.setProperty("duelistScore", "1");
		duelistDefaults.setProperty("explosiveDmgLow", "2");
		duelistDefaults.setProperty("explosiveDmgHigh", "6");
		duelistDefaults.setProperty("souls", "0");
		duelistDefaults.setProperty("startSouls", "3");

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
		monsterTypes.add(Tags.TOON_POOL);	typeCardMap_ID.put(Tags.TOON_POOL, makeID("ToonTypeCard"));					typeCardMap_IMG.put(Tags.TOON_POOL, makePath(Strings.TOON_GOBLIN_ATTACK));
		monsterTypes.add(Tags.ZOMBIE);		typeCardMap_ID.put(Tags.ZOMBIE, makeID("ZombieTypeCard"));				typeCardMap_IMG.put(Tags.ZOMBIE, makePath(Strings.ARMORED_ZOMBIE));	
		monsterTypes.add(Tags.WARRIOR);		typeCardMap_ID.put(Tags.WARRIOR, makeID("WarriorTypeCard"));			typeCardMap_IMG.put(Tags.WARRIOR, makeCardPath("HardArmor.png"));
		monsterTypes.add(Tags.ROCK);		typeCardMap_ID.put(Tags.ROCK, makeID("RockTypeCard"));					typeCardMap_IMG.put(Tags.ROCK, makeCardPath("Giant_Soldier.png"));
		monsterTypes.add(Tags.WYRM);		typeCardMap_ID.put(Tags.WYRM, makeID("WyrmTypeCard"));					typeCardMap_IMG.put(Tags.WYRM, makeCardPath("Bixi.png"));
		monsterTypes.add(Tags.DINOSAUR);	typeCardMap_ID.put(Tags.DINOSAUR, makeID("DinosaurTypeCard"));			typeCardMap_IMG.put(Tags.DINOSAUR, makeCardPath("SauropodBrachion.png"));
		
											typeCardMap_ID.put(Tags.ROSE, makeID("RoseTypeCard"));					typeCardMap_IMG.put(Tags.ROSE, makeCardPath("RevivalRose.png"));	
											typeCardMap_ID.put(Tags.GIANT, makeID("GiantTypeCard"));				typeCardMap_IMG.put(Tags.GIANT, makeCardPath("EarthGiant.png"));	
											typeCardMap_ID.put(Tags.ARCANE, makeID("ArcaneTypeCard"));				typeCardMap_IMG.put(Tags.ARCANE, makeCardPath("AmuletAmbition.png"));	
											typeCardMap_ID.put(Tags.MAGNET, makeID("MagnetTypeCard"));				typeCardMap_IMG.put(Tags.MAGNET, makeCardPath("Gamma_Magnet.png"));
											typeCardMap_ID.put(Tags.MEGATYPED, makeID("MegatypeTypeCard"));			typeCardMap_IMG.put(Tags.MEGATYPED, makeCardPath("Eva.png"));											
											typeCardMap_ID.put(Tags.MONSTER, makeID("MonsterTypeCard"));			typeCardMap_IMG.put(Tags.MONSTER, makeCardPath("Giant_Soldier.png"));
											typeCardMap_ID.put(Tags.SPELL, makeID("SpellTypeCard"));				typeCardMap_IMG.put(Tags.SPELL, makeCardPath("Red_Medicine.png"));
											typeCardMap_ID.put(Tags.TRAP, makeID("TrapTypeCard"));					typeCardMap_IMG.put(Tags.TRAP, makeCardPath("Castle_Walls.png"));
											typeCardMap_ID.put(Tags.OJAMA, makeID("OjamaTypeCard"));				typeCardMap_IMG.put(Tags.OJAMA, makeCardPath("OjamaEmperor.png"));

		// Setup map to find which tribute synergy function to run based on a monster card's current tags
		// Map simply holds each monster type cardtag with an integer value to use with a switch statement
		// Integer values should be in the same order as monster types are added to the array above, 0-11
		int counter = 0; for (CardTags t : monsterTypes) { monsterTypeTributeSynergyFunctionMap.put(t, counter); counter++; }
		
		cardSets.add("Deck + Basic (Default)"); 
		cardSets.add("Deck Only");
		cardSets.add("Basic Only");
		cardSets.add("Deck + Basic + 1 Random Deck");
		cardSets.add("Deck + 1 Random Deck");
		cardSets.add("Basic + 1 Random Deck");
		cardSets.add("Basic + Deck + 2 Random Decks");
		cardSets.add("2 Random Decks");
		cardSets.add("Deck + 2 Random Decks");
		cardSets.add("Always ALL Cards");

		int save = 0;
		StarterDeck regularDeck = new StarterDeck(Tags.STANDARD_DECK, save, "Standard Deck"); starterDeckList.add(regularDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck dragDeck = new StarterDeck(Tags.DRAGON_DECK, save, "Dragon Deck"); starterDeckList.add(dragDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck natDeck = new StarterDeck(Tags.NATURIA_DECK, save, "Naturia Deck"); starterDeckList.add(natDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck spellcDeck = new StarterDeck(Tags.SPELLCASTER_DECK, save, "Spellcaster Deck"); starterDeckList.add(spellcDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck toonDeck = new StarterDeck(Tags.TOON_DECK, save, "Toon Deck"); starterDeckList.add(toonDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck zombieDeck = new StarterDeck(Tags.ZOMBIE_DECK, save, "Zombie Deck"); starterDeckList.add(zombieDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck aquaDeck = new StarterDeck(Tags.AQUA_DECK, save, "Aqua Deck"); starterDeckList.add(aquaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck fiendDeck = new StarterDeck(Tags.FIEND_DECK, save, "Fiend Deck"); starterDeckList.add(fiendDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck machineDeck = new StarterDeck(Tags.MACHINE_DECK, save, "Machine Deck"); starterDeckList.add(machineDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck magnetDeck = new StarterDeck(Tags.WARRIOR_DECK,  save, "Warrior Deck"); starterDeckList.add(magnetDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck insectDeck = new StarterDeck(Tags.INSECT_DECK,  save, "Insect Deck"); starterDeckList.add(insectDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck plantDeck = new StarterDeck(Tags.PLANT_DECK, save, "Plant Deck"); starterDeckList.add(plantDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck predaplantDeck = new StarterDeck(Tags.PREDAPLANT_DECK, save, "Predaplant Deck"); starterDeckList.add(predaplantDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck megatypeDeck = new StarterDeck(Tags.MEGATYPE_DECK, save, "Megatype Deck"); starterDeckList.add(megatypeDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck incDeck = new StarterDeck(Tags.INCREMENT_DECK, save, "Increment Deck"); starterDeckList.add(incDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck creaDeck = new StarterDeck(Tags.CREATOR_DECK, save, "Creator Deck"); starterDeckList.add(creaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ojDeck = new StarterDeck(Tags.OJAMA_DECK, save, "Ojama Deck"); starterDeckList.add(ojDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck exodiaDeck = new StarterDeck(Tags.EXODIA_DECK, save, "Exodia Deck"); starterDeckList.add(exodiaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck giantsDeck = new StarterDeck(Tags.GIANT_DECK,  save, "Giant Deck"); starterDeckList.add(giantsDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck a1Deck = new StarterDeck(Tags.ASCENDED_ONE_DECK, save, "Ascended I"); starterDeckList.add(a1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck a2Deck = new StarterDeck(Tags.ASCENDED_TWO_DECK,  save, "Ascended II"); starterDeckList.add(a2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck a3Deck = new StarterDeck(Tags.ASCENDED_THREE_DECK,  save, "Ascended III"); starterDeckList.add(a3Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck p1Deck = new StarterDeck(Tags.PHARAOH_ONE_DECK,  save, "Pharaoh I"); starterDeckList.add(p1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck p2Deck = new StarterDeck(Tags.PHARAOH_TWO_DECK, save, "Pharaoh II"); starterDeckList.add(p2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck p3Deck = new StarterDeck(Tags.PHARAOH_THREE_DECK,  save, "Pharaoh III"); starterDeckList.add(p3Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck p4Deck = new StarterDeck(Tags.PHARAOH_FOUR_DECK, save, "Pharaoh IV"); starterDeckList.add(p4Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck p5Deck = new StarterDeck(Tags.PHARAOH_FIVE_DECK,  save, "Pharaoh V"); starterDeckList.add(p5Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran1Deck = new StarterDeck(Tags.RANDOM_DECK_SMALL,  save, "Random Deck (Small)"); starterDeckList.add(ran1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran2Deck = new StarterDeck(Tags.RANDOM_DECK_BIG,  save, "Random Deck (Big)"); starterDeckList.add(ran2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck upgradeRanDeck = new StarterDeck(Tags.RANDOM_DECK_UPGRADE,  save, "Upgrade Deck"); starterDeckList.add(upgradeRanDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck metRanDeck = new StarterDeck(Tags.METRONOME_DECK, save, "Metronome Deck"); starterDeckList.add(metRanDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save));
		
		for (StarterDeck d : starterDeckList) { starterDeckNamesMap.put(d.getSimpleName(), d); }
		for (int i = 0; i < starterDeckList.size(); i++)
		{
			if (i > 0 && i < 14)
			{
				switch (i)
				{
				case 1:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.DRAGON);
					break;
				case 2:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.INSECT);
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.PLANT);
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.PREDAPLANT);
					break;
				case 3:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.SPELLCASTER);
					break;
				case 4:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.TOON_POOL);
					break;
				case 5:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.ZOMBIE);
					break;
				case 6:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.AQUA);
					break;
				case 7:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.FIEND);
					break;
				case 8:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.MACHINE);
					break;
				case 9:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.SUPERHEAVY);
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.WARRIOR);
					break;
				case 10:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.INSECT);
					break;
				case 11:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.PLANT);
					break;
				case 12:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.PREDAPLANT);
					break;
				case 13:
					starterDeckList.get(i).tagsThatMatchCards = new ArrayList<>();
					starterDeckList.get(i).tagsThatMatchCards.add(Tags.MEGATYPED);
					break;
				}
			}
		}
		//DECKS = starterDeckList.size();
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
            gotWisemanHaunted = config.getBool(PROP_WISEMAN);
            forcePuzzleSummons = config.getBool(PROP_FORCE_PUZZLE);
            allowBoosters = config.getBool(PROP_ALLOW_BOOSTERS);
            alwaysBoosters = config.getBool(PROP_ALWAYS_BOOSTERS);
            removeCardRewards = config.getBool(PROP_REMOVE_CARD_REWARDS);
            smallBasicSet = config.getBool(PROP_SMALL_BASIC);
            duelistMonsters = config.getBool(PROP_DUELIST_MONSTERS);
            duelistCurses = config.getBool(PROP_DUELIST_CURSES);
            hasCardRewardRelic = config.getBool(PROP_CARD_REWARD_RELIC);
            hasBoosterRewardRelic = config.getBool(PROP_BOOSTER_REWARD_RELIC);
            hasShopDupeRelic = config.getBool(PROP_SHOP_DUPE_RELIC);
            addOrbPotions = config.getBool(PROP_ADD_ORB_POTIONS);
            quicktimeEventsAllowed = config.getBool("quicktimeEventsAllowed");  
            playAsKaiba = config.getBool(PROP_PLAY_KAIBA);
            monsterIsKaiba = config.getBool(PROP_MONSTER_IS_KAIBA);
            lastCardPoolFullList = config.getString(PROP_LAST_CARD_POOL);
            saveSlotA = config.getString(PROP_SAVE_SLOT_A);
            saveSlotB = config.getString(PROP_SAVE_SLOT_B);
            saveSlotC = config.getString(PROP_SAVE_SLOT_C);
            loadedUniqueMonstersThisRunList = config.getString(PROP_MONSTERS_RUN);
            loadedSpellsThisRunList = config.getString(PROP_SPELLS_RUN);
            loadedTrapsThisRunList = config.getString(PROP_TRAPS_RUN);
            entombedCardsThisRunList = config.getString("entombed");
            allowCardPoolRelics = config.getBool(PROP_ALLOW_CARD_POOL_RELICS);
            challengeLevel20 = config.getBool("challengeLevel20");
            defaultMaxSummons = config.getInt("defaultMaxSummons");
            allowDuelistEvents = config.getBool("allowDuelistEvents");
            birthdayMonth = config.getInt("birthdayMonth");
            birthdayDay = config.getInt("birthdayDay");
            neverChangedBirthday = config.getBool("neverChangedBirthday");
			explosiveDmgLow = config.getInt("explosiveDmgLow");
			explosiveDmgHigh = config.getInt("explosiveDmgHigh");
			currentZombieSouls = config.getInt("souls");
			defaultStartZombieSouls = config.getInt("startSouls");
            entombedCustomCardProperites = config.getString("entombedCustomCardProperites");
            corpsesEntombed = config.getInt("corpsesEntombed");
        	playingChallenge = config.getBool("playingChallenge");
        	challengeLevel = config.getInt("currentChallengeLevel");
        	allowLocaleUpload = config.getBool("allowLocaleUpload");

        	duelistScore = config.getInt("duelistScore");
        	int originalDuelistScore = duelistScore;
			int currentTotalScore = UnlockTracker.unlockProgress.getInteger("THE_DUELISTTotalScore");
			int scoreToSet = currentTotalScore > 0 ? currentTotalScore : duelistScore;
			scoreToSet = Math.max(duelistScore, scoreToSet);
			duelistScore = scoreToSet;
			if (duelistScore >= originalDuelistScore) { config.setInt("duelistScore", duelistScore);  config.save(); }

			Util.log("currentScore as according to current score tracking: " + currentTotalScore);
			Util.log("current Duelist Score: " + duelistScore);

        	BonusDeckUnlockHelper.loadProperties();
        } catch (Exception e) { e.printStackTrace(); }
	}


	public static void initialize() {
		logger.info("Initializing Duelist Mod");
		new DuelistMod();
		incrementDiscardSubscribers = new ArrayList<>();
		logger.info("Duelist Mod Initialized");
	}

	// ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

	

	// =============== LOAD THE CHARACTER =================

	@Override
	public void receiveEditCharacters() 
	{
		// Yugi Moto
		resetDuelist();
		duelistChar = new TheDuelist("the Duelist", TheDuelistEnum.THE_DUELIST);
		BaseMod.addCharacter(duelistChar,makePath(Strings.THE_DEFAULT_BUTTON), makePath(Strings.THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_DUELIST);
		receiveEditPotions();
	}

	// =============== /LOAD THE CHARACTER/ =================


	// =============== POST-INITIALIZE =================


	@Override
	public void receivePostInitialize() 
	{	
		// Mod Options
		Util.halloweenCheck();
		Texture badgeTexture = new Texture(makePath(Strings.BADGE_IMAGE));
		Config_UI_String = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");
		setupExtraConfigStrings();
		settingsPanel = new ModPanel();
		configPanelSetup();
		BaseMod.registerModBadge(badgeTexture, modName, modAuthor, modDescription, settingsPanel);
		combatIconViewer = new CombatIconViewer();
		bonusUnlockHelper = new BonusDeckUnlockHelper();
		receiveEditSounds();
		
		// Animated Cards
		if (isGifTheSpire) { new GifSpireHelper(); }
		
		// Events													
		BaseMod.addEvent(MillenniumItems.ID, MillenniumItems.class);
		BaseMod.addEvent(AknamkanonTomb.ID, AknamkanonTomb.class);
		BaseMod.addEvent(EgyptVillage.ID, EgyptVillage.class);
		BaseMod.addEvent(TombNameless.ID, TombNameless.class);	
		BaseMod.addEvent(TombNamelessPuzzle.ID, TombNamelessPuzzle.class);	
		BaseMod.addEvent(BattleCity.ID, BattleCity.class);	
		BaseMod.addEvent(CardTrader.ID, CardTrader.class);
		BaseMod.addEvent(RelicDuplicator.ID, RelicDuplicator.class);
		BaseMod.addEvent(new AddEventParams.Builder(VisitFromAnubis.ID, VisitFromAnubis.class)
				.eventType(EventUtils.EventType.ONE_TIME)
				.dungeonID(TheCity.ID)
				.create());
		
		// Monsters
		BaseMod.addMonster(DuelistEnemy.ID, "Seto Kaiba", DuelistEnemy::new);
		BaseMod.addMonster(DuelistEnemy.ID_YUGI, "Yugi Muto", DuelistEnemy::new);
		BaseMod.addMonster(SuperKaiba.ID, "Seto Kaiba (Event)", SuperKaiba::new);
		BaseMod.addMonster(SuperYugi.ID, "Yugi Muto (Event)", SuperYugi::new);

		// Encounters
		if (DuelistMod.duelistMonsters)
		{
			BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo(DuelistEnemy.ID, 4.0F));
		}
		
		// Rewards
		BaseMod.registerCustomReward(RewardItemTypeEnumPatch.DUELIST_PACK, (rewardSave) -> BoosterHelper.getPackFromSave(rewardSave.id), (customReward) -> new RewardSave(customReward.type.toString(), ((BoosterPack)customReward).packName));

		// Top Panel
		/*topPanelChallengeIcon = new ChallengeIcon();
		BaseMod.addTopPanelItem(topPanelChallengeIcon);*/

		// Custom Powers (for basemod console)
		Util.registerCustomPowers();

		// Upload any untracked mod info to metrics server (card/relic/potion/creature/keyword data)
		//if (DuelistMod.modMode == Mode.DEV) {
			ExportUploader.uploadInfoJSON();
		//}
		cardTierScores = MetricsHelper.getTierScores();
	}
	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		ArrayList<AbstractPotion> pots = new ArrayList<>();
		pots.add(new MillenniumElixir());
		pots.add(new MegaupgradePotion());
		pots.add(new DragonfirePotion());
		pots.add(new SealedPack());
		pots.add(new SealedPackB());
		pots.add(new SealedPackC());
		pots.add(new SealedPackD());
		pots.add(new SealedPackE());
		pots.add(new TributeBottle());
		pots.add(new BigTributeBottle());		
		pots.add(new DestructPotionPot());
		pots.add(new DestructPotionPotB());	
		pots.add(new BabyPotion());
		pots.add(new TokenPotion());
		pots.add(new TokenPotionB());
		pots.add(new DragonSoulPotion());
		pots.add(new BottledKuriboh());
		pots.add(new SummonFuryPotion());
		pots.add(new SummonArmorPotion());
		pots.add(new DebuffFuryPotion());
		pots.add(new DebuffArmorPotion());
		pots.add(new DuelistPosionPotion());
		pots.add(new DebuffPosionPotion());
		pots.add(new ReducerPotion());
		pots.add(new BarricadePotion());
		pots.add(new MayhemPotion());
		pots.add(new BombBottle());
		pots.add(new FluxPotion());
		pots.add(new FreezingPotion());
		pots.add(new IcePotion());
		pots.add(new GreasePot());
		pots.add(new GrimePotion());
		pots.add(new BurningPotion());
		pots.add(new BurningPotionB());
		pots.add(new GiftPotion());
		pots.add(new StunPotion());
		pots.add(new SolderPotion());
		pots.add(new SolderPotionB());
		pots.add(new ElectricPotion());
		pots.add(new ElectricBrew());
		pots.add(new SteelBrew());
		pots.add(new DetonatePotion());
		pots.add(new BonePotion());
		pots.add(new SoulPotion());
		pots.add(new FusionPotion());
		pots.add(new VampirePotion());
		pots.add(new VampireVial());
		pots.add(new Soulbrew());
		pots.add(new Bonebrew());
		pots.add(new MagicalCauldron());	
		for (AbstractPotion p : pots){ duelistPotionMap.put(p.ID, p); allDuelistPotions.add(p);BaseMod.addPotion(p.getClass(), Colors.WHITE, Colors.WHITE, Colors.WHITE, p.ID, TheDuelistEnum.THE_DUELIST); }
		pots.clear();

		pots.add(new BigOrbBottle());
		pots.add(new CoolBottle());
		pots.add(new DragonOrbBottle());
		pots.add(new DragonOrbPlusBottle());
		pots.add(new EarthBottle());
		pots.add(new ExtraOrbsBottle());
		pots.add(new FireBottle());
		pots.add(new FlamingBottle());
		pots.add(new GadgetBottle());
		pots.add(new LavaBottle());
		pots.add(new MetalBottle());
		pots.add(new MudBottle());
		pots.add(new OrbBottle());
		pots.add(new SummonerBottle());
		pots.add(new VoidBottle());
		pots.add(new WaterBottle());
		pots.add(new WhiteBottle());
		for (AbstractPotion p : pots){ duelistPotionMap.put(p.ID, p); orbPotionIDs.add(p.ID); allDuelistPotions.add(p);BaseMod.addPotion(p.getClass(), Colors.WHITE, Colors.WHITE, Colors.WHITE, p.ID, TheDuelistEnum.THE_DUELIST); }
		pots.clear();
	}

	// ================ /ADD POTIONS/ ===================


	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		ArrayList<AbstractRelic> allRelics = new ArrayList<>();
		
		// Duelist Relics
		allRelics.add(new AeroRelic());
		allRelics.add(new AknamkanonsEssence());
		allRelics.add(new AquaRelic());
		allRelics.add(new AquaRelicB());
		allRelics.add(new BlessingAnubis());
		allRelics.add(new BoosterBetterBoostersRelic());
		allRelics.add(new BoosterExtraAllRaresRelic());
		allRelics.add(new BoosterPackHealer());
		allRelics.add(new BoosterPackMonsterEgg());
		allRelics.add(new BoosterPackSpellEgg());
		allRelics.add(new BoosterPackTrapEgg());
		allRelics.add(new CardPoolAddRelic());
		allRelics.add(new CardPoolMinusRelic());
		allRelics.add(new CardPoolOptionsRelic());
		allRelics.add(new CardPoolRelic());
		allRelics.add(new CardPoolBasicRelic());
		allRelics.add(new BoosterPackPoolRelic());
		allRelics.add(new CardPoolSaveRelic());
		allRelics.add(new CardRewardRelicA());
		allRelics.add(new CardRewardRelicB());
		allRelics.add(new CardRewardRelicC());
		allRelics.add(new CardRewardRelicD());
		allRelics.add(new CardRewardRelicE());
		allRelics.add(new CardRewardRelicF());
		allRelics.add(new CardRewardRelicG());
		allRelics.add(new CardRewardRelicH());
		allRelics.add(new CardRewardRelicI());
		allRelics.add(new TokenUpgradeRelic());
		allRelics.add(new CursedHealer());
		allRelics.add(new DragonBurnRelic());
		allRelics.add(new DragonRelic());
		allRelics.add(new DragonRelicB());
		allRelics.add(new DragonRelicC());
		allRelics.add(new DuelistCoin());
		allRelics.add(new MillenniumPeriapt());
		allRelics.add(new DuelistPrismaticShard());
		allRelics.add(new DuelistOrichalcum());
		allRelics.add(new DuelistLetterOpener());
		allRelics.add(new DuelistTeaSet());
		allRelics.add(new DuelistUrn());
		allRelics.add(new FatMaxHPRelic());		
		allRelics.add(new GamblerChip());
		allRelics.add(new GiftAnubis());
		allRelics.add(new GoldenScale());
		allRelics.add(new HauntedRelic());
		allRelics.add(new InsectRelic());
		allRelics.add(new InversionEvokeRelic());
		allRelics.add(new InversionRelic());
		allRelics.add(new KaibaToken());
		allRelics.add(new Leafblower());
		allRelics.add(new MachineToken());
		allRelics.add(new MachineOrb());
		allRelics.add(new Wirebundle());
		allRelics.add(new Fluxrod());
		allRelics.add(new TokenArmor());
		allRelics.add(new BrazeToken());
		allRelics.add(new RouletteWheel());
		allRelics.add(new EngineeringToken());
		allRelics.add(new Bombchain());
		allRelics.add(new LoadedDice());
		allRelics.add(new TokenfestPendant());
		allRelics.add(new MagnetRelic());
		allRelics.add(new MarkExxod());
		allRelics.add(new MarkOfNature());
		allRelics.add(new MerchantNecklace());
		allRelics.add(new MerchantPendant());
		allRelics.add(new MerchantRugbox());
		allRelics.add(new MerchantSword());
		allRelics.add(new MerchantTalisman());
		allRelics.add(new MetronomeRelicA());
		allRelics.add(new MetronomeRelicB());
		allRelics.add(new MetronomeRelicC());
		allRelics.add(new MetronomeRelicD());
		allRelics.add(new MillenniumCoin());
		allRelics.add(new MillenniumEye());
		allRelics.add(new MillenniumKey());
		allRelics.add(new MillenniumNecklace());
		allRelics.add(new MillenniumPuzzle());
		allRelics.add(new MillenniumRing());
		allRelics.add(new MillenniumRod());
		allRelics.add(new MillenniumScale());
		allRelics.add(new MillenniumStone());
		allRelics.add(new MillenniumSymbol());
		allRelics.add(new MillenniumToken());
		allRelics.add(new MonsterEggRelic());
		allRelics.add(new MutatorToken());
		allRelics.add(new Monsterbox());
		allRelics.add(new NamelessGreedRelic());
		allRelics.add(new NamelessHungerRelic());
		allRelics.add(new NamelessPowerRelicA());
		allRelics.add(new NamelessPowerRelicB());
		allRelics.add(new NamelessWarRelicA());
		allRelics.add(new NamelessWarRelicB());
		allRelics.add(new NamelessWarRelicC());
		allRelics.add(new NatureOrb());
		allRelics.add(new NatureRelic());
		allRelics.add(new NaturiaRelic());
		allRelics.add(new OrbCardRelic());
		allRelics.add(new ResummonBranch());
		allRelics.add(new ShopToken());
		allRelics.add(new SpellMaxHPRelic());
		allRelics.add(new SpellcasterOrb());
		allRelics.add(new SpellcasterStone());
		allRelics.add(new SpellcasterToken());
		allRelics.add(new Spellheart());
		allRelics.add(new StoneExxod());
		allRelics.add(new SummonAnchor());
		allRelics.add(new SummonAnchorRare());
		allRelics.add(new ToonRelic());
		allRelics.add(new TrapVortex());
		allRelics.add(new TributeEggRelic());
		allRelics.add(new WhiteBowlRelic());
		allRelics.add(new YugiMirror());
		allRelics.add(new ZombieRelic());
		allRelics.add(new ZombieResummonBuffRelic());
		allRelics.add(new ChallengePuzzle());
		allRelics.add(new AkhenamkhanenSceptre());
		allRelics.add(new MillenniumPrayerbook());
		allRelics.add(new PrayerPageA());
		allRelics.add(new PrayerPageB());
		allRelics.add(new PrayerPageC());
		allRelics.add(new PrayerPageD());
		allRelics.add(new PrayerPageE());
		allRelics.add(new MillenniumArmor());
		allRelics.add(new ArmorPlateA());
		allRelics.add(new ArmorPlateB());
		allRelics.add(new ArmorPlateC());
		allRelics.add(new ArmorPlateD());
		allRelics.add(new ArmorPlateE());
		allRelics.add(new ZoneToken());
		allRelics.add(new SolderToken());
		allRelics.add(new ElectricToken());
		allRelics.add(new ElectricKey());
		allRelics.add(new ElectricBurst());
		allRelics.add(new DuelistSnakeEye());		
		allRelics.add(new SailingToken());
		allRelics.add(new Flowstate());
		allRelics.add(new NileToken());
		allRelics.add(new FlowToken());
		allRelics.add(new WavemastersBlessing());
		allRelics.add(new GoldenSail());
		allRelics.add(new Splashbox());
		allRelics.add(new CoralToken());		
		allRelics.add(new ResummonerFury());		
		allRelics.add(new ResummonerBane());		
		allRelics.add(new ResummonerMight());		
		allRelics.add(new VampiricPendant());		
		allRelics.add(new FusionToken());		
		allRelics.add(new NuclearDecay());
		allRelics.add(new GhostToken());
		allRelics.add(new GraveToken());
		allRelics.add(new PointPass());
		//allRelics.add(new RandomTributeMonsterRelic());
		//allRelics.add(new Spellbox());
		//allRelics.add(new Trapbox());
		
		// Base Game Shared Relics
		allRelics.add(new Brimstone());
		allRelics.add(new ChampionsBelt());
		allRelics.add(new CharonsAshes());
		allRelics.add(new GoldPlatedCables());
		allRelics.add(new HoveringKite());
		allRelics.add(new Inserter());
		allRelics.add(new MagicFlower());
		allRelics.add(new MarkOfPain());
		allRelics.add(new PaperCrane());
		allRelics.add(new PaperFrog());
		allRelics.add(new RedSkull());
		allRelics.add(new RunicCapacitor());
		allRelics.add(new RunicCube());
		allRelics.add(new SelfFormingClay());
		allRelics.add(new SneckoSkull());
		allRelics.add(new Tingsha());
		allRelics.add(new ToughBandages());
		allRelics.add(new TwistedFunnel());
		allRelics.add(new Melange());
		//allRelics.add(new NuclearBattery());
		//allRelics.add(new DataDisk());
		//allRelics.add(new SymbioticVirus());
		//allRelics.add(new EmotionChip());
		//allRelics.add(new TheSpecimen());
		//allRelics.add(new WristBlade());
		for (AbstractRelic r : allRelics) { BaseMod.addRelicToCustomPool(r, AbstractCardEnum.DUELIST); }	
		Util.unlockAllRelics(allRelics);	
		Util.setupDuelistTombRelics();
	
		// Crossover Non-Shared Relics (now shared with The Duelist)
		if (isReplay) { ReplayHelper.extraRelics(); }	
		if (isHubris) { HubrisHelper.extraRelics(); }	
		if (isDisciple) { DiscipleHelper.extraRelics(); }		
		if (isConspire) { ConspireHelper.extraRelics(); } 
		if (isClockwork) { ClockworkHelper.extraRelics(); }
		if (isGatherer) { GathererHelper.extraRelics(); } 
		if (isInfiniteSpire) { InfiniteSpireHelper.extraRelics(); }
		if (isAnimator) { AnimatorHelper.extraRelics(); }
	}

	// ================ /ADD RELICS/ ===================

	// ================ ADD CARDS ===================
	@Override
	public void receiveEditCards() 
	{
		// ================ VARIABLES ===================
		BaseMod.addDynamicVariable(new TributeMagicNumber());
		BaseMod.addDynamicVariable(new SummonMagicNumber());
		BaseMod.addDynamicVariable(new SecondMagicNumber());
		BaseMod.addDynamicVariable(new ThirdMagicNumber());
		BaseMod.addDynamicVariable(new EntombNum());
		BaseMod.addDynamicVariable(new IncrementNum());
		// ================ ORB CARDS ===================
		DuelistCardLibrary.setupOrbCards();
		// ================ PRIVATE LIBRARY SETUP ===================
		DuelistCardLibrary.setupMyCards();
		lastCardPlayed = new CancelCard();
		secondLastCardPlayed = new CancelCard();
		lastPlantPlayed = new CancelCard();
		secondLastPlantPlayed = new CancelCard();
		lastGhostrickPlayed = new CancelCard();
		secondLastGhostrickPlayed = new CancelCard();
		lastCardDrawn = new CancelCard();
		secondLastCardDrawn = new CancelCard();
		// ================ STARTER DECKS ===================
		StarterDeckSetup.initStartDeckArrays();
		// ================ SUMMON MAP ===================
		DuelistCardLibrary.fillSummonMap(myCards);
		// ================ COMPENDIUM SETUP ===================
		DuelistCardLibrary.addCardsToGame();
		// ================ COLORED CARDS ===================
	}
	// ================ /ADD CARDS/ ===================



	// ================ LOAD THE TEXT ===================

	@Override
	public void receiveEditStrings() {
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
		
		// MonsterStrings
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-Monster-Strings.json");

        // Custom Tips
        BaseMod.loadCustomStringsFile(TutorialStrings.class, "duelistModResources/localization/" + loc + "/DuelistMod-Tip-Strings.json");
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
	            	Util.log("Adding keyword: " + keyword.PROPER_NAME + " | " + keyword.NAMES[0]);
	                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            	}
            }
        }
	}

	// ================ /LOAD THE KEYWORDS/ ===================    
	
	public void receiveEditSounds() 
	{
        addSound("theDuelist:TimeToDuel", DuelistMod.makeCharAudioPath("CharSelect.ogg"));
        addSound("theDuelist:TimeToDuelB", DuelistMod.makeCharAudioPath("CharSelectB.ogg"));
        addSound("theDuelist:AirChannel", DuelistMod.makeCharAudioPath("AirChannel.ogg"));
        addSound("theDuelist:GateChannel", DuelistMod.makeCharAudioPath("GateChannel.ogg"));
        addSound("theDuelist:MudChannel", DuelistMod.makeCharAudioPath("MudChannel.ogg"));
        addSound("theDuelist:MetalChannel", DuelistMod.makeCharAudioPath("MetalChannel.ogg"));
        addSound("theDuelist:FireChannel", DuelistMod.makeCharAudioPath("FireChannel.ogg"));
        addSound("theDuelist:ResummonWhoosh", DuelistMod.makeCharAudioPath("ResummonWhoosh.ogg"));
		addSound("theDuelist:ShadowChannel", DuelistMod.makeCharAudioPath("ShadowChannel.ogg"));
    }

    private static void addSound(String id, String path) {
        @SuppressWarnings("unchecked")
        HashMap<String,Sfx> map = (HashMap<String,Sfx>) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put(id, new Sfx(path, false));
    }

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return "theDuelist:" + idText;
	}

	public static boolean isToken(AbstractCard c)
	{
		return c.hasTag(Tags.TOKEN);
	}

	public static boolean isMonster(AbstractCard c)
	{
		return c.hasTag(Tags.MONSTER);
	}

	public static boolean isSpell(AbstractCard c)
	{
		return c.hasTag(Tags.SPELL);
	}

	public static boolean isTrap(AbstractCard c)
	{
		return c.hasTag(Tags.TRAP);
	}
	
	public static boolean isArchetype(AbstractCard c)
	{
		return c.hasTag(Tags.ARCHETYPE);
	}
	
	public static boolean isOrbCard(AbstractCard c)
	{
		return c.hasTag(Tags.ORB_CARD);
	}
	
	public static boolean isBooster(AbstractCard c)
	{
		return c.hasTag(Tags.BOOSTER);
	}

	public static int getChallengeDiffIndex()
	{
		if (Util.isCustomModActive("challengethespire:Bronze Difficulty")) { return 1; }		
		else if (Util.isCustomModActive("challengethespire:Silver Difficulty")) { return 2; }		
		else if (Util.isCustomModActive("challengethespire:Gold Difficulty")) { return 3; }	
		else if (Util.isCustomModActive("challengethespire:Platinum Difficulty")) { return 4; }
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
    public void receivePostUpdate() {
        if (speedScreen != null && speedScreen.isDone) {
            speedScreen = null;
        }
    }
	
	@Override
	public void receivePotionGet(AbstractPotion arg0) 
	{
		if (AbstractDungeon.player.hasRelic(NamelessHungerRelic.ID))
		{
			AbstractDungeon.player.getRelic(NamelessHungerRelic.ID).flash();
			AbstractDungeon.player.increaseMaxHp(2, true);
		}
		
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPotionGetWhileInMasterDeck(); }}
	}
	
	public static void onTurnStart()
	{
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
    	{
    		if (c.hasTag(Tags.IMMORTAL))
    		{
    			DuelistCard.gainEnergy(1);
    			break;
    		}
    	}
		
		playedVampireThisTurn = false;
		if (Util.canRevive(1, false))
		{
			DuelistCard.reviveStatic(1);
		}
	}
	
	@Override
	public void receiveOnBattleStart(AbstractRoom arg0) 
	{
		//DuelistTipHelper.showTip("TEST_TIP", "Adam is a cool guy", "This is a test please", DuelistTipHelper.DuelistTipType.SHUFFLE);
		if (replacedCardPool) { 
			replacedCardPool = false;
			BoosterHelper.refreshPool();
			Util.log("Detected card pool changes from Card Pool Relics, refreshing booster pool to match new card pool");
		}
		Util.fillCardsPlayedThisRunLists();
		entombBattleStartHandler();
		Util.removeRelicFromPools(PrismaticShard.ID);
		Util.removeRelicFromPools(Courier.ID);
		TheDuelist.resummonPile.group.clear();
		firstCardInGraveThisCombat = new CancelCard();
		battleFusionMonster = new CancelCard();
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) { wasEliteCombat = true; }
		else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { wasBossCombat = true; }
		else { wasEliteCombat = false; wasBossCombat = false; }
		Util.handleBossResistNature(wasBossCombat);
		Util.handleEliteResistNature(wasEliteCombat);
		if (!wasBossCombat && !wasEliteCombat) { Util.handleHallwayResistNature(); }
		warriorTribEffectsTriggeredThisCombat = 0;
		warriorTribThisCombat = false;
		lastTurnHP = AbstractDungeon.player.currentHealth;
		secondLastTurnHP = lastTurnHP;
		overflowedThisTurn = false;
		overflowedLastTurn = false;
		bookEclipseThisCombat = false;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				((DuelistCard)c).startBattleReset();
			}
		}
		BuffHelper.resetBuffPool();
		lastMaxSummons = defaultMaxSummons;
		currentZombieSouls = defaultStartZombieSouls;
		if (Util.deckIs("Metronome Deck")) { currentZombieSouls = 999; }
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			pow.MAX_SUMMONS = defaultMaxSummons;
		}
		tokensThisCombat = 0;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		overflowsThisCombat = 0;
		poisonAppliedThisCombat = 0;
		zombiesResummonedThisCombat = 0;
		godsPlayedForBonus = 0;
		firstCardResummonedThisCombat = new CancelCard();
		firstMonsterResummonedThisCombat = new CancelCard();
		godsPlayedNames = new ArrayList<>();
		try 
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
            config.load();
            vampiresPlayed = config.getInt("vampiresPlayed");
            vendreadPlayed = config.getInt("vendreadPlayed");
            ghostrickPlayed = config.getInt("ghostrickPlayed");
            mayakashiPlayed = config.getInt("mayakashiPlayed");
            shiranuiPlayed = config.getInt("shiranuiPlayed");
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) 
	{
		Util.genesisDragonHelper();
		for (AbstractPotion p : AbstractDungeon.player.potions) { if (p instanceof DuelistPotion) { ((DuelistPotion)p).onEndOfBattle(); }}
		// Reset some settings
		lastCardResummoned = null;
		wasEliteCombat = false; 
		wasBossCombat = false;
		wyrmTribThisCombat = false;
		playedBug = false;
		playedSecondBug = false;
		playedSpider = false;
		playedSecondSpider = false;
		playedThirdSpider = false;
		warriorTribThisCombat = false;
		godsPlayedForBonus = 0;
		warriorTribEffectsTriggeredThisCombat = 0;
		godsPlayedNames = new ArrayList<>();
		spellsPlayedCombatNames = new ArrayList<>();
		skillsPlayedCombatNames = new ArrayList<>();
		monstersPlayedCombatNames = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		uniqueSkillsThisCombat = new ArrayList<>();
		metronomeResummonsThisCombat = new ArrayList<>();
		playedOneCardThisCombat = false;
		lastMaxSummons = defaultMaxSummons;
		currentZombieSouls = defaultStartZombieSouls;
		spellCombatCount = 0;
		trapCombatCount = 0;
		tokensThisCombat = 0;
		summonLastCombatCount = summonCombatCount;
		tributeLastCombatCount = tribCombatCount;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		
		// Spellcaster Puzzle Effect
		if (spellcasterDidChannel)
		{
			spellcasterRandomOrbsChanneled = 0;
			currentSpellcasterOrbChance = 25;
			spellcasterDidChannel = false;
		}
		else { spellcasterRandomOrbsChanneled++; }
		
		// Reset unlock level to 0 so the player score will continue to increase (otherwise, score stops going up after lvl 5)
		if (UnlockTracker.getUnlockLevel(TheDuelistEnum.THE_DUELIST) > 0)
		{ 
			UnlockTracker.unlockProgress.putInteger(TheDuelistEnum.THE_DUELIST.toString() + "UnlockLevel", 0);
			SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_COMBAT);
			SaveAndContinue.save(saveFile);
			Util.log("unlock level was greater than 0, reset to 0");
		}
		
		entombedCardsThisRunList += battleEntombedList;
		battleEntombedList = "";
		
		// Save settings
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setBool(PROP_MONSTER_IS_KAIBA, monsterIsKaiba);
			config.setInt("corpsesEntombed", corpsesEntombed);
			config.setString("entombed", entombedCardsThisRunList);
			config.setString("entombedCustomCardProperites", entombedCustomCardProperites);
			config.setInt("vampiresPlayed", vampiresPlayed);
			config.setInt("vendreadPlayed", vendreadPlayed);
			config.setInt("ghostrickPlayed", ghostrickPlayed);
			config.setInt("mayakashiPlayed", mayakashiPlayed);
			config.setInt("shiranuiPlayed", shiranuiPlayed);
			config.setString(DuelistMod.PROP_MONSTERS_RUN, loadedUniqueMonstersThisRunList);
			config.setString(DuelistMod.PROP_TRAPS_RUN, loadedTrapsThisRunList);
			config.setString(DuelistMod.PROP_SPELLS_RUN, loadedSpellsThisRunList);
			DuelistTipHelper.saveTips(config);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) 
	{
		if (power != null)
		{
			if (power.owner.equals(AbstractDungeon.player))
			{
				if (AbstractDungeon.player.hasRelic(CursedHealer.ID))
				{
					if (AbstractDungeon.cardRandomRng.random(1, 6) == 1)
					{
						DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, -1));
						AbstractDungeon.player.getRelic(CursedHealer.ID).flash();
					}
				}
				
				for (AbstractOrb o : AbstractDungeon.player.orbs)
	            {
	            	if (o instanceof DuelistOrb)
	            	{
	            		((DuelistOrb)o).onPowerApplied(power);
	            	}
	            }
				
				if (power instanceof FocusUpPower)
				{
					DuelistCard.applyPower(new FocusPower(power.owner, power.amount), power.owner);
				}
				
				if (power instanceof StrengthUpPower)
				{
					DuelistCard.applyPower(new StrengthPower(power.owner, power.amount), power.owner);
				}
				
				if (power instanceof OverworkedPower)
				{
					OverworkedPower pow = (OverworkedPower)power;
					int strGain = pow.strGain;
					DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, strGain));
				}
				
				if (power instanceof MegaconfusionPower)
				{
					MegaconfusionPower pow = (MegaconfusionPower)power;
					pow.statRescrambler();
				}

				if (power instanceof TombLooterPower)
				{
					if (AbstractDungeon.player.hasPower(TombLooterPower.POWER_ID))
					{
						TombLooterPower pow = (TombLooterPower)AbstractDungeon.player.getPower(TombLooterPower.POWER_ID);
						((TombLooterPower) power).goldGainedThisCombat = pow.goldGainedThisCombat;
						((TombLooterPower) power).goldLimit = pow.goldLimit;
					}
				}
				
				if (power instanceof StrengthPower && power.amount > 0)
				{
					if (!AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID) && AbstractDungeon.player.hasRelic(MetronomeRelicD.ID))
					{
						MetronomeRelicD relic = (MetronomeRelicD)AbstractDungeon.player.getRelic(MetronomeRelicD.ID);
						relic.addMetToHand();
					}
				}
				
				if (power instanceof DexterityPower)
				{
					if (power.amount > 0)
					{
						if (AbstractDungeon.player.stance instanceof DuelistStance)
						{
							DuelistStance stance = (DuelistStance) AbstractDungeon.player.stance;
							stance.onGainDex(power.amount);
						}
						
						for (AbstractOrb o : AbstractDungeon.player.orbs)
			            {
			            	if (o instanceof DuelistOrb)
			            	{
			            		((DuelistOrb)o).onGainDex(power.amount);
			            	}
			            }
					}
					
					for (AbstractPower pow : AbstractDungeon.player.powers)
					{
						if (pow instanceof DuelistPower)
						{
							((DuelistPower)pow).onDexChange();
						}
					}
				}
				
				if (power instanceof VinesPower && power.amount > 0)
				{
					for (AbstractPower pow : AbstractDungeon.player.powers)
					{
						if (pow instanceof DuelistPower)
						{
							((DuelistPower)pow).onGainVines();
						}
					}
				}
			}
			else
			{
				if (power instanceof PoisonPower)
				{
					poisonAppliedThisCombat+=power.amount;
				}
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
				oOrb.checkFocus(oOrb instanceof Smoke);
			}
		}
	}

	@Override
	public void receivePostDeath() 
	{
		dungeonCardPool.clear();		// MARKERBOY
		coloredCards = new ArrayList<>(); // MARKERBOY
		archRoll1 = -1; // MARKERBOY
		archRoll2 = -1; // MARKERBOY
		boosterDeath = true;
		uniqueMonstersThisRun = new ArrayList<>();
		uniqueSpellsThisRun = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		uniqueTrapsThisRun = new ArrayList<>();
		uniqueSkillsThisCombat = new ArrayList<>();
		spellsPlayedCombatNames = new ArrayList<>();
		skillsPlayedCombatNames = new ArrayList<>();
		monstersPlayedCombatNames = new ArrayList<>();
		monstersPlayedRunNames = new ArrayList<>();
		spellCombatCount = 0;
		tokensThisCombat = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		summonLastCombatCount = 0;
		spellRunCount = 0;
		trapRunCount = 0;
		summonRunCount = 0;
		tribRunCount = 0;
		tribCombatCount = 0;
		tributeLastCombatCount = 0;
		zombiesResummonedThisRun = 0;
		spectralDamageMult = 2;
		dragonStr = 2;
		warriorTribEffectsTriggeredThisCombat = 0;
		warriorTribEffectsPerCombat = 1;
		toonVuln = 1;
		machineArt = 1;
		rockBlock = 2;
		zombieResummonBlock = 5;
		spellcasterBlockOnAttack = 4;
		explosiveDmgLow = explosiveDamageLowDefault;
		explosiveDmgHigh = explosiveDamageHighDefault;
		insectPoisonDmg = baseInsectPoison;
		naturiaVines = 1;
		naturiaLeaves = 1;
		AbstractPlayer.customMods = new ArrayList<>();
		defaultMaxSummons = 5;
		lastMaxSummons = 5;
		currentZombieSouls = 0;
		defaultStartZombieSouls = 3;
		swordsPlayed = 0;
		hasUpgradeBuffRelic = false;
		hasShopBuffRelic = false;
		hadFrozenEye = false;
		gotFrozenEyeFromBigEye = false;
		gotWisemanHaunted = false;
		spellcasterRandomOrbsChanneled = 0;
		currentSpellcasterOrbChance = 25;
		lastCardPlayed = new CancelCard();
		secondLastCardPlayed = new CancelCard();
		lastPlantPlayed = new CancelCard();
		secondLastPlantPlayed = new CancelCard();
		lastGhostrickPlayed = new CancelCard();
		secondLastGhostrickPlayed = new CancelCard();
		battleFusionMonster = new CancelCard();
		warriorTribThisCombat = false;
		vampiresPlayed = 0;
		vendreadPlayed = 0;
		ghostrickPlayed = 0;
		mayakashiPlayed = 0;
		shiranuiPlayed = 0;
		corpsesEntombed = 0;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt("souls", currentZombieSouls);
			config.setInt("startSouls", defaultStartZombieSouls);
			config.setInt(PROP_RESUMMON_DMG, 1);
			config.setBool(PROP_WISEMAN, gotWisemanHaunted);
			config.setInt("corpsesEntombed", corpsesEntombed);
			config.setInt("defaultMaxSummons", defaultMaxSummons);
			config.setString("lastCardPool", "~"); // MARKERBOY
			config.setInt("vampiresPlayed", vampiresPlayed);
			config.setInt("vendreadPlayed", vendreadPlayed);
			config.setInt("ghostrickPlayed", ghostrickPlayed);
			config.setInt("mayakashiPlayed", mayakashiPlayed);
			config.setInt("shiranuiPlayed", shiranuiPlayed);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveCardUsed(AbstractCard arg0) 
	{
		Util.handleZombSubTypes(arg0);
		if (arg0.hasTag(Tags.VAMPIRE)) { playedVampireThisTurn = true; }
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { c.onCardPlayedWhileSummoned(arg0); }
		}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { DuelistCard dc = (DuelistCard)c; dc.onCardPlayedWhileInGraveyard(arg0); }}
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			if (c.hasTag(Tags.CARDINAL) && !c.uuid.equals(arg0.uuid) && !arg0.hasTag(Tags.CARDINAL))
			{
				AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(c, AbstractDungeon.player.hand));
				if (AbstractDungeon.player.hasRelic(CoralToken.ID)) { AbstractDungeon.actionManager.addToBottom(new TsunamiAction(3)); }
			}
		}
		if (arg0 instanceof TokenCard || arg0.hasTag(Tags.TOKEN)) { tokensThisCombat++; }
		for (AbstractOrb o : AbstractDungeon.player.orbs)
		{
			if (o instanceof Alien)
			{
				Alien al = (Alien)o;
				al.triggerPassiveEffect();
			}
		}
		
		if (arg0.hasTag(Tags.SPIDER))
		{
			if (!playedSpider) { playedSpider = true; }
			else if (!playedSecondSpider) { playedSecondSpider = true; }
			else if (!playedThirdSpider) { DuelistCard.gainTempHP(spiderTempHP); playedThirdSpider = true; }
		}
		
		if (arg0.hasTag(Tags.BUG))
		{
			if (!playedBug) { playedBug = true; }
			else if (!playedSecondBug) { DuelistCard.gainTempHP(bugTempHP); playedSecondBug = true; }
		}
		
		if (arg0.hasTag(Tags.NATURIA))
		{
			if (AbstractDungeon.player.hasRelic(Leafpile.ID)) { DuelistCard.applyPowerToSelf(new LeavesPower(1)); }
			if (!AbstractDungeon.player.hasPower(VinesPower.POWER_ID)) 
			{ 
				DuelistCard.applyPowerToSelf(new VinesPower(DuelistMod.naturiaVines)); 
				for (AbstractPower pow : AbstractDungeon.player.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onGainVines(); }}
				for (AbstractRelic r : AbstractDungeon.player.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onGainVines(); }}
			}
		}
		
		secondLastCardPlayed = lastCardPlayed;
		lastCardPlayed = arg0;
		// Haunted Check
		if (AbstractDungeon.player.hasPower(HauntedPower.POWER_ID))
		{
			HauntedPower pow = (HauntedPower) AbstractDungeon.player.getPower(HauntedPower.POWER_ID);
			if (!(pow.hauntedCardBaseType.equals(CardType.CURSE))) { if (arg0.type.equals(pow.hauntedCardBaseType)) { pow.triggerHaunt(arg0); }}
			else if (!(pow.hauntedCardType.equals(Tags.DRAGON))) { if (arg0.hasTag(pow.hauntedCardType)) { pow.triggerHaunt(arg0); }}
			if (debug ) { logger.info("Triggered Haunted Power check code"); }
		}
		
		if (AbstractDungeon.player.hasPower(HauntedDebuff.POWER_ID))
		{
			HauntedDebuff pow = (HauntedDebuff) AbstractDungeon.player.getPower(HauntedDebuff.POWER_ID);
			if (!(pow.hauntedCardBaseType.equals(CardType.CURSE))) { if (arg0.type.equals(pow.hauntedCardBaseType)) { pow.triggerHaunt(arg0); }}
			else if (!(pow.hauntedCardType.equals(Tags.DRAGON))) { if (arg0.hasTag(pow.hauntedCardType)) { pow.triggerHaunt(arg0); }}
			if (debug ) { logger.info("Triggered Haunted Debuff check code"); }
		}
		
		// Fire Orb Check
		if (AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof FireOrb) { ((FireOrb) o).triggerPassiveEffect(); }}}

		playedOneCardThisCombat = true;
		logger.info("Card Used: " + arg0.name);
		if (arg0.type.equals(CardType.ATTACK))
		{
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				boolean isOnlySpellcasters = instance.isEveryMonsterCheck(Tags.SPELLCASTER, false);
				int extra = 0;
				if (isOnlySpellcasters)
				{
					if (AbstractDungeon.player.hasPower(MagickaPower.POWER_ID)) 
					{ 
						extra = AbstractDungeon.player.getPower(MagickaPower.POWER_ID).amount; 
						if (!arg0.hasTag(Tags.NO_MANA_RESET))
						{
							if (AbstractDungeon.player.hasPower(MagiciansRobePower.POWER_ID))
							{
								TwoAmountPower pow = (TwoAmountPower)AbstractDungeon.player.getPower(MagiciansRobePower.POWER_ID);
								if (pow.amount2 > 0)
								{
									pow.flash();
									pow.amount2--; pow.updateDescription();
								}
								else
								{
									AbstractDungeon.player.getPower(MagickaPower.POWER_ID).amount = 0;
									AbstractDungeon.player.getPower(MagickaPower.POWER_ID).updateDescription();
								}
							}
							else
							{
								AbstractDungeon.player.getPower(MagickaPower.POWER_ID).amount = 0;
								AbstractDungeon.player.getPower(MagickaPower.POWER_ID).updateDescription();
							}
						}						
					}
					if (spellcasterBlockOnAttack + extra > 0 && AbstractDungeon.player.hasPower(MagickaPower.POWER_ID) && extra > 0) { MagickaPower pow = (MagickaPower)AbstractDungeon.player.getPower(MagickaPower.POWER_ID); DuelistCard.manaBlock(spellcasterBlockOnAttack + extra, pow); }
					else if (spellcasterBlockOnAttack + extra > 0) { DuelistCard.staticBlock(spellcasterBlockOnAttack + extra); }
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
			if (!skillsPlayedCombatNames.contains(arg0.originalName))
			{
				skillsPlayedCombatNames.add(arg0.originalName);
				uniqueSkillsThisCombat.add(arg0);
			}
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
			playedSpellThisTurn = true;
			if (!uniqueSpellsThisRunMap.containsKey(arg0.cardID)) 
			{
				uniqueSpellsThisRunMap.put(arg0.cardID, arg0);
				uniqueSpellsThisRun.add((DuelistCard) arg0);
				DuelistMod.loadedSpellsThisRunList += arg0.cardID + "~";
			}
		}
		
		if (arg0.hasTag(Tags.MONSTER) && arg0 instanceof DuelistCard)
		{
			if (battleFusionMonster == null || battleFusionMonster instanceof CancelCard)
			{
				if (!arg0.hasTag(Tags.EXEMPT)) {
					battleFusionMonster = arg0.makeStatEquivalentCopy();
				}				
			}
			// Check for monsters with >2 summons for Splash orbs
			DuelistCard duelistArg0 = (DuelistCard)arg0;

			if (arg0.hasTag(Tags.PLANT)) 
			{
				secondLastPlantPlayed = lastPlantPlayed;
				lastPlantPlayed = arg0;
				if (duelistArg0.tributes > 1)
				{
					for (AbstractCard c : AbstractDungeon.player.exhaustPile.group)
					{
						if (c instanceof RevivalRose)
						{
							DuelistCard rvrose = (DuelistCard)c;
							AbstractMonster m = AbstractDungeon.getRandomMonster();
							if (m != null) { DuelistCard.resummon(rvrose, m, 1, c.upgraded, false);}
						}
					}
				}
			}
			
			if (arg0.hasTag(Tags.GHOSTRICK))
			{
				secondLastGhostrickPlayed = lastGhostrickPlayed;
				lastGhostrickPlayed = arg0;
			}
			
			if (!uniqueMonstersThisRunMap.containsKey(arg0.cardID))
			{
				uniqueMonstersThisRun.add((DuelistCard)arg0);
				uniqueMonstersThisRunMap.put(arg0.cardID, arg0);
				DuelistMod.loadedUniqueMonstersThisRunList += arg0.cardID + "~";
			}
		
			if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID))
			{
				DuelistCard dC = (DuelistCard)arg0;
				if (dC.tributes < 1 && arg0.hasTag(Tags.MONSTER)) { DuelistCard.summon(AbstractDungeon.player, 1, (DuelistCard)arg0); }
			}
			
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c instanceof GiantRex && arg0.hasTag(Tags.DINOSAUR))
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
				
				if ((c instanceof ArmageddonDragonEmp) && arg0.hasTag(Tags.DRAGON))
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
				if (c instanceof GiantRex && arg0.hasTag(Tags.DINOSAUR))
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
				
				if ((c instanceof ArmageddonDragonEmp) && arg0.hasTag(Tags.DRAGON))
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
			if (!uniqueTrapsThisRunMap.containsKey(arg0.cardID)) 
			{
				uniqueTrapsThisRunMap.put(arg0.cardID, arg0);
				uniqueTrapsThisRun.add((DuelistCard) arg0);
				DuelistMod.loadedSpellsThisRunList += arg0.cardID + "~";
			}
		}
	}

	// MARKERBOY
	@Override
	public void receivePostCreateStartingDeck(PlayerClass arg0, CardGroup arg1) 
	{
		boolean badMods = false;
		ArrayList<String> badModNames = new ArrayList<>();
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
				if (holidayDeckCard != null && addingHolidayCard && arg0.name().equals("THE_DUELIST")) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
			} 
		}
		if (!badMods)
		{
			if (arg0.name().equals("THE_DUELIST"))
			{
				StarterDeckSetup.setupStartDecksB();
				ArrayList<AbstractCard> startingDeckB = new ArrayList<>();
				ArrayList<AbstractCard> startingDeck = new ArrayList<>(deckToStartWith);
				if (startingDeck.size() > 0)
				{
					arg1.group.clear();
					CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
					StringBuilder cardNames = new StringBuilder();
					//boolean replacedOneAlready = false;
					//int sparksGenerated = 0;
					for (AbstractCard c : startingDeck) 
					{ 
						if (c instanceof Sparks)
						{
							int roll = ThreadLocalRandom.current().nextInt(1, 20);
							if (Util.getChallengeLevel() > 9) { roll = 2; }
							//roll = 1;		// debug, make it always give you a special sparks
							if (roll == 1) 
							{ 
								startingDeckB.add(Util.getSpecialSparksCard()); 
								//replacedOneAlready = true; 
								Util.log("Generated a Special Sparks!"); 
								//sparksGenerated++;
							}
							else { startingDeckB.add(c); }
						}
						else { startingDeckB.add(c); }
					}
					for (AbstractCard c : startingDeckB) 
					{ 
						cardNames.append(c.name).append(", ");
						newStartGroup.addToRandomSpot(c.makeStatEquivalentCopy());
					}

					if (Util.getChallengeLevel() > 9)
					{
						DuelistCard da = new DuelistAscender();
						newStartGroup.addToRandomSpot(da);
						UnlockTracker.unlockCard(da.getID());
					}
					else if (AbstractDungeon.ascensionLevel >= 10) 
					{
						newStartGroup.addToRandomSpot(new AscendersBane());
						UnlockTracker.markCardAsSeen("AscendersBane");
					}
					arg1.group.addAll(newStartGroup.group);	
					if (holidayDeckCard != null && addingHolidayCard) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
					arg1.sortAlphabetically(true);
					lastTagSummoned = StarterDeckSetup.getCurrentDeck().getCardTag();
					if (lastTagSummoned == null) { lastTagSummoned = Tags.ALL; if (debug) { logger.info("starter deck has no associated card tag, so lastTagSummoned is reset to default value of ALL");}}

					if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck"))
					{
						if (debug) { DuelistMod.logger.info("Current Deck on receivePostCreateStartingDeck() was the Exodia Deck. Making cards soulbound..."); }
						for (AbstractCard c : arg1.group)
						{
							if (c.hasTag(Tags.EXODIA_DECK_UPGRADE))
							{
								c.upgrade();
							}
							
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
		if (arg0 instanceof DuelistPrismaticShard && AbstractDungeon.player.hasRelic(CardPoolRelic.ID))
		{
			AbstractDungeon.player.getRelic(CardPoolRelic.ID).flash();
		}
		
		if (arg0 instanceof Courier)
		{
			Util.log("Picked up The Courier, so we are removing all colored basic set cards from the colorless pool to avoid crashes. I'd rather do this than patch shopscreen to be smarter");
			ArrayList<AbstractCard> resetColorless = new ArrayList<>();
			for (AbstractCard c : AbstractDungeon.colorlessCardPool.group) { if (c.color.equals(CardColor.COLORLESS)) { resetColorless.add(c); }}
			AbstractDungeon.colorlessCardPool.clear();
			AbstractDungeon.colorlessCardPool.group.addAll(resetColorless);
			
			// If playing with basic cards turned on
			// or if the player possibly added basic cards to the colorless slots with either of the shop relics
			// We need to reset the shop to prevent the game from attempting to fill colored card slots with a new card when you purchase a colored card from a colorless card slot
			// .. because devs are lazy and shopscreen was coded stupid
			// .. but then again look at me writing lazy fixes too
			if (setIndex == 0 || setIndex == 3 || setIndex == 5 || setIndex == 6 || AbstractDungeon.player.hasRelic(MerchantPendant.ID) || AbstractDungeon.player.hasRelic(MerchantNecklace.ID)) 
			{ 
				if (Util.refreshShop()) { Util.log("Forced shop reset on Courier pickup"); }
			}
		}
		if (arg0 instanceof QuestionCard)
		{
			if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
			{
				BoosterHelper.modifyPackSize(1);
				Util.log("Question Card --> incremented booster pack size");
			}
		}

		if (arg0 instanceof BustedCrown)
		{
			if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters)
			{
				BoosterHelper.modifyPackSize(-2);
				Util.log("Busted Crown --> decremented booster pack size");
			}
		}
	}
	
	@Override
	public void receivePostDraw(AbstractCard drawnCard) 
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { c.onCardDrawnWhileSummoned(drawnCard); }
		}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { DuelistCard dc = (DuelistCard)c; dc.onCardDrawnWhileInGraveyard(drawnCard); }}
		if (drawnCard.hasTag(Tags.MALICIOUS))
		{
			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead && AbstractDungeon.cardRandomRng.random(1, 3) == 1)
				{
					DuelistCard.applyPower(new VulnerablePower(mon, 1, false), mon);
				}
			}
		}
		
		if (drawnCard.hasTag(Tags.FLUVIAL))
		{
			if (AbstractDungeon.player.discardPile.group.size() > 0)
			{
				int size = AbstractDungeon.player.discardPile.group.size();
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) { DuelistCard.gainTempHP(size); }
			}
		}
		
		if (drawnCard.hasTag(Tags.THALASSIC))
		{
			if (AbstractDungeon.cardRandomRng.random(1, 5) == 1) { DuelistCard.channel(new WaterOrb()); }
		}
		
		if (drawnCard.hasTag(Tags.PELAGIC))
		{
			if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) { 
				AbstractDungeon.actionManager.addToBottom(new TsunamiAction(1));
			}
		}
		
		if (AbstractDungeon.player.hasPower(GridRodPower.POWER_ID))
		{
			GridRodPower pow = ((GridRodPower)AbstractDungeon.player.getPower(GridRodPower.POWER_ID));
			if (pow.ready)
			{
				pow.trigger(drawnCard);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			stance.onDrawCard(drawnCard);
		}
		
		for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
        	if (o instanceof DuelistOrb)
        	{
        		((DuelistOrb)o).onDrawCard(drawnCard);
        	}
        }
		secondLastCardDrawn = lastCardDrawn;
		lastCardDrawn = drawnCard;
		for (AbstractOrb orb : AbstractDungeon.player.orbs)
		{
			if ((orb instanceof WhiteOrb) && (drawnCard.hasTag(Tags.SPELL) || drawnCard.hasTag(Tags.TRAP)))
			{
				WhiteOrb white = (WhiteOrb)orb;
				white.triggerPassiveEffect(drawnCard);
				if (white.gpcCheck()) { white.triggerPassiveEffect(drawnCard); }
				if (debug) { logger.info("found a White orb and a Spell/Trap card was drawn"); }
			}
		}
	
		if (drawnCard.hasTag(Tags.MONSTER))
		{
			DuelistCard dc = (DuelistCard)drawnCard;

			for (AbstractOrb orb : AbstractDungeon.player.orbs)
			{
				if (orb instanceof Smoke)
				{
					Smoke duelSmoke = (Smoke)orb;
					duelSmoke.triggerPassiveEffect((DuelistCard)drawnCard);
					if (duelSmoke.gpcCheck()) { duelSmoke.triggerPassiveEffect((DuelistCard)drawnCard); }
				}

				if (orb instanceof Sun && dc.isSummonCard(true))
				{
					Sun sun = (Sun)orb;
					sun.triggerPassiveEffect(drawnCard);
					if (sun.gpcCheck()) { sun.triggerPassiveEffect(drawnCard); }
				}
				
				if (orb instanceof Moon && dc.isTributeCard(true))
				{
					Moon moon = (Moon)orb;
					moon.triggerPassiveEffect(drawnCard);
					if (moon.gpcCheck()) { moon.triggerPassiveEffect(drawnCard); }
				}
			}
			
			if (dc.isTributeCard(true) && AbstractDungeon.player.hasRelic(NamelessWarRelicC.ID)) 
			{ 
				AbstractRelic r = AbstractDungeon.player.getRelic(NamelessWarRelicC.ID);
				r.flash();
				DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1)); 
			}
			
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
			
			if (AbstractDungeon.player.hasPower(FutureFusionPower.POWER_ID))
			{
				if (AbstractDungeon.player.getPower(FutureFusionPower.POWER_ID).amount > 0 && DuelistCard.allowResummonsWithExtraChecks(drawnCard))
				{
					AbstractPower pow = AbstractDungeon.player.getPower(FutureFusionPower.POWER_ID);
					pow.amount--; pow.updateDescription();
					AbstractMonster m = AbstractDungeon.getRandomMonster();
					if (m != null) { DuelistCard.resummon(drawnCard, m, 1); }
				}
				else if (AbstractDungeon.player.getPower(FutureFusionPower.POWER_ID).amount < 1)
				{
					AbstractPower pow = AbstractDungeon.player.getPower(FutureFusionPower.POWER_ID);
					DuelistCard.removePower(pow, pow.owner);
				}
				else
				{
					Util.log("Future Fusion is skipping the Resummon of " + drawnCard.cardID + " because that card cannot be Resummoned currently for some reason (Exempt, anti-resummon powers/effects, etc.)");
				}
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
			if (AbstractDungeon.player.getPower(HeartUndertrapPower.POWER_ID).amount > 0)
			{
				if (drawnCard.hasTag(Tags.TRAP))
				{
					DuelistCard.heal(AbstractDungeon.player, 3);
				}
			}
		}
		
		// Undertribute - Draw tribute monster = Summon 1
		if (AbstractDungeon.player.hasPower(HeartUndertributePower.POWER_ID))
		{
			if (drawnCard instanceof DuelistCard && drawnCard.hasTag(Tags.MONSTER))
			{
				DuelistCard ref = (DuelistCard) drawnCard;
				if (ref.isTributeCard())
				{
					DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new UnderdogToken());
					DuelistCard.summon(AbstractDungeon.player, 1, tok);
				}
			}
		}
		
		
	}

	// MARKERBOY
	@Override
	public void receivePostDungeonInitialize() 
	{
		logger.info("dungeon initialize hook");
	}


	// MARKERBOY
	@Override
	public void receivePostDungeonUpdate() 
	{
		if (!checkedCardPool)
		{
			//Util.log("hasnt checked card pool yet");
			// Card Pool Reload Handler
			String fullCardPool = "";
			try { SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults); config.load();  fullCardPool = config.getString("fullCardPool"); } catch (Exception e) { e.printStackTrace(); }
			String[] savedStrings = fullCardPool.split("~");
			ArrayList<String> strings = new ArrayList<>();
			Collections.addAll(strings, savedStrings);
			if (strings.size() > 0 && CardCrawlGame.characterManager.anySaveFileExists())
			{
				//Util.log("Found and loaded previous card pool from this run. Pool Size=" + strings.size());
				toReplacePoolWith.clear();
				shouldReplacePool = true;
				replacingOnUpdate = true;
				for (String s : strings)
				{
					if (mapForCardPoolSave.containsKey(s))
					{
						dungeonCardPool.put(s, mapForCardPoolSave.get(s).name);
						toReplacePoolWith.add(mapForCardPoolSave.get(s).makeCopy());
					}
				}
			}
			else if (strings.size() > 0)
			{
				//Util.log("Found previous card pool but no save file exists. Resetting saved card pool so it doesn't overwrite the new run pool.");
				toReplacePoolWith.clear();
				shouldReplacePool = false;
				replacingOnUpdate = false;
				try { SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults); config.setString("fullCardPool", "~"); config.save(); } catch (Exception e) { e.printStackTrace(); }
			}
			if (DuelistMod.toReplacePoolWith.size() > 0)
			{
				CardCrawlGame.dungeon.initializeCardPools();
			}
			checkedCardPool = true;
		}
	}

	@Override
	public void receiveCustomModeMods(List<CustomMod> arg0) 
	{
		
	}
	
	@Override
	public int receiveOnPlayerLoseBlock(int arg0) 
	{
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			stance.onLoseBlock(arg0);
		}
		
		for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
        	if (o instanceof DuelistOrb)
        	{
        		((DuelistOrb)o).onLoseBlock(arg0);
        	}
        }
		
		return arg0;
	}
	
	@Override
	public boolean receivePreMonsterTurn(AbstractMonster arg0) 
	{
		playedSpellThisTurn = false;
		AbstractPlayer p = AbstractDungeon.player;
		summonedTypesThisTurn = new ArrayList<>();
		kuribohrnFlipper = false;
		secondLastTurnHP = lastTurnHP;
		lastTurnHP = AbstractDungeon.player.currentHealth;
		if (overflowedThisTurn) { overflowedLastTurn = true; overflowedThisTurn = false; }
		else { overflowedLastTurn = false; }
		// Fix tributes & summons & magic nums that were modified for turn only
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
		
		for (AbstractCard c : TheDuelist.resummonPile.group)
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}
		
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			for (AbstractCard c : pow.actualCardSummonList)
			{
				if (c instanceof DuelistCard)
				{
					DuelistCard dC = (DuelistCard)c;
					dC.postTurnReset();
				}
			}
		}
		
		
		// Check to maybe print secret message
		if (summonTurnCount > 2)
		{
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			int msgRoll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (debugMsg && m != null)
			{					
				AbstractDungeon.actionManager.addToBottom(new TalkAction(m, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
			}
			else if (m != null)
			{
				if (msgRoll <= 2)
				{
					AbstractDungeon.actionManager.addToBottom(new TalkAction(m, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
				}
			}
		}
		
		summonTurnCount = 0;
		tribTurnCount = 0;
		// Mirror Force Helper
		if (p.hasPower(MirrorForcePower.POWER_ID))
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
		if (AbstractDungeon.floorNum <= 1)
		{
			BoosterHelper.setPackSize(5);
			Util.resetCardsPlayedThisRunLists();
			//if (Util.getChallengeLevel() > 4 && AbstractDungeon.player.gold > 0) { AbstractDungeon.player.gold = 0; }
			if (Util.getChallengeLevel() > 1) { lastMaxSummons = defaultMaxSummons = 4; }
			if (debug) { logger.info("Started act and should fill was false. Current Floor was <2! So we reset everything!!"); }
			poolIsCustomized = false; // MARKERBOY
			chosenRockSunriseTag = Tags.DUMMY_TAG;
			selectedDeck = StarterDeckSetup.getCurrentDeck().getSimpleName();
			coloredCards = new ArrayList<>();
			wyrmTribThisCombat = false;
			playedBug = false;
			playedSecondBug = false;
			playedSpider = false;
			playedSecondSpider = false;
			playedThirdSpider = false;
			secondaryTierScorePools = new ArrayList<>();
			uniqueMonstersThisRun = new ArrayList<>();
			uniqueSpellsThisRun = new ArrayList<>();
			uniqueSpellsThisCombat = new ArrayList<>();
			uniqueTrapsThisRun = new ArrayList<>();
			uniqueSkillsThisCombat = new ArrayList<>();
			spellsPlayedCombatNames = new ArrayList<>();
			skillsPlayedCombatNames = new ArrayList<>();
			monstersPlayedCombatNames = new ArrayList<>();
			monstersPlayedRunNames = new ArrayList<>();
			spellCombatCount = 0;
			trapCombatCount = 0;
			sevenCompletedsThisCombat = 0;
			summonCombatCount = 0;
			summonLastCombatCount = 0;
			tributeLastCombatCount = 0;
			spellRunCount = 0;
			trapRunCount = 0;
			tribRunCount = 0;
			tribCombatCount = 0;
			summonRunCount = 0;
			spectralDamageMult = 2;
			dragonStr = 2;
			warriorTribEffectsPerCombat = 1;
			warriorTribEffectsTriggeredThisCombat = 0;
			toonVuln = 1;
			machineArt = 1;
			rockBlock = 2;
			zombieResummonBlock = 5;
			spellcasterBlockOnAttack = 4;
			explosiveDmgLow = explosiveDamageLowDefault;
			explosiveDmgHigh = explosiveDamageHighDefault;
			insectPoisonDmg = baseInsectPoison;
			naturiaVines = 1;
			naturiaLeaves = 1;
			zombiesResummonedThisRun = 0;
			AbstractPlayer.customMods = new ArrayList<>();
			swordsPlayed = 0;
			hasUpgradeBuffRelic = false;
			gotWisemanHaunted = false;
			hasShopBuffRelic = false;
			hadFrozenEye = false;
			gotFrozenEyeFromBigEye = false;
			spellcasterRandomOrbsChanneled = 0;
			currentSpellcasterOrbChance = 25;
			hasCardRewardRelic = false;
			hasBoosterRewardRelic = false;
			hasShopDupeRelic = false;
			warriorTribThisCombat = false;
			monstersObtained = 0;
			spellsObtained = 0;
			trapsObtained = 0;
			synergyTributesRan = 0;
			highestMaxSummonsObtained = 5;
			resummonsThisRun = 0;
			vampiresPlayed = 0;
			vendreadPlayed = 0;
			ghostrickPlayed = 0;
			mayakashiPlayed = 0;
			shiranuiPlayed = 0;
			challengeLevel20 = Util.getChallengeLevel() > 19;
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.hasTag(Tags.MONSTER)) { monstersObtained++; }
				if (c.hasTag(Tags.SPELL)) { spellsObtained++; }
				if (c.hasTag(Tags.TRAP)) { trapsObtained++; }
			}
			//CardCrawlGame.dungeon.initializeCardPools();
		}
		//else if (shouldFill) { CardCrawlGame.dungeon.initializeCardPools(); }
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setBool(PROP_WISEMAN, gotWisemanHaunted);
			config.setBool("playingChallenge", DuelistMod.playingChallenge);
			config.setBool("challengeLevel20", challengeLevel20);
			config.setInt("currentChallengeLevel", DuelistMod.challengeLevel);
			config.setInt("defaultMaxSummons", DuelistMod.defaultMaxSummons);
			config.setString("entombed", entombedCardsThisRunList);
			config.setString("entombedCustomCardProperites", entombedCustomCardProperites);
			config.setString(DuelistMod.PROP_MONSTERS_RUN, loadedUniqueMonstersThisRunList);
			config.setString(DuelistMod.PROP_TRAPS_RUN, loadedTrapsThisRunList);
			config.setString(DuelistMod.PROP_SPELLS_RUN, loadedSpellsThisRunList);
			config.setInt(PROP_DECK, deckIndex);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	// CONFIG MENU SETUP -------------------------------------------------------------------------------------------------------------------------------------- //
	
	// Line breakers
	private void cbLB() { yPos-=45; }
	private void genericLB(float lb) { yPos-=lb; }

	private void configPanelSetup()
	{
		String cardsString = Config_UI_String.TEXT[5];
		String toonString = Config_UI_String.TEXT[0];
		String creatorString = Config_UI_String.TEXT[11];
		String exodiaString = Config_UI_String.TEXT[1];
		String ojamaString = Config_UI_String.TEXT[2];
		String unlockString = Config_UI_String.TEXT[8];
		String oldCharString = Config_UI_String.TEXT[12];
		String flipString = Config_UI_String.TEXT[9];
		String debugString = Config_UI_String.TEXT[10];
		String setString = Config_UI_String.TEXT[4];
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
		cardLabelTxt = new ModLabel(cardsString + cardCount, xLabPos, yPos,settingsPanel,(me)->{});
		
		allowBoostersBtn = new ModLabeledToggleButton(Strings.configAllowBoosters,xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, allowBoosters, settingsPanel, (label) -> {}, (button) -> 
		{
			allowBoosters = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ALLOW_BOOSTERS, allowBoosters);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		
		alwaysBoostersBtn = new ModLabeledToggleButton(Strings.configAlwaysBoosters,xLabPos + xSecondCol + xSecondCol - 100, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, alwaysBoosters, settingsPanel, (label) -> {}, (button) -> 
		{
			alwaysBoosters = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ALWAYS_BOOSTERS, alwaysBoosters);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		
		cbLB();
		// END Card Count Label

		// Remove Toons
		toonBtn = new ModLabeledToggleButton(toonString,xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, toonBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			toonBtnBool = button.enabled;
			//shouldFill = true;
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
			//shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CREATOR_BTN, creatorBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Remove Creator
		
		// Check Light Basic
		lightBasicBtn = new ModLabeledToggleButton("Reduced Basic Set", xLabPos + xSecondCol + xSecondCol - 100, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, smallBasicSet, settingsPanel, (label) -> {}, (button) -> 
		{
			smallBasicSet = button.enabled;
			//shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_SMALL_BASIC, smallBasicSet);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();
		// END Light Basic

		// Remove Exodia
		exodiaBtn = new ModLabeledToggleButton(exodiaString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, exodiaBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			exodiaBtnBool = button.enabled;
			//shouldFill = true;
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
			//shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_OJAMA_BTN, ojamaBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});	
		// END Remove Ojama
		
		// Check Remove Card Rewards
		removeCardRewardsBtn = new ModLabeledToggleButton("Remove Card Rewards", xLabPos + xSecondCol + xSecondCol - 100, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, removeCardRewards, settingsPanel, (label) -> {}, (button) -> 
		{
			removeCardRewards = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_REMOVE_CARD_REWARDS, removeCardRewards);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Remove Card Rewards

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
		// END Switch to old character model
		
		// Toggle Duelist Monsters
		noDuelistMonstersBtn = new ModLabeledToggleButton("Encounter Duelists", xLabPos + xSecondCol + xSecondCol - 100, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, duelistMonsters, settingsPanel, (label) -> {}, (button) -> 
		{
			duelistMonsters = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_DUELIST_MONSTERS, duelistMonsters);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Toggle Duelist Monsters
		
		// Toggle Duelist events
		allowDuelistEventsBtn = new ModLabeledToggleButton("Allow Duelist Events", xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, allowDuelistEvents, settingsPanel, (label) -> {}, (button) -> 
		{
			allowDuelistEvents = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool("allowDuelistEvents", allowDuelistEvents);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Toggle Duelist events


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
		// END Flip card tags
		
		// Toggle Duelist Curses
		noDuelistCursesBtn = new ModLabeledToggleButton("Duelist Curses", xLabPos + xSecondCol + xSecondCol - 40, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, duelistCurses, settingsPanel, (label) -> {}, (button) -> 
		{
			duelistCurses = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_DUELIST_CURSES, duelistCurses);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Toggle Duelist Curses

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
		saver++;
		// END Add 2 Randomziation Buttons
		
		// Toggle Orb potions
		addOrbPotionsBtn = new ModLabeledToggleButton("Orb Potions", xLabPos + xSecondCol + xSecondCol - 40, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, addOrbPotions, settingsPanel, (label) -> {}, (button) -> 
		{
			addOrbPotions = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ADD_ORB_POTIONS, addOrbPotions);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Toggle Orb potions
		
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
		saver++;
		// END Add 2 Randomziation Buttons
		
		// Toggle QTE
		quickTimeBtn = new ModLabeledToggleButton("Allow QTE", xLabPos + xSecondCol + xSecondCol - 10, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, quicktimeEventsAllowed, settingsPanel, (label) -> {}, (button) -> 
		{
			quicktimeEventsAllowed = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool("quicktimeEventsAllowed", quicktimeEventsAllowed);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		cbLB();	
		// END Toggle QTE
		
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
		saver++;
		// END Add 2 Randomziation Buttons
		
		// Switch to kaiba character model
		kaibaCharBtn = new ModLabeledToggleButton("Play as Kaiba", xLabPos + xSecondCol + xSecondCol - 10, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, playAsKaiba, settingsPanel, (label) -> {}, (button) -> 
		{
			playAsKaiba = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_PLAY_KAIBA, playAsKaiba);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetDuelist();
		});
		cbLB();	
		// END Switch to kaiba character model
		
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
		saver++;
		// END Add 2 Randomziation Buttons
		
		// Toggle card pool add/remove/save/load relics
		cardPoolRelicsBtn = new ModLabeledToggleButton("Card Pool Relics", xLabPos + xSecondCol + xSecondCol - 10, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, allowCardPoolRelics, settingsPanel, (label) -> {}, (button) -> 
		{
			allowCardPoolRelics = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_ALLOW_CARD_POOL_RELICS, allowCardPoolRelics);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
		});
		cbLB();	
		// END Toggle card pool add/remove/save/load relics
		
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
		
		// Check Box Allow Red/Blue/Green
		allowBaseGameCardsBtn = new ModLabeledToggleButton(Strings.allowBaseGameCards,xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, baseGameCards, settingsPanel, (label) -> {}, (button) -> 
		{
			baseGameCards = button.enabled;
			//shouldFill = true;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_BASE_GAME_CARDS, baseGameCards);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		//settingsPanel.addUIElement(debugBtn);
		// END Check Box Allow Red/Blue/Green
		
		// Check Box Force Puzzle Summons
		forcePuzzleBtn = new ModLabeledToggleButton(Strings.forcePuzzleText,xLabPos + xSecondCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, forcePuzzleSummons, settingsPanel, (label) -> {}, (button) -> 
		{
			forcePuzzleSummons = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_FORCE_PUZZLE, forcePuzzleSummons);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Check Box Force Puzzle Summons
		
		// Check Box DEBUG
		debugBtn = new ModLabeledToggleButton(debugString,xLabPos + xSecondCol + xThirdCol, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, debug, settingsPanel, (label) -> {}, (button) -> 
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

		//genericLB(20);

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
			//shouldFill = true;
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
			//shouldFill = true;
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
		
		float xPosLoc = xLabPos;
		float yPosMod = -10.0f + yPos;
		birthdayLabel = new ModLabel("Enter Your Birthday:", xPosLoc, yPos, settingsPanel, (me)->{});
		
		xPosLoc = xLArrow - 115.0f;
		birthdayMonthLabel = new ModLabel("Month", xPosLoc, yPos, settingsPanel, (me)->{});
		
		xPosLoc = xLArrow;
		birthdayMonthLeftBtn =  new ModButton(xPosLoc, yPosMod, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
			if (birthdayMonth > 1) { birthdayMonth--; }
			else { birthdayMonth = 12; }
			birthdayMonthTxt.text = "" + birthdayMonth;
			if (neverChangedBirthday) { neverChangedBirthday = false; }
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt("birthdayMonth", birthdayMonth);
				config.setBool("neverChangedBirthday", neverChangedBirthday);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		xPosLoc += 75.0f;
		birthdayMonthTxt = new ModLabel("" + birthdayMonth, xPosLoc, yPos, settingsPanel, (me)->{});
		
		xPosLoc += 50.0f;
		birthdayMonthRightBtn =  new ModButton(xPosLoc, yPosMod, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
			if (birthdayMonth < 12) { birthdayMonth++; }
			else { birthdayMonth = 1; }
			birthdayMonthTxt.text = "" + birthdayMonth;
			if (neverChangedBirthday) { neverChangedBirthday = false; }
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt("birthdayMonth", birthdayMonth);
				config.setBool("neverChangedBirthday", neverChangedBirthday);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		xPosLoc += 200.0f;
		birthdayDayLabel = new ModLabel("Day", xPosLoc, yPos, settingsPanel, (me)->{});
		
		xPosLoc += 90.0f;
		birthdayDayLeftBtn =  new ModButton(xPosLoc, yPosMod, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
			if (birthdayDay > 1) { birthdayDay--; }
			else { birthdayDay = 31; }
			birthdayDayTxt.text = "" + birthdayDay;
			if (neverChangedBirthday) { neverChangedBirthday = false; }
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt("birthdayDay", birthdayDay);
				config.setBool("neverChangedBirthday", neverChangedBirthday);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		xPosLoc += 75.0f;
		birthdayDayTxt = new ModLabel("" + birthdayDay, xPosLoc, yPos, settingsPanel, (me)->{});
		
		xPosLoc += 50.0f;
		birthdayDayRightBtn =  new ModButton(xPosLoc, yPosMod, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
			if (birthdayDay < 31) { birthdayDay++; }
			else { birthdayDay = 1; }
			birthdayDayTxt.text = "" + birthdayDay;
			if (neverChangedBirthday) { neverChangedBirthday = false; }
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt("birthdayDay", birthdayDay);
				config.setBool("neverChangedBirthday", neverChangedBirthday);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		genericLB(60.0f);

		// Allow Country/Language Upload
		allowLocaleBtn = new ModLabeledToggleButton("Upload Country & Language with Metric Data", xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, allowLocaleUpload, settingsPanel, (label) -> {}, (button) ->
		{
			allowLocaleUpload = button.enabled;
			try
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool("allowLocaleUpload", allowLocaleUpload);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }

		});
		// END Allow Country/Language Upload
		
		
		
		
		
		// Starting Deck Selector
		/*
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
		*/
		
		settingsPanel.addUIElement(cardLabelTxt);
		settingsPanel.addUIElement(allowBoostersBtn);
		settingsPanel.addUIElement(alwaysBoostersBtn);
		settingsPanel.addUIElement(toonBtn);
		settingsPanel.addUIElement(creatorBtn);
		settingsPanel.addUIElement(lightBasicBtn);
		settingsPanel.addUIElement(exodiaBtn);
		settingsPanel.addUIElement(ojamaBtn);
		settingsPanel.addUIElement(removeCardRewardsBtn);
		settingsPanel.addUIElement(unlockBtn);
		settingsPanel.addUIElement(oldCharBtn);
		settingsPanel.addUIElement(noDuelistMonstersBtn);
		settingsPanel.addUIElement(allowDuelistEventsBtn);
		settingsPanel.addUIElement(flipBtn);
		settingsPanel.addUIElement(noDuelistCursesBtn);
		settingsPanel.addUIElement(noChangeBtnCost);
		settingsPanel.addUIElement(onlyDecBtnCost);
		settingsPanel.addUIElement(addOrbPotionsBtn);
		settingsPanel.addUIElement(noChangeBtnTrib);
		settingsPanel.addUIElement(onlyDecBtnTrib);
		settingsPanel.addUIElement(quickTimeBtn);
		settingsPanel.addUIElement(noChangeBtnSumm);
		settingsPanel.addUIElement(onlyIncBtnSumm);
		settingsPanel.addUIElement(kaibaCharBtn);		
		settingsPanel.addUIElement(alwaysUpgradeBtn);
		settingsPanel.addUIElement(neverUpgradeBtn);
		settingsPanel.addUIElement(cardPoolRelicsBtn);
		settingsPanel.addUIElement(etherealBtn);
		settingsPanel.addUIElement(exhaustBtn);
		settingsPanel.addUIElement(allowBaseGameCardsBtn);
		settingsPanel.addUIElement(forcePuzzleBtn);
		settingsPanel.addUIElement(debugBtn);
		settingsPanel.addUIElement(setSelectLabelTxt);
		settingsPanel.addUIElement(setSelectColorTxt);
		settingsPanel.addUIElement(setSelectLeftBtn);
		settingsPanel.addUIElement(setSelectRightBtn);
		settingsPanel.addUIElement(birthdayLabel);
		settingsPanel.addUIElement(birthdayMonthLabel);
		settingsPanel.addUIElement(birthdayMonthLeftBtn);
		settingsPanel.addUIElement(birthdayMonthTxt);		
		settingsPanel.addUIElement(birthdayMonthRightBtn);
		settingsPanel.addUIElement(birthdayDayLabel);
		settingsPanel.addUIElement(birthdayDayLeftBtn);
		settingsPanel.addUIElement(birthdayDayTxt);		
		settingsPanel.addUIElement(birthdayDayRightBtn);
		settingsPanel.addUIElement(allowLocaleBtn);
		//settingsPanel.addUIElement(setSelectLabelTxtB);
		//settingsPanel.addUIElement(setSelectColorTxtB);
		//settingsPanel.addUIElement(setSelectLeftBtnB);
		//settingsPanel.addUIElement(setSelectRightBtnB);


	}
	
	private void setupExtraConfigStrings()
	{
		Strings.etherealForCardText = Config_UI_String.TEXT[24];
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
		Strings.forcePuzzleText = Config_UI_String.TEXT[137];
		Strings.configAllowBoosters = Config_UI_String.TEXT[138];
		Strings.configAlwaysBoosters = Config_UI_String.TEXT[139];
		Strings.configRemoveCards = Config_UI_String.TEXT[140];		
		Strings.configGiantDeck = Config_UI_String.TEXT[141];
		Strings.configInsectDeck = Config_UI_String.TEXT[142];
		Strings.configPlantDeck = Config_UI_String.TEXT[143];
		Strings.configPredaplantDeck = Config_UI_String.TEXT[144];
		Strings.configMegatypeDeck = Config_UI_String.TEXT[145];
		Strings.configAscended1 = Config_UI_String.TEXT[146];
		Strings.configAscended2 = Config_UI_String.TEXT[147];
		Strings.configAscended3 = Config_UI_String.TEXT[148];
		Strings.configPharaoh1 = Config_UI_String.TEXT[149];
		Strings.configPharaoh2 = Config_UI_String.TEXT[150];
		Strings.configPharaoh3 = Config_UI_String.TEXT[151];
		Strings.configPharaoh4 = Config_UI_String.TEXT[152];
		Strings.configPharaoh5 = Config_UI_String.TEXT[153];
	}

	public void resetDuelist() 
	{
		if (playAsKaiba) { characterModel = kaibaPlayerModel; }
		else if (oldCharacter) { characterModel = oldYugiChar; }
		else { characterModel = yugiChar; }
	}
	
	public static void resetDuelistWithDeck(int deckCode)
	{
		boolean isDragonDeck = deckCode == 1;
		boolean isSpellcasterDeck = deckCode == 3;
		//boolean isToonDeck = deckCode == 4;
		if (isDragonDeck) { characterModel = kaibaPlayerModel; }
		//else if (isToonDeck) { characterModel = pegasusPlayerModel; }
		else if (isSpellcasterDeck && oldCharacter) { characterModel = oldYugiChar; }
		else if (isSpellcasterDeck) { characterModel = yugiChar; }
		else if (playAsKaiba) { characterModel = kaibaPlayerModel; }
		else if (oldCharacter) { characterModel = oldYugiChar; }
		else { characterModel = yugiChar; }
	}

	public static void getEnemyDuelistModel(int deckCode)
	{
		boolean isDragonDeck = deckCode == 1;
		boolean isSpellcasterDeck = deckCode == 3;
		if (isDragonDeck && oldCharacter) { DuelistMod.kaibaEnemyModel = "OldYugiEnemy2"; monsterIsKaiba = false; }
		else if (isDragonDeck) { DuelistMod.kaibaEnemyModel = "YugiEnemy2"; monsterIsKaiba = false; }
		else if (isSpellcasterDeck) { DuelistMod.kaibaEnemyModel = "KaibaModel2"; monsterIsKaiba = true; }
		else if (playAsKaiba && oldCharacter) { DuelistMod.kaibaEnemyModel = "OldYugiEnemy2"; monsterIsKaiba = false; }
		else if (playAsKaiba) { DuelistMod.kaibaEnemyModel = "YugiEnemy2"; monsterIsKaiba = false; }
		else { DuelistMod.kaibaEnemyModel = "KaibaModel2"; monsterIsKaiba = true; }
	}
	
	@Override
	public void receivePostObtainCard(AbstractCard card) 
	{
		if (card instanceof OnObtainEffect)
		{
			((OnObtainEffect) card).onObtain();
		}			
	}

	@Override
	public void receiveStartGame() {}
	
	public static void onReceiveBoosterPack(BoosterPack pack)
	{
		for (AbstractRelic r : AbstractDungeon.player.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onReceiveBoosterPack(pack); }}
		for (AbstractPotion r : AbstractDungeon.player.potions) { if (r instanceof DuelistPotion) { ((DuelistPotion)r).onReceiveBoosterPack(pack); }}
	}

	// MARKERBOY
	public static void onAbandonRunFromMainMenu()
	{
		Util.log("Player abandoned run from the main menu!");
		dungeonCardPool.clear();
		coloredCards = new ArrayList<>();
		archRoll1 = -1;
		archRoll2 = -1;
		boosterDeath = true;
		uniqueMonstersThisRun = new ArrayList<>();
		uniqueSpellsThisRun = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		uniqueTrapsThisRun = new ArrayList<>();
		uniqueSkillsThisCombat = new ArrayList<>();
		spellsPlayedCombatNames = new ArrayList<>();
		skillsPlayedCombatNames = new ArrayList<>();
		monstersPlayedCombatNames = new ArrayList<>();
		monstersPlayedRunNames = new ArrayList<>();
		spellCombatCount = 0;
		tokensThisCombat = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		summonLastCombatCount = 0;
		spellRunCount = 0;
		trapRunCount = 0;
		summonRunCount = 0;
		tribRunCount = 0;
		tribCombatCount = 0;
		tributeLastCombatCount = 0;
		zombiesResummonedThisRun = 0;
		spectralDamageMult = 2;
		dragonStr = 2;
		warriorTribEffectsTriggeredThisCombat = 0;
		warriorTribEffectsPerCombat = 1;
		toonVuln = 1;
		machineArt = 1;
		rockBlock = 2;
		zombieResummonBlock = 5;
		spellcasterBlockOnAttack = 4;
		explosiveDmgLow = explosiveDamageLowDefault;
		explosiveDmgHigh = explosiveDamageHighDefault;
		insectPoisonDmg = baseInsectPoison;
		naturiaVines = 1;
		naturiaLeaves = 1;
		AbstractPlayer.customMods = new ArrayList<>();
		defaultMaxSummons = 5;
		lastMaxSummons = 5;
		currentZombieSouls = 0;
		defaultStartZombieSouls = 3;
		swordsPlayed = 0;
		hasUpgradeBuffRelic = false;
		hasShopBuffRelic = false;
		hadFrozenEye = false;
		gotFrozenEyeFromBigEye = false;
		gotWisemanHaunted = false;
		spellcasterRandomOrbsChanneled = 0;
		currentSpellcasterOrbChance = 25;
		lastCardPlayed = new CancelCard();
		secondLastCardPlayed = new CancelCard();
		lastPlantPlayed = new CancelCard();
		secondLastPlantPlayed = new CancelCard();
		lastGhostrickPlayed = new CancelCard();
		secondLastGhostrickPlayed = new CancelCard();
		warriorTribThisCombat = false;
		vampiresPlayed = 0;
		vendreadPlayed = 0;
		ghostrickPlayed = 0;
		mayakashiPlayed = 0;
		shiranuiPlayed = 0;
		corpsesEntombed = 0;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt(PROP_RESUMMON_DMG, 1);
			config.setBool(PROP_WISEMAN, gotWisemanHaunted);
			config.setInt("defaultMaxSummons", defaultMaxSummons);
			config.setString("fullCardPool", "~");
			config.setInt("vampiresPlayed", vampiresPlayed);
			config.setInt("vendreadPlayed", vendreadPlayed);
			config.setInt("ghostrickPlayed", ghostrickPlayed);
			config.setInt("mayakashiPlayed", mayakashiPlayed);
			config.setInt("shiranuiPlayed", shiranuiPlayed);
			config.setInt("corpsesEntombed", corpsesEntombed);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void entombBattleStartHandler()
	{
		entombedCardsCombat.clear();
		for (AbstractCard c : entombedCards) 
		{ 
			if (c instanceof CustomResummonCard)
			{
				entombedCardsCombat.add(c.makeStatEquivalentCopy());
				//Util.log("Adding " + c.cardID + " to Entombed cards in combat (CRC)");
			}
			else if (!(c instanceof ZombieCorpse))
			{
				entombedCardsCombat.add(c.makeStatEquivalentCopy()); 
				//Util.log("Adding " + c.cardID + " to Entombed cards in combat");
			}			
		}
		for (int i = 0; i < corpsesEntombed; i++) {
			entombedCardsCombat.add(new ZombieCorpse());
			//Util.log("Adding Zombie Corpse to Entombed cards in combat");
		}
		
		if (entombedCards.size() < entombedCardsCombat.size() + corpsesEntombed) {
			for (int i = 0; i < corpsesEntombed; i++) {
				entombedCards.add(new ZombieCorpse());
				//Util.log("Adding Zombie Corpse to Entombed cards, due to the Global Entomb list size being < the Combat Entomb list size + number of corpses Entombed");
			}
		}
	}


	@Override
	public void receiveRender(SpriteBatch spriteBatch) {

	}

	@Override
	public void receivePostRender(SpriteBatch spriteBatch) {

	}

	@Override
	public void receiveCameraRender(OrthographicCamera orthographicCamera) {

	}
}
