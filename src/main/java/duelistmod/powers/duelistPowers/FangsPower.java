package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.StrengthUpPower;

public class FangsPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("FangsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("FangsPower.png");
    private final AnyDuelist duelist;

	public FangsPower(AbstractCreature owner, AbstractCreature source, int fangs) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = fangs;
        this.updateDescription();
	}

    @Override
    public void atStartOfTurn() {
        if (this.amount > 0) {
            int strAmt = this.amount;
            if (this.duelist.hasPower(DreamingNemleriaPower.POWER_ID)) {
                strAmt *= 2;
            }
            this.duelist.applyPowerToSelf(new StrengthUpPower(duelist.creature(), duelist.creature(), strAmt));
            this.amount -= 2;
            if (this.amount < 1) {
                AnyDuelist duelist = AnyDuelist.from(this);
                AbstractPower instance = duelist.getPower(FangsPower.POWER_ID);
                DuelistCard.removePower(instance, duelist.creature());
            }
        }
        this.updateDescription();
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
