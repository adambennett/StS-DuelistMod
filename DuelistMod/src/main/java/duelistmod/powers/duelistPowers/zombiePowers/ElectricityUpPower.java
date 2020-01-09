package duelistmod.powers.duelistPowers.zombiePowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;


public class ElectricityUpPower extends DuelistPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("StrengthUpPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("StrengthUpPower.png");

	public ElectricityUpPower(final AbstractCreature owner, final AbstractCreature source, int focGain) 
	{
		this(owner, source, 1, focGain);
	}
	
	public ElectricityUpPower(final AbstractCreature owner, final AbstractCreature source, int turns, int focGain) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = focGain;
		this.amount2 = turns;
		this.updateDescription();
	}

	@Override
	public void atEndOfRound() 
	{
		if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); DuelistCard.applyPower(new StrengthPower(this.owner, -this.amount), this.owner); }
		else
		{
			this.amount2--;
			if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); DuelistCard.applyPower(new StrengthPower(this.owner, -this.amount), this.owner); }
			else { updateDescription(); }
		}
	}

	@Override
	public void updateDescription() 
	{
		if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
		if (this.amount2 == 1) { this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]; }
		else { this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]; }
		
	}
}
