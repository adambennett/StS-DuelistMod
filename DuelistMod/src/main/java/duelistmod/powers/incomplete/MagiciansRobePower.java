package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class MagiciansRobePower extends TwoAmountPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("MagiciansRobePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("MagicianRobePower.png");
	private boolean finished = false;
	
	public MagiciansRobePower(final AbstractCreature owner, final AbstractCreature source, int gainCharges, int heldCharges) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = gainCharges;
		this.amount2 = heldCharges;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		// Description Layout: Effect, singular, plural
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		if (this.amount != 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2;}
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2; }
		
	}
	
	@Override
	public void atStartOfTurn()
	{
		if (this.amount > 0)
		{
			this.amount2 += this.amount;
			updateDescription();
		}
	}
}
