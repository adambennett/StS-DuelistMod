package duelistmod.helpers.poolhelpers;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;

public class RandomBigPool 
{
	private static String deckName = "Random Deck (Big)";
	
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
		Map<String, String> added = new HashMap<>();
		while (cards.size() < 20)
		{
			AbstractCard c = DuelistMod.allPowers.get(ThreadLocalRandom.current().nextInt(DuelistMod.allPowers.size()));
			while (added.containsKey(c.name)) { c = DuelistMod.allPowers.get(ThreadLocalRandom.current().nextInt(DuelistMod.allPowers.size())); }
			added.put(c.name, c.name);
			cards.add(c);
		}
		while (cards.size() < 165) 
		{ 
			AbstractCard c = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(DuelistMod.myCards.size()));
			while (added.containsKey(c.name)) { c = DuelistMod.myCards.get(ThreadLocalRandom.current().nextInt(DuelistMod.myCards.size())); }
			added.put(c.name, c.name);
			cards.add(c);
		}
		//cards.addAll(MegatypePool.deck());
		deck.fillPoolCards(cards);
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
