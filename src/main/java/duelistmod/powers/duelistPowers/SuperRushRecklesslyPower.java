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

public class SuperRushRecklesslyPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SuperRushRecklesslyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SuperRushRecklesslyPower.png");
    private final AnyDuelist duelist;

	public SuperRushRecklesslyPower(AbstractCreature owner, AbstractCreature source, int dmgMod, int turns) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = dmgMod;
        this.amount2 = turns;
		updateDescription();
	}

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        this.amount2--;
        if (this.amount2 <= 0) {
            AbstractPower instance = this.duelist.getPower(SuperRushRecklesslyPower.POWER_ID);
            DuelistCard.removePower(instance, this.duelist.creature());
        }
        this.updateDescription();
    }

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[this.amount2 == 1 ? 2 : 3];
	}
}
