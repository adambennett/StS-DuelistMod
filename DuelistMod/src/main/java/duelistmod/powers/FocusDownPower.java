package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;


public class FocusDownPower extends TwoAmountPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("FocusDownPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("FocusDownPower.png");

	public FocusDownPower(final AbstractCreature owner, final AbstractCreature source, int turns, int focLoss) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = turns;
		this.amount2 = focLoss;
		this.updateDescription();
	}
  
	@Override
	public void onInitialApplication()
	{
		DuelistCard.applyPower(new FocusPower(this.owner, -this.amount2), this.owner); 
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		else
		{
			this.amount--;
			if (this.amount < 1) { DuelistCard.removePower(this, this.owner); DuelistCard.applyPower(new FocusPower(this.owner, this.amount2), this.owner); }
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
