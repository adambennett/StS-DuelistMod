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
import duelistmod.relics.NaturiaRelic;


public class NaturiaPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("NaturiaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.NATURIA_POWER);

    public NaturiaPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    	if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
    	{
    		SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			{
				int naturias = instance.getNumberOfTypeSummoned(Tags.NATURIA);
				if (naturias > 0)
				{
					DuelistCard.damageAllEnemiesThorns(this.amount * DuelistMod.naturiaDmg);
				}
			}
    	}
	}

    @Override
	public void updateDescription() 
    {
    	if (AbstractDungeon.player.hasRelic(NaturiaRelic.ID))
    	{
    		this.description = DESCRIPTIONS[2] + this.amount * 2 + DESCRIPTIONS[1];  
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];  
    	}    	
    }
}
