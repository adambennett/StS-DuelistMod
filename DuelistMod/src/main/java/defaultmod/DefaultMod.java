package defaultmod;

import java.util.ArrayList;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.*;
import basemod.interfaces.*;
import defaultmod.cards.*;
import defaultmod.characters.TheDuelist;
import defaultmod.patches.*;
import defaultmod.potions.*;
import defaultmod.relics.*;



@SpireInitializer
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
    
    
    // Arraylist full of my cards, basically a copy of CardLibrary for this set only
    // Potentially speed up all the random generation
    // But more importantly, make sure random effects can pull from cards that are not actually in the compendium
    public static ArrayList<DuelistCard> myCards = new ArrayList<DuelistCard>();
    
    // Tags (should move to using the patches file tags instead)
    @SpireEnum public static AbstractCard.CardTags MONSTER;  // 86 cards
    @SpireEnum public static AbstractCard.CardTags SPELL; // 43 cards
    @SpireEnum public static AbstractCard.CardTags TRAP; // 8 cards
    @SpireEnum public static AbstractCard.CardTags POT;
    @SpireEnum public static AbstractCard.CardTags TOON;// 15 cards
    @SpireEnum public static AbstractCard.CardTags GUARDIAN;
    @SpireEnum public static AbstractCard.CardTags EXODIA;
    @SpireEnum public static AbstractCard.CardTags MAGNETWARRIOR;
    @SpireEnum public static AbstractCard.CardTags SUPERHEAVY;
    @SpireEnum public static AbstractCard.CardTags DRAGON; // 23 cards
    @SpireEnum public static AbstractCard.CardTags OJAMA;
    @SpireEnum public static AbstractCard.CardTags GOD;
    @SpireEnum public static AbstractCard.CardTags TRIBUTE;
    @SpireEnum public static AbstractCard.CardTags NO_PUMPKIN;
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
    
    public static final String BOOK_SECRET = "cards/Book_Secret_Arts.png";
    public static final String FINAL_FLAME = "cards/Final_Flame.png";
    public static final String GOBLIN_SECRET = "cards/Goblin_Secret_Remedy.png";
    public static final String MACHINE_FACTORY = "cards/Machine_Conversion_Factory.png";
    public static final String MOUNTAIN = "cards/Mountain.png";
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
    public static final String ARMORED_ZOMBIE = "cards/Armored_Zombie.png";
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
    public static final String MOLTEN_ZOMBIE = "cards/Molten_Zombie.png";
    public static final String RED_EYES_ZOMBIE = "cards/Red_Eyes_Zombie.png";
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
    
    // Relic images  
    public static final String PLACEHOLDER_RELIC = "relics/placeholder_relic.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE = "relics/outline/placeholder_relic.png";
    
    public static final String M_EYE_RELIC = "relics/MEye.png";
    public static final String M_EYE_RELIC_OUTLINE = "relics/MEye.png";
    
    public static final String M_RING_RELIC = "relics/MEye.png";
    public static final String M_RING_RELIC_OUTLINE = "relics/MEye.png";
    
    public static final String M_ROD_RELIC = "relics/MEye.png";
    public static final String M_ROD_RELIC_OUTLINE = "relics/MEye.png";
    
    public static final String M_COIN_RELIC = "relics/MEye.png";
    public static final String M_COIN_RELIC_OUTLINE = "relics/MEye.png";
    
    public static final String EXXOD_STONE_RELIC = "relics/MEye.png";
    public static final String EXXOD_STONE_RELIC_OUTLINE = "relics/MEye.png";

    public static final String PLACEHOLDER_RELIC_2 = "relics/placeholder_relic2.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE_2 = "relics/outline/placeholder_relic2.png";
    
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

        logger.info("Done creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Duelist Mod. Hi. =========================");
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
        // Load the Mod Badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("DuelistMod doesn't have any settings!", 400.0f, 700.0f,
                settingsPanel, (me) -> {
                }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

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
        BaseMod.addRelicToCustomPool(new MillenniumEye(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new MillenniumRing(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new MillenniumKey(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new MillenniumRod(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new MillenniumCoin(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new StoneExxod(), AbstractCardEnum.DEFAULT_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    
    
    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        //BaseMod.addDynamicVariable(new TributeCustomVariable());
        
        logger.info("Adding cards");
        // Add the cards
        BaseMod.addCard(new AlphaMagnet());
		BaseMod.addCard(new AncientRules());
		BaseMod.addCard(new AxeDespair());
		BaseMod.addCard(new BadReaction());
		BaseMod.addCard(new BarrelDragon());
		BaseMod.addCard(new BetaMagnet());
		BaseMod.addCard(new BlueEyes());
		BaseMod.addCard(new BlueEyesToon());
		BaseMod.addCard(new BlueEyesUltimate());
		BaseMod.addCard(new BusterBlader());
		BaseMod.addCard(new CannonSoldier());
		BaseMod.addCard(new CardDestruction());
		BaseMod.addCard(new CastleDarkIllusions());
		BaseMod.addCard(new CastleWalls());
		BaseMod.addCard(new CatapultTurtle());
		BaseMod.addCard(new CelticGuardian());
		BaseMod.addCard(new ChangeHeart());
		BaseMod.addCard(new DarkFactory());
		BaseMod.addCard(new DarkHole());
		BaseMod.addCard(new DarkMagician());
		BaseMod.addCard(new DarkMagicianGirl());
		BaseMod.addCard(new DarkMirrorForce());
		BaseMod.addCard(new DarklordMarie());
		BaseMod.addCard(new DianKeto());
		BaseMod.addCard(new ExodiaHead());
		BaseMod.addCard(new ExodiaLA());
		BaseMod.addCard(new ExodiaLL());
		BaseMod.addCard(new ExodiaRA());
		BaseMod.addCard(new ExodiaRL());
		BaseMod.addCard(new FeatherPho());
		BaseMod.addCard(new FiendMegacyber());
		BaseMod.addCard(new Fissure());
		BaseMod.addCard(new FlameSwordsman());
		BaseMod.addCard(new GaiaFierce());
		BaseMod.addCard(new GammaMagnet());
		BaseMod.addCard(new GateGuardian());
		BaseMod.addCard(new GeminiElf());
		BaseMod.addCard(new GiantSoldier());
		BaseMod.addCard(new GiantTrunade());
		BaseMod.addCard(new GracefulCharity());
		BaseMod.addCard(new HarpieFeather());
		BaseMod.addCard(new Hinotoma());
		BaseMod.addCard(new ImperialOrder());
		BaseMod.addCard(new InjectionFairy());
		BaseMod.addCard(new InsectQueen());
		BaseMod.addCard(new JamBreeding());
		BaseMod.addCard(new JudgeMan());
		BaseMod.addCard(new Kuriboh());
		BaseMod.addCard(new SmallLabyrinthWall());
		BaseMod.addCard(new LabyrinthWall());
		BaseMod.addCard(new LegendaryFisherman());
		BaseMod.addCard(new MagicCylinder());
		BaseMod.addCard(new Mausoleum());
		BaseMod.addCard(new MillenniumShield());
		BaseMod.addCard(new MirrorForce());
		BaseMod.addCard(new MonsterReborn());
		BaseMod.addCard(new NutrientZ());
		BaseMod.addCard(new ObeliskTormentor());
		BaseMod.addCard(new OjamaBlack());
		BaseMod.addCard(new OjamaGreen());		
		BaseMod.addCard(new OjamaKnight());
		BaseMod.addCard(new OjamaYellow());
		BaseMod.addCard(new Ojamagic());
		BaseMod.addCard(new Ookazi());
		BaseMod.addCard(new Parasite());
		BaseMod.addCard(new PotAvarice());
		BaseMod.addCard(new PotDichotomy());
		BaseMod.addCard(new PotDuality());
		BaseMod.addCard(new PotGenerosity());
		BaseMod.addCard(new PotGreed());
		BaseMod.addCard(new Pumpking());
		BaseMod.addCard(new Pumprincess());
		BaseMod.addCard(new RadiantMirrorForce());
		BaseMod.addCard(new RainMercy());
		BaseMod.addCard(new RedEyes());
		BaseMod.addCard(new RedEyesToon());
		BaseMod.addCard(new RedMedicine());
		BaseMod.addCard(new Relinquished());
		BaseMod.addCard(new SangaEarth());
		BaseMod.addCard(new SangaThunder());
		BaseMod.addCard(new SangaWater());
		BaseMod.addCard(new Scapegoat());
		BaseMod.addCard(new ScrapFactory());
		BaseMod.addCard(new SevenColoredFish());
		BaseMod.addCard(new ShardGreed());
		BaseMod.addCard(new SliferSky());
		BaseMod.addCard(new StormingMirrorForce());
		BaseMod.addCard(new SummonedSkull());
		BaseMod.addCard(new SuperheavyBenkei());
		BaseMod.addCard(new SuperheavyScales());
		BaseMod.addCard(new SuperheavySwordsman());
		BaseMod.addCard(new SuperheavyWaraji());
		BaseMod.addCard(new SwordsBurning());
		BaseMod.addCard(new SwordsConcealing());
		//BaseMod.addCard(new SwordsRevealing());
		//BaseMod.addCard(new TimeWizard());
		BaseMod.addCard(new ToonBarrelDragon());
		BaseMod.addCard(new ToonBriefcase());
		BaseMod.addCard(new ToonDarkMagician());
		BaseMod.addCard(new ToonDarkMagicianGirl());
		BaseMod.addCard(new ToonGeminiElf());
		BaseMod.addCard(new ToonMermaid());
		BaseMod.addCard(new ToonSummonedSkull());
		BaseMod.addCard(new ToonWorld());
		//BaseMod.addCard(new TrapHole());
		BaseMod.addCard(new ValkMagnet());
		BaseMod.addCard(new WingedDragonRa());
		
		// Second wave
		BaseMod.addCard(new BigFire());
	    BaseMod.addCard(new ToonMask());
	    BaseMod.addCard(new ToonMagic());
	    BaseMod.addCard(new ToonKingdom());
	    BaseMod.addCard(new ToonRollback());
	    BaseMod.addCard(new SuperheavyOgre());
	    BaseMod.addCard(new SuperheavyMagnet());	    
	    BaseMod.addCard(new SuperheavyFlutist());	    
	    BaseMod.addCard(new SuperheavyBlueBrawler());
	    BaseMod.addCard(new SpiritHarp());
	    BaseMod.addCard(new SnowDragon());
	    BaseMod.addCard(new SnowdustDragon());
	    BaseMod.addCard(new Raigeki());
	    BaseMod.addCard(new PreventRat());
	    BaseMod.addCard(new Ojamuscle());
	    BaseMod.addCard(new MysticalElf());
	    BaseMod.addCard(new IslandTurtle());
	    BaseMod.addCard(new GravityAxe());
	    BaseMod.addCard(new FortressWarrior());
	    BaseMod.addCard(new CaveDragon());
	    BaseMod.addCard(new BlizzardDragon());
	    BaseMod.addCard(new BabyDragon());
	    BaseMod.addCard(new LordD());
	    BaseMod.addCard(new FluteSummoning());
	    BaseMod.addCard(new HaneHane());
	    BaseMod.addCard(new LesserDragon());
	    BaseMod.addCard(new GaiaDragonChamp());
	    BaseMod.addCard(new DragonCaptureJar());
	    BaseMod.addCard(new BlastJuggler());
	    BaseMod.addCard(new LegendaryExodia());
	    BaseMod.addCard(new ThunderDragon());
	    BaseMod.addCard(new SuperancientDinobeast());
	    
    	// random only
	    
	    //BaseMod.addCard(new BigCastleWalls());
    	BaseMod.addCard(new CurseDragon());
    	BaseMod.addCard(new CyberDragon());
    	BaseMod.addCard(new DragonMaster());
    	BaseMod.addCard(new FiendSkull());
    	BaseMod.addCard(new FiveHeaded());
    	BaseMod.addCard(new Gandora());
    	BaseMod.addCard(new SuperheavyDaihachi());
    	BaseMod.addCard(new SuperheavyGeneral());
    	BaseMod.addCard(new OjamaKing());
    	
        
		logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        UnlockTracker.unlockCard(AlphaMagnet.ID);
		UnlockTracker.unlockCard(AncientRules.ID);
		UnlockTracker.unlockCard(AxeDespair.ID);
		UnlockTracker.unlockCard(BadReaction.ID);
		UnlockTracker.unlockCard(BarrelDragon.ID);
		UnlockTracker.unlockCard(BetaMagnet.ID);
		//UnlockTracker.unlockCard(BigCastleWalls.ID);
		UnlockTracker.unlockCard(BlueEyes.ID);
		UnlockTracker.unlockCard(BlueEyesToon.ID);
		UnlockTracker.unlockCard(BlueEyesUltimate.ID);
		UnlockTracker.unlockCard(BusterBlader.ID);
		UnlockTracker.unlockCard(CannonSoldier.ID);
		UnlockTracker.unlockCard(CardDestruction.ID);
		UnlockTracker.unlockCard(CastleDarkIllusions.ID);
		UnlockTracker.unlockCard(CastleWalls.ID);
		UnlockTracker.unlockCard(CatapultTurtle.ID);
		UnlockTracker.unlockCard(CelticGuardian.ID);
		UnlockTracker.unlockCard(ChangeHeart.ID);
		UnlockTracker.unlockCard(DarkFactory.ID);
		UnlockTracker.unlockCard(DarkHole.ID);
		UnlockTracker.unlockCard(DarkMagician.ID);
		UnlockTracker.unlockCard(DarkMagicianGirl.ID);
		UnlockTracker.unlockCard(DarkMirrorForce.ID);
		UnlockTracker.unlockCard(DarklordMarie.ID);
		UnlockTracker.unlockCard(DianKeto.ID);
		UnlockTracker.unlockCard(ExodiaHead.ID);
		UnlockTracker.unlockCard(ExodiaLA.ID);
		UnlockTracker.unlockCard(ExodiaLL.ID);
		UnlockTracker.unlockCard(ExodiaRA.ID);
		UnlockTracker.unlockCard(ExodiaRL.ID);
		UnlockTracker.unlockCard(FeatherPho.ID);
		UnlockTracker.unlockCard(FiendMegacyber.ID);
		UnlockTracker.unlockCard(Fissure.ID);
		UnlockTracker.unlockCard(FlameSwordsman.ID);
		UnlockTracker.unlockCard(GaiaFierce.ID);
		UnlockTracker.unlockCard(GammaMagnet.ID);
		UnlockTracker.unlockCard(GateGuardian.ID);
		UnlockTracker.unlockCard(GeminiElf.ID);
		UnlockTracker.unlockCard(GiantSoldier.ID);
		UnlockTracker.unlockCard(GiantTrunade.ID);
		UnlockTracker.unlockCard(GracefulCharity.ID);
		UnlockTracker.unlockCard(HarpieFeather.ID);
		UnlockTracker.unlockCard(Hinotoma.ID);
		UnlockTracker.unlockCard(ImperialOrder.ID);
		UnlockTracker.unlockCard(InjectionFairy.ID);
		UnlockTracker.unlockCard(InsectQueen.ID);
		UnlockTracker.unlockCard(JamBreeding.ID);
		UnlockTracker.unlockCard(JudgeMan.ID);
		UnlockTracker.unlockCard(Kuriboh.ID);
		UnlockTracker.unlockCard(LabyrinthWall.ID);
		UnlockTracker.unlockCard(SmallLabyrinthWall.ID);
		UnlockTracker.unlockCard(LegendaryFisherman.ID);
		UnlockTracker.unlockCard(MagicCylinder.ID);
		UnlockTracker.unlockCard(Mausoleum.ID);
		UnlockTracker.unlockCard(MillenniumShield.ID);
		UnlockTracker.unlockCard(MirrorForce.ID);
		UnlockTracker.unlockCard(MonsterReborn.ID);
		UnlockTracker.unlockCard(NutrientZ.ID);
		UnlockTracker.unlockCard(ObeliskTormentor.ID);
		UnlockTracker.unlockCard(OjamaBlack.ID);
		UnlockTracker.unlockCard(OjamaGreen.ID);
		UnlockTracker.unlockCard(OjamaKing.ID);
		UnlockTracker.unlockCard(OjamaKnight.ID);
		UnlockTracker.unlockCard(OjamaYellow.ID);
		UnlockTracker.unlockCard(Ojamagic.ID);
		UnlockTracker.unlockCard(Ookazi.ID);
		UnlockTracker.unlockCard(Parasite.ID);
		UnlockTracker.unlockCard(PotAvarice.ID);
		UnlockTracker.unlockCard(PotDichotomy.ID);
		UnlockTracker.unlockCard(PotDuality.ID);
		UnlockTracker.unlockCard(PotGenerosity.ID);
		UnlockTracker.unlockCard(PotGreed.ID);
		UnlockTracker.unlockCard(Pumpking.ID);
		UnlockTracker.unlockCard(Pumprincess.ID);
		UnlockTracker.unlockCard(RadiantMirrorForce.ID);
		UnlockTracker.unlockCard(RainMercy.ID);
		UnlockTracker.unlockCard(RedEyes.ID);
		UnlockTracker.unlockCard(RedEyesToon.ID);
		UnlockTracker.unlockCard(RedMedicine.ID);
		UnlockTracker.unlockCard(Relinquished.ID);
		UnlockTracker.unlockCard(SangaEarth.ID);
		UnlockTracker.unlockCard(SangaThunder.ID);
		UnlockTracker.unlockCard(SangaWater.ID);
		UnlockTracker.unlockCard(Scapegoat.ID);
		UnlockTracker.unlockCard(ScrapFactory.ID);
		UnlockTracker.unlockCard(SevenColoredFish.ID);
		UnlockTracker.unlockCard(ShardGreed.ID);
		UnlockTracker.unlockCard(SliferSky.ID);
		UnlockTracker.unlockCard(StormingMirrorForce.ID);
		UnlockTracker.unlockCard(SummonedSkull.ID);
		UnlockTracker.unlockCard(SuperheavyBenkei.ID);
		UnlockTracker.unlockCard(SuperheavyScales.ID);
		UnlockTracker.unlockCard(SuperheavySwordsman.ID);
		UnlockTracker.unlockCard(SuperheavyWaraji.ID);
		UnlockTracker.unlockCard(SwordsBurning.ID);
		UnlockTracker.unlockCard(SwordsConcealing.ID);
		//UnlockTracker.unlockCard(SwordsRevealing.ID);
		//UnlockTracker.unlockCard(TimeWizard.ID);
		UnlockTracker.unlockCard(ToonBarrelDragon.ID);
		UnlockTracker.unlockCard(ToonBriefcase.ID);
		UnlockTracker.unlockCard(ToonDarkMagician.ID);
		UnlockTracker.unlockCard(ToonDarkMagicianGirl.ID);
		UnlockTracker.unlockCard(ToonGeminiElf.ID);
		UnlockTracker.unlockCard(ToonMermaid.ID);
		UnlockTracker.unlockCard(ToonSummonedSkull.ID);
		UnlockTracker.unlockCard(ToonWorld.ID);
		//UnlockTracker.unlockCard(TrapHole.ID);
		UnlockTracker.unlockCard(ValkMagnet.ID);
		UnlockTracker.unlockCard(WingedDragonRa.ID);
		
		// Second wave
		UnlockTracker.unlockCard(BigFire.ID);
	    UnlockTracker.unlockCard(ToonMask.ID);
	    UnlockTracker.unlockCard(ToonMagic.ID);
	    UnlockTracker.unlockCard(ToonKingdom.ID);
	    UnlockTracker.unlockCard(ToonRollback.ID);
	    UnlockTracker.unlockCard(SuperheavyOgre.ID);
	    UnlockTracker.unlockCard(SuperheavyMagnet.ID);
	    UnlockTracker.unlockCard(SuperheavyGeneral.ID);
	    UnlockTracker.unlockCard(SuperheavyFlutist.ID);
	    UnlockTracker.unlockCard(SuperheavyDaihachi.ID);
	    UnlockTracker.unlockCard(SuperheavyBlueBrawler.ID);
	    UnlockTracker.unlockCard(SpiritHarp.ID);
	    UnlockTracker.unlockCard(SnowDragon.ID);
	    UnlockTracker.unlockCard(SnowdustDragon.ID);
	    UnlockTracker.unlockCard(Raigeki.ID);
	    UnlockTracker.unlockCard(PreventRat.ID);
	    UnlockTracker.unlockCard(Ojamuscle.ID);
	    UnlockTracker.unlockCard(MysticalElf.ID);
	    UnlockTracker.unlockCard(IslandTurtle.ID);
	    UnlockTracker.unlockCard(GravityAxe.ID);
	    UnlockTracker.unlockCard(FortressWarrior.ID);
	    UnlockTracker.unlockCard(CaveDragon.ID);
	    UnlockTracker.unlockCard(BlizzardDragon.ID);
	    UnlockTracker.unlockCard(BabyDragon.ID);
	    UnlockTracker.unlockCard(LordD.ID);
	    UnlockTracker.unlockCard(FluteSummoning.ID);
	    UnlockTracker.unlockCard(HaneHane.ID);
	    UnlockTracker.unlockCard(LesserDragon.ID);
	    UnlockTracker.unlockCard(GaiaDragonChamp.ID);
	    UnlockTracker.unlockCard(DragonCaptureJar.ID);
	    UnlockTracker.unlockCard(BlastJuggler.ID);
	    UnlockTracker.unlockCard(LegendaryExodia.ID);
	    UnlockTracker.unlockCard(ThunderDragon.ID);
	    UnlockTracker.unlockCard(SuperancientDinobeast.ID);
	    
    	// random only
    	UnlockTracker.unlockCard(CurseDragon.ID);
    	UnlockTracker.unlockCard(CyberDragon.ID);
    	UnlockTracker.unlockCard(DragonMaster.ID);
    	UnlockTracker.unlockCard(FiendSkull.ID);
    	UnlockTracker.unlockCard(FiveHeaded.ID);
    	UnlockTracker.unlockCard(Gandora.ID);
    	
		
        logger.info("Done adding cards!");
        
        // Adding to array for random generation
        myCards.add(new AlphaMagnet()); 
		myCards.add(new AncientRules());
		myCards.add(new AxeDespair());
		myCards.add(new BadReaction());
		myCards.add(new BarrelDragon());
		myCards.add(new BetaMagnet());
		myCards.add(new BigCastleWalls());
		myCards.add(new BlueEyes());
		myCards.add(new BlueEyesToon());
		myCards.add(new BlueEyesUltimate());
		myCards.add(new BusterBlader());
		myCards.add(new CannonSoldier());
		myCards.add(new CardDestruction());
		myCards.add(new CastleDarkIllusions());
		myCards.add(new CastleWalls());
		myCards.add(new CatapultTurtle());
		myCards.add(new CelticGuardian());
		myCards.add(new ChangeHeart());
		myCards.add(new DarkFactory());
		myCards.add(new DarkHole());
		myCards.add(new DarkMagician());
		myCards.add(new DarkMagicianGirl());
		myCards.add(new DarkMirrorForce());
		myCards.add(new DarklordMarie());
		myCards.add(new DianKeto());
		myCards.add(new ExodiaHead());
		myCards.add(new ExodiaLA());
		myCards.add(new ExodiaLL());
		myCards.add(new ExodiaRA());
		myCards.add(new ExodiaRL());
		myCards.add(new FeatherPho());
		myCards.add(new FiendMegacyber());
		myCards.add(new Fissure());
		myCards.add(new FlameSwordsman());
		myCards.add(new GaiaFierce());
		myCards.add(new GammaMagnet());
		myCards.add(new GateGuardian());
		myCards.add(new GeminiElf());
		myCards.add(new GiantSoldier());
		myCards.add(new GiantTrunade());
		myCards.add(new GracefulCharity());
		myCards.add(new HarpieFeather());
		myCards.add(new Hinotoma());
		myCards.add(new ImperialOrder());
		myCards.add(new InjectionFairy());
		myCards.add(new InsectQueen());
		myCards.add(new JamBreeding());
		myCards.add(new JudgeMan());
		myCards.add(new Kuriboh());
		myCards.add(new SmallLabyrinthWall());
		myCards.add(new LabyrinthWall());
		myCards.add(new LegendaryFisherman());
		myCards.add(new MagicCylinder());
		myCards.add(new Mausoleum());
		myCards.add(new MillenniumShield());
		myCards.add(new MirrorForce());
		myCards.add(new MonsterReborn());
		myCards.add(new NutrientZ());
		myCards.add(new ObeliskTormentor());
		myCards.add(new OjamaBlack());
		myCards.add(new OjamaGreen());
		myCards.add(new OjamaKing());
		myCards.add(new OjamaKnight());
		myCards.add(new OjamaYellow());
		myCards.add(new Ojamagic());
		myCards.add(new Ookazi());
		myCards.add(new Parasite());
		myCards.add(new PotAvarice());
		myCards.add(new PotDichotomy());
		myCards.add(new PotDuality());
		myCards.add(new PotGenerosity());
		myCards.add(new PotGreed());
		myCards.add(new Pumpking());
		myCards.add(new Pumprincess());
		myCards.add(new RadiantMirrorForce());
		myCards.add(new RainMercy());
		myCards.add(new RedEyes());
		myCards.add(new RedEyesToon());
		myCards.add(new RedMedicine());
		myCards.add(new Relinquished());
		myCards.add(new SangaEarth());
		myCards.add(new SangaThunder());
		myCards.add(new SangaWater());
		myCards.add(new Scapegoat());
		myCards.add(new ScrapFactory());
		myCards.add(new SevenColoredFish());
		myCards.add(new ShardGreed());
		myCards.add(new SliferSky());
		myCards.add(new StormingMirrorForce());
		myCards.add(new SummonedSkull());
		myCards.add(new SuperheavyBenkei());
		myCards.add(new SuperheavyScales());
		myCards.add(new SuperheavySwordsman());
		myCards.add(new SuperheavyWaraji());
		myCards.add(new SwordsBurning());
		myCards.add(new SwordsConcealing());
		//myCards.add(new SwordsRevealing());
		//myCards.add(new TimeWizard());
		myCards.add(new ToonBarrelDragon());
		myCards.add(new ToonBriefcase());
		myCards.add(new ToonDarkMagician());
		myCards.add(new ToonDarkMagicianGirl());
		myCards.add(new ToonGeminiElf());
		myCards.add(new ToonMermaid());
		myCards.add(new ToonSummonedSkull());
		myCards.add(new ToonWorld());
		//myCards.add(new TrapHole());
		myCards.add(new ValkMagnet());
		myCards.add(new WingedDragonRa());
		myCards.add(new BigFire());
	    myCards.add(new ToonMask());
	    myCards.add(new ToonMagic());
	    myCards.add(new ToonKingdom());
	    myCards.add(new ToonRollback());
	    myCards.add(new SuperheavyOgre());
	    myCards.add(new SuperheavyMagnet());
	    myCards.add(new SuperheavyGeneral());
	    myCards.add(new SuperheavyFlutist());
	    myCards.add(new SuperheavyDaihachi());
	    myCards.add(new SuperheavyBlueBrawler());
	    myCards.add(new SpiritHarp());
	    myCards.add(new SnowDragon());
	    myCards.add(new SnowdustDragon());
	    myCards.add(new Raigeki());
	    myCards.add(new PreventRat());
	    myCards.add(new Ojamuscle());
	    myCards.add(new MysticalElf());
	    myCards.add(new IslandTurtle());
	    myCards.add(new GravityAxe());
	    myCards.add(new FortressWarrior());
	    myCards.add(new CaveDragon());
	    myCards.add(new BlizzardDragon());
	    myCards.add(new BabyDragon());
	    myCards.add(new LordD());
	    myCards.add(new FluteSummoning());
    	myCards.add(new CurseDragon());
    	myCards.add(new CyberDragon());
    	myCards.add(new DragonMaster());
    	myCards.add(new FiendSkull());
    	myCards.add(new FiveHeaded());
    	myCards.add(new Gandora());
    	myCards.add(new HaneHane());
    	myCards.add(new LesserDragon());
    	myCards.add(new GaiaDragonChamp());
    	myCards.add(new DragonCaptureJar());
    	myCards.add(new BlastJuggler());
    	myCards.add(new LegendaryExodia());
    	myCards.add(new ThunderDragon());
    	myCards.add(new SuperancientDinobeast());
    	
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
        BaseMod.addKeyword(new String[] {"summon", "Summon", "Summons", "summons", "Resummon", "resummon"}, "Counts monsters currently summoned. Maximum of #b5 #ySummons.");
        BaseMod.addKeyword(new String[] {"tribute", "Tribute", "Tributes", "tributes", "sacrifice"}, "Removes X #ySummons. Unless you have enough #ySummons to #yTribute, you cannot play a #yTribute monster.");
        BaseMod.addKeyword(new String[] {"Increment", "increment" }, "Increase your maximum #ySummons by the number given.");
        //BaseMod.addKeyword(new String[] {"counter", "Counter", "Counters", "counters"}, "#ySpell #yCounters have no inherent effect. Used in tandem with magic monsters to trigger powerful effects.");	
        BaseMod.addKeyword(new String[] {"exodia", "Exodia"}, "A powerful monster found within your Grandpa's deck.");
        BaseMod.addKeyword(new String[] {"Gate", "gate"}, "#yOrb: Deal damage to ALL enemies, gain #yEnergy and #yBlock. NL #yGate is unaffected by #yFocus.");
        BaseMod.addKeyword(new String[] {"Buffer", "buffer"}, "#yOrb: Increase your power stacks at the start of turn. #yEvoke gives random #ydebuffs.");
        BaseMod.addKeyword(new String[] {"Summoner", "summoner"}, "#yOrb: #ySummon at the end of turn. #yEvoke increases your max #ySummons.");
        BaseMod.addKeyword(new String[] {"Reducer", "reducer"}, "#yOrb: At the start of turn, increase the cards reduced when this is evoked. #yEvoke sets the cost of random card(s) in your hand to 0.");
        BaseMod.addKeyword(new String[] {"MonsterOrb", "monsterorb", "MonsterOrb"}, "#yOrb: At the start of turn, adds random monster cards to your hand. #yEvoke also adds monsters to your hand.");
        BaseMod.addKeyword(new String[] {"Dragonorb", "dragonorb", "DragonOrb"}, "#yOrb: At the start of turn, adds random #yDragon cards to your hand. #yEvoke sets the cost of random #yDragons in your hand to 0.");
        BaseMod.addKeyword(new String[] {"Overflow", "overflow"}, "When a card with #yOverflow is in your hand at the end of the turn, activate an effect. This effect has a limited amount of uses.");
        BaseMod.addKeyword(new String[] {"Toon", "toon"}, "Can only be played if #yToon #yWorld is active. If you Tribute Summon a Toon monster using another Toon as the tribute, deal #b5 damage to all enemies.");
        BaseMod.addKeyword(new String[] {"Magnet", "magnet", "Magnets", "magnets"}, "Tokens associated with the #yMagnet #yWarrior monsters. #yMagnets have no inherent effect.");
        BaseMod.addKeyword(new String[] {"Ojamania", "ojamania" }, "Add #b2 random cards to your hand, they cost #b0 this turn. Apply #b1 random #ybuff. Apply #b2 random #ydebuffs to an enemy.");
       
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

}
