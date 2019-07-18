package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class HeartUndertrapPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("HeartUndertrap");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.HEART_UNDERDOG_POWER);

    public HeartUndertrapPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
        this.amount = amount;
        this.canGoNegative = false;
        //if (owner.hasPower(HeartUnderdogPower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUnderdogPower.POWER_ID), owner); }
        //if (owner.hasPower(HeartUnderspellPower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUnderspellPower.POWER_ID), owner); }
       // if (owner.hasPower(HeartUndertributePower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUndertributePower.POWER_ID), owner); }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount--; updateDescription(); if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }  }
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount != 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
        
    }
}
