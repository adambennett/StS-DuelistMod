package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.patches.DuelistCard;

// 

public class SwordsBurnPower extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SwordsBurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.SWORDS_BURN_POWER);
    public boolean finished = false;
    
    public SwordsBurnPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = newAmount;
        this.updateDescription();
    }
 
    @Override
    public void onDrawOrDiscard() 
    {
    	
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	finished = false;
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	finished = false;
    }
    
    @Override
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	if (!finished) 
    	{ 
    		for (int i = 0; i < this.amount; i++)
    		{
	    		DuelistCard.channelRandom();
    		}
    	}
    	finished = true;
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	finished = false;
	}
    

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    }
}
