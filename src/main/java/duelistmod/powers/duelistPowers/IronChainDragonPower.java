package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.dto.AnyDuelist;

public class IronChainDragonPower extends NoStackDuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("IronChainDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("IronChainDragonPower.png");
	private final AnyDuelist duelist;

	public IronChainDragonPower(AbstractCreature owner, AbstractCreature source, int amt) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
		this.duelist = AnyDuelist.from(this);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		this.amount = amt;
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!this.duelist.hasBlock() && this.amount > 0) {
			this.duelist.applyPower(this.duelist.creature(), this.duelist.creature(), new PlatedArmorPower(this.duelist.creature(), this.amount));
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

}
