package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.metronomes.*;

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
		cards.add(new Metronome());
		cards.add(new AttackMetronome());
		cards.add(new RareAttackMetronome());
		cards.add(new SkillMetronome());
		cards.add(new RareSkillMetronome());
		cards.add(new PowerMetronome());
		cards.add(new RarePowerMetronome());
		cards.add(new UncommonMetronome());
		cards.add(new UncommonAttackMetronome());
		cards.add(new AttackTrapMetronome());
		cards.add(new TrapMetronome());
		cards.add(new BlockMetronome());
		cards.add(new BlockSpellMetronome());
		cards.add(new RareBlockMetronome());
		cards.add(new SpellMetronome());
		cards.addAll(MegatypePool.deck());
		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);	
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { cards.addAll(BasicPool.smallBasic("")); }
		else { cards.addAll(BasicPool.fullBasic("")); }
		cards.add(new Metronome());
		cards.add(new AttackMetronome());
		cards.add(new RareAttackMetronome());
		cards.add(new SkillMetronome());
		cards.add(new RareSkillMetronome());
		cards.add(new PowerMetronome());
		cards.add(new RarePowerMetronome());
		cards.add(new UncommonMetronome());
		cards.add(new UncommonAttackMetronome());
		cards.add(new AttackTrapMetronome());
		cards.add(new TrapMetronome());
		cards.add(new BlockMetronome());
		cards.add(new BlockSpellMetronome());
		cards.add(new RareBlockMetronome());
		cards.add(new SpellMetronome());
		deck.fillPoolCards(cards); 
		return cards;
	}
}
