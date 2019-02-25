package defaultmod;

import java.util.*;
import java.util.Map.Entry;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.screens.charSelect.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.interfaces.*;
import defaultmod.cards.*;
import defaultmod.characters.TheDuelist;
import defaultmod.patches.*;
import defaultmod.potions.*;
import defaultmod.relics.*;



@SpireInitializer @SuppressWarnings("unused")
public class DefaultMod
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber {
	public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
	public static final String PLACEHOLDER = "Placeholder";
	public static final String MOD_ID_PREFIX = "theDuelist:";

	//This is for the in-game mod settings panel.
	private static final String MODNAME = "Duelist Mod";
	private static final String AUTHOR = "Nyoxide";
	private static final String DESCRIPTION = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	public static Properties duelistDefaults = new Properties();
	public static final String PROP_TOON_BTN = "toonBtnBool";
	public static final String PROP_EXODIA_BTN = "exodiaBtnBool";
	public static final String PROP_CROSSOVER_BTN = "crossoverBtnBool";
	public static final String PROP_OTHERC_BTN = "otherBtnBoolC";
	public static final String PROP_OTHERD_BTN = "otherBtnBoolD";
	public static final String PROP_SET = "setIndex";
	public static final String PROP_CARDS = "cardCount";
	private static boolean toonBtnBool = false;
	private static boolean exodiaBtnBool = false;
	private static boolean crossoverBtnBool = true;
	private static boolean otherBtnBoolC = false;
	private static boolean otherBtnBoolD = false;
	
	public static int lastMaxSummons = 5;
	private static int setIndex = 0;
	private static final int SETS = 5;
	private static int cardCount = 75;
	public static HashMap<String, DuelistCard> summonMap = new HashMap<String, DuelistCard>();
	private static ArrayList<String> cardSets = new ArrayList<String>();
	


	// Arraylist full of my cards, basically a copy of CardLibrary for this set only
	// Potentially speed up all the random generation
	// But more importantly, make sure random effects can pull from cards that are not actually in the compendium
	public static ArrayList<DuelistCard> myCards = new ArrayList<DuelistCard>();

	// Tags (should move to using the patches file tags instead)
	@SpireEnum public static AbstractCard.CardTags MONSTER;  // 86 cards
	@SpireEnum public static AbstractCard.CardTags SPELL; // 43 cards
	@SpireEnum public static AbstractCard.CardTags TRAP; // 8 cards
	@SpireEnum public static AbstractCard.CardTags FIELDSPELL;
	@SpireEnum public static AbstractCard.CardTags EQUIPSPELL;
	@SpireEnum public static AbstractCard.CardTags POT;
	@SpireEnum public static AbstractCard.CardTags TOON;// 15 cards
	@SpireEnum public static AbstractCard.CardTags GUARDIAN;
	@SpireEnum public static AbstractCard.CardTags EXODIA;
	@SpireEnum public static AbstractCard.CardTags MAGNETWARRIOR;
	@SpireEnum public static AbstractCard.CardTags SUPERHEAVY;
	@SpireEnum public static AbstractCard.CardTags DRAGON; // 23 cards
	@SpireEnum public static AbstractCard.CardTags SPELLCASTER; // 23 cards
	@SpireEnum public static AbstractCard.CardTags OJAMA;
	@SpireEnum public static AbstractCard.CardTags GOD;
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
	@SpireEnum public static AbstractCard.CardTags MAGIC_RULER; // 3 cards
	@SpireEnum public static AbstractCard.CardTags LEGEND_BLUE_EYES; // 24 cards
	@SpireEnum public static AbstractCard.CardTags PHARAOH_SERVANT; //7 cards
	@SpireEnum public static AbstractCard.CardTags METAL_RAIDERS; // 17 cards
	@SpireEnum public static AbstractCard.CardTags LABYRINTH_NIGHTMARE; // 4 cards
	@SpireEnum public static AbstractCard.CardTags LEGACY_DARKNESS; // 4 cards
	@SpireEnum public static AbstractCard.CardTags MAGICIANS_FORCE; // 1 card
	@SpireEnum public static AbstractCard.CardTags INVASION_CHAOS; // 3 cards
	@SpireEnum public static AbstractCard.CardTags DARK_CRISIS; // 2 cards


	// =============== INPUT TEXTURE LOCATION =================

	// Colors (RGB)
	// Character Color
	public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

	// Potion Colors in RGB
	public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
	public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
	public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

	// Image folder name - This is where your image folder is.
	// This is good practice in case you ever need to move/rename it without screwing up every single path.
	// In this case, it's resources/defaultModResources/images (and then, say, /cards/Strike.png).

	private static final String DEFAULT_MOD_ASSETS_FOLDER = "defaultModResources/images";

	// Card backgrounds
	private static final String ATTACK_DEFAULT_GRAY = "512/bg_attack_default_gray.png";
	private static final String POWER_DEFAULT_GRAY = "512/bg_power_default_gray.png";
	private static final String SKILL_DEFAULT_GRAY = "512/bg_skill_default_gray.png";
	private static final String ENERGY_ORB_DEFAULT_GRAY = "512/card_default_gray_orb.png";
	private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

	private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "1024/bg_attack_default_gray.png";
	private static final String POWER_DEFAULT_GRAY_PORTRAIT = "1024/bg_power_default_gray.png";
	private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "1024/bg_skill_default_gray.png";
	private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "1024/card_default_gray_orb.png";

	// Card images
	/*
    public static final String DEFAULT_COMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_COMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_COMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_UNCOMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_UNCOMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_UNCOMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_RARE_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_RARE_SKILL = "cards/Skill.png";
    public static final String DEFAULT_RARE_POWER = "cards/Power.png";
	 */

	public static final String ALPHA_MAGNET = "cards/Alpha_Magnet.png";
	public static final String ANCIENT_RULES = "cards/Ancient_Rules.png";
	public static final String AXE_DESPAIR = "cards/Axe_Despair.png";
	public static final String BAD_REACTION = "cards/Bad_Reaction.png";
	public static final String BARREL_DRAGON = "cards/Barrel_Dragon.png";
	public static final String BETA_MAGNET = "cards/Beta_Magnet.png";
	//public static final String BLUE_EYES = "cards/Blue_Eyes.png";
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
	//public static final String SUMMONED_SKULL = "cards/Summoned_Skull.png";
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

	// Second Set
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

	// Third Set
	public static final String CURSE_DRAGON = "cards/Curse_of_Dragon.png";
	public static final String CYBER_DRAGON = "cards/Cyber_Dragon.png";
	public static final String DRAGON_MASTER = "cards/Dragon_Master_Knight.png";
	public static final String FIEND_SKULL = "cards/Fiend_Skull_Dragon.png";
	public static final String FIVE_HEADED = "cards/Five_Headed_Dragon.png";
	public static final String FLUTE_SUMMONING = "cards/Flute_Summoning_Dragon.png";
	public static final String GANDORA = "cards/Gandora.png";
	public static final String INFINITY_DRAGON = "cards/Infinity_Dragon.png";
	public static final String LORD_D = "cards/Lord_D.png";

	// Fourth Set
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

	public static final String BOOK_SECRET = "cards/Book_Secret_Arts.png";
	public static final String FINAL_FLAME = "cards/Final_Flame.png";
	public static final String GOBLIN_SECRET = "cards/Goblin_Secret_Remedy.png";
	public static final String MACHINE_FACTORY = "cards/Machine_Conversion_Factory.png";
	public static final String POLYMERIZATION = "cards/Polymerization.png";
	public static final String SPARKS = "cards/Sparks.png";    
	public static final String CHEERFUL_COFFIN = "cards/Cheerful_Coffin.png";
	public static final String PETIT_MOTH = "cards/Petit_Moth.png";
	public static final String COCOON_EVOLUTION = "cards/Cocoon_Evolution.png";
	public static final String GREAT_MOTH = "cards/Great_Moth.png";
	public static final String HEAVY_STORM = "cards/Heavy_Storm.png";
	public static final String LAVA_BATTLEGUARD = "cards/Lava_Battleguard.png";
	public static final String SWAMP_BATTLEGUARD = "cards/Swamp_Battleguard.png";
	public static final String SWORD_DEEP_SEATED = "cards/Sword_Deep_Seated.png";    
	public static final String TWIN_HEADED_FIRE = "cards/Twin_Headed_Fire_Dragon.png";
	public static final String TWIN_HEADED_THUNDER = "cards/Twin_Headed_Thunder_Dragon.png";
	public static final String REVIVAL_JAM = "cards/Revival_Jam.png";
	public static final String RYU_RAN = "cards/Ryu_Ran.png";
	public static final String HAYABUSA_KNIGHT = "cards/Hayabusa_Knight.png";
	public static final String MANGA_RYU_RAN = "cards/Manga_Ryu_Ran.png";
	public static final String TOON_ANCIENT_GEAR = "cards/Toon_Ancient_Gear.png";
	public static final String TOON_CYBER_DRAGON = "cards/Toon_Cyber_Dragon.png";
	public static final String TOON_GOBLIN_ATTACK = "cards/Toon_Goblin_Attack_Force.png";
	public static final String WHITE_MAGICAL_HAT = "cards/White_Magical_Hat.png";
	public static final String ILLUSIONIST = "cards/Illusionist_Faceless_Mage.png";
	public static final String TRIBUTE_DOOMED = "cards/Tribute_Doomed.png";
	public static final String DRAGON_PIPER = "cards/Dragon_Piper.png";
	public static final String HARPIES_BROTHER = "cards/Harpies_Brother.png";
	public static final String HARPIE_LADY = "cards/Harpie_Lady.png";
	public static final String HARPIE_LADY_PHO = "cards/Harpie_Lady_Phoneix.png";
	public static final String HARPIE_LADY_SISTERS = "cards/Harpie_Lady_Sisters.png";
	public static final String ELEGANT_EGOTIST = "cards/Elegant_Egotist.png";
	public static final String BLACKLAND_FIRE_DRAGON = "cards/Blackland_Fire_Dragon.png";
	public static final String B_SKULL_DRAGON = "cards/B_Skull_Dragon.png";
	public static final String DARKFIRE_DRAGON = "cards/Darkfire_Dragon.png";
	public static final String LEVIA_DRAGON = "cards/Levia_Dragon_Daedalus.png";
	public static final String LUSTER_DRAGON = "cards/Luster_Dragon.png";
	public static final String LUSTER_DRAGON2 = "cards/Luster_Dragon2.png";
	public static final String METAL_DRAGON = "cards/Metal_Dragon.png";

	public static final String OCEAN_LORD = "cards/Ocean_Lord.png";
	public static final String TRIHORNED_DRAGON = "cards/TriHorned_Dragon.png";
	public static final String TWIN_BARREL_DRAGON = "cards/Twin_Barrel_Dragon.png";
	public static final String TWIN_HEADED = "cards/Twin_Headed_Thunder_Dragon.png";
	public static final String TYRANT_DRAGON = "cards/Tyrant_Dragon.png";
	public static final String WHITE_HORNED = "cards/White_Horned_Dragon.png";
	public static final String WHITE_NIGHT = "cards/White_Night_Dragon.png";
	public static final String YAMATA_DRAGON = "cards/Yamata_Dragon.png";

	// Expansion Set

	public static final String ATTACK_RECEIVE = "cards/Attack_Receive.png";
	public static final String BASIC_INSECT = "cards/Basic_Insect.png";
	public static final String BEAST_FANGS = "cards/Beast_Fangs.png";
	public static final String BEAVER_WARRIOR = "cards/Beaver_Warrior.png";
	public static final String CHARUBIN = "cards/Charubin_Fire_Knight.png";
	public static final String COMIC_HAND = "cards/Comic_Hand.png";
	public static final String DARK_ENERGY = "cards/Dark_Energy.png";
	public static final String DARK_GRAY = "cards/Dark_Gray.png";
	public static final String EMPRESS_MANTIS = "cards/Empress_Mantis.png";
	public static final String EXODIA_NECROSS = "cards/Exodia_Necross.png";
	public static final String EXXOD_MASTER = "cards/Exxod_Master_Guard.png";
	public static final String FERAL_IMP = "cards/Feral_Imp.png";
	public static final String FIENDISH_CHAIN = "cards/Fiendish_Chain.png";
	public static final String FIREGRASS = "cards/Firegrass.png";
	public static final String FLAME_MANIPULATOR = "cards/Flame_Manipulator.png";
	public static final String GARNECIA_ELEFANTIS = "cards/Garnecia_Elefantis.png";
	public static final String GEARFRIED = "cards/Gearfried.png";
	public static final String GIGAPLANT = "cards/Gigaplant.png";
	public static final String GRAND_HORN_HEAVEN = "cards/Grand_Horn_Heaven.png";
	public static final String GRASSCHOPPER = "cards/Grasschopper.png";
	public static final String GRAVEROBBER = "cards/Graverobber.png";
	public static final String GREEDPOT_AVATAR = "cards/Greedpot_Avatar.png";
	public static final String GUARDIAN_THRONE = "cards/Guardian_Throne_Room.png";
	public static final String HITOTSU_GIANT = "cards/Hitotsu_Me_Giant.png";
	public static final String JINZO = "cards/Jinzo.png";
	public static final String KAISER_SEA_HORSE = "cards/Kaiser_Sea_Horse.png";
	public static final String KING_YAMIMAKAI = "cards/King_Yamimakai.png";
	public static final String LAJINN = "cards/LaJinn.png";

	public static final String LEGENDARY_SWORD = "cards/Legendary_Sword.png";
	public static final String LEGEND_EXODIA = "cards/Legend_Exodia.png";
	public static final String MAMMOTH_GRAVEYARD = "cards/Mammoth_Graveyard.png";
	public static final String MAN_EATER = "cards/Man_Eater_Bug.png";
	public static final String MONSTER_EGG = "cards/Monster_Egg.png";
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
	public static final String OBLITERATE = "cards/Obliterate.png";
	public static final String POWER_KAISHIN = "cards/Power_Kaishin.png";
	public static final String PREDAPLANT_CHIMERAFFLESIA = "cards/Predaplant_Chimerafflesia.png";
	public static final String PREDAPLANT_CHLAMYDOSUNDEW = "cards/Predaplant_Chlamydosundew.png";
	public static final String PREDAPLANT_DROSOPHYLLUM = "cards/Predaplant_Drosophyllum.png";
	public static final String PREDAPLANT_FLYTRAP = "cards/Predaplant_Flytrap.png";
	public static final String PREDAPLANT_PTERAPENTHES = "cards/Predaplant_Pterapenthes.png";
	public static final String PREDAPLANT_SARRACENIANT = "cards/Predaplant_Sarraceniant.png";
	public static final String PREDAPLANT_SPINODIONAEA = "cards/Predaplant_Spinodionaea.png";
	public static final String PREDAPONICS = "cards/Predaponics.png";
	public static final String PREDAPRUNING = "cards/Predapruning.png";
	public static final String RELINKURIBOH = "cards/Relinkuriboh.png";
	public static final String SANGAN = "cards/Sangan.png";
	public static final String SAUROPOD_BRACHION = "cards/Sauropod Brachion.png";
	public static final String SILVER_FANG = "cards/Silver_Fang.png";
	public static final String SKULL_SERVANT = "cards/Skull_Servant.png";
	public static final String SPHERE_KURIBOH = "cards/Sphere_Kuriboh.png";
	public static final String STEAM_TRAIN_KING = "cards/Steam_Train_King.png";
	public static final String STIM_PACK = "cards/Stim_Pack.png";
	public static final String STRAY_LAMBS = "cards/Stray_Lambs.png";

	public static final String SUPERCONDUCTOR_TYRANNO = "cards/Super_Conductor_Tyranno.png";
	public static final String SUPER_SOLAR_NUTRIENT = "cards/Super_Solar_Nutrient.png";
	public static final String SWORD_HUNTER = "cards/Sword_Hunter.png";
	public static final String THE_CREATOR = "cards/The_Creator.png";
	public static final String THIRTEEN_GRAVE = "cards/13th_Grave.png";
	public static final String THOUSAND_DRAGON = "cards/Thousand_Dragon.png";
	public static final String THOUSAND_EYES_IDOL = "cards/Thousand_Eyes_Idol.png";
	public static final String THOUSAND_EYES_RESTRICT = "cards/Thousand_Eyes_Restrict.png";
	public static final String TRIAL_NIGHTMARE = "cards/Trial_Nightmare.png";
	public static final String VIOLET_CRYSTAL = "cards/Violet_Crystal.png";
	public static final String VORSE_RAIDER = "cards/Vorse_Raider.png";
	public static final String WARRIOR_GREPHER = "cards/Warrior_Dai_Grepher.png";
	public static final String WINGWEAVER = "cards/Wingweaver.png";
	public static final String WIRETAP = "cards/Wiretap.png";
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

	// Character assets
	//private static final String THE_DEFAULT_BUTTON = "charSelect/DefaultCharacterButton.png";
	private static final String THE_DEFAULT_BUTTON = "charSelect/DuelistCharacterButton.png";
	//private static final String THE_DEFAULT_PORTRAIT = "charSelect/DefaultCharacterPortraitBG.png";
	private static final String THE_DEFAULT_PORTRAIT = "charSelect/DuelistCharacterPortraitBG_HD.png";
	public static final String THE_DEFAULT_SHOULDER_1 = "char/defaultCharacter/shoulder.png";
	public static final String THE_DEFAULT_SHOULDER_2 = "char/defaultCharacter/shoulder2.png";
	public static final String THE_DEFAULT_CORPSE = "char/defaultCharacter/corpse.png";

	//Mod Badge
	public static final String BADGE_IMAGE = "Badge.png";

	// Animations atlas and JSON files
	//public static final String THE_DEFAULT_SKELETON_ATLAS = "char/defaultCharacter/skeleton.atlas";
	//public static final String THE_DEFAULT_SKELETON_JSON = "char/defaultCharacter/skeleton.json";



	// =============== /INPUT TEXTURE LOCATION/ =================

	// =============== IMAGE PATHS =================

	// This is the command that will link up your core assets folder (line 89) ("defaultModResources/images")
	// together with the card image (everything above) ("cards/Attack.png") and it puts a "/" between them.
	// When adding a card image, you can, in fact, just do "defaultModResources/images/cards/Attack.png" in the actual card file.
	// This however, is good practice in case you want to change your "/images" folder at any point in time.

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
		logger.info("Subscribe to BaseMod hooks");

		BaseMod.subscribe(this);

		logger.info("Done subscribing");

		logger.info("Creating the color " + AbstractCardEnum.DEFAULT_GRAY.toString());

		BaseMod.addColor(AbstractCardEnum.DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
				DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(ATTACK_DEFAULT_GRAY),
				makePath(SKILL_DEFAULT_GRAY), makePath(POWER_DEFAULT_GRAY),
				makePath(ENERGY_ORB_DEFAULT_GRAY), makePath(ATTACK_DEFAULT_GRAY_PORTRAIT),
				makePath(SKILL_DEFAULT_GRAY_PORTRAIT), makePath(POWER_DEFAULT_GRAY_PORTRAIT),
				makePath(ENERGY_ORB_DEFAULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

		duelistDefaults.setProperty(PROP_TOON_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_EXODIA_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_CROSSOVER_BTN, "TRUE");
		duelistDefaults.setProperty(PROP_OTHERC_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_OTHERD_BTN, "FALSE");
		duelistDefaults.setProperty(PROP_SET, "1");
		duelistDefaults.setProperty(PROP_CARDS, "151");
		
		cardSets.add("All (187 cards)");
		cardSets.add("Full (138 cards)");
		cardSets.add("Reduced (119 cards)");
		cardSets.add("Limited (91 cards)");
		cardSets.add("Core (62 cards)");
		
		try 
		{
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
            config.load();
            toonBtnBool = config.getBool(PROP_TOON_BTN);
            exodiaBtnBool = config.getBool(PROP_EXODIA_BTN);
            crossoverBtnBool = config.getBool(PROP_CROSSOVER_BTN);
            otherBtnBoolC = config.getBool(PROP_OTHERC_BTN);
            otherBtnBoolD = config.getBool(PROP_OTHERD_BTN);
            setIndex = config.getInt(PROP_SET);
            cardCount = config.getInt(PROP_CARDS);
            
        } catch (Exception e) { e.printStackTrace(); }

		logger.info("Done creating the color");
	}

	public static void initialize() {
		logger.info("========================= Initializing Duelist Mod  =========================");
		DefaultMod defaultmod = new DefaultMod();
		logger.info("========================= /Duelist Mod Initialized/ =========================");
	}

	// ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


	// =============== LOAD THE CHARACTER =================

	@Override
	public void receiveEditCharacters() {
		logger.info("Beginning to edit characters. " + "Add " + TheDuelistEnum.THE_DUELIST.toString());

		BaseMod.addCharacter(new TheDuelist("the Duelist", TheDuelistEnum.THE_DUELIST),
				makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_DUELIST);

		receiveEditPotions();
		logger.info("Done editing characters");
	}

	// =============== /LOAD THE CHARACTER/ =================


	// =============== POST-INITIALIZE =================


	@Override
	public void receivePostInitialize() {

		logger.info("Loading badge image and mod options");
		Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
		ModPanel settingsPanel = new ModPanel();
		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

		// Check Box A
		ModLabeledToggleButton toonBtn = new ModLabeledToggleButton("Remove ALL Toons (REQUIRES RESTART)",350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, toonBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			toonBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_TOON_BTN, toonBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetCharSelect();
		});
		settingsPanel.addUIElement(toonBtn);
		// END Check Box A
		
		// Check Box B
		ModLabeledToggleButton exodiaBtn = new ModLabeledToggleButton("Remove Exodia cards (REQUIRES RESTART)",350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, exodiaBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			exodiaBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_EXODIA_BTN, exodiaBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetCharSelect();
		});
		settingsPanel.addUIElement(exodiaBtn);
		// END Check Box B
		
		// Check Box C
		ModLabeledToggleButton crossoverBtn = new ModLabeledToggleButton("Allow 13 extra crossover mod cards (REQUIRES RESTART)",350.0f, 600.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, crossoverBtnBool, settingsPanel, (label) -> {}, (button) -> 
		{
			crossoverBtnBool = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_CROSSOVER_BTN, crossoverBtnBool);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetCharSelect();
		});
		settingsPanel.addUIElement(crossoverBtn);
		// END Check Box C
		
		// Check Box D
		ModLabeledToggleButton fourthBtn = new ModLabeledToggleButton("Placeholder",350.0f, 550.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, otherBtnBoolC, settingsPanel, (label) -> {}, (button) -> 
		{
			otherBtnBoolC = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_OTHERC_BTN, otherBtnBoolC);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetCharSelect();
		});
		settingsPanel.addUIElement(fourthBtn);
		// END Check Box D
		
		// Check Box D
		ModLabeledToggleButton fifthBtn = new ModLabeledToggleButton("Placeholder",350.0f, 500.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, otherBtnBoolD, settingsPanel, (label) -> {}, (button) -> 
		{
			otherBtnBoolD = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_OTHERD_BTN, otherBtnBoolD);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			resetCharSelect();
		});
		settingsPanel.addUIElement(fifthBtn);
		// END Check Box D
		
		// Set Size Selector
		ModLabel setSelectLabelTxt = new ModLabel("Card Set size:",350.0f, 415.0f,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectLabelTxt);
		ModLabel setSelectColorTxt = new ModLabel(cardSets.get(setIndex),670.0f, 415.0f,settingsPanel,(me)->{});
		settingsPanel.addUIElement(setSelectColorTxt);

		ModButton setSelectLeftBtn = new ModButton(605.0f, 400.0f, ImageMaster.loadImage("img/tinyLeftArrow.png"),settingsPanel,(me)->{
			setIndex = setIndex-1>=0?setIndex-1:SETS-1;
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
		ModButton setSelectRightBtn = new ModButton(950.0f, 400.0f, ImageMaster.loadImage("img/tinyRightArrow.png"),settingsPanel,(me)->{
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
		ModLabel extraLabelTxt = new ModLabel("Changing the card set requires a restart to take effect.",350.0f, 350.0f,settingsPanel,(me)->{});
		settingsPanel.addUIElement(extraLabelTxt);
		// END Set Size Selector
		
		// Card Count Label
		ModLabel cardLabelTxt = new ModLabel("Active Duelist Cards: " + cardCount,350.0f, 300.0f,settingsPanel,(me)->{});
		settingsPanel.addUIElement(cardLabelTxt);
		// END Card Count Label

		logger.info("Done loading badge Image and mod options");

	}

	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		logger.info("Beginning to edit potions");

		// Class Specific Potion. If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDuelistEnum.THE_DUELIST")
		BaseMod.addPotion(MillenniumElixir.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, MillenniumElixir.POTION_ID, TheDuelistEnum.THE_DUELIST);
		//BaseMod.addPotion(JoeyJuice.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, JoeyJuice.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPack.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, SealedPack.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPackB.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, SealedPackB.POTION_ID, TheDuelistEnum.THE_DUELIST);



		logger.info("Done editing potions");
	}

	// ================ /ADD POTIONS/ ===================


	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		logger.info("Adding relics");

		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		//BaseMod.addRelicToCustomPool(new PlaceholderRelic(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new MillenniumPuzzle(), AbstractCardEnum.DEFAULT_GRAY);
		if (!toonBtnBool) { BaseMod.addRelicToCustomPool(new MillenniumEye(), AbstractCardEnum.DEFAULT_GRAY); }
		BaseMod.addRelicToCustomPool(new MillenniumRing(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new MillenniumKey(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new MillenniumRod(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new MillenniumCoin(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new StoneExxod(), AbstractCardEnum.DEFAULT_GRAY);
		BaseMod.addRelicToCustomPool(new GiftAnubis(), AbstractCardEnum.DEFAULT_GRAY);

		// This adds a relic to the Shared pool. Every character can find this relic.
		//BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

		logger.info("Done adding relics!");
	}

	// ================ /ADD RELICS/ ===================



	// ================ ADD CARDS ===================

	@Override
	public void receiveEditCards() {
		//logger.info("Adding variables");
		// Add the Custom Dynamic Variables
		//BaseMod.addDynamicVariable(new WingedDragonVariable());

		
		
		logger.info("Adding cards");
		// Add the cards
		
		// CORE Set - 74 cards
			// Starting Deck - 6 cards
		myCards.add(new CastleWalls());
		myCards.add(new GiantSoldier());
		myCards.add(new Ookazi());
		myCards.add(new ScrapFactory());
		myCards.add(new SevenColoredFish());
		myCards.add(new SummonedSkull());
			// Other core cards - 69 cards
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
		myCards.add(new Hinotoma());
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
		myCards.add(new MagicCylinder());
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
		//myCards.add(new BadToken()); 		//debug card
		// END CORE SET
		
		// ALL Set - 48 cards ( need 47 more cards here)
		myCards.add(new BigCastleWalls());
		// END ALL Set
		
		// FULL Set - 22 cards ( need to complete the 3 unfinished cards here )
		//myCards.add(new SwordsRevealing());
		//myCards.add(new TimeWizard()); 
		//myCards.add(new TrapHole());
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
		
		//CONSPIRE Set - 3 cards
		myCards.add(new GateGuardian());
		myCards.add(new LegendaryFisherman());
		myCards.add(new SangaWater());
		// END CONSPIRE Set
		
		// REPLAY Set - 10 cards
		myCards.add(new BarrelDragon());
		myCards.add(new BlastJuggler());
		myCards.add(new DarkMirrorForce());
		myCards.add(new FlameSwordsman()); 
		myCards.add(new NutrientZ());
		myCards.add(new OjamaBlack());
		myCards.add(new OjamaKing());
		myCards.add(new OjamaKnight());
		myCards.add(new Parasite());
		myCards.add(new ToonDarkMagicianGirl());
		// END REPLAY Set
		

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

		if (!otherBtnBoolC)
		{
			
		}
		
		if (!otherBtnBoolD)
		{
			
		}
		
		if (toonBtnBool)
		{
			for (int i = 0; i < myCards.size(); i++)
			{
				if (myCards.get(i).hasTag(DefaultMod.TOON))
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
					if (myCards.get(i).hasTag(DefaultMod.ALL))
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
					if (myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL))
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
					if (myCards.get(i).hasTag(DefaultMod.REDUCED) || myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL))
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
					if (myCards.get(i).hasTag(DefaultMod.LIMITED) || myCards.get(i).hasTag(DefaultMod.REDUCED) || myCards.get(i).hasTag(DefaultMod.FULL) || myCards.get(i).hasTag(DefaultMod.ALL))
					{
						myCards.remove(i);
						i = 0;
					}
				}
				break;
			default:
				break;
		}

		// Unlock all cards
		logger.info("Making sure the cards are unlocked.");
		int tempCardCount = 0;
		for (DuelistCard c : myCards) { BaseMod.addCard(c); UnlockTracker.unlockCard(c.getID()); summonMap.put(c.originalName, c); tempCardCount++; }
		summonMap.put("Puzzle Token", new Token());
		summonMap.put("Ancient Token", new Token());
		summonMap.put("Anubis Token", new Token());
		cardCount = tempCardCount;
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
			config.setInt(PROP_CARDS, cardCount);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Done adding cards!");

	}

	// ================ /ADD CARDS/ ===================



	// ================ LOAD THE TEXT ===================

	@Override
	public void receiveEditStrings() {
		logger.info("Beginning to edit strings");

		// CardStrings
		BaseMod.loadCustomStringsFile(CardStrings.class,
				"defaultModResources/localization/DuelistMod-Card-Strings.json");

		// PowerStrings
		BaseMod.loadCustomStringsFile(PowerStrings.class,
				"defaultModResources/localization/DuelistMod-Power-Strings.json");

		// RelicStrings
		BaseMod.loadCustomStringsFile(RelicStrings.class,
				"defaultModResources/localization/DuelistMod-Relic-Strings.json");

		// PotionStrings
		BaseMod.loadCustomStringsFile(PotionStrings.class,
				"defaultModResources/localization/DuelistMod-Potion-Strings.json");

		// OrbStrings
		BaseMod.loadCustomStringsFile(OrbStrings.class,
				"defaultModResources/localization/DuelistMod-Orb-Strings.json");

		logger.info("Done edittting strings");
	}

	// ================ /LOAD THE TEXT/ ===================

	// ================ LOAD THE KEYWORDS ===================

	@Override
	public void receiveEditKeywords() {
		final String[] placeholder = { "keyword", "keywords", "Keyword", "Keywords" };
		BaseMod.addKeyword(placeholder, "Whenever you play a card, gain 1 dexterity this turn only.");
		BaseMod.addKeyword(new String[] {"summon", "Summon", "Summons", "summons"}, "Counts monsters currently summoned. Maximum of #b5 #ySummons.");
		BaseMod.addKeyword(new String[] {"resummon", "Resummon", "Resummons", "resummons"}, "Replays the card, ignoring Tribute costs. Some monsters trigger extra special effects when Resummoned.");
		BaseMod.addKeyword(new String[] {"tribute", "Tribute", "Tributes", "tributes", "sacrifice"}, "Removes X #ySummons. Unless you have enough #ySummons to #yTribute, you cannot play a #yTribute monster.");
		BaseMod.addKeyword(new String[] {"increment", "Increment" }, "Increase your maximum #ySummons by the number given.");
		BaseMod.addKeyword(new String[] {"exodia", "Exodia"}, "A powerful monster found within your Grandpa's deck.");
		BaseMod.addKeyword(new String[] {"gate", "Gate"}, "#yOrb: Deal damage to ALL enemies, gain #yEnergy and #yBlock. NL #yGate is unaffected by #yFocus.");
		BaseMod.addKeyword(new String[] {"buffer", "Buffer"}, "#yOrb: Increase your power stacks at the start of turn. #yEvoke gives random #ydebuffs.");
		BaseMod.addKeyword(new String[] {"summoner", "Summoner"}, "#yOrb: #ySummon at the end of turn. #yEvoke increases your max #ySummons.");
		BaseMod.addKeyword(new String[] {"reducer", "Reducer"}, "#yOrb: At the start of turn, increase the cards reduced when this is evoked. #yEvoke sets the cost of random card(s) in your hand to 0.");
		BaseMod.addKeyword(new String[] {"monsterOrb", "Monsterorb", "MonsterOrb"}, "#yOrb: At the start of turn, adds random monster cards to your hand. #yEvoke also adds monsters to your hand.");
		BaseMod.addKeyword(new String[] {"dragonorb", "Dragonorb", "DragonOrb"}, "#yOrb: At the start of turn, adds random #yDragon cards to your hand. #yEvoke sets the cost of random #yDragons in your hand to 0.");
		BaseMod.addKeyword(new String[] {"overflow", "Overflow"}, "When a card with #yOverflow is in your hand at the end of the turn, activate an effect. This effect has a limited amount of uses.");
		BaseMod.addKeyword(new String[] {"toon", "Toon"}, "Can only be played if #yToon #yWorld is active. If you Tribute Summon a Toon monster using another Toon as the tribute, deal #b5 damage to all enemies.");
		BaseMod.addKeyword(new String[] {"magnet", "Magnet", "Magnets", "magnets"}, "Tokens associated with the #yMagnet #yWarrior monsters. #yMagnets have no inherent effect.");
		BaseMod.addKeyword(new String[] {"ojamania", "Ojamania" }, "Add #b2 random cards to your hand, they cost #b0 this turn. Apply #b1 random #ybuff. Apply #b2 random #ydebuffs to an enemy.");
		BaseMod.addKeyword(new String[] {"dragon", "Dragon"}, "Powerful monster cards. When you Tribute a Dragon for another Dragon, Gain 1 Strength.");
		BaseMod.addKeyword(new String[] {"spellcaster", "Spellcaster"}, "Powerful monster cards. When you Tribute a Mystical monster for a Dragon, lose 2 HP.");
		BaseMod.addKeyword(new String[] {"earth", "Earth"}, "");
	}

	// ================ /LOAD THE KEYWORDS/ ===================    

	// this adds "ModName:" before the ID of any card/relic/power etc.
	// in order to avoid conflicts if any other mod uses the same ID.
	public static String makeID(String idText) {
		return "theDuelist:" + idText;
	}



	public static String getExistingOrPlaceholder(String prefix, String id, String postfix) {
		String idWithoutModName = id.replaceAll(MOD_ID_PREFIX, "");
		String maybeExisting = prefix + idWithoutModName + postfix;
		if (Gdx.files.internal(maybeExisting).exists()) {
			return maybeExisting;
		} else {
			DefaultMod.logger.debug(
					id + " has no image configured. Defaulting to placeholder image (should be in "
							+ prefix + ")");
			return prefix + PLACEHOLDER + postfix;
		}
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

	@SuppressWarnings("unchecked")
	public void resetCharSelect() {
		((ArrayList<CharacterOption>) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "options")).clear();
		CardCrawlGame.mainMenuScreen.charSelectScreen.initialize();
	}

}
