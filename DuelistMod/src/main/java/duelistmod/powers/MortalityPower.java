package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class MortalityPower extends DuelistPower 
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

    
    @Override
    public void onResummon(DuelistCard resummoned) 
    {
    	if (this.amount > 0) { this.amount--; }
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		updateDescription();
    }
    
    @Override
    public boolean allowResummon(AbstractCard resummoningCard) { return false; }
	
    
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
