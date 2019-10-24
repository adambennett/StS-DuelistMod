package duelistmod.helpers.crossover;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import infinitespire.cards.black.*;
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
	
	public static ArrayList<AbstractCard> getAllBlackCards()
	{
		ArrayList<AbstractCard> blacks = new ArrayList<>();
		blacks.add(new Collect());
		blacks.add(new DeathsTouch());
		blacks.add(new Execution());
		blacks.add(new FinalStrike());
		blacks.add(new Fortify());
		blacks.add(new FutureSight());
		blacks.add(new Gouge());
		blacks.add(new Menacing());
		blacks.add(new NeuralNetwork());
		blacks.add(new Punishment());
		blacks.add(new TheBestDefense());
		blacks.add(new UltimateForm());
		return blacks;
	}
}
