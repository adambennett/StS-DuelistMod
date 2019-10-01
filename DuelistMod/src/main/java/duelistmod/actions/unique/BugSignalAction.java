package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.variables.Tags;

public class BugSignalAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int mag = 0;

    public BugSignalAction(int magic)
    {
        actionType = ActionType.CARD_MANIPULATION;
        duration = DURATION;
        mag = magic;
    }

    @Override
    public void update()
    {
        if (duration == DURATION) 
        {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
            	if (c.cost > 0 && c.hasTag(Tags.BUG))
            	{
            		c.modifyCostForCombat(-this.mag);
            	}
            }
        }
        tickDuration();
    }
}
