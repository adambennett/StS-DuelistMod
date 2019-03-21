package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.GreatMoth;
import defaultmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class CocoonPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("CocoonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.COCOON_POWER);
    
    private static int turnCounter = 0;
    private static int triggerTurn = 3;
    private boolean addedMoth = false;

    public CocoonPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 0;
        if (DefaultMod.challengeMode)
        {
        	triggerTurn = 4;
        }
        this.updateDescription();
    }
    
    public CocoonPower(final AbstractCreature owner, final AbstractCreature source, int startingTurn, int turnToTrigger) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = startingTurn;
        turnCounter = startingTurn;
        triggerTurn = turnToTrigger;
        if (DefaultMod.challengeMode)
        {
        	triggerTurn++;
        }
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	updateDescription();
    }
   
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	updateDescription();
	}
    
    @Override
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	updateDescription();
    }

    @Override
    public void atStartOfTurn() 
    {
    	turnCounter++;
    	if (turnCounter >= triggerTurn && !addedMoth) 
    	{ 
    		DuelistCard.addCardToHand(new GreatMoth());
    		DuelistCard.removePower(this, this.owner);
    		addedMoth = true;
    	}
    	else if (turnCounter >= triggerTurn && addedMoth)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
    	updateDescription();
    }

    @Override
	public void updateDescription() 
    {
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
    	if (turnCounter >= triggerTurn && !addedMoth) 
    	{ 
    		DuelistCard.addCardToHand(new GreatMoth());
    		DuelistCard.removePower(this, this.owner);
    		addedMoth = true;
    	}
    	else if (turnCounter >= triggerTurn && addedMoth)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    	else if (triggerTurn - turnCounter == 1) { this.description = DESCRIPTIONS[0] + (triggerTurn - turnCounter) + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + (triggerTurn - turnCounter) + DESCRIPTIONS[2]; }
    }
}
