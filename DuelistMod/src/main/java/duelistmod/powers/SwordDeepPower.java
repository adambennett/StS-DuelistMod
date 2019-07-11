package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class SwordDeepPower extends AbstractPower 
{
	public AbstractCreature source;

	public static final String POWER_ID = DuelistMod.makeID("SwordDeepPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.DESPAIR_POWER);
	private static int STR_LOSS = 5;

	public SwordDeepPower(final AbstractCreature owner, final AbstractCreature source, int newAmount, int strLoss) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;		
		this.type = PowerType.DEBUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = newAmount;
		STR_LOSS = strLoss;
		this.updateDescription();
	}
	
	@Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
    }
	
	@Override
    public void atStartOfTurn() 
    {
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
    }
    
	// At the end of the turn, remove gained Strength.
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		else if (AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID))
		{
			GravityAxePower power = (GravityAxePower) AbstractDungeon.player.getPower(GravityAxePower.POWER_ID);
			power.onSpecificTrigger();
			DuelistCard.removePower(this, this.owner);
		}
		else
		{
			for (int i = 0; i < this.amount; i++)
			{
				DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, -1 * STR_LOSS));
			}
			
			DuelistCard.removePower(this, this.owner);
		}
	}
	
	@Override
	public void onVictory()
	{

	}
	
	@Override
	public void onDeath()
	{
	
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + STR_LOSS * this.amount + DESCRIPTIONS[1];
	}
}
