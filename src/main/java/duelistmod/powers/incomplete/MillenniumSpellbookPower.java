package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.Boot;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

public class MillenniumSpellbookPower extends TwoAmountPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("MillenniumSpellbookPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("MillenniumSpellbookPower.png");
	private int dmg = 150;
	
	public MillenniumSpellbookPower(final AbstractCreature owner, final AbstractCreature source, int damageReq, int turns) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = damageReq;
		this.amount2 = turns;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		// Description Layout: Effect, singular, plural
		if (this.amount2 > 1 && this.amount < 1) { this.description = DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[5]; }
		else if (this.amount < 1) { this.description = DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[4]; }
		else if (this.amount2 > 1) { this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + this.amount; }
		else { this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount; }
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		// No turns remain, damage required still greater than 0
		if (this.amount2 < 1 && this.amount > 0)
		{
			DuelistCard.removePower(this, this.owner);
		}
		
		// No turns remain, damage required is met
		else if (this.amount2 < 1 && this.amount < 1)
		{
			DuelistCard.removePower(this, this.owner);
			triggerEffect();
		}
		
		// Turns still remaining
		else if (this.amount2 > 0)
		{
			// Decrement turns remaining by 1 at the end of turn, then check if turns left is 0 now
			// If turns remaining is now 0, remove this but check one more time to see if damage requirement is met
			this.amount2--;
			if (this.amount2 < 1 && this.amount > 0)
			{
				DuelistCard.removePower(this, this.owner);
			}
			else if (this.amount2 < 1 && this.amount < 1)
			{
				DuelistCard.removePower(this, this.owner);
				triggerEffect();
			}
		}	
		
		updateDescription();
	}
	
	private void triggerEffect()
	{
		DuelistCard.damageAllEnemiesThornsFire(dmg);
	}

	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) 
	{
		if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL)
		{
			if (AbstractDungeon.player.hasRelic(Boot.ID) && damageAmount < 5) 
			{
				this.flash();
				this.amount -= 5;
				if (this.amount < 0) { this.amount = 0; }
			}
			else if (damageAmount > target.currentHealth) 
			{
				this.flash();
				this.amount -= target.currentHealth;
				if (this.amount < 0) { this.amount = 0; }
			}
			else 
			{
				this.flash();
				this.amount -= damageAmount;
				if (this.amount < 0) { this.amount = 0; }
			}
		}
		updateDescription();
	}
}
