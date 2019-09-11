package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistStance;

public class CardGroupPatch
{
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="moveToExhaustPile", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class moveToExhaustPile
    {
        @SuppressWarnings("rawtypes")
		public static SpireReturn Prefix(CardGroup __instance, AbstractCard c)
        {
           if (AbstractDungeon.player.stance instanceof DuelistStance)
           {
        	   DuelistStance stanceRef = (DuelistStance) AbstractDungeon.player.stance;
        	   stanceRef.onExhaust(c);        	  
           }
           return SpireReturn.Continue();
        }
    }
}
