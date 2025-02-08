package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DuelistStance;
import duelistmod.dto.AnyDuelist;

public class CardGroupPatch {

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="moveToDiscardPile", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class moveToDiscardPile {
        @SuppressWarnings("rawtypes")
        public static SpireReturn Postfix(CardGroup __instance, AbstractCard c) {
            if (c instanceof DuelistCard) {
                ((DuelistCard)c).onMovedToDiscardPile();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="moveToExhaustPile", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class moveToExhaustPile {
        @SuppressWarnings("rawtypes")
		public static SpireReturn Prefix(CardGroup __instance, AbstractCard c) {
           if (AbstractDungeon.player.stance instanceof DuelistStance) {
        	   DuelistStance stanceRef = (DuelistStance) AbstractDungeon.player.stance;
        	   stanceRef.onExhaust(c);        	  
           }
           
           for (AbstractOrb o : AbstractDungeon.player.orbs) {
        	   if (o instanceof DuelistOrb) {
        		   ((DuelistOrb) o).onExhaust(c);
        	   }
           }
           return SpireReturn.Continue();
        }
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.CardGroup", method="addToHand", paramtypes = {"com.megacrit.cardcrawl.cards.AbstractCard"})
    public static class addToHand {
        @SuppressWarnings("rawtypes")
		public static SpireReturn Prefix(CardGroup __instance, AbstractCard c) {
           AnyDuelist duelist = AnyDuelist.from(c);
           if (duelist.stance() instanceof DuelistStance) {
        	   DuelistStance stanceRef = (DuelistStance) duelist.stance();
        	   stanceRef.onAddCardToHand(c);        	  
           }
           
           for (AbstractOrb o : duelist.orbs()) {
        	   if (o instanceof DuelistOrb) {
        		   ((DuelistOrb) o).onAddCardToHand(c);   
        	   }
           }
           for (AbstractPower power : duelist.powers()) {
               if (power instanceof DuelistPower) {
                   ((DuelistPower)power).onAddCardToHand(c);
               }
           }
           return SpireReturn.Continue();
        }
    }

}
