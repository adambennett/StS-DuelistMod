package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.ModifyTributeAction;


public class DarknessNeospherePower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DarknessNeospherePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DarknessNeospherePower.png");
    public int turnDmg = 3;

    public DarknessNeospherePower(AbstractCreature owner, AbstractCreature source, int strLoss, int tribInc) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.source = source;
        this.owner = owner;
        this.amount = strLoss;
        this.amount2 = tribInc;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{    	
    	// If strength down turns remaining, remove 1 strength from all alive enemies
		if (amount > 0)
		{
	    	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
			{
	    		if (!m.isDeadOrEscaped() && !m.isDying)
	    		{
	    			DuelistCard.applyPower(new StrengthPower(m, -1), m);
	    		}
			}
		}
    	
		// If tribute up turns remaining, increase all tribute monsters in draw pile tribute cost by 1
		if (amount2 > 0)
		{
	    	for (AbstractCard c : AbstractDungeon.player.drawPile.group)
	    	{
	    		if (c instanceof DuelistCard)
	    		{
	    			DuelistCard dC = (DuelistCard)c;
	    			if (dC.baseTributes > 0)
	    			{
	    				AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, 1, true));
	    			}
	    		}
	    	}
		}
		
		// Decrement either amount if it is positive
    	if (this.amount > 0) 	{ this.amount--; 	}
    	if (this.amount2 > 0) 	{ this.amount2--; 	}
    	
    	// If both effects turns have ended, remove this power
    	if (this.amount < 1 && this.amount2 < 1)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    
    	this.updateDescription();
    }
    
    @Override
	public void updateDescription() 
    {
    	String pluralStr = DESCRIPTIONS[1];
    	String pluralTrib = DESCRIPTIONS[2];
    	String singStr = DESCRIPTIONS[3];
    	String singTrib = DESCRIPTIONS[4];
    	
    	// Both plural
    	if (this.amount2 > 1 && this.amount > 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + pluralStr + this.amount2 + pluralTrib;
    	}
    	
    	// Tribute turns remaining is singular, strength down turns is plural
    	else if (this.amount2 == 1 && this.amount > 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + pluralStr + this.amount2 + singTrib;
    	}
    	
    	// Tribute turns remaining is plural, strength down turns remaining is singular
    	else if (this.amount2 > 1 && this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + singStr + this.amount2 + pluralTrib;
    	}
    	
    	else if (this.amount2 == 0 && this.amount > 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + pluralStr;
    	}
    	
    	else if (this.amount == 0 && this.amount2 > 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + pluralTrib;
    	}
    	
    	else if (this.amount2 == 0 && this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + singStr;
    	}
    	
    	else if (this.amount == 0 && this.amount2 == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + singTrib;
    	}
    	
    	// Both singular
    	else
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + singStr + this.amount2 + singTrib;
    	}
    }
}
