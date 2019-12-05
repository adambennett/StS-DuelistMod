package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.variables.Tags;

public class FishborgArcherAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;

    public FishborgArcherAction()
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
        	for (AbstractCard c : AbstractDungeon.player.discardPile.group)
    		{
    			if (c.hasTag(Tags.IS_OVERFLOW))
    			{
    				c.triggerOnEndOfPlayerTurn();
    			}
    		}
        }
        tickDuration();
    }
}
