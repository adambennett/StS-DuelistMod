package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;



public class TributeToonPowerB extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("TributeToonPowerB");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TRIBUTE_TOON_POWER);
    
    public TributeToonPowerB(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = newAmount;
        this.updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount >= 2) 
    	{ 
    		this.amount -= 2; 
    		if (this.amount < 1)
    		{
    			AbstractPower instance = AbstractDungeon.player.getPower(TributeToonPowerB.POWER_ID);
    			DuelistCard.removePower(instance, AbstractDungeon.player);
    		}
    	}
    	else if (this.amount > 0) 
    	{ 
    		this.amount--; 
    		if (this.amount < 1)
    		{
    			AbstractPower instance = AbstractDungeon.player.getPower(TributeToonPowerB.POWER_ID);
    			DuelistCard.removePower(instance, AbstractDungeon.player);
    		}
    	}
    	else 
    	{ 
    		if (AbstractDungeon.player.hasPower(TributeToonPowerB.POWER_ID)) 
    		{
    			AbstractPower instance = AbstractDungeon.player.getPower(TributeToonPowerB.POWER_ID);
    			DuelistCard.removePower(instance, AbstractDungeon.player);
    		}
    	}
    	
    	updateDescription();
	}
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	updateDescription();
    }
    

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0];
    }
}
