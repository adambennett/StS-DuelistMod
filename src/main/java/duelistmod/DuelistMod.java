package duelistmod;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import duelistmod.characters.DuelistCharacterSelect;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.dto.LoadoutUnlockOrderInfo;
import duelistmod.dto.OrbConfigData;
import duelistmod.dto.PotionConfigData;
import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.RelicConfigData;
import duelistmod.enums.*;
import duelistmod.helpers.customConsole.CustomConsoleCommandHelper;
import duelistmod.metrics.*;
import duelistmod.metrics.tierScoreDTO.ActScore;
import duelistmod.metrics.tierScoreDTO.CardScore;
import duelistmod.metrics.tierScoreDTO.CardTierScores;
import duelistmod.metrics.tierScoreDTO.PoolScore;
import duelistmod.stances.Chaotic;
import duelistmod.stances.Entrenched;
import duelistmod.stances.Forsaken;
import duelistmod.stances.Guarded;
import duelistmod.stances.Meditative;
import duelistmod.stances.Nimble;
import duelistmod.stances.Samurai;
import duelistmod.stances.Spectral;
import duelistmod.stances.Unstable;
import duelistmod.ui.*;
import duelistmod.ui.GenericCancelButton;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistModPanel;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.ConfigMenuPage;
import duelistmod.ui.configMenu.DuelistPaginator;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.ui.configMenu.pages.CardConfigs;
import duelistmod.ui.configMenu.pages.DeckUnlock;
import duelistmod.ui.configMenu.pages.EventConfigs;
import duelistmod.ui.configMenu.pages.Gameplay;
import duelistmod.ui.configMenu.pages.General;
import duelistmod.ui.configMenu.pages.CardPool;
import duelistmod.ui.configMenu.pages.Metrics;
import duelistmod.ui.configMenu.pages.MonsterType;
import duelistmod.ui.configMenu.pages.OrbConfigs;
import duelistmod.ui.configMenu.pages.PotionConfigs;
import duelistmod.ui.configMenu.pages.PuzzleConfigs;
import duelistmod.ui.configMenu.pages.Randomized;
import duelistmod.ui.configMenu.pages.RelicConfigs;
import duelistmod.ui.configMenu.pages.StanceConfigs;
import duelistmod.ui.configMenu.pages.Visual;
import duelistmod.ui.gameOver.DuelistDeathScreen;
import duelistmod.ui.gameOver.DuelistVictoryScreen;
import duelistmod.variables.Colors;
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
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
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
import org.apache.logging.log4j.core.util.UuidUtil;

