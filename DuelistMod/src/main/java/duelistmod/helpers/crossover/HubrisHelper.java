package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.hubris.HubrisMod;
import com.evacipated.cardcrawl.mod.hubris.cards.black.*;
import com.evacipated.cardcrawl.mod.hubris.relics.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;

public class HubrisHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from Hubris to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new IronBody(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new AncientText(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new RGBLights(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new BallOfYels(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new SoftwareUpdate(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(IronBody.ID);
		UnlockTracker.markRelicAsSeen(AncientText.ID);
		UnlockTracker.markRelicAsSeen(RGBLights.ID);
		UnlockTracker.markRelicAsSeen(BallOfYels.ID);
		UnlockTracker.markRelicAsSeen(SoftwareUpdate.ID);
		
	}
	
	public static ArrayList<AbstractCard> getAllCards()
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		HubrisMod.loadOtherData();
		cards.add(new Fate());
		cards.add(new InfiniteBlow());
		cards.add(new Rewind());
		return cards; 
	}
}
