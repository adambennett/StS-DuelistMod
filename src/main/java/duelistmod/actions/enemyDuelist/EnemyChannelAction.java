package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyChannelAction extends AbstractGameAction
{
    private final AbstractOrb orbType;
    private final boolean autoEvoke;

    public EnemyChannelAction(final AbstractOrb newOrbType) {
        this(newOrbType, true);
    }

    public EnemyChannelAction(final AbstractOrb newOrbType, final boolean autoEvoke) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }

    public void update() {
        if (AbstractEnemyDuelist.enemyDuelist != null && this.duration == Settings.ACTION_DUR_FAST) {
            if (this.autoEvoke && this.orbType instanceof DuelistOrb) {
                AbstractEnemyDuelist.enemyDuelist.channelOrb((DuelistOrb)this.orbType);
            }
            else {
                for (final AbstractOrb o : AbstractEnemyDuelist.enemyDuelist.orbs) {
                    if (o instanceof EmptyOrbSlot && this.orbType instanceof DuelistOrb) {
                        AbstractEnemyDuelist.enemyDuelist.channelOrb((DuelistOrb)this.orbType);
                        break;
                    }
                }
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
