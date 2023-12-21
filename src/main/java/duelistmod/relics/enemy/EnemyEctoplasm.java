package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.interfaces.EnemyEnergyRelic;

public class EnemyEctoplasm extends AbstractEnemyDuelistRelic implements EnemyEnergyRelic
{
    public static final String ID = "Ectoplasm";

    public EnemyEctoplasm() {
        super("Ectoplasm", "ectoplasm.png", RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.setDescription();
    }

    private String setDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyEctoplasm();
    }
}
