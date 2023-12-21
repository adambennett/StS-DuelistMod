package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.dto.AnyDuelist;

public class EnemyEnergizedBluePower extends AbstractPower {
    public static final String POWER_ID = "EnemyEnergizedBluePower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public AnyDuelist duelist;

    public EnemyEnergizedBluePower(AbstractCreature owner, int energyAmt) {
        this.name = NAME;
        this.ID = "EnemyEnergizedBluePower";
        this.owner = owner;
        this.duelist = AnyDuelist.from(owner);
        this.amount = energyAmt;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        this.updateDescription();
        this.loadRegion("energized_blue");
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void onEnergyRecharge() {
        this.flash();
        this.duelist.getEnemy().gainEnergy(this.amount);
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "EnemyEnergizedBluePower"));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnergizedBlue");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
