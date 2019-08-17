package duelistmod.helpers;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import conspire.relics.IceCreamScoop;
import duelistmod.patches.AbstractCardEnum;

public class ConspireHelper 
{

	public static void extraRelics()
	{
		BaseMod.addRelicToCustomPool(new IceCreamScoop(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(IceCreamScoop.ID);		
	}
}
