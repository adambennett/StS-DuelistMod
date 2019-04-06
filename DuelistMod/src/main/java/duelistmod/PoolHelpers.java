package duelistmod;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.interfaces.StarterDeck;
import duelistmod.patches.*;

public class PoolHelpers {

	public static void fillColoredCards()
	{
		DuelistMod.coloredCards = new ArrayList<AbstractCard>();
		
		// Standard Pool Fill - Basic Cards, Deck archetype, 1 random archetype
		if (DuelistMod.setIndex == 0)
		{
			if (DuelistMod.deckIndex < 10 && DuelistMod.deckIndex != 0)
			{
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);
					}
				}
				int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
				while (archRoll == DuelistMod.deckIndex || archRoll == 4) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
				StarterDeck ref = StarterDeckSetup.findDeck(archRoll);
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 0, random archetype deck was: " + ref.getSimpleName());  }
				for (AbstractCard c : ref.getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);
					}
				}
			}
			else
			{
				for (DuelistCard c : DuelistMod.myCards)
				{
					if ((c.color.equals(AbstractCardEnum.DUELIST_SPELLS) || c.color.equals(AbstractCardEnum.DUELIST_TRAPS) || c.color.equals(AbstractCardEnum.DUELIST_MONSTERS)) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);
						DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
					}
				}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 0, but we filled with all cards in the mod because you are using a deck with no specific archetype. your deck: " + StarterDeckSetup.findDeck(DuelistMod.deckIndex).getSimpleName()); }
			}
		}
		
		// Basic + Archetype only
		else if (DuelistMod.setIndex == 1)
		{
			// Specific archetype selection (includes basic cards)
			if (DuelistMod.deckIndex < 10 && DuelistMod.deckIndex != 0)
			{
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);
					}
				}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 1, only basic + deck archetype" );  }
			}
			else
			{
				for (DuelistCard c : DuelistMod.myCards)
				{
					if ((c.color.equals(AbstractCardEnum.DUELIST_SPELLS) || c.color.equals(AbstractCardEnum.DUELIST_TRAPS) || c.color.equals(AbstractCardEnum.DUELIST_MONSTERS)) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);
						DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
					}
				}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 1, but we filled with all cards in the mod because you are using a deck with no specific archetype. your deck: " + StarterDeckSetup.findDeck(DuelistMod.deckIndex).getSimpleName()); }
			}
		}
		
		// Basic only
		else if (DuelistMod.setIndex == 2)
		{
			for (AbstractCard c : DuelistMod.basicCards)
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);
				}
			}
			if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 2, only basic cards");  }
		}
		
		// Basic + 1 random archetype
		else if (DuelistMod.setIndex == 3)
		{
			for (AbstractCard c : DuelistMod.basicCards)
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);
				}
			}
			
			int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
			while (archRoll == 4) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
			StarterDeck ref = StarterDeckSetup.findDeck(archRoll);
			if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 3, random archetype deck was: " + ref.getSimpleName());  }
			for (AbstractCard c : ref.getPoolCards())
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);
				}
			}
		}
		
		// Basic + 2 random archetypes
		else if (DuelistMod.setIndex == 4)
		{
			for (AbstractCard c : DuelistMod.basicCards)
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);					
				}
			}
			
			int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
			while (archRoll == 4) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
			StarterDeck ref = StarterDeckSetup.findDeck(archRoll);
			for (AbstractCard c : ref.getPoolCards())
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);					
				}
			}
	
			int archRollB = ThreadLocalRandom.current().nextInt(1, 9);
			while (archRollB == archRoll|| archRollB == 4) { archRollB = ThreadLocalRandom.current().nextInt(1, 9); }
			StarterDeck refB = StarterDeckSetup.findDeck(archRollB);
			for (AbstractCard c : refB.getPoolCards())
			{
				if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);					
				}
			}
			if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 4, random archetype deck was: " + ref.getSimpleName() + ", and second random archetype deck was : " + refB.getSimpleName());  }
		}
		
		// Basic + Deck Archetype + 2 random archetypes
		else if (DuelistMod.setIndex == 5)
		{
			// Specific archetype selection (includes basic cards)
			if (DuelistMod.deckIndex < 10 && DuelistMod.deckIndex != 0)
			{
				for (AbstractCard c : StarterDeckSetup.findDeck(DuelistMod.deckIndex).getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
						
				
				int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
				while (archRoll == DuelistMod.deckIndex || archRoll == 4) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
				StarterDeck ref = StarterDeckSetup.findDeck(archRoll);
				for (AbstractCard c : ref.getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
				
				int archRollB = ThreadLocalRandom.current().nextInt(1, 9);
				while (archRollB == DuelistMod.deckIndex || archRollB == archRoll || archRollB == 4) { archRollB = ThreadLocalRandom.current().nextInt(1, 9); }
				StarterDeck refB = StarterDeckSetup.findDeck(archRollB);
				for (AbstractCard c : refB.getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 5, random archetype deck was: " + ref.getSimpleName() + ", and second random archetype deck was : " + refB.getSimpleName());  }
			}
			else
			{
				for (AbstractCard c : DuelistMod.basicCards)
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
				
				int archRoll = ThreadLocalRandom.current().nextInt(1, 9);
				while (archRoll == 4) { archRoll = ThreadLocalRandom.current().nextInt(1, 9); }
				StarterDeck ref = StarterDeckSetup.findDeck(archRoll);				
				for (AbstractCard c : ref.getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
	
				int archRollB = ThreadLocalRandom.current().nextInt(1, 9);
				while (archRollB == archRoll || archRollB == 4) { archRollB = ThreadLocalRandom.current().nextInt(1, 9); }
				StarterDeck refB = StarterDeckSetup.findDeck(archRollB);
				for (AbstractCard c : refB.getPoolCards())
				{
					if (!c.rarity.equals(CardRarity.BASIC) || !c.rarity.equals(CardRarity.SPECIAL))
					{
						DuelistMod.coloredCards.add(c);						
					}
				}
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredPools() ---> setIndex was 5, but no deck archetype, so just basic + 2 random archetypes:: first random deck was: " + ref.getSimpleName() + ", and second random archetype deck was : " + refB.getSimpleName());  }
			}
		}
		
		// All cards
		else if (DuelistMod.setIndex == 6)
		{
			for (DuelistCard c : DuelistMod.myCards)
			{
				if ((c.color.equals(AbstractCardEnum.DUELIST_SPELLS) || c.color.equals(AbstractCardEnum.DUELIST_TRAPS) || c.color.equals(AbstractCardEnum.DUELIST_MONSTERS)) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
				{
					DuelistMod.coloredCards.add(c);
					DuelistMod.logger.info("theDuelist:DuelistMod:fillColoredCards() ---> added " + c.originalName + " to coloredCards");
				}
			}
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
	}

}
