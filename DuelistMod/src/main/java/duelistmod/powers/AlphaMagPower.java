package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.incomplete.MagneticFieldPower;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class AlphaMagPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("AlphaMagPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.ALPHA_MAG_POWER);
    private boolean electrified = false;

    public AlphaMagPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
        if (owner.hasPower(MagneticFieldPower.POWER_ID)) { this.electrified = true; this.amount = 3; DuelistCard.removePower(owner.getPower(MagneticFieldPower.POWER_ID), owner); }
        this.updateDescription();
    }
    
    public void electrify(int amount)
    {
    	this.amount = amount;
    	this.electrified = true;
    	updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.electrified && c.type.equals(CardType.ATTACK) && this.amount > 0)
    	{
    		DuelistCard.channel(new Lightning());
    		this.amount--;
    		updateDescription();
    	}
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 1) { this.amount = 0; this.electrified = false; }
    	if (this.electrified) 
    	{ 
    		if (this.amount != 1) { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]; }
    		else { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]; }
    	}
    	else { this.description = DESCRIPTIONS[0]; }
    }
}
