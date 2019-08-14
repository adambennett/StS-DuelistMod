package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.incomplete.MagneticFieldPower;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class GammaMagPower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("GammaMagPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.GAMMA_MAG_POWER);
    private boolean electrified = false;

    public GammaMagPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
        this.amount2 = 0;
        if (owner.hasPower(MagneticFieldPower.POWER_ID)) { this.electrified = true; this.amount2 = 2; this.amount = 3; DuelistCard.removePower(owner.getPower(MagneticFieldPower.POWER_ID), owner); }
        this.updateDescription();

    }
    
    public void electrify(int cards, int turns)
    {
    	this.electrified = true;
    	this.amount = turns;
    	this.amount2 = cards;
    	updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.electrified && c.type.equals(CardType.POWER) && this.amount > 0)
    	{
    		DuelistCard.draw(this.amount2);
    		this.amount--;
    		updateDescription();
    	}
    }   
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount < 1) { this.electrified = false; this.amount = 0; this.amount2 = 0; }
    	if (this.electrified)
    	{
    		if (this.amount != 1) { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3]; }
    		else { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[4]; }
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0];
    	}
    }
}
