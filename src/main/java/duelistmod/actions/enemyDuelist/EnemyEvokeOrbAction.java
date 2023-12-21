package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyEvokeOrbAction extends AbstractGameAction
{
    private final int orbCount;

    public EnemyEvokeOrbAction(final int amount) {
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
        this.duration = this.startDuration;
        this.orbCount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (int i = 0; i < this.orbCount; ++i) {
                AbstractEnemyDuelist.enemyDuelist.evokeOrb();
            }
        }
        this.tickDuration();
    }
}
