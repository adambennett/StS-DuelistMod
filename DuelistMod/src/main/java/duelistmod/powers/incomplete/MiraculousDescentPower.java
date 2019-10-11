package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;

public class MiraculousDescentPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("MiraculousDescentPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("MiraculousDescentPower.png");
	
	public boolean upgrade = false;
	
	public MiraculousDescentPower(final AbstractCreature owner, final AbstractCreature source, boolean upgraded, int amt) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amt;
		this.upgrade = upgraded;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.upgrade)
		{
			if (this.amount == 0) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3]; }
			else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[4]; }
		}
		else
		{
			if (this.amount == 0) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
			else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		}

	}

	@Override
	public void atStartOfTurn()
	{
		for (int i = 0; i < this.amount; i++)
		{
			AbstractCard spec = Util.getSpecialCardForMiracleDescent();
			if (this.upgrade) { spec.upgrade(); }
			DuelistCard.addCardToHand(spec);
		}
		updateDescription();
	}
}
