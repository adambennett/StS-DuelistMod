package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import conspire.cards.blue.*;
import conspire.cards.colorless.*;
import conspire.cards.green.*;
import conspire.cards.red.*;
import conspire.relics.IceCreamScoop;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class ConspireHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from Conspire to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new IceCreamScoop(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(IceCreamScoop.ID);		
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		cards.add(new Charge());
        cards.add(new ExplosiveBarrier());
        cards.add(new HitWhereItHurts());
        cards.add(new Purge());   
        cards.add(new DoublingDagger());
        cards.add(new PoisonWeapons());
        cards.add(new Rain());
        cards.add(new SharedLibrary());
        cards.add(new Banana());
        cards.add(new GhostlyDefend());
        cards.add(new GhostlyStrike());
        cards.add(new InfernalBerry());
        cards.add(new SpireCoStock());
		return cards; 
	}
}
