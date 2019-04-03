package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;

// Passive no-effect power, just lets Toon Monsters check for playability

public class CardSafePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("CardSafePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CARD_SAFE_POWER);

    public CardSafePower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    	
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
        
    }
}
