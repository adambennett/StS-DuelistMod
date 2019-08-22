package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class RandomMetronomePool 
{
	private static String deckName = "Metronome Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new Metronome());
		cards.add(new AttackMetronome());
		cards.add(new RareAttackMetronome());
		cards.add(new SkillMetronome());
		cards.add(new RareSkillMetronome());
		cards.add(new PowerMetronome());
		cards.add(new RarePowerMetronome());
		cards.add(new UncommonMetronome());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(cards);	
		Util.log(deckName + " was filled with only Metronomes!");
		return cards;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new Metronome());
		cards.add(new AttackMetronome());
		cards.add(new RareAttackMetronome());
		cards.add(new SkillMetronome());
		cards.add(new RareSkillMetronome());
		cards.add(new PowerMetronome());
		cards.add(new RarePowerMetronome());
		cards.add(new UncommonMetronome());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(cards);	
		Util.log(deckName + " was filled with only Metronomes!");
		return cards;
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
		
		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);	
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new Metronome());
		cards.add(new AttackMetronome());
		cards.add(new RareAttackMetronome());
		cards.add(new SkillMetronome());
		cards.add(new RareSkillMetronome());
		cards.add(new PowerMetronome());
		cards.add(new RarePowerMetronome());
		cards.add(new UncommonMetronome());
		return cards;
	}
}
