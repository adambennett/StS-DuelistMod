package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.incomplete.MagneticFieldPower;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class BetaMagPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("BetaMagPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.BETA_MAG_POWER);
    private boolean electrified = false;

    public BetaMagPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
        if (owner.hasPower(MagneticFieldPower.POWER_ID)) { this.electrified = true; DuelistCard.removePower(owner.getPower(MagneticFieldPower.POWER_ID), owner); }
        this.updateDescription();

    }
    
    public void electrify(int newAmount)
    {
    	this.electrified = true;
    	this.amount = newAmount;
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
    	if (c.type.equals(CardType.SKILL) && this.electrified && this.amount > 0)
    	{
    		DuelistCard.staticBlock(this.amount);
    	}
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{

	}

    @Override
	public void updateDescription() 
    {
    	if (this.electrified) { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0]; }
    }
}
