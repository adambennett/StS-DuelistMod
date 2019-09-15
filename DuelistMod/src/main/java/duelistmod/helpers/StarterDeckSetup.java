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
	public static void refreshPoolOptions()
	{
		DuelistMod.archetypeCards.clear();
		DuelistMod.coloredCards.clear();
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			s.getArchetypeCards().clear();
			s.getPoolCards().clear();
		}
		initStarterDeckPool();
	}
	
	public static void setupRandomDecks()
	{
		ArrayList<AbstractCard> newRandomCardList = new ArrayList<AbstractCard>();
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (!c.hasTag(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL))
			{
				boolean toonCard = c.hasTag(Tags.TOON);
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
	
	public static void initStarterDeckPool()
	{
		if (StarterDeckSetup.getCurrentDeck().getIndex() > 18 && StarterDeckSetup.getCurrentDeck().getIndex() < 27) { BasicPool.ascendedBasics(); }
		else if (DuelistMod.smallBasicSet) { BasicPool.smallBasic(); Util.log("Reduced Basic Set"); }
		else { BasicPool.fullBasic(); Util.log("Full Basic Set"); }
		
		// Standard -- Card Pool defined in poolhelper file
		if (DuelistMod.setIndex == 0) { standardFill(); }
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1) { deckFill(); }
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2) { basicFill(); }
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		else if (DuelistMod.setIndex == 3) { deckBasicOneRandomFill(); }
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4) { deckOneRandomFill(); }
		
		// Basic + 1 random -- 1 random set + Basic set
		else if (DuelistMod.setIndex == 5) { basicOneRandomFill(); }
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		else if (DuelistMod.setIndex == 6) { basicTwoRandomDeckFill(); }
		
		// 2 random -- Card pools from two random poolhelper files (using AscendedOnePool here because it has all available pools in it's random list)
		else if (DuelistMod.setIndex == 7) { twoRandomFill(); }
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8) { twoRandomDeckFill(); }
		
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
				if (c.hasTag(t))
				{
					StarterDeck ref = DuelistMod.deckTagMap.get(t);
					int copyIndex = StarterDeck.getDeckCopiesMap().get(ref.getDeckTag());
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) 
					{ 
						if (DuelistMod.debug)
						{
							DuelistMod.logger.info("theDuelist:DuelistMod:initStartDeckArrays() ---> added " + c.originalName + " to " + ref.getSimpleName()); 
						}
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
	
	public static void standardFill()
	{
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		PlantPool.deck();
		PredaplantPool.deck();
		SpellcasterPool.deck();
		StandardPool.deck();
		ToonPool.deck();
		WarriorPool.deck();
		ZombiePool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
		
		AquaPool.basic();
		AscendedOnePool.basic();
		AscendedTwoPool.basic();
		AscendedThreePool.basic();
		CreatorPool.basic();
		DragonPool.basic();
		ExodiaPool.basic();
		FiendPool.basic();
		GiantPool.basic();
		IncrementPool.basic();
		InsectPool.basic();
		MachinePool.basic();
		MegatypePool.basic();
		NaturePool.basic();
		OjamaPool.basic();
		PharaohPool.basic();
		PlantPool.basic();
		PredaplantPool.basic();
		SpellcasterPool.basic();
		StandardPool.basic();
		ToonPool.basic();
		WarriorPool.basic();
		ZombiePool.basic();
		RandomSmallPool.basic();
		RandomBigPool.basic();
		RandomUpgradePool.basic();
		RandomMetronomePool.basic();
	}

	public static void deckFill()
	{
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		PlantPool.deck();
		PredaplantPool.deck();
		SpellcasterPool.deck();
		StandardPool.deck();
		ToonPool.deck();
		WarriorPool.deck();
		ZombiePool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
	}
	
	public static void basicFill()
	{
		AquaPool.basic();
		AscendedOnePool.basic();
		AscendedTwoPool.basic();
		AscendedThreePool.basic();
		CreatorPool.basic();
		DragonPool.basic();
		ExodiaPool.basic();
		FiendPool.basic();
		GiantPool.basic();
		IncrementPool.basic();
		InsectPool.basic();
		MachinePool.basic();
		MegatypePool.basic();
		NaturePool.basic();
		OjamaPool.basic();
		PharaohPool.basic();
		PlantPool.basic();
		PredaplantPool.basic();
		SpellcasterPool.basic();
		StandardPool.basic();
		ToonPool.basic();
		WarriorPool.basic();
		ZombiePool.basic();
		RandomSmallPool.basic();
		RandomBigPool.basic();
		RandomUpgradePool.basic();
		RandomMetronomePool.basic();
	}

	public static void deckBasicOneRandomFill()
	{
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		PlantPool.deck();
		PredaplantPool.deck();
		SpellcasterPool.deck();
		StandardPool.deck();
		ToonPool.deck();
		WarriorPool.deck();
		ZombiePool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
		
		AquaPool.basic();
		AscendedOnePool.basic();
		AscendedTwoPool.basic();
		AscendedThreePool.basic();
		CreatorPool.basic();
		DragonPool.basic();
		ExodiaPool.basic();
		FiendPool.basic();
		GiantPool.basic();
		IncrementPool.basic();
		InsectPool.basic();
		MachinePool.basic();
		MegatypePool.basic();
		NaturePool.basic();
		OjamaPool.basic();
		PharaohPool.basic();
		PlantPool.basic();
		PredaplantPool.basic();
		SpellcasterPool.basic();
		StandardPool.basic();
		ToonPool.basic();
		WarriorPool.basic();
		ZombiePool.basic();
		RandomSmallPool.basic();
		RandomBigPool.basic();
		RandomUpgradePool.basic();
		RandomMetronomePool.basic();
		
		AquaPool.oneRandom();
		AscendedOnePool.oneRandom();
		AscendedTwoPool.oneRandom();
		AscendedThreePool.oneRandom();
		CreatorPool.oneRandom();
		DragonPool.oneRandom();
		ExodiaPool.oneRandom();
		FiendPool.oneRandom();
		GiantPool.oneRandom();
		IncrementPool.oneRandom();
		InsectPool.oneRandom();
		MachinePool.oneRandom();
		MegatypePool.oneRandom();
		NaturePool.oneRandom();
		OjamaPool.oneRandom();
		PharaohPool.oneRandom();
		PlantPool.oneRandom();
		PredaplantPool.oneRandom();
		SpellcasterPool.oneRandom();
		StandardPool.oneRandom();
		ToonPool.oneRandom();
		WarriorPool.oneRandom();
		ZombiePool.oneRandom();
		RandomSmallPool.oneRandom();
		RandomBigPool.oneRandom();
		RandomUpgradePool.oneRandom();
		RandomMetronomePool.oneRandom();
	}

	public static void deckOneRandomFill()
	{
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		PlantPool.deck();
		PredaplantPool.deck();
		SpellcasterPool.deck();
		StandardPool.deck();
		ToonPool.deck();
		WarriorPool.deck();
		ZombiePool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
		AquaPool.oneRandom();
		AscendedOnePool.oneRandom();
		AscendedTwoPool.oneRandom();
		AscendedThreePool.oneRandom();
		CreatorPool.oneRandom();
		DragonPool.oneRandom();
		ExodiaPool.oneRandom();
		FiendPool.oneRandom();
		GiantPool.oneRandom();
		IncrementPool.oneRandom();
		InsectPool.oneRandom();
		MachinePool.oneRandom();
		MegatypePool.oneRandom();
		NaturePool.oneRandom();
		OjamaPool.oneRandom();
		PharaohPool.oneRandom();
		PlantPool.oneRandom();
		PredaplantPool.oneRandom();
		SpellcasterPool.oneRandom();
		StandardPool.oneRandom();
		ToonPool.oneRandom();
		WarriorPool.oneRandom();
		ZombiePool.oneRandom();
		RandomSmallPool.oneRandom();
		RandomBigPool.oneRandom();
		RandomUpgradePool.oneRandom();
		RandomMetronomePool.oneRandom();
	}

	public static void basicOneRandomFill()
	{
		AquaPool.basic();
		AscendedOnePool.basic();
		AscendedTwoPool.basic();
		AscendedThreePool.basic();
		CreatorPool.basic();
		DragonPool.basic();
		ExodiaPool.basic();
		FiendPool.basic();
		GiantPool.basic();
		IncrementPool.basic();
		InsectPool.basic();
		MachinePool.basic();
		MegatypePool.basic();
		NaturePool.basic();
		OjamaPool.basic();
		PharaohPool.basic();
		PlantPool.basic();
		PredaplantPool.basic();
		SpellcasterPool.basic();
		StandardPool.basic();
		ToonPool.basic();
		WarriorPool.basic();
		ZombiePool.basic();
		RandomSmallPool.basic();
		RandomBigPool.basic();
		RandomUpgradePool.basic();
		RandomMetronomePool.basic();
		ArrayList<AbstractCard> poolCards = new ArrayList<AbstractCard>();
		poolCards.addAll(BasicPool.oneRandom());
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			s.fillPoolCards(poolCards);
		}
	}
	
	public static void basicTwoRandomDeckFill()
	{
		AquaPool.basic();
		AscendedOnePool.basic();
		AscendedTwoPool.basic();
		AscendedThreePool.basic();
		CreatorPool.basic();
		DragonPool.basic();
		ExodiaPool.basic();
		FiendPool.basic();
		GiantPool.basic();
		IncrementPool.basic();
		InsectPool.basic();
		MachinePool.basic();
		MegatypePool.basic();
		NaturePool.basic();
		OjamaPool.basic();
		PharaohPool.basic();
		PlantPool.basic();
		PredaplantPool.basic();
		SpellcasterPool.basic();
		StandardPool.basic();
		ToonPool.basic();
		WarriorPool.basic();
		ZombiePool.basic();
		RandomSmallPool.basic();
		RandomBigPool.basic();
		RandomUpgradePool.basic();
		RandomMetronomePool.basic();
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		PlantPool.deck();
		PredaplantPool.deck();
		SpellcasterPool.deck();
		StandardPool.deck();
		ToonPool.deck();
		WarriorPool.deck();
		ZombiePool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
		AscendedOnePool.twoRandom();
		AscendedTwoPool.twoRandom();
		AscendedThreePool.twoRandom();
		CreatorPool.twoRandom();
		DragonPool.twoRandom();
		ExodiaPool.twoRandom();
		FiendPool.twoRandom();
		GiantPool.twoRandom();
		IncrementPool.twoRandom();
		InsectPool.twoRandom();
		MachinePool.twoRandom();
		MegatypePool.twoRandom();
		NaturePool.twoRandom();
		OjamaPool.twoRandom();
		PharaohPool.twoRandom();
		PlantPool.twoRandom();
		PredaplantPool.twoRandom();
		SpellcasterPool.twoRandom();
		StandardPool.twoRandom();
		ToonPool.twoRandom();
		WarriorPool.twoRandom();
		ZombiePool.twoRandom();
		RandomSmallPool.twoRandom();
		RandomBigPool.twoRandom();
		RandomUpgradePool.twoRandom();
		RandomMetronomePool.twoRandom();
	}
	
	public static void twoRandomFill()
	{
		ArrayList<AbstractCard> poolCards = new ArrayList<AbstractCard>();
		poolCards.addAll(BasicPool.twoRandom());
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			s.fillPoolCards(poolCards);
		}
	}
	
	public static void twoRandomDeckFill()
	{
		AscendedOnePool.twoRandom();
		AscendedTwoPool.twoRandom();
		AscendedThreePool.twoRandom();
		CreatorPool.twoRandom();
		DragonPool.twoRandom();
		ExodiaPool.twoRandom();
		FiendPool.twoRandom();
		GiantPool.twoRandom();
		IncrementPool.twoRandom();
		InsectPool.twoRandom();
		MachinePool.twoRandom();
		MegatypePool.twoRandom();
		NaturePool.twoRandom();
		OjamaPool.twoRandom();
		PharaohPool.twoRandom();
		PlantPool.twoRandom();
		PredaplantPool.twoRandom();
		SpellcasterPool.twoRandom();
		StandardPool.twoRandom();
		ToonPool.twoRandom();
		WarriorPool.twoRandom();
		ZombiePool.twoRandom();
		RandomSmallPool.twoRandom();
		RandomBigPool.twoRandom();
		RandomUpgradePool.twoRandom();
		RandomMetronomePool.twoRandom();
		AquaPool.deck();
		AscendedOnePool.deck();
		AscendedTwoPool.deck();
		AscendedThreePool.deck();
		CreatorPool.deck();
		DragonPool.deck();
		ExodiaPool.deck();
		FiendPool.deck();
		GiantPool.deck();
		IncrementPool.deck();
		InsectPool.deck();
		MachinePool.deck();
		MegatypePool.deck();
		NaturePool.deck();
		OjamaPool.deck();
		PharaohPool.deck();
		RandomSmallPool.deck();
		RandomBigPool.deck();
		RandomUpgradePool.deck();
		RandomMetronomePool.deck();
	}
	
	
}
