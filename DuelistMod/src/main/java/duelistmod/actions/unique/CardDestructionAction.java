package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.patches.DuelistCard;

public class CardDestructionAction extends AbstractGameAction 
{
	private boolean upgrade = false;
	
    public CardDestructionAction(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = DuelistCard.returnTrulyRandomDuelistCard().makeStatEquivalentCopy();
            if (upgrade)
            {
            	c.upgrade();
            }
            if (!c.exhaust) 
            {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + " NL Exhaust.";
            }        
            if (!c.isEthereal && upgrade)
            {
            	c.isEthereal = true;
            	c.rawDescription = "Ethereal NL " + c.rawDescription;
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
