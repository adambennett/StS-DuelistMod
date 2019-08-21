package duelistmod.helpers.crossover;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import infinitespire.relics.*;

public class InfiniteSpireHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from Infinite Spire to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new Freezer(), AbstractCardEnum.DUELIST);	
		BaseMod.addRelicToCustomPool(new SolderingIron(), AbstractCardEnum.DUELIST);	
		BaseMod.addRelicToCustomPool(new BurningSword(), AbstractCardEnum.DUELIST);	
		
		UnlockTracker.markRelicAsSeen(Freezer.ID);
		UnlockTracker.markRelicAsSeen(SolderingIron.ID);
		UnlockTracker.markRelicAsSeen(BurningSword.ID);
		
	}
}
