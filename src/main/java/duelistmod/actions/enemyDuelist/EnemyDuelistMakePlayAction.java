package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyDuelistMakePlayAction extends AbstractGameAction {
    public void update() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            AbstractEnemyDuelist.enemyDuelist.makePlay();
        }
        this.isDone = true;
    }
}
