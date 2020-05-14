package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
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
public class SpikedGillmanPower extends AbstractPower implements IShufflePower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("SpikedGillmanPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("SpikedGillmanPower.png");
	private boolean finished = false;
	
	public SpikedGillmanPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
		// Description Layout: Effect, singular, plural
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void atStartOfTurn() 
	{
		
		
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) 
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
		// Use bool to ensure no infinite channeling
		/*
		if (!finished) 
    	{ 
	    	DuelistCard.channelRandom();    		
    	}
    	finished = true;
    	*/
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
	public float modifyBlock(float blockAmount) 
	{
		
		return blockAmount; 
	}

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
