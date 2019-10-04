package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.abstracts.*;

public class CardGroupPatch
{
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="moveToExhaustPile", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class moveToExhaustPile
    {
        @SuppressWarnings("rawtypes")
		public static SpireReturn Prefix(CardGroup __instance, AbstractCard c)
        {         
           for (AbstractOrb o : AbstractDungeon.player.orbs)
           {
        	   if (o instanceof DuelistOrb)
        	   {
        		   ((DuelistOrb) o).onExhaust(c);
        	   }
           }
           return SpireReturn.Continue();
        }
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="addToHand", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class addToHand
    {
        @SuppressWarnings("rawtypes")
		public static SpireReturn Prefix(CardGroup __instance, AbstractCard c)
        {
           for (AbstractOrb o : AbstractDungeon.player.orbs)
           {
        	   if (o instanceof DuelistOrb)
        	   {
        		   ((DuelistOrb) o).onAddCardToHand(c);   
        	   }
           }
           return SpireReturn.Continue();
        }
    }
}
