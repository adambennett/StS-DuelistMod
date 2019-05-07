package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class SplashPassiveAction extends AbstractGameAction 
{
	
    public SplashPassiveAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = DuelistCard.returnTrulyRandomDuelistCard().makeStatEquivalentCopy();
            if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) 
            {
                c.isEthereal = true;
                c.rawDescription = DuelistMod.etherealForCardText + c.rawDescription;
            }              
            if (c.cost > 0)
			{
				c.modifyCostForCombat(-c.cost);
				c.isCostModified = true;
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
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
