package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class GreedShardPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("GreedShardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.GREED_SHARD_POWER);
    
    private static int turnCounter = 0;

    public GreedShardPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = 1;
        turnCounter = 1;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
    }
   
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
	}

    
    @Override
    public void atStartOfTurn() 
    {
    	turnCounter++;
    	if (turnCounter > 3) 
    	{ 
    		turnCounter = 1; 
    		DuelistCard.draw(1);
    		//System.out.println("theDuelist:GreedShardPower --- > Drew 2 cards!");
    	}
    	DuelistCard.draw(1);
    	if (this.amount != turnCounter) { this.amount = turnCounter; }
    	updateDescription();
    }

    @Override
	public void updateDescription() 
    {
        this.description = DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1] + turnCounter;
    }
}
