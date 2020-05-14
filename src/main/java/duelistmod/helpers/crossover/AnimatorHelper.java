package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import eatyourbeets.relics.animator.*;

public class AnimatorHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Animator to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new Buoy(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CursedBlade(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(Buoy.ID);
		UnlockTracker.markRelicAsSeen(CursedBlade.ID);		
	}
	
	public static ArrayList<AbstractCard> getAllCards() throws IllegalAccessException
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		return cards; 
	}
}
