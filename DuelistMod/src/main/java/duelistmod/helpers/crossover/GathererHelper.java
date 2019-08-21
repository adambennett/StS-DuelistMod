package duelistmod.helpers.crossover;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import the_gatherer.relics.*;

public class GathererHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Gatherer to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new MistGenerator(), AbstractCardEnum.DUELIST);	
		BaseMod.addRelicToCustomPool(new FlyingFruit(), AbstractCardEnum.DUELIST);		
		
		UnlockTracker.markRelicAsSeen(MistGenerator.ID);		
		UnlockTracker.markRelicAsSeen(FlyingFruit.ID);				
	}
}
