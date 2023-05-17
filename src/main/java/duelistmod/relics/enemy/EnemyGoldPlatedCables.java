package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyGoldPlatedCables extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Cables";

    public EnemyGoldPlatedCables() {
        super("Cables", "cables.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyGoldPlatedCables();
    }
}
