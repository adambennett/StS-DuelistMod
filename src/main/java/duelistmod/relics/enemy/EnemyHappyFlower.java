package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.actions.enemyDuelist.EnemyGainEnergyAction;

public class EnemyHappyFlower extends AbstractEnemyDuelistRelic
{
    public static final String ID = "Happy Flower";

    public EnemyHappyFlower() {
        super("Happy Flower", "sunflower.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.setDescription();
    }

    private String setDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
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
        if (this.counter == 3) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, this));
            this.addToBot(new EnemyGainEnergyAction(1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyHappyFlower();
    }
}
