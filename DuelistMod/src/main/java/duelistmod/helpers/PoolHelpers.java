package duelistmod.helpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class PoolHelpers 
{

	
	public static void newFillColored()
	{
		// before calling this we try to load saved array for colored cards
		// if save exists, fill colored cards with saved cards
		// if the save does not exist, fill colored cards with the below code
		
		// refresh all the cards in all pools to match any "remove all of.." options the player may have selected since startup
		StarterDeckSetup.refreshPoolOptions();
		
		// this if block makes sure the pool is filled with the deck you actually selected
		if (StarterDeckSetup.getCurrentDeck().getIndex() != DuelistMod.normalSelectDeck && DuelistMod.normalSelectDeck > -1)
		{
			DuelistMod.deckIndex = DuelistMod.normalSelectDeck;
		}
		
		// All Cards - exception where pool is filled based on DuelistMod.myCards (and possibly the base game set)
		if (DuelistMod.setIndex == 9)
		{
			allCardsFillHelper();
			Util.log("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 9");
		}
		
		else
		{
			ArrayList<String> addedToPool = new ArrayList<String>();
			for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !addedToPool.contains(c.name)) { addedToPool.add(c.name); DuelistMod.coloredCards.add(c); }}
			Util.log("Card Fill was NOT 'All Cards' so we are filling the pool specifically based on your fill type");
		}
		
		// POWER CHECK
		boolean hasPower = false;
		int powerCounter = 0;
		int powersToFind = 5;
		for (AbstractCard c : DuelistMod.coloredCards) { if (c.type.equals(CardType.POWER)) { powerCounter++; }}
		if (powerCounter >= powersToFind) { hasPower = true; }
		if (!hasPower)
		{
			Util.log("Power check triggered!");
		}
			/*DuelistMod.powerCheckIncCheck++;
			for (int i = 0; i < 5; i++)
			{
				DuelistCard poolCard = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.myCards.size()));
				while (!poolCard.type.equals(CardType.POWER) || poolCard.rarity.equals(CardRarity.BASIC) || poolCard.rarity.equals(CardRarity.SPECIAL) || poolCard.hasTag(Tags.TOON)) 
				{ 
					poolCard = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.myCards.size())); 
				}
				DuelistMod.coloredCards.add(poolCard);
			}
		
			try 
			{
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
	        	config.setInt(DuelistMod.PROP_POWER_CHECK_CHECK, DuelistMod.powerCheckIncCheck);   
				config.save();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}*/
		
		ArrayList<AbstractCard> nonDupes = new ArrayList<AbstractCard>();
		ArrayList<String> nonDupeNames = new ArrayList<String>();
		for (AbstractCard c : DuelistMod.coloredCards)
		{
			if (nonDupeNames.contains(c.name))
			{
				Util.log("Found duplicate of " + c.name + " so it got removed");
			}
			else
			{
				nonDupes.add(c.makeCopy());
				nonDupeNames.add(c.name);
			}
		}
		
		DuelistMod.coloredCards.clear();
		DuelistMod.coloredCards.addAll(nonDupes);
		
		// EXTRA PROCESSING
		if (DuelistMod.debug) { int counter = 1; for (AbstractCard c : DuelistMod.coloredCards) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> coloredCards (" + counter + "): " + c.originalName); counter++; }}
		if (DuelistMod.debug) { for (AbstractCard c : DuelistMod.coloredCards) { if (c.rarity.equals(CardRarity.SPECIAL) || c.rarity.equals(CardRarity.BASIC)) { Util.log("Found bad card inside colored cards after filling. Card Name: " + c.originalName); }}}
		DuelistMod.rareCardInPool = new ArrayList<AbstractCard>(); // For CardLibPatch (fills rare cards properly from pool to return rare cards from)
		for (AbstractCard c : DuelistMod.coloredCards) { if (c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.TOKEN)) { DuelistMod.rareCardInPool.add(c); }}
		// END EXTRA PROCESSING
	}

	public static void allCardsFillHelper()
	{
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
			{
				DuelistMod.coloredCards.add(c);
				DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
			}
		}
		
		if (DuelistMod.baseGameCards)
		{
			 DuelistMod.coloredCards.addAll(BaseGameHelper.getAllBaseGameCards());
		}
	}
	
	public static int randomArchetypeHelper(int deckIndex, int badIndex)
	{
		int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
		if (DuelistMod.archRoll1 == -1) { DuelistMod.archRoll1 = archRoll;}
		else { archRoll = DuelistMod.archRoll1; }
		while (archRoll == deckIndex || archRoll == 4 || archRoll == badIndex) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
		StarterDeck ref = StarterDeckSetup.findDeck(archRoll);
		if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was " + DuelistMod.setIndex + ", random archetype deck was: " + ref.getSimpleName() + ", current deck is: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
		for (int i = 0; i < DuelistMod.extraCardsFromRandomArch; i++)
		{			
			AbstractCard c = ref.getArchetypeCards().get(ThreadLocalRandom.current().nextInt(0, ref.getArchetypeCards().size())); { c = ref.getArchetypeCards().get(ThreadLocalRandom.current().nextInt(0, ref.getArchetypeCards().size())); }
			DuelistMod.coloredCards.add(c);	
		}		
		return archRoll;
	}
	
	public static void archetypeRehelper(int indexToAddFrom)
	{
		StarterDeck ref = StarterDeckSetup.findDeck(indexToAddFrom);
		if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was " + DuelistMod.setIndex + ", random archetype deck was: " + ref.getSimpleName() + ", current deck is: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
		for (int i = 0; i < DuelistMod.extraCardsFromRandomArch; i++)
		{
				AbstractCard c = ref.getArchetypeCards().get(ThreadLocalRandom.current().nextInt(0, ref.getArchetypeCards().size()));
				while (c.rarity.equals(CardRarity.BASIC) || c.rarity.equals(CardRarity.SPECIAL)) { c = ref.getArchetypeCards().get(ThreadLocalRandom.current().nextInt(0, ref.getArchetypeCards().size())); }
				DuelistMod.coloredCards.add(c);	
		}		
	}
	
	public static void coloredCardsHadCards()
	{
		if (DuelistMod.debug)
		{
			{
				DuelistMod.logger.info("theDuelist:DuelistMod:coloredCardsHadCards() ---> coloredCards size: " + DuelistMod.coloredCards.size() + ", and every card in there: "); 
				for (int i = 0; i < DuelistMod.coloredCards.size(); i++)
				{
					DuelistMod.logger.info("(" + i + "): " + DuelistMod.coloredCards.get(i).originalName);
				}
			}
		}
		if (DuelistMod.debug && DuelistMod.archRoll1 != -1 && DuelistMod.archRoll2 != -1) { DuelistMod.logger.info("theDuelist:DuelistMod:coloredCardsHadCards() ---> setIndex was " + DuelistMod.setIndex + ", random archetype deck was: " + StarterDeckSetup.findDeck(DuelistMod.archRoll1).getSimpleName() + ", current deck is: " + StarterDeckSetup.getCurrentDeck().getSimpleName() + ", random archetype deck #2 was: " + StarterDeckSetup.findDeck(DuelistMod.archRoll2).getSimpleName());  }
		else if (DuelistMod.debug && DuelistMod.archRoll1 != -1) { DuelistMod.logger.info("theDuelist:DuelistMod:coloredCardsHadCards() ---> setIndex was " + DuelistMod.setIndex + ", random archetype 2 was null, but random archetype 1 deck was: " + StarterDeckSetup.findDeck(DuelistMod.archRoll1).getSimpleName() + ", current deck is: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
		else if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:coloredCardsHadCards() ---> setIndex was " + DuelistMod.setIndex + ", random archetype 1 and 2 were both null, current deck is: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
		
	}
	
	public boolean isAllCards()
	{
		if (!isTwoRandomSets() && !isRandomSet() && !isDeckSet() && !isBasicSet()) { return true; }
		else { return false; }
	}
	
	public boolean isTwoRandomSets()
	{
		// Standard -- Card Pool defined in poolhelper file
		if (DuelistMod.setIndex == 0)
		{
			return false;
		}
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1)
		{
			return false;
		}
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2)
		{
			return false;
		}
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		else if (DuelistMod.setIndex == 3)
		{
			return false;
		}
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4)
		{
			return false;
		}
		
		// Basic + 2 random -- Two random poolhelper sets + basic set
		else if (DuelistMod.setIndex == 5)
		{
			return true;
		}
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		else if (DuelistMod.setIndex == 6)
		{
			return true;
		}
		
		// 2 random -- Card pools from two random poolhelper files
		else if (DuelistMod.setIndex == 7)
		{
			return true;
		}
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8)
		{
			return true;
		}
		
		// All Cards
		else if (DuelistMod.setIndex == 9)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
	
	public boolean isRandomSet()
	{
		// Standard -- Card Pool defined in poolhelper file
		if (DuelistMod.setIndex == 0)
		{
			return false;
		}
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1)
		{
			return false;
		}
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2)
		{
			return false;
		}
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		else if (DuelistMod.setIndex == 3)
		{
			return true;
		}
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4)
		{
			return true;
		}
		
		// Basic + 2 random -- Two random poolhelper sets + basic set
		else if (DuelistMod.setIndex == 5)
		{
			return false;
		}
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		else if (DuelistMod.setIndex == 6)
		{
			return false;
		}
		
		// 2 random -- Card pools from two random poolhelper files
		else if (DuelistMod.setIndex == 7)
		{
			return false;
		}
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8)
		{
			return false;
		}
		
		// All Cards
		else if (DuelistMod.setIndex == 9)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
	
	public boolean isDeckSet()
	{
		// Standard -- Card Pool defined in poolhelper file
		if (DuelistMod.setIndex == 0)
		{
			return true;
		}
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1)
		{
			return true;
		}
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2)
		{
			return false;
		}
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		else if (DuelistMod.setIndex == 3)
		{
			return true;
		}
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4)
		{
			return false;
		}
		
		// Basic + 2 random -- Two random poolhelper sets + basic set
		else if (DuelistMod.setIndex == 5)
		{
			return false;
		}
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		else if (DuelistMod.setIndex == 6)
		{
			return true;
		}
		
		// 2 random -- Card pools from two random poolhelper files
		else if (DuelistMod.setIndex == 7)
		{
			return false;
		}
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8)
		{
			return true;
		}
		
		// All Cards
		else if (DuelistMod.setIndex == 9)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
	
	public boolean isBasicSet()
	{
		// Standard -- Card Pool defined in poolhelper file
		if (DuelistMod.setIndex == 0)
		{
			return true;
		}
		
		// Deck Only -- Card pool defined in poolhelper file (without basic cards)
		else if (DuelistMod.setIndex == 1)
		{
			return false;
		}
		
		// Basic Only -- Basic set only
		else if (DuelistMod.setIndex == 2)
		{
			return true;
		}
		
		// Deck + Basic + 1 random -- Deck set, basic set, one random poolhelper set
		else if (DuelistMod.setIndex == 3)
		{
			return true;
		}
		
		// Deck + 1 random -- Deck set and one random poolhelper set
		else if (DuelistMod.setIndex == 4)
		{
			return false;
		}
		
		// Basic + 2 random -- Two random poolhelper sets + basic set
		else if (DuelistMod.setIndex == 5)
		{
			return true;
		}
		
		// Basic + 2 random + Deck -- Two random poolhelper sets, deck set, basic set
		else if (DuelistMod.setIndex == 6)
		{
			return true;
		}
		
		// 2 random -- Card pools from two random poolhelper files
		else if (DuelistMod.setIndex == 7)
		{
			return false;
		}
		
		// 2 random + Deck -- Card pools from two random poolhelper files + your decks poolhelper set
		else if (DuelistMod.setIndex == 8)
		{
			return false;
		}
		
		// All Cards
		else if (DuelistMod.setIndex == 9)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
	
	public static void printNonDeckCards()
	{
		
		ArrayList<DuelistCard> toPrint = new ArrayList<DuelistCard>();
		ArrayList<String> archetypeCardNames = new ArrayList<String>();
		
		for (AbstractCard c : DuelistMod.archetypeCards)
		{
			archetypeCardNames.add(c.originalName);
		}
		
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !archetypeCardNames.contains(c.originalName))
			{
				toPrint.add(c);
			}
		}
		
		for (int i = 0; i < toPrint.size(); i++)
		{
			DuelistMod.logger.info("theDuelist:Debug:printNonBasicSetCards() ---> found a non-basic, non-archetype card [" + i + "]: " + toPrint.get(i).originalName);
		}
	}

}
