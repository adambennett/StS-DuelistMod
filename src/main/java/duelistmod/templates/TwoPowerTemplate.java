package duelistmod.templates;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class TwoPowerTemplate extends TwoAmountPower implements IShufflePower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("PowerTemplate");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
	
	public TwoPowerTemplate(final AbstractCreature owner, final AbstractCreature source, int amount, int amount2) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.amount2 = amount2;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		// Description Layout: Effect, singular A, singular B, plural A, plural B
		String effect = DESCRIPTIONS[0];
		String singularAmount = DESCRIPTIONS[1];
		String singularAmount2 = DESCRIPTIONS[2];
		String pluralAmount = DESCRIPTIONS[3];
		String pluralAmount2 = DESCRIPTIONS[4];
		
		// Plural A & Plural B
		if (this.amount > 2 && this.amount2 > 2)
		{
			this.description = effect + this.amount + pluralAmount + this.amount2 + pluralAmount2;
		}
		
		// Singular A & Singular B
		else if (this.amount < 2 && this.amount2 < 2)
		{
			this.description = effect + this.amount + singularAmount + this.amount2 + singularAmount2;
		}
		
		// Plural A & Singular B
		else if (this.amount > 2 && this.amount2 < 2)
		{
			this.description = effect + this.amount + pluralAmount + this.amount2 + singularAmount2;
		}
		
		// Singular A & Plural B
		else if (this.amount < 2 && this.amount2 > 2)
		{
			this.description = effect + this.amount + singularAmount + this.amount2 + pluralAmount2;
		}
	
	}

	public void atStartOfTurn() 
	{
		
		
	}

	@Override
	public void duringTurn() 
	{
		
		
	}

	@Override
	public void atStartOfTurnPostDraw()
	{
		
		
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		
		
	}

	@Override
	public void atEndOfRound() 
	{
		
		
	}

	@Override
	public void onDamageAllEnemies(int[] damage) 
	{
		
		
	}

	@Override
	public int onHeal(int healAmount) 
	{ 
		
		return healAmount; 
	}

	@Override
	public int onAttacked(DamageInfo info, int damageAmount) 
	{ 
		// Check if monster is attacking
		if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (!this.owner.hasPower("Buffer")))
    	{
			
    	}
		return damageAmount; 
	}

	@Override
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) 
	{
		
		
	}

	@Override
	public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) 
	{
		
		
	}

	@Override
	public void onEvokeOrb(AbstractOrb orb) 
	{
		
		
	}

	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) 
	{
		
		
	}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) 
	{
		
		
	}

	@Override
	public void onAfterUseCard(AbstractCard card, UseCardAction action) 
	{
		
		
	}

	@Override
	public void onSpecificTrigger() 
	{
		
		
	}

	@Override
	public void onDeath() 
	{
		
		
	}

	@Override
	public void onChannel(AbstractOrb orb) 
	{
		
		
	}

	@Override
	public void atEnergyGain() 
	{
		
		
	}

	@Override
	public void onExhaust(AbstractCard card)
	{
		
		
	}

	@Override
	public float modifyBlock(float blockAmount) { return blockAmount; }

	@Override
	public void onGainedBlock(float blockAmount) 
	{
		
		
	}

	@Override
	public int onPlayerGainedBlock(float blockAmount) 
	{ 
		
		return MathUtils.floor(blockAmount); 
	}

	@Override
	public int onPlayerGainedBlock(int blockAmount) 
	{ 
		
		return blockAmount; 
	}

	@Override
	public void onGainCharge(int chargeAmount) 
	{
		
		
	}

	@Override
	public void onRemove() 
	{
		
		
	}

	@Override
	public void onEnergyRecharge() 
	{
		
		
	}

	@Override
	public void onDrawOrDiscard() 
	{
		
		
	}

	@Override
	public void onAfterCardPlayed(AbstractCard usedCard) 
	{
		
		
	}

	@Override
	public void onInitialApplication() 
	{
		
		
	}
	
	@Override
	public void onShuffle() 
	{
		
		
	}
}
