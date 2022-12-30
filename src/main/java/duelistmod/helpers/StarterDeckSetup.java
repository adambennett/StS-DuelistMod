package duelistmod.helpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.helpers.poolhelpers.*;
import duelistmod.patches.*;
import duelistmod.variables.Tags;

public class StarterDeckSetup {

	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void refreshPoolOptions(String deckName)
	{
		//DuelistMod.archetypeCards.clear();
		DuelistMod.coloredCards.clear();
		DuelistMod.duelColorlessCards.clear();
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			s.getPoolCards().clear();
		}
		initStarterDeckPool(deckName);
	}
	
	public static void setupRandomDecks()
	{
		ArrayList<AbstractCard> newRandomCardList = new ArrayList<AbstractCard>();
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (!c.hasTag(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				boolean toonCard = c.hasTag(Tags.TOON_POOL);
				boolean ojamaCard = c.hasTag(Tags.OJAMA);
				boolean exodiaCard = c.hasTag(Tags.EXODIA);
				boolean creatorCard = (c instanceof TheCreator || c instanceof DarkCreator);
				
				if (toonCard)
				{
					if (!DuelistMod.toonBtnBool)
					{
						newRandomCardList.add(c.makeCopy());
					}
				}
				
				if (ojamaCard)
				{
					if (!DuelistMod.ojamaBtnBool)
					{
						newRandomCardList.add(c.makeCopy());
					}
				}
				
				if (exodiaCard)
				{
					if (!DuelistMod.exodiaBtnBool)
					{
						newRandomCardList.add(c.makeCopy());
					}
				}
				
				if (creatorCard)
				{
					if (!DuelistMod.creatorBtnBool)
					{
						newRandomCardList.add(c.makeCopy());
					}
				}
				
				if (!toonCard && !creatorCard && !ojamaCard && !exodiaCard)
				{
					newRandomCardList.add(c.makeCopy());
				}
			}
		}
		DuelistMod.cardsForRandomDecks.clear();
		DuelistMod.cardsForRandomDecks.addAll(newRandomCardList);
	}
	
	public static void initStarterDeckPool(String deckName)
	{
		// Standard -- Card Pool defined in poolhelper file
		// Fill Colorless with Basic Set
		if (DuelistMod.setIndex == 0) { deckFill(deckName); setupColorlessCards(deckName); }
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1) { deckFill(deckName); }
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2) { basicFill(deckName); }
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		// Fill Colorless with Basic Set
		else if (DuelistMod.setIndex == 3) { deckOneRandomFill(deckName); setupColorlessCards(deckName); }
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4) { deckOneRandomFill(deckName); }
		
		// Basic + 1 random -- 1 random set + Basic set
		// Fill Colorless with Basic Set
		else if (DuelistMod.setIndex == 5) { basicOneRandomFill(deckName); setupColorlessCards(deckName); }
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		// Fill Colorless with Basic Set
		else if (DuelistMod.setIndex == 6) { twoRandomDeckFill(deckName); setupColorlessCards(deckName); }
		
		// 2 random -- Card pools from two random poolhelper files (using AscendedOnePool here because it has all available pools in it's random list)
		else if (DuelistMod.setIndex == 7) { twoRandomFill(deckName); }
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8) { twoRandomDeckFill(deckName); }
		
		// All Cards - exception where pool is filled based on DuelistMod.myCards (and possibly the base game set)
		//else if (DuelistMod.setIndex == 9) {}
	}
	
	public static boolean isDeckArchetype()
	{
		if (getCurrentDeck().getIndex() > 0 && getCurrentDeck().getIndex() < 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static StarterDeck getCurrentDeck()
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == DuelistMod.deckIndex) { return d; }}
		return DuelistMod.starterDeckList.get(0);
	}

	public static CardTags findDeckTag(int deckIndex) 
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == deckIndex) { Util.log("StarterDeckSetup.findDeckTag(" + deckIndex + ") is about to return " + d.getDeckTag().toString()); return d.getDeckTag(); }}
		return null;
	}

	public static StarterDeck findDeck(int deckIndex) 
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == deckIndex) { return d; }}
		return null;
	}

	public static void initStartDeckArrays()
	{
		ArrayList<CardTags> deckTagList = StarterDeckSetup.getAllDeckTags();
		for (DuelistCard c : DuelistMod.myCards)
		{
			for (CardTags t : deckTagList)
			{
				if (c.hasTag(t) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
				{
					StarterDeck ref = DuelistMod.deckTagMap.get(t);
					int copyIndex = StarterDeck.getDeckCopiesMap().get(ref.getDeckTag());
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) 
					{
						ref.getDeck().add((DuelistCard) c.makeCopy()); 
					}
				}
			}
		}
	}

	public static ArrayList<CardTags> getAllDeckTags()
	{
		ArrayList<CardTags> deckTagList = new ArrayList<CardTags>();
		for (StarterDeck d : DuelistMod.starterDeckList) { deckTagList.add(d.getDeckTag()); }
		return deckTagList;
	}

	public static boolean hasTags(AbstractCard c, ArrayList<CardTags> tags)
	{
		boolean hasAnyTag = false;
		for (CardTags t : tags) { if (c.hasTag(t)) { hasAnyTag = true; }}
		return hasAnyTag;
	}
	
	private static boolean randomUpgradeDeckChecker(int lastRoll, boolean arcane)
	{
		if (arcane) 
		{ 
			int rollCheck = 1;
			int maxChance = 15;
			if (lastRoll > (maxChance - rollCheck+1)) { return false; }
			int roll = ThreadLocalRandom.current().nextInt(1, maxChance);	// otherwise roll 1-6
			if (roll > (rollCheck + lastRoll)) { return true; }				// if roll is bigger than the sum of the original low bound + number of upgrades, return true
			else { return false; }											// else, final fail
		}
		else
		{
			int rollCheck = 2;
			int maxChance = 10;
			if (lastRoll > (maxChance - rollCheck+1)) { return false; }		// if they cant roll a number that passes the check because they already upgraded a lot
			int roll = ThreadLocalRandom.current().nextInt(1, maxChance);	// otherwise roll 1-10
			if (roll > (rollCheck + lastRoll)) { return true; }				// if roll is bigger than the sum of the original low bound + number of upgrades, return true
			else { return false; }											// else, final fail
		}
	}
	
	public static void setupStartDecksB()
	{
		DuelistMod.chosenDeckTag = findDeckTag(DuelistMod.deckIndex);
		StarterDeck refDeck = null;
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getDeckTag().equals(DuelistMod.chosenDeckTag)) { refDeck = d; }}
		if (refDeck != null)
		{
			Util.log("setupStartDecksB set chosenDeckTag to " + DuelistMod.chosenDeckTag.toString());
			if (DuelistMod.chosenDeckTag.equals(Tags.RANDOM_DECK_SMALL))
			{
				setupRandomDecks();
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < DuelistMod.randomDeckSmallSize; i++) { DuelistMod.deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCardForRandomDecks()); }
				Util.log("Random Deck (Small) initialized");
			}
			
			else if (DuelistMod.chosenDeckTag.equals(Tags.RANDOM_DECK_BIG))
			{				
				setupRandomDecks();
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < DuelistMod.randomDeckBigSize; i++) { DuelistMod.deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCardForRandomDecks()); }
				Util.log("Random Deck (Big) initialized");
			}
			
			else if (DuelistMod.chosenDeckTag.equals(Tags.RANDOM_DECK_UPGRADE))
			{
				setupRandomDecks();
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				int lastRoll = 0;
				for (int i = 0; i < DuelistMod.randomDeckSmallSize; i++) 
				{ 
					DuelistCard nextRand = (DuelistCard)DuelistCard.returnTrulyRandomDuelistCardForRandomDecks();
					nextRand.upgrade();
					while (randomUpgradeDeckChecker(lastRoll, nextRand.hasTag(Tags.ARCANE)) && nextRand.canUpgrade()) { nextRand.upgrade(); lastRoll++; }
					lastRoll = 0;
					DuelistMod.deckToStartWith.add(nextRand); 
				}
				Util.log("Upgrade Deck initialized");
			}
			else 
			{
				Util.log("theDuelist:DuelistMod:setupStartDecksB() ---> " + refDeck.getSimpleName() + " size: " + refDeck.getDeck().size());
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				DuelistMod.deckToStartWith.addAll(refDeck.getDeck());
			}
		}
		
		else
		{
			StarterDeckSetup.initStandardDeck();
			DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
			DuelistMod.deckToStartWith.addAll(DuelistMod.standardDeck);
			Util.log("Your starting deck was null for some reason so your deck was filled with the Standard Deck starting cards instead of whatever you chose");
		}
		
		if (DuelistMod.deckToStartWith.size() < 1)
		{
			StarterDeckSetup.initStandardDeck();
			DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
			DuelistMod.deckToStartWith.addAll(DuelistMod.standardDeck);
			Util.log("You're playing with a deck that is not setup properly");
		}
	}

	public static void initStandardDeck()
	{
		DuelistMod.standardDeck = new ArrayList<DuelistCard>();
		for (DuelistCard c : DuelistMod.myCards) { if (c.hasTag(Tags.STANDARD_DECK)) { for (int i = 0; i < c.standardDeckCopies; i++) { DuelistMod.standardDeck.add((DuelistCard) c.makeCopy()); }}}
	}

	public static void resetStarterDeck()
	{		
		setupStartDecksB();
		if (DuelistMod.deckToStartWith.size() > 0)
		{
			CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
			for (AbstractCard c : DuelistMod.deckToStartWith) { newStartGroup.addToRandomSpot(c);}
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.initializeDeck(newStartGroup);
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.sortAlphabetically(true);
		}
	}
	
	public static void setupColorlessCards(String deckName)
	{
		DuelistMod.duelColorlessCards.clear();
		if (DuelistMod.smallBasicSet) { DuelistMod.duelColorlessCards.addAll(BasicPool.smallBasic(deckName)); }
		else { DuelistMod.duelColorlessCards.addAll(BasicPool.fullBasic(deckName)); }
	}

	public static void deckFill(String deckName)
	{
		if (deckName.equals("Aqua Deck")) { AquaPool.deck(); }
		else if (deckName.equals("Ascended I")) { AscendedOnePool.deck(); }
		else if (deckName.equals("Ascended II")) { AscendedTwoPool.deck(); }
		else if (deckName.equals("Ascended III")) { AscendedThreePool.deck(); }
		else if (deckName.equals("Pharaoh I")) { PharaohPool.deck(1);}
		else if (deckName.equals("Pharaoh II")) { PharaohPool.deck(2); }
		else if (deckName.equals("Pharaoh III")) { PharaohPool.deck(3);}
		else if (deckName.equals("Pharaoh IV")) { PharaohPool.deck(4);}
		else if (deckName.equals("Pharaoh V")) { PharaohPool.deck(5);}
		else if (deckName.equals("Creator Deck")) { CreatorPool.deck();}
		else if (deckName.equals("Dragon Deck")) { DragonPool.deck();}
		else if (deckName.equals("Exodia Deck")) { ExodiaPool.deck();}
		else if (deckName.equals("Fiend Deck")) { FiendPool.deck();}
		else if (deckName.equals("Giant Deck")) { GiantPool.deck();}
		else if (deckName.equals("Increment Deck")) { IncrementPool.deck();}
		else if (deckName.equals("Insect Deck")) { InsectPool.deck();}
		else if (deckName.equals("Machine Deck")) { MachinePool.deck();}
		else if (deckName.equals("Megatype Deck")) { MegatypePool.deck();}
		else if (deckName.equals("Naturia Deck")) { NaturiaPool.deck();}
		else if (deckName.equals("Ojama Deck")) { OjamaPool.deck();}
		else if (deckName.equals("Plant Deck")) { PlantPool.deck();}
		else if (deckName.equals("Predaplant Deck")) { PredaplantPool.deck();}
		else if (deckName.equals("Spellcaster Deck")) { SpellcasterPool.deck();}
		else if (deckName.equals("Standard Deck")) { StandardPool.deck();}
		else if (deckName.equals("Toon Deck")) { ToonPool.deck();}
		else if (deckName.equals("Warrior Deck")) { WarriorPool.deck();}
		else if (deckName.equals("Zombie Deck")) { ZombiePool.deck();}
		else if (deckName.equals("Random Deck (Small)")) { RandomSmallPool.deck();}
		else if (deckName.equals("Random Deck (Big)")) { RandomBigPool.deck();}
		else if (deckName.equals("Upgrade Deck")) { RandomUpgradePool.deck();}
		else if (deckName.equals("Metronome Deck")) { RandomMetronomePool.deck();}
	}
	
	public static void basicFill(String deckName)
	{
		if (deckName.equals("Aqua Deck")) { AquaPool.basic(); }
		else if (deckName.equals("Ascended I")) { AscendedOnePool.basic(); }
		else if (deckName.equals("Ascended II")) { AscendedTwoPool.basic(); }
		else if (deckName.equals("Ascended III")) { AscendedThreePool.basic(); }
		else if (deckName.equals("Pharaoh I")) { PharaohPool.basic();}
		else if (deckName.equals("Pharaoh II")) { PharaohPool.basic(); }
		else if (deckName.equals("Pharaoh III")) { PharaohPool.basic();}
		else if (deckName.equals("Pharaoh IV")) { PharaohPool.basic();}
		else if (deckName.equals("Pharaoh V")) { PharaohPool.basic();}
		else if (deckName.equals("Creator Deck")) { CreatorPool.basic();}
		else if (deckName.equals("Dragon Deck")) { DragonPool.basic();}
		else if (deckName.equals("Exodia Deck")) { ExodiaPool.basic();}
		else if (deckName.equals("Fiend Deck")) { FiendPool.basic();}
		else if (deckName.equals("Giant Deck")) { GiantPool.basic();}
		else if (deckName.equals("Increment Deck")) { IncrementPool.basic();}
		else if (deckName.equals("Insect Deck")) { InsectPool.basic();}
		else if (deckName.equals("Machine Deck")) { MachinePool.basic();}
		else if (deckName.equals("Megatype Deck")) { MegatypePool.basic();}
		else if (deckName.equals("Naturia Deck")) { NaturiaPool.basic();}
		else if (deckName.equals("Ojama Deck")) { OjamaPool.basic();}
		else if (deckName.equals("Plant Deck")) { PlantPool.basic();}
		else if (deckName.equals("Predaplant Deck")) { PredaplantPool.basic();}
		else if (deckName.equals("Spellcaster Deck")) { SpellcasterPool.basic();}
		else if (deckName.equals("Standard Deck")) { StandardPool.basic();}
		else if (deckName.equals("Toon Deck")) { ToonPool.basic();}
		else if (deckName.equals("Warrior Deck")) { WarriorPool.basic();}
		else if (deckName.equals("Zombie Deck")) { ZombiePool.basic();}
		else if (deckName.equals("Random Deck (Small)")) { RandomSmallPool.basic();}
		else if (deckName.equals("Random Deck (Big)")) { RandomBigPool.basic();}
		else if (deckName.equals("Upgrade Deck")) { RandomUpgradePool.basic();}
		else if (deckName.equals("Metronome Deck")) { RandomMetronomePool.basic();}
	}

	public static void deckOneRandomFill(String deckName)
	{
		if (deckName.equals("Aqua Deck")) { AquaPool.deck(); AquaPool.oneRandom();}
		else if (deckName.equals("Ascended I")) { AscendedOnePool.deck(); AscendedOnePool.oneRandom();}
		else if (deckName.equals("Ascended II")) { AscendedTwoPool.deck(); AscendedTwoPool.oneRandom();}
		else if (deckName.equals("Ascended III")) { AscendedThreePool.deck(); AscendedThreePool.oneRandom();}
		else if (deckName.equals("Pharaoh I")) { PharaohPool.deck(1);PharaohPool.oneRandom(); }
		else if (deckName.equals("Pharaoh II")) { PharaohPool.deck(2); PharaohPool.oneRandom();}
		else if (deckName.equals("Pharaoh III")) { PharaohPool.deck(3);PharaohPool.oneRandom();}
		else if (deckName.equals("Pharaoh IV")) { PharaohPool.deck(4);PharaohPool.oneRandom();}
		else if (deckName.equals("Pharaoh V")) { PharaohPool.deck(5);PharaohPool.oneRandom();}
		else if (deckName.equals("Creator Deck")) { CreatorPool.deck();CreatorPool.oneRandom();}
		else if (deckName.equals("Dragon Deck")) { DragonPool.deck();DragonPool.oneRandom();}
		else if (deckName.equals("Exodia Deck")) { ExodiaPool.deck();ExodiaPool.oneRandom();}
		else if (deckName.equals("Fiend Deck")) { FiendPool.deck();FiendPool.oneRandom();}
		else if (deckName.equals("Giant Deck")) { GiantPool.deck();GiantPool.oneRandom();}
		else if (deckName.equals("Increment Deck")) { IncrementPool.deck();IncrementPool.oneRandom();}
		else if (deckName.equals("Insect Deck")) { InsectPool.deck();InsectPool.oneRandom();}
		else if (deckName.equals("Machine Deck")) { MachinePool.deck();MachinePool.oneRandom();}
		else if (deckName.equals("Megatype Deck")) { MegatypePool.deck();MegatypePool.oneRandom();}
		else if (deckName.equals("Naturia Deck")) { NaturiaPool.deck();NaturiaPool.oneRandom();}
		else if (deckName.equals("Ojama Deck")) { OjamaPool.deck();OjamaPool.oneRandom();}
		else if (deckName.equals("Plant Deck")) { PlantPool.deck();PlantPool.oneRandom();}
		else if (deckName.equals("Predaplant Deck")) { PredaplantPool.deck();PredaplantPool.oneRandom();}
		else if (deckName.equals("Spellcaster Deck")) { SpellcasterPool.deck();SpellcasterPool.oneRandom();}
		else if (deckName.equals("Standard Deck")) { StandardPool.deck();StandardPool.oneRandom();}
		else if (deckName.equals("Toon Deck")) { ToonPool.deck(); ToonPool.oneRandom();}
		else if (deckName.equals("Warrior Deck")) { WarriorPool.deck();WarriorPool.oneRandom();}
		else if (deckName.equals("Zombie Deck")) { ZombiePool.deck();ZombiePool.oneRandom();}
		else if (deckName.equals("Random Deck (Small)")) { RandomSmallPool.deck();RandomSmallPool.oneRandom();}
		else if (deckName.equals("Random Deck (Big)")) { RandomBigPool.deck();RandomBigPool.oneRandom();}
		else if (deckName.equals("Upgrade Deck")) { RandomUpgradePool.deck();RandomUpgradePool.oneRandom();}
		else if (deckName.equals("Metronome Deck")) { RandomMetronomePool.deck();RandomMetronomePool.oneRandom();}
	}

	public static void basicOneRandomFill(String deckName)
	{
		ArrayList<AbstractCard> poolCards = new ArrayList<AbstractCard>();
		poolCards.addAll(GlobalPoolHelper.oneRandom());
		for (StarterDeck s : DuelistMod.starterDeckList) { if (s.getSimpleName().equals(deckName)) { s.fillPoolCards(poolCards); }}
	}
	
	public static void twoRandomFill(String deckName)
	{
		ArrayList<AbstractCard> poolCards = new ArrayList<AbstractCard>();
		poolCards.addAll(GlobalPoolHelper.twoRandom());
		for (StarterDeck s : DuelistMod.starterDeckList) { if (s.getSimpleName().equals(deckName)) { s.fillPoolCards(poolCards); }}
	}
	
	public static void twoRandomDeckFill(String deckName)
	{
		if (deckName.equals("Aqua Deck")) { AquaPool.deck(); AquaPool.twoRandom();}
		else if (deckName.equals("Ascended I")) { AscendedOnePool.deck(); AscendedOnePool.twoRandom();}
		else if (deckName.equals("Ascended II")) { AscendedTwoPool.deck(); AscendedTwoPool.twoRandom();}
		else if (deckName.equals("Ascended III")) { AscendedThreePool.deck(); AscendedThreePool.twoRandom();}
		else if (deckName.equals("Pharaoh I")) { PharaohPool.deck(1);PharaohPool.twoRandom(); }
		else if (deckName.equals("Pharaoh II")) { PharaohPool.deck(2); PharaohPool.twoRandom();}
		else if (deckName.equals("Pharaoh III")) { PharaohPool.deck(3);PharaohPool.twoRandom();}
		else if (deckName.equals("Pharaoh IV")) { PharaohPool.deck(4);PharaohPool.twoRandom();}
		else if (deckName.equals("Pharaoh V")) { PharaohPool.deck(5);PharaohPool.twoRandom();}
		else if (deckName.equals("Creator Deck")) { CreatorPool.deck();CreatorPool.twoRandom();}
		else if (deckName.equals("Dragon Deck")) { DragonPool.deck();DragonPool.twoRandom();}
		else if (deckName.equals("Exodia Deck")) { ExodiaPool.deck();ExodiaPool.twoRandom();}
		else if (deckName.equals("Fiend Deck")) { FiendPool.deck();FiendPool.twoRandom();}
		else if (deckName.equals("Giant Deck")) { GiantPool.deck();GiantPool.twoRandom();}
		else if (deckName.equals("Increment Deck")) { IncrementPool.deck();IncrementPool.twoRandom();}
		else if (deckName.equals("Insect Deck")) { InsectPool.deck();InsectPool.twoRandom();}
		else if (deckName.equals("Machine Deck")) { MachinePool.deck();MachinePool.twoRandom();}
		else if (deckName.equals("Megatype Deck")) { MegatypePool.deck();MegatypePool.twoRandom();}
		else if (deckName.equals("Naturia Deck")) { NaturiaPool.deck();NaturiaPool.twoRandom();}
		else if (deckName.equals("Ojama Deck")) { OjamaPool.deck();OjamaPool.twoRandom();}
		else if (deckName.equals("Plant Deck")) { PlantPool.deck();PlantPool.twoRandom();}
		else if (deckName.equals("Predaplant Deck")) { PredaplantPool.deck();PredaplantPool.twoRandom();}
		else if (deckName.equals("Spellcaster Deck")) { SpellcasterPool.deck();SpellcasterPool.twoRandom();}
		else if (deckName.equals("Standard Deck")) { StandardPool.deck();StandardPool.twoRandom();}
		else if (deckName.equals("Toon Deck")) { ToonPool.deck(); ToonPool.twoRandom();}
		else if (deckName.equals("Warrior Deck")) { WarriorPool.deck();WarriorPool.twoRandom();}
		else if (deckName.equals("Zombie Deck")) { ZombiePool.deck();ZombiePool.twoRandom();}
		else if (deckName.equals("Random Deck (Small)")) { RandomSmallPool.deck();RandomSmallPool.twoRandom();}
		else if (deckName.equals("Random Deck (Big)")) { RandomBigPool.deck();RandomBigPool.twoRandom();}
		else if (deckName.equals("Upgrade Deck")) { RandomUpgradePool.deck();RandomUpgradePool.twoRandom();}
		else if (deckName.equals("Metronome Deck")) { RandomMetronomePool.deck();RandomMetronomePool.twoRandom();}
	}
	
	
}
