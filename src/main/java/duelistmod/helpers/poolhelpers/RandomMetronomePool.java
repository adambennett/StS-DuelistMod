package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class RandomMetronomePool 
{
	private static String deckName = "Metronome Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();	
		for (AbstractCard c : DuelistMod.myCards)
		{
			if (c instanceof MetronomeCard && !c.hasTag(Tags.NO_METRONOME_POOL))
			{
				cards.add(c.makeCopy());
			}
		}
		//cards.addAll(MegatypePool.deck());
		deck.fillPoolCards(cards);
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { cards.addAll(BasicPool.smallBasic("Metronome Deck")); }
		else { cards.addAll(BasicPool.fullBasic("Metronome Deck")); }
		deck.fillPoolCards(cards); 
		return cards;
	}
}
