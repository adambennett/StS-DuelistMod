package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyShowCardAction extends AbstractGameAction
{
    private static final float PURGE_DURATION = 0.2f;
    private AbstractCard card;

    public EnemyShowCardAction(final AbstractCard card) {
        this.card = null;
        this.setValues(AbstractEnemyDuelist.enemyDuelist, null, 1);
        this.card = card;
        this.duration = 0.2f;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == 0.2f) {
            if (AbstractEnemyDuelist.enemyDuelist.limbo.contains(this.card)) {
                AbstractEnemyDuelist.enemyDuelist.limbo.removeCard(this.card);
            }
            AbstractEnemyDuelist.enemyDuelist.cardInUse = null;
        }
        this.tickDuration();
    }
}
