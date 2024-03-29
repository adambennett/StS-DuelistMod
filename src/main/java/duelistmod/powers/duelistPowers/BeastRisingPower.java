package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class BeastRisingPower extends DuelistPower {
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("BeastRisingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("BeastRisingPower.png");

	public BeastRisingPower(AbstractCreature owner, AbstractCreature source, int tributeMultiplier) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
		this.amount = tributeMultiplier;
		updateDescription();
	}

	@Override
	public int modifyTributeCost(AnyDuelist duelist, DuelistCard card, boolean summonChallenge, int current) {
		if (card.hasTag(Tags.BEAST) && this.amount > 1) {
			int total = current * this.amount;
			return total - current;
		}
		return current;
	}

	@Override
	public void updateDescription() {
		if (this.amount > 0) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		} else {
			this.description = DESCRIPTIONS[2];
		}
	}
}
