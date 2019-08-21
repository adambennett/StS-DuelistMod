package duelistmod.helpers.crossover;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
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
}
