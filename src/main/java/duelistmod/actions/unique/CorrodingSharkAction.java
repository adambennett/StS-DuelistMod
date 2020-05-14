package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.aqua.CorrodingShark;

public class CorrodingSharkAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int mag = 0;

    public CorrodingSharkAction(AbstractMonster monster, int magic)
    {
        target = monster;
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        mag = magic;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
            if (target.isDying || target.currentHealth <= 0) 
            {
            	DuelistCard cs = new CorrodingShark();
            	if (this.mag > 0) { cs.upgrade(); }
            	DuelistCard.addCardToHand(cs);            	
            }
        }
        tickDuration();
    }
}
