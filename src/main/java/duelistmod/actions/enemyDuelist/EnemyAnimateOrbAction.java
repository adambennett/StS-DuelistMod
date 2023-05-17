package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyAnimateOrbAction  extends AbstractGameAction
{
    private final int orbCount;

    public EnemyAnimateOrbAction(final int amount) {
        this.orbCount = amount;
    }

    public void update() {
        for (int i = 0; i < this.orbCount; ++i) {
            AbstractEnemyDuelist.enemyDuelist.triggerEvokeAnimation(i);
        }
        this.isDone = true;
    }
}
