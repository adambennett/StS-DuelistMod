package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyGainEnergyAction extends AbstractGameAction
{
    private int energyGain;
    private AbstractEnemyDuelist boss;

    public EnemyGainEnergyAction(final int amount) {
        this(AbstractEnemyDuelist.enemyDuelist, amount);
    }

    public EnemyGainEnergyAction(final AbstractEnemyDuelist target, final int amount) {
        this.setValues(target, target, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.energyGain = amount;
        this.boss = target;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractEnemyDuelist.enemyDuelist == null) {
                this.isDone = true;
                return;
            }
            (this.boss = AbstractEnemyDuelist.enemyDuelist).gainEnergy(this.energyGain);
            for (final AbstractCard c : this.boss.hand.group) {
                c.triggerOnGainEnergy(this.energyGain, true);
            }
        }
        this.tickDuration();
    }
}

