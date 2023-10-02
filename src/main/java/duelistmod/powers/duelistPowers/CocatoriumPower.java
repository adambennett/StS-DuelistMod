package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;

public class CocatoriumPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CocatoriumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");

	public CocatoriumPower(AbstractCreature owner, AbstractCreature source, int beastsCheck) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = beastsCheck;
		updateDescription(); 
	}

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        AnyDuelist duelist = AnyDuelist.from(this);
        int amt = DuelistMod.uniqueBeastsPlayedThisTurn.size() * this.amount;
        if (amt > 0) {
            duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), amt));
        }
    }

	@Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]) + DESCRIPTIONS[3];
	}
}
