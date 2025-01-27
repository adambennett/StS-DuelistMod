package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Strings;

public class StrengthDownPower extends TwoAmountPower {

    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("StrengthDownPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.DESPAIR_POWER);
    private final AnyDuelist duelist;

    public StrengthDownPower(final AbstractCreature owner, final AbstractCreature source, int turns, int strLoss) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = strLoss;
        this.amount2 = turns;
        this.duelist = AnyDuelist.from(this);
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        this.duelist.applyPower(this.owner, this.owner, new StrengthPower(this.owner, -this.amount));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.duelist.applyPower(this.owner, this.owner, new StrengthPower(this.owner, -stackAmount));
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (this.amount2 < 1) {
            DuelistCard.removePower(this, this.owner);
        } else if (this.owner.hasPower(GravityAxePower.POWER_ID)) {
            DuelistCard.removePower(this, this.owner);
            this.duelist.applyPower(this.owner, this.owner, new StrengthPower(this.owner, this.amount));
        } else {
            this.amount2--;
            if (this.amount2 < 1) {
                DuelistCard.removePower(this, this.owner);
                this.duelist.applyPower(this.owner, this.owner, new StrengthPower(this.owner, this.amount));
            } else {
                updateDescription();
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount2 < 1) {
            DuelistCard.removePower(this, this.owner);
        }
        if (this.amount2 == 1) {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        }
    }

}
