package duelistmod;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.interfaces.*;

public class PoolHelpers 
{
	
	public static void printNonDeckCards()
	{
		
		ArrayList<DuelistCard> toPrint = new ArrayList<DuelistCard>();
		ArrayList<String> archetypeCardNames = new ArrayList<String>();
		
		for (DuelistCard c : DuelistMod.archetypeCards)
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

	public static void fillColoredCards()
	{
		
		// try to load saved array for colored cards
		// if save exists, fill colored cards with saved cards
		// if the save does not exist, fill colored cards like below
		
		DuelistMod.coloredCards = new ArrayList<AbstractCard>();
		
		// Standard Pool Fill - Basic Cards, Deck archetype, 1 random archetype
		if (DuelistMod.setIndex == 0)
		{
			// When playing with an archetype deck (index 1-9)
			if (DuelistMod.deckIndex < DuelistMod.deckArchetypePoolCheck && DuelistMod.deckIndex != 0)
			{
				// Add all basic + deck archetype cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				
				// Add all random archetype cards
				randomArchetypeHelper(DuelistMod.deckIndex, 4);
			}
			
			// Ojama
			else if (DuelistMod.deckIndex == 11)
			{
				// Add all basic + deck archetype cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				
				// Add all random archetype cards
				randomArchetypeHelper(DuelistMod.deckIndex, 4);
			}
			
			// When playing with a non-archetype deck
			else
			{
				// Add all basic cards
				for (AbstractCard c : DuelistMod.basicCards) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				
				// Add 1 random archetype cards
				randomArchetypeHelper(4, 4);
				//if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 0, but we filled with Basic + 1 random archetype because you are using a deck with no specific archetype. your deck: " + StarterDeckSetup.findDeck(DuelistMod.deckIndex).getSimpleName() + ", and the random archetype you rolled: " + DuelistMod.archRoll1); }
			}
		}
		
		// Basic + Archetype only
		else if (DuelistMod.setIndex == 1)
		{
			// Specific archetype selection (includes basic cards)
			if (DuelistMod.deckIndex < DuelistMod.deckArchetypePoolCheck && DuelistMod.deckIndex != 0)
			{
				// Add all basic cards + deck archetype cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 1, only basic + deck archetype. deck: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
			}
			
			// Ojama
			else if (DuelistMod.deckIndex == 11)
			{
				// Add all basic cards + deck archetype cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 1, only basic + deck archetype. deck: " + StarterDeckSetup.getCurrentDeck().getSimpleName());  }
			}
			
			// When playing with a non-archetype deck
			else
			{
				// Add all basic cards
				for (AbstractCard c : DuelistMod.basicCards) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
				
				// Add 1 random archetype cards
				randomArchetypeHelper(4, 4);
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 1, but we filled with Basic + 1 random archetype because you are using a deck with no specific archetype. your deck: " + StarterDeckSetup.findDeck(DuelistMod.deckIndex).getSimpleName() + ", and the random archetype you rolled: " + DuelistMod.archRoll1); }
			}
		}
		
		// Basic only
		else if (DuelistMod.setIndex == 2)
		{
			// Add all basic cards only
			for (AbstractCard c : DuelistMod.basicCards) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
			if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 2, only basic cards");  }
		}
		
		// Basic + 1 random archetype
		else if (DuelistMod.setIndex == 3)
		{
			// Add all basic cards
			for (AbstractCard c : DuelistMod.basicCards) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
			
			// Add 1 random archetype cards
			randomArchetypeHelper(4, 4);
		}
		
		// Basic + 2 random archetypes
		else if (DuelistMod.setIndex == 4) { basicAndTwoRandom(); }
		
		// Basic + Deck Archetype + 2 random archetypes
		else if (DuelistMod.setIndex == 5)
		{
			// Specific archetype selection (includes basic cards)
			if (DuelistMod.deckIndex < DuelistMod.deckArchetypePoolCheck && DuelistMod.deckIndex != 0)
			{
				// Add all basic cards + deck cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
						
				// Add 2 random archetype cards
				int archRoll = randomArchetypeHelper(4, 4);
				randomArchetypeHelper(4, archRoll);
				
			}
			
			// Ojama
			else if (DuelistMod.deckIndex == 11)
			{
				// Add all basic cards + deck cards
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards()) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
						
				// Add 2 random archetype cards
				int archRoll = randomArchetypeHelper(4, 4);
				randomArchetypeHelper(4, archRoll);
			}
			
			// Playing with non-archetype deck, so just basic and two random
			else { basicAndTwoRandom(); }
		}
		
		// All cards
		else if (DuelistMod.setIndex == 6)
		{
			allCardsFillHelper();
			if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 5"); }
		}
	
		// POWER CHECK
		boolean hasPower = false;
		int powerCounter = 0;
		int powersToFind = 5;
		for (AbstractCard c : DuelistMod.coloredCards) { if (c.type.equals(CardType.POWER)) { powerCounter++; }}
		if (powerCounter >= powersToFind) { hasPower = true; }
		if (!hasPower)
		{
			for (int i = 0; i < 5; i++)
			{
				DuelistCard poolCard = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.myCards.size()));
				while (!poolCard.type.equals(CardType.POWER) || poolCard.rarity.equals(CardRarity.BASIC) || poolCard.rarity.equals(CardRarity.SPECIAL) || poolCard.hasTag(Tags.TOON)) 
				{ 
					poolCard = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.myCards.size())); 
				}
				DuelistMod.coloredCards.add(poolCard);
			}
		}
		
		if (DuelistMod.debug)
		{
			int counter = 1;
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> coloredCards (" + counter + "): " + c.originalName); 
				counter++;
			}
		}
		// /POWER CHECK/
		
		if (DuelistMod.debug)
		{
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				if (c.rarity.equals(CardRarity.SPECIAL) || c.rarity.equals(CardRarity.BASIC))
				{
					DuelistMod.logger.info("Found bad card inside colored cards after filling. Card Name: " + c.originalName);
				}
			}
		}
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
	
	public static void basicAndTwoRandom()
	{
		// Add all basic cards
		for (AbstractCard c : DuelistMod.basicCards) { if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.coloredCards.add(c); }}
		
		// Add 2 random archetype cards
		if (DuelistMod.archRoll1 == -1)
		{
			int archRoll = randomArchetypeHelper(4, 4);
			DuelistMod.archRoll1 = archRoll;
			int archRoll2 = randomArchetypeHelper(4, archRoll);
			DuelistMod.archRoll2 = archRoll2;
		}
		else
		{
			archetypeRehelper(DuelistMod.archRoll1);
			archetypeRehelper(DuelistMod.archRoll2);
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

}
