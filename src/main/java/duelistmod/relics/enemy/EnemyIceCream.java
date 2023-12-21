package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyIceCream extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Ice Cream";

    public EnemyIceCream() {
        super("Ice Cream", "iceCream.png", RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyIceCream();
    }
}
