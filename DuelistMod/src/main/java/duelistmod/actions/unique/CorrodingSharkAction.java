package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;

public class CorrodingSharkAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int inc = 2;

    public CorrodingSharkAction(AbstractMonster monster, int magic)
    {
        target = monster;
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        inc = magic;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
            if (target.isDying || target.currentHealth <= 0) 
            {
            	for (AbstractCard c : AbstractDungeon.player.hand.group)
    			{
    				if (c instanceof DuelistCard)
    				{
    					DuelistCard dC = (DuelistCard)c;
    					if (dC.baseSummons > 0)
    					{
    						dC.modifySummonsForTurn(inc);
    					}
    				}
    			}
            }
        }
        tickDuration();
    }
}
