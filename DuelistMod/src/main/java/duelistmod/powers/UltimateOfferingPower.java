package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.tokens.BloodToken;



public class UltimateOfferingPower extends TwoAmountPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("UltimateOfferingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("UltimateOfferingPowerB.png");
    
    private int startTurnReset = 0;
    
    public UltimateOfferingPower(final AbstractCreature owner, final AbstractCreature source, int incAndSummons, int hpLoss) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = incAndSummons;
        this.amount2 = hpLoss;
        this.startTurnReset = hpLoss;
        this.updateDescription();
    }
    
    public void onTriggerEffect()
    {
    	if (this.amount > 0)
    	{
    		AbstractPlayer p = AbstractDungeon.player;
	    	DuelistCard.incMaxSummons(p, this.amount);
	    	if (this.amount2 > 0) { DuelistCard.damageSelf(this.amount2); }
	    	DuelistCard.uoSummon(p, this.amount, new BloodToken());
	    	this.flash();
    	}
    	if (this.amount2 > 0) { this.amount2 -= 1; }
    	updateDescription();
    }
    
    @Override
    public void atStartOfTurn()
    {
    	this.amount2 = this.startTurnReset;
    	updateDescription();
    }

    @Override
	public void updateDescription() 
    {
    	if (this.amount2 < 0) { this.amount2 = 0; }
    	if (this.amount < 0)  { this.amount = 0; }
        this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }
}
