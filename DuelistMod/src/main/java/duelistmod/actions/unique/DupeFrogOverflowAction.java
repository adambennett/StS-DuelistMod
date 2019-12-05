package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DupeFrogOverflowAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int magicNumber = 0;

    public DupeFrogOverflowAction(int amt)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        magicNumber = amt;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
        	ArrayList<AbstractCard> upgradeCards = new ArrayList<>();
        	for (AbstractCard c : AbstractDungeon.player.hand.group)
        	{
        		if (c.canUpgrade())
        		{
        			upgradeCards.add(c);
        		}
        	}
        	
        	for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        	{
        		if (c.canUpgrade())
        		{
        			upgradeCards.add(c);
        		}
        	}
        	
        	for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        	{
        		if (c.canUpgrade())
        		{
        			upgradeCards.add(c);
        		}
        	}
        	
        	if (upgradeCards.size() > this.magicNumber + 1)
        	{
        		while (upgradeCards.size() > this.magicNumber + 1)
        		{
        			upgradeCards.remove(AbstractDungeon.cardRandomRng.random(upgradeCards.size() - 1));
        		}
        		
        		for (AbstractCard c : upgradeCards)
        		{
        			c.upgrade();
        		}
        	}
        	
        	else
        	{
        		for (AbstractCard c : upgradeCards)
        		{
        			c.upgrade();
        		}
        	}
        }
        tickDuration();
    }
}
