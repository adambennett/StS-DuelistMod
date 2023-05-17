package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyEvokeSpecificOrbAction extends AbstractGameAction {
    private final DuelistOrb orb;

    public EnemyEvokeSpecificOrbAction(DuelistOrb orb) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && this.orb != null) {
            AbstractEnemyDuelist.enemyDuelist.orbs.remove(this.orb);
            AbstractEnemyDuelist.enemyDuelist.orbs.add(0, this.orb);
            AbstractEnemyDuelist.enemyDuelist.evokeOrb();
        }

        this.tickDuration();
    }
}
