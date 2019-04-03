package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;

public class SliferSkyPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SliferSkyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.SLIFER_SKY_POWER);
    public boolean triggerAllowed = true;
    public int turnTriggers = 2;
    public int turnTriggerReset = 2;
    public SliferSkyPower(AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.amount = newAmount;
        this.triggerAllowed = true;
        this.turnTriggers = newAmount;
        this.turnTriggerReset = newAmount;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount != turnTriggers) { this.amount = turnTriggers; }
    	if (turnTriggers < 1) { triggerAllowed = false; }
    }

    @Override
    public void atStartOfTurn() 
    {
    	triggerAllowed = true;
    	turnTriggers = turnTriggerReset;
    	if (this.amount != turnTriggers) { this.amount = turnTriggers; }
    	if (turnTriggers < 1) { triggerAllowed = false; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != turnTriggers) { this.amount = turnTriggers; }
    	if (turnTriggers < 1) { triggerAllowed = false; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount != turnTriggers) { this.amount = turnTriggers; }
    	if (turnTriggers < 1) { triggerAllowed = false; }
	}
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount != turnTriggers) { this.amount = turnTriggers; }
    	if (turnTriggers < 1) { triggerAllowed = false; }
    	this.description = DESCRIPTIONS[0] + turnTriggers + DESCRIPTIONS[1];
    }
}
