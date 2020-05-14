package duelistmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class RetainForTurnsPower extends TwoAmountPower
{
	public static final String POWER_ID = DuelistMod.makeID("RetainForTurnsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public RetainForTurnsPower(AbstractCreature owner, int numCards, int turns) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;        
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.amount = numCards;
		this.amount2 = turns;
		this.updateDescription();
		this.loadRegion("retain");
	}

    @Override
    public void updateDescription() 
    {
    	if (this.amount < 0) { this.amount = 0; }
        if (this.amount == 1) 
        {
        	if (this.amount2 == 1)
        	{
        		 this.description = DESCRIPTIONS[5] + this.amount + DESCRIPTIONS[3];
        	}
        	else
        	{
        		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        	}
        }
        else 
        {
        	if (this.amount2 == 1)
        	{
        		this.description = DESCRIPTIONS[5] + this.amount + DESCRIPTIONS[4];
        	}
        	else
        	{
        		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4];
        	}
        }
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) 
    {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic("Runic Pyramid") && !AbstractDungeon.player.hasPower("Equilibrium") && this.amount2 > 0) 
        {
            AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(this.owner, this.amount));
            this.amount2--;
            updateDescription();
            if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
        }
        else if (this.amount2 < 1)
        {
        	DuelistCard.removePower(this, this.owner);
        }
    }
}
