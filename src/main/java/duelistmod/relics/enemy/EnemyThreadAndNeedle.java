package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyThreadAndNeedle extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Thread and Needle";

    public EnemyThreadAndNeedle() {
        super("Thread and Needle", "threadAndNeedle.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 4 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, new PlatedArmorPower(AbstractEnemyDuelist.enemyDuelist, 4), 4));
        this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyThreadAndNeedle();
    }
}
