package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;

public class SolemnWarningAction extends AbstractGameAction
{
    private AbstractMonster m;
    
    public SolemnWarningAction(final int weakAmt, final AbstractMonster m) {
        /*SL:13*/this.actionType = ActionType.WAIT;
        /*SL:14*/this.amount = weakAmt;
        /*SL:15*/this.m = m;
    }
    
    @Override
    public void update() {
        /*SL:20*/if (this.m != null && this.m.getIntentBaseDmg() >= 0) {
            /*SL:21*/DuelistCard.gainTempHP(this.amount);
        }
        /*SL:23*/this.isDone = true;
    }
}
