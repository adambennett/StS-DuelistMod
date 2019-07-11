package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

// 

public class SwordsRevealPower extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("SwordsRevealPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.SWORDS_REVEAL_POWER);
    
    public SwordsRevealPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    public int onLoseHp(int damageAmount)
    {
    	if (this.amount > 0) { return 0; }
    	else { return damageAmount; }
    }
    
    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
    	if (this.amount > 0) { return 0; }
    	else { return damageAmount; }
    }
 
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
   
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	else 
    	{
    		this.amount--;
    	}
    	updateDescription();
	}
    

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
