package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;

import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;

@SpirePatch(clz= AbstractDungeon.class,method="closeCurrentScreen")
public class CardPoolViewPatch
{
    public static void Postfix()
    {
    	DuelistMod.selectingForRelics = false;
    	DuelistMod.selectingCardPoolOptions = false;
    	if (DuelistMod.lastDeckViewWasCustomScreen)
    	{
    		Util.log("Patch saw that you opened the custom deck view screen, and is now attempting to close it fully and reset it properly to the normal one");
	        for (final AbstractCard c : TheDuelist.cardPool.group) {
	            c.unhover();
	            c.untip();
	        }
	        AbstractDungeon.deckViewScreen = new MasterDeckViewScreen();
	        DuelistMod.lastDeckViewWasCustomScreen = false;
    	}
    	
    	//if (AbstractDungeon.gridSelectScreen instanceof DuelistCardSelectScreen) { Util.log("Patch found the AbstractDungeon gridSelectScreen to be an instanceof DuelistCardSelectScreen, so we are resetting it"); AbstractDungeon.gridSelectScreen = new GridCardSelectScreen(); }
    }
}

