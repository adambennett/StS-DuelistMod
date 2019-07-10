package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ToonWorldPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonWorldPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    public int lowend = 1;
    
    public ToonWorldPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;        
        if (DuelistMod.challengeMode) { this.amount += 2; }
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 5) { this.amount = 5; updateDescription(); }
    	if (AbstractDungeon.player.hasPower(ToonKingdomPower.POWER_ID))
    	{
    		DuelistCard.removePower(this, AbstractDungeon.player);
    	}
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount > 5) { this.amount = 5; updateDescription(); }
    	if (AbstractDungeon.player.hasPower(ToonKingdomPower.POWER_ID))
    	{
    		DuelistCard.removePower(this, AbstractDungeon.player);
    	}
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 5) { this.amount = 5; updateDescription(); }
    	if (AbstractDungeon.player.hasPower(ToonKingdomPower.POWER_ID))
    	{
    		DuelistCard.removePower(this, AbstractDungeon.player);
    	}
    	else
    	{
	    	if (c.hasTag(Tags.TOON) && !c.originalName.equals("Toon World") && !c.originalName.equals("Toon Kingdom")) 
	    	{ 
	    		if (this.amount > 0) { DuelistCard.damageSelf(this.amount); }
	    	}
	    	updateDescription();
    	}
    }

    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (AbstractDungeon.player.hasPower(ToonKingdomPower.POWER_ID))
    	{
    		DuelistCard.removePower(this, AbstractDungeon.player);
    	}
    	else
    	{
    		if (this.amount > 5) { this.amount = 5; updateDescription(); }
    		if (this.amount == 5)
    		{
    			int dmgRoll = AbstractDungeon.cardRandomRng.random(lowend, 5);
        		this.amount = dmgRoll;
    		}
    		
    		else if (this.amount > 0)
    		{
    			int dmgRoll = AbstractDungeon.cardRandomRng.random(lowend, this.amount + 1);
        		this.amount = dmgRoll;
    		}
    		else
    		{
    			int dmgRoll = AbstractDungeon.cardRandomRng.random(0, 1);
        		this.amount = dmgRoll;
    		}    		
    		updateDescription();
    	}
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount > 5) { this.amount = 5; }
    	if (this.amount == 5) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + lowend + DESCRIPTIONS[2] + 5 + DESCRIPTIONS[4]; }
    	else if (this.amount < 1) { this.description = DESCRIPTIONS[3]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + lowend + DESCRIPTIONS[2] + (this.amount + 1) + DESCRIPTIONS[4]; }
    }
}
