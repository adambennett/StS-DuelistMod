package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;

public class EnemyIncenseBurner extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Incense Burner";
    private static final int NUM_TURNS = 6;

    public EnemyIncenseBurner() {
        super("Incense Burner", "incenseBurner.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter == -1) {
            this.counter += 2;
        }
        else {
            ++this.counter;
        }
        if (this.counter == 6) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
            this.addToBot(new ApplyPowerAction(AbstractEnemyDuelist.enemyDuelist, null, new IntangiblePower(AbstractEnemyDuelist.enemyDuelist, 1), 1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyIncenseBurner();
    }
}
