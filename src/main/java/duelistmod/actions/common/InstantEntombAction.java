package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.helpers.Util;

public class InstantEntombAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private final AbstractCard card;

    public InstantEntombAction(AbstractCard c)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        card = c;
    }

    @Override
    public void update()
    {
        if (duration == DURATION) 
        {
        	Util.entombCard(this.card);
        }
        tickDuration();
    }
}
