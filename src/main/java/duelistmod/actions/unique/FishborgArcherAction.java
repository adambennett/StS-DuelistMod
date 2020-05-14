package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.powers.duelistPowers.FishborgArcherPower;
import duelistmod.variables.Tags;

public class FishborgArcherAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private final FishborgArcherPower power;

    public FishborgArcherAction(FishborgArcherPower pow)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        power = pow;
    }

    @Override
    public void update()
    {
    	if (duration == DURATION)  
        {
        	for (AbstractCard c : AbstractDungeon.player.discardPile.group)
    		{
    			if (c.hasTag(Tags.IS_OVERFLOW))
    			{
    				if (c.magicNumber > 0) { power.flash(); }
    				c.triggerOnEndOfPlayerTurn();
    			}
    		}
        }
        tickDuration();
    }
}
