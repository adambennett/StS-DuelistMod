package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.*;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ToonKingdomPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonKingdomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    public int lowend = 0;
    public int maxDmg = 2;
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source, int amt) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amt;
        this.lowend = 0;
        this.maxDmg = 2;
        this.updateDescription();
    }
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source, int amt, int lowend, int maxdmg) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amt;
        this.lowend = lowend;
        this.maxDmg = maxdmg;
        this.updateDescription();
    }
    
   
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > maxDmg) { this.amount = maxDmg; updateDescription(); }
    	if (c.hasTag(Tags.TOON) && !c.hasTag(Tags.TOON_DONT_TRIG))
    	{ 
    		if (this.amount > 0) { DuelistCard.damageSelfNotHP(this.amount); }
    	}
    	updateDescription();
    	
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{    	
		if (this.amount > maxDmg) { this.amount = maxDmg; updateDescription(); }
		int dmgRoll = AbstractDungeon.cardRandomRng.random(lowend, maxDmg);
    	this.amount = dmgRoll;    		 		
		updateDescription();    	
	}

    @Override
    public void onInitialApplication()
    {
    	AbstractDungeon.player.hand.glowCheck();
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount > maxDmg) { this.amount = maxDmg; }
    	if (this.amount < 1) { this.description = DESCRIPTIONS[3]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + lowend + DESCRIPTIONS[2] + maxDmg + DESCRIPTIONS[4]; }
    }
}
