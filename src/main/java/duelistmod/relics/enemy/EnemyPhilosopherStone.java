package duelistmod.relics.enemy;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistRelic;
import duelistmod.interfaces.EnemyEnergyRelic;

public class EnemyPhilosopherStone extends AbstractEnemyDuelistRelic implements EnemyEnergyRelic
{
    public static final String ID = "Philosopher's Stone";

    public EnemyPhilosopherStone() {
        super("Philosopher's Stone", "philosopherStone.png", RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 1 + this.DESCRIPTIONS[1];
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer m = AbstractDungeon.player;
        this.addToTop(new RelicAboveCreatureAction(m, this));
        m.addPower(new StrengthPower(m, 1));
        AbstractDungeon.onModifyPower();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EnemyPhilosopherStone();
    }
}
