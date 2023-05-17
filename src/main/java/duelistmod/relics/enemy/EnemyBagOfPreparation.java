package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;

public class EnemyBagOfPreparation extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Bag of Preparation";
    private static final int NUM_CARDS = 2;

    public EnemyBagOfPreparation() {
        super("Bag of Preparation", "bag_of_prep.png", RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 2 + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
        this.addToBot(new EnemyDrawActualCardsAction(AbstractEnemyDuelist.enemyDuelist, 2));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyBagOfPreparation();
    }
}
