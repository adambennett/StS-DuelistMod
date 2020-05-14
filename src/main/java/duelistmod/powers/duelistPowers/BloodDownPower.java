package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;


public class BloodDownPower extends DuelistPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("BloodDownPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("BloodDownPower.png");

	public BloodDownPower(final AbstractCreature owner, final AbstractCreature source, int newAmount, int strLoss) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = newAmount;
		this.amount2 = strLoss;
		this.updateDescription();
	}
  
	@Override
	public void onInitialApplication()
	{
		DuelistCard.applyPower(new BloodPower(this.owner, this.owner, -this.amount2), this.owner); 
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		else
		{
			this.amount--;
			if (this.amount < 1) { DuelistCard.removePower(this, this.owner); DuelistCard.applyPower(new BloodPower(this.owner, this.owner, this.amount2), this.owner); }
			else { updateDescription(); }
		}
	}

	@Override
	public void updateDescription() 
	{
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3]; }
		
	}
}
