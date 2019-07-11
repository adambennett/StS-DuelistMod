package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

public class RandomEtherealDuelistCardToHandAction extends AbstractGameAction 
{
	
    public RandomEtherealDuelistCardToHandAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = DuelistCard.returnTrulyRandomDuelistCardInCombat().makeStatEquivalentCopy();
            if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) 
            {
                c.isEthereal = true;
                c.rawDescription = Strings.etherealForCardText + c.rawDescription;
            }              
            c.initializeDescription();
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
        	{
            	AbstractDungeon.actionManager.addToTop(new MakeStatEquivalentLocal(c));
        	}
            this.tickDuration();
        }
        this.isDone = true;
    }

    public class MakeStatEquivalentLocal extends AbstractGameAction {
        private AbstractCard c;

        public MakeStatEquivalentLocal(AbstractCard c) {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.duration = Settings.ACTION_DUR_FAST;
            this.c = c;

        }

        public void update() {
            if (this.duration == Settings.ACTION_DUR_FAST) {
            	if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
            	{
            		AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
            	}
            	else
            	{
            		AbstractDungeon.player.createHandIsFullDialog();
            	}
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
