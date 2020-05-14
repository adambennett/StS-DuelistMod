package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Frost;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.*;



public class BlizzardDragonPower extends DuelistPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("BlizzardDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.BLIZZARD_DRAGON_POWER);
    
    public BlizzardDragonPower(final AbstractCreature owner, final AbstractCreature source) 
    {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
    }
    
	@Override
	public void onSummon(DuelistCard c, int amt)
	{
		if (c.hasTag(Tags.DRAGON) && amt > 0)
		{
			DuelistCard.channel(new Frost(), amt);
		}
	}
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) 
    {
    	if (isPlayer)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    }    
  
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0];
    }
}
