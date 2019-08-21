package duelistmod.helpers.crossover;

import basemod.BaseMod;
import clockworkmod.relics.RubberBall;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class ClockworkHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Clockwork to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new RubberBall(), AbstractCardEnum.DUELIST);	
	}
}
