package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

import duelistmod.abstracts.DuelistCard;

public class StormSparksAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int magic = 1;

    public StormSparksAction(AbstractMonster monster, int magic)
    {
        target = monster;
        actionType = ActionType.DAMAGE;
        duration = DURATION;
        this.magic = magic;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
            if (target.isDying || target.currentHealth <= 0) 
            {
            	DuelistCard.channel(new Lightning(), this.magic);
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
