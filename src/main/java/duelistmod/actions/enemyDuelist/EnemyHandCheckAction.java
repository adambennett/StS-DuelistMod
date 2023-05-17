package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyHandCheckAction extends AbstractGameAction
{
    private final AbstractEnemyDuelist player;

    public EnemyHandCheckAction() {
        this.player = AbstractEnemyDuelist.enemyDuelist;
    }

    public void update() {
        this.player.hand.applyPowers();
        this.player.hand.glowCheck();
        this.isDone = true;
    }
}
