package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


public class FocusLossPower extends AbstractPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("FocusLossPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("FocusDownPower.png");

	public FocusLossPower(final AbstractCreature owner, final AbstractCreature source, int focLoss) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = focLoss;
		this.updateDescription();
	}

	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		DuelistCard.applyPower(new FocusPower(this.owner, -this.amount), this.owner);
		DuelistCard.removePower(this, this.owner); 
	}

	@Override
	public void updateDescription() 
	{
		if (this.amount >= 0) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[2] + -this.amount + DESCRIPTIONS[1]; }		
	}
}
