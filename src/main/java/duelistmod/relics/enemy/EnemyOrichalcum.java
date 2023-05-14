package duelistmod.relics.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyOrichalcum extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Orichalcum";
    public boolean trigger;

    public EnemyOrichalcum() {
        super("Orichalcum", "orichalcum.png", RelicTier.COMMON, LandingSound.HEAVY);
        this.trigger = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 6 + this.DESCRIPTIONS[1];
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractEnemyDuelist.enemyDuelist.currentBlock == 0 || this.trigger) {
            this.trigger = false;
            this.flash();
            this.stopPulse();
            this.addToTop(new GainBlockAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, 6));
            this.addToTop(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
        }
    }

    @Override
    public void atTurnStart() {
        this.trigger = false;
        if (AbstractEnemyDuelist.enemyDuelist.currentBlock == 0) {
            this.beginLongPulse();
        }
    }

    @Override
    public int onPlayerGainedBlock(final float blockAmount) {
        if (blockAmount > 0.0f) {
            this.stopPulse();
        }
        return MathUtils.floor(blockAmount);
    }

    @Override
    public void onVictory() {
        this.stopPulse();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyOrichalcum();
    }
}
