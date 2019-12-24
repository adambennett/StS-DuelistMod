package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.variables.Tags;

public class RosePaladinAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;

    public RosePaladinAction(AbstractMonster monster)
    {
        target = monster;
        actionType = ActionType.SPECIAL;
        duration = DURATION;
    }

    @Override
    public void update()
    {
    	boolean otherMonstersAlive = false;
    	
        if (duration == DURATION && target != null) 
        {
        	for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
        	{
        		if (!m.equals(target)) { otherMonstersAlive = true; }
        	}
            if (target.isDying || target.currentHealth <= 0) 
            {
            	if (otherMonstersAlive)
            	{
	            	ArrayList<AbstractCard> plants = new ArrayList<>();
	            	for (AbstractCard c : AbstractDungeon.player.discardPile.group)
	            	{
	            		if (c.hasTag(Tags.PLANT) && !c.hasTag(Tags.EXEMPT))
	            		{
	            			plants.add((DuelistCard) c);
	            		}
	            	}
	            	
	            	if (plants.size() > 0)
	            	{    		
	            		AbstractMonster m = AbstractDungeon.getRandomMonster();
	            		if (m != null) { AbstractDungeon.actionManager.addToBottom(new CardSelectScreenResummonAction(plants, 1, m, true, false));  }		 		
	            	}
            	}
            }
        }
        tickDuration();
    }
}
