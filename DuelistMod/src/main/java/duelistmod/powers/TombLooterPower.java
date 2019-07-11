package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


public class TombLooterPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = duelistmod.DuelistMod.makeID("TombLooterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.ENERGY_TREASURE_POWER);

    public TombLooterPower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;       
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = 15 + newAmount;
        this.updateDescription();
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
    	if (this.amount > 0) 
    	{ 
    		int roll = AbstractDungeon.cardRandomRng.random(1, 10);
    		if (roll < 6)
    		{
	    		this.amount -= Math.floor(this.amount/3); 
	    		if (this.amount < 1) 
	    		{ 
	    			DuelistCard.removePower(this, AbstractDungeon.player); 
	    		} 
    		}
    		else if (roll == 7)
    		{
    			this.amount += 10;
    			this.flash();
    		}
    		
    		if (DuelistMod.debug) { System.out.println("theDuelist:EnergyTreasurePower:atEndOfTurn() ---> roll: " + roll); }
    	}
    	updateDescription();
    } 
    
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
    	if (DuelistCard.getSummons(AbstractDungeon.player) == DuelistCard.getMaxSummons(AbstractDungeon.player)) { DuelistCard.gainGold(this.amount, AbstractDungeon.player, true); }
    }
}
