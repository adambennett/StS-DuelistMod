package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyDuelistTurnStartDrawAction extends AbstractGameAction {
    public void update() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            AbstractEnemyDuelist.enemyDuelist.endTurnStartTurn();
        }
        this.isDone = true;
    }
}
