package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

public class CardDestructionAction extends AbstractGameAction 
{
	private boolean upgrade = false;
	private boolean debugCardMode = false;
	private AbstractCard card;
	
    public CardDestructionAction(boolean upgrade) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
    }
    
    public CardDestructionAction(boolean upgrade, AbstractCard fillHandWithThisCard) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgrade = upgrade;
        this.card = fillHandWithThisCard;
        this.debugCardMode = true;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = DuelistCard.returnTrulyRandomDuelistCardInCombat().makeStatEquivalentCopy();
            if (debugCardMode) { c = card; }
            if (upgrade)
            {
            	c.upgrade();
            }
            if (!c.exhaust && !c.hasTag(Tags.NEVER_EXHAUST)) 
            {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + DuelistMod.exhaustForCardText;
            }        
            if (!c.isEthereal && upgrade && !c.hasTag(Tags.NEVER_ETHEREAL))
            {
            	c.isEthereal = true;
            	c.rawDescription = Strings.etherealForCardText + c.rawDescription;
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
