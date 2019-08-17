package duelistmod.helpers;

import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.patches.AbstractCardEnum;

public class ReplayHelper 
{

	public static void extraRelics()
	{
		BaseMod.addRelicToCustomPool(new ByrdSkull(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new OozeArmor(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SecondSwordRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SnakeBasket(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SneckoScales(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ReplaySpearhead(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new VampiricSpirits(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(ByrdSkull.ID);
		UnlockTracker.markRelicAsSeen(OozeArmor.ID);
		UnlockTracker.markRelicAsSeen(SecondSwordRelic.ID);
		UnlockTracker.markRelicAsSeen(SnakeBasket.ID);
		UnlockTracker.markRelicAsSeen(SneckoScales.ID);
		UnlockTracker.markRelicAsSeen(ReplaySpearhead.ID);
		UnlockTracker.markRelicAsSeen(VampiricSpirits.ID);
	}
}
