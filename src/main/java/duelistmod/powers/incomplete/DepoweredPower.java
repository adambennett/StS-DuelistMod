package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class DepoweredPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("DepoweredPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("DepoweredPower.png");
	private boolean finished = false;
	
	public DepoweredPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.amount != 1)
		{
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
		}
		else
		{
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}
	}

	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (this.amount > 0)
		{
			this.amount--;
			updateDescription();
			if (this.amount == 0)
			{
				DuelistCard.removePower(this, this.owner);
			}
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
	}
}
