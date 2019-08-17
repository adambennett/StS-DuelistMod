package duelistmod.helpers;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import chronomuncher.relics.*;
import duelistmod.patches.AbstractCardEnum;

public class DiscipleHelper 
{

	public static void extraRelics()
	{
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
