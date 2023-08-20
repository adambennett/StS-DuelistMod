package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.machine.*;

public class CreatorPool 
{
	private static String deckName = "Creator Deck";
	
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
	
		cards.add(new FlyingSaucer());
		cards.add(new CosmicHorrorGangiel());
		cards.add(new AlienTelepath());
		cards.add(new Jinzo());
		cards.add(new TrapHole());
		cards.add(new UltimateOffering());
		cards.add(new OrbitalBombardment());
		
		if (DuelistMod.smallBasicSet) { cards.addAll(BasicPool.smallBasic("")); }
		else { cards.addAll(BasicPool.fullBasic("")); }
		
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			//cards.add(new Token());
		}
		
		deck.fillPoolCards(cards);
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		return pool;
	}
}
