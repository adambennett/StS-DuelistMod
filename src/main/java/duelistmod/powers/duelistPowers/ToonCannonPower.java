package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.*;



public class ToonCannonPower extends DuelistPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonCannonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_CANNON_SOLDIER_POWER);
    
    public ToonCannonPower(final AbstractCreature owner, final AbstractCreature source, int toonDmg) 
    {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = toonDmg;
        this.updateDescription();
    }
    
	@Override
	public void onSummon(DuelistCard c, int amt)
	{
		if (c.hasTag(Tags.TOON_POOL) && amt > 0)
		{
			DuelistCard.damageAllEnemiesThornsPoison(this.amount);
		}
	}
    
  
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
