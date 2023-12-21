package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.SpiritToken;
import duelistmod.powers.SummonPower;

public class RevendreadExecutorAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;

    public RevendreadExecutorAction(AbstractMonster monster)
    {
        target = monster;
        actionType = ActionType.DAMAGE;
        duration = DURATION;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
            if (target.isDying || target.currentHealth <= 0) 
            {
            	SummonPower pow = DuelistCard.getSummonPower();
            	if (pow != null)
            	{
            		int spirits = 0;
            		for (AbstractCard c : pow.getCardsSummoned())
            		{
            			if (c instanceof SpiritToken)
            			{
            				spirits++;
            			}
            		}
            		
            		if (spirits > 0)
            		{
            			AbstractDungeon.player.increaseMaxHp(spirits, true);
            		}
            	}
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
