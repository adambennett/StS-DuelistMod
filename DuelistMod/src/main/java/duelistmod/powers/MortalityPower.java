package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;

public class MortalityPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("MortalityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("HauntedPower.png");

    public MortalityPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.updateDescription();
    }
    
    public void triggerOnResummon()
    {
    	if (this.amount > 0) { this.amount--; }
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		updateDescription();
    }
    
    @Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (this.amount > 0) { this.amount--; }
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		updateDescription();
	}
    
    @Override
	public void updateDescription() 
    {
        this.description = DESCRIPTIONS[0] + this.amount;
    }
}
