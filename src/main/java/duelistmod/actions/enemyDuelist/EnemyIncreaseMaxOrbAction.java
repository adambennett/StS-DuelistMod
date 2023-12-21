package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyIncreaseMaxOrbAction extends AbstractGameAction {

    private final AbstractEnemyDuelist duelist;

    public EnemyIncreaseMaxOrbAction(int slotIncrease, AbstractEnemyDuelist duelist) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = slotIncrease;
        this.actionType = ActionType.BLOCK;
        this.duelist = duelist;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for(int i = 0; i < this.amount; ++i) {
                duelist.increaseMaxOrbSlots(1, true);
            }
        }

        this.tickDuration();
    }
}
