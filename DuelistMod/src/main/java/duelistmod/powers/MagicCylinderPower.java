package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Strings;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class MagicCylinderPower extends DuelistPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("MagicCylinderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.MAGIC_CYLINDER_POWER);
    
    public boolean upgraded = false;
    public int MIN_TURNS = 1;
    public int MAX_TURNS = 3;

    public MagicCylinderPower(AbstractCreature owner, AbstractCreature source, int amt) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.canGoNegative = false;
        this.amount = amt;
        this.updateDescription();
    }
    

    @Override
    public int onLoseHp(int damageAmount)
    {
    	if (this.amount > 0 && damageAmount > 0) 
    	{ 
    		int lowRoll = 1;
    		int highRoll = damageAmount + this.amount;
    		int hpRoll = AbstractDungeon.cardRandomRng.random(lowRoll, highRoll);
    		DuelistCard.gainTempHP(hpRoll);
    		this.flash();
    	}
    	return damageAmount;
    }
    
    @Override
	public void updateDescription() 
    {    	
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];    	
    }
}
