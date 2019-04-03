package duelistmod;

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
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
import duelistmod.actions.common.RandomizedAction;
import duelistmod.cards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.interfaces.*;
import duelistmod.orbCards.*;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.potions.*;
import duelistmod.powers.*;
import duelistmod.relics.*;
import duelistmod.variables.*;



@SpireInitializer @SuppressWarnings("unused")
public class DuelistMod
implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
EditCharactersSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, PostBattleSubscriber, OnPlayerDamagedSubscriber,
PostPowerApplySubscriber, OnPowersModifiedSubscriber, PostDeathSubscriber, OnCardUseSubscriber, PostCreateStartingDeckSubscriber,
RelicGetSubscriber, AddCustomModeModsSubscriber, PostDrawSubscriber, PostDungeonInitializeSubscriber, OnPlayerLoseBlockSubscriber,
PreMonsterTurnSubscriber
{
	public static final Logger logger = LogManager.getLogger("theDuelist:DuelistMod ---> " + DuelistMod.class.getName());
	public static final String MOD_ID_PREFIX = "theDuelist:";
	
	// Member fields
	private static final String MODNAME = "Duelist Mod";
	private static final String AUTHOR = "Nyoxide";
	private static final String DESCRIPTION = "A Slay the Spire adaptation of Yu-Gi-Oh!";
	private static String modID = "duelistmod";
	private static ArrayList<String> cardSets = new ArrayList<String>();
	private static ArrayList<StarterDeck> starterDeckList = new ArrayList<StarterDeck>();
	private static ArrayList<DuelistCard> deckToStartWith = new ArrayList<DuelistCard>();
	private static ArrayList<DuelistCard> standardDeck = new ArrayList<DuelistCard>();
	private static ArrayList<DuelistCard> orbCards = new ArrayList<DuelistCard>();
	private static Map<CardTags, StarterDeck> deckTagMap = new HashMap<CardTags, StarterDeck>();
	private static int setIndex = 0;
	private static final int SETS = 7;
	private static int DECKS = 20;
	private static int cardCount = 75;
	private static CardTags chosenDeckTag = Tags.STANDARD_DECK;
	private static int randomDeckSmallSize = 10;
	private static int randomDeckBigSize = 15;
	private static boolean runInProgress = false;
	private static boolean ranFunc = false;
	
	// Global Fields
	
	// Config Settings
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
	public static final String PROP_RESET = "resetProg";
	public static Properties duelistDefaults = new Properties();
	public static boolean toonBtnBool = false;
	public static boolean exodiaBtnBool = false;
	public static boolean crossoverBtnBool = true;
	public static boolean challengeMode = false;
	public static boolean unlockAllDecks = false;
	public static boolean flipCardTags = false;
	
	// Maps and Lists
	public static HashMap<String, DuelistCard> summonMap = new HashMap<String, DuelistCard>();
	public static HashMap<String, AbstractPower> buffMap = new HashMap<String, AbstractPower>();
	public static HashMap<String, AbstractOrb> invertStringMap = new HashMap<String, AbstractOrb>();
	public static HashMap<String, StarterDeck> starterDeckNamesMap = new HashMap<String, StarterDeck>();
	private static final HashMap<Integer, Texture> characterPortraits = new HashMap<>();
	public static Map<String, DuelistCard> orbCardMap = new HashMap<String, DuelistCard>();
	public static ArrayList<DuelistCard> myCards = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> monstersThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> monstersThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> spellsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> spellsThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> trapsThisCombat = new ArrayList<DuelistCard>();
	public static ArrayList<DuelistCard> trapsThisRun = new ArrayList<DuelistCard>();
	public static ArrayList<AbstractCard> tinFluteCards = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractCard> coloredCards = new ArrayList<AbstractCard>();
	public static ArrayList<AbstractPower> randomBuffs = new ArrayList<AbstractPower>();
	public static ArrayList<String> startingDecks = new ArrayList<String>();
	public static ArrayList<String> randomBuffStrings = new ArrayList<String>();
	
	// Global Flags
	public static boolean toonWorldTemp = false;
	public static boolean resetProg = false;
	public static boolean hasRing = false;
	public static boolean hasKey = false;
	public static boolean checkTrap = false;
	public static boolean checkUO = false;
	public static boolean gotFirePot = false;
	public static boolean ultimateOfferingTrig = false;
	public static boolean playedOneCardThisCombat = false;
	public static boolean isApi = Loader.isModLoaded("archetypeapi");
	public static boolean isConspire = Loader.isModLoaded("conspire");
	public static boolean isReplay = Loader.isModLoaded("ReplayTheSpireMod");
	public static boolean isHubris = Loader.isModLoaded("hubris");

	// Numbers
	public static int lastMaxSummons = 5;
	//public static int toonDamage = 7;
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
	
	// Other
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
	public static final boolean addTokens = true;	// adds debug tokens to library
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
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Subscribe to BaseMod hooks");

		BaseMod.subscribe(this);

		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Done subscribing");
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Creating the color " + AbstractCardEnum.DUELIST.toString());
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Creating the color " + AbstractCardEnum.DUELIST_MONSTERS.toString());
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Creating the color " + AbstractCardEnum.DUELIST_SPELLS.toString());
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Creating the color " + AbstractCardEnum.DUELIST_TRAPS.toString());

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
		
		// Register green again for Special Pot of Greed
		BaseMod.addColor(AbstractCardEnum.DUELIST, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE,
				Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, Colors.DEFAULT_PURPLE, makePath(Strings.ATTACK_DEFAULT_GREEN),
				makePath(Strings.SKILL_DEFAULT_GREEN), makePath(Strings.POWER_DEFAULT_GREEN),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN), makePath(Strings.ATTACK_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.SKILL_DEFAULT_GREEN_PORTRAIT), makePath(Strings.POWER_DEFAULT_GREEN_PORTRAIT),
				makePath(Strings.ENERGY_ORB_DEFAULT_GREEN_PORTRAIT), makePath(Strings.CARD_ENERGY_ORB_GREEN));

		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Done creating the color");
		
		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Setting up or loading the settings config file");
		duelistDefaults.setProperty(PROP_TOON_BTN, "FALSE");
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
		duelistDefaults.setProperty(PROP_RESET, "FALSE");
		
		cardSets.add("Normal"); 
		cardSets.add("Basic Only");
		cardSets.add("Basic + 1 random archetype");
		cardSets.add("Basic + 2 random archetypes");
		cardSets.add("Normal + 1 random archetype");
		cardSets.add("Normal + 2 random archetypes");
		cardSets.add("Full set always");
		
		int save = 0;
		StarterDeck regularDeck = new StarterDeck(Tags.STANDARD_DECK, "Standard Deck (11 cards)", save, "Standard Deck", true); starterDeckList.add(regularDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck dragDeck = new StarterDeck(Tags.DRAGON_DECK, "Dragon Deck (11 cards)", save, "Dragon Deck", false); starterDeckList.add(dragDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck natDeck = new StarterDeck(Tags.NATURE_DECK, "Nature Deck (11 cards)", save, "Nature Deck", false); starterDeckList.add(natDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck spellcDeck = new StarterDeck(Tags.SPELLCASTER_DECK, "Spellcaster Deck (9 cards)", save, "Spellcaster Deck", false); starterDeckList.add(spellcDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck creaDeck = new StarterDeck(Tags.CREATOR_DECK, "Creator Deck (10 cards)", save, "Creator Deck", true); starterDeckList.add(creaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran1Deck = new StarterDeck(Tags.RANDOM_DECK_SMALL, "Random Deck (10 cards)", save, "Random Deck (Small)", true); starterDeckList.add(ran1Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ran2Deck = new StarterDeck(Tags.RANDOM_DECK_BIG, "Random Deck (15 cards)", save, "Random Deck (Big)", true); starterDeckList.add(ran2Deck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck toonDeck = new StarterDeck(Tags.TOON_DECK, "Toon Deck (10 cards)", save, "Toon Deck", false); starterDeckList.add(toonDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck oDeck = new StarterDeck(Tags.ORB_DECK, "Orb Deck (12 cards)", save, "Orb Deck", true); starterDeckList.add(oDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck resDeck = new StarterDeck(Tags.RESUMMON_DECK, "Resummon Deck (10 cards)", save, "Resummon Deck", true); starterDeckList.add(resDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck gDeck = new StarterDeck(Tags.GENERATION_DECK, "Generation Deck (16 cards)", save, "Generation Deck", true); starterDeckList.add(gDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck ojDeck = new StarterDeck(Tags.OJAMA_DECK, "Ojama Deck (12 cards)", save, "Ojama Deck", true); starterDeckList.add(ojDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck hpDeck = new StarterDeck(Tags.HEAL_DECK, "Heal Deck (12 cards)", save, "Heal Deck", true); starterDeckList.add(hpDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck incDeck = new StarterDeck(Tags.INCREMENT_DECK, "Increment Deck (14 cards)", save, "Increment Deck", true); starterDeckList.add(incDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck exodiaDeck = new StarterDeck(Tags.EXODIA_DECK, "Exodia Deck (60 cards)", save, "Exodia Deck", true); starterDeckList.add(exodiaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck magnetDeck = new StarterDeck(MAGNET_DECK, "Superheavy Deck (12 cards)", save, "Superheavy Deck", false); starterDeckList.add(magnetDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck aquaDeck = new StarterDeck(AQUA_DECK, "Aqua Deck (10 cards)", save, "Aqua Deck", false); starterDeckList.add(aquaDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		//StarterDeck machineDeck = new StarterDeck(MACHINE_DECK, "Machine Deck (12 cards)", save, "Machine Deck", false); starterDeckList.add(machineDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck originalDeck = new StarterDeck(Tags.ORIGINAL_DECK, "Original Deck (10 cards)", save, "Original Deck", true); starterDeckList.add(originalDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opDragDeck = new StarterDeck(Tags.OP_DRAGON_DECK, "Old Dragon Deck (10 cards)", save, "Old Dragon Deck", false); starterDeckList.add(opDragDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opNatDeck = new StarterDeck(Tags.OP_NATURE_DECK, "Old Nature Deck (11 cards)", save, "Old Nature Deck", false); starterDeckList.add(opNatDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opSpellcDeck = new StarterDeck(Tags.OP_SPELLCASTER_DECK, "Old Spellcaster Deck (10 cards)", save, "Old Spellcaster Deck", false); starterDeckList.add(opSpellcDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opOrbDeck = new StarterDeck(Tags.ORIGINAL_ORB_DECK, "Old Orb Deck (10 cards)", save, "Old Orb Deck", true); starterDeckList.add(opOrbDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opResummonDeck = new StarterDeck(Tags.ORIGINAL_RESUMMON_DECK, "Old Resummon Deck (10 cards)", save, "Old Resummon Deck", true); starterDeckList.add(opResummonDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		StarterDeck opHealDeck = new StarterDeck(Tags.ORIGINAL_HEAL_DECK, "Old Heal Deck (10 cards)", save, "Old Heal Deck", true); starterDeckList.add(opHealDeck); deckTagMap.put(starterDeckList.get(save).getDeckTag(), starterDeckList.get(save)); save++;
		
		for (StarterDeck d : starterDeckList) { startingDecks.add(d.getName()); starterDeckNamesMap.put(d.getSimpleName(), d); }
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
            resetProg = config.getBool(PROP_RESET);
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
		
		if (resetProg)
		{
			//if (debug)
			//{
			//	System.out.println("Resetting player progress for the Duelist!");
			//}
			
			//UnlockTracker.resetUnlockProgress(TheDuelistEnum.THE_DUELIST);
		}

		logger.info("theDuelist:DuelistMod:DuelistMod() ---> Done setting up or loading the settings config file");
	}


	public static void initialize() {
		logger.info("theDuelist:DuelistMod:initialize() ---> Initializing Duelist Mod");
		DuelistMod defaultmod = new DuelistMod();
		logger.info("theDuelist:DuelistMod:initialize() ---> Duelist Mod Initialized");
	}

	// ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

	

	// =============== LOAD THE CHARACTER =================

	@Override
	public void receiveEditCharacters() 
	{
		// Yugi Moto
		BaseMod.addCharacter(new TheDuelist("the Duelist", TheDuelistEnum.THE_DUELIST),makePath(Strings.THE_DEFAULT_BUTTON), makePath(Strings.THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_DUELIST);

		// Seto Kaiba
		//BaseMod.addCharacter(new TheDuelist("the Rich Duelist", TheDuelistEnum.THE_RICH_DUELIST),makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_RICH_DUELIST);


		// Maximillion Pegasus
		//BaseMod.addCharacter(new TheDuelist("the Villian", TheDuelistEnum.THE_VILLIAN),makePath(THE_DEFAULT_BUTTON), makePath(THE_DEFAULT_PORTRAIT), TheDuelistEnum.THE_VILLIAN);

		receiveEditPotions();

		logger.info("theDuelist:DuelistMod:receiveEditCharacters() ---> Done editing characters");

	}

	// =============== /LOAD THE CHARACTER/ =================


	// =============== POST-INITIALIZE =================


	@Override
	public void receivePostInitialize() 
	{	
		//if (isApi)
		//{
		//	logger.info("theDuelist:DuelistMod:receivePostInitialize() ---> adding archetypes for API mod");
			//CharacterHelper.loadArchetypes();
		//}
		// END Archetype API Check
		
		// MOD OPTIONS PANEL
		logger.info("theDuelist:DuelistMod:receivePostInitialize() ---> Loading badge image and mod options");
		String loc = localize();

		Texture badgeTexture = new Texture(makePath(Strings.BADGE_IMAGE));
		ModPanel settingsPanel = new ModPanel();
		BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
		UIStrings UI_String = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");
		
		float yPos = 750.0f;
		float xLabPos = 360.0f;
		float xLArrow = 615.0f;
		float xRArrow = 1250.0f;
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
		
		// Check Box F
		String resetString = UI_String.TEXT[12];
		ModLabeledToggleButton resetBtn = new ModLabeledToggleButton(resetString, xLabPos, yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, resetProg, settingsPanel, (label) -> {}, (button) -> 
		{
			resetProg = button.enabled;
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",duelistDefaults);
				config.setBool(PROP_RESET, resetProg);
				config.save();
			} catch (Exception e) { e.printStackTrace(); }
			//if (resetProg)
			//{
			//	UnlockTracker.resetUnlockProgress(TheDuelistEnum.THE_DUELIST);
			//}
		});
		//settingsPanel.addUIElement(resetBtn);
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
			resetStarterDeck();
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
			resetStarterDeck();
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

		logger.info("theDuelist:DuelistMod:receivePostInitialize() ---> Done loading badge Image and mod options");

	}

	// =============== / POST-INITIALIZE/ =================


	// ================ ADD POTIONS ===================


	public void receiveEditPotions() {
		logger.info("theDuelist:DuelistMod:receiveEditPotions() ---> Beginning to edit potions");

		// Class Specific Potion. If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDuelistEnum.THE_DUELIST")
		BaseMod.addPotion(MillenniumElixir.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, MillenniumElixir.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPack.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, SealedPack.POTION_ID, TheDuelistEnum.THE_DUELIST);
		BaseMod.addPotion(SealedPackB.class, Colors.PLACEHOLDER_POTION_LIQUID, Colors.PLACEHOLDER_POTION_HYBRID, Colors.PLACEHOLDER_POTION_SPOTS, SealedPackB.POTION_ID, TheDuelistEnum.THE_DUELIST);

		logger.info("theDuelist:DuelistMod:receiveEditPotions() ---> Done editing potions");
	}

	// ================ /ADD POTIONS/ ===================


	// ================ ADD RELICS ===================

	@Override
	public void receiveEditRelics() {
		logger.info("theDuelist:DuelistMod:receiveEditRelics() ---> Adding relics");

		// This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
		BaseMod.addRelicToCustomPool(new MillenniumPuzzle(), AbstractCardEnum.DUELIST);
		if (!toonBtnBool) { BaseMod.addRelicToCustomPool(new MillenniumEye(), AbstractCardEnum.DUELIST); }
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
		BaseMod.addRelicToCustomPool(new GoldPlatedCables(), AbstractCardEnum.DUELIST);
		if (!exodiaBtnBool) { BaseMod.addRelicToCustomPool(new StoneExxod(), AbstractCardEnum.DUELIST); }
		BaseMod.addRelicToCustomPool(new GiftAnubis(), AbstractCardEnum.DUELIST);
		AbstractDungeon.shopRelicPool.remove("Prismatic Shard");
		
		// This adds a relic to the Shared pool. Every character can find this relic.
		BaseMod.addRelic(new MillenniumPuzzleShared(), RelicType.SHARED);

		logger.info("theDuelist:DuelistMod:receiveEditRelics() ---> Done adding relics!");
	}

	// ================ /ADD RELICS/ ===================

	// ================ ADD CARDS ===================

	@Override
	public void receiveEditCards() {
		//logger.info("Adding variables");
		// Add the Custom Dynamic Variables
		BaseMod.addDynamicVariable(new TributeMagicNumber());
		BaseMod.addDynamicVariable(new SummonMagicNumber());

		// ================ ORB CARDS ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> adding orb cards to array for orb modal");
		setupOrbCards();
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done adding orb cards to array");
		
		// ================ LIBRARY CARDS ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> adding all cards to myCards array");
		setupMyCards();
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done adding all cards to myCards array");

		// ================ STARTER DECKS ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> filling up starting decks");
		initStartDeckArrays();
		initStarterDeckPool();
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> starting deck set as: " + chosenDeckTag.name());
		
		// ================ SUMMON MAP ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> filling summonMap");
		fillSummonMap(myCards);
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done filling summonMap");

		// ================ METRICS HELPER ===================
		if (DuelistMod.debug)
		{
			logger.info("theDuelist:DuelistMod:receiveEditCards() ---> START SQL METRICS PRINT");
			outputSQLListsForMetrics();
			logger.info("theDuelist:DuelistMod:receiveEditCards() ---> END SQL METRICS PRINT");
		}
		
		// ================ COMPENDIUM MANIPULATION ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> begin checking config options and removing cards");
		removeCardsFromSet();
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> all needed cards have been removed from myCards array");
		
		// ================ COLORED CARDS ===================
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> filling colored cards with necessary spells and traps to add to card reward/shop pool");
		fillColoredCards();
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done filling colored cards");
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done");
	}

	// ================ /ADD CARDS/ ===================



	// ================ LOAD THE TEXT ===================

	@Override
	public void receiveEditStrings() {
		logger.info("theDuelist:DuelistMod:receiveEditStrings() ---> Beginning to edit strings");

		String loc = localize();
		
		// Card Strings
		BaseMod.loadCustomStringsFile(CardStrings.class,"duelistModResources/localization/" + loc + "/DuelistMod-Card-Strings.json");

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

		logger.info("theDuelist:DuelistMod:receiveEditStrings() ---> Done editing strings");
	}

	// ================ /LOAD THE TEXT/ ===================

	// ================ LOAD THE KEYWORDS ===================

	@Override
	public void receiveEditKeywords() 
	{
		String loc = localize();
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
	
	public static Texture GetCharacterPortrait(int id)
    {
        Texture result;
        if (!characterPortraits.containsKey(id))
        {
            result = new Texture(makePath("charselect/duelist_portrait_" + id + ".png"));
            characterPortraits.put(id, result);
        }
        else
        {
            result = characterPortraits.get(id);
        }

        return result;
    }
	

	public static void resetStarterDeck()
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
		if (gotFirePot)
		{
			gotFirePot = false;
			AbstractPotion firePot = new FirePotion();
	    	AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(firePot));
		}
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
		{
			if (c instanceof DuelistCard)
			{
				((DuelistCard) c).startBattleReset();
			}
		}
		resetBuffPool();
		lastMaxSummons = 5;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		if (challengeMode) { lastMaxSummons = 4; }
		swordsPlayed = 0;
		logger.info("theDuelist:DuelistMod:receiveOnBattleStart() ---> Reset max summons to 5");
		if (hasRing) { lastMaxSummons = 8; if (challengeMode) { lastMaxSummons = 7; }}
		if (hasKey) { lastMaxSummons = 5; logger.info("theDuelist:DuelistMod:receiveOnBattleStart() ---> Reset max summons to 5");}
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
		if (UnlockTracker.getUnlockLevel(TheDuelistEnum.THE_DUELIST) > 0) 
		{ 
			UnlockTracker.unlockProgress.putInteger(TheDuelistEnum.THE_DUELIST.toString() + "UnlockLevel", 0);
			SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_COMBAT);
			SaveAndContinue.save(saveFile);
			logger.info("theDuelist:DuelistMod:receivePostBattle() ---> unlock level was greater than 0, reset to 0");
		}
		playedOneCardThisCombat = false;
		lastMaxSummons = 5;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		if (challengeMode) { lastMaxSummons = 4; }
		swordsPlayed = 0;
		logger.info("theDuelist:DuelistMod:receivePostBattle() ---> Reset max summons to 5");
		if (hasRing)
		{ 
			lastMaxSummons = 8; 
			if (challengeMode) { lastMaxSummons = 7; }
		}
		if (hasKey) { lastMaxSummons = 5; logger.info("theDuelist:DuelistMod:receiveOnBattleStart() ---> Reset max summons to 5");}
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
		monstersThisRun = new ArrayList<DuelistCard>();
		monstersThisCombat = new ArrayList<DuelistCard>();
		spellsThisRun = new ArrayList<DuelistCard>();
		spellsThisCombat = new ArrayList<DuelistCard>();
		trapsThisRun = new ArrayList<DuelistCard>();
		trapsThisCombat = new ArrayList<DuelistCard>();
		runInProgress = false;
		ranFunc = false;
		spellCombatCount = 0;
		trapCombatCount = 0;
		summonCombatCount = 0;
		spellRunCount = 0;
		trapRunCount = 0;
		summonRunCount = 0;
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
		logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> Card: " + arg0.name);
		if (arg0.hasTag(Tags.SPELL))
		{
			spellCombatCount++;
			spellRunCount++;
			
			spellsThisCombat.add((DuelistCard) arg0.makeCopy());
			spellsThisRun.add((DuelistCard) arg0.makeCopy());
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented spellsThisCombat, new value: " + spellCombatCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented spellRunCombat, new value: " + spellRunCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> added " + arg0.originalName + " to spellsThisCombat, spellsThisRun");
		}
		
		if (arg0.hasTag(Tags.MONSTER))
		{
			//summonCombatCount++;
			//summonRunCount++;
			monstersThisCombat.add((DuelistCard) arg0.makeCopy());
			monstersThisRun.add((DuelistCard) arg0.makeCopy());
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented summonsThisCombat, new value: " + summonCombatCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented summonRunCount, new value: " + summonRunCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> added " + arg0.originalName + " to monstersThisCombat, monstersThisRun");
		}
		
		if (arg0.hasTag(Tags.TRAP))
		{
			trapCombatCount++;
			trapRunCount++;
			trapsThisCombat.add((DuelistCard) arg0.makeCopy());
			trapsThisRun.add((DuelistCard) arg0.makeCopy());
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented trapsThisCombat, new value: " + trapCombatCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> incremented spellRunCombat, new value: " + trapRunCount);
			logger.info("theDuelist:DuelistMod:receiveCardUsed() ---> added " + arg0.originalName + " to trapsThisCombat, trapsThisRun");
		}
	}

	@Override
	public void receivePostCreateStartingDeck(PlayerClass arg0, CardGroup arg1) 
	{
		logger.info("theDuelist:DuelistMod:receivePostCreateStartingDeck() ---> Ran function. Has postDungeonInit ran?: " + ranFunc);
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
			logger.info("theDuelist:DuelistMod:receivePostCreateStartingDeck() ---> found bad mods");
		}
	}
	
	@Override
	public void receiveRelicGet(AbstractRelic arg0) 
	{
		
	}
	
	@Override
	public void receivePostDraw(AbstractCard arg0) 
	{
		boolean hasSmokeOrb = false;
		boolean hasSplashOrb = false;
		boolean hasLavaOrb = false;
		boolean hasFireOrb = false;
		Smoke smoke = new Smoke();
		Lava lava = new Lava();
		Splash splash = new Splash();
		FireOrb fire = new FireOrb();
		
		for (AbstractOrb orb : AbstractDungeon.player.orbs)
		{
			if (orb.name.equals("Smoke"))
			{
				hasSmokeOrb = true;
				smoke = (Smoke) orb;
				if (debug) { logger.info("theDuelist:DuelistMod:receivePostDraw() ---> found a Smoke orb, set flag");  }
			}
			
			if (orb.name.equals("Lava"))
			{
				hasLavaOrb = true;
				lava = (Lava) orb;
				if (debug) { logger.info("theDuelist:DuelistMod:receivePostDraw() ---> found a Lava orb, set flag");  }
			}
			
			if (orb.name.equals("Fire"))
			{
				hasFireOrb = true;
				fire = (FireOrb) orb;
				if (debug) { logger.info("theDuelist:DuelistMod:receivePostDraw() ---> found a Fire orb, set flag");  }
			}
			
			if (orb.name.equals("Splash"))
			{
				hasSplashOrb = true;
				splash = (Splash) orb;
				if (debug) { logger.info("theDuelist:DuelistMod:receivePostDraw() ---> found a Splash orb, set flag");  }
			}
		}
	
		if (arg0.hasTag(Tags.MONSTER))
		{
			if (hasSmokeOrb) { smoke.triggerPassiveEffect((DuelistCard)arg0); }
			if (hasSplashOrb) { splash.triggerPassiveEffect((DuelistCard)arg0); }
			if (hasLavaOrb) { lava.triggerPassiveEffect((DuelistCard)arg0); }
			if (hasFireOrb) { fire.triggerPassiveEffect((DuelistCard)arg0); }
		}
		
		// Underdog - Draw monster = draw 1 card
		if (AbstractDungeon.player.hasPower(HeartUnderdogPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.group.size();
			if (arg0.hasTag(Tags.MONSTER) && handSize < BaseMod.MAX_HAND_SIZE)
			{
				DuelistCard.draw(1);
			}
		}
		
		// Underspell - Draw spell = copy it
		if (AbstractDungeon.player.hasPower(HeartUnderspellPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0.hasTag(Tags.SPELL) && handSize < 10)
			{
				AbstractDungeon.actionManager.addToTop(new RandomizedAction(arg0.makeCopy(), arg0.upgraded, true, true, false, false, false, false, false, 1, 3, 0, 0, 0, 0));
			}
		}
		
		// Undertrap - Draw trap = gain 10 HP
		if (AbstractDungeon.player.hasPower(HeartUndertrapPower.POWER_ID))
		{
			int handSize = AbstractDungeon.player.hand.size();
			if (arg0.hasTag(Tags.TRAP))
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
		runInProgress = true;
		ranFunc = true;
		monstersThisCombat = new ArrayList<DuelistCard>();
		monstersThisRun = new ArrayList<DuelistCard>();
		spellsThisRun = new ArrayList<DuelistCard>();
		spellsThisCombat = new ArrayList<DuelistCard>();
		trapsThisRun = new ArrayList<DuelistCard>();
		trapsThisCombat = new ArrayList<DuelistCard>();
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
		// Variable Manipulation
		summonTurnCount = 0;
		AbstractPlayer p = AbstractDungeon.player;
		
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
			if (debug)
			{				
				AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 2.5F, 2.0F));
				AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.getRandomMonster(), "Screw the rules, I have money!", 1.0F, 2.0F));
			}
			else
			{
				if (msgRoll <= 2)
				{
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Did you just summon a whole bunch of monsters in one turn? Isn't that against the rules?", 2.5F, 2.0F));
					AbstractDungeon.actionManager.addToBottom(new TalkAction(AbstractDungeon.getRandomMonster(), "Screw the rules, I have money!", 1.0F, 2.0F));
				}
			}
		}
		
		
		// Mirror Force Helper
		if (p.hasPower(MirrorForcePower.POWER_ID) && p.currentBlock > 0)
		{
			MirrorForcePower instance = (MirrorForcePower) AbstractDungeon.player.getPower(MirrorForcePower.POWER_ID);
			instance.PLAYER_BLOCK = p.currentBlock;
			if (debug)
			{
				logger.info("theDuelist:DuelistMod:receiveOnPlayerLoseBlock() ---> set mirror force power block to: " + p.currentBlock + ".");
			}
		}
		if (debug)
		{
			logger.info("theDuelist:DuelistMod:receiveOnPlayerLoseBlock() ---> player lost " + arg0 + " block.");
		}
		
		return true;
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
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
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
		
		/* LOOK UP BUFFS DOC IN NOTEPAD */
		
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
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
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
        return Gdx.files.internal("resources/duelistModResources/localization/" + locCode + "/" + name + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
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
					if (myCards.get(i).hasTag(Tags.REPLAYSPIRE))
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
					if (myCards.get(i).hasTag(Tags.CONSPIRE))
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
				if (myCards.get(i).hasTag(Tags.CONSPIRE) || myCards.get(i).hasTag(Tags.REPLAYSPIRE))
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
					if (myCards.get(i).hasTag(Tags.EXODIA))
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
					if ((myCards.get(i).hasTag(Tags.TOON)))
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
					if ((myCards.get(i).hasTag(Tags.ALL)))
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
					if ((myCards.get(i).hasTag(Tags.FULL) || myCards.get(i).hasTag(Tags.ALL)))
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
					if ((myCards.get(i).hasTag(Tags.REDUCED) || myCards.get(i).hasTag(Tags.FULL) || myCards.get(i).hasTag(Tags.ALL)))
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
					if ((myCards.get(i).hasTag(Tags.LIMITED) || myCards.get(i).hasTag(Tags.REDUCED) || myCards.get(i).hasTag(Tags.FULL) || myCards.get(i).hasTag(Tags.ALL)))
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
			if ((!c.hasTag(Tags.RANDOMONLY) && (!c.hasTag(Tags.RANDOMONLY_NOCREATOR))))
			{
				BaseMod.addCard(c); UnlockTracker.unlockCard(c.getID()); summonMap.put(c.originalName, c); tempCardCount++;
				//summonMap.put(c.originalName, c);	
			}
			else
			{
				//summonMap.put(c.originalName, c);				
				if (!c.rarity.equals(CardRarity.SPECIAL)) { UnlockTracker.unlockCard(c.getID()); }
			}
		}
		
		// Special extra duelist card with special blue color
		//BaseMod.addCard(new PotGreedSpell());
		//UnlockTracker.unlockCard(PotGreedSpell.ID);

		/*
		summonMap.put("Token", new Token());
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
		*/
		cardCount = tempCardCount;
		
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done initializing cards");
		logger.info("theDuelist:DuelistMod:receiveEditCards() ---> saving config options for card set");
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
		myCards.add(new BadReaction());
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
		myCards.add(new Sparks());
		myCards.add(new CastleWallsBasic());
		myCards.add(new Sangan());
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
		myCards.add(new WorldCarrot());
		myCards.add(new SoulAbsorbingBone());
		myCards.add(new MsJudge());
		myCards.add(new FiendishChain());
		myCards.add(new Firegrass());
		myCards.add(new PowerKaishin());
		myCards.add(new AncientElf());
		myCards.add(new FinalFlame());
		myCards.add(new YamataDragon());
		myCards.add(new TwinBarrelDragon());		
		myCards.add(new HundredFootedHorror());
		myCards.add(new SlateWarrior());
		myCards.add(new MotherSpider());
		myCards.add(new MangaRyuRan());
		myCards.add(new ToonAncientGear());
		myCards.add(new ClownZombie());
		myCards.add(new RyuKokki());
		myCards.add(new GoblinRemedy());
		myCards.add(new CallGrave());
		myCards.add(new AllyJustice());
		myCards.add(new Graverobber());
		myCards.add(new DragonPiper());
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
		myCards.add(new BadReactionRare());
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
		if (debug)
		{
			printCardSetsForGithubReadme(myCards);
			printTextForTranslation();
			for (DuelistCard c : myCards)
			{
				if (c.tributes != c.baseTributes || c.summons != c.baseSummons)
				{
					if (c.hasTag(Tags.MONSTER))
					{
						logger.info("something didn't match for " + c.originalName + " Base/Current Tributes: " + c.baseTributes + "/" + c.tributes + " :: Base/Current Summons: " + c.baseSummons + "/" + c.summons);
					}
					else
					{
						logger.info("something didn't match for " + c.originalName + " but this card is a spell or trap");					
					}
				}
			}
		}
		
		if (addTokens)
		{
			myCards.add(new Token());
			myCards.add(new BadToken());
			for (DuelistCard orbCard : orbCards)
			{
				myCards.add(orbCard);
			}
			//myCards.add(new GreatMoth());
			//myCards.add(new HeartUnderspell());
			//myCards.add(new HeartUndertrap());
			//myCards.add(new HeartUndertribute());
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
		orbCards.add(new BlackOrbCard());
		orbCards.add(new BlazeOrbCard());
		orbCards.add(new ConsumerOrbCard());
		orbCards.add(new GadgetOrbCard());
		orbCards.add(new LavaOrbCard());
		orbCards.add(new MetalOrbCard());
		orbCards.add(new MillenniumOrbCard());
		orbCards.add(new MistOrbCard());
		orbCards.add(new MudOrbCard());
		orbCards.add(new SandOrbCard());
		orbCards.add(new SmokeOrbCard());
		orbCards.add(new StormOrbCard());
		
		if (isConspire) { orbCards.add(new WaterOrbCard()); }
		for (DuelistCard o : orbCards) { orbCardMap.put(o.name, o); }
		//DuelistCard.resetInvertMap();
	}
	
	public static void fillColoredCards()
	{
		coloredCards = new ArrayList<AbstractCard>();
		
		// Specific archetype selection
		if  	(  findDeckTag(getCurrentDeck().getIndex()).equals(Tags.DRAGON_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.SPELLCASTER_DECK)
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.NATURE_DECK)				
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.TOON_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.ZOMBIE_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.AQUA_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.FIEND_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.MACHINE_DECK) 
				|| findDeckTag(getCurrentDeck().getIndex()).equals(Tags.MAGNET_DECK)
				)
				{
					for (AbstractCard c : getCurrentDeck().getPoolCards())
					{
						if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
						{
							coloredCards.add(c);
						}
					}
				}
				
		else if (findDeckTag(getCurrentDeck().getIndex()).equals(Tags.NATURE_DECK) || findDeckTag(getCurrentDeck().getIndex()).equals(Tags.OP_NATURE_DECK))
		{
			for (AbstractCard c : getCurrentDeck().getPoolCards())
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					coloredCards.add(c);
				}
			}
		}
		
		// All cards
		else
		{
			for (DuelistCard c : myCards)
			{
				if ((c.color.equals(AbstractCardEnum.DUELIST_SPELLS) || c.color.equals(AbstractCardEnum.DUELIST_TRAPS) || c.color.equals(AbstractCardEnum.DUELIST_MONSTERS)) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
				{
					coloredCards.add(c);
					logger.info("theDuelist:DuelistMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
				}
			}
		}

		// POWER CHECK
		boolean hasPower = false;
		int powerCounter = 0;
		int powersToFind = 5;
		for (AbstractCard c : coloredCards) { if (c.type.equals(CardType.POWER)) { powerCounter++; }}
		if (powerCounter >= powersToFind) { hasPower = true; }
		if (!hasPower)
		{
			for (int i = 0; i < 5; i++)
			{
				DuelistCard poolCard = myCards.get(ThreadLocalRandom.current().nextInt(0, myCards.size()));
				while (!poolCard.type.equals(CardType.POWER) || poolCard.rarity.equals(CardRarity.BASIC) || poolCard.rarity.equals(CardRarity.SPECIAL)) 
				{ 
					poolCard = myCards.get(ThreadLocalRandom.current().nextInt(0, myCards.size())); 
				}
				coloredCards.add(poolCard);
			}
		}
		
		if (debug)
		{
			int counter = 1;
			for (AbstractCard c : coloredCards)
			{
				logger.info("theDuelist:DuelistMod:fillColoredCards() ---> coloredCards (" + counter + "): " + c.originalName); 
				counter++;
			}
		}
		// /POWER CHECK/
	}
	
	public void fillSummonMap(ArrayList<DuelistCard> cards)
	{
		for (DuelistCard c : cards) 
		{ 
			summonMap.put(c.originalName, c);	
		}
		
		//summonMap.put("R. Stone Soldier", new RandomSoldier());
		summonMap.put("Token", new Token());
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
	}
	
	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void initStarterDeckPool()
	{
		// Non-archetype Set
		ArrayList<DuelistCard> basicCards = new ArrayList<DuelistCard>();
		basicCards.add(new AcidTrapHole());
		basicCards.add(new AlphaMagnet());
		basicCards.add(new BetaMagnet());
		basicCards.add(new BlastJuggler());
		basicCards.add(new BottomlessTrapHole());
		basicCards.add(new CardDestruction());
		basicCards.add(new CannonSoldier());
		basicCards.add(new CastleWalls());
		basicCards.add(new CatapultTurtle());
		basicCards.add(new CelticGuardian());
		basicCards.add(new CheerfulCoffin());
		basicCards.add(new Cloning());
		basicCards.add(new DarkCreator());
		basicCards.add(new DarkFactory());
		basicCards.add(new DarkHole());
		basicCards.add(new DarkMirrorForce());
		basicCards.add(new DianKeto());
		basicCards.add(new FinalFlame());
		basicCards.add(new Fissure());
		basicCards.add(new FlameSwordsman());
		basicCards.add(new FortressWarrior());
		basicCards.add(new GaiaFierce());
		basicCards.add(new GammaMagnet());
		basicCards.add(new GiantSoldier());
		basicCards.add(new GoldenApples());
		basicCards.add(new HammerShot());
		basicCards.add(new HaneHane());
		basicCards.add(new HarpieFeather());
		basicCards.add(new HeavyStorm());
		basicCards.add(new Hinotama());
		basicCards.add(new ImperialOrder());
		basicCards.add(new JudgeMan());
		basicCards.add(new Kuriboh());
		basicCards.add(new LabyrinthWall());
		basicCards.add(new Mausoleum());
		basicCards.add(new MillenniumShield());
		basicCards.add(new MirrorForce());
		basicCards.add(new MonsterEgg());
		basicCards.add(new ObeliskTormentor());
		basicCards.add(new Ookazi());
		basicCards.add(new FeatherPho());
		basicCards.add(new PotAvarice());
		basicCards.add(new PotDuality());
		basicCards.add(new PotForbidden());
		basicCards.add(new PotDichotomy());
		basicCards.add(new PotGenerosity());
		basicCards.add(new PotGreed());
		basicCards.add(new PreventRat());
		basicCards.add(new RadiantMirrorForce());
		basicCards.add(new Raigeki());
		basicCards.add(new Sangan());
		basicCards.add(new Scapegoat());
		basicCards.add(new ScrapFactory());
		basicCards.add(new ShardGreed());
		basicCards.add(new SmashingGround());
		basicCards.add(new SphereKuriboh());
		basicCards.add(new StimPack());
		basicCards.add(new StormingMirrorForce());
		basicCards.add(new StrayLambs());
		basicCards.add(new SuperancientDinobeast());
		basicCards.add(new SwordsRevealing());
		basicCards.add(new Terraforming());
		basicCards.add(new TheCreator());
		basicCards.add(new TokenVacuum());
		basicCards.add(new BigFire());
		basicCards.add(new UltimateOffering());
		basicCards.add(new ValkMagnet());
		basicCards.add(new Wiretap());
		
		// Dragon Deck && Old Dragon
		StarterDeck dragonDeck = starterDeckNamesMap.get("Dragon Deck");
		ArrayList<DuelistCard> dragonCards = new ArrayList<DuelistCard>();
		dragonCards.add(new AncientRules());
		dragonCards.add(new AxeDespair());
		dragonCards.add(new BabyDragon());
		dragonCards.add(new BarrelDragon());
		dragonCards.add(new BlacklandFireDragon());
		dragonCards.add(new BlizzardDragon());
		dragonCards.add(new BlueEyes());
		dragonCards.add(new BlueEyesUltimate());
		dragonCards.add(new BSkullDragon());
		dragonCards.add(new BusterBlader());
		dragonCards.add(new CaveDragon());
		dragonCards.add(new CurseDragon());
		dragonCards.add(new CyberDragon());
		dragonCards.add(new DarkfireDragon());
		dragonCards.add(new DragonCaptureJar());
		dragonCards.add(new DragonMaster());
		dragonCards.add(new DragonPiper());
		dragonCards.add(new ExploderDragon());
		dragonCards.add(new FiendSkull());
		dragonCards.add(new FiveHeaded());
		dragonCards.add(new FluteSummoning());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new Gandora());
		dragonCards.add(new GravityAxe());
		dragonCards.add(new LesserDragon());
		dragonCards.add(new LeviaDragon());
		dragonCards.add(new LordD());
		dragonCards.add(new MetalDragon());
		dragonCards.add(new Mountain());
		dragonCards.add(new OceanDragonLord());
		dragonCards.add(new RedEyes());
		dragonCards.add(new RedEyesZombie());
		dragonCards.add(new Reinforcements());
		dragonCards.add(new SliferSky());
		dragonCards.add(new SnowDragon());
		dragonCards.add(new SnowdustDragon());
		dragonCards.add(new SwordDeepSeated());
		dragonCards.add(new ThunderDragon());
		dragonCards.add(new TrihornedDragon());
		dragonCards.add(new TwinBarrelDragon());
		//dragonCards.add(new TyrantWing());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());
		// more cards
		dragonDeck.fillPoolCards(dragonCards); 
		dragonDeck.fillPoolCards(basicCards);
		
		// Spellcaster Deck && Old Spellcaster
		StarterDeck spellcasterDeck = starterDeckNamesMap.get("Spellcaster Deck");
		ArrayList<DuelistCard> spellcasterCards = new ArrayList<DuelistCard>();
		spellcasterCards.add(new DarkMagician());
		// more cards
		// more cards
		
		/*   CHECK FOR EXODIA REMOVAL HERE, DONT ADD EXODIA CARDS IF REMOVED  */
		
		spellcasterDeck.fillPoolCards(spellcasterCards);
		spellcasterDeck.fillPoolCards(basicCards);
		
		
		// Nature Deck && Old Nature
		StarterDeck natureDeck = starterDeckNamesMap.get("Nature Deck");
		
		ArrayList<DuelistCard> natureCards = new ArrayList<DuelistCard>();
		natureCards.add(new EmpressMantis());
		// more cards
		// more cards
		natureDeck.fillPoolCards(natureCards);
		natureDeck.fillPoolCards(basicCards);

		
		// Toon Deck
		StarterDeck toonDeck = starterDeckNamesMap.get("Toon Deck");
		ArrayList<DuelistCard> toonCards = new ArrayList<DuelistCard>();
		toonCards.add(new ToonMermaid());
		// more cards
		// more cards
		toonDeck.fillPoolCards(toonCards);
		toonDeck.fillPoolCards(basicCards);
		
		// Zombie Deck
		//StarterDeck zombieDeck = starterDeckNamesMap.get("Zombie Deck");
		//ArrayList<DuelistCard> zombieCards = new ArrayList<DuelistCard>();
		//zombieCards.add(new ClownZombie());
		// more cards
		// more cards
		//zombieDeck.fillPoolCards(zombieCards);
		//zombieDeck.fillPoolCards(basicCards);
		
		// Aqua Deck
		//StarterDeck aquaDeck = starterDeckNamesMap.get("Aqua Deck");
		//ArrayList<DuelistCard> aquaCards = new ArrayList<DuelistCard>();
		//aquaCards.add(new SevenColoredFish());
		// more cards
		// more cards
		//aquaDeck.fillPoolCards(aquaCards);
		//aquaDeck.fillPoolCards(basicCards);
		
		// Fiend Deck
		//StarterDeck fiendDeck = starterDeckNamesMap.get("Fiend Deck");
		//ArrayList<DuelistCard> fiendCards = new ArrayList<DuelistCard>();
		//fiendCards.add(new SummonedSkull());
		// more cards
		// more cards
		//fiendDeck.fillPoolCards(fiendCards);
		//fiendDeck.fillPoolCards(basicCards);
		
		// Machine Deck
		//StarterDeck machineDeck = starterDeckNamesMap.get("Machine Deck");
		//ArrayList<DuelistCard> machineCards = new ArrayList<DuelistCard>();
		//machineCards.add(new MachineKing());
		// more cards
		// more cards
		//machineDeck.fillPoolCards(machineCards);
		//machineDeck.fillPoolCards(basicCards);
		
		// Magnet Deck
		//StarterDeck magnetDeck = starterDeckNamesMap.get("Superheavy Deck");
		//ArrayList<DuelistCard> magnetCards = new ArrayList<DuelistCard>();
		//magnetCards.add(new SuperheavyScales());
		// more cards
		// more cards
		//magnetDeck.fillPoolCards(magnetCards);
		//magnetDeck.fillPoolCards(basicCards);

	}
	
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
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) 
					{ 
						if (debug)
						{
							logger.info("theDuelist:DuelistMod:initStartDeckArrays() ---> added " + c.originalName + " to " + ref.getSimpleName()); 
						}
						ref.getDeck().add(c); 
					}
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
			if (chosenDeckTag.equals(Tags.RANDOM_DECK_SMALL))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < randomDeckSmallSize; i++) { deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard());}
			}
			
			else if (chosenDeckTag.equals(Tags.RANDOM_DECK_BIG))
			{
				deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < randomDeckBigSize; i++) { deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard()); }
			}

			else 
			{
				if (debug) { logger.info("theDuelist:DuelistMod:setupStartDecksB() ---> " + refDeck.getSimpleName() + " size: " + refDeck.getDeck().size());  }
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
		for (DuelistCard c : myCards) { if (c.hasTag(Tags.STANDARD_DECK)) { for (int i = 0; i < c.startingDeckCopies; i++) { standardDeck.add(c); }}}
	}
	
	// DEBUG PRINT COMMANDS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void printTextForTranslation()
	{
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> START");
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card Names");
		for (DuelistCard c : myCards)
		{
			System.out.println(c.originalName);
			//logger.info(c.originalName);
		}
		
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card IDs");
		for (DuelistCard c : myCards)
		{
			System.out.println(";()" + c.getID() + ",;()");
			//logger.info(c.originalName);
		}
		
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card Descriptions");
		for (DuelistCard c : myCards)
		{
			System.out.println(c.rawDescription + " - " + DuelistCard.UPGRADE_DESCRIPTION);
			//logger.info(c.rawDescription);
		}
		
		/*
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Powers");
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
			PowerStrings powerString = CardCrawlGame.languagePack.getPowerStrings(DuelistMod.makeID(s));
			String[] powerDesc = powerString.DESCRIPTIONS;
			for (int i = 0; i < powerDesc.length; i++)
			{
				if (i == 0) { System.out.print(s + " - " + powerDesc[i]);}
				else { System.out.print(powerDesc[i]); }
			}
			logger.info("");
		}
		
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Relics");
		String[] relicList = new String[] {"MillenniumPuzzle", "MillenniumEye", "MillenniumRing", "MillenniumKey",
				"MillenniumRod", "MillenniumCoin", "StoneExxod", "GiftAnubis"};
		for (String s : relicList)
		{
			RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(DuelistMod.makeID(s));
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
		
		
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> END");
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
			if (c.hasTag(Tags.ALL))
			{
				all.add(c);
			}
			else if (c.hasTag(Tags.FULL))
			{
				full.add(c);
			}
			else if (c.hasTag(Tags.REDUCED))
			{
				reduced.add(c);
			}
			
			else if (c.hasTag(Tags.LIMITED))
			{
				limited.add(c);
			}
			
			else if (c.hasTag(Tags.CONSPIRE) || c.hasTag(Tags.REPLAYSPIRE))
			{
				mod.add(c);
			}
			
			else if (c.hasTag(Tags.RANDOMONLY))
			{
				random.add(c);
			}
			
			else
			{
				core.add(c);
			}
			
			if (c.hasTag(Tags.EXODIA))
			{
				exodia.add(c);
			}
			
			if (c.hasTag(Tags.TOON))
			{
				toon.add(c);
			}
			
			if (c.hasTag(Tags.DRAGON_DECK))
			{
				dragon.add(c);
			}
			
			if (c.hasTag(Tags.SPELLCASTER_DECK))
			{
				spellcaster.add(c);
			}
			
			if (c.hasTag(Tags.NATURE_DECK))
			{
				nature.add(c);
			}
			
			if (c.hasTag(Tags.CREATOR_DECK))
			{
				creator.add(c);
			}
			
			if (c.hasTag(Tags.TOON_DECK))
			{
				toonDeck.add(c);
			}
			
			if (c.hasTag(Tags.ORB_DECK))
			{
				orb.add(c);
			}
			
			if (c.hasTag(Tags.RESUMMON_DECK))
			{
				resummon.add(c);
			}
			
			if (c.hasTag(Tags.GENERATION_DECK))
			{
				generation.add(c);
			}
			
			if (c.hasTag(Tags.OJAMA_DECK))
			{
				ojama.add(c);
			}
			
			if (c.hasTag(Tags.HEAL_DECK))
			{
				healDeck.add(c);
			}
			
			if (c.hasTag(Tags.HEAL_DECK))
			{
				healDeck.add(c);
			}
			
			if (c.hasTag(Tags.INCREMENT_DECK))
			{
				incrementDeck.add(c);
			}
			
			if (c.hasTag(Tags.EXODIA_DECK))
			{
				exodiaDeck.add(c);
			}
			
			if (c.hasTag(Tags.MAGNET_DECK))
			{
				magnetDeck.add(c);
			}
			
			if (c.hasTag(Tags.MACHINE_DECK))
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
	
	public void outputSQLListsForMetrics() {
        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
        cards.addAll(myCards);

        System.out.println("Cards in cardlist: " + myCards.size());

        String cardstring = "INSERT INTO `meta_card_data` (`id`, `name`, `character_class`, `neutral`, `invalid`, `rarity`, `type`, `cost`, `description`, `ignore_before`, `updated_on`, `score`, `a0_total`, `a114_total`, `a15_total`, `pick_updated_on`, `a0_pick`, `a114_pick`, `a15_pick`, `a0_not_pick`, `a114_not_pick`, `a15_not_pick`, `up_updated_on`, `a0_up`, `a114_up`, `a15_up`, `a0_purchased`, `a114_purchased`, `a15_purchased`, `a0_purged`, `a114_purged`, `a15_purged`, `wr_updated_on`, `a0_wr`, `a114_wr`, `a15_wr`, `a0_floor`, `a114_floor`, `a15_floor`, `a0_floordetails`, `a114_floordetails`, `a15_floordetails`) VALUES ";
        cardstring = cardstring + "(0,'',1,0,0,'','','','',NULL,'0000-00-00 00:00:00',0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'','',''),";

        System.out.println(cardstring);
        
        int i = 0;
        for (AbstractCard c : cards) {
            i++;
            cardstring = String.format("(%d,'%s',1,0,0,'%s','%s','%d','%s',NULL,'0000-00-00 00:00:00',0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'','',''),", i, c.cardID, titleCase(c.rarity.name()), titleCase(c.type.name()), c.cost, c.rawDescription.replace("'","\'"));
            System.out.println(cardstring);
        }

        //cardstring = cardstring.substring(0, cardstring.length() - 1) + ";/*!40000 ALTER TABLE `meta_card_data` ENABLE KEYS */;";
        System.out.println(";/*!40000 ALTER TABLE `meta_card_data` ENABLE KEYS */;");

        //System.out.println(cardstring);

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");


        ArrayList<AbstractRelic> relics = new ArrayList<>();
        HashMap<String,AbstractRelic> sharedRelics = (HashMap<String,AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "sharedRelics");
        for (AbstractRelic relic : sharedRelics.values()) {
            relics.add(relic);
        }
        for (HashMap.Entry<AbstractCard.CardColor,HashMap<String,AbstractRelic>> entry : BaseMod.getAllCustomRelics().entrySet()) {
            for (AbstractRelic relic : entry.getValue().values()) {
                relics.add(relic);
                if (relic != null) { System.out.println("theDuelist:outputSQLListsForMetrics() ---> added " + relic.name + " to relics"); }
                else 
                {
                	System.out.println("theDuelist:outputSQLListsForMetrics() ---> relic not added because it was null!");
                }
            }
        }

        String relicstring = "INSERT INTO `meta_relic_data` (`id`, `name`, `invalid`, `character_class`, `description`, `rarity`, `event_id`, `ignore_before`) VALUES ";


        i = 0;
        for (AbstractRelic relic: relics) {
            i++;
            relicstring = relicstring + String.format("(%d,'%s',0,1,'%s','%s',0,'0000-00-00'),", i, relic.name, relic.description, relic.tier.name().toLowerCase());
        }
        System.out.println(relicstring);


    }
	
	 public static String titleCase(String text) {
	        if (text == null || text.isEmpty()) {
	            return text;
	        }
	     
	        StringBuilder converted = new StringBuilder();
	     
	        boolean convertNext = true;
	        for (char ch : text.toCharArray()) {
	            if (Character.isSpaceChar(ch)) {
	                convertNext = true;
	            } else if (convertNext) {
	                ch = Character.toTitleCase(ch);
	                convertNext = false;
	            } else {
	                ch = Character.toLowerCase(ch);
	            }
	            converted.append(ch);
	        }
	     
	        return converted.toString();
	    }

	
	// END METHODS

}// END DuelistMod
