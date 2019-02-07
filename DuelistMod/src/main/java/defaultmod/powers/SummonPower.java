package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;

public class SummonPower extends AbstractPower
{
	public static final String POWER_ID = defaultmod.DefaultMod.makeID("SummonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.SUMMON_POWER);

	public int MAX_SUMMONS = 5;
	public int test = 5;

	public SummonPower(AbstractCreature owner, int newAmount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		updateCount(this.amount);
		updateDescription();
	}

	public SummonPower(AbstractCreature owner, int newAmount, String desc) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		this.description = desc;
		updateCount(this.amount);
		updateDescription();
	}

	public SummonPower(AbstractCreature owner, int newMax, boolean increment) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.img = new Texture(IMG);
		if (increment) { MAX_SUMMONS += newMax; }
		else { MAX_SUMMONS = newMax; }
		updateCount(this.amount);
		updateDescription();
	}

	@Override
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateDescription();
	}


	@Override
	public void updateDescription() 
	{
		if (this.amount > 0)
		{
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2];
		}
		else
		{
			this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2];
		} 
	}

	public void updateCount(int amount)
	{
		if (this.amount > MAX_SUMMONS) { this.amount = MAX_SUMMONS; }
		if (this.amount < 0) { this.amount = 0; }
	}
	

}