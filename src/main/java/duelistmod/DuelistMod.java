package duelistmod;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.DuelistKeyword;
import duelistmod.dto.LoadoutUnlockOrderInfo;
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
import duelistmod.persistence.DeckUnlockProgressDTO;
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
import duelistmod.persistence.PersistentDuelistData;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistModPanel;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.ConfigMenuPage;
import duelistmod.ui.configMenu.DuelistPaginator;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.ui.configMenu.pages.CardConfigs;
import duelistmod.ui.configMenu.pages.ColorlessShop;
import duelistmod.ui.configMenu.pages.DeckUnlock;
import duelistmod.ui.configMenu.pages.EventConfigs;
import duelistmod.ui.configMenu.pages.Gameplay;
import duelistmod.ui.configMenu.pages.General;
import duelistmod.ui.configMenu.pages.CardPool;
import duelistmod.ui.configMenu.pages.Metrics;
import duelistmod.ui.configMenu.pages.MonsterTypeConfigs;
import duelistmod.ui.configMenu.pages.OrbConfigs;
import duelistmod.ui.configMenu.pages.PotionConfigs;
import duelistmod.ui.configMenu.pages.PuzzleConfigs;
import duelistmod.ui.configMenu.pages.Randomized;
import duelistmod.ui.configMenu.pages.RelicConfigs;
import duelistmod.ui.configMenu.pages.StanceConfigs;
import duelistmod.ui.configMenu.pages.Visual;
import duelistmod.ui.gameOver.DuelistDeathScreen;
import duelistmod.ui.gameOver.DuelistVictoryScreen;
import duelistmod.persistence.DuelistConfig;
import duelistmod.variables.Colors;
import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
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
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.interfaces.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.*;
import duelistmod.cards.*;
import duelistmod.cards.curses.DuelistAscender;
import duelistmod.cards.other.bookOfLifeOptions.CustomResummonCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.*;
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

import static duelistmod.enums.CardPoolType.DECK_BASIC_DEFAULT;

