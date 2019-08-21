package duelistmod.helpers.crossover;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import chronomuncher.relics.*;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class DiscipleHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Disciple to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new BlueBox(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new PaperTurtyl(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SlipperyGoo(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new HangingClock(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Metronome(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(BlueBox.ID);
		UnlockTracker.markRelicAsSeen(PaperTurtyl.ID);
		UnlockTracker.markRelicAsSeen(HangingClock.ID);
		UnlockTracker.markRelicAsSeen(SlipperyGoo.ID);
		UnlockTracker.markRelicAsSeen(Metronome.ID);
		
	}
}
