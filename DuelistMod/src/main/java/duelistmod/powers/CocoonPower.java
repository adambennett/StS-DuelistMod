package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.insects.GreatMoth;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class CocoonPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("CocoonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.COCOON_POWER);
    
    public CocoonPower(final AbstractCreature owner, final AbstractCreature source, int turns) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
        this.updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount--; updateDescription();}
    	else { DuelistCard.removePower(this, this.owner); }    	
	}

    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount == 0) 
    	{ 
    		DuelistCard.addCardToHand(new GreatMoth());
    		DuelistCard.removePower(this, this.owner);
    	}
    	else { updateDescription(); }    	
    }

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 0) { this.amount = 0; }
    	if (this.amount == 0) { this.description = DESCRIPTIONS[3]; }
    	else if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    	
    }
}