@SuppressWarnings("CommentedOutCode")
@SpireInitializer
public class DuelistMod 
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber,
PreMonsterTurnSubscriber, PostDungeonUpdateSubscriber, StartActSubscriber, PostObtainCardSubscriber, PotionGetSubscriber, StartGameSubscriber,
PostUpdateSubscriber, RenderSubscriber, PostRenderSubscriber, PreRenderSubscriber, PreUpdateSubscriber
{
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());
	
	// Member fields
	public static String version = "v3.481.20";
	public static Mode modMode = Mode.NIGHTLY;
	public static String trueVersion = version.substring(1);
	public static String nightlyBuildNum = "v3.481.20.3";
	private static String modName = "Duelist Mod";
	private static String modAuthor = "Nyoxide";
	private static String modDescription = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	public static final ArrayList<String> cardSets = new ArrayList<>();
	private static ArrayList<IncrementDiscardSubscriber> incrementDiscardSubscribers;

	// Global Fields
	// Config Settings
	public static DuelistVictoryScreen victoryScreen;
	public static DuelistDeathScreen deathScreen;
	public static BonusDeckUnlockHelper bonusUnlockHelper;
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
	public static final String PROP_DECK_UNLOCK_RATE = "deckUnlockRate";
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
	public static final String PROP_RUN_UUID = "runUUID";
	public static final String PROP_FORCE_PUZZLE = "forcePuzzleSummons";
	public static final String PROP_ALLOW_SPECIAL_SPARKS = "allowSpecialSparks";
	public static final String PROP_FORCE_SPECIAL_SPARKS = "forceSpecialSparks";
	public static final String PROP_SELECTED_SPARKS_STRATEGY = "sparksStrategy";
	public static final String PROP_ALLOW_BOOSTERS = "allowBoosters";
	public static final String PROP_ALWAYS_BOOSTERS = "alwaysBoosters";
	public static final String PROP_REMOVE_CARD_REWARDS = "removeCardRewards";
	public static final String PROP_SMALL_BASIC = "smallBasicSet";
	public static final String PROP_DUELIST_MONSTERS = "duelistMonsters";
	public static final String PROP_CELEBRATE_HOLIDAYS = "celebrateHolidays";
	public static final String PROP_DUELIST_CURSES = "duelistCurses";
	public static final String PROP_CARD_REWARD_RELIC = "hasCardRewardRelic";
	public static final String PROP_BOOSTER_REWARD_RELIC = "hasBoosterRewardRelic";
	public static final String PROP_ADD_ORB_POTIONS = "addOrbPotions";
	public static final String PROP_ADD_ACT_FOUR = "actFourEnabled";
	public static final String PROP_PLAY_KAIBA = "playAsKaiba";
	public static final String PROP_MONSTER_IS_KAIBA = "monsterIsKaiba";
	public static final String PROP_SAVE_SLOT_A = "saveSlotA";	
	public static final String PROP_SAVE_SLOT_B = "saveSlotB";	
	public static final String PROP_SAVE_SLOT_C = "saveSlotC";	
	public static final String PROP_ALLOW_CARD_POOL_RELICS = "allowCardPoolRelics";
	public static final String PROP_MONSTERS_RUN = "loadedUniqueMonstersThisRunList";	
	public static final String PROP_SPELLS_RUN = "loadedSpellsThisRunList";	
	public static final String PROP_TRAPS_RUN = "loadedTrapsThisRunList";
	public static final String PROP_WEB_BUTTONS = "webButtons";
	public static final String PROP_TIER_SCORES_ENABLED = "tierScoresEnabled";
	public static final String PROP_LAST_TIME_TIER_SCORES_CHECKED = "lastTimeTierScoresChecked";
	public static final String PROP_TIER_SCORE_CACHE = "tierScoreCache";
	public static final String PROP_MODULE_CACHE = "moduleCache";
	public static final String PROP_RESTRICT_SUMMONING = "restrictSummoningZones";
	public static final String PROP_SELECTED_CHARACTER_MODEL = "selectedCharacterModel";
	public static final String PROP_REPLACE_COMMON_KEYWORDS_WITH_ICON = "replaceCommonKeywordsWithIcon";
	public static final String PROP_METRICS_UUID = "guid";
	public static final String PROP_HIDE_UNLOCK_ALL_DECKS_BTN = "hideUnlockAllDecksButton";
	public static final String PROP_RAIGEKI_ALWAYS_STUN = "raigekiAlwaysStuns";
	public static final String PROP_RAIGEKI_ALWAYS_STUN_UPGRADED = "raigekiAlwaysStunsUpgraded";
	public static final String PROP_RAIGEKI_INCLUDE_MAGIC = "raigekiIncludeMagicNumber";
	public static final String PROP_RAIGEKI_BONUS_PERCENTAGE_INDEX = "raigekiBonusPercentageIndex";
	public static final String PROP_RAIGEKI_BONUS_UPGRADE_PERCENTAGE_INDEX = "raigekiBonusUpgradePercentageIndex";
	public static final String PROP_RAIGEKI_BONUS_DAMAGE = "raigekiBonusDamage";
	public static CharacterModel selectedCharacterModel = CharacterModel.ANIM_YUGI;
	public static SpecialSparksStrategy selectedSparksStrategy = SpecialSparksStrategy.RANDOM_WEIGHTED;
	public static String selectedCharacterModelAnimationName = "animation";
	public static String characterModel = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String yugiChar = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String oldYugiChar = "duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml";
	public static final String kaibaPlayerModel = "duelistModResources/images/char/duelistCharacterUpdate/KaibaPlayer.scml";
	public static String kaibaEnemyModel = "KaibaModel2";
	public static Properties duelistDefaults = new Properties();
	public static boolean raigekiAlwaysStun = false;
	public static boolean raigekiAlwaysStunUpgrade = false;
	public static boolean raigekiIncludeMagic = true;
	public static boolean allowLocaleUpload = true;
	public static boolean toonBtnBool = false;
	public static boolean exodiaBtnBool = false;
	public static boolean ojamaBtnBool = false;
	public static boolean creatorBtnBool = false;
	public static boolean oldCharacter = false;
	public static boolean playAsKaiba = false;
	public static boolean restrictSummonZones = false;
	public static boolean isReplaceCommonKeywordsWithIcons = false;
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
	public static boolean forcePuzzleSummons = false;
	public static boolean allowSpecialSparks = true;
	public static boolean forceSpecialSparks = false;
	public static boolean allowBoosters = false;
	public static boolean alwaysBoosters = false;
	public static boolean removeCardRewards = false;
	public static boolean smallBasicSet = false;
	public static boolean duelistMonsters = false;
	public static boolean holidayCardsEnabled = false;
	public static boolean duelistCurses = false;
	public static boolean quicktimeEventsAllowed = false;
	public static boolean addOrbPotions = false;
	public static boolean monsterIsKaiba = true;
	public static boolean playingChallenge = false;
	public static boolean playedVampireThisTurn = false;
	public static boolean badBoosterSituation = false;
	public static String metricsUUID = null;
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
	public static String lastTimeTierScoreChecked;
	public static String runUUID = null;
	
	// Maps and Lists
	public static final HashMap<Integer, Texture> characterPortraits = new HashMap<>();
	public static final HashMap<String, Integer> boostersOpenedThisRun = new HashMap<>();
	public static final HashMap<String, Integer> boostersOpenedThisAct = new HashMap<>();
	public static HashMap<String, DuelistCard> summonMap = new HashMap<>();
	public static HashMap<String, DuelistCard> cardIdMap = new HashMap<>();
	public static HashMap<String, AbstractPower> buffMap = new HashMap<>();
	public static HashMap<String, AbstractOrb> invertStringMap = new HashMap<>();
	public static HashMap<String, StarterDeck> starterDeckNamesMap = new HashMap<>();
	public static HashMap<String, CardTags> typeCardMap_NameToString = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_ID = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_IMG = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_NAME = new HashMap<>();
	public static HashMap<CardTags, String> typeCardMap_DESC = new HashMap<>();
	public static HashMap<CardTags, Integer> monsterTypeTributeSynergyFunctionMap = new HashMap<>();
	public static HashMap<String, PotionConfigData> potionCanSpawnConfigMap = new HashMap<>();
	public static HashMap<String, RelicConfigData> relicCanSpawnConfigMap = new HashMap<>();
	public static HashMap<String, OrbConfigData> orbConfigSettingsMap = new HashMap<>();
	public static HashMap<String, EventConfigData> eventConfigSettingsMap = new HashMap<>();
	public static HashMap<String, PuzzleConfigData> puzzleConfigSettingsMap = new HashMap<>();
	public static Map<String, DuelistCard> orbCardMap = new HashMap<>();
	public static Map<CardTags, StarterDeck> deckTagMap = new HashMap<>();
	public static Map<String, AbstractCard> mapForCardPoolSave = new HashMap<>();
	public static Map<String, AbstractCard> mapForRunCardsLoading = new HashMap<>();
	public static Map<String, AbstractCard> uniqueSpellsThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueMonstersThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueTrapsThisRunMap = new HashMap<>();
	public static Map<String, AbstractPotion> duelistPotionMap = new HashMap<>();
	public static Map<String, String> magicNumberCards = new HashMap<>();
	public static Map<String, Integer> summonCards = new HashMap<>();
	public static ArrayList<String> summonCardNames = new ArrayList<>();
	public static ArrayList<String> monsterAndTokenCardNames = new ArrayList<>();
	public static ArrayList<String> nonExemptCardNames = new ArrayList<>();
	public static Map<String, Integer> tributeCards = new HashMap<>();
	public static Map<String, String> dungeonCardPool = new HashMap<>();
	public static Map<String, String> totallyRandomCardMap = new HashMap<>();

	public static CardTierScores cardTierScores;
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
	public static final ArrayList<AbstractCard> currentlyHaunted = new ArrayList<>();
	public static final Color hauntedGlowColor = Color.PURPLE;
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<>();
	public static ArrayList<AbstractPotion> allDuelistPotions = new ArrayList<>();
	public static ArrayList<AbstractRelic> duelistRelicsForTombEvent = new ArrayList<>();
	public static ArrayList<DuelistRelic> allDuelistRelics = new ArrayList<>();
	public static ArrayList<String> allDuelistRelicIds = new ArrayList<>();
	public static ArrayList<DuelistPotion> allDuelistPotionsForOutput = new ArrayList<>();
	public static ArrayList<String> allDuelistPotionIds = new ArrayList<>();
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
	public static ArrayList<RefreshablePage> refreshablePages = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> cardConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> orbConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> relicConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> stanceConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> potionConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> eventConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> puzzleConfigurations = new ArrayList<>();
	public static Map<String, Map<String, List<String>>> relicAndPotionByDeckData = new HashMap<>();
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
	public static boolean shouldReplacePool = false;
	public static boolean replacingOnUpdate = false;
	public static boolean replacedCardPool = false;
	public static boolean relicReplacement = false;
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
	public static boolean webButtonsEnabled = true;
	public static boolean tierScoresEnabled = true;
	public static boolean hideUnlockAllDecksButtonInCharacterSelect = false;
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
	public static boolean openedModSettings = false;
	public static boolean dragonEffectRanThisCombat = false;
	public static boolean tokensPurgeAtEndOfTurn = true;
	public static boolean bugEffectResets = false;
	public static boolean spiderEffectResets = false;
	public static boolean vampiresPlayEffect = true;
	public static boolean mayakashiPlayEffect = true;
	public static boolean vendreadPlayEffect = true;
	public static boolean shiranuiPlayEffect = true;
	public static boolean ghostrickPlayEffect = true;
	public static boolean randomMagnetAddedToDeck = false;
	public static boolean allowRandomSuperMagnets = false;
	public static boolean disableAllCommonPotions = false;
	public static boolean disableAllUncommonPotions = false;
	public static boolean disableAllRarePotions = false;
	public static boolean disableAllCommonRelics = false;
	public static boolean disableAllUncommonRelics = false;
	public static boolean disableAllRareRelics = false;
	public static boolean disableAllShopRelics = false;
	public static boolean disableAllBossRelics = false;
	public static boolean enableWarriorTributeEffect = true;
	public static boolean disableAllOrbPassives = false;
	public static boolean disableAllOrbEvokes = false;
	public static boolean disableNamelessTombCards = false;
	public static boolean dragonRemoveEffects = true;

	// Numbers
	public static int duelistScore = 0;
	public static int trueDuelistScore = 0;
	public static int trueVersionScore = 0;
	public static int randomDeckSmallSize = 10;
	public static int randomDeckBigSize = 15;
	public static int cardCount = 75;
	public static int setIndex = 0;
	public static int lowNoBuffs = 3;
	public static int highNoBuffs = 6;
	public static int lastMaxSummons = 5;
	public static int defaultMaxSummons = 5;
	public static int tribCombatCount = 0;
	public static int tribRunCount = 0;
	public static int spellCombatCount = 0;
	public static int summonCombatCount = 0;
	public static int summonTurnCount = 0;
	public static int summonRunCount = 0;
	public static int swordsPlayed = 0;
	public static int deckIndex = 0;
	public static int normalSelectDeck = -1;
	public static int dragonStr = 2;
	public static int toonVuln = 1;
	public static int zombieSouls = 1;
	public static int insectPoisonDmg = 1;
	public static int plantConstricted = 1;
	public static int predaplantThorns = 1;	
	public static int fiendDraw = 1;
	public static int aquaInc = 1;
	public static int superheavyDex = 1;
	public static int naturiaVinesDmgMod = 0;
	public static int naturiaLeavesNeeded = 5;
	public static int machineArt = 1;
	public static int rockBlock = 2;
	public static int archRoll1 = -1;
	public static int archRoll2 = -1;	
	public static int bugTempHP = 5;
	public static int bugsPlayedThisCombat = 0;
	public static int bugsToPlayForTempHp = 2;
	public static int spiderTempHP = 7;
	public static int spidersPlayedThisCombat = 0;
	public static int spidersToPlayForTempHp = 3;
	public static int gravAxeStr = -99;
	public static int poisonAppliedThisCombat = 0;
	public static int zombiesResummonedThisCombat = 0;
	public static int zombiesResummonedThisRun = 0;
	public static int explosiveDmgLow = 2;
	public static int explosiveDmgHigh = 6;
	public static int superExplodeMultiplierLow = 3;
	public static int superExplodeMultiplierHigh = 4;
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
	public static int megatypeTributesThisRun = 0;
	public static int warriorSynergyTributeNeededToTrigger = 1;
	public static int warriorSynergyTributesThisCombat = 0;
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
	public static int vampiresNeedPlayed = 9;
	public static int mayakashiNeedPlayed = 2;
	public static int vendreadNeedPlayed = 4;
	public static int shiranuiNeedPlayed = 4;
	public static int ghostrickNeedPlayed = 9;
	public static int deckUnlockRateIndex = 0;
	public static int raigekiBonusUpgradeIndex = 0;
	public static int raigekiBonusIndex = 0;
	public static int raigekiBonusDamage = 0;
	public static int dragonScalesSelectorIndex = 4;
	public static int dragonScalesModIndex = 1;
	public static int vinesSelectorIndex = 0;
	public static int leavesSelectorIndex = 0;
	public static int warriorTributeEffectTriggersPerCombat = 1;
	public static int warriorTributeEffectTriggersThisCombat = 0;

	// Other
	public static TheDuelist duelistChar;
	public static StartingDecks currentDeck;
	public static DuelistCardSelectScreen duelistCardSelectScreen;
	public static DuelistCardViewScreen duelistCardViewScreen;
	public static DuelistMasterCardViewScreen duelistMasterCardViewScreen;
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
	public static BoosterPack currentReward;
	public static CharacterSelectScreen characterSelectScreen;
	public static DeckUnlockRate currentUnlockRate = DeckUnlockRate.NORMAL;
	public static Percentages raigekiBonusPercentage = Percentages.ZERO;
	public static Percentages raigekiBonusUpgradePercentage = Percentages.ZERO;
	public static GenericCancelButton configCancelButton;
	public static ChallengeIcon topPanelChallengeIcon;
	public static ConfigOpenSource lastSource = ConfigOpenSource.BASE_MOD;
	public static VinesLeavesMods vinesOption;
	public static VinesLeavesMods leavesOption;
	
	// Config Menu
	public static float yPos = 760.0f;
	public static final float startingYPos = yPos;
	public static float xLabPos = 360.0f;
	public static float xSecondCol = 490.0f;
	public static float xThirdCol = 475.0f;
	public static final String rightArrow = "duelistModResources/images/ui/tinyRightArrow.png";
	public static final String leftArrow = "duelistModResources/images/ui/tinyLeftArrow.png";
	public static DuelistPaginator paginator;
	public static UIStrings Config_UI_String;
	public static DuelistModPanel settingsPanel;
	public static DuelistDropdown daySelector;
	public static DuelistDropdown openDropdown;


	// Global Character Stats
	public static int energyPerTurn = 3;
	public static int startHP = 80;
	public static int maxHP = 80;
	public static int startGold = 125;
	public static int cardDraw = 5;
	public static int orbSlots = 3;
	
	
	// Turn off for Workshop releases, just prints out stuff and adds debug cards/tokens to game
	public static boolean debug = true;									// print statements only, used in mod option panel
	public static boolean debugMsg = false;								// for secret msg
	public static final boolean addTokens = modMode == Mode.DEV;		// adds debug tokens to library
	public static boolean allowBonusDeckUnlocks = true;					// turn bonus deck unlocks (Ascended/Pharaoh Decks) on
	public static boolean allowChallengeMode = true;					// turn challenge mode options on the selection screen on/off

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

		String potConfigMapStr = "";
		String relicConfigMapStr = "";
		String orbConfigMapStr = "";
		String eventConfigMapStr = "";
		String puzzleConfigMapStr = "";
		try {
			potConfigMapStr = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(potionCanSpawnConfigMap);
			relicConfigMapStr = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(relicCanSpawnConfigMap);
			orbConfigMapStr = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(orbConfigSettingsMap);
			eventConfigMapStr = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(eventConfigSettingsMap);
			puzzleConfigMapStr = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(puzzleConfigSettingsMap);
		} catch (Exception ex) {
			Util.logError("Error writing potCanSpawnConfigMap JSON to string", ex);
		}

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
		duelistDefaults.setProperty(PROP_FORCE_PUZZLE, "FALSE");
		duelistDefaults.setProperty(PROP_ALLOW_BOOSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_ALWAYS_BOOSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_REMOVE_CARD_REWARDS, "TRUE");
		duelistDefaults.setProperty(PROP_SMALL_BASIC, "FALSE");
		duelistDefaults.setProperty(PROP_DUELIST_MONSTERS, "TRUE");
		duelistDefaults.setProperty(PROP_DUELIST_CURSES, "TRUE");
		duelistDefaults.setProperty(PROP_CARD_REWARD_RELIC, "FALSE");
		duelistDefaults.setProperty(PROP_BOOSTER_REWARD_RELIC, "FALSE");
		duelistDefaults.setProperty(PROP_ADD_ORB_POTIONS, "TRUE");
		duelistDefaults.setProperty(PROP_ADD_ACT_FOUR, "TRUE");
		duelistDefaults.setProperty(PROP_PLAY_KAIBA, "FALSE");
		duelistDefaults.setProperty(PROP_MONSTER_IS_KAIBA, "TRUE");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_A, "");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_B, "");
		duelistDefaults.setProperty(PROP_SAVE_SLOT_C, "");
		duelistDefaults.setProperty(PROP_ALLOW_CARD_POOL_RELICS, "TRUE");
		duelistDefaults.setProperty(PROP_MONSTERS_RUN, "");
		duelistDefaults.setProperty(PROP_SPELLS_RUN, "");
		duelistDefaults.setProperty(PROP_TRAPS_RUN, "");
		duelistDefaults.setProperty(PROP_WEB_BUTTONS, "TRUE");
		duelistDefaults.setProperty(PROP_TIER_SCORES_ENABLED, "TRUE");
		duelistDefaults.setProperty(PROP_LAST_TIME_TIER_SCORES_CHECKED, "NEVER");
		duelistDefaults.setProperty(PROP_TIER_SCORE_CACHE, "");
		duelistDefaults.setProperty(PROP_MODULE_CACHE, "");
		duelistDefaults.setProperty(PROP_SELECTED_CHARACTER_MODEL, "0");
		duelistDefaults.setProperty(PROP_RESTRICT_SUMMONING, "FALSE");
		duelistDefaults.setProperty(PROP_REPLACE_COMMON_KEYWORDS_WITH_ICON, "FALSE");
		duelistDefaults.setProperty(PROP_CELEBRATE_HOLIDAYS, "TRUE");
		duelistDefaults.setProperty(PROP_SELECTED_SPARKS_STRATEGY, "0");
		duelistDefaults.setProperty(PROP_ALLOW_SPECIAL_SPARKS, "TRUE");
		duelistDefaults.setProperty(PROP_FORCE_SPECIAL_SPARKS, "FALSE");
		duelistDefaults.setProperty(PROP_HIDE_UNLOCK_ALL_DECKS_BTN, "false");
		duelistDefaults.setProperty(PROP_DECK_UNLOCK_RATE, "0");
		duelistDefaults.setProperty(PROP_RAIGEKI_ALWAYS_STUN, "FALSE");
		duelistDefaults.setProperty(PROP_RAIGEKI_ALWAYS_STUN_UPGRADED, "FALSE");
		duelistDefaults.setProperty(PROP_RAIGEKI_INCLUDE_MAGIC, "TRUE");
		duelistDefaults.setProperty(PROP_RAIGEKI_BONUS_PERCENTAGE_INDEX, "0");
		duelistDefaults.setProperty(PROP_RAIGEKI_BONUS_UPGRADE_PERCENTAGE_INDEX, "0");
		duelistDefaults.setProperty(PROP_RAIGEKI_BONUS_DAMAGE, "0");
		duelistDefaults.setProperty("dragonScalesSelectorIndex", "6");
		duelistDefaults.setProperty("dragonScalesModIndex", "1");
		duelistDefaults.setProperty("vinesSelectorIndex", "0");
		duelistDefaults.setProperty("leavesSelectorIndex", "0");
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
		duelistDefaults.setProperty("duelistScore", "0");
		duelistDefaults.setProperty("trueDuelistScore", "0");
		duelistDefaults.setProperty("trueDuelistScore"+trueVersion, "0");
		duelistDefaults.setProperty("explosiveDmgLow", "2");
		duelistDefaults.setProperty("explosiveDmgHigh", "6");
		duelistDefaults.setProperty("superExplodeMultiplierLow", "3");
		duelistDefaults.setProperty("superExplodeMultiplierHigh", "4");
		duelistDefaults.setProperty("souls", "0");
		duelistDefaults.setProperty("startSouls", "3");
		duelistDefaults.setProperty("dragonStr", "2");
		duelistDefaults.setProperty("toonVuln", "1");
		duelistDefaults.setProperty("zombieSouls", "1");
		duelistDefaults.setProperty("insectPoisonDmg", "1");
		duelistDefaults.setProperty("plantConstricted", "1");
		duelistDefaults.setProperty("predaplantThorns", "1");
		duelistDefaults.setProperty("fiendDraw", "1");
		duelistDefaults.setProperty("aquaInc", "1");
		duelistDefaults.setProperty("superheavyDex", "1");
		duelistDefaults.setProperty("naturiaVinesDmgMod", "0");
		duelistDefaults.setProperty("disableAllCommonPotions", "FALSE");
		duelistDefaults.setProperty("disableAllUncommonPotions", "FALSE");
		duelistDefaults.setProperty("disableAllRarePotions", "FALSE");
		duelistDefaults.setProperty("disableAllCommonRelics", "FALSE");
		duelistDefaults.setProperty("disableAllUncommonRelics", "FALSE");
		duelistDefaults.setProperty("disableAllRareRelics", "FALSE");
		duelistDefaults.setProperty("disableAllShopRelics", "FALSE");
		duelistDefaults.setProperty("disableAllBossRelics", "FALSE");
		duelistDefaults.setProperty("warriorTributeEffectTriggersPerCombat", "1");
		duelistDefaults.setProperty("warriorSynergyTributeNeededToTrigger", "1");
		duelistDefaults.setProperty("enableWarriorTributeEffect", "TRUE");
		duelistDefaults.setProperty("disableAllOrbPassives", "FALSE");
		duelistDefaults.setProperty("disableAllOrbEvokes", "FALSE");
		duelistDefaults.setProperty("disableNamelessTombCards", "FALSE");
		duelistDefaults.setProperty("potionCanSpawnConfigMap", potConfigMapStr);
		duelistDefaults.setProperty("relicCanSpawnConfigMap", relicConfigMapStr);
		duelistDefaults.setProperty("orbConfigSettingsMap", orbConfigMapStr);
		duelistDefaults.setProperty("eventConfigSettingsMap", eventConfigMapStr);
		duelistDefaults.setProperty("puzzleConfigSettingsMap", puzzleConfigMapStr);
		duelistDefaults.setProperty("naturiaLeavesNeeded", "5");
		duelistDefaults.setProperty("randomMagnetAddedToDeck", "FALSE");
		duelistDefaults.setProperty("allowRandomSuperMagnets", "FALSE");
		duelistDefaults.setProperty("machineArt", "1");
		duelistDefaults.setProperty("dragonDeckPuzzleEffectsToChoose", "1");
		duelistDefaults.setProperty("rockBlock", "2");
		duelistDefaults.setProperty("bugTempHP", "5");
		duelistDefaults.setProperty("bugsToPlayForTempHp", "2");
		duelistDefaults.setProperty("spiderTempHP", "7");
		duelistDefaults.setProperty("spidersToPlayForTempHp", "3");
		duelistDefaults.setProperty("spellcasterBlockOnAttack", "4");
		duelistDefaults.setProperty("bugEffectResets", "FALSE");
		duelistDefaults.setProperty("spiderEffectResets", "FALSE");
		duelistDefaults.setProperty("tokensPurgeAtEndOfTurn", "TRUE");
		duelistDefaults.setProperty("vampiresNeedPlayed", "10");
		duelistDefaults.setProperty("mayakashiNeedPlayed", "3");
		duelistDefaults.setProperty("vendreadNeedPlayed", "5");
		duelistDefaults.setProperty("shiranuiNeedPlayed", "5");
		duelistDefaults.setProperty("ghostrickNeedPlayed", "10");
		duelistDefaults.setProperty("vampiresPlayEffect", "true");
		duelistDefaults.setProperty("mayakashiPlayEffect", "true");
		duelistDefaults.setProperty("vendreadPlayEffect", "true");
		duelistDefaults.setProperty("shiranuiPlayEffect", "true");
		duelistDefaults.setProperty("ghostrickPlayEffect", "true");
		duelistDefaults.setProperty("poolIsCustomized", "FALSE");

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
            baseGameCards = config.getBool(PROP_BASE_GAME_CARDS);
            forcePuzzleSummons = config.getBool(PROP_FORCE_PUZZLE);
            allowBoosters = config.getBool(PROP_ALLOW_BOOSTERS);
            alwaysBoosters = config.getBool(PROP_ALWAYS_BOOSTERS);
            removeCardRewards = config.getBool(PROP_REMOVE_CARD_REWARDS);
            smallBasicSet = config.getBool(PROP_SMALL_BASIC);
            duelistMonsters = config.getBool(PROP_DUELIST_MONSTERS);
            duelistCurses = config.getBool(PROP_DUELIST_CURSES);
            addOrbPotions = config.getBool(PROP_ADD_ORB_POTIONS);
            quicktimeEventsAllowed = config.getBool("quicktimeEventsAllowed");
            playAsKaiba = config.getBool(PROP_PLAY_KAIBA);
            monsterIsKaiba = config.getBool(PROP_MONSTER_IS_KAIBA);
            saveSlotA = config.getString(PROP_SAVE_SLOT_A);
            saveSlotB = config.getString(PROP_SAVE_SLOT_B);
            saveSlotC = config.getString(PROP_SAVE_SLOT_C);
            loadedUniqueMonstersThisRunList = config.getString(PROP_MONSTERS_RUN);
            loadedSpellsThisRunList = config.getString(PROP_SPELLS_RUN);
            loadedTrapsThisRunList = config.getString(PROP_TRAPS_RUN);
            entombedCardsThisRunList = config.getString("entombed");
            allowCardPoolRelics = config.getBool(PROP_ALLOW_CARD_POOL_RELICS);
            defaultMaxSummons = config.getInt("defaultMaxSummons");
            allowDuelistEvents = config.getBool("allowDuelistEvents");
            birthdayMonth = config.getInt("birthdayMonth");
            birthdayDay = config.getInt("birthdayDay");
            neverChangedBirthday = config.getBool("neverChangedBirthday");
			explosiveDmgLow = config.getInt("explosiveDmgLow");
			explosiveDmgHigh = config.getInt("explosiveDmgHigh");
			superExplodeMultiplierLow = config.getInt("superExplodeMultiplierLow");
			superExplodeMultiplierHigh = config.getInt("superExplodeMultiplierHigh");
			currentZombieSouls = config.getInt("souls");
			defaultStartZombieSouls = config.getInt("startSouls");
            entombedCustomCardProperites = config.getString("entombedCustomCardProperites");
            corpsesEntombed = config.getInt("corpsesEntombed");
        	playingChallenge = config.getBool("playingChallenge");
        	challengeLevel = config.getInt("currentChallengeLevel");
        	allowLocaleUpload = config.getBool("allowLocaleUpload");
			webButtonsEnabled = config.getBool(PROP_WEB_BUTTONS);
			tierScoresEnabled = config.getBool(PROP_TIER_SCORES_ENABLED);
			lastTimeTierScoreChecked = config.getString(PROP_LAST_TIME_TIER_SCORES_CHECKED);
			restrictSummonZones = config.getBool(PROP_RESTRICT_SUMMONING);
			isReplaceCommonKeywordsWithIcons = config.getBool(PROP_REPLACE_COMMON_KEYWORDS_WITH_ICON);
			metricsUUID = config.getString(PROP_METRICS_UUID);
			holidayCardsEnabled = config.getBool(PROP_CELEBRATE_HOLIDAYS);
			allowSpecialSparks = config.getBool(PROP_ALLOW_SPECIAL_SPARKS);
			forceSpecialSparks = config.getBool(PROP_FORCE_SPECIAL_SPARKS);
			hideUnlockAllDecksButtonInCharacterSelect = config.getBool(PROP_HIDE_UNLOCK_ALL_DECKS_BTN);
			deckUnlockRateIndex = config.getInt(PROP_DECK_UNLOCK_RATE);
			currentUnlockRate = DeckUnlockRate.menuMapping.get(deckUnlockRateIndex);
			raigekiAlwaysStun = config.getBool(PROP_RAIGEKI_ALWAYS_STUN);
			raigekiAlwaysStunUpgrade = config.getBool(PROP_RAIGEKI_ALWAYS_STUN_UPGRADED);
			raigekiIncludeMagic = config.getBool(PROP_RAIGEKI_INCLUDE_MAGIC);
			raigekiBonusIndex = config.getInt(PROP_RAIGEKI_BONUS_PERCENTAGE_INDEX);
			raigekiBonusUpgradeIndex = config.getInt(PROP_RAIGEKI_BONUS_UPGRADE_PERCENTAGE_INDEX);
			raigekiBonusPercentage = Percentages.menuMapping.get(raigekiBonusIndex);
			raigekiBonusUpgradePercentage = Percentages.menuMapping.get(raigekiBonusUpgradeIndex);
			raigekiBonusDamage = config.getInt(PROP_RAIGEKI_BONUS_DAMAGE);
			dragonScalesModIndex = config.getInt("dragonScalesModIndex");
			dragonScalesSelectorIndex = config.getInt("dragonScalesSelectorIndex");
			vinesSelectorIndex = config.getInt("vinesSelectorIndex");
			leavesSelectorIndex = config.getInt("leavesSelectorIndex");
			vinesOption = MonsterType.vinesMenuMapping.get(vinesSelectorIndex);
			leavesOption = MonsterType.leavesMenuMapping.get(leavesSelectorIndex);
			naturiaVinesDmgMod = config.getInt("naturiaVinesDmgMod");
			naturiaLeavesNeeded = config.getInt("naturiaLeavesNeeded");
			disableAllCommonPotions = config.getBool("disableAllCommonPotions");
			disableAllUncommonPotions = config.getBool("disableAllUncommonPotions");
			disableAllRarePotions = config.getBool("disableAllRarePotions");
			disableAllCommonRelics = config.getBool("disableAllCommonRelics");
			disableAllUncommonRelics = config.getBool("disableAllUncommonRelics");
			disableAllRareRelics = config.getBool("disableAllRareRelics");
			disableAllShopRelics = config.getBool("disableAllShopRelics");
			disableAllBossRelics = config.getBool("disableAllBossRelics");
			enableWarriorTributeEffect = config.getBool("enableWarriorTributeEffect");
			disableAllOrbPassives = config.getBool("disableAllOrbPassives");
			disableAllOrbEvokes = config.getBool("disableAllOrbEvokes");
			disableNamelessTombCards = config.getBool("disableNamelessTombCards");
			warriorTributeEffectTriggersPerCombat = config.getInt("warriorTributeEffectTriggersPerCombat");
			warriorSynergyTributeNeededToTrigger = config.getInt("warriorSynergyTributeNeededToTrigger");
			randomMagnetAddedToDeck = config.getBool("randomMagnetAddedToDeck");
			allowRandomSuperMagnets = config.getBool("allowRandomSuperMagnets");
			predaplantThorns = config.getInt("predaplantThorns");
			rockBlock = config.getInt("rockBlock");
			dragonStr = config.getInt("dragonStr");
			aquaInc = config.getInt("aquaInc");
			fiendDraw = config.getInt("fiendDraw");
			machineArt = config.getInt("machineArt");
			toonVuln = config.getInt("toonVuln");
			zombieSouls = config.getInt("zombieSouls");
			insectPoisonDmg = config.getInt("insectPoisonDmg");
			superheavyDex = config.getInt("superheavyDex");
			plantConstricted = config.getInt("plantConstricted");
			bugTempHP = config.getInt("bugTempHP");
			bugsToPlayForTempHp = config.getInt("bugsToPlayForTempHp");
			spiderTempHP = config.getInt("spiderTempHP");
			spidersToPlayForTempHp = config.getInt("spidersToPlayForTempHp");
			spellcasterBlockOnAttack = config.getInt("spellcasterBlockOnAttack");
			tokensPurgeAtEndOfTurn = config.getBool("tokensPurgeAtEndOfTurn");
			vampiresNeedPlayed = config.getInt("vampiresNeedPlayed");
			mayakashiNeedPlayed = config.getInt("mayakashiNeedPlayed");
			vendreadNeedPlayed = config.getInt("vendreadNeedPlayed");
			shiranuiNeedPlayed = config.getInt("shiranuiNeedPlayed");
			ghostrickNeedPlayed = config.getInt("ghostrickNeedPlayed");
			vampiresPlayEffect = config.getBool("vampiresPlayEffect");
			mayakashiPlayEffect = config.getBool("mayakashiPlayEffect");
			vendreadPlayEffect = config.getBool("vendreadPlayEffect");
			shiranuiPlayEffect = config.getBool("shiranuiPlayEffect");
			ghostrickPlayEffect = config.getBool("ghostrickPlayEffect");
			poolIsCustomized = config.getBool("poolIsCustomized");
			MetricsHelper.setupUUID(config);

			int characterModelIndex = config.getInt(PROP_SELECTED_CHARACTER_MODEL);
			if (characterModelIndex > -1) {
				selectedCharacterModel = CharacterModel.menuMappingReverse.getOrDefault(characterModelIndex, CharacterModel.ANIM_YUGI);
			}

			int sparksStrategy = config.getInt(PROP_SELECTED_SPARKS_STRATEGY);
			if (sparksStrategy > -1) {
				selectedSparksStrategy = SpecialSparksStrategy.menuMappingReverse.getOrDefault(sparksStrategy, SpecialSparksStrategy.RANDOM_WEIGHTED);
			}

        	duelistScore = config.getInt("duelistScore");
			trueDuelistScore = config.getInt("trueDuelistScore");
			trueVersionScore = config.getInt("trueDuelistScore" + trueVersion);

			try {
				String potConfigMapJSON = config.getString("potionCanSpawnConfigMap");
				if (!potConfigMapJSON.equals("")) {
					potionCanSpawnConfigMap = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.readValue(potConfigMapJSON, new TypeReference<HashMap<String, PotionConfigData>>(){});
				}
			} catch (Exception ex) {
				Util.logError("Exception while loading Potion configurations", ex);
			}

			try {
				String relicConfigMapJSON = config.getString("relicCanSpawnConfigMap");
				if (!relicConfigMapJSON.equals("")) {
					relicCanSpawnConfigMap = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.readValue(relicConfigMapJSON, new TypeReference<HashMap<String, RelicConfigData>>(){});
				}
			} catch (Exception ex) {
				Util.logError("Exception while loading Relic configurations", ex);
			}

			try {
				String orbConfigMapJSON = config.getString("orbConfigSettingsMap");
				if (!orbConfigMapJSON.equals("")) {
					orbConfigSettingsMap = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.readValue(orbConfigMapJSON, new TypeReference<HashMap<String, OrbConfigData>>(){});
				}
			} catch (Exception ex) {
				Util.logError("Exception while loading Orb configurations", ex);
			}

			try {
				String eventConfigMapJSON = config.getString("eventConfigSettingsMap");
				if (!eventConfigMapJSON.equals("")) {
					eventConfigSettingsMap = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.readValue(eventConfigMapJSON, new TypeReference<HashMap<String, EventConfigData>>(){});
				}
			} catch (Exception ex) {
				Util.logError("Exception while loading Event configurations", ex);
			}


			try {
				String puzzleConfigMapJSON = config.getString("puzzleConfigSettingsMap");
				if (!puzzleConfigMapJSON.equals("")) {
					puzzleConfigSettingsMap = new ObjectMapper()
							.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
							.readValue(puzzleConfigMapJSON, new TypeReference<HashMap<String, PuzzleConfigData>>(){});
				}
			} catch (Exception ex) {
				Util.logError("Exception while loading aaa configurations", ex);
			}

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
		receiveEditStances();
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
		BaseMod.registerModBadge(badgeTexture, modName, modAuthor, modDescription, settingsPanel);
		combatIconViewer = new CombatIconViewer();
		bonusUnlockHelper = new BonusDeckUnlockHelper();
		isUnlockAllDecksButtonNeeded();
		receiveEditSounds();
		
		// Animated Cards
		if (isGifTheSpire) { new GifSpireHelper(); }
		
		// Events
		Util.addEventsToGame();
		configPanelSetup();

		// Monsters
		BaseMod.addMonster(DuelistEnemy.ID, "Seto Kaiba", DuelistEnemy::new);
		BaseMod.addMonster(DuelistEnemy.ID_YUGI, "Yugi Muto", DuelistEnemy::new);
		BaseMod.addMonster(SuperKaiba.ID, "Seto Kaiba (Event)", SuperKaiba::new);
		BaseMod.addMonster(SuperYugi.ID, "Yugi Muto (Event)", SuperYugi::new);

		// Custom Dev Console Commands
		CustomConsoleCommandHelper.setupCommands();

		// Encounters
		if (DuelistMod.duelistMonsters)
		{
			BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo(DuelistEnemy.ID, 4.0F));
		}
		
		// Rewards
		BaseMod.registerCustomReward(RewardItemTypeEnumPatch.DUELIST_PACK, (rewardSave) -> BoosterHelper.getPackFromSave(rewardSave.id), (customReward) -> new RewardSave(customReward.type.toString(), ((BoosterPack)customReward).packName));

		// Top Panel
		topPanelChallengeIcon = new ChallengeIcon();
		BaseMod.addTopPanelItem(topPanelChallengeIcon);

		// Custom Powers (for basemod console)
		Util.registerCustomPowers();

		int lastIndex = DuelistMod.deckIndex;
		relicAndPotionByDeckData = getRelicsAndPotionsForAllDecks();
		hardSetCurrentDeck(lastIndex);

		// Upload any untracked mod info to metrics server (card/relic/potion/creature/keyword data)
		ExportUploader.uploadInfoJSON();

		// Check tier scores
		Map<String, Map<String, Map<Integer, Integer>>> cardTierScores = MetricsHelper.getTierScores();
		Map<String, CardScore> pool = new HashMap<>();
		for (Map.Entry<String, Map<String, Map<Integer, Integer>>> entry : cardTierScores.entrySet()) {
			Map<String, ActScore> first = new HashMap<>();
			for (Map.Entry<String, Map<Integer, Integer>> entryA : entry.getValue().entrySet()) {
				ActScore score = new ActScore(entryA.getValue());
				first.put(entryA.getKey(), score);
			}
			CardScore sco = new CardScore(first);
			pool.put(entry.getKey(), sco);
		}
		DuelistMod.cardTierScores = new CardTierScores(new PoolScore(pool));
		duelistCardSelectScreen = new DuelistCardSelectScreen(false);
		duelistCardViewScreen = new DuelistCardViewScreen();
		duelistMasterCardViewScreen = new DuelistMasterCardViewScreen();
	}

	private static void isUnlockAllDecksButtonNeeded() {
		LoadoutUnlockOrderInfo deckUnlockCheck = DuelistCharacterSelect.getNextUnlockDeckAndScore(duelistScore);
		boolean needButton = !deckUnlockCheck.deck().equals("ALL DECKS UNLOCKED") ||
				!isAscendedDeckOneUnlocked ||
				!isAscendedDeckTwoUnlocked ||
				!isExtraRandomDecksUnlocked;
		hideUnlockAllDecksButtonInCharacterSelect = !needButton;
	}
	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		boolean configMapWasEmpty = potionCanSpawnConfigMap.isEmpty();
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
		for (AbstractPotion p : pots) {
			if (p instanceof DuelistPotion) {
				DuelistPotion dp = (DuelistPotion)p;
				allDuelistPotionsForOutput.add(dp);
				allDuelistPotionIds.add(p.ID);
				DuelistConfigurationData config = dp.getConfigurations();
				if (config != null) {
					potionConfigurations.add(config);
				}
				if (configMapWasEmpty) {
					potionCanSpawnConfigMap.put(p.ID, dp.getDefaultConfig());
				}
			}
			duelistPotionMap.put(p.ID, p); allDuelistPotions.add(p);BaseMod.addPotion(p.getClass(), Colors.WHITE, Colors.WHITE, Colors.WHITE, p.ID, TheDuelistEnum.THE_DUELIST);
		}
		pots.clear();

		pots.add(new BigOrbBottle());
		pots.add(new CoolBottle());
		pots.add(new DragonOrbBottle());
		pots.add(new DragonOrbPlusBottle());
		pots.add(new EarthBottle());
		// pots.add(new ExtraOrbsBottle());
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
		for (AbstractPotion p : pots) {
			if (p instanceof DuelistPotion) {
				DuelistPotion dp = (DuelistPotion)p;
				allDuelistPotionsForOutput.add(dp);
				allDuelistPotionIds.add(p.ID);
				DuelistConfigurationData config = dp.getConfigurations();
				if (config != null) {
					potionConfigurations.add(config);
				}
				if (configMapWasEmpty) {
					potionCanSpawnConfigMap.put(p.ID, dp.getDefaultConfig());
				}
			}
			duelistPotionMap.put(p.ID, p); orbPotionIDs.add(p.ID); allDuelistPotions.add(p);BaseMod.addPotion(p.getClass(), Colors.WHITE, Colors.WHITE, Colors.WHITE, p.ID, TheDuelistEnum.THE_DUELIST);
		}
		pots.clear();

		if (configMapWasEmpty) {
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				String potConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(potionCanSpawnConfigMap);
				config.setString("potionCanSpawnConfigMap", potConfigMap);
				config.save();
			} catch (Exception ex) { ex.printStackTrace(); }
		}
	}

	// ================ /ADD POTIONS/ ===================

	public void receiveEditStances() {
		ArrayList<DuelistStance> stances = new ArrayList<>();
		stances.add(new Chaotic());
		stances.add(new Entrenched());
		stances.add(new Forsaken());
		stances.add(new Guarded());
		stances.add(new Meditative());
		stances.add(new Nimble());
		stances.add(new Samurai());
		stances.add(new Spectral());
		stances.add(new Unstable());
		for (DuelistStance s : stances) {
			DuelistConfigurationData config = s.getConfigurations();
			if (config != null) {
				stanceConfigurations.add(config);
			}
		}
	}

	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		ArrayList<AbstractRelic> allRelics = new ArrayList<>();
		boolean configMapWasEmpty = relicCanSpawnConfigMap.isEmpty();
		
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
		for (AbstractRelic r : allRelics) {
			if (r instanceof DuelistRelic) {
				allDuelistRelics.add(((DuelistRelic)r));
				allDuelistRelicIds.add(r.relicId);
				DuelistConfigurationData config = ((DuelistRelic)r).getConfigurations();
				if (config != null) {
					relicConfigurations.add(config);
				}
				if (configMapWasEmpty) {
					relicCanSpawnConfigMap.put(r.relicId, ((DuelistRelic)r).getDefaultConfig());
				}
			}
		}

		if (configMapWasEmpty) {
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				String relicConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(relicCanSpawnConfigMap);
				config.setString("relicCanSpawnConfigMap", relicConfigMap);
				config.save();
			} catch (Exception ex) { ex.printStackTrace(); }
		}
		
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
		for (AbstractRelic r : allRelics) {
			BaseMod.addRelicToCustomPool(r, AbstractCardEnum.DUELIST);
		}
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
		BaseMod.addDynamicVariable(new OriginalDamageNum());
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

		StartingDecks.refreshStartingDecksData();

		boolean wasEmpty = puzzleConfigSettingsMap.isEmpty();
		for (StartingDecks deck : StartingDecks.values()) {
			DuelistConfigurationData config = deck.getConfigMenu();
			if (config != null) {
				puzzleConfigurations.add(config);
			}

			if (wasEmpty) {
				puzzleConfigSettingsMap.put(deck.getDeckId(), deck.getDefaultPuzzleConfig());
			}
		}

		if (wasEmpty) {
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
				String puzzleConfigMap = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(DuelistMod.puzzleConfigSettingsMap);
				config.setString("puzzleConfigSettingsMap", puzzleConfigMap);
				config.save();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
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
        addSound("theDuelist:AirChannel", DuelistMod.makeCharAudioPath("AirChannelLow.ogg"));
        addSound("theDuelist:GateChannel", DuelistMod.makeCharAudioPath("GateChannelLow.ogg"));
        addSound("theDuelist:MudChannel", DuelistMod.makeCharAudioPath("MudChannelLow.ogg"));
        addSound("theDuelist:MetalChannel", DuelistMod.makeCharAudioPath("MetalChannelLow.ogg"));
        addSound("theDuelist:FireChannel", DuelistMod.makeCharAudioPath("FireChannelLow.ogg"));
        addSound("theDuelist:ResummonWhoosh", DuelistMod.makeCharAudioPath("ResummonWhoosh.ogg"));
		addSound("theDuelist:ShadowChannel", DuelistMod.makeCharAudioPath("ShadowChannelLow.ogg"));
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
		dragonEffectRanThisCombat = false;
		firstCardInGraveThisCombat = new CancelCard();
		battleFusionMonster = new CancelCard();
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) { wasEliteCombat = true; }
		else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) { wasBossCombat = true; }
		else { wasEliteCombat = false; wasBossCombat = false; }
		Util.handleBossResistNature(wasBossCombat);
		Util.handleEliteResistNature(wasEliteCombat);
		if (!wasBossCombat && !wasEliteCombat) { Util.handleHallwayResistNature(); }
		warriorSynergyTributesThisCombat = 0;
		warriorTributeEffectTriggersThisCombat = 0;
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
		bugsPlayedThisCombat = 0;
		spidersPlayedThisCombat = 0;
		warriorTribThisCombat = false;
		godsPlayedForBonus = 0;
		warriorSynergyTributesThisCombat = 0;
		warriorTributeEffectTriggersThisCombat = 0;
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

		entombedCardsThisRunList += battleEntombedList;
		battleEntombedList = "";
	}

	public static void onSave() {
		setupRunUUID();
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setBool("poolIsCustomized", poolIsCustomized);
			config.setBool(PROP_MONSTER_IS_KAIBA, monsterIsKaiba);
			config.setInt("currentChallengeLevel", challengeLevel);
			config.setInt("corpsesEntombed", corpsesEntombed);
			config.setInt("ghostrickPlayed", ghostrickPlayed);
			config.setInt("mayakashiPlayed", mayakashiPlayed);
			config.setInt("shiranuiPlayed", shiranuiPlayed);
			config.setInt("vampiresPlayed", vampiresPlayed);
			config.setInt("vendreadPlayed", vendreadPlayed);
			config.setInt(PROP_DECK, deckIndex);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt("defaultMaxSummons", defaultMaxSummons);
			config.setString("entombed", entombedCardsThisRunList);
			config.setString("entombedCustomCardProperites", entombedCustomCardProperites);
			config.setString(PROP_MONSTERS_RUN, loadedUniqueMonstersThisRunList);
			config.setString(PROP_SPELLS_RUN, loadedSpellsThisRunList);
			config.setString(PROP_TRAPS_RUN, loadedTrapsThisRunList);
			DuelistTipHelper.saveTips(config);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void onLoad() {
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			runUUID = config.getString(PROP_RUN_UUID);
			deckIndex = config.getInt(PROP_DECK);
			poolIsCustomized = config.getBool("poolIsCustomized");
			lastMaxSummons = config.getInt(PROP_MAX_SUMMONS);
			monsterIsKaiba = config.getBool(PROP_MONSTER_IS_KAIBA);
			corpsesEntombed = config.getInt("corpsesEntombed");
			entombedCardsThisRunList = config.getString("entombed");
			entombedCustomCardProperites = config.getString("entombedCustomCardProperites");
			vampiresPlayed = config.getInt("vampiresPlayed");
			vendreadPlayed = config.getInt("vendreadPlayed");
			ghostrickPlayed = config.getInt("ghostrickPlayed");
			mayakashiPlayed = config.getInt("mayakashiPlayed");
			shiranuiPlayed = config.getInt("shiranuiPlayed");
			loadedUniqueMonstersThisRunList = config.getString(PROP_MONSTERS_RUN);
			loadedTrapsThisRunList = config.getString(PROP_TRAPS_RUN);
			loadedSpellsThisRunList = config.getString(PROP_SPELLS_RUN);
			challengeLevel = config.getInt("currentChallengeLevel");
			defaultMaxSummons = config.getInt("defaultMaxSummons");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source) 
	{
		if (power != null)
		{
			if (power.owner != null && power.owner.equals(AbstractDungeon.player))
			{
				if (AbstractDungeon.player.hasRelic(CursedHealer.ID))
				{
					if (AbstractDungeon.cardRandomRng.random(1, 6) == 1)
					{
						DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, -1));
						AbstractDungeon.player.getRelic(CursedHealer.ID).flash();
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

				if (power instanceof VinesPower) {
					Util.leavesVinesCommonOptionHandler(vinesOption);
					VinesPower vp = (VinesPower)power;
					if (!vp.skipConfigChecks) {
						boolean isLeavesAsWell =
								vinesOption == VinesLeavesMods.GAIN_THAT_MANY_LEAVES_AS_WELL ||
								vinesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_LEAVES_AS_WELL ||
								vinesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL;
						boolean halfAsMuch =
								vinesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_LEAVES_INSTEAD ||
								vinesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_LEAVES_AS_WELL ||
								vinesOption == VinesLeavesMods.GAIN_HALF;
						boolean twiceAsMuch =
								vinesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_LEAVES_INSTEAD ||
								vinesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL ||
								vinesOption == VinesLeavesMods.GAIN_TWICE_AS_MANY;
						int amount = halfAsMuch ? power.amount / 2 : twiceAsMuch ? power.amount * 2 : power.amount;
						if (isLeavesAsWell) {
							DuelistCard.applyPowerToSelf(new LeavesPower(amount, true));
						}
					}
				}

				if (power instanceof LeavesPower) {
					Util.leavesVinesCommonOptionHandler(leavesOption);
					LeavesPower lp = (LeavesPower)power;
					if (!lp.skipConfigChecks) {
						boolean isVinesAsWell =
								leavesOption == VinesLeavesMods.GAIN_THAT_MANY_VINES_AS_WELL ||
								leavesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_VINES_AS_WELL ||
								leavesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_VINES_AS_WELL;
						boolean halfAsMuch =
								leavesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_VINES_INSTEAD ||
								leavesOption == VinesLeavesMods.GAIN_HALF_THAT_MANY_VINES_AS_WELL ||
								leavesOption == VinesLeavesMods.GAIN_HALF;
						boolean twiceAsMuch =
								leavesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_VINES_INSTEAD ||
								leavesOption == VinesLeavesMods.GAIN_TWICE_THAT_MANY_VINES_AS_WELL ||
								leavesOption == VinesLeavesMods.GAIN_TWICE_AS_MANY;
						int amount = halfAsMuch ? power.amount / 2 : twiceAsMuch ? power.amount * 2 : power.amount;
						if (isVinesAsWell) {
							DuelistCard.applyPowerToSelf(new VinesPower(amount, true));
						}
					}
				}

				if (power instanceof VinesPower && power.amount > 0)
				{
					VinesPower vp = (VinesPower)power;
					if (!vp.naturalDisaster) {
						for (AbstractPower pow : AbstractDungeon.player.powers) {
							if (pow instanceof DuelistPower) {
								((DuelistPower)pow).onGainVines();
							}
						}
					}
				}

				for (AbstractOrb o : AbstractDungeon.player.orbs)
				{
					if (o instanceof DuelistOrb)
					{
						((DuelistOrb)o).onPowerApplied(power);
					}
				}
			}
			else if (power instanceof PoisonPower) {
				poisonAppliedThisCombat+=power.amount;
			}
		}
	}

	@Override
	public void receivePowersModified() 
	{
		for (AbstractOrb o : AbstractDungeon.player.orbs) {
			if (o instanceof DuelistOrb) {
				((DuelistOrb)o).checkFocus();
			}
		}
	}

	@Override
	public void receivePostDeath() {}

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
			spidersPlayedThisCombat++;
			if (spidersPlayedThisCombat > spidersToPlayForTempHp) {
				DuelistCard.gainTempHP(spiderTempHP);
				if (spiderEffectResets) {
					spidersPlayedThisCombat = 0;
				}
			}
		}
		
		if (arg0.hasTag(Tags.BUG))
		{
			bugsPlayedThisCombat++;
			if (bugsPlayedThisCombat > bugsToPlayForTempHp) {
				DuelistCard.gainTempHP(bugTempHP);
				if (bugEffectResets) {
					bugsPlayedThisCombat = 0;
				}
			}
		}
		
		if (arg0.hasTag(Tags.NATURIA))
		{
			if (AbstractDungeon.player.hasRelic(Leafpile.ID)) { DuelistCard.applyPowerToSelf(Util.leavesPower(1)); }
			int amt = 1;
			if (AbstractDungeon.player.hasRelic(NaturiaRelic.ID)) {
				amt++;
			}
			AbstractPower vines = Util.vinesPower(amt);
			if (vines instanceof VinesPower) {
				if (!AbstractDungeon.player.hasPower(VinesPower.POWER_ID)) {
					DuelistCard.applyPowerToSelf(vines);
					for (AbstractPower pow : AbstractDungeon.player.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onGainVines(); }}
					for (AbstractRelic r : AbstractDungeon.player.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onGainVines(); }}
				}
			} else if (vines instanceof LeavesPower) {
				DuelistCard.applyPowerToSelf(vines);
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
				uniqueSpellsThisCombat.add((DuelistCard) arg0);
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
				uniqueMonstersThisRun.add(duelistArg0);
				uniqueMonstersThisRunMap.put(arg0.cardID, arg0);
				DuelistMod.loadedUniqueMonstersThisRunList += arg0.cardID + "~";
			}
		
			if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID) && duelistArg0.tributes < 1 && arg0.hasTag(Tags.MONSTER))
			{
				DuelistCard.summon(AbstractDungeon.player, 1, duelistArg0);
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
			if (!uniqueTrapsThisRunMap.containsKey(arg0.cardID)) 
			{
				uniqueTrapsThisRunMap.put(arg0.cardID, arg0);
				uniqueTrapsThisRun.add((DuelistCard) arg0);
				DuelistMod.loadedSpellsThisRunList += arg0.cardID + "~";
			}
		}
	}

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
				if (holidayCardsEnabled && holidayDeckCard != null && addingHolidayCard && arg0.name().equals("THE_DUELIST")) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
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
					boolean addedSpecialSparks = false;
					for (AbstractCard c : startingDeck) 
					{ 
						if (c instanceof Sparks) {
							int roll = ThreadLocalRandom.current().nextInt(1, 20);
							if (Util.getChallengeLevel() > 9) { roll = 2; }
							if ((forceSpecialSparks && !addedSpecialSparks) || (allowSpecialSparks && roll == 1)) {
								startingDeckB.add(Util.getSpecialSparksCard());
								addedSpecialSparks = true;
							} else {
								startingDeckB.add(c);
							}
						} else {
							startingDeckB.add(c);
						}
					}
					for (AbstractCard c : startingDeckB) 
					{
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

					if (randomMagnetAddedToDeck) {
						DuelistCard magnet = Util.getRandomMagnetCard(allowRandomSuperMagnets);
						newStartGroup.addToRandomSpot(magnet);
					}
					arg1.group.addAll(newStartGroup.group);	
					if (holidayCardsEnabled && holidayDeckCard != null && addingHolidayCard) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
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
								dc.fixUpgradeDesc();
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
		if (drawnCard instanceof DuelistCard) {
			((DuelistCard)drawnCard).onDraw();
		}
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
					duelSmoke.triggerPassiveEffect(dc);
					if (duelSmoke.gpcCheck()) { duelSmoke.triggerPassiveEffect(dc); }
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

	@Override
	public void receivePostDungeonInitialize() 
	{
		logger.info("dungeon initialize hook");
	}


	@Override
	public void receivePostDungeonUpdate() 
	{
		if (!checkedCardPool)
		{
			//Util.log("hasnt checked card pool yet");
			// Card Pool Reload Handler
			String fullCardPool = "";
			String run_uuid = null;
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.load();
				fullCardPool = config.getString("fullCardPool");
				run_uuid = config.getString(PROP_RUN_UUID);
			} catch (Exception e) {
				Util.logError("Error loading config file during receivePostDungeonUpdate() (A)", e);
			}
			String[] savedStrings = fullCardPool.split("~");
			ArrayList<String> strings = new ArrayList<>();
			Collections.addAll(strings, savedStrings);
			//setupRunUUID();
			if (strings.size() > 0 && CardCrawlGame.characterManager.anySaveFileExists() && runUUID != null && runUUID.equals(run_uuid))
			{
				//Util.log("Found and loaded previous card pool from this run. Pool Size=" + strings.size());
				toReplacePoolWith.clear();
				shouldReplacePool = true;
				replacingOnUpdate = true;
				//poolIsCustomized = true;
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
				Util.log("Found previous card pool but save file does not exist or does not match previous runUUID. Resetting saved card pool so it doesn't overwrite the new run pool.");
				toReplacePoolWith.clear();
				shouldReplacePool = false;
				replacingOnUpdate = false;
				try {
					SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
					config.setString("fullCardPool", "~");
					config.save();
				} catch (Exception e) {
					Util.logError("Error updating config file during receivePostDungeonUpdate() (B)", e);
				}
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
			for (DuelistCard c : pow.actualCardSummonList)
			{
				c.postTurnReset();
			}
		}
		
		
		// Check to maybe print secret message
		if (summonTurnCount > 2)
		{
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			int msgRoll = AbstractDungeon.cardRandomRng.random(1, 100);
			if ((debugMsg || msgRoll <= 2) && m != null)
			{					
				AbstractDungeon.actionManager.addToBottom(new TalkAction(m, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
			}
		}
		
		summonTurnCount = 0;
		// Mirror Force Helper
		if (p.hasPower(MirrorForcePower.POWER_ID))
		{
			MirrorForcePower instance = (MirrorForcePower) AbstractDungeon.player.getPower(MirrorForcePower.POWER_ID);
			instance.PLAYER_BLOCK = p.currentBlock;
			if (debug) { logger.info("set mirror force power block to: " + p.currentBlock + "."); }
		}
		return true;
	}

	public static void resetAfterRun() {
		BoosterHelper.setPackSize(5);
		Util.resetCardsPlayedThisRunLists();
		Util.log("Ended run, so we are resetting various DuelistMod properties, as well as resetting the card pool");
		AbstractPlayer.customMods = new ArrayList<>();
		archRoll1 = -1;
		archRoll2 = -1;
		battleFusionMonster = new CancelCard();
		boosterDeath = true;
		boostersOpenedThisAct.clear();
		boostersOpenedThisRun.clear();
		chosenRockSunriseTag = Tags.DUMMY_TAG;
		coloredCards = new ArrayList<>();
		corpsesEntombed = 0;
		currentSpellcasterOrbChance = 25;
		currentZombieSouls = 0;
		defaultMaxSummons = 5;
		defaultStartZombieSouls = 3;
		dungeonCardPool.clear();
		ghostrickPlayed = 0;
		gotFrozenEyeFromBigEye = false;
		hadFrozenEye = false;
		hasShopBuffRelic = false;
		hasUpgradeBuffRelic = false;
		highestMaxSummonsObtained = 5;
		lastCardPlayed = new CancelCard();
		lastGhostrickPlayed = new CancelCard();
		lastMaxSummons = 5;
		lastPlantPlayed = new CancelCard();
		mayakashiPlayed = 0;
		megatypeTributesThisRun = 0;
		monstersObtained = 0;
		monstersPlayedCombatNames = new ArrayList<>();
		monstersPlayedRunNames = new ArrayList<>();
		bugsPlayedThisCombat = 0;
		spidersPlayedThisCombat = 0;
		poolIsCustomized = false;
		resummonsThisRun = 0;
		runUUID = null;
		secondLastCardPlayed = new CancelCard();
		secondLastGhostrickPlayed = new CancelCard();
		secondLastPlantPlayed = new CancelCard();
		secondaryTierScorePools = new ArrayList<>();
		selectedDeck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		sevenCompletedsThisCombat = 0;
		shiranuiPlayed = 0;
		skillsPlayedCombatNames = new ArrayList<>();
		spectralDamageMult = 2;
		spellCombatCount = 0;
		spellcasterRandomOrbsChanneled = 0;
		spellsObtained = 0;
		spellsPlayedCombatNames = new ArrayList<>();
		summonCombatCount = 0;
		summonLastCombatCount = 0;
		summonRunCount = 0;
		swordsPlayed = 0;
		synergyTributesRan = 0;
		tokensThisCombat = 0;
		trapsObtained = 0;
		tribCombatCount = 0;
		tribRunCount = 0;
		tributeLastCombatCount = 0;
		uniqueMonstersThisRun = new ArrayList<>();
		uniqueSkillsThisCombat = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		uniqueSpellsThisRun = new ArrayList<>();
		uniqueTrapsThisRun = new ArrayList<>();
		vampiresPlayed = 0;
		vendreadPlayed = 0;
		warriorSynergyTributesThisCombat = 0;
		warriorTributeEffectTriggersThisCombat = 0;
		warriorTribThisCombat = false;
		wyrmTribThisCombat = false;
		zombiesResummonedThisRun = 0;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt("corpsesEntombed", corpsesEntombed);
			config.setInt("defaultMaxSummons", defaultMaxSummons);
			config.setInt("ghostrickPlayed", ghostrickPlayed);
			config.setInt("mayakashiPlayed", mayakashiPlayed);
			config.setInt("shiranuiPlayed", shiranuiPlayed);
			config.setInt("souls", currentZombieSouls);
			config.setInt("startSouls", defaultStartZombieSouls);
			config.setInt("vampiresPlayed", vampiresPlayed);
			config.setInt("vendreadPlayed", vendreadPlayed);
			config.setBool("poolIsCustomized", false);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt(PROP_RESUMMON_DMG, 1);
			config.setString("fullCardPool", "~");
			config.setString(PROP_RUN_UUID, "");
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setupRunUUID() {
		if (runUUID == null) {
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", duelistDefaults);
				String uuid = config.getString(PROP_RUN_UUID);
				if (uuid != null && !uuid.equals("")) {
					runUUID = uuid;
				}
			} catch (Exception ex) {
				Util.logError("Error loading runUUID from config at start of run", ex);
			}
		}
		if (runUUID == null) {
			runUUID = UuidUtil.getTimeBasedUuid().toString();
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", duelistDefaults);
				config.setString(PROP_RUN_UUID, runUUID);
				config.save();
			} catch (Exception ex) {
				Util.logError("Error saving runUUID from config at start of run", ex);
			}

		}
	}

	public static void receiveStartRun() {
		if (Util.getChallengeLevel() > 1) { lastMaxSummons = defaultMaxSummons = 4; }
		poolIsCustomized = false;
		monstersObtained = 0;
		spellsObtained = 0;
		trapsObtained = 0;
	}

	@Override
	public void receiveStartAct()
	{
		boostersOpenedThisAct.clear();
		if (AbstractDungeon.floorNum <= 1)
		{
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
			{
				if (c.hasTag(Tags.MONSTER)) { monstersObtained++; }
				if (c.hasTag(Tags.SPELL)) { spellsObtained++; }
				if (c.hasTag(Tags.TRAP)) { trapsObtained++; }
			}
		}
	}
	
	
	
	// CONFIG MENU SETUP -------------------------------------------------------------------------------------------------------------------------------------- //
	
	// Line breakers
	public static void linebreak() { linebreak(0); }

	public static void linebreak(int extra) { yPos -= (45 + extra); }

	private void configPanelSetup() {
		settingsPanel = new DuelistModPanel();
		List<ConfigMenuPage> settingsPages = new ArrayList<>();
		List<SpecificConfigMenuPage> pages = new ArrayList<>();
		ArrayList<String> pageNames = new ArrayList<>();
		int pagerY = (int)startingYPos - 580;
		int pagerRightX = (int)(xLabPos + xSecondCol + xThirdCol + 120);
		int pagerLeftX = (int)xLabPos - 25;
		int footerY = pagerY + 65;

		pages.add(new General());
		pages.add(new Gameplay());
		pages.add(new CardPool());
		pages.add(new DeckUnlock());
		pages.add(new Visual());
		pages.add(new MonsterType());
		pages.add(new CardConfigs());
		pages.add(new RelicConfigs());
		pages.add(new PotionConfigs());
		pages.add(new OrbConfigs());
		pages.add(new EventConfigs());
		pages.add(new PuzzleConfigs());
		pages.add(new StanceConfigs());
		pages.add(new Randomized());
		pages.add(new Metrics());

		for (SpecificConfigMenuPage page : pages) {
			if (page instanceof RefreshablePage) {
				refreshablePages.add((RefreshablePage) page);
			}
			settingsPages.add(page.generatePage());
		}

		for (SpecificConfigMenuPage page : pages) {
			pageNames.add(page.getPageName());
		}

		DuelistDropdown pageSelector = new DuelistDropdown("", pageNames, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol - 30), Settings.scale * footerY, 6, null, (s, i) -> DuelistMod.paginator.setPage(s));
		paginator = new DuelistPaginator(2,3, 50,50, settingsPages, pageNames, pageSelector);
		Pager nextPageBtn = new Pager(rightArrow, pagerRightX, pagerY, 100, 100, true, paginator);
		Pager prevPageBtn = new Pager(leftArrow, pagerLeftX, pagerY, 100, 100, false, paginator);

		settingsPanel.addUIElement(pageSelector);
		settingsPanel.addUIElement(nextPageBtn);
		settingsPanel.addUIElement(prevPageBtn);
		settingsPanel.addUIElement(paginator);
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

	public static void onAbandonRunFromMainMenu(AbstractPlayer player) {

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
		if (openedModSettings && settingsPanel != null) {
			settingsPanel.renderFrom(spriteBatch, lastSource);
			configCancelButton.render(spriteBatch);
		}
	}

	@Override
	public void receivePreUpdate() {
		if (openedModSettings && settingsPanel != null && settingsPanel.isUp) {
			settingsPanel.update();
		}
	}

	@Override
	public void receivePostRender(SpriteBatch spriteBatch) {

	}

	@Override
	public void receiveCameraRender(OrthographicCamera orthographicCamera) {

	}

	public static void onVeryEndOfMonsterTurn(AbstractMonster m) {
		if (m.hasPower(IceHandPower.POWER_ID)) {
			IceHandPower pow = (IceHandPower)m.getPower(IceHandPower.POWER_ID);
			pow.trigger();
		}
	}

	private Map<String, Map<String, List<String>>> getRelicsAndPotionsForAllDecks() {
		Map<String, Map<String, List<String>>> output = new HashMap<>();
		Map<String, List<String>> relics = new HashMap<>();
		Map<String, List<String>> potions = new HashMap<>();
		Set<String> canSpawnUnchecked = new HashSet<>();
		Set<String> canSpawnUncheckedPot = new HashSet<>();
		for (int i = 0; i < 31; i++) {
			hardSetCurrentDeck(i);
			String deckName = Util.getDeck();
			if (!relics.containsKey(deckName)) {
				relics.put(deckName, new ArrayList<>());
			}
			if (!potions.containsKey(deckName)) {
				potions.put(deckName, new ArrayList<>());
			}
			for (DuelistRelic relic : DuelistMod.allDuelistRelics) {
				boolean canSpawn = true;
				try { canSpawn = relic.canSpawn(); } catch (Exception ignored) {
					canSpawnUnchecked.add(relic.relicId);
				}
				if (canSpawn) {
					relics.get(deckName).add(relic.relicId);
				}
			}
			for (DuelistPotion potion : DuelistMod.allDuelistPotionsForOutput) {
				boolean canSpawn = true;
				try { canSpawn = potion.canSpawn(); } catch (Exception ignored) {
					canSpawnUncheckedPot.add(potion.ID);
				}
				if (canSpawn) {
					potions.get(deckName).add(potion.ID);
				}
			}
		}
		logger.info("Could not check relic canSpawn() -- " + canSpawnUnchecked);
		logger.info("Could not check potion canSpawn() -- " + canSpawnUncheckedPot);
		output.put("Relics", relics);
		output.put("Potions", potions);
		return output;
	}

	private void hardSetCurrentDeck(int index) {
		DuelistMod.deckIndex = index;
	}
}
