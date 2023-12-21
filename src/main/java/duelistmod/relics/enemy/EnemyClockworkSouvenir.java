package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyClockworkSouvenir  extends AbstractEnemyDuelistRelic
{
    public static final String ID = "ClockworkSouvenir";

    public EnemyClockworkSouvenir() {
        super("ClockworkSouvenir", "clockwork.png", RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new ApplyPowerAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, new ArtifactPower(AbstractEnemyDuelist.enemyDuelist, 1), 1));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyClockworkSouvenir();
    }
}
