package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistStance;

public class CardGroupPatch
{
    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class StanceExhaustPilePatch
    {
        public static void Postfix(final AbstractCard c)
        {
           if (AbstractDungeon.player.stance instanceof DuelistStance)
           {
        	   DuelistStance stanceRef = (DuelistStance) AbstractDungeon.player.stance;
        	   stanceRef.onExhaust(c);
           }
        }
    }
}
