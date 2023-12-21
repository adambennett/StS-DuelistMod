package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyShowCardAndPoofAction extends AbstractGameAction
{
    private static final float PURGE_DURATION = 0.2f;
    private AbstractCard card;

    public EnemyShowCardAndPoofAction(final AbstractCard card) {
        this.card = null;
        this.setValues(AbstractEnemyDuelist.enemyDuelist, null, 1);
        this.card = card;
        this.duration = 0.2f;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update() {
        if (this.duration == 0.2f) {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(this.card));
            if (AbstractEnemyDuelist.enemyDuelist.limbo.contains(this.card)) {
                AbstractEnemyDuelist.enemyDuelist.limbo.removeCard(this.card);
            }
            AbstractEnemyDuelist.enemyDuelist.cardInUse = null;
        }
        this.tickDuration();
    }
}
