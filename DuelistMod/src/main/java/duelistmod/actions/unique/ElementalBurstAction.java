package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.duelistPowers.FrozenDebuff;
import duelistmod.variables.Tags;

public class ElementalBurstAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int amount = 0;
    private boolean upgrade = false;

    public ElementalBurstAction(int blkAmt, boolean upgraded)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        amount = blkAmt;
        upgrade = upgraded;
    }

    @Override
    public void update()
    {
    	if (duration == DURATION)  
        {
        	boolean frozenEnemies = false;
        	for (AbstractMonster mon : DuelistCard.getAllMons())
        	{
        		if (mon.hasPower(FrozenDebuff.POWER_ID)) { frozenEnemies = true; break; }
        	}
        	
        	if (frozenEnemies)
        	{
        		DuelistCard.staticBlock(this.amount);
        		for (AbstractCard c : AbstractDungeon.player.hand.group)
        		{
        			if (c.hasTag(Tags.IS_OVERFLOW) && c instanceof DuelistCard)
        			{
        				((DuelistCard)c).triggerOverflowEffect();
        				if (this.upgrade) { ((DuelistCard)c).triggerOverflowEffect(); }
        			}
        		}
        	}
        }
        tickDuration();
    }
}
