package defaultmod;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.interfaces.*;
import defaultmod.actions.common.*;
import defaultmod.cards.*;
import defaultmod.characters.*;
import defaultmod.interfaces.*;
import defaultmod.orbCards.*;
import defaultmod.patches.*;
import defaultmod.potions.*;
import defaultmod.powers.*;
import defaultmod.relics.*;



@SpireInitializer @SuppressWarnings("unused")
public class DefaultMod
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, OnPlayerDamagedSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber
{
	public static final Logger logger = LogManager.getLogger("theDuelist:DefaultMod ---> " + DefaultMod.class.getName());
	public static final String MOD_ID_PREFIX = "theDuelist:";
	
	// Tags
	@SpireEnum public static AbstractCard.CardTags MONSTER; 
	@SpireEnum public static AbstractCard.CardTags SPELL;
	@SpireEnum public static AbstractCard.CardTags TRAP;
	@SpireEnum public static AbstractCard.CardTags TOKEN;
	@SpireEnum public static AbstractCard.CardTags ORB_CARD;
	@SpireEnum public static AbstractCard.CardTags RANDOMONLY;
	@SpireEnum public static AbstractCard.CardTags RANDOMONLY_NOCREATOR;
	@SpireEnum public static AbstractCard.CardTags FIELDSPELL;
	@SpireEnum public static AbstractCard.CardTags EQUIPSPELL;
	@SpireEnum public static AbstractCard.CardTags POT;
	@SpireEnum public static AbstractCard.CardTags TOON;
	@SpireEnum public static AbstractCard.CardTags GUARDIAN;
	@SpireEnum public static AbstractCard.CardTags EXODIA;
	@SpireEnum public static AbstractCard.CardTags MAGNETWARRIOR;
	@SpireEnum public static AbstractCard.CardTags SUPERHEAVY;
	@SpireEnum public static AbstractCard.CardTags DRAGON; 
	@SpireEnum public static AbstractCard.CardTags SPELLCASTER;
	@SpireEnum public static AbstractCard.CardTags OJAMA;
	@SpireEnum public static AbstractCard.CardTags GOD;
	@SpireEnum public static AbstractCard.CardTags AQUA;
	@SpireEnum public static AbstractCard.CardTags ZOMBIE;
	@SpireEnum public static AbstractCard.CardTags FIEND;
	@SpireEnum public static AbstractCard.CardTags TRIBUTE;
	@SpireEnum public static AbstractCard.CardTags NO_PUMPKIN;
	@SpireEnum public static AbstractCard.CardTags GOOD_TRIB;
	@SpireEnum public static AbstractCard.CardTags BAD_TRIB;
	@SpireEnum public static AbstractCard.CardTags CONSPIRE;
	@SpireEnum public static AbstractCard.CardTags REPLAYSPIRE;
	@SpireEnum public static AbstractCard.CardTags LIMITED;
	@SpireEnum public static AbstractCard.CardTags REDUCED;
	@SpireEnum public static AbstractCard.CardTags FULL;
	@SpireEnum public static AbstractCard.CardTags ALL;
	@SpireEnum public static AbstractCard.CardTags NOT_ADDED;
	@SpireEnum public static AbstractCard.CardTags PETIT;
	@SpireEnum public static AbstractCard.CardTags COCOON;
	@SpireEnum public static AbstractCard.CardTags INSECT;
	@SpireEnum public static AbstractCard.CardTags PLANT;
	@SpireEnum public static AbstractCard.CardTags CRASHBUG;
	@SpireEnum public static AbstractCard.CardTags PREDAPLANT;	
	@SpireEnum public static AbstractCard.CardTags ARCHETYPE;
	@SpireEnum public static AbstractCard.CardTags STANDARD_DECK;
	@SpireEnum public static AbstractCard.CardTags DRAGON_DECK;
	@SpireEnum public static AbstractCard.CardTags SPELLCASTER_DECK;
	@SpireEnum public static AbstractCard.CardTags NATURE_DECK;
	@SpireEnum public static AbstractCard.CardTags CREATOR_DECK;
	@SpireEnum public static AbstractCard.CardTags TOON_DECK;
	@SpireEnum public static AbstractCard.CardTags ORB_DECK;
	@SpireEnum public static AbstractCard.CardTags RESUMMON_DECK;
	@SpireEnum public static AbstractCard.CardTags GENERATION_DECK;
	@SpireEnum public static AbstractCard.CardTags OJAMA_DECK;
	@SpireEnum public static AbstractCard.CardTags HEAL_DECK;
	@SpireEnum public static AbstractCard.CardTags INCREMENT_DECK;
	@SpireEnum public static AbstractCard.CardTags EXODIA_DECK;
	@SpireEnum public static AbstractCard.CardTags MAGNET_DECK;
	@SpireEnum public static AbstractCard.CardTags AQUA_DECK;
	@SpireEnum public static AbstractCard.CardTags MACHINE_DECK;
	@SpireEnum public static AbstractCard.CardTags RANDOM_DECK_SMALL;
	@SpireEnum public static AbstractCard.CardTags RANDOM_DECK_BIG;
	@SpireEnum public static AbstractCard.CardTags MAGIC_RULER; 
	@SpireEnum public static AbstractCard.CardTags LEGEND_BLUE_EYES; 
	@SpireEnum public static AbstractCard.CardTags PHARAOH_SERVANT;
	@SpireEnum public static AbstractCard.CardTags METAL_RAIDERS;
	@SpireEnum public static AbstractCard.CardTags LABYRINTH_NIGHTMARE;
	@SpireEnum public static AbstractCard.CardTags LEGACY_DARKNESS;
	@SpireEnum public static AbstractCard.CardTags MAGICIANS_FORCE;
	@SpireEnum public static AbstractCard.CardTags INVASION_CHAOS;
	@SpireEnum public static AbstractCard.CardTags DARK_CRISIS;

	// Member fields
	private static final String MODNAME = "Duelist Mod";
	private static final String AUTHOR = "Nyoxide";
	private static final String DESCRIPTION = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	private static String modID = "duelistmod";
	private static ArrayList<String> cardSets = new ArrayList<String>();
	public static ArrayList<String> startingDecks = new ArrayList<String>();
	private static ArrayList<StarterDeck> starterDeckList = new ArrayList<StarterDeck>();
	private static ArrayList<DuelistCard> deckToStartWith = new ArrayList<DuelistCard>();
	private static ArrayList<DuelistCard> standardDeck = new ArrayList<DuelistCard>();
	private static ArrayList<DuelistCard> orbCards = new ArrayList<DuelistCard>();
	public static ArrayList<AbstractCard> coloredCards = new ArrayList<AbstractCard>();
	private static Map<CardTags, StarterDeck> deckTagMap = new HashMap<CardTags, StarterDeck>();
	private static int setIndex = 0;
	public static int deckIndex = 0;
	private static final int SETS = 5;
	private static int DECKS = 20;
	private static int cardCount = 75;
	private static CardTags chosenDeckTag = STANDARD_DECK;
	private static int randomDeckSmallSize = 10;
	private static int randomDeckBigSize = 15;
	
	// Global Fields
	public static Properties duelistDefaults = new Properties();
	public static HashMap<String, DuelistCard> summonMap = new HashMap<String, DuelistCard>();
	public static HashMap<String, AbstractPower> buffMap = new HashMap<String, AbstractPower>();
	public static ArrayList<DuelistCard> myCards = new ArrayList<DuelistCard>();
	public static ArrayList<AbstractCard> tinFluteCards = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<AbstractPower>();
	public static ArrayList<String> randomBuffStrings = new ArrayList<String>();
	public static Map<String, DuelistCard> orbCardMap = new HashMap<String, DuelistCard>();
	public static final String PROP_TOON_BTN = "toonBtnBool";
	public static final String PROP_EXODIA_BTN = "exodiaBtnBool";
	public static final String PROP_CROSSOVER_BTN = "crossoverBtnBool";
	public static final String PROP_SET = "setIndex";
	public static final String PROP_DECK = "deckIndex";
	public static final String PROP_CARDS = "cardCount";
	public static final String PROP_MAX_SUMMONS = "lastMaxSummons";
	public static final String PROP_HAS_KEY = "hasKey";
	public static final String PROP_HAS_RING = "hasRing";
	public static final String PROP_RESUMMON_DMG = "resummonDeckDamage";
	public static final String PROP_CHALLENGE = "challengeMode";
	public static final String PROP_UNLOCK = "unlockAllDecks";
	public static final String PROP_FLIP = "flipCardTags";
	public static boolean toonBtnBool = true;
	public static boolean exodiaBtnBool = false;
	public static boolean crossoverBtnBool = true;
	public static boolean challengeMode = false;
	public static boolean unlockAllDecks = false;
	public static boolean flipCardTags = false;
	public static boolean toonWorldTemp = false;
	public static int lastMaxSummons = 5;
	//public static int toonDamage = 7;
	public static int spellsThisCombat = 0;
	public static int summonsThisCombat = 0;
	public static boolean hasRing = false;
	public static boolean hasKey = false;
	public static boolean checkTrap = false;
	public static boolean checkUO = false;
	public static boolean ultimateOfferingTrig = false;
	public static boolean isApi = Loader.isModLoaded("archetypeapi");
	public static boolean isConspire = Loader.isModLoaded("conspire");
	public static boolean isReplay = Loader.isModLoaded("ReplayTheSpireMod");
	public static boolean isHubris = Loader.isModLoaded("hubris");
	public static boolean playedOneCardThisCombat = false;
	public static int swordsPlayed = 0;
	public static int cardsToDraw = 5;
	public static int resummonDeckDamage = 1;
	public static StarterDeck currentDeck;
	public static ModLabel setSelectColorTxtB;
	
	// Global Character Stats
	public static int energyPerTurn = 3;
	public static int startHP = 80;
	public static int maxHP = 80;
	public static int startGold = 99;
	public static int cardDraw = 5;
	public static int orbSlots = 3;
	
	// Turn off for Workshop releases, just prints out stuff and adds debug cards/tokens to game
	public static final boolean debug = true;		// print statements only really
	public static final boolean fullDebug = false;	// actually modifies char stats, cards in compendium, starting max summons, etc

	// =============== INPUT TEXTURE LOCATION =================

	// Colors (RGB)
	// Character Color
	public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
	public static final Color DEFAULT_PURPLE = CardHelper.getColor(0.0f, 0.0f, 61.0f);
	public static final Color DEFAULT_GREEN = CardHelper.getColor(0.0f, 39.0f, 0.0f);
	public static final Color DEFAULT_YELLOW = CardHelper.getColor(70.0f, 53.0f, 28.0f);

	// Potion Colors in RGB
	public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
	public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
	public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
	
	// Assets folder
	private static final String DEFAULT_MOD_ASSETS_FOLDER = "defaultModResources/images";

	// Gray card BG
	private static final String ATTACK_DEFAULT_GRAY = "512/bg_attack_default_gray.png";
	private static final String POWER_DEFAULT_GRAY = "512/bg_power_default_gray.png";
	private static final String SKILL_DEFAULT_GRAY = "512/bg_skill_default_gray.png";
	private static final String ENERGY_ORB_DEFAULT_GRAY = "512/card_default_gray_orb.png";
	private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";
	private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "1024/bg_attack_default_gray.png";
	private static final String POWER_DEFAULT_GRAY_PORTRAIT = "1024/bg_power_default_gray.png";
	private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "1024/bg_skill_default_gray.png";
	private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "1024/card_default_gray_orb.png";
	
	// Green card BG
	private static final String ATTACK_DEFAULT_GREEN = "512/bg_attack_default_green.png";
	private static final String POWER_DEFAULT_GREEN = "512/bg_power_default_green.png";
	private static final String SKILL_DEFAULT_GREEN = "512/bg_skill_default_green.png";
	private static final String ENERGY_ORB_DEFAULT_GREEN = "512/card_default_green_orb.png";
	private static final String CARD_ENERGY_ORB_GREEN = "512/card_small_orb_green.png";
	private static final String ATTACK_DEFAULT_GREEN_PORTRAIT = "1024/bg_attack_default_green.png";
	private static final String POWER_DEFAULT_GREEN_PORTRAIT = "1024/bg_power_default_green.png";
	private static final String SKILL_DEFAULT_GREEN_PORTRAIT = "1024/bg_skill_default_green.png";
	private static final String ENERGY_ORB_DEFAULT_GREEN_PORTRAIT = "1024/card_default_green_orb.png";
	
	// Purple card BG
	private static final String ATTACK_DEFAULT_PURPLE = "512/bg_attack_default_purple.png";
	private static final String POWER_DEFAULT_PURPLE = "512/bg_power_default_purple.png";
	private static final String SKILL_DEFAULT_PURPLE = "512/bg_skill_default_purple.png";
	private static final String ENERGY_ORB_DEFAULT_PURPLE = "512/card_default_purple_orb.png";
	private static final String CARD_ENERGY_ORB_PURPLE = "512/card_small_orb_purple.png";
	private static final String ATTACK_DEFAULT_PURPLE_PORTRAIT = "1024/bg_attack_default_purple.png";
	private static final String POWER_DEFAULT_PURPLE_PORTRAIT = "1024/bg_power_default_purple.png";
	private static final String SKILL_DEFAULT_PURPLE_PORTRAIT = "1024/bg_skill_default_purple.png";
	private static final String ENERGY_ORB_DEFAULT_PURPLE_PORTRAIT = "1024/card_default_purple_orb.png";
	
	// Yellow card BG
	private static final String ATTACK_DEFAULT_YELLOW = "512/bg_attack_default_yellow.png";
	private static final String POWER_DEFAULT_YELLOW = "512/bg_power_default_yellow.png";
	private static final String SKILL_DEFAULT_YELLOW = "512/bg_skill_default_yellow.png";
	private static final String ENERGY_ORB_DEFAULT_YELLOW = "512/card_default_yellow_orb.png";
	private static final String CARD_ENERGY_ORB_YELLOW = "512/card_small_orb_yellow.png";
	private static final String ATTACK_DEFAULT_YELLOW_PORTRAIT = "1024/bg_attack_default_yellow.png";
	private static final String POWER_DEFAULT_YELLOW_PORTRAIT = "1024/bg_power_default_yellow.png";
	private static final String SKILL_DEFAULT_YELLOW_PORTRAIT = "1024/bg_skill_default_yellow.png";
	private static final String ENERGY_ORB_DEFAULT_YELLOW_PORTRAIT = "1024/card_default_yellow_orb.png";

	// Card images
	public static final String ALPHA_MAGNET = "cards/Alpha_Magnet.png";
	public static final String ANCIENT_RULES = "cards/Ancient_Rules.png";
	public static final String AXE_DESPAIR = "cards/Axe_Despair.png";
	public static final String BAD_REACTION = "cards/Bad_Reaction.png";
	public static final String BARREL_DRAGON = "cards/Barrel_Dragon.png";
	public static final String BETA_MAGNET = "cards/Beta_Magnet.png";
	public static final String BLUE_EYES = "cards/Blue_Eyes_Alt.png";
	public static final String BLUE_EYES_TOON = "cards/Blue_Eyes_Toon.png";
	public static final String BLUE_EYES_ULTIMATE = "cards/Blue_Eyes_Ultimate.png";
	public static final String BUSTER_BLADER = "cards/Buster_Blader.png";
	public static final String CANNON_SOLDIER = "cards/Cannon_Soldier.png";
	public static final String CARD_DESTRUCTION = "cards/Card_Destruction.png";
	public static final String CASTLE_DARK = "cards/Castle_Dark.png";
	public static final String CASTLE_WALLS = "cards/Castle_Walls.png";
	public static final String CATAPULT_TURTLE = "cards/Catapult_Turtle.png";
	public static final String CELTIC_GUARDIAN = "cards/Celtic_Guardian.png";
	public static final String CHANGE_HEART = "cards/Change_Heart.png";
	public static final String DARKLORD_MARIE = "cards/Darklord_Marie.png";
	public static final String DARK_FACTORY = "cards/Dark_Factory.png";
	public static final String DARK_HOLE = "cards/Dark_Hole.png";
	public static final String DARK_MAGICIAN = "cards/Dark_Magician.png";
	public static final String DARK_MAGICIAN_GIRL = "cards/Dark_Magician_Girl.png";
	public static final String DARK_MIRROR_FORCE = "cards/Dark_Mirror_Force.png";
	public static final String DIAN_KETO = "cards/Dian_Keto.png";
	public static final String EXODIA_HEAD = "cards/Exodia_Head.png";
	public static final String EXODIA_LEFT_ARM = "cards/Exodia_LA.png";
	public static final String EXODIA_LEFT_LEG = "cards/Exodia_LL.png";
	public static final String EXODIA_RIGHT_ARM = "cards/Exodia_RA.png";
	public static final String EXODIA_RIGHT_LEG = "cards/Exodia_RL.png";
	public static final String FEATHER_PHOENIX = "cards/Feather_Phoenix.png";
	public static final String FIEND_MEGACYBER = "cards/Fiend_Megacyber.png";
	public static final String FISSURE = "cards/Fissure.png";
	public static final String FLAME_SWORDSMAN = "cards/Flame_Swordsman.png";
	public static final String GAIA_FIERCE = "cards/Gaia_Fierce.png";
	public static final String GAMMA_MAGNET = "cards/Gamma_Magnet.png";
	public static final String GATE_GUARDIAN = "cards/Gate_Guardian.png";
	public static final String GEMINI_ELF = "cards/Gemini_Elf.png";
	public static final String GIANT_SOLDIER = "cards/Giant_Soldier.png";
	public static final String GIANT_TRUNADE = "cards/Giant_Trunade.png";
	public static final String GRACEFUL_CHARITY = "cards/Graceful_Charity.png";
	public static final String HARPIE_FEATHER = "cards/Harpie_Feather.png";
	public static final String HINOTOMA = "cards/Hinotoma.png";
	public static final String IMPERIAL_ORDER = "cards/Imperial_Order.png";
	public static final String INJECTION_FAIRY = "cards/Injection_Fairy.png";
	public static final String INSECT_QUEEN = "cards/Insect_Queen.png";
	public static final String JAM_BREEDING = "cards/Jam_Breeding.png";
	public static final String JUDGE_MAN = "cards/Judge_Man.png";
	public static final String KURIBOH = "cards/Kuriboh.png";
	public static final String LABYRINTH_WALL = "cards/Labyrinth_Wall.png";
	public static final String LEGENDARY_FISHERMAN = "cards/Legendary_Fisherman.png";
	public static final String MAGIC_CYLINDER = "cards/Magic_Cylinder.png";
	public static final String MAUSOLEUM = "cards/Mausoleum.png";
	public static final String MILLENNIUM_SHIELD = "cards/Millennium_Shield.png";
	public static final String MIRROR_FORCE = "cards/Mirror_Force.png";
	public static final String MONSTER_REBORN = "cards/Monster_Reborn.png";
	public static final String NUTRIENT_Z = "cards/Nutrient_Z.png";
	public static final String OBELISK_TORMENTOR = "cards/Obelisk_Tormentor.png";
	public static final String OJAMAGIC = "cards/Ojamagic.png";
	public static final String OJAMA_BLACK = "cards/Ojama_Black.png";
	public static final String OJAMA_GREEN = "cards/Ojama_Green.png";
	public static final String OJAMA_KING = "cards/Ojama_King.png";
	public static final String OJAMA_KNIGHT = "cards/Ojama_Knight.png";
	public static final String OJAMA_YELLOW = "cards/Ojama_Yellow.png";
	public static final String OOKAZI = "cards/Ookazi.png";
	public static final String PARASITE = "cards/Parasite.png";
	public static final String POT_AVARICE = "cards/Pot_Avarice.png";
	public static final String POT_DICHOTOMY = "cards/Pot_Dichotomy.png";
	public static final String POT_DUALITY = "cards/Pot_Duality.png";
	public static final String POT_GENEROSITY = "cards/Pot_Generosity.png";
	public static final String POT_GREED = "cards/Pot_Greed.png";
	public static final String PUMPKING = "cards/Pumpking.png";
	public static final String PUMPRINCESS = "cards/Pumprincess.png";
	public static final String RADIANT_MIRROR_FORCE = "cards/Radiant_Mirror_Force.png";
	public static final String RAIN_MERCY = "cards/Rain_Mercy.png";
	public static final String RED_EYES = "cards/Red_Eyes.png";
	public static final String RED_EYES_TOON = "cards/Red_Eyes_Toon.png";
	public static final String RED_MEDICINE = "cards/Red_Medicine.png";
	public static final String RELINQUISHED = "cards/Relinquished.png";
	public static final String SANGA_EARTH = "cards/Sanga_Earth.png";
	public static final String SANGA_THUNDER = "cards/Sanga_Thunder.png";
	public static final String SANGA_WATER = "cards/Sanga_Water.png";
	public static final String SCAPEGOAT = "cards/Scapegoat.png";
	public static final String SCRAP_FACTORY = "cards/Scrap_Factory.png";
	public static final String SEVEN_COLORED_FISH = "cards/7_Colored_Fish.png";
	public static final String SHARD_GREED = "cards/Shard_Greed.png";
	public static final String SLIFER_SKY = "cards/Slifer_Sky.png";
	public static final String STORMING_MIRROR_FORCE = "cards/Storming_Mirror_Force.png";
	public static final String SUMMONED_SKULL = "cards/Summoned_Skull_Alt.png";
	public static final String SUPERHEAVY_BENKEI = "cards/Superheavy_Benkei.png";
	public static final String SUPERHEAVY_SCALES = "cards/Superheavy_Scales.png";
	public static final String SUPERHEAVY_SWORDSMAN = "cards/Superheavy_Swordsman.png";
	public static final String SUPERHEAVY_WARAJI = "cards/Superheavy_Waraji.png";
	public static final String SWORDS_BURNING = "cards/Swords_Burning.png";
	public static final String SWORDS_CONCEALING = "cards/Swords_Concealing.png";
	public static final String SWORDS_REVEALING = "cards/Swords_Revealing.png";
	public static final String TIME_WIZARD = "cards/Time_Wizard.png";
	public static final String TOON_BARREL_DRAGON = "cards/Toon_Barrel_Dragon.png";
	public static final String TOON_BRIEFCASE = "cards/Toon_Briefcase.png";
	public static final String TOON_DARK_MAGICIAN = "cards/Toon_Dark_Magician.png";
	public static final String TOON_DARK_MAGICIAN_GIRL = "cards/Toon_Dark_Magician_Girl.png";
	public static final String TOON_GEMINI_ELF = "cards/Toon_Gemini_Elf.png";
	public static final String TOON_MERMAID = "cards/Toon_Mermaid.png";
	public static final String TOON_SUMMONED_SKULL = "cards/Toon_Summoned_Skull.png";
	public static final String TOON_WORLD = "cards/Toon_World.png";
	public static final String TRAP_HOLE = "cards/Trap_Hole.png";
	public static final String VALK_MAGNET = "cards/Valk_Magnet.png";
	public static final String WINGED_DRAGON_RA = "cards/Winged_Dragon_Ra.png";
	public static final String TREMENDOUS_FIRE = "cards/Tremendous_Fire.png";
	public static final String TOON_MASK = "cards/Toon_Mask.png";
	public static final String TOON_MAGIC = "cards/Toon_Magic.png";
	public static final String TOON_KINGDOM = "cards/Toon_Kingdom.png";
	public static final String TOON_ROLLBACK = "cards/Toon_Rollback.png";
	public static final String SUPERHEAVY_OGRE = "cards/Superheavy_Ogre.png";
	public static final String SUPERHEAVY_MAGNET = "cards/Superheavy_Magnet.png";
	public static final String SUPERHEAVY_GENERAL = "cards/Superheavy_General.png";
	public static final String SUPERHEAVY_FLUTIST = "cards/Superheavy_Flutist.png";
	public static final String SUPERHEAVY_DAIHACHI = "cards/Superheavy_Daihachi.png";
	public static final String SUPERHEAVY_BLUE_BRAWLER = "cards/Superheavy_Blue_Brawler.png";
	public static final String SPIRIT_HARP = "cards/Spirit_Harp.png";
	public static final String SNOW_DRAGON = "cards/Snow_Dragon.png";
	public static final String SNOWDUST_DRAGON = "cards/Snowdust_Dragon.png";
	public static final String RAIGEKI = "cards/Raigeki.png";
	public static final String PREVENT_RAT = "cards/Prevent_Rat.png";
	public static final String OJAMUSCLE = "cards/Ojamuscle.png";
	public static final String MYSTICAL_ELF = "cards/Mystical_Elf.png";
	public static final String ISALND_TURTLE = "cards/Island_Turtle.png";
	public static final String GRAVITY_AXE = "cards/Gravity_Axe.png";
	public static final String FORTRESS_WARRIOR = "cards/Fortress_Warrior.png";
	public static final String CAVE_DRAGON = "cards/Cave_Dragon.png";
	public static final String BLIZZARD_DRAGON = "cards/Blizzard_Dragon.png";
	public static final String BABY_DRAGON = "cards/Baby_Dragon.png";
	public static final String CURSE_DRAGON = "cards/Curse_of_Dragon.png";
	public static final String CYBER_DRAGON = "cards/Cyber_Dragon.png";
	public static final String DRAGON_MASTER = "cards/Dragon_Master_Knight.png";
	public static final String FIEND_SKULL = "cards/Fiend_Skull_Dragon.png";
	public static final String FIVE_HEADED = "cards/Five_Headed_Dragon.png";
	public static final String FLUTE_SUMMONING = "cards/Flute_Summoning_Dragon.png";
	public static final String GANDORA = "cards/Gandora.png";
	public static final String INFINITY_DRAGON = "cards/Infinity_Dragon.png";
	public static final String LORD_D = "cards/Lord_D.png";
	public static final String HANE_HANE = "cards/Hane_Hane.png";
	public static final String LESSER_DRAGON = "cards/Lesser_Dragon.png";
	public static final String GAIA_DRAGON_CHAMP = "cards/Gaia_Dragon_Champion.png";
	public static final String DRAGON_CAPTURE = "cards/Dragon_Capture_Jar.png";
	public static final String THUNDER_DRAGON = "cards/Thunder_Dragon.png";
	public static final String BLAST_JUGGLER = "cards/Blast_Juggler.png";
	public static final String LEGENDARY_EXODIA = "cards/Legendary_Exodia_Incarnate.png";
	public static final String SUPERANCIENT_DINOBEAST = "cards/Superancient_Dinobeast.png";
	public static final String MOLTEN_ZOMBIE = "cards/Molten_Zombie.png";
	public static final String RED_EYES_ZOMBIE = "cards/Red_Eyes_Zombie.png";
	public static final String ARMORED_ZOMBIE = "cards/Armored_Zombie.png";
	public static final String TOKEN_VACUUM = "cards/Token_Vacuum.png";
	public static final String MOUNTAIN = "cards/Mountain.png";
	public static final String YAMI = "cards/Yami.png";
	public static final String MACHINE_KING = "cards/Machine_King.png";
	public static final String BOOK_SECRET = "cards/Book_Secret_Arts.png";
	public static final String STRAY_LAMBS = "cards/Stray_Lambs.png";
	public static final String HEAVY_STORM = "cards/Heavy_Storm.png";
	public static final String FOG_KING = "cards/Fog_King.png";
	public static final String KING_YAMIMAKAI = "cards/King_Yamimakai.png";
	public static final String LAJINN = "cards/LaJinn.png";
	public static final String BLACKLAND_FIRE_DRAGON = "cards/Blackland_Fire_Dragon.png";
	public static final String WHITE_HORNED = "cards/White_Horned_Dragon.png";
	public static final String WHITE_NIGHT = "cards/White_Night_Dragon.png";
	public static final String REVIVAL_JAM = "cards/Revival_Jam.png";
	public static final String STIM_PACK = "cards/Stim_Pack.png";
	public static final String BOTTOMLESS_TRAP_HOLE = "cards/Bottomless_Trap_Hole.png";
	public static final String KURIBOH_TOKEN = "cards/Kuriboh_Token.png";
	public static final String EXPLOSIVE_TOKEN = "cards/Explosive_Token.png";
	public static final String GREEDPOT_AVATAR = "cards/Greedpot_Avatar.png";
	public static final String WISEMAN = "cards/Wiseman.png";
	public static final String MACHINE_FACTORY = "cards/Machine_Conversion_Factory.png";
	public static final String MONSTER_EGG = "cards/Monster_Egg.png";
	public static final String STEAM_TRAIN_KING = "cards/SteamTrain.png";
	public static final String SWORD_DEEP_SEATED = "cards/Sword_Deep_Seated.png"; 
	public static final String TRIBUTE_DOOMED = "cards/Tribute_Doomed.png";
	public static final String PETIT_MOTH = "cards/Petit_Moth.png";
	public static final String COCOON_EVOLUTION = "cards/Cocoon_Evolution.png";
	public static final String GREAT_MOTH = "cards/Great_Moth.png";
	public static final String CHEERFUL_COFFIN = "cards/Cheerful_Coffin.png";
	public static final String THE_CREATOR = "cards/The_Creator.png";
	public static final String POLYMERIZATION = "cards/Polymerization.png";
	public static final String VIOLET_CRYSTAL = "cards/Violet_Crystal.png";
	public static final String PREDAPONICS = "cards/Predaponics.png";
	public static final String METAL_DRAGON = "cards/Metal_Dragon.png";
	public static final String SUPER_SOLAR_NUTRIENT = "cards/Super_Solar_Nutrient.png";
	public static final String GIGAPLANT = "cards/Gigaplant.png";
	public static final String BASIC_INSECT = "cards/Basic_Insect.png";		
	public static final String PREDAPLANT_CHIMERAFFLESIA = "cards/Predaplant_Chimerafflesia.png";
	public static final String PREDAPLANT_CHLAMYDOSUNDEW = "cards/Predaplant_Chlamydosundew.png";
	public static final String PREDAPLANT_DROSOPHYLLUM = "cards/Predaplant_Drosophyllum.png";
	public static final String PREDAPLANT_FLYTRAP = "cards/Predaplant_Flytrap.png";
	public static final String PREDAPLANT_PTERAPENTHES = "cards/Predaplant_Pterapenthes.png";
	public static final String PREDAPLANT_SARRACENIANT = "cards/Predaplant_Sarraceniant.png";
	public static final String PREDAPLANT_SPINODIONAEA = "cards/Predaplant_Spinodionaea.png";
	public static final String EMPRESS_MANTIS = "cards/Empress_Mantis.png";		
	public static final String GRASSCHOPPER = "cards/Grasschopper.png";
	public static final String MAN_EATER = "cards/Man_Eater_Bug.png";
	public static final String PREDAPRUNING = "cards/Predapruning.png";		
	public static final String B_SKULL_DRAGON = "cards/B_Skull_Dragon.png";
	public static final String DARKFIRE_DRAGON = "cards/Darkfire_Dragon.png";
	public static final String LEVIA_DRAGON = "cards/Levia_Dragon_Daedalus.png";		
	public static final String OCEAN_LORD = "cards/Ocean_Lord.png";
	public static final String TRIHORNED_DRAGON = "cards/TriHorned_Dragon.png";
	public static final String WIRETAP = "cards/Wiretap.png";
	public static final String JINZO = "cards/Jinzo.png";		
	public static final String SHADOW_TOKEN = "cards/Shadow_Token.png";	
	public static final String SHADOW_TOON = "cards/Shadow_Toon.png";	
	public static final String PREDA_TOKEN = "cards/Predaplant_Token.png";
	public static final String SHALLOW_GRAVE = "cards/Shallow_Grave.png";
	public static final String RANDOM_SOLDIER = "cards/Random_Soldier.png";
	public static final String GUARDIAN_ANGEL = "cards/Guardian_Angel.png";
	public static final String JERRY_BEANS = "cards/Jerry_Beans_Man.png";
	public static final String REINFORCEMENTS = "cards/Reinforcements.png";
	public static final String ULTIMATE_OFFERING = "cards/Ultimate_Offering.png";
	public static final String GENERIC_TOKEN = "cards/Token.png";	
	public static final String EXPLODER_DRAGON = "cards/Exploder_Dragon.png";	
	public static final String INVIGORATION = "cards/Invigoration.png";	
	public static final String INVITATION = "cards/Invitation_Dark_Sleep.png";	
	public static final String ILLUSIONIST = "cards/Illusionist_Faceless.png";
	
	public static final String ACID_TRAP = "cards/Acid_Trap_Hole.png";
	public static final String ALTAR_TRIBUTE = "cards/Altar_Tribute.png";
	public static final String BLIZZARD_PRINCESS = "cards/Blizzard_Princess.png";
	public static final String CARD_SAFE_RETURN = "cards/Card_Safe_Return.png";
	public static final String CLONING = "cards/Cloning.png";
	public static final String CONTRACT_EXODIA = "cards/Contract_Exodia.png";
	public static final String DARK_CREATOR = "cards/Dark_Creator.png";
	public static final String DOUBLE_COSTON = "cards/Double_Coston.png";
	public static final String GATES_DARK_WORLD = "cards/Gates_Dark_World.png";
	public static final String HAMMER_SHOT = "cards/Hammer_Shot.png";
	public static final String HEART_UNDERDOG = "cards/Heart_Underdog.png";
	public static final String HEART_UNDERSPELL = "cards/Heart_Underspell.png";
	public static final String HEART_UNDERTRAP = "cards/Heart_Undertrap.png";
	public static final String HEART_UNDERTRIBUTE = "cards/Heart_Undertribute.png";
	public static final String INSECT_KNIGHT = "cards/Insect_Knight.png";
	public static final String JIRAI_GUMO = "cards/Jirai_Gumo.png";
	public static final String MYTHICAL_BEAST = "cards/Mythical_Beast.png";
	public static final String POT_FORBIDDEN = "cards/Pot_Forbidden.png";
	public static final String SMASHING_GROUND = "cards/Smashing_Ground.png";
	public static final String TERRAFORMING = "cards/Terraforming.png";
	public static final String COMIC_HAND = "cards/Comic_Hand.png";
	public static final String ICY_CREVASSE = "cards/Icy_Crevasse.png";		
		
	public static final String CRASHBUG_ROAD = "cards/Crashbug_Road.png";
	public static final String CRASHBUG_X = "cards/Crashbug_X.png";
	public static final String CRASHBUG_Y = "cards/Crashbug_Y.png";
	public static final String CRASHBUG_Z = "cards/Crashbug_Z.png";
	public static final String GOLDEN_APPLES = "cards/Golden_Apples.png";
	public static final String HYPERANCIENT_SHARK = "cards/Hyperancient_Shark.png";
	public static final String ICE_QUEEN = "cards/Ice_Queen.png";
	public static final String TERRA_TERRIBLE = "cards/Terra_Terrible.png";	
	public static final String SPHERE_KURIBOH = "cards/Sphere_Kuriboh.png";
	
	public static final String LEGENDARY_OCEAN = "cards/Legendary_Ocean.png";
	public static final String MAGICAL_STONE = "cards/Magical_Stone.png";
	public static final String MOTHER_SPIDER = "cards/Mother_Spider.png";
	public static final String MS_JUDGE = "cards/Ms_Judge.png";	
	public static final String OH_FISH = "cards/Oh_Fish.png";
	public static final String OJAMA_DELTA_HURRICANE = "cards/Ojama_Delta_Hurricane.png";
	public static final String PATRICIAN_DARKNESS = "cards/Patrician_Darkness.png";	
	public static final String PREDAPLANET = "cards/predaplanet.png";
	public static final String RYU_KOKKI = "cards/Ryu_Kokki.png";
	public static final String SKULL_ARCHFIEND = "cards/Skull_Archfiend.png";
	public static final String SLATE_WARRIOR = "cards/Slate_Warrior.png";	
	public static final String SUPER_CRASHBUG = "cards/Super_Crashbug.png";
	public static final String TYRANT_WING = "cards/Tyrant_Wing.png";
	public static final String UMI = "cards/Umi.png";
	public static final String UMIIRUKA = "cards/Umiiruka.png";
	public static final String VAMPIRE_GENESIS = "cards/Vampire_Genesis.png";
	public static final String VAMPIRE_LORD = "cards/Vampire_Lord.png";
	public static final String VANITY_FIEND = "cards/Vanity_Fiend.png";
	public static final String WASTELAND = "cards/Wasteland.png";
	public static final String WORLD_TREE = "cards/World_Tree.png";
	
	public static final String MANGA_RYU_RAN = "cards/Manga_Ryu_Ran.png";
	public static final String TOON_ANCIENT_GEAR = "cards/Toon_Ancient_Gear.png";
	public static final String TOON_CYBER_DRAGON = "cards/Toon_Cyber_Dragon.png";
	public static final String TOON_GOBLIN_ATTACK = "cards/Toon_Goblin_Attack_Force.png";
	public static final String DARK_ENERGY = "cards/Dark_Energy.png";
	public static final String EXODIA_NECROSS = "cards/Exodia_Necross.png";
	public static final String EXXOD_MASTER = "cards/Exxod_Master_Guard.png";
	public static final String OBLITERATE = "cards/Obliterate.png";
	public static final String LEGEND_EXODIA = "cards/Legend_Exodia.png";
	public static final String KAISER_SEA_HORSE = "cards/Kaiser_Sea_Horse.png";
	

	
	// Expansion Set
	public static final String FINAL_FLAME = "cards/Final_Flame.png";
	public static final String GOBLIN_SECRET = "cards/Goblin_Secret_Remedy.png";		
	public static final String SPARKS = "cards/Sparks.png";    
	public static final String LAVA_BATTLEGUARD = "cards/Lava_Battleguard.png";
	public static final String SWAMP_BATTLEGUARD = "cards/Swamp_Battleguard.png";
	public static final String TWIN_HEADED_FIRE = "cards/Twin_Headed_Fire_Dragon.png";
	public static final String TWIN_HEADED_THUNDER = "cards/Twin_Headed_Thunder_Dragon.png";
	public static final String RYU_RAN = "cards/Ryu_Ran.png";
	public static final String HAYABUSA_KNIGHT = "cards/Hayabusa_Knight.png";	
	public static final String DRAGON_PIPER = "cards/Dragon_Piper.png";
	public static final String HARPIES_BROTHER = "cards/Harpies_Brother.png";
	public static final String HARPIE_LADY = "cards/Harpie_Lady.png";
	public static final String HARPIE_LADY_PHO = "cards/Harpie_Lady_Phoneix.png";
	public static final String HARPIE_LADY_SISTERS = "cards/Harpie_Lady_Sisters.png";
	public static final String ELEGANT_EGOTIST = "cards/Elegant_Egotist.png";	
	public static final String TWIN_BARREL_DRAGON = "cards/Twin_Barrel_Dragon.png";
	public static final String TWIN_HEADED = "cards/Twin_Headed_Thunder_Dragon.png";
	public static final String TYRANT_DRAGON = "cards/Tyrant_Dragon.png";
	public static final String YAMATA_DRAGON = "cards/Yamata_Dragon.png";
	public static final String ATTACK_RECEIVE = "cards/Attack_Receive.png";	
	public static final String BEAST_FANGS = "cards/Beast_Fangs.png";
	public static final String BEAVER_WARRIOR = "cards/Beaver_Warrior.png";
	public static final String CHARUBIN = "cards/Charubin_Fire_Knight.png";
	public static final String DARK_GRAY = "cards/Dark_Gray.png";	
	public static final String FERAL_IMP = "cards/Feral_Imp.png";
	public static final String FIENDISH_CHAIN = "cards/Fiendish_Chain.png";
	public static final String FIREGRASS = "cards/Firegrass.png";
	public static final String FLAME_MANIPULATOR = "cards/Flame_Manipulator.png";
	public static final String GARNECIA_ELEFANTIS = "cards/Garnecia_Elefantis.png";
	public static final String GEARFRIED = "cards/Gearfried.png";	
	public static final String GRAND_HORN_HEAVEN = "cards/Grand_Horn_Heaven.png";	
	public static final String GRAVEROBBER = "cards/Graverobber.png";
	public static final String GUARDIAN_THRONE = "cards/Guardian_Throne_Room.png";
	public static final String HITOTSU_GIANT = "cards/Hitotsu_Me_Giant.png";	
	public static final String LEGENDARY_SWORD = "cards/Legendary_Sword.png";
	public static final String LUSTER_DRAGON = "cards/Luster_Dragon.png";
	public static final String LUSTER_DRAGON2 = "cards/Luster_Dragon2.png";
	public static final String MAMMOTH_GRAVEYARD = "cards/Mammoth_Graveyard.png";		
	public static final String NATURIA_BEAST = "cards/Naturia_Beast.png";
	public static final String NATURIA_CLIFF = "cards/Naturia_Cliff.png";
	public static final String NATURIA_DRAGONFLY = "cards/Naturia_Dragonfly.png";
	public static final String NATURIA_GUARDIAN = "cards/Naturia_Guardian.png";
	public static final String NATURIA_HORNEEDLE = "cards/Naturia_Horneedle.png";
	public static final String NATURIA_LANDOISE = "cards/Naturia_Landoise.png";
	public static final String NATURIA_MANTIS = "cards/Naturia_Mantis.png";
	public static final String NATURIA_PINEAPPLE = "cards/Naturia_Pineapple.png";
	public static final String NATURIA_PUMPKIN = "cards/Naturia_Pumpkin.png";
	public static final String NATURIA_ROSEWHIP = "cards/Naturia_Rosewhip.png";
	public static final String NATURIA_SACRED_TREE = "cards/Naturia_Sacred_Tree.png";
	public static final String NATURIA_STRAWBERRY = "cards/Naturia_Strawberry.png";
	public static final String NEMURIKO = "cards/Nemuriko.png";
	public static final String NUMINOUS_HEALER = "cards/Numinous_Healer.png";
	public static final String POWER_KAISHIN = "cards/Power_Kaishin.png";		
	public static final String RELINKURIBOH = "cards/Relinkuriboh.png";
	public static final String SANGAN = "cards/Sangan.png";
	public static final String SAUROPOD_BRACHION = "cards/Sauropod Brachion.png";
	public static final String SILVER_FANG = "cards/Silver_Fang.png";
	public static final String SKULL_SERVANT = "cards/Skull_Servant.png";
	
	public static final String SUPERCONDUCTOR_TYRANNO = "cards/Super_Conductor_Tyranno.png";	
	public static final String SWORD_HUNTER = "cards/Sword_Hunter.png";
	public static final String THIRTEEN_GRAVE = "cards/13th_Grave.png";
	public static final String THOUSAND_DRAGON = "cards/Thousand_Dragon.png";
	public static final String THOUSAND_EYES_IDOL = "cards/Thousand_Eyes_Idol.png";
	public static final String THOUSAND_EYES_RESTRICT = "cards/Thousand_Eyes_Restrict.png";
	public static final String TRIAL_NIGHTMARE = "cards/Trial_Nightmare.png";	
	public static final String VORSE_RAIDER = "cards/Vorse_Raider.png";
	public static final String WARRIOR_GREPHER = "cards/Warrior_Dai_Grepher.png";
	public static final String WHITE_MAGICAL_HAT = "cards/White_Magical_Hat.png";
	public static final String WINGWEAVER = "cards/Wingweaver.png";	
	public static final String ZOMBYRA = "cards/Zombyra.png";

	// Power images
	public static final String SUMMON_POWER = "powers/SummonPowerTest.png";
	public static final String JAM_POWER = "powers/JamPower.png";
	public static final String DESPAIR_POWER = "powers/DespairPower.png";
	public static final String TOON_WORLD_POWER = "powers/ToonWorldPower.png";
	public static final String SPELL_COUNTER_POWER = "powers/SpellCounterPower.png";
	public static final String OBELISK_POWER = "powers/ObeliskPower.png";
	public static final String ALPHA_MAG_POWER = "powers/AlphaMagPower.png";
	public static final String BETA_MAG_POWER = "powers/BetaMagPower.png";
	public static final String GAMMA_MAG_POWER = "powers/GammaMagPower.png";
	public static final String GREED_SHARD_POWER = "powers/GreedShardPower.png";
	public static final String SWORDS_BURNING_POWER = "powers/SwordsBurnPower.png";
	public static final String SWORDS_CONCEALING_POWER = "powers/SwordsConcealPower.png";
	public static final String SWORDS_REVEALING_POWER = "powers/SwordsRevealPower.png";
	public static final String TIME_WIZARD_POWER = "powers/TimeWizardPower.png";
	public static final String TOON_BRIEFCASE_POWER = "powers/ToonBriefPower.png";
	public static final String POT_GENEROSITY_POWER = "powers/PotGenerosityPower.png";
	public static final String CANNON_SOLDIER_POWER = "powers/CannonPower.png";
	public static final String CATAPULT_TURTLE_POWER = "powers/CatapultPower.png";
	public static final String BAD_REACTION_POWER = "powers/BadReactionPower.png";
	public static final String CASTLE_POWER = "powers/CastlePower.png";
	public static final String EMPEROR_POWER = "powers/EmperorPower.png";
	public static final String MAGIC_CYLINDER_POWER = "powers/MagicCylinderPower.png";
	public static final String MIRROR_FORCE_POWER = "powers/MirrorPower.png";
	public static final String SLIFER_SKY_POWER = "powers/SliferSkyPower.png";
	public static final String EXODIA_POWER = "powers/ExodiaPower.png";
	public static final String IMPERIAL_POWER = "powers/ImperialPower.png";
	public static final String DARK_POWER = "powers/DarkMirrorPower.png";
	public static final String RADIANT_POWER = "powers/DarkMirrorPower.png";
	public static final String PARASITE_POWER = "powers/ParasitePower.png";
	public static final String STORMING_POWER = "powers/StormingMirrorPower.png";
	public static final String SWORDS_BURN_POWER = "powers/SwordsBurnPower.png";
	public static final String SWORDS_CONCEAL_POWER = "powers/SwordsConcealPower.png";
	public static final String SWORDS_REVEAL_POWER = "powers/SwordsRevealPower.png";
	public static final String SUMMON_SICKNESS_POWER = "powers/SummonSicknessPower.png";
	public static final String TRIBUTE_SICKNESS_POWER = "powers/TributeSicknessPower.png";
	public static final String EVOKE_SICKNESS_POWER = "powers/EvokeSicknessPower.png";
	public static final String ORB_HEAL_POWER = "powers/OrbHealPower.png";
	public static final String ENERGY_TREASURE_POWER = "powers/EnergyTreasurePower.png";
	public static final String TRIBUTE_TOON_POWER = "powers/TributeToonPower.png";
	public static final String GRAVITY_AXE_POWER = "powers/GravityAxePower.png";
	public static final String TOON_ROLLBACK_POWER = "powers/ToonRollbackPower.png";
	public static final String REDUCER_POWER = "powers/ReducerPower.png";
	public static final String MOUNTAIN_POWER = "powers/MountainPower.png";
	public static final String YAMI_POWER = "powers/YamiPower.png";
	public static final String TRAP_HOLE_POWER = "powers/TrapHolePower.png";
	public static final String COCOON_POWER = "powers/CocoonPower.png";
	public static final String VIOLET_POWER = "powers/VioletPower.png";
	public static final String JINZO_POWER = "powers/JinzoPower.png";
	public static final String SARRACENIANT_POWER = "powers/SarraceniantPower.png";
	public static final String ULTIMATE_OFFERING_POWER = "powers/UltimateOfferingPower.png";
	public static final String NATURE_POWER = "powers/NaturePower.png";
	public static final String CARD_SAFE_POWER = "powers/CardSafePower.png";
	public static final String HEART_UNDERDOG_POWER = "powers/HeartUnderdogPower.png";
	public static final String SPHERE_KURIBOH_POWER = "powers/SphereKuribohPower.png";

	// Relic images  
	public static final String M_PUZZLE_RELC = "relics/MillenniumPuzzleRelic_Y.png";
	public static final String M_PUZZLE_RELIC_OUTLINE = "relics/outline/MillenniumPuzzle_Outline.png";
	public static final String M_EYE_RELIC = "relics/MillenniumEyeRelic.png";
	public static final String M_EYE_RELIC_OUTLINE = "relics/outline/MillenniumEye_Outline.png";
	public static final String M_RING_RELIC = "relics/MillenniumRingRelic.png";
	public static final String M_RING_RELIC_OUTLINE = "relics/MillenniumRingRelic.png";
	public static final String M_ROD_RELIC = "relics/MillenniumRodRelic.png";
	public static final String M_ROD_RELIC_OUTLINE = "relics/MillenniumRodRelic.png";
	public static final String M_COIN_RELIC = "relics/MillenniumCoinRelic.png";
	public static final String M_COIN_RELIC_OUTLINE = "relics/outline/MillenniumCoin_Outline.png";
	public static final String EXXOD_STONE_RELIC = "relics/StoneExxodRelic.png";
	public static final String EXXOD_STONE_RELIC_OUTLINE = "relics/outline/StoneExxod_Outline.png";
	public static final String M_KEY_RELIC = "relics/MillenniumKeyRelic.png";
	public static final String M_KEY_RELIC_OUTLINE = "relics/outline/MillenniumKey_Outline.png";
	public static final String GIFT_ANUBIS_RELIC = "relics/GiftAnubisRelic.png";
	public static final String GIFT_ANUBIS_RELIC_OUTLINE = "relics/outline/GiftAnubis_Outline.png";
	
	// Archetype Card Images
	public static final String BASIC_ARCH = "archetypes/Basic.png";
	public static final String DRAGON_ARCH = "archetypes/Dragon.png";
	public static final String EXODIA_ARCH = "archetypes/Exodia.png";
	public static final String GOD_ARCH = "archetypes/God.png";
	public static final String GUARDIAN_ARCH = "archetypes/Guardian.png";
	public static final String INSECT_ARCH = "archetypes/Insect.png";
	public static final String MAGNET_ARCH = "archetypes/Magnet.png";
	public static final String NATURE_ARCH = "archetypes/Nature.png";
	public static final String OJAMA_ARCH = "archetypes/Ojama.png";
	public static final String PLANTS_ARCH = "archetypes/Plant.png";
	public static final String POTS_ARCH = "archetypes/Pot.png";
	public static final String RESUMMON_ARCH = "archetypes/Resummon.png";
	public static final String SPELLCASTER_ARCH = "archetypes/Spellcaster.png";
	public static final String SPELLS_ARCH = "archetypes/Spell.png";
	public static final String SUPERHEAVY_ARCH = "archetypes/Superheavy.png";
	public static final String TOON_ARCH = "archetypes/Toon.png";
	public static final String TRAP_ARCH = "archetypes/Trap.png";
	
	// Orb Card Images
	public static final String WATER_ORB_CARD = "orbCards/WaterCard.png";
	public static final String LIGHTNING_ORB_CARD = "orbCards/LightningCard.png";
	public static final String PLASMA_ORB_CARD = "orbCards/PlasmaCard.png";
	public static final String DARK_ORB_CARD = "orbCards/DarkCard.png";
	public static final String HELLFIRE_ORB_CARD = "orbCards/HellfireCard.png";
	public static final String FROST_ORB_CARD = "orbCards/FrostCard.png";
	public static final String CRYSTAL_ORB_CARD = "orbCards/CrystalCard.png";
	public static final String GLASS_ORB_CARD = "orbCards/GlassCard.png";
	public static final String GATE_ORB_CARD = "orbCards/GateCard.png";
	public static final String BUFFER_ORB_CARD = "orbCards/EarthCard.png";
	public static final String SUMMONER_ORB_CARD = "orbCards/SummonerCard.png";
	public static final String MONSTER_ORB_CARD = "orbCards/MonsterCard.png";
	public static final String DRAGON_ORB_CARD = "orbCards/DragonCard.png";
	public static final String REDUCER_ORB_CARD = "orbCards/ReducerCard.png";
	public static final String LIGHT_ORB_CARD = "orbCards/LightCard.png";
	public static final String AIR_ORB_CARD = "orbCards/AirCard.png";
	public static final String EARTH_ORB_CARD = "orbCards/Earth2Card.png";
	public static final String FIRE_ORB_CARD = "orbCards/FireCard.png";
	public static final String GLITCH_ORB_CARD = "orbCards/GlitchCard.png";
	public static final String SHADOW_ORB_CARD = "orbCards/ShadowCard.png";
	public static final String SPLASH_ORB_CARD = "orbCards/SplashCard.png";

	// Character assets
	public static final String THE_DEFAULT_BUTTON = "charSelect/DuelistCharacterButtonB.png";
	public static final String THE_DEFAULT_PORTRAIT = "charSelect/DuelistCharacterPortraitBG_HD_C.png";
	public static final String THE_DEFAULT_SHOULDER_1 = "char/defaultCharacter/shoulder.png";
	public static final String THE_DEFAULT_SHOULDER_2 = "char/defaultCharacter/shoulder2.png";
	public static final String THE_DEFAULT_CORPSE = "char/defaultCharacter/corpse.png";

	//Mod Badge
	public static final String BADGE_IMAGE = "Badge.png";

	// Animations atlas and JSON files
	//public static final String THE_DEFAULT_SKELETON_ATLAS = "char/defaultCharacter/skeleton.atlas";
	//public static final String THE_DEFAULT_SKELETON_JSON = "char/defaultCharacter/skeleton.json";

	// =============== MAKE IMAGE PATHS =================

    public static String makeCardPath(String resourcePath) {
        return makePath("cards/" + resourcePath);
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================


	// =============== /INPUT TEXTURE LOCATION/ =================

	// =============== IMAGE PATHS =================
	/**
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	public static final String makePath(String resource) {
		return DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
	}

	// =============== /IMAGE PATHS/ =================

	// =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

	public DefaultMod() {
		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Subscribe to BaseMod hooks");

		BaseMod.subscribe(this);

		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Done subscribing");
		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Creating the color " + AbstractCardEnum.DUELIST_MONSTERS.toString());

		// Register Default Gray
		BaseMod.addColor(AbstractCardEnum.DUELIST_MONSTERS, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
				DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(ATTACK_DEFAULT_GRAY),
				makePath(SKILL_DEFAULT_GRAY), makePath(POWER_DEFAULT_GRAY),
				makePath(ENERGY_ORB_DEFAULT_GRAY), makePath(ATTACK_DEFAULT_GRAY_PORTRAIT),
				makePath(SKILL_DEFAULT_GRAY_PORTRAIT), makePath(POWER_DEFAULT_GRAY_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));
		
		// Register purple for Traps
		BaseMod.addColor(AbstractCardEnum.DUELIST_TRAPS, DEFAULT_PURPLE, DEFAULT_PURPLE, DEFAULT_PURPLE,
				DEFAULT_PURPLE, DEFAULT_PURPLE, DEFAULT_PURPLE, DEFAULT_PURPLE, makePath(ATTACK_DEFAULT_PURPLE),
				makePath(SKILL_DEFAULT_PURPLE), makePath(POWER_DEFAULT_PURPLE),
				makePath(ENERGY_ORB_DEFAULT_PURPLE), makePath(ATTACK_DEFAULT_PURPLE_PORTRAIT),
				makePath(SKILL_DEFAULT_PURPLE_PORTRAIT), makePath(POWER_DEFAULT_PURPLE_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_PURPLE_PORTRAIT), makePath(CARD_ENERGY_ORB_PURPLE));
		
		// Register green for Spells
		BaseMod.addColor(AbstractCardEnum.DUELIST_SPELLS, DEFAULT_GREEN, DEFAULT_GREEN, DEFAULT_GREEN,
				DEFAULT_GREEN, DEFAULT_GREEN, DEFAULT_GREEN, DEFAULT_GREEN, makePath(ATTACK_DEFAULT_GREEN),
				makePath(SKILL_DEFAULT_GREEN), makePath(POWER_DEFAULT_GREEN),
				makePath(ENERGY_ORB_DEFAULT_GREEN), makePath(ATTACK_DEFAULT_GREEN_PORTRAIT),
				makePath(SKILL_DEFAULT_GREEN_PORTRAIT), makePath(POWER_DEFAULT_GREEN_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_GREEN_PORTRAIT), makePath(CARD_ENERGY_ORB_GREEN));
		
		// Register yellow for Monsters
		BaseMod.addColor(AbstractCardEnum.DUELIST_MONSTERS, DEFAULT_YELLOW, DEFAULT_YELLOW, DEFAULT_YELLOW,
				DEFAULT_YELLOW, DEFAULT_YELLOW, DEFAULT_YELLOW, DEFAULT_YELLOW, makePath(ATTACK_DEFAULT_YELLOW),
				makePath(SKILL_DEFAULT_YELLOW), makePath(POWER_DEFAULT_YELLOW),
				makePath(ENERGY_ORB_DEFAULT_YELLOW), makePath(ATTACK_DEFAULT_YELLOW_PORTRAIT),
				makePath(SKILL_DEFAULT_YELLOW_PORTRAIT), makePath(POWER_DEFAULT_YELLOW_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_YELLOW_PORTRAIT), makePath(CARD_ENERGY_ORB_YELLOW));
		
		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Done creating the color");
		
		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Setting up or loading the settings config file");
		duelistDefaults.setProperty(PROP_TOON_BTN, "TRUE");
		duelistDefaults.setProperty(PROP_EXODIA_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_CROSSOVER_BTN, "TRUE");
		duelistDefaults.setProperty(PROP_SET, "0");
		duelistDefaults.setProperty(PROP_DECK, "0");
		duelistDefaults.setProperty(PROP_CARDS, "200");
		duelistDefaults.setProperty(PROP_MAX_SUMMONS, "5");
		duelistDefaults.setProperty(PROP_HAS_KEY, "FALSE");
		duelistDefaults.setProperty(PROP_HAS_RING, "FALSE");
		duelistDefaults.setProperty(PROP_RESUMMON_DMG, "1");
		duelistDefaults.setProperty(PROP_CHALLENGE, "FALSE");
		duelistDefaults.setProperty(PROP_UNLOCK, "FALSE");
		duelistDefaults.setProperty(PROP_FLIP, "FALSE");
		
		cardSets.add("All (221 cards)"); 
		cardSets.add("Full (144 cards)");
		cardSets.add("Reduced (121 cards)");
		cardSets.add("Limited (93 cards)");
		cardSets.add("Core (77 cards)");
		
		int save = 0;
		StarterDeck regularDeck = new StarterDeck(STANDARD_DECK, "Standard Deck (10 cards)", save, "Standard Deck"); starterDeckList.add(regularDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck dragDeck = new StarterDeck(DRAGON_DECK, "Dragon Deck (10 cards)", save, "Dragon Deck"); starterDeckList.add(dragDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck natDeck = new StarterDeck(NATURE_DECK, "Nature Deck (11 cards)", save, "Nature Deck"); starterDeckList.add(natDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck spellcDeck = new StarterDeck(SPELLCASTER_DECK, "Spellcaster Deck (10 cards)", save, "Spellcaster Deck"); starterDeckList.add(spellcDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck creaDeck = new StarterDeck(CREATOR_DECK, "Creator Deck (4 cards)", save, "Creator Deck"); starterDeckList.add(creaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran1Deck = new StarterDeck(RANDOM_DECK_SMALL, "Random Deck (10 cards)", save, "Random Deck (Small)"); starterDeckList.add(ran1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran2Deck = new StarterDeck(RANDOM_DECK_BIG, "Random Deck (15 cards)", save, "Random Deck (Big)"); starterDeckList.add(ran2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck toonDeck = new StarterDeck(TOON_DECK, "Toon Deck (11 cards)", save, "Toon Deck"); starterDeckList.add(toonDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck oDeck = new StarterDeck(ORB_DECK, "Orb Deck (10 cards)", save, "Orb Deck"); starterDeckList.add(oDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck resDeck = new StarterDeck(RESUMMON_DECK, "Resummon Deck (10 cards)", save, "Resummon Deck"); starterDeckList.add(resDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck gDeck = new StarterDeck(GENERATION_DECK, "Generation Deck (12 cards)", save, "Generation Deck"); starterDeckList.add(gDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ojDeck = new StarterDeck(OJAMA_DECK, "Ojama Deck (12 cards)", save, "Ojama Deck"); starterDeckList.add(ojDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck hpDeck = new StarterDeck(HEAL_DECK, "Heal Deck (9 cards)", save, "Heal Deck"); starterDeckList.add(hpDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck incDeck = new StarterDeck(INCREMENT_DECK, "Increment Deck (14 cards)", save, "Increment Deck"); starterDeckList.add(incDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck exodiaDeck = new StarterDeck(EXODIA_DECK, "Exodia Deck (60 cards)", save, "Exodia Deck"); starterDeckList.add(exodiaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck magnetDeck = new StarterDeck(MAGNET_DECK, "Superheavy Deck (12 cards)", save, "Superheavy Deck"); starterDeckList.add(magnetDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck aquaDeck = new StarterDeck(AQUA_DECK, "Aqua Deck (10 cards)", save, "Aqua Deck"); starterDeckList.add(aquaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck machineDeck = new StarterDeck(MACHINE_DECK, "Machine Deck (12 cards)", save, "Machine Deck"); starterDeckList.add(machineDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		
		
		
		for (StarterDeck d : starterDeckList) { startingDecks.add(d.getName()); }
		DECKS = starterDeckList.size();
		currentDeck = regularDeck;
		try 
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
            config.load();
            toonBtnBool = config.getBool(PROP_TOON_BTN);
            exodiaBtnBool = config.getBool(PROP_EXODIA_BTN);
            crossoverBtnBool = config.getBool(PROP_CROSSOVER_BTN);
            challengeMode = config.getBool(PROP_CHALLENGE);
            unlockAllDecks = config.getBool(PROP_UNLOCK);
            flipCardTags = config.getBool(PROP_FLIP);
            setIndex = config.getInt(PROP_SET);
            cardCount = config.getInt(PROP_CARDS);
            deckIndex = config.getInt(PROP_DECK);
            chosenDeckTag = findDeckTag(deckIndex);
            lastMaxSummons = config.getInt(PROP_MAX_SUMMONS);
            resummonDeckDamage = config.getInt(PROP_RESUMMON_DMG);
            if (debug) { lastMaxSummons = 50; }
            hasRing = config.getBool(PROP_HAS_RING);
            hasKey = config.getBool(PROP_HAS_KEY);
            
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

		logger.info("theDuelist:DefaultMod:DefaultMod() ---> Done setting up or loading the settings config file");
	}


	public static void initialize() {
		logger.info("theDuelist:DefaultMod:initialize() ---> Initializing Duelist Mod");
		DefaultMod defaultmod = new DefaultMod();
		logger.info("theDuelist:DefaultMod:initialize() ---> Duelist Mod Initialized");
	}

	// ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

	

	// =============== LOAD THE CHARACTER =================

	@Override
	public void receiveEditCharacters() 
	{
		// Yugi Moto
		BaseMod.addCharacter(new TheDuelist("the Duelist", TheDuelistEnum.THE_DUELIST),makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_DUELIST);

		// Seto Kaiba
		//BaseMod.addCharacter(new TheDuelist("the Rich Duelist", TheDuelistEnum.THE_RICH_DUELIST),makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_RICH_DUELIST);


		// Maximillion Pegasus
		//BaseMod.addCharacter(new TheDuelist("the Villian", TheDuelistEnum.THE_VILLIAN),makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_VILLIAN);

		receiveEditPotions();

		logger.info("theDuelist:DefaultMod:receiveEditCharacters() ---> Done editing characters");

	}

	// =============== /LOAD THE CHARACTER/ =================


	// =============== POST-INITIALIZE =================


	@Override
	public void receivePostInitialize() 
	{	
		//if (isApi)
		//{
		//	logger.info("theDuelist:DefaultMod:receivePostInitialize() ---> adding archetypes for API mod");
			//CharacterHelper.loadArchetypes();
		//}
		// END Archetype API Check
		
		// MOD OPTIONS PANEL
		logger.info("theDuelist:DefaultMod:receivePostInitialize() ---> Loading badge image and mod options");
		String loc = localize();

		Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
		ModPanel settingsPanel = new ModPanel();
		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
		UIStrings UI_String = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");
		
		float yPos = 750.0f;
		float xLabPos = 360.0f;
		float xLArrow = 615.0f;
		float xRArrow = 1110.0f;
		float xSelection = 680.0f;
		
		
		// Card Count Label
		String cardsString = UI_String.TEXT[6];
		ModLabel cardLabelTxt = new ModLabel(cardsString + cardCount, xLabPos - 10, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(cardLabelTxt);
		yPos-=50;
		// END Card Count Label
		
		// Check Box A
		String toonString = UI_String.TEXT[0];
		ModLabeledToggleButton toonBtn = new ModLabeledToggleButton(toonString,xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, toonBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			toonBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_TOON_BTN, toonBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(toonBtn);
		yPos-=50;
		// END Check Box A
		
		// Check Box B
		String exodiaString = UI_String.TEXT[1];
		ModLabeledToggleButton exodiaBtn = new ModLabeledToggleButton(exodiaString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, exodiaBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			exodiaBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_EXODIA_BTN, exodiaBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(exodiaBtn);
		yPos-=50;
		// END Check Box B
		
		// Check Box C
		String crossString = UI_String.TEXT[2];
		ModLabeledToggleButton crossoverBtn = new ModLabeledToggleButton(crossString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, crossoverBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			crossoverBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CROSSOVER_BTN, crossoverBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(crossoverBtn);
		yPos-=50;
		// END Check Box C
		
		// Check Box D
		String flipString = UI_String.TEXT[11];
		ModLabeledToggleButton flipBtn = new ModLabeledToggleButton(flipString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, flipCardTags, settingsPanel, (label) -> {}, (button) -> 
		{
			flipCardTags = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_FLIP, flipCardTags);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(flipBtn);
		yPos-=50;
		// END Check Box D
		
		// Check Box E
		String unlockString = UI_String.TEXT[10];
		ModLabeledToggleButton unlockBtn = new ModLabeledToggleButton(unlockString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, unlockAllDecks, settingsPanel, (label) -> {}, (button) -> 
		{
			unlockAllDecks = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_UNLOCK, unlockAllDecks);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(unlockBtn);
		yPos-=50;
		// END Check Box E
		
		// Check Box F
		String challengeString = UI_String.TEXT[9];
		ModLabeledToggleButton challengeBtn = new ModLabeledToggleButton(challengeString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, challengeMode, settingsPanel, (label) -> {}, (button) -> 
		{
			challengeMode = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CHALLENGE, challengeMode);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//resetCharSelect();
		});
		settingsPanel.addUIElement(challengeBtn);
		yPos-=50;
		// END Check Box F
		
		// Set Size Selector
		String setString = UI_String.TEXT[4];
		ModLabel setSelectLabelTxt = new ModLabel(setString,xLabPos, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectLabelTxt);
		ModLabel setSelectColorTxt = new ModLabel(cardSets.get(setIndex),xSelection, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectColorTxt);
		yPos-=15;
		ModButton setSelectLeftBtn = new ModButton(xLArrow, yPos, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
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
		settingsPanel.addUIElement(setSelectLeftBtn);
		ModButton setSelectRightBtn = new ModButton(xRArrow, yPos, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
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
		settingsPanel.addUIElement(setSelectRightBtn);
		yPos-=40;
		// END Set Size Selector
		
		// Starting Deck Selector
		String deckString = UI_String.TEXT[3];
		ModLabel setSelectLabelTxtB = new ModLabel(deckString, xLabPos, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectLabelTxtB);
		setSelectColorTxtB = new ModLabel(startingDecks.get(deckIndex),xSelection, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectColorTxtB);
		yPos-=15;
		ModButton setSelectLeftBtnB = new ModButton(xLArrow, yPos, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
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
			resetCharSelect();
		});
		settingsPanel.addUIElement(setSelectLeftBtnB);
		ModButton setSelectRightBtnB = new ModButton(xRArrow, yPos, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
			deckIndex = (deckIndex+1)%DECKS;
			setSelectColorTxtB.text = startingDecks.get(deckIndex);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setInt(PROP_DECK, deckIndex);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			resetCharSelect();
		});
		settingsPanel.addUIElement(setSelectRightBtnB);
		yPos-=50;
		// Starting Deck Selector

		// Info Labels
		String restartString = UI_String.TEXT[7];
		ModLabel extraLabelTxt = new ModLabel(restartString, xLabPos, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(extraLabelTxt);
		yPos-=35;
		String freshString = UI_String.TEXT[8];
		ModLabel extraLabelTxtB = new ModLabel(freshString, xLabPos, yPos,settingsPanel,(me)->{});
		settingsPanel.addUIElement(extraLabelTxtB);
		yPos-=35;
		// END Info Labels
		
		// Archetype API Message
		String apiString = UI_String.TEXT[5];
		ModLabel apiLabelTxt = new ModLabel(apiString, xLabPos, yPos,settingsPanel,(me)->{});
		//settingsPanel.addUIElement(apiLabelTxt);
		yPos-=50;
		// END Archetype API Message

		logger.info("theDuelist:DefaultMod:receivePostInitialize() ---> Done loading badge Image and mod options");

	}

	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		logger.info("theDuelist:DefaultMod:receiveEditPotions() ---> Beginning to edit potions");

		// Class Specific Potion. If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDuelistEnum.THE_DUELIST")
		BaseMod.addPotion(MillenniumElixir.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, MillenniumElixir.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPack.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, SealedPack.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPackB.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, SealedPackB.POTION_ID, TheDuelistEnum.THE_DUELIST);

		logger.info("theDuelist:DefaultMod:receiveEditPotions() ---> Done editing potions");
	}

	// ================ /ADD POTIONS/ ===================


	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		logger.info("theDuelist:DefaultMod:receiveEditRelics() ---> Adding relics");

		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		BaseMod.addRelicToCustomPool(new MillenniumPuzzle(), AbstractCardEnum.DUELIST_MONSTERS);
		if (!toonBtnBool) { BaseMod.addRelicToCustomPool(new MillenniumEye(), AbstractCardEnum.DUELIST_MONSTERS); }
		BaseMod.addRelicToCustomPool(new MillenniumRing(), AbstractCardEnum.DUELIST_MONSTERS);
		BaseMod.addRelicToCustomPool(new MillenniumKey(), AbstractCardEnum.DUELIST_MONSTERS);
		BaseMod.addRelicToCustomPool(new MillenniumRod(), AbstractCardEnum.DUELIST_MONSTERS);
		BaseMod.addRelicToCustomPool(new MillenniumCoin(), AbstractCardEnum.DUELIST_MONSTERS);
		if (!exodiaBtnBool) { BaseMod.addRelicToCustomPool(new StoneExxod(), AbstractCardEnum.DUELIST_MONSTERS); }
		BaseMod.addRelicToCustomPool(new GiftAnubis(), AbstractCardEnum.DUELIST_MONSTERS);
		AbstractDungeon.shopRelicPool.remove("Prismatic Shard");
		
		// This adds a relic to the Shared pool. Every character can find this relic.
		//BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

		logger.info("theDuelist:DefaultMod:receiveEditRelics() ---> Done adding relics!");
	}

	// ================ /ADD RELICS/ ===================

	// ================ ADD CARDS ===================

	@Override
	public void receiveEditCards() {
		//logger.info("Adding variables");
		// Add the Custom Dynamic Variables
		//BaseMod.addDynamicVariable(new WingedDragonVariable());

		// ================ ORB CARDS ===================
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> adding orb cards to array for orb modal");
		setupOrbCards();
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> done adding orb cards to array");
		
		// ================ LIBRARY CARDS ===================
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> adding all cards to myCards array");
		setupMyCards();
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> done adding all cards to myCards array");

		// ================ STARTER DECKS ===================
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> setting up starting deck");
		initStartDeckArrays();
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> starting deck set as: " + chosenDeckTag.name());
		
		// ================ COMPENDIUM MANIPULATION ===================
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> begin checking config options and removing cards");
		removeCardsFromSet();
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> all needed cards have been removed from myCards array");
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> done");
		
		// ================ COLORED CARDS ===================
		fillColoredCards();

	}

	// ================ /ADD CARDS/ ===================



	// ================ LOAD THE TEXT ===================

	@Override
	public void receiveEditStrings() {
		logger.info("theDuelist:DefaultMod:receiveEditStrings() ---> Beginning to edit strings");

		String loc = localize();
		
		// Card Strings
		BaseMod.loadCustomStringsFile(CardStrings.class,"defaultModResources/localization/" + loc + "/DuelistMod-Card-Strings.json");

		// UI Strings
		BaseMod.loadCustomStringsFile(UIStrings.class, "defaultModResources/localization/" + loc + "/DuelistMod-UI-Strings.json");
		
		// Power Strings
		BaseMod.loadCustomStringsFile(PowerStrings.class,"defaultModResources/localization/" + loc + "/DuelistMod-Power-Strings.json");

		// Relic Strings
		BaseMod.loadCustomStringsFile(RelicStrings.class,"defaultModResources/localization/" + loc + "/DuelistMod-Relic-Strings.json");

		// Potion Strings
		BaseMod.loadCustomStringsFile(PotionStrings.class,"defaultModResources/localization/" + loc + "/DuelistMod-Potion-Strings.json");

		// Orb Strings
		BaseMod.loadCustomStringsFile(OrbStrings.class,"defaultModResources/localization/" + loc + "/DuelistMod-Orb-Strings.json");
		
		// Character Strings
		BaseMod.loadCustomStringsFile(CharacterStrings.class, "defaultModResources/localization/" + loc + "/DuelistMod-Character-Strings.json");

		logger.info("theDuelist:DefaultMod:receiveEditStrings() ---> Done editing strings");
	}

	// ================ /LOAD THE TEXT/ ===================

	// ================ LOAD THE KEYWORDS ===================

	@Override
	public void receiveEditKeywords() 
	{
		String loc = localize();
		Gson gson = new Gson();
        String json = Gdx.files.internal("defaultModResources/localization/" + loc + "/DuelistMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
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
		if (c.hasTag(DefaultMod.TOKEN)) { return true; }
		else { return false; }
	}

	public static boolean isMonster(AbstractCard c)
	{
		if (c.hasTag(DefaultMod.MONSTER)) { return true; }
		else { return false; }
	}

	public static boolean isSpell(AbstractCard c)
	{
		if (c.hasTag(DefaultMod.SPELL)) { return true; }
		else { return false; }
	}

	public static boolean isTrap(AbstractCard c)
	{
		if (c.hasTag(DefaultMod.TRAP)) { return true; }
		else { return false; }
	}
	
	public static boolean isArchetype(AbstractCard c)
	{
		if (c.hasTag(DefaultMod.ARCHETYPE)) { return true; }
		else { return false; }
	}
	
	public static boolean isOrbCard(AbstractCard c)
	{
		if (c.hasTag(DefaultMod.ORB_CARD)) { return true; }
		else { return false; }
	}
	
	

	public static void resetCharSelect()
	{		
		setupStartDecksB();
		if (deckToStartWith.size() > 0)
		{
			CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
			for (AbstractCard c : deckToStartWith) { newStartGroup.addToRandomSpot(c);}
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.initializeDeck(newStartGroup);
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.sortAlphabetically(true);
		}
	}

	
	// HOOKS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void receiveOnBattleStart(AbstractRoom arg0) 
	{
		resetBuffPool();
		lastMaxSummons = 5;
		spellsThisCombat = 0;
		summonsThisCombat = 0;
		if (challengeMode) { lastMaxSummons = 4; }
		swordsPlayed = 0;
		logger.info("theDuelist:DefaultMod:receiveOnBattleStart() ---> Reset max summons to 5");
		if (hasRing) { lastMaxSummons = 8; if (challengeMode) { lastMaxSummons = 7; }}
		if (hasKey) { lastMaxSummons = 4; logger.info("theDuelist:DefaultMod:receiveOnBattleStart() ---> Reset max summons to 4");}
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (fullDebug) { lastMaxSummons = 50; }
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) 
	{
		playedOneCardThisCombat = false;
		lastMaxSummons = 5;
		spellsThisCombat = 0;
		summonsThisCombat = 0;
		if (challengeMode) { lastMaxSummons = 4; }
		swordsPlayed = 0;
		logger.info("theDuelist:DefaultMod:receivePostBattle() ---> Reset max summons to 5");
		if (hasRing) { lastMaxSummons = 8; if (challengeMode) { lastMaxSummons = 7; }}
		if (hasKey) { lastMaxSummons = 4; logger.info("theDuelist:DefaultMod:receiveOnBattleStart() ---> Reset max summons to 4");}
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
	
	}

	@Override
	public void receivePowersModified() 
	{
		for (AbstractOrb o : AbstractDungeon.player.orbs)
		{
			if (o instanceof DuelistOrb)
			{
				DuelistOrb oOrb = (DuelistOrb) o;
				oOrb.checkFocus();
			}
		}
	}

	@Override
	public void receivePostDeath() 
	{
		spellsThisCombat = 0;
		summonsThisCombat = 0;
		AbstractPlayer.customMods = new ArrayList<String>();
		hasRing = false;
		hasKey = false;
		lastMaxSummons = 5;
		if (challengeMode) { lastMaxSummons = 4; }
		swordsPlayed = 0;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setBool(PROP_HAS_KEY, hasKey);
			config.setInt(PROP_RESUMMON_DMG, 1);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receiveCardUsed(AbstractCard arg0) 
	{
		playedOneCardThisCombat = true;
		logger.info("theDuelist:DefaultMod:receiveCardUsed() ---> Card: " + arg0.name);
		if (arg0.hasTag(DefaultMod.SPELL))
		{
			spellsThisCombat++;
			logger.info("theDuelist:DefaultMod:receiveCardUsed() ---> incremented spellsThisCombat, new value: " + spellsThisCombat);
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
				setupStartDecksB();
				ArrayList<AbstractCard> startingDeck = new ArrayList<AbstractCard>();
				startingDeck.addAll(deckToStartWith);
				if (startingDeck.size() > 0)
				{
					CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
					for (AbstractCard c : startingDeck) { newStartGroup.addToRandomSpot(c); }
					arg1.initializeDeck(newStartGroup);
					arg1.sortAlphabetically(true);
				}
			}
		}
		else
		{
			logger.info("theDuelist:DefaultMod:receivePostCreateStartingDeck() ---> found bad mods");
		}
	}
	
	@Override
	public void receiveRelicGet(AbstractRelic arg0) 
	{
		
	}
	
	@Override
	public void receivePostDraw(AbstractCard arg0) 
	{
		// Underdog - Draw monster = draw 1 card
		if (AbstractDungeon.player.hasPower(HeartUnderdogPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0.hasTag(DefaultMod.MONSTER) && handSize < 10)
			{
				DuelistCard.draw(1);
			}
		}
		
		// Underspell - Draw spell = copy it
		if (AbstractDungeon.player.hasPower(HeartUnderspellPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0.hasTag(DefaultMod.SPELL) && handSize < 10)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(arg0.makeCopy(), arg0.upgraded, true, true, false, 1, 3));
			}
		}
		
		// Undertrap - Draw trap = gain 10 HP
		if (AbstractDungeon.player.hasPower(HeartUndertrapPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0.hasTag(DefaultMod.TRAP))
			{
				DuelistCard.heal(AbstractDungeon.player, 10);
			}
		}
		
		// Undertribute - Draw tribute monster = Summon 1
		if (AbstractDungeon.player.hasPower(HeartUndertributePower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0 instanceof DuelistCard)
			{
				DuelistCard ref = (DuelistCard) arg0;
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
		
	}

	@Override
	public void receiveCustomModeMods(List<CustomMod> arg0) 
	{
		
	}
	
	
	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static StarterDeck getCurrentDeck()
	{
		for (StarterDeck d : starterDeckList) { if (d.getIndex() == deckIndex) { return d; }}
		return starterDeckList.get(0);
	}

	private static CardTags findDeckTag(int deckIndex) 
	{
		for (StarterDeck d : starterDeckList) { if (d.getIndex() == deckIndex) { return d.getDeckTag(); }}
		return null;
	}
	
	private void initStartDeckArrays()
	{
		ArrayList<CardTags> deckTagList = getAllDeckTags();
		for (DuelistCard c : myCards)
		{
			for (CardTags t : deckTagList)
			{
				if (c.hasTag(t))
				{
					StarterDeck ref = deckTagMap.get(t);
					int copyIndex = StarterDeck.getDeckCopiesMap().get(ref.getDeckTag());
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) { ref.getDeck().add(c); }
				}
			}
		}
	}
	
	private ArrayList<CardTags> getAllDeckTags()
	{
		ArrayList<CardTags> deckTagList = new ArrayList<CardTags>();
		for (StarterDeck d : starterDeckList) { deckTagList.add(d.getDeckTag()); }
		return deckTagList;
	}
	
	public static boolean hasTags(AbstractCard c, ArrayList<CardTags> tags)
	{
		boolean hasAnyTag = false;
		for (CardTags t : tags) { if (c.hasTag(t)) { hasAnyTag = true; }}
		return hasAnyTag;
	}
	
	private static void setupStartDecksB()
	{
		chosenDeckTag = findDeckTag(deckIndex);
		StarterDeck refDeck = null;
		for (StarterDeck d : starterDeckList) { if (d.getDeckTag().equals(chosenDeckTag)) { refDeck = d; }}
		if (refDeck != null)
		{
			if (chosenDeckTag.equals(RANDOM_DECK_SMALL))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < randomDeckSmallSize; i++) { deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard());}
			}
			
			else if (chosenDeckTag.equals(RANDOM_DECK_BIG))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < randomDeckBigSize; i++) { deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard()); }
			}
			
			else if (chosenDeckTag.equals(GENERATION_DECK) || chosenDeckTag.equals(OJAMA_DECK))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				deckToStartWith.addAll(refDeck.getDeck());
				deckToStartWith.add(new RandomSoldier());
				deckToStartWith.add(new RandomSoldier());
			}
			
			else if (chosenDeckTag.equals(TOON_DECK))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				deckToStartWith.addAll(refDeck.getDeck());
				deckToStartWith.add(new RandomSoldier());
			}
			
			else 
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				deckToStartWith.addAll(refDeck.getDeck());
			}
		}
		
		else
		{
			initStandardDeck();
			deckToStartWith = new ArrayList<DuelistCard>();
			deckToStartWith.addAll(standardDeck);
		}
		
	}
	
	private static void initStandardDeck()
	{
		standardDeck = new ArrayList<DuelistCard>();
		for (DuelistCard c : myCards) { if (c.hasTag(STANDARD_DECK)) { for (int i = 0; i < c.startingDeckCopies; i++) { standardDeck.add(c); }}}
	}
	
	// RANDOM BUFF HELPERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static AbstractPower createRandomBuff()
	{
		initBuffMap(AbstractDungeon.player);
		Set<Entry<String, AbstractPower>> set = buffMap.entrySet();
		ArrayList<AbstractPower> localBuffs = new ArrayList<AbstractPower>();
		for (Entry<String, AbstractPower> e : set) { localBuffs.add(e.getValue()); }
		int roll = AbstractDungeon.cardRandomRng.random(localBuffs.size() - 1);
		return localBuffs.get(roll);
	}
	
	private static void resetBuffPool()
	{
		int noBuffs = AbstractDungeon.cardRandomRng.random(3, 6);
		randomBuffs = new ArrayList<AbstractPower>();
		randomBuffStrings = new ArrayList<String>();
		for (int i = 0; i < noBuffs; i++)
		{
			AbstractPower randomBuff = createRandomBuff();
			while (randomBuffStrings.contains(randomBuff.name)) { randomBuff = createRandomBuff(); }
			randomBuffs.add(randomBuff);
			randomBuffStrings.add(randomBuff.name);
		}
	}
	
	public static void resetRandomBuffs()
	{
		initBuffMap(AbstractDungeon.player);
		for (int i = 0; i < randomBuffs.size(); i++) { randomBuffs.set(i, buffMap.get(randomBuffs.get(i).name));	}
	}
	
	public static void resetRandomBuffs(int turnNum)
	{
		initBuffMap(AbstractDungeon.player, turnNum);
		for (int i = 0; i < randomBuffs.size(); i++) { randomBuffs.set(i, buffMap.get(randomBuffs.get(i).name)); }
	}
	
	
	private static void initBuffMap(AbstractPlayer p)
	{
		buffMap = new HashMap<String, AbstractPower>();
		int turnNum = AbstractDungeon.cardRandomRng.random(1, 4);
		logger.info("random buff map turn num roll: " + turnNum);
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower intan = new IntangiblePlayerPower(p, 1);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		AbstractPower creative = new CreativeAIPower(p, 1); //probably too good
		//AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower equal = new EquilibriumPower(p, 2);
		AbstractPower noPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fire = new FireBreathingPower(p, 3);
		AbstractPower jugger = new JuggernautPower(p, turnNum);
		AbstractPower metal = new MetallicizePower(p, turnNum);
		AbstractPower penNib = new PenNibPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower storm = new StormPower(p, 1);
		AbstractPower orbHeal = new OrbHealerPower(p, turnNum);
		AbstractPower tombLoot = new EnergyTreasurePower(p, turnNum);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum * 10);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, 1);
		AbstractPower mayhem = new MayhemPower(p, 1);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower amplify = new AmplifyPower(p, 1);
		AbstractPower angry = new AngryPower(p, 1);
		AbstractPower anger = new AngerPower(p, 1);
		AbstractPower buffer = new BufferPower(p, 1);
		AbstractPower conserve = new ConservePower(p, 1);
		AbstractPower curiosity = new CuriosityPower(p, 1);
		AbstractPower[] buffs = new AbstractPower[] { str };
		if (challengeMode)
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, thorns, blur, 
					orbHeal, tombLoot, orbEvoker, tombPilfer,
					focus, reductionist, envenom,
					anger, angry, conserve, curiosity 
			};
		}
		else
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
					burst, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
					orbEvoker, tombPilfer, retainCards, timeWizard,
					generosity, focus, reductionist, creative, mayhem, envenom,
					amplify, anger, angry, buffer, conserve, curiosity 
			};
		}
		for (AbstractPower a : buffs)
		{
			buffMap.put(a.name, a);
		}
	}
	
	private static void initBuffMap(AbstractPlayer p, int turnNum)
	{
		buffMap = new HashMap<String, AbstractPower>();
		logger.info("random buff map turn num roll: " + turnNum);
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower intan = new IntangiblePlayerPower(p, 1);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		AbstractPower creative = new CreativeAIPower(p, 1); //probably too good
		//AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower equal = new EquilibriumPower(p, 2);
		AbstractPower noPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fire = new FireBreathingPower(p, 3);
		AbstractPower jugger = new JuggernautPower(p, turnNum);
		AbstractPower metal = new MetallicizePower(p, turnNum);
		AbstractPower penNib = new PenNibPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower storm = new StormPower(p, 1);
		AbstractPower orbHeal = new OrbHealerPower(p, turnNum);
		AbstractPower tombLoot = new EnergyTreasurePower(p, turnNum);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum * 10);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, 1);
		AbstractPower mayhem = new MayhemPower(p, 1);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower amplify = new AmplifyPower(p, 1);
		AbstractPower angry = new AngryPower(p, 1);
		AbstractPower anger = new AngerPower(p, 1);
		AbstractPower buffer = new BufferPower(p, 1);
		AbstractPower conserve = new ConservePower(p, 1);
		AbstractPower curiosity = new CuriosityPower(p, 1);
		AbstractPower[] buffs = new AbstractPower[] { str };
		if (challengeMode)
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, thorns, blur, 
					orbHeal, tombLoot, orbEvoker, tombPilfer,
					focus, reductionist, envenom,
					anger, angry, conserve, curiosity 
			};
		}
		else
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
					burst, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
					orbEvoker, tombPilfer, retainCards, timeWizard,
					generosity, focus, reductionist, creative, mayhem, envenom,
					amplify, anger, angry, buffer, conserve, curiosity 
			};
		}
		for (AbstractPower a : buffs)
		{
			buffMap.put(a.name, a);
		}
	}
	
	// LOCALIZATION METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Copied from Jedi (who copied from Gatherer)
    private static String GetLocString(String locCode, String name)
    {
        return Gdx.files.internal("resources/defaultModResources/localization/" + locCode + "/" + name + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
    }
	
	public static String localize() 
	{
        switch (Settings.language) 
        {
            case RUS:
                return "rus";
            case ENG:
                return "eng";
            case ZHS:
            	return "zhs";
            case ZHT:
            	return "zht";
            case KOR:
            	return "kor";
            default:
                return "eng";
        }
    }
	
	
	
	// DEBUG PRINT COMMANDS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void printTextForTranslation()
	{
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> START");
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> Card Names");
		for (DuelistCard c : myCards)
		{
			System.out.println(c.originalName);
			//logger.info(c.originalName);
		}
		
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> Card IDs");
		for (DuelistCard c : myCards)
		{
			System.out.println(";()" + c.getID() + ",;()");
			//logger.info(c.originalName);
		}
		
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> Card Descriptions");
		for (DuelistCard c : myCards)
		{
			System.out.println(c.rawDescription + " - " + DuelistCard.UPGRADE_DESCRIPTION);
			//logger.info(c.rawDescription);
		}
		
		/*
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> Powers");
		String[] powerList = new String[] {"SummonPower", "DespairPower","JamPower", "ToonWorldPower",
				"ObeliskPower", "AlphaMagPower", "BetaMagPower", "GammaMagPower", "GreedShardPower",
				"MirrorPower", "ToonBriefcasePower", "DragonCapturePower", "PotGenerosityPower", "CannonPower",
				"CatapultPower", "BadReactionPower", "CastlePower", "EmperorPower", "MagicCylinderPower",
				"MirrorForcePower", "ImperialPower", "SliferSkyPower", "ExodiaPower", "DarkMirrorPower",
				"ParasitePower", "StormingMirrorPower", "RadiantMirrorPower", "SwordsBurnPower", "SwordsConcealPower",
				"SwordsRevealPower", "SummonSicknessPower", "TributeSicknessPower", "EvokeSicknessPower", "OrbHealPower",
				"OrbEvokerPower", "EnergyTreasurePower", "HealGoldPower", "TributeToonPower", "TributeToonPowerB", "GravityAxePower",
				"ToonRollbackPower", "ToonKingdomPower", "ReducerPower", "CrystallizerPower", "MountainPower", "VioletCrystalPower",
				"YamiPower", "TimeWizardPower", "TrapHolePower", "SwordDeepPower", "CocoonPower", "SarraceniantPower", "JinzoPower",
				"UltimateOfferingPower"};
		for (String s : powerList)
		{
			PowerStrings powerString = CardCrawlGame.languagePack.getPowerStrings(DefaultMod.makeID(s));
			String[] powerDesc = powerString.DESCRIPTIONS;
			for (int i = 0; i < powerDesc.length; i++)
			{
				if (i == 0) { System.out.print(s + " - " + powerDesc[i]);}
				else { System.out.print(powerDesc[i]); }
			}
			logger.info("");
		}
		
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> Relics");
		String[] relicList = new String[] {"MillenniumPuzzle", "MillenniumEye", "MillenniumRing", "MillenniumKey",
				"MillenniumRod", "MillenniumCoin", "StoneExxod", "GiftAnubis"};
		for (String s : relicList)
		{
			RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(DefaultMod.makeID(s));
			String[] relicDesc = relicString.DESCRIPTIONS;
			String flavor = relicString.FLAVOR;
			for (int i = 0; i < relicDesc.length; i++)
			{
				if (i == 0) { System.out.print(s + " - " + relicDesc[i]);}
				else { System.out.print(relicDesc[i]); }
			}
			logger.info(" -- " + flavor);
		}
		*/
		
		
		logger.info("theDuelist:DefaultMod:printTextForTranslation() ---> END");
	}
	
	private void printCardSetsForGithubReadme(ArrayList<DuelistCard> cardsToPrint)
	{
		ArrayList<DuelistCard> all = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> full = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> reduced = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> limited = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> mod = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> random = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> core = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> toon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> exodia = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> dragon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> spellcaster = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> nature = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> creator = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> toonDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> orb = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> resummon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> generation = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> ojama = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> healDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> incrementDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> exodiaDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> magnetDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> aquaDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> machineDeck = new ArrayList<DuelistCard>();
		for (DuelistCard c : cardsToPrint)
		{
			if (c.hasTag(DefaultMod.ALL))
			{
				all.add(c);
			}
			else if (c.hasTag(DefaultMod.FULL))
			{
				full.add(c);
			}
			else if (c.hasTag(DefaultMod.REDUCED))
			{
				reduced.add(c);
			}
			
			else if (c.hasTag(DefaultMod.LIMITED))
			{
				limited.add(c);
			}
			
			else if (c.hasTag(DefaultMod.CONSPIRE) || c.hasTag(DefaultMod.REPLAYSPIRE))
			{
				mod.add(c);
			}
			
			else if (c.hasTag(DefaultMod.RANDOMONLY))
			{
				random.add(c);
			}
			
			else
			{
				core.add(c);
			}
			
			if (c.hasTag(DefaultMod.EXODIA))
			{
				exodia.add(c);
			}
			
			if (c.hasTag(DefaultMod.TOON))
			{
				toon.add(c);
			}
			
			if (c.hasTag(DefaultMod.DRAGON_DECK))
			{
				dragon.add(c);
			}
			
			if (c.hasTag(DefaultMod.SPELLCASTER_DECK))
			{
				spellcaster.add(c);
			}
			
			if (c.hasTag(DefaultMod.NATURE_DECK))
			{
				nature.add(c);
			}
			
			if (c.hasTag(DefaultMod.CREATOR_DECK))
			{
				creator.add(c);
			}
			
			if (c.hasTag(DefaultMod.TOON_DECK))
			{
				toonDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.ORB_DECK))
			{
				orb.add(c);
			}
			
			if (c.hasTag(DefaultMod.RESUMMON_DECK))
			{
				resummon.add(c);
			}
			
			if (c.hasTag(DefaultMod.GENERATION_DECK))
			{
				generation.add(c);
			}
			
			if (c.hasTag(DefaultMod.OJAMA_DECK))
			{
				ojama.add(c);
			}
			
			if (c.hasTag(DefaultMod.HEAL_DECK))
			{
				healDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.HEAL_DECK))
			{
				healDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.INCREMENT_DECK))
			{
				incrementDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.EXODIA_DECK))
			{
				exodiaDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.MAGNET_DECK))
			{
				magnetDeck.add(c);
			}
			
			if (c.hasTag(DefaultMod.MACHINE_DECK))
			{
				machineDeck.add(c);
			}
		}
		
		for (DuelistCard c : core)
		{
			logger.info(c.originalName + " - " + "[i]Core[/i]");
		}
		
		for (DuelistCard c : limited)
		{
			logger.info(c.originalName + " - " + "[i]Limited[/i]");
		}
		
		for (DuelistCard c : reduced)
		{
			logger.info(c.originalName + " - " + "[i]Reduced[/i]");
		}
		
		for (DuelistCard c : full)
		{
			logger.info(c.originalName + " - " + "[i]Full[/i]");
		}
		
		for (DuelistCard c : all)
		{
			logger.info(c.originalName + " - " + "[i]All[/i]");
		}
		
		for (DuelistCard c : mod)
		{
			logger.info(c.originalName + " - " + "[i]Crossover[/i]");
		}
		
		for (DuelistCard c : random)
		{
			logger.info(c.originalName + " - " + "[i]Random generation only[/i]");
		}
		
		for (DuelistCard c : toon)
		{
			logger.info(c.originalName + " - " + "[i]Toons[/i]");
		}
		
		for (DuelistCard c : exodia)
		{
			logger.info(c.originalName + " - " + "[i]Exodia[/i]");
		}
		
		for (DuelistCard c : dragon)
		{
			logger.info(c.originalName + " - " + "[i]Dragon Deck[/i]");
		}
		
		for (DuelistCard c : spellcaster)
		{
			logger.info(c.originalName + " - " + "[i]Spellcaster Deck[/i]");
		}
		
		for (DuelistCard c : nature)
		{
			logger.info(c.originalName + " - " + "[i]Nature Deck[/i]");
		}
		
		for (DuelistCard c : creator)
		{
			logger.info(c.originalName + " - " + "[i]Creator Deck[/i]");
		}
		
		for (DuelistCard c : toonDeck)
		{
			logger.info(c.originalName + " - " + "[i]Toon Deck[/i]");
		}
		
		for (DuelistCard c : orb)
		{
			logger.info(c.originalName + " - " + "[i]Orb Deck[/i]");
		}
		
		for (DuelistCard c : resummon)
		{
			logger.info(c.originalName + " - " + "[i]Resummon Deck[/i]");
		}
		
		for (DuelistCard c : generation)
		{
			logger.info(c.originalName + " - " + "[i]Generation Deck[/i]");
		}
		
		for (DuelistCard c : ojama)
		{
			logger.info(c.originalName + " - " + "[i]Ojama Deck[/i]");
		}
		
		for (DuelistCard c : healDeck)
		{
			logger.info(c.originalName + " - " + "[i]Heal Deck[/i]");
		}		
		
		for (DuelistCard c : incrementDeck)
		{
			logger.info(c.originalName + " - " + "[i]Increment Deck[/i]");
		}
		
		for (DuelistCard c : exodiaDeck)
		{
			logger.info(c.originalName + " - " + "[i]Exodia Deck[/i]");
		}
		
		for (DuelistCard c : magnetDeck)
		{
			logger.info(c.originalName + " - " + "[i]Magnet Deck[/i]");
		}
		
		for (DuelistCard c : aquaDeck)
		{
			logger.info(c.originalName + " - " + "[i]Aqua Deck[/i]");
		}
		
		for (DuelistCard c : machineDeck)
		{
			logger.info(c.originalName + " - " + "[i]Machine Deck[/i]");
		}
	}
	
	// COMPENDIUM MANIPULATION FUNCTIONS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<AbstractCard> getAllColoredCards()
	{
		return null;
	}
	
	public void removeCardsFromSet()
	{
		// If Grem's Archetype API is loaded, don't mess anything up just skip this section
		// and jump to adding all the cards to the library, unlocking them, and letting Grem work his magic
		//if (!isApi)
		//{
		// If they are allowing the extra mod cards, check if the mods are loaded and remove the cards if not
		if (crossoverBtnBool)
		{
			// Remove Replay-dependent cards
			if (!Loader.isModLoaded("ReplayTheSpireMod"))
			{
				for (int i = 0; i < myCards.size(); i++)
				{
					if (myCards.get(i).hasTag(DefaultMod.REPLAYSPIRE))
					{
						myCards.remove(i);
						i = 0;
					}
				}
			}

			// Remove Conspire-dependent cards
			if (!Loader.isModLoaded("conspire"))
			{
				for (int i = 0; i < myCards.size(); i++)
				{
					if (myCards.get(i).hasTag(DefaultMod.CONSPIRE))
					{
						myCards.remove(i);
						i = 0;
					}
				}
			}
		}

		// If they are not allowing the extra mod cards just remove them
		else if (!crossoverBtnBool)
		{
			for (int i = 0; i < myCards.size(); i++)
			{
				if (myCards.get(i).hasTag(DefaultMod.CONSPIRE) || myCards.get(i).hasTag(DefaultMod.REPLAYSPIRE))
				{
					myCards.remove(i);
					i = 0;
				}
			}
		}

		// If debugging, don't remove toons, exodia or any set/random-only cards
		if (!fullDebug)
		{
			if (exodiaBtnBool)
			{
				for (int i = 0; i < myCards.size(); i++)
				{
					if (myCards.get(i).hasTag(DefaultMod.EXODIA))
					{
						myCards.remove(i);
						i = 0;
					}
				}
			}

			if (toonBtnBool)
			{
				for (int i = 0; i < myCards.size(); i++)
				{
					if ((myCards.get(i).hasTag(DefaultMod.TOON)))
					{
						myCards.remove(i);
						i = 0;
					}
				}
			}

			switch (setIndex)
			{
			// Full - [ removes 35 cards ] 187 cards
			case 1:				
				for (int i = 0; i < myCards.size(); i++)
				{
					if ((myCards.get(i).hasTag(DefaultMod.ALL)))
					{
						myCards.remove(i);
						i = 0;
					}
				}
				break;
				// Reduced - [ removes 20 cards ] 132 cards
			case 2:				
				for (int i = 0; i < myCards.size(); i++)
				{
					if ((myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL)))
					{
						myCards.remove(i);
						i = 0;
					}
				}
				break;
				// Limited - [ removes 48 cards ] 104 cards
			case 3:				
				for (int i = 0; i < myCards.size(); i++)
				{
					if ((myCards.get(i).hasTag(DefaultMod.REDUCED) || myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL)))
					{
						myCards.remove(i);
						i = 0;
					}
				}				
				break;
				// Core - [ removes 77 cards ] 75 cards
			case 4:
				for (int i = 0; i < myCards.size(); i++)
				{
					if ((myCards.get(i).hasTag(DefaultMod.LIMITED) || myCards.get(i).hasTag(DefaultMod.REDUCED) || myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL)))
					{
						myCards.remove(i);
						i = 0;
					}
				}
				break;
			default:
				break;
			}
			// END Switch removal
		}
		// END Debug Check
		//}
		// END API Check
		
		int tempCardCount = 0;
		for (DuelistCard c : myCards) 
		{ 
			if ((!c.hasTag(DefaultMod.RANDOMONLY) && (!c.hasTag(DefaultMod.RANDOMONLY_NOCREATOR))))
			{
				BaseMod.addCard(c); UnlockTracker.unlockCard(c.getID()); summonMap.put(c.originalName, c); tempCardCount++;
			}
			else
			{
				summonMap.put(c.originalName, c);				
				if (!c.rarity.equals(CardRarity.SPECIAL)) { UnlockTracker.unlockCard(c.getID()); }
			}
		}

		summonMap.put("Great Moth", new GreatMoth());
		summonMap.put("Puzzle Token", new Token());
		summonMap.put("Ancient Token", new Token());
		summonMap.put("Anubis Token", new Token());
		summonMap.put("Glitch Token", new Token());
		summonMap.put("Summoner Token", new Token());
		summonMap.put("Gate Token", new Token());
		summonMap.put("Jam Token", new Token());
		summonMap.put("Castle Token", new Token());
		summonMap.put("Storm Token", new Token());
		summonMap.put("Random Token", new Token());
		summonMap.put("Pot Token", new Token());
		summonMap.put("Buffer Token", new Token());
		summonMap.put("Blood Token", new Token());
		summonMap.put("Hane Token", new Token());
		summonMap.put("Bonanza Token", new Token());
		summonMap.put("Orb Token", new Token());
		summonMap.put("Resummon Token", new Token());
		summonMap.put("Resummoned Token", new Token());
		summonMap.put("Stim Token", new Token());
		summonMap.put("Underdog Token", new Token());
		summonMap.put("Judge Token", new Token());
		summonMap.put("Exxod Token", new Token());
		summonMap.put("Spellcaster Token", new SpellcasterToken());
		summonMap.put("Predaplant Token", new PredaplantToken());
		summonMap.put("Kuriboh Token", new KuribohToken());
		summonMap.put("Exploding Token", new ExplosiveToken());
		summonMap.put("Explosive Token", new ExplosiveToken());
		summonMap.put("Shadow Token", new ShadowToken());
		summonMap.put("Insect Token", new InsectToken());
		summonMap.put("Plant Token", new PlantToken());
		cardCount = tempCardCount;
		
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> done initializing cards");
		logger.info("theDuelist:DefaultMod:receiveEditCards() ---> saving config options for card set");
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_CARDS, cardCount);
			config.setInt(PROP_DECK, deckIndex);
			config.setInt(PROP_MAX_SUMMONS, lastMaxSummons);
			config.setInt(PROP_RESUMMON_DMG, resummonDeckDamage);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setupMyCards()
	{
		// CORE Set - 74 cards
		// Starting Deck - 6 cards
		myCards.add(new CastleWalls());
		myCards.add(new GiantSoldier());
		myCards.add(new Ookazi());
		myCards.add(new ScrapFactory());
		myCards.add(new SevenColoredFish());
		myCards.add(new SummonedSkull());
		// Other core cards - 68 cards
		myCards.add(new ArmoredZombie());
		myCards.add(new AxeDespair());
		myCards.add(new BabyDragon());
		myCards.add(new BigFire());
		myCards.add(new BlizzardDragon());
		myCards.add(new BlueEyes());
		myCards.add(new BlueEyesUltimate());
		myCards.add(new BusterBlader());
		myCards.add(new CannonSoldier());
		myCards.add(new CardDestruction());
		myCards.add(new CastleDarkIllusions());
		myCards.add(new CatapultTurtle());
		myCards.add(new CaveDragon());
		myCards.add(new CelticGuardian());
		myCards.add(new ChangeHeart());
		myCards.add(new DarkMagician());
		myCards.add(new DarklordMarie());
		myCards.add(new DianKeto());
		myCards.add(new DragonCaptureJar());
		myCards.add(new FiendMegacyber());
		myCards.add(new Fissure());
		myCards.add(new FluteSummoning());
		myCards.add(new FortressWarrior());
		myCards.add(new GaiaDragonChamp());
		myCards.add(new GaiaFierce());
		myCards.add(new GeminiElf());
		myCards.add(new GracefulCharity());
		myCards.add(new GravityAxe());
		myCards.add(new HaneHane());
		myCards.add(new Hinotama());
		myCards.add(new ImperialOrder());
		myCards.add(new InjectionFairy());
		myCards.add(new InsectQueen());
		myCards.add(new IslandTurtle());
		myCards.add(new JamBreeding());
		myCards.add(new JudgeMan());
		myCards.add(new Kuriboh());
		myCards.add(new LabyrinthWall());		
		myCards.add(new LesserDragon());
		myCards.add(new LordD());
		myCards.add(new MirrorForce());
		myCards.add(new MonsterReborn());
		myCards.add(new Mountain());
		myCards.add(new MysticalElf());
		myCards.add(new ObeliskTormentor());
		myCards.add(new PotGenerosity());
		myCards.add(new PotGreed());
		myCards.add(new PreventRat());
		myCards.add(new Pumpking());
		myCards.add(new Raigeki());
		myCards.add(new RainMercy());
		myCards.add(new RedEyes());
		myCards.add(new Relinquished());
		myCards.add(new SangaEarth());
		myCards.add(new SangaThunder());		
		myCards.add(new Scapegoat());			
		myCards.add(new SliferSky());
		myCards.add(new SmallLabyrinthWall());
		myCards.add(new SnowDragon());
		myCards.add(new SnowdustDragon());
		myCards.add(new SpiritHarp());		
		myCards.add(new SuperheavyBenkei());
		myCards.add(new SuperheavyScales());
		myCards.add(new SuperheavySwordsman());
		myCards.add(new SuperheavyWaraji());
		myCards.add(new ThunderDragon());
		myCards.add(new WingedDragonRa());
		myCards.add(new Yami());
		myCards.add(new NeoMagic());
		myCards.add(new GoldenApples());
		myCards.add(new SphereKuriboh());
		myCards.add(new Wiseman());
		// END CORE SET

		// ALL Set -  cards
		myCards.add(new MachineKing());
		myCards.add(new BookSecret());
		myCards.add(new HeavyStorm());
		myCards.add(new FogKing());
		myCards.add(new Lajinn());
		myCards.add(new KingYami());
		myCards.add(new BlacklandFireDragon());
		myCards.add(new WhiteNightDragon());
		myCards.add(new WhiteHornDragon());
		myCards.add(new RevivalJam());
		myCards.add(new StimPack());
		myCards.add(new BottomlessTrapHole());
		myCards.add(new SwordDeepSeated());
		myCards.add(new MonsterEgg());
		myCards.add(new SteamTrainKing());
		myCards.add(new MachineFactory());
		myCards.add(new TributeDoomed());
		myCards.add(new PetitMoth());
		myCards.add(new CocoonEvolution());
		myCards.add(new CheerfulCoffin());
		myCards.add(new TheCreator());
		myCards.add(new Polymerization());
		myCards.add(new VioletCrystal());
		myCards.add(new Predaponics());
		myCards.add(new MetalDragon());
		myCards.add(new SuperSolarNutrient());
		myCards.add(new Gigaplant());
		myCards.add(new BasicInsect());		
		myCards.add(new BSkullDragon());
		myCards.add(new DarkfireDragon());
		myCards.add(new EmpressMantis());
		myCards.add(new Grasschopper());
		myCards.add(new Jinzo());
		myCards.add(new LeviaDragon());
		myCards.add(new ManEaterBug());
		myCards.add(new OceanDragonLord());
		myCards.add(new PredaplantChimerafflesia());
		myCards.add(new PredaplantChlamydosundew());
		myCards.add(new PredaplantDrosophyllum());
		myCards.add(new PredaplantFlytrap());
		myCards.add(new PredaplantPterapenthes());
		myCards.add(new PredaplantSarraceniant());
		myCards.add(new PredaplantSpinodionaea());
		myCards.add(new Predapruning());
		myCards.add(new TrihornedDragon());
		myCards.add(new Wiretap());
		myCards.add(new Reinforcements());
		myCards.add(new UltimateOffering());
		myCards.add(new JerryBeansMan());
		myCards.add(new Illusionist());
		myCards.add(new ExploderDragon());
		myCards.add(new Invigoration());
		myCards.add(new InvitationDarkSleep());
		myCards.add(new AcidTrapHole());
		myCards.add(new AltarTribute());
		myCards.add(new BlizzardPrincess());
		myCards.add(new CardSafeReturn());
		myCards.add(new Cloning());
		myCards.add(new ComicHand());
		myCards.add(new ContractExodia());
		myCards.add(new DarkCreator());
		myCards.add(new DoubleCoston());
		myCards.add(new GatesDarkWorld());
		myCards.add(new HammerShot());
		myCards.add(new HeartUnderdog());
		myCards.add(new InsectKnight());
		myCards.add(new JiraiGumo());
		myCards.add(new MythicalBeast());
		myCards.add(new PotForbidden());
		myCards.add(new SmashingGround());
		myCards.add(new Terraforming());
		myCards.add(new TerraTerrible());
		myCards.add(new IcyCrevasse());
		myCards.add(new StrayLambs());
		myCards.add(new GuardianAngel());
		myCards.add(new ShadowToon());
		myCards.add(new ShallowGrave());
		myCards.add(new MiniPolymerization());
		// END ALL Set

		// FULL Set - 22 cards
		myCards.add(new SwordsRevealing());
		myCards.add(new TimeWizard()); 
		myCards.add(new TrapHole());
		myCards.add(new BlueEyesToon());
		myCards.add(new DragonMaster());
		myCards.add(new Gandora());
		myCards.add(new LegendaryExodia());
		myCards.add(new RadiantMirrorForce());
		myCards.add(new RedEyesToon());
		myCards.add(new SuperancientDinobeast());
		myCards.add(new TokenVacuum());
		myCards.add(new ToonBarrelDragon());
		myCards.add(new ToonBriefcase());
		myCards.add(new ToonDarkMagician());
		myCards.add(new ToonGeminiElf());
		myCards.add(new ToonMagic());	   
		myCards.add(new ToonMask());
		myCards.add(new ToonMermaid());
		myCards.add(new ToonRollback());
		myCards.add(new ToonSummonedSkull());
		myCards.add(new ToonWorld());
		myCards.add(new ToonKingdom());
		// END FULL Set

		// REDUCED Set - 28 cards
		myCards.add(new CurseDragon());
		myCards.add(new CyberDragon());
		myCards.add(new DarkFactory());
		myCards.add(new FiendSkull());
		myCards.add(new FiveHeaded());
		myCards.add(new GiantTrunade());
		myCards.add(new HarpieFeather());
		myCards.add(new MoltenZombie());
		myCards.add(new OjamaGreen());
		myCards.add(new OjamaYellow());
		myCards.add(new Ojamagic());
		myCards.add(new Ojamuscle());
		myCards.add(new PotAvarice());
		myCards.add(new PotDichotomy());
		myCards.add(new PotDuality());
		myCards.add(new Pumprincess());		
		myCards.add(new RedEyesZombie());
		myCards.add(new RedMedicine());
		myCards.add(new ShardGreed());
		myCards.add(new StormingMirrorForce());
		myCards.add(new SuperheavyBlueBrawler());
		myCards.add(new SuperheavyDaihachi());
		myCards.add(new SuperheavyFlutist());	    
		myCards.add(new SuperheavyGeneral());
		myCards.add(new SuperheavyMagnet());	    
		myCards.add(new SuperheavyOgre());
		myCards.add(new SwordsBurning());
		myCards.add(new SwordsConcealing());
		// END REDUCED Set

		// LIMITED Set - 16 cards
		myCards.add(new AlphaMagnet());
		myCards.add(new AncientRules());
		myCards.add(new BadReaction());
		myCards.add(new BetaMagnet());
		myCards.add(new DarkHole());
		myCards.add(new DarkMagicianGirl());
		myCards.add(new ExodiaHead());
		myCards.add(new ExodiaLA());
		myCards.add(new ExodiaLL());
		myCards.add(new ExodiaRA());
		myCards.add(new ExodiaRL());
		myCards.add(new FeatherPho());
		myCards.add(new GammaMagnet());		
		myCards.add(new Mausoleum());
		myCards.add(new MillenniumShield());
		myCards.add(new ValkMagnet());
		// END LIMITED Set

		if (Loader.isModLoaded("ReplayTheSpireMod"))
		{
			// REPLAY Set - 11 cards
			myCards.add(new BarrelDragon());
			myCards.add(new BlastJuggler());
			myCards.add(new DarkMirrorForce());
			myCards.add(new FlameSwordsman()); 
			myCards.add(new MagicCylinder());
			myCards.add(new NutrientZ());
			myCards.add(new OjamaBlack());
			myCards.add(new OjamaKing());
			myCards.add(new OjamaKnight());
			myCards.add(new Parasite());
			myCards.add(new ToonDarkMagicianGirl());
			// END REPLAY Set
		}

		if (Loader.isModLoaded("conspire"))
		{
			//CONSPIRE Set - 3 cards
			myCards.add(new GateGuardian());
			myCards.add(new LegendaryFisherman());
			myCards.add(new SangaWater());
			// END CONSPIRE Set
		}


		//RANDOM ONLY Set - 0 cards

		// END RANDOM ONLY Set

		// DEBUG CARD STUFF
		if (fullDebug)
		{
			myCards.add(new Token());
			myCards.add(new BadToken());
			//myCards.add(new GreatMoth());
			//myCards.add(new HeartUnderspell());
			//myCards.add(new HeartUndertrap());
			//myCards.add(new HeartUndertribute());
			printCardSetsForGithubReadme(myCards);
			printTextForTranslation();
		}
		// END DEBUG CARD STUFF
		
		/*
		if (challengeMode)
		{
			for (DuelistCard c : myCards)
			{
				int roll = ThreadLocalRandom.current().nextInt(1, 11); 
				c.downgradeCardForChallenge(roll);
				c.initializeDescription();
			}
		}
		*/
	}
	
	public void setupOrbCards()
	{
		if (isReplay)
		{
			orbCards.add(new CrystalOrbCard());
			orbCards.add(new GlassOrbCard());
			orbCards.add(new HellfireOrbCard());
			orbCards.add(new LightOrbCard());
		}
		orbCards.add(new AirOrbCard());
		orbCards.add(new BufferOrbCard());
		orbCards.add(new DarkOrbCard());
		orbCards.add(new DragonOrbCard());
		orbCards.add(new EarthOrbCard());
		orbCards.add(new FireOrbCard());
		orbCards.add(new FrostOrbCard());
		orbCards.add(new GateOrbCard());
		orbCards.add(new GlitchOrbCard());
		orbCards.add(new LightningOrbCard());
		orbCards.add(new MonsterOrbCard());
		orbCards.add(new PlasmaOrbCard());
		orbCards.add(new ReducerOrbCard());
		orbCards.add(new ShadowOrbCard());
		orbCards.add(new SplashOrbCard());
		orbCards.add(new SummonerOrbCard());
		if (isConspire) { orbCards.add(new WaterOrbCard()); }
		for (DuelistCard o : orbCards) { orbCardMap.put(o.name, o); }
	}
	
	public void fillColoredCards()
	{
		coloredCards = new ArrayList<AbstractCard>();
		for (DuelistCard c : myCards)
		{
			if (c.color.equals(AbstractCardEnum.DUELIST_SPELLS) || c.color.equals(AbstractCardEnum.DUELIST_TRAPS))
			{
				coloredCards.add(c);
				logger.info("theDuelist:DefaultMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
			}
		}
	}
	// END METHODS

	
}// END DefaultMod
