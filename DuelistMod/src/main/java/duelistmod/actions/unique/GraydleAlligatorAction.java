package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class GraydleAlligatorAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int magicNumber = 0;

    public GraydleAlligatorAction(int amt)
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
        	ArrayList<DuelistCard> overflowCards = new ArrayList<>();
        	ArrayList<DuelistCard> prefOverflowCards = new ArrayList<>();
        	AbstractPlayer p = AbstractDungeon.player;
        	for (AbstractCard c : p.hand.group)
        	{
        		if (c instanceof DuelistCard && c.hasTag(Tags.IS_OVERFLOW))
        		{
        			DuelistCard dc = (DuelistCard)c;
        			if (c.magicNumber > 0) { prefOverflowCards.add(dc); }
        			overflowCards.add(dc);
        		}
        	}
        	
        	if (prefOverflowCards.size() >= this.magicNumber)
        	{
        		while (prefOverflowCards.size() > this.magicNumber)
    			{
        			prefOverflowCards.remove(AbstractDungeon.cardRandomRng.random(prefOverflowCards.size() - 1));
    			}
        		
        		for (DuelistCard c : prefOverflowCards)
    			{
    				c.triggerOverflowEffect();
    			}
        	}
        	
        	else if (prefOverflowCards.size() > 0)
        	{
        		int needed = this.magicNumber - prefOverflowCards.size();
        		if (needed > 0)
        		{
        			if (overflowCards.size() > needed)
            		{
            			while (overflowCards.size() > needed)
            			{
            				overflowCards.remove(AbstractDungeon.cardRandomRng.random(overflowCards.size() - 1));
            			}
            		}
            		prefOverflowCards.addAll(overflowCards);
            		for (DuelistCard c : prefOverflowCards)
        			{
        				c.triggerOverflowEffect();
        			}
        		}
        		else
        		{
        			for (DuelistCard c : prefOverflowCards)
        			{
        				c.triggerOverflowEffect();
        			}
        		}
        		
        	}
        	
        	else if (overflowCards.size() > this.magicNumber)
        	{
        		while (overflowCards.size() > this.magicNumber)
    			{
    				overflowCards.remove(AbstractDungeon.cardRandomRng.random(overflowCards.size() - 1));
    			}
        		
        		for (DuelistCard c : overflowCards)
    			{
    				c.triggerOverflowEffect();
    			}
        	}
        	
        	else if (overflowCards.size() > 0)
        	{
        		for (DuelistCard c : overflowCards)
    			{
    				c.triggerOverflowEffect();
    			}
        	}
        }
        tickDuration();
    }
}