@SuppressWarnings("CommentedOutCode")
@SpireInitializer
public class DuelistMod 
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber,
PreMonsterTurnSubscriber, PostDungeonUpdateSubscriber, StartActSubscriber, PostObtainCardSubscriber, PotionGetSubscriber, StartGameSubscriber,
PostUpdateSubscriber, RenderSubscriber, PostRenderSubscriber, PreRenderSubscriber, PreUpdateSubscriber
{
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());

	// Member fields
	public static String version = "v4.0.1";
	public static Mode modMode = Mode.PROD;
	public static MetricsMode metricsMode = MetricsMode.LOCAL;
	public static String trueVersion = version.substring(1);
	private static String modName = "Duelist Mod";
	private static String modAuthor = "Nyoxide";
	private static String modDescription = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	public static final ArrayList<String> cardSets = new ArrayList<>();
	private static ArrayList<IncrementDiscardSubscriber> incrementDiscardSubscribers;

	// Global Fields
	// Config Settings
	public static DuelistVictoryScreen victoryScreen;
	public static DuelistDeathScreen deathScreen;
	public static CardPoolType cardPoolType = DECK_BASIC_DEFAULT;
	public static DuelistConfig configSettingsLoader;
	public static PersistentDuelistData persistentDuelistData;
	public static String saveSlotA = "";
	public static String saveSlotB = "";
	public static String saveSlotC = "";
	public static final String PROP_TOON_BTN = "toonBtnBool";
	public static final String PROP_EXODIA_BTN = "exodiaBtnBool";
	public static final String PROP_OJAMA_BTN = "ojamaBtnBool";
	public static final String PROP_CREATOR_BTN = "creatorBtnBool";
	public static final String PROP_OLD_CHAR = "oldCharacter";
	public static final String PROP_SET = "setIndex";
	public static final String PROP_CARD_POOL_TYPE = "cardPoolType";
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
	public static final String PROP_LAST_TIME_TIER_SCORES_CHECKED = "lastTimeTierScoresChecked";
	public static final String PROP_TIER_SCORE_CACHE = "tierScoreCache";
	public static final String PROP_MODULE_CACHE = "moduleCache";
	public static final String PROP_RESTRICT_SUMMONING = "restrictSummoningZones";
	public static final String PROP_SELECTED_CHARACTER_MODEL = "selectedCharacterModel";
	public static final String PROP_REPLACE_COMMON_KEYWORDS_WITH_ICON = "replaceCommonKeywordsWithIcon";
	public static final String PROP_METRICS_UUID = "guid";
	public static final String PROP_HIDE_UNLOCK_ALL_DECKS_BTN = "hideUnlockAllDecksButton";
	public static CharacterModel selectedCharacterModel = CharacterModel.ANIM_YUGI;
	public static SpecialSparksStrategy selectedSparksStrategy = SpecialSparksStrategy.RANDOM_WEIGHTED;
	public static String selectedCharacterModelAnimationName = "animation";
	public static String characterModel = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String yugiChar = "duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml";
	public static final String oldYugiChar = "duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml";
	public static final String kaibaPlayerModel = "duelistModResources/images/char/duelistCharacterUpdate/KaibaPlayer.scml";
	public static String kaibaEnemyModel = "KaibaModel2";
	public static Properties duelistDefaults = new Properties();
	public static boolean oldCharacter = false;
	public static boolean playAsKaiba = false;
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
	public static String specialSummonKeywordDescription = "";

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
	public static Map<String, DuelistCard> orbCardMap = new HashMap<>();
	public static Map<String, AbstractCard> mapForCardPoolSave = new HashMap<>();
	public static Map<String, AbstractCard> mapForRunCardsLoading = new HashMap<>();
	public static Map<String, AbstractCard> uniqueSpellsThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueMonstersThisRunMap = new HashMap<>();
	public static Map<String, AbstractCard> uniqueTrapsThisRunMap = new HashMap<>();
	public static Map<String, AbstractPotion> duelistPotionMap = new HashMap<>();
	public static ArrayList<String> allDuelistCardNames = new ArrayList<>();
	public static Map<String, String> magicNumberCards = new HashMap<>();
	public static Map<String, Integer> summonCards = new HashMap<>();
	public static ArrayList<String> summonCardNames = new ArrayList<>();
	public static ArrayList<String> monsterAndTokenCardNames = new ArrayList<>();
	public static ArrayList<String> nonExemptCardNames = new ArrayList<>();
	public static HashMap<String, DuelistCard> implementedEnemyDuelistCards = new HashMap<>();
	public static HashMap<String, DuelistCard> nonImplementedEnemyDuelistCards = new HashMap<>();
	public static Map<String, Integer> tributeCards = new HashMap<>();
	public static Map<String, String> dungeonCardPool = new HashMap<>();
	public static Map<String, String> totallyRandomCardMap = new HashMap<>();
	public static HashMap<String, AbstractOrb> implementedEnemyDuelistOrbs = new HashMap<>();
	public static final HashMap<String, String> buffCardPowerKeywordsByPowerId = new HashMap<>();

	public static CardTierScores cardTierScores;
	public static List<String> secondaryTierScorePools = new ArrayList<>();
	public static Set<UUID> colorlessShopCardUUIDs = new HashSet<>();
	public static UUID colorlessShopSlotLeft;
	public static UUID colorlessShopSlotRight;

	public static ArrayList<BoosterPack> currentBoosters = new ArrayList<>();
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
	public static final ArrayList<DuelistCard> enduringCards = new ArrayList<>();
	public static final ArrayList<DuelistCard> enemyDuelistEnduringCards = new ArrayList<>();
	public static ArrayList<AbstractCard> entombedCards = new ArrayList<>();
	public static ArrayList<AbstractCard> entombedCardsCombat = new ArrayList<>();
	public static ArrayList<DuelistCard> uniqueTrapsThisRun = new ArrayList<>();
	public static ArrayList<AbstractCard> arcaneCards = new ArrayList<>();
	public static ArrayList<AbstractCard> toReplacePoolWith = new ArrayList<>();
	public static ArrayList<AbstractCard> metronomes = new ArrayList<>();
	public static ArrayList<AbstractCard> coloredCards = new ArrayList<>();
	public static ArrayList<AbstractCard> duelColorlessCards = new ArrayList<>();
	public static ArrayList<AbstractCard> rareCardInPool = new ArrayList<>();
	public static ArrayList<AbstractCard> metronomeResummonsThisCombat = new ArrayList<>();
	public static ArrayList<AbstractCard> holidayNonDeckCards = new ArrayList<>();
	public static ArrayList<AbstractCard> totallyRandomCardList = new ArrayList<>();
	public static ArrayList<AbstractCard> waterHazardCards = new ArrayList<>();
	public static final ArrayList<AbstractCard> currentlyHaunted = new ArrayList<>();
	public static Color hauntedGlowColor = Color.PURPLE;
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<>();
	public static ArrayList<AbstractPotion> allDuelistPotions = new ArrayList<>();
	public static ArrayList<AbstractRelic> duelistRelicsForTombEvent = new ArrayList<>();
	public static ArrayList<DuelistRelic> allDuelistRelics = new ArrayList<>();
	public static ArrayList<AbstractEvent> allDuelistEvents = new ArrayList<>();
	public static ArrayList<String> allDuelistRelicIds = new ArrayList<>();
	public static ArrayList<DuelistPotion> allDuelistPotionsForOutput = new ArrayList<>();
	public static ArrayList<String> allDuelistPotionIds = new ArrayList<>();
	public static ArrayList<String> spellsPlayedCombatNames = new ArrayList<>();
	public static ArrayList<String> monstersPlayedCombatNames = new ArrayList<>();
	public static ArrayList<String> monstersPlayedRunNames = new ArrayList<>();
	public static ArrayList<String> randomBuffStrings = new ArrayList<>();
	public static ArrayList<String> invertableOrbNames = new ArrayList<>();
	public static ArrayList<String> godsPlayedNames = new ArrayList<>();
	public static ArrayList<String> orbPotionIDs = new ArrayList<>();
	public static ArrayList<CardTags> monsterTypes = new ArrayList<>();
	public static ArrayList<CardTags> summonedTypesThisTurn = new ArrayList<>();
	public static ArrayList<RefreshablePage> refreshablePages = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> cardConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> orbConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> relicConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> stanceConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> potionConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> eventConfigurations = new ArrayList<>();
	public static ArrayList<DuelistConfigurationData> puzzleConfigurations = new ArrayList<>();
	public static List<DuelistKeyword> duelistKeywords;
	public static final HashSet<String> uniqueBeastsPlayedThisTurn = new HashSet<>();
	public static final ArrayList<DuelistCard> kuribohCardsPlayedThisCombat = new ArrayList<>();
	public static HashMap<String, DuelistKeyword> duelistKeywordMultiwordKeyMap = new HashMap<>();
	public static Map<String, Map<String, List<String>>> relicAndPotionByDeckData = null;
	public static AbstractCard holidayDeckCard;
	public static DuelistCard lastMonsterPlayedThisCombat;
	public static DuelistCard lastEnemyDuelistMonsterPlayedThisCombat;
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
	public static boolean hadFrozenEye = false;
	public static boolean gotFrozenEyeFromBigEye = false;
	public static boolean spellcasterDidChannel = false;
	public static boolean warriorTribThisCombat = false;
	public static boolean wyrmTribThisCombat = false;
	public static boolean wasEliteCombat = false;
	public static boolean wasBossCombat = false;
	public static boolean mirrorLadybug = false;
	public static boolean poolIsCustomized = false;
	public static boolean isConspire = Loader.isModLoaded("conspire");
	public static boolean isReplay = Loader.isModLoaded("ReplayTheSpireMod");
	public static boolean isHubris = Loader.isModLoaded("hubris");
	public static boolean isDisciple = Loader.isModLoaded("chronomuncher");
	public static boolean isClockwork = Loader.isModLoaded("ClockworkMod");
	public static boolean isGatherer = Loader.isModLoaded("gatherermod");
	public static boolean isInfiniteSpire = Loader.isModLoaded("infinitespire");
	public static boolean isAnimator = Loader.isModLoaded("eatyourbeetsvg-theanimator");
	public static boolean isGifTheSpire = Loader.isModLoaded("GifTheSpireLib");
	public static boolean isHighlightPath = Loader.isModLoaded("HighlightPath");
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
	public static boolean addedBeastSet = false;
	public static boolean addedToonSet = false;
	public static boolean addedDinoSet = false;
	public static boolean addedArcaneSet = false;
	public static boolean addedRandomCards = false;
	public static boolean addedRedSet = false;
	public static boolean addedBlueSet = false;
	public static boolean addedGreenSet = false;
	public static boolean addedPurpleSet = false;
	public static boolean addedHalloweenCards = false;
	public static boolean addedBirthdayCards = false;
	public static boolean addedXmasCards = false;
	public static boolean addedWeedCards = false;
	public static boolean checkedCardPool = false;
	public static boolean overflowedThisTurn = false;
	public static boolean overflowedLastTurn = false;
	public static boolean bookEclipseThisCombat = false;
	public static boolean boosterDeath = false;
	public static boolean openedModSettings = false;
	public static boolean seedPanelOpen = false;
	public static boolean puzzleEffectRanThisCombat = false;
	public static boolean isSensoryStone = false;
	public static boolean unblockedDamageTakenLastTurn = false;
	public static boolean unblockedDamageTakenThisTurn = false;
	public static boolean unblockedDamageTriggerCheck = false;
	public static boolean enemyDuelistUnblockedDamageTakenLastTurn = false;
	public static boolean enemyDuelistUnblockedDamageTakenThisTurn = false;
	public static boolean enemyDuelistUnblockedDamageTriggerCheck = false;
	public static boolean shopScreenIgnorePurgeUpdates = false;

	// Numbers
	public static int duelistScore = 0;
	public static int trueDuelistScore = 0;
	public static int trueVersionScore = 0;
	public static int randomDeckSmallSize = 10;
	public static int randomDeckBigSize = 15;
	public static int cardCount = 75;
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
	public static int archRoll1 = -1;
	public static int archRoll2 = -1;
	public static int bugsPlayedThisCombat = 0;
	public static int spidersPlayedThisCombat = 0;
	public static int gravAxeStr = -99;
	public static int poisonAppliedThisCombat = 0;
	public static int zombiesResummonedThisCombat = 0;
	public static int zombiesResummonedThisRun = 0;
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
	public static int warriorSynergyTributesThisCombat = 0;
	public static int beastFeralBump = 2;
	public static int beastTerritorialMultiplier = 2;
	public static int namelessTombMagicMod = 5;
	public static int namelessTombPowerMod = 8;
	public static int namelessTombGoldMod = 20;
	public static int challengeLevel = 0;
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
	public static int warriorTributeEffectTriggersThisCombat = 0;
	public static int drawExtraCardsAtTurnStart = 0;
	public static int drawExtraCardsAtTurnStartThisBattle = 0;
	public static final List<Integer> beastsDrawnByTurn = new ArrayList<>();
	public static final List<Integer> enemyBeastsDrawnByTurn = new ArrayList<>();
	public static int beastsDrawnThisTurn = 0;
	public static int enemyBeastsDrawnThisTurn = 0;
	public static ColorlessShopSource colorlessShopLeftSlotSource = ColorlessShopSource.BASIC_COLORLESS;
	public static MenuCardRarity colorlessShopLeftSlotLowRarity = MenuCardRarity.COMMON;
	public static MenuCardRarity colorlessShopLeftSlotHighRarity = MenuCardRarity.UNCOMMON;
	public static ColorlessShopSource colorlessShopRightSlotSource = ColorlessShopSource.BASIC_COLORLESS;
	public static MenuCardRarity colorlessShopRightSlotLowRarity = MenuCardRarity.RARE;
	public static MenuCardRarity colorlessShopRightSlotHighRarity = MenuCardRarity.RARE;

	// Other
	public static TheDuelist duelistChar;
	public static StartingDeck currentDeck;
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
	public static Percentage raigekiBonusPercentage = Percentage.ZERO;
	public static Percentage raigekiBonusUpgradePercentage = Percentage.ZERO;
	public static GenericCancelButton configCancelButton;
	public static MidRunConfigurationIcon topPanelMidRunConfigurationIcon;
	public static ConfigOpenSource lastSource = ConfigOpenSource.BASE_MOD;

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
	public static DuelistDropdown openDropdown;


	// Global Character Stats
	public static int energyPerTurn = 3;
	public static int startHP = 80;
	public static int maxHP = 80;
	public static int startGold = 125;
	public static int cardDraw = 5;

	// Turn off for Workshop releases, just prints out stuff and adds debug cards/tokens to game
	public static boolean debug = true;									// print statements only, used in mod option panel
	public static boolean debugMsg = false;								// for secret msg
	@SuppressWarnings("ConstantValue")
	public static final boolean addTokens = modMode == Mode.DEV;		// adds debug tokens to library

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

	public static String makeTypeIconPath(String resourcePath) {
		return makePath("icons/monsterType/" + resourcePath);
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
		BaseMod.subscribe(this);

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
		duelistDefaults.setProperty(PROP_CARD_POOL_TYPE, "0");
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
		duelistDefaults.setProperty(PROP_ALLOW_CARD_POOL_RELICS, "FALSE");
		duelistDefaults.setProperty(PROP_MONSTERS_RUN, "");
		duelistDefaults.setProperty(PROP_SPELLS_RUN, "");
		duelistDefaults.setProperty(PROP_TRAPS_RUN, "");
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
		duelistDefaults.setProperty("colorlessShopLeftSlotSource", "0");
		duelistDefaults.setProperty("colorlessShopRightSlotSource", "0");
		duelistDefaults.setProperty("colorlessShopLeftSlotLowRarity", "1");
		duelistDefaults.setProperty("colorlessShopRightSlotLowRarity", "2");
		duelistDefaults.setProperty("colorlessShopLeftSlotHighRarity", "1");
		duelistDefaults.setProperty("colorlessShopRightSlotHighRarity", "2");
		duelistDefaults.setProperty("dragonScalesSelectorIndex", "6");
		duelistDefaults.setProperty("dragonScalesModIndex", "1");
		duelistDefaults.setProperty("vinesSelectorIndex", "0");
		duelistDefaults.setProperty("leavesSelectorIndex", "0");
		duelistDefaults.setProperty("allowDuelistEvents", "TRUE");
		duelistDefaults.setProperty("playingChallenge", "FALSE");
		duelistDefaults.setProperty("currentChallengeLevel", "0");
		duelistDefaults.setProperty("birthdayMonth", "0");
		duelistDefaults.setProperty("birthdayDay", "0");
		duelistDefaults.setProperty("neverChangedBirthday", "TRUE");
		duelistDefaults.setProperty("fullCardPool", "~");
		duelistDefaults.setProperty("entombed", "");
		duelistDefaults.setProperty("quicktimeEventsAllowed", "FALSE");
		duelistDefaults.setProperty("entombedCustomCardProperites", "");
		duelistDefaults.setProperty("corpsesEntombed", "0");
		duelistDefaults.setProperty("duelistScore", "0");
		duelistDefaults.setProperty("trueDuelistScore", "0");
		duelistDefaults.setProperty("trueDuelistScore"+trueVersion, "0");
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
		duelistDefaults.setProperty("beastIncrement", "1");
		duelistDefaults.setProperty("superheavyDex", "1");
		duelistDefaults.setProperty("naturiaVinesDmgMod", "0");
		duelistDefaults.setProperty("warriorTributeEffectTriggersPerCombat", "1");
		duelistDefaults.setProperty("warriorSynergyTributeNeededToTrigger", "1");
		duelistDefaults.setProperty("enableWarriorTributeEffect", "TRUE");
		duelistDefaults.setProperty("disableAllOrbPassives", "FALSE");
		duelistDefaults.setProperty("disableAllOrbEvokes", "FALSE");
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
		duelistDefaults.setProperty("isChallengeForceUnlocked", "FALSE");
		duelistDefaults.setProperty("isAllowStartingDeckCardsInPool", "FALSE");
		duelistDefaults.setProperty("currentStartingDeck", "0");
		duelistDefaults.setProperty("bonusStartingOrbSlots", "0");
		duelistDefaults.setProperty("playerAnimationSpeed", "6");
		duelistDefaults.setProperty("enemyAnimationSpeed", "6");

		monsterTypes.add(Tags.AQUA);		typeCardMap_ID.put(Tags.AQUA, makeID("AquaTypeCard"));					typeCardMap_IMG.put(Tags.AQUA, makePath(Strings.ISLAND_TURTLE));
		monsterTypes.add(Tags.DRAGON);		typeCardMap_ID.put(Tags.DRAGON, makeID("DragonTypeCard"));				typeCardMap_IMG.put(Tags.DRAGON, makePath(Strings.BABY_DRAGON));
		monsterTypes.add(Tags.FIEND);		typeCardMap_ID.put(Tags.FIEND, makeID("FiendTypeCard"));					typeCardMap_IMG.put(Tags.FIEND, makeCardPath("GrossGhost.png"));
		monsterTypes.add(Tags.INSECT);		typeCardMap_ID.put(Tags.INSECT, makeID("InsectTypeCard"));				typeCardMap_IMG.put(Tags.INSECT, makePath(Strings.BASIC_INSECT));
		monsterTypes.add(Tags.MACHINE);		typeCardMap_ID.put(Tags.MACHINE, makeID("MachineTypeCard"));				typeCardMap_IMG.put(Tags.MACHINE, makeCardPath("YellowGadget.png"));
		monsterTypes.add(Tags.NATURIA);		typeCardMap_ID.put(Tags.NATURIA, makeID("NaturiaTypeCard"));				typeCardMap_IMG.put(Tags.NATURIA, makePath(Strings.NATURIA_HORNEEDLE));
		monsterTypes.add(Tags.PLANT);		typeCardMap_ID.put(Tags.PLANT, makeID("PlantTypeCard"));					typeCardMap_IMG.put(Tags.PLANT, makePath(Strings.FIREGRASS));
		monsterTypes.add(Tags.PREDAPLANT);	typeCardMap_ID.put(Tags.PREDAPLANT, makeID("PredaplantTypeCard"));		typeCardMap_IMG.put(Tags.PREDAPLANT, makePath(Strings.PREDA_TOKEN));
		monsterTypes.add(Tags.SPELLCASTER);	typeCardMap_ID.put(Tags.SPELLCASTER, makeID("SpellcasterTypeCard"));		typeCardMap_IMG.put(Tags.SPELLCASTER, makeCardPath("SpellcasterToken.png"));
		monsterTypes.add(Tags.SUPERHEAVY);	typeCardMap_ID.put(Tags.SUPERHEAVY, makeID("SuperheavyTypeCard"));		typeCardMap_IMG.put(Tags.SUPERHEAVY, makePath(Strings.SUPERHEAVY_SCALES));
		monsterTypes.add(Tags.TOON_POOL);	typeCardMap_ID.put(Tags.TOON_POOL, makeID("ToonTypeCard"));				typeCardMap_IMG.put(Tags.TOON_POOL, makePath(Strings.TOON_GOBLIN_ATTACK));
		monsterTypes.add(Tags.ZOMBIE);		typeCardMap_ID.put(Tags.ZOMBIE, makeID("ZombieTypeCard"));				typeCardMap_IMG.put(Tags.ZOMBIE, makePath(Strings.ARMORED_ZOMBIE));
		monsterTypes.add(Tags.WARRIOR);		typeCardMap_ID.put(Tags.WARRIOR, makeID("WarriorTypeCard"));				typeCardMap_IMG.put(Tags.WARRIOR, makeCardPath("HardArmor.png"));
		monsterTypes.add(Tags.ROCK);		typeCardMap_ID.put(Tags.ROCK, makeID("RockTypeCard"));					typeCardMap_IMG.put(Tags.ROCK, makeCardPath("Giant_Soldier.png"));
		monsterTypes.add(Tags.WYRM);		typeCardMap_ID.put(Tags.WYRM, makeID("WyrmTypeCard"));					typeCardMap_IMG.put(Tags.WYRM, makeCardPath("Bixi.png"));
		monsterTypes.add(Tags.DINOSAUR);	typeCardMap_ID.put(Tags.DINOSAUR, makeID("DinosaurTypeCard"));			typeCardMap_IMG.put(Tags.DINOSAUR, makeCardPath("SauropodBrachion.png"));

											typeCardMap_ID.put(Tags.ROSE, makeID("RoseTypeCard"));					typeCardMap_IMG.put(Tags.ROSE, makeCardPath("RevivalRose.png"));
											typeCardMap_ID.put(Tags.GIANT, makeID("GiantTypeCard"));					typeCardMap_IMG.put(Tags.GIANT, makeCardPath("EarthGiant.png"));
											typeCardMap_ID.put(Tags.ARCANE, makeID("ArcaneTypeCard"));				typeCardMap_IMG.put(Tags.ARCANE, makeCardPath("AmuletAmbition.png"));
											typeCardMap_ID.put(Tags.MAGNET, makeID("MagnetTypeCard"));				typeCardMap_IMG.put(Tags.MAGNET, makeCardPath("Gamma_Magnet.png"));
											typeCardMap_ID.put(Tags.MEGATYPED, makeID("MegatypeTypeCard"));			typeCardMap_IMG.put(Tags.MEGATYPED, makeCardPath("Eva.png"));
											typeCardMap_ID.put(Tags.MONSTER, makeID("MonsterTypeCard"));				typeCardMap_IMG.put(Tags.MONSTER, makeCardPath("Giant_Soldier.png"));
											typeCardMap_ID.put(Tags.SPELL, makeID("SpellTypeCard"));					typeCardMap_IMG.put(Tags.SPELL, makeCardPath("Red_Medicine.png"));
											typeCardMap_ID.put(Tags.TRAP, makeID("TrapTypeCard"));					typeCardMap_IMG.put(Tags.TRAP, makeCardPath("Castle_Walls.png"));
											typeCardMap_ID.put(Tags.OJAMA, makeID("OjamaTypeCard"));					typeCardMap_IMG.put(Tags.OJAMA, makeCardPath("OjamaEmperor.png"));
		monsterTypes.add(Tags.BEAST);		typeCardMap_ID.put(Tags.BEAST, makeID("BeastTypeCard"));					typeCardMap_IMG.put(Tags.BEAST, makeCardPath("BigKoala.png"));

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

		try {
			configSettingsLoader = new DuelistConfig("TheDuelist", "DuelistConfig");
		} catch (Exception ex) {
			Util.logError("Error loading DuelistConfig.json file", ex);
		}
		try
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
            config.load();
            oldCharacter = config.getBool(PROP_OLD_CHAR);
            resetProg = config.getBool(PROP_RESET);
            cardCount = config.getInt(PROP_CARDS);
            debug = config.getBool(PROP_DEBUG);
			chosenDeckTag = StartingDeck.currentDeck.getStartingDeckTag();
            lastMaxSummons = config.getInt(PROP_MAX_SUMMONS);
            playAsKaiba = config.getBool(PROP_PLAY_KAIBA);
            monsterIsKaiba = config.getBool(PROP_MONSTER_IS_KAIBA);
            saveSlotA = config.getString(PROP_SAVE_SLOT_A);
            saveSlotB = config.getString(PROP_SAVE_SLOT_B);
            saveSlotC = config.getString(PROP_SAVE_SLOT_C);
            loadedUniqueMonstersThisRunList = config.getString(PROP_MONSTERS_RUN);
            loadedSpellsThisRunList = config.getString(PROP_SPELLS_RUN);
            loadedTrapsThisRunList = config.getString(PROP_TRAPS_RUN);
            entombedCardsThisRunList = config.getString("entombed");
            defaultMaxSummons = config.getInt("defaultMaxSummons");
			currentZombieSouls = config.getInt("souls");
			defaultStartZombieSouls = config.getInt("startSouls");
            entombedCustomCardProperites = config.getString("entombedCustomCardProperites");
            corpsesEntombed = config.getInt("corpsesEntombed");
        	playingChallenge = config.getBool("playingChallenge");
        	challengeLevel = config.getInt("currentChallengeLevel");
			lastTimeTierScoreChecked = config.getString(PROP_LAST_TIME_TIER_SCORES_CHECKED);
			metricsUUID = config.getString(PROP_METRICS_UUID);
			poolIsCustomized = config.getBool("poolIsCustomized");
			MetricsHelper.setupUUID(config);
        	duelistScore = config.getInt("duelistScore");
			trueDuelistScore = config.getInt("trueDuelistScore");
			trueVersionScore = config.getInt("trueDuelistScore" + trueVersion);
        } catch (Exception e) { Util.logError("Error loading old properties config file", e); }
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
		combatIconViewer = new CombatIconViewer();
		receiveEditSounds();

		// Animated Cards
		if (isGifTheSpire) { new GifSpireHelper(); }

		// Events
		if (persistentDuelistData.GameplaySettings.getDuelistEvents()) {
			Util.addEventsToGame();
		}
		configPanelSetup();
		BaseMod.registerModBadge(badgeTexture, modName, modAuthor, modDescription, settingsPanel);

		// Monsters
		BaseMod.addMonster("theDuelist:OppositeDuelistEnemy", () -> new OppositeDuelistEnemy());

		// Custom Dev Console Commands
		CustomConsoleCommandHelper.setupCommands();

		// Encounters
		if (DuelistMod.persistentDuelistData.GameplaySettings.getEnemyDuelists()) {
			BaseMod.addEliteEncounter(TheCity.ID, new MonsterInfo("theDuelist:OppositeDuelistEnemy", 4.0F));
		}

		// Rewards
		BaseMod.registerCustomReward(RewardItemTypeEnumPatch.DUELIST_PACK, (rewardSave) -> BoosterHelper.getPackFromSave(rewardSave.id), (customReward) -> new RewardSave(customReward.type.toString(), ((BoosterPack)customReward).packName));

		// Top Panel
		topPanelMidRunConfigurationIcon = new MidRunConfigurationIcon();
		BaseMod.addTopPanelItem(topPanelMidRunConfigurationIcon);

		// Custom Powers (for basemod console)
		Util.registerCustomPowers();

		//relicAndPotionByDeckData = getRelicsAndPotionsForAllDecks();

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

		// Dynamic Text Handlers
		DynamicTextBlocks.registerCustomCheck("theDuelist:BeastDrawCount", BeastDrawCount::customCheck);

		Util.fillCardsPlayedThisRunLists();
	}

	public static boolean allDecksUnlocked(boolean includeNonScoreDecks) {
		LoadoutUnlockOrderInfo deckUnlockCheck = StartingDeck.getNextUnlockDeckAndScore(duelistScore);
		if (!includeNonScoreDecks && "ALL DECKS UNLOCKED".equals(deckUnlockCheck.deck())) {
			return true;
		} else {
			return !(!"ALL DECKS UNLOCKED".equals(deckUnlockCheck.deck()) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_I) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_II) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_III) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_I) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_II) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_III) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_IV) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_V) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.RANDOM_UPGRADE) ||
					!checkDeckUnlockProgressForDeck(StartingDeck.METRONOME));
		}
	}

	public static boolean isUnlockAllDecksButtonNeeded() {
		LoadoutUnlockOrderInfo deckUnlockCheck = StartingDeck.getNextUnlockDeckAndScore(duelistScore);
        return !"ALL DECKS UNLOCKED".equals(deckUnlockCheck.deck()) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_I) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_II) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.ASCENDED_III) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_I) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_II) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_III) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_IV) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.PHARAOH_V) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.RANDOM_UPGRADE) ||
				!checkDeckUnlockProgressForDeck(StartingDeck.METRONOME);
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
		pots.add(new DeckJuice());
		pots.add(new BeastSwarmPotion());
		pots.add(new AgilityJuiceUncommon());
		pots.add(new AgilityJuiceRare());
		pots.add(new PackMentalityPotion());
		for (AbstractPotion p : pots) {
			if (p instanceof DuelistPotion) {
				DuelistPotion dp = (DuelistPotion)p;
				allDuelistPotionsForOutput.add(dp);
				allDuelistPotionIds.add(p.ID);
				DuelistConfigurationData config = dp.getConfigurations();
				if (config != null) {
					potionConfigurations.add(config);
				}
				PotionConfigData addToMap;
				if (!persistentDuelistData.PotionConfigurations.getPotionConfigurations().containsKey(p.ID)) {
					addToMap = dp.getDefaultConfig();
				} else {
					PotionConfigData base = dp.getDefaultConfig();
					PotionConfigData active = dp.getActiveConfig();
					PotionConfigData toAdd = new PotionConfigData();
					toAdd.setIsDisabled(active.getIsDisabled());
					for (Map.Entry<String, Object> entry : base.getProperties().entrySet()) {
						if (active.getProperties().containsKey(entry.getKey())) {
							toAdd.getProperties().put(entry.getKey(), active.getProperties().get(entry.getKey()));
						} else {
							toAdd.getProperties().put(entry.getKey(), entry.getValue());
						}
					}
					addToMap = toAdd;
				}
				persistentDuelistData.PotionConfigurations.getPotionConfigurations().put(p.ID, addToMap);
				dp.updateConfigSettings(addToMap);
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
				PotionConfigData addToMap;
				if (!persistentDuelistData.PotionConfigurations.getPotionConfigurations().containsKey(p.ID)) {
					addToMap = dp.getDefaultConfig();
				} else {
					PotionConfigData base = dp.getDefaultConfig();
					PotionConfigData active = dp.getActiveConfig();
					PotionConfigData toAdd = new PotionConfigData();
					toAdd.setIsDisabled(active.getIsDisabled());
					for (Map.Entry<String, Object> entry : base.getProperties().entrySet()) {
						if (active.getProperties().containsKey(entry.getKey())) {
							toAdd.getProperties().put(entry.getKey(), active.getProperties().get(entry.getKey()));
						} else {
							toAdd.getProperties().put(entry.getKey(), entry.getValue());
						}
					}
					addToMap = toAdd;
				}
				persistentDuelistData.PotionConfigurations.getPotionConfigurations().put(p.ID, addToMap);
				dp.updateConfigSettings(addToMap);
			}
			duelistPotionMap.put(p.ID, p); orbPotionIDs.add(p.ID); allDuelistPotions.add(p);BaseMod.addPotion(p.getClass(), Colors.WHITE, Colors.WHITE, Colors.WHITE, p.ID, TheDuelistEnum.THE_DUELIST);
		}
		pots.clear();
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

		// Duelist Relics
		allRelics.add(new AeroRelic());
		allRelics.add(new AknamkanonsEssence());
		allRelics.add(new AquaRelic());
		allRelics.add(new AquaRelicB());
		allRelics.add(new BlessingAnubis());
		//allRelics.add(new BoosterBetterBoostersRelic());
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
		//allRelics.add(new SummonAnchor());
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
		allRelics.add(new ModdedDuelDisk());
		allRelics.add(new RandomTributeMonsterRelic());
		allRelics.add(new FlameMedallion());
		allRelics.add(new ApexToken());
		allRelics.add(new NaturesGift());
		allRelics.add(new ClawedCodex());
		allRelics.add(new EruptionToken());
		allRelics.add(new VolcanoToken());
		allRelics.add(new ChronicleOfElders());
		allRelics.add(new SphinxInsight());
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
				DuelistRelic dr = (DuelistRelic) r;
				RelicConfigData addToMap;
				if (!persistentDuelistData.RelicConfigurations.getRelicConfigurations().containsKey(r.relicId)) {
					addToMap = dr.getDefaultConfig();
				} else {
					RelicConfigData base = dr.getDefaultConfig();
					RelicConfigData active = dr.getActiveConfig();
					RelicConfigData toAdd = new RelicConfigData();
					toAdd.setIsDisabled(active.getIsDisabled());
					for (Map.Entry<String, Object> entry : base.getProperties().entrySet()) {
						if (active.getProperties().containsKey(entry.getKey())) {
							toAdd.getProperties().put(entry.getKey(), active.getProperties().get(entry.getKey()));
						} else {
							toAdd.getProperties().put(entry.getKey(), entry.getValue());
						}
					}
					addToMap = toAdd;
				}
				persistentDuelistData.RelicConfigurations.getRelicConfigurations().put(r.relicId, addToMap);
				dr.updateConfigSettings(addToMap);
			}
		}

		// Base Game Shared Relics
		allRelics.add(new Brimstone());
		allRelics.add(new ChampionsBelt());
		allRelics.add(new CharonsAshes());
		allRelics.add(new GoldPlatedCables());
		//allRelics.add(new HoveringKite());
		//allRelics.add(new Inserter());
		//allRelics.add(new MagicFlower());
		allRelics.add(new MarkOfPain());
		allRelics.add(new PaperCrane());
		allRelics.add(new PaperFrog());
		allRelics.add(new RedSkull());
		//allRelics.add(new RunicCapacitor());
		//allRelics.add(new RunicCube());
		allRelics.add(new SelfFormingClay());
		allRelics.add(new SneckoSkull());
		//allRelics.add(new Tingsha());
		//allRelics.add(new ToughBandages());
		//allRelics.add(new TwistedFunnel());
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
		BaseMod.addDynamicVariable(new BeastDrawCount());
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

		// ================ SUMMON MAP ===================
		DuelistCardLibrary.fillSummonMap(myCards);
		// ================ COMPENDIUM SETUP ===================
		DuelistCardLibrary.addCardsToGame();
		// ================ COLORED CARDS ===================

		StartingDeck.refreshStartingDecksData();

		for (StartingDeck deck : StartingDeck.values()) {
			DuelistConfigurationData config = deck.getConfigMenu();
			if (config != null) {
				puzzleConfigurations.add(config);
			}

			PuzzleConfigData addToMap;
			if (!persistentDuelistData.PuzzleConfigurations.getPuzzleConfigurations().containsKey(deck.getDeckId())) {
				addToMap = deck.getDefaultPuzzleConfig();
			} else {
				PuzzleConfigData base = deck.getDefaultPuzzleConfig();
				PuzzleConfigData active = deck.getActiveConfig();
				PuzzleConfigData toAdd = new PuzzleConfigData();
				toAdd.setStats(active.getStats());
				toAdd.setDeck(active.getDeck());
				for (Map.Entry<String, Object> entry : base.getProperties().entrySet()) {
					if (active.getProperties().containsKey(entry.getKey())) {
						toAdd.getProperties().put(entry.getKey(), active.getProperties().get(entry.getKey()));
					} else {
						toAdd.getProperties().put(entry.getKey(), entry.getValue());
					}
				}
				addToMap = toAdd;
			}
			persistentDuelistData.PuzzleConfigurations.getPuzzleConfigurations().put(deck.getDeckId(), addToMap);
			deck.updateConfigSettings(addToMap);
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
	public void receiveEditKeywords() {
		String loc = Localization.localize();
        String json = Gdx.files.internal("duelistModResources/localization/" + loc + "/DuelistMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));

		try {
			duelistKeywords = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
					.readValue(json, new TypeReference<List<DuelistKeyword>>(){});
			for (DuelistKeyword keyword : duelistKeywords) {
				if (keyword != null) {
					if (keyword.PROPER_NAME != null && keyword.NAMES != null && keyword.DESCRIPTION != null) {
						BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
						if (keyword.PROPER_NAME.equals("Special Summon")) {
							specialSummonKeywordDescription = keyword.DESCRIPTION;
						}
					}
					if (keyword.MULTIWORD_KEY != null && keyword.FORMATTED_DISPLAY != null && keyword.BASE_KEYWORD != null) {
						duelistKeywordMultiwordKeyMap.put(keyword.MULTIWORD_KEY, keyword);
					}
				}
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error reading keywords JSON");
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
        HashMap<String,Sfx> map = ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put(id, new Sfx(path, false));
    }

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return "theDuelist:" + idText;
	}

	@SuppressWarnings("SameParameterValue")
	private static <T> void subscribeIfInstance(ArrayList<T> list, ISubscriber sub, Class<T> clazz) {
		if (clazz.isInstance(sub)) {
			list.add(clazz.cast(sub));
		}
	}

	public static void subscribe(ISubscriber sub) {
		subscribeIfInstance(incrementDiscardSubscribers, sub, IncrementDiscardSubscriber.class);
	}

	@SuppressWarnings("SameParameterValue")
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

	// ANY DUELIST UPDATE
	@Override
	public void receiveOnBattleStart(AbstractRoom arg0)
	{
		if (replacedCardPool) {
			replacedCardPool = false;
			BoosterHelper.refreshPool();
		}
		unblockedDamageTakenLastTurn = false;
		unblockedDamageTakenThisTurn = false;
		unblockedDamageTriggerCheck = false;
		enemyDuelistUnblockedDamageTakenLastTurn = false;
		enemyDuelistUnblockedDamageTakenThisTurn = false;
		enemyDuelistUnblockedDamageTriggerCheck = false;
		uniqueBeastsPlayedThisTurn.clear();
		entombBattleStartHandler();
		TheDuelist.setAnimationSpeed(persistentDuelistData.VisualSettings.getAnimationSpeed());
		Util.removeRelicFromPools(PrismaticShard.ID);
		TheDuelist.resummonPile.group.clear();
		beastsDrawnByTurn.clear();
		enemyBeastsDrawnByTurn.clear();
		puzzleEffectRanThisCombat = false;
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
		kuribohCardsPlayedThisCombat.clear();
		lastMonsterPlayedThisCombat = null;
		lastEnemyDuelistMonsterPlayedThisCombat = null;
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
			pow.setMaxSummons(defaultMaxSummons);
		}
		spellCombatCount = 0;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		overflowsThisCombat = 0;
		poisonAppliedThisCombat = 0;
		zombiesResummonedThisCombat = 0;
		godsPlayedForBonus = 0;
		drawExtraCardsAtTurnStartThisBattle = 0;
		firstCardResummonedThisCombat = new CancelCard();
		firstMonsterResummonedThisCombat = new CancelCard();
		godsPlayedNames = new ArrayList<>();
		enduringCards.clear();

		beastsDrawnThisTurn = 0;
		enemyBeastsDrawnThisTurn = 0;
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
		monstersPlayedCombatNames = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		metronomeResummonsThisCombat = new ArrayList<>();
		playedOneCardThisCombat = false;
		lastMaxSummons = defaultMaxSummons;
		currentZombieSouls = defaultStartZombieSouls;
		spellCombatCount = 0;
		summonLastCombatCount = summonCombatCount;
		tributeLastCombatCount = tribCombatCount;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0)
	{
		Util.genesisDragonHelper();
		for (AbstractPotion p : AbstractDungeon.player.potions) { if (p instanceof DuelistPotion) { ((DuelistPotion)p).onEndOfBattle(); }}
		// Reset some settings
		beastsDrawnThisTurn = 0;
		enemyBeastsDrawnThisTurn = 0;
		drawExtraCardsAtTurnStartThisBattle = 0;
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
		monstersPlayedCombatNames = new ArrayList<>();
		uniqueSpellsThisCombat = new ArrayList<>();
		metronomeResummonsThisCombat = new ArrayList<>();
		playedOneCardThisCombat = false;
		lastMaxSummons = defaultMaxSummons;
		currentZombieSouls = defaultStartZombieSouls;
		spellCombatCount = 0;
		summonLastCombatCount = summonCombatCount;
		tributeLastCombatCount = tribCombatCount;
		summonCombatCount = 0;
		sevenCompletedsThisCombat = 0;
		tribCombatCount = 0;
		swordsPlayed = 0;
		kuribohCardsPlayedThisCombat.clear();
		lastMonsterPlayedThisCombat = null;
		lastEnemyDuelistMonsterPlayedThisCombat = null;

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
			config.setInt("currentStartingDeck", StartingDeck.currentDeck.ordinal());
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt("defaultMaxSummons", defaultMaxSummons);
			config.setString("entombed", entombedCardsThisRunList);
			config.setString("entombedCustomCardProperites", entombedCustomCardProperites);
			config.setString(PROP_MONSTERS_RUN, loadedUniqueMonstersThisRunList);
			config.setString(PROP_SPELLS_RUN, loadedSpellsThisRunList);
			config.setString(PROP_TRAPS_RUN, loadedTrapsThisRunList);
			DuelistTipHelper.saveTips(config);
			if (isHighlightPath) {
				HighlightPathHelper.onSave();
			}
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void onLoad() {
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			try {
				int deckOrdinal = config.getInt("currentStartingDeck");
				StartingDeck.loadDeck(deckOrdinal);
			} catch (Exception deckEx) {
				Util.logError("Error parsing current deck from save file!", deckEx);
			}
			runUUID = config.getString(PROP_RUN_UUID);
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
			if (isHighlightPath) {
				HighlightPathHelper.onLoad();
			}
		} catch (Exception e) {
			Util.logError("Exception while loading data on startup", e);
		}
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature target, AbstractCreature source)
	{
		if (power != null && power.owner != null) {
			AnyDuelist duelist = AnyDuelist.from(power);
			if (duelist.player() || duelist.getEnemy() != null) {
				if (duelist.hasRelic(CursedHealer.ID)) {
					if (AbstractDungeon.cardRandomRng.random(1, 6) == 1) {
						DuelistCard.applyPowerToSelf(new StrengthPower(duelist.creature(), -1));
						duelist.getRelic(CursedHealer.ID).flash();
					}
				}

				if (power instanceof SlowPower && duelist.player() && persistentDuelistData.VisualSettings.getAnimationSpeed() > 0) {
					int currentAmt = duelist.getPlayer().hasPower(SlowPower.POWER_ID) ? duelist.getPlayer().getPower(SlowPower.POWER_ID).amount : 0;
					float mod = (float)currentAmt / 10;
					if (mod > 0) {
						float newVal = mod >= 1 ? 0.9f : (persistentDuelistData.VisualSettings.getAnimationSpeed() * mod);
						TheDuelist.setAnimationSpeed(newVal);
					}
				}

				if (power instanceof FocusUpPower) {
					DuelistCard.applyPower(new FocusPower(power.owner, power.amount), power.owner);
				}

				if (power instanceof StrengthUpPower) {
					DuelistCard.applyPower(new StrengthPower(power.owner, power.amount), power.owner);
				}

				if (power instanceof BloodUpPower) {
					DuelistCard.applyPower(new BloodPower(power.owner, power.owner, power.amount), power.owner);
				}

				if (power instanceof ElectricityUpPower) {
					DuelistCard.applyPower(new ElectricityPower(power.owner, power.owner, power.amount), power.owner);
				}

				if (power instanceof OverworkedPower) {
					OverworkedPower pow = (OverworkedPower)power;
					int strGain = pow.strGain;
					DuelistCard.applyPowerToSelf(new StrengthPower(duelist.creature(), strGain));
				}

				if (power instanceof MegaconfusionPower) {
					MegaconfusionPower pow = (MegaconfusionPower)power;
					pow.statRescrambler();
				}

				if (power instanceof TombLooterPower) {
					if (duelist.hasPower(TombLooterPower.POWER_ID) && duelist.player()) {
						TombLooterPower pow = (TombLooterPower)duelist.getPower(TombLooterPower.POWER_ID);
						((TombLooterPower) power).goldGainedThisCombat = pow.goldGainedThisCombat;
						((TombLooterPower) power).goldLimit = pow.goldLimit;
					}
				}

				if (power instanceof StrengthPower && power.amount > 0) {
					if (!duelist.hasPower(GravityAxePower.POWER_ID) && duelist.hasRelic(MetronomeRelicD.ID)) {
						MetronomeRelicD relic = (MetronomeRelicD)duelist.getRelic(MetronomeRelicD.ID);
						relic.addMetToHand();
					}
				}

				if (power instanceof DexterityPower)
				{
					if (power.amount > 0)
					{
						if (duelist.stance() instanceof DuelistStance) {
							DuelistStance stance = (DuelistStance) duelist.stance();
							stance.onGainDex(power.amount);
						}

						for (AbstractOrb o : duelist.orbs())
						{
							if (o instanceof DuelistOrb)
							{
								((DuelistOrb)o).onGainDex(power.amount);
							}
						}
					}

					for (AbstractPower pow : duelist.powers())
					{
						if (pow instanceof DuelistPower)
						{
							((DuelistPower)pow).onDexChange();
						}
					}
				}

				if (power instanceof VinesPower) {
					VinesLeavesMod vinesOption = VinesLeavesMod.vinesOption();
					Util.leavesVinesCommonOptionHandler(vinesOption, duelist);
					VinesPower vp = (VinesPower)power;
					if (!vp.skipConfigChecks) {
						boolean isLeavesAsWell =
								vinesOption == VinesLeavesMod.GAIN_THAT_MANY_LEAVES_AS_WELL ||
										vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_AS_WELL ||
										vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL;
						boolean halfAsMuch =
								vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_INSTEAD ||
										vinesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_LEAVES_AS_WELL ||
										vinesOption == VinesLeavesMod.GAIN_HALF;
						boolean twiceAsMuch =
								vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_INSTEAD ||
										vinesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_LEAVES_AS_WELL ||
										vinesOption == VinesLeavesMod.GAIN_TWICE_AS_MANY;
						int amount = halfAsMuch ? power.amount / 2 : twiceAsMuch ? power.amount * 2 : power.amount;
						if (isLeavesAsWell) {
							DuelistCard.applyPowerToSelf(new LeavesPower(duelist.creature(), amount, true));
						}
					}
				}

				if (power instanceof LeavesPower) {
					VinesLeavesMod leavesOption = VinesLeavesMod.leavesOption();
					Util.leavesVinesCommonOptionHandler(leavesOption, duelist);
					LeavesPower lp = (LeavesPower)power;
					if (!lp.skipConfigChecks) {
						boolean isVinesAsWell =
								leavesOption == VinesLeavesMod.GAIN_THAT_MANY_VINES_AS_WELL ||
										leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_AS_WELL ||
										leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_AS_WELL;
						boolean halfAsMuch =
								leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_INSTEAD ||
										leavesOption == VinesLeavesMod.GAIN_HALF_THAT_MANY_VINES_AS_WELL ||
										leavesOption == VinesLeavesMod.GAIN_HALF;
						boolean twiceAsMuch =
								leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_INSTEAD ||
										leavesOption == VinesLeavesMod.GAIN_TWICE_THAT_MANY_VINES_AS_WELL ||
										leavesOption == VinesLeavesMod.GAIN_TWICE_AS_MANY;
						int amount = halfAsMuch ? power.amount / 2 : twiceAsMuch ? power.amount * 2 : power.amount;
						if (isVinesAsWell) {
							DuelistCard.applyPowerToSelf(new VinesPower(duelist.creature(), amount, true));
						}
					}
				}

				if (power instanceof VinesPower && power.amount > 0)
				{
					VinesPower vp = (VinesPower)power;
					if (!vp.naturalDisaster) {
						for (AbstractPower pow : duelist.powers()) {
							if (pow instanceof DuelistPower) {
								((DuelistPower)pow).onGainVines();
							}
						}
					}
				}

				if (power instanceof FangsPower && power.amount > 0) {
					for (AbstractPower pow : duelist.powers()) {
						if (pow instanceof DuelistPower) {
							((DuelistPower)pow).onGainFangs(power.amount);
						}
					}
				}

				if (power instanceof PoisonPower) {
					if (duelist.player()) {
						poisonAppliedThisCombat+=power.amount;
					} else if (duelist.getEnemy() != null) {
						duelist.getEnemy().counters.compute(EnemyDuelistCounter.POISON_APPLIED, (k,v)->v==null?1:v+1);
					}
				}

				for (AbstractOrb o : duelist.orbs())
				{
					if (o instanceof DuelistOrb)
					{
						((DuelistOrb)o).onPowerApplied(power);
					}
				}
			}
		}
	}

	@Override
	public void receivePowersModified()
	{
		Consumer<AbstractOrb> checkFocus = (o) -> { if (o instanceof DuelistOrb) { ((DuelistOrb)o).checkFocus(); }};
		for (AbstractOrb o : AbstractDungeon.player.orbs) {
			checkFocus.accept(o);
		}
		if (AbstractEnemyDuelist.enemyDuelist != null) {
			for (AbstractOrb o : AbstractEnemyDuelist.enemyDuelist.orbs) {
				checkFocus.accept(o);
			}
		}
	}

	@Override
	public void receivePostDeath() {}

	@Override
	public void receiveCardUsed(AbstractCard arg0) {
		new AnyDuelist(AbstractDungeon.player).receiveCardUsed(arg0);
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
				if (persistentDuelistData.GameplaySettings.getHolidayCards() && holidayDeckCard != null && addingHolidayCard && arg0.name().equals("THE_DUELIST")) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
			}
		}
		if (!badMods)
		{
			if (arg0.name().equals("THE_DUELIST"))
			{
				ArrayList<AbstractCard> startingDeckB = new ArrayList<>();
				ArrayList<AbstractCard> startingDeck;
				switch (StartingDeck.currentDeck) {
					case RANDOM_BIG:
					case RANDOM_SMALL:
					case RANDOM_UPGRADE:
						startingDeck = StartingDeck.getStartingCardsForRandomDeck();
						break;
					default:
						startingDeck = new ArrayList<>(StartingDeck.currentDeck.startingDeck());
						break;
				}

				if (startingDeck.size() > 0) {
					arg1.group.clear();
					CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
					boolean addedSpecialSparks = false;
					for (AbstractCard c : startingDeck)
					{
						if (c instanceof Sparks) {
							int roll = ThreadLocalRandom.current().nextInt(1, 20);
							if (Util.getChallengeLevel() > 9) { roll = 2; }
							if (
								(DuelistMod.persistentDuelistData.GameplaySettings.getForceSpecialSparks() && !addedSpecialSparks) ||
								(DuelistMod.persistentDuelistData.GameplaySettings.getAllowSpecialSparks() && roll == 1)
							) {
								startingDeckB.add(Util.getSpecialSparksCard());
								addedSpecialSparks = true;
							} else {
								startingDeckB.add(c);
							}
						} else {
							startingDeckB.add(c);
						}
					}
					if (DuelistMod.persistentDuelistData.GameplaySettings.getForceSpecialSparks() && !addedSpecialSparks) {
						startingDeckB.add(Util.getSpecialSparksCard());
					}
					for (AbstractCard c : startingDeckB)
					{
						newStartGroup.addToRandomSpot(c.makeStatEquivalentCopy());
					}

					if (Util.getChallengeLevel() > 9)
					{
						DuelistCard da = new DuelistAscender();
						newStartGroup.addToRandomSpot(da);
						UnlockTracker.unlockCard(da.cardID);
					}
					else if (AbstractDungeon.ascensionLevel >= 10)
					{
						newStartGroup.addToRandomSpot(new AscendersBane());
						UnlockTracker.markCardAsSeen("AscendersBane");
					}

					if (DuelistMod.getMonsterSetting(MonsterType.MAGNET, MonsterType.magnetDeckKey, MonsterType.magnetDefaultDeck)) {
						DuelistCard magnet = Util.getRandomMagnetCard(DuelistMod.getMonsterSetting(MonsterType.MAGNET, MonsterType.magnetSuperKey, MonsterType.magnetDefaultSuper));
						newStartGroup.addToRandomSpot(magnet);
					}
					arg1.group.addAll(newStartGroup.group);
					if (persistentDuelistData.GameplaySettings.getHolidayCards() && holidayDeckCard != null && addingHolidayCard) { arg1.group.add(holidayDeckCard.makeCopy()); addingHolidayCard = false; }
					arg1.sortAlphabetically(true);
					lastTagSummoned = StartingDeck.currentDeck.getStartingDeckTag();
					if (lastTagSummoned == null) { lastTagSummoned = Tags.ALL; if (debug) { logger.info("starter deck has no associated card tag, so lastTagSummoned is reset to default value of ALL");}}

					if (StartingDeck.currentDeck == StartingDeck.EXODIA)
					{
						PuzzleConfigData config = StartingDeck.currentDeck.getActiveConfig();
						for (AbstractCard c : arg1.group)
						{
							if (c.hasTag(Tags.EXODIA_DECK_UPGRADE))
							{
								c.upgrade();
							}

							if (c instanceof DuelistCard)
							{
								DuelistCard dc = (DuelistCard)c;
								if (config.getApplySoulbound() != null && config.getApplySoulbound()) {
									dc.makeSoulbound(true);
									dc.rawDescription = Strings.exodiaSoulbound + dc.rawDescription;
								}
								dc.fixUpgradeDesc();
								dc.initializeDescription();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void receiveRelicGet(AbstractRelic arg0)
	{
		if (arg0 instanceof DuelistPrismaticShard && AbstractDungeon.player.hasRelic(CardPoolRelic.ID))
		{
			AbstractDungeon.player.getRelic(CardPoolRelic.ID).flash();
		}

		if (arg0 instanceof QuestionCard)
		{
			if (persistentDuelistData.CardPoolSettings.getAllowBoosters() || persistentDuelistData.CardPoolSettings.getAlwaysBoosters())
			{
				BoosterHelper.modifyPackSize(1);
				Util.log("Question Card --> incremented booster pack size");
			}
		}

		if (arg0 instanceof BustedCrown)
		{
			if (persistentDuelistData.CardPoolSettings.getAllowBoosters() || persistentDuelistData.CardPoolSettings.getAlwaysBoosters())
			{
				BoosterHelper.modifyPackSize(-2);
				Util.log("Busted Crown --> decremented booster pack size");
			}
		}
	}

	@Override
	public void receivePostDraw(AbstractCard drawnCard) {
		if (drawnCard instanceof DuelistCard) {
			((DuelistCard)drawnCard).onDraw();
		}

		AnyDuelist duelist = AnyDuelist.from(drawnCard);
		DuelistCard.handleOnDrawnForAllAbstracts(drawnCard, duelist);

		if (drawnCard.hasTag(Tags.BEAST)) {
			if (duelist.player()) {
				beastsDrawnThisTurn++;
			} else if (duelist.getEnemy() != null) {
				enemyBeastsDrawnThisTurn++;
			}
		}

		if (drawnCard.hasTag(Tags.MALICIOUS)) {
			if (duelist.player()) {
				for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
					if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead && AbstractDungeon.cardRandomRng.random(1, 3) == 1) {
						DuelistCard.applyPower(new VulnerablePower(mon, 1, false), mon);
					}
				}
			} else if (duelist.getEnemy() != null && AbstractDungeon.cardRandomRng.random(1, 3) == 1) {
				duelist.applyPower(AbstractDungeon.player, duelist.creature(), new VulnerablePower(AbstractDungeon.player, 1, true));
			}
		}

		if (drawnCard.hasTag(Tags.FLUVIAL)) {
			if (duelist.discardPile().size() > 0) {
				int size = duelist.discardPile().size();
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) {
					DuelistCard.gainTempHP(duelist.creature(), duelist.creature(), size);
				}
			}
		}

		if (drawnCard.hasTag(Tags.THALASSIC)) {
			if (AbstractDungeon.cardRandomRng.random(1, 5) == 1) {
				duelist.channel(new WaterOrb());
			}
		}

		if (duelist.player() && drawnCard.hasTag(Tags.PELAGIC)) {
			if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) {
				AbstractDungeon.actionManager.addToBottom(new TsunamiAction(1));
			}
		}

		if (duelist.hasPower(GridRodPower.POWER_ID)) {
			GridRodPower pow = ((GridRodPower)duelist.getPower(GridRodPower.POWER_ID));
			if (pow.ready) {
				pow.trigger(drawnCard);
			}
		}
		if (duelist.stance() instanceof DuelistStance) {
			DuelistStance stance = (DuelistStance)duelist.stance();
			stance.onDrawCard(drawnCard);
		}

		for (AbstractOrb o : duelist.orbs()) {
        	if (o instanceof DuelistOrb) {
        		((DuelistOrb)o).onDrawCard(drawnCard);
        	}
        }
		if (duelist.player()) {
			secondLastCardDrawn = lastCardDrawn;
			lastCardDrawn = drawnCard;
		}

		for (AbstractOrb orb : duelist.orbs()) {
			if ((orb instanceof WhiteOrb) && (drawnCard.hasTag(Tags.SPELL) || drawnCard.hasTag(Tags.TRAP))) {
				WhiteOrb white = (WhiteOrb)orb;
				white.triggerPassiveEffect(drawnCard);
				if (white.gpcCheck()) {
					white.triggerPassiveEffect(drawnCard);
				}
			}
		}

		if (drawnCard.hasTag(Tags.MONSTER) && drawnCard instanceof DuelistCard) {
			DuelistCard dc = (DuelistCard)drawnCard;
			for (AbstractOrb orb : duelist.orbs()) {
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

			if (dc.isTributeCard(true) && duelist.hasRelic(NamelessWarRelicC.ID)) {
				AbstractRelic r = duelist.getRelic(NamelessWarRelicC.ID);
				r.flash();
				duelist.applyPowerToSelf(new StrengthPower(duelist.creature(), 1));
			}

			// Underdog - Draw monster = draw 1 card
			if (duelist.hasPower(HeartUnderdogPower.POWER_ID))
			{
				int handSize = duelist.hand().size();
				if (handSize < BaseMod.MAX_HAND_SIZE) {
					duelist.draw(1);
				}
			}

			if (duelist.hasPower(DrillBarnaclePower.POWER_ID) && drawnCard.hasTag(Tags.AQUA)) {
				if (duelist.player()) {
					DuelistCard.damageAllEnemiesThornsNormal(AbstractDungeon.player.getPower(DrillBarnaclePower.POWER_ID).amount);
				} else if (duelist.getEnemy() != null) {
					((DuelistCard) drawnCard).attack(AbstractDungeon.player, ((DuelistCard) drawnCard).baseAFX, duelist.getPower(DrillBarnaclePower.POWER_ID).amount);
				}
			}

			if (duelist.hasPower(FutureFusionPower.POWER_ID)) {
				if (duelist.getPower(FutureFusionPower.POWER_ID).amount > 0 && DuelistCard.allowResummonsWithExtraChecks(drawnCard))
				{
					AbstractPower pow = duelist.getPower(FutureFusionPower.POWER_ID);
					pow.amount--;
					pow.updateDescription();
					AbstractMonster m = AbstractDungeon.getRandomMonster();
					if (m != null) {
						if (duelist.player()) {
							DuelistCard.resummon(drawnCard, m, 1);
						} else if (duelist.getEnemy() != null) {
							DuelistCard.anyDuelistResummon(drawnCard, duelist, AbstractDungeon.player);
						}
					}
				} else if (duelist.getPower(FutureFusionPower.POWER_ID).amount < 1) {
					AbstractPower pow = duelist.getPower(FutureFusionPower.POWER_ID);
					DuelistCard.removePower(pow, pow.owner);
				} else {
					Util.log("Future Fusion is skipping the Resummon of " + drawnCard.cardID + " because that card cannot be Resummoned currently for some reason (Exempt, anti-resummon powers/effects, etc.)");
				}
			}
		}

		// Underspell - Draw spell = copy it
		if (duelist.hasPower(HeartUnderspellPower.POWER_ID)) {
			int handSize = duelist.hand().size();
			if (drawnCard.hasTag(Tags.SPELL) && handSize < 10) {
				if (duelist.player()) {
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(drawnCard.makeStatEquivalentCopy(), drawnCard.upgraded, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
				} else if (duelist.getEnemy() != null) {
					duelist.addCardToHand(drawnCard.makeStatEquivalentCopy());
				}
			}
		}

		// Undertrap - Draw trap = gain 3 HP
		if (duelist.hasPower(HeartUndertrapPower.POWER_ID)) {
			if (duelist.getPower(HeartUndertrapPower.POWER_ID).amount > 0) {
				if (drawnCard.hasTag(Tags.TRAP)) {
					AbstractDungeon.actionManager.addToTop(new HealAction(duelist.creature(), duelist.creature(), 3));
				}
			}
		}

		// Undertribute - Draw tribute monster = Summon 1
		if (duelist.hasPower(HeartUndertributePower.POWER_ID)) {
			if (drawnCard instanceof DuelistCard && drawnCard.hasTag(Tags.MONSTER)) {
				DuelistCard ref = (DuelistCard) drawnCard;
				if (ref.isTributeCard()) {
					DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new UnderdogToken());
					DuelistCard.summon(duelist.creature(), 1, tok);
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

	public static int onLoseBlockLogic(int arg0, AnyDuelist duelist) {
		if (duelist.stance() instanceof DuelistStance) {
			DuelistStance stance = (DuelistStance)duelist.stance();
			stance.onLoseBlock(arg0);
		}

		for (AbstractOrb o : duelist.orbs()) {
			if (o instanceof DuelistOrb) {
				((DuelistOrb)o).onLoseBlock(arg0);
			}
		}
		return arg0;
	}

	@Override
	public int receiveOnPlayerLoseBlock(int arg0) {
		return onLoseBlockLogic(arg0, AnyDuelist.from(AbstractDungeon.player));
	}

	public static boolean preMonsterTurnLogic(AnyDuelist duelist) {
		if (duelist.player()) {
			playedSpellThisTurn = false;
			summonedTypesThisTurn = new ArrayList<>();
			kuribohrnFlipper = false;
			secondLastTurnHP = lastTurnHP;
			lastTurnHP = AbstractDungeon.player.currentHealth;
			if (overflowedThisTurn) { overflowedLastTurn = true; overflowedThisTurn = false; }
			else { overflowedLastTurn = false; }
		}

		// Fix tributes & summons & magic nums that were modified for turn only
		for (AbstractCard c : duelist.discardPile())
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}

		for (AbstractCard c : duelist.hand())
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}

		for (AbstractCard c : duelist.drawPile())
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}

		for (AbstractCard c : duelist.exhaustPile())
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}

		for (AbstractCard c : duelist.resummonPile())
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard dC = (DuelistCard)c;
				dC.postTurnReset();
			}
		}

		if (duelist.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)duelist.getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.getCardsSummoned()) {
				c.postTurnReset();
			}
		}

		// Check to maybe print secret message
		int turnSummons = duelist.player() ? summonTurnCount : duelist.getEnemy() != null ? duelist.getEnemy().summonTurnCount : 0;
		if (turnSummons > 2) {
			int msgRoll = AbstractDungeon.cardRandomRng.random(1, 100);
			if (duelist.player()) {
				AbstractMonster m = AbstractDungeon.getRandomMonster();
				if ((debugMsg || msgRoll <= 2) && m != null) {
					AbstractDungeon.actionManager.addToBottom(new TalkAction(m, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Screw the rules, I have money!", 1.0F, 2.0F));
				}
			} else if (duelist.getEnemy() != null) {
				if ((debugMsg || msgRoll <= 2)) {
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 3.5F, 3.0F));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(duelist.creature(), "Screw the rules, I have money!", 1.0F, 2.0F));
				}
			}
		}

		if (duelist.player()) {
			summonTurnCount = 0;
		}

		// Mirror Force Helper
		if (duelist.hasPower(MirrorForcePower.POWER_ID)) {
			MirrorForcePower instance = (MirrorForcePower) duelist.getPower(MirrorForcePower.POWER_ID);
			instance.PLAYER_BLOCK = duelist.creature().currentBlock;
		}
		return true;
	}

	@Override
	public boolean receivePreMonsterTurn(AbstractMonster arg0) {
		return preMonsterTurnLogic(AnyDuelist.from(AbstractDungeon.player));
	}

	// ANY DUELIST UPDATE
	public static void onVeryEndOfMonsterTurn(AbstractMonster m) {
		if (m.hasPower(IceHandPower.POWER_ID)) {
			IceHandPower pow = (IceHandPower)m.getPower(IceHandPower.POWER_ID);
			pow.trigger();
		}
	}

	public static void resetAfterRun() {
		if (isHighlightPath) {
			HighlightPathHelper.reset();
		}
		StartingDeck.currentDeck.resetColoredPool();
		BoosterHelper.setPackSize(5);
		Util.resetCardsPlayedThisRunLists();
		Util.log("Ended run, so we are resetting various DuelistMod properties, as well as resetting the card pool");
		AbstractPlayer.customMods = new ArrayList<>();
		CustomModeScreenPatches.draftLimit = 15;
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
		sevenCompletedsThisCombat = 0;
		shiranuiPlayed = 0;
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
		trapsObtained = 0;
		tribCombatCount = 0;
		tribRunCount = 0;
		tributeLastCombatCount = 0;
		uniqueMonstersThisRun.clear();
		uniqueSpellsThisCombat.clear();
		uniqueSpellsThisRun.clear();
		uniqueTrapsThisRun.clear();
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
		if (Util.getChallengeDiffIndex() > -1 && !DuelistMod.playingChallenge) {
			Util.setChallengeLevel((Util.getChallengeDiffIndex() * 5) - 5);
			DuelistMod.playingChallenge = true;
		}
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
		if (isHighlightPath) {
			HighlightPathHelper.reset();
		}
		if (AbstractDungeon.floorNum <= 1) {
			for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
				if (c.hasTag(Tags.MONSTER)) { monstersObtained++; }
				if (c.hasTag(Tags.SPELL)) { spellsObtained++; }
				if (c.hasTag(Tags.TRAP)) { trapsObtained++; }
			}
		}
		if (AbstractDungeon.getCurrMapNode() != null) {
			BoosterHelper.refreshPool();
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
		pages.add(new MonsterTypeConfigs());
		pages.add(new CardConfigs());
		pages.add(new RelicConfigs());
		pages.add(new PotionConfigs());
		pages.add(new OrbConfigs());
		pages.add(new EventConfigs());
		pages.add(new PuzzleConfigs());
		// Booster configs
		// Power configs
		pages.add(new StanceConfigs());
		pages.add(new Randomized());
		pages.add(new ColorlessShop());
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
		paginator = new DuelistPaginator(2,3, 50,50, settingsPages, pages, pageNames, pageSelector);
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

	public static void getEnemyDuelistModel() {
		boolean kaiba = selectedCharacterModel.isYugi();
		monsterIsKaiba = kaiba;
		kaibaEnemyModel = kaiba ? "KaibaModel2" : "OldYugiEnemy2";
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

	public static void onAbandonRunFromMainMenu(@SuppressWarnings("unused") AbstractPlayer player) {

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
		if (openedModSettings && settingsPanel != null && lastSource != ConfigOpenSource.BASE_MOD) {
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

	public static Map<String, Map<String, List<String>>> getRelicsAndPotionsForAllDecks() {
		Map<String, Map<String, List<String>>> output = new HashMap<>();
		Map<String, List<String>> relics = new HashMap<>();
		Map<String, List<String>> potions = new HashMap<>();
		Set<String> canSpawnUnchecked = new HashSet<>();
		Set<String> canSpawnUncheckedPot = new HashSet<>();
		StartingDeck initCurrentDeck = StartingDeck.currentDeck;
		for (StartingDeck deck : StartingDeck.values()) {
			StartingDeck.currentDeck = deck;
			String deckName = deck.getDeckName();
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
		StartingDeck.currentDeck = initCurrentDeck;
		logger.info("Could not check relic canSpawn() -- " + canSpawnUnchecked);
		logger.info("Could not check potion canSpawn() -- " + canSpawnUncheckedPot);
		output.put("Relics", relics);
		output.put("Potions", potions);
		return output;
	}

	public static boolean isNotAllCardsPoolType() {
		return cardPoolType != CardPoolType.ALL_CARDS;
	}

	public static <T> T getMonsterSetting(MonsterType type, String setting) {
		return getMonsterSetting(type, setting, null);
	}

	public static <T> T getMonsterSetting(MonsterType type, String setting, T defaultValue) {
		try {
			return (T) DuelistMod.persistentDuelistData.MonsterTypeConfigurations.getTypeConfigurations().get(type).getProperties().get(setting);
		} catch (Exception ex) {
			Util.logError("Error attempting to lookup monster type settings. Type=" + type + ", setting=" + setting, ex, true);
		}
		return defaultValue;
	}

	public static boolean checkDeckUnlockProgressForDeck(StartingDeck deck) {
		DeckUnlockProgressDTO progress = persistentDuelistData.deckUnlockProgress;
		switch (deck) {
            case ASCENDED_I:
                return progress.isAscendedDeckOneUnlocked();
            case ASCENDED_II:
				return progress.isAscendedDeckTwoUnlocked();
            case ASCENDED_III:
				return progress.isAscendedDeckThreeUnlocked();
            case PHARAOH_I:
				return progress.isPharaohDeckOneUnlocked();
            case PHARAOH_II:
				return progress.isPharaohDeckTwoUnlocked();
            case PHARAOH_III:
				return progress.isPharaohDeckThreeUnlocked();
            case PHARAOH_IV:
				return progress.isPharaohDeckFourUnlocked();
            case PHARAOH_V:
				return progress.isPharaohDeckFiveUnlocked();
            case RANDOM_UPGRADE:
            case METRONOME:
				return progress.isExtraRandomDecksUnlocked();
			default:
				return true;
        }
	}
}
