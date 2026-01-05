package duelistmod.powers.warrior;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class WeaponChangePower extends DuelistPower {

    public static final String POWER_ID = DuelistMod.makeID("WeaponChangePower");
    public static final String IMG = DuelistMod.makePowerPath("WeaponChangePower.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AnyDuelist duelist;

    public WeaponChangePower(final AbstractCreature owner, final int dexGain) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = dexGain;
        this.duelist = AnyDuelist.from(owner);
        this.updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == this.owner && power != null && DexterityPower.POWER_ID.equals(power.ID) && power.amount > 0 && this.amount > 0) {
            flash();
            AnyDuelist.from(this.owner).drawTag(this.amount, Tags.WARRIOR);
        }
    }

    @Override
    public void updateDescription() {
        String s = this.amount == 1 ? DESCRIPTIONS[2] : DESCRIPTIONS[3];
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + s;
    }
}
