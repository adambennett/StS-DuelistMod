package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class ManaPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("ManaPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ManaPower.png");
	private boolean finished = false;
	
	public ManaPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
		if (this.amount > -1)
		{
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}
		else
		{
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
		}
	}

	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			this.amount = pow.getNumberOfTypeSummoned(Tags.SPELLCASTER);
			if (this.amount == 0) { DuelistCard.removePower(this, this.owner); }
			updateDescription();
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
	}
}
