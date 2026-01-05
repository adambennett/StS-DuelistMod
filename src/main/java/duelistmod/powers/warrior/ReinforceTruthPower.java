package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class ReinforceTruthPower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("ReinforceTruthPower");
    public static final String IMG = DuelistMod.makePowerPath("ReinforceTruthPower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public ReinforceTruthPower(final AbstractCreature owner, final int tempDexGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = tempDexGain;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == this.owner && power != null && DexterityPower.POWER_ID.equals(power.ID) && power.amount > 0 && this.amount > 0) {
            flash();
            this.duelist.applyPowerToSelf(new DexterityPower(this.owner, this.amount), this.owner);
            this.duelist.applyPowerToSelf(new LoseDexterityPower(this.owner, this.amount), this.owner);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
