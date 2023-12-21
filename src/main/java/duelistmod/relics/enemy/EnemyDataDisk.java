package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyDataDisk extends AbstractEnemyDuelistRelic
{
    public static final String ID = "DataDisk";

    public EnemyDataDisk() {
        super("DataDisk", "dataDisk.png", RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, new FocusPower(AbstractEnemyDuelist.enemyDuelist, 1), 1));
        this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyDataDisk();
    }
}

