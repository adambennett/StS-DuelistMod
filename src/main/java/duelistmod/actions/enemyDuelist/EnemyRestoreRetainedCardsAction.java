package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

import java.util.Iterator;

public class EnemyRestoreRetainedCardsAction extends AbstractGameAction
{
    private CardGroup group;
    private AbstractEnemyDuelist boss;

    public EnemyRestoreRetainedCardsAction(final AbstractEnemyDuelist boss, final CardGroup group) {
        this.setValues(boss, this.source, -1);
        this.group = group;
        this.boss = boss;
    }

    public void update() {
        this.isDone = true;
        final Iterator<AbstractCard> c = this.group.group.iterator();
        while (c.hasNext()) {
            final AbstractCard e = c.next();
            if (e.retain || e.selfRetain) {
                e.onRetained();
                this.boss.hand.addToTop(e);
                e.retain = false;
                c.remove();
            }
        }
        if (this.boss != null) {
            this.boss.hand.refreshHandLayout();
        }
    }
}
