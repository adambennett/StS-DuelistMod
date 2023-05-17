package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyAnchor extends AbstractEnemyDuelistRelic {

    public static final String ID = "Anchor";
    private static final int BLOCK_AMT = 10;

    public EnemyAnchor() {
        super("Anchor", "anchor.png", RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BLOCK_AMT + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
        this.addToBot(new GainBlockAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, BLOCK_AMT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyAnchor();
    }
}
