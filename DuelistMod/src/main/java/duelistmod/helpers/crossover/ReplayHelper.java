package duelistmod.helpers.crossover;

import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class ReplayHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from Replay the Spire to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new ByrdSkull(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new OozeArmor(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SecondSwordRelic(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SnakeBasket(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SneckoScales(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ReplaySpearhead(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new VampiricSpirits(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new FrozenProgram(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new KingOfHearts(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new Carrot(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SolarPanel(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ByrdFeeder(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new ElectricBlood(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RaidersMask(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(ByrdSkull.ID);
		UnlockTracker.markRelicAsSeen(OozeArmor.ID);
		UnlockTracker.markRelicAsSeen(SecondSwordRelic.ID);
		UnlockTracker.markRelicAsSeen(SnakeBasket.ID);
		UnlockTracker.markRelicAsSeen(SneckoScales.ID);
		UnlockTracker.markRelicAsSeen(ReplaySpearhead.ID);
		UnlockTracker.markRelicAsSeen(VampiricSpirits.ID);
		UnlockTracker.markRelicAsSeen(FrozenProgram.ID);
		UnlockTracker.markRelicAsSeen(KingOfHearts.ID);
		UnlockTracker.markRelicAsSeen(Carrot.ID);
		UnlockTracker.markRelicAsSeen(SolarPanel.ID);
		UnlockTracker.markRelicAsSeen(ByrdFeeder.ID);
		UnlockTracker.markRelicAsSeen(ElectricBlood.ID);
		UnlockTracker.markRelicAsSeen(RaidersMask.ID);
	}
}
