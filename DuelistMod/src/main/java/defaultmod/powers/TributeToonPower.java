package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;



public class TributeToonPower extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("TributeToonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.TRIBUTE_TOON_POWER);
    
    public TributeToonPower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = newAmount;
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount >= 2) { this.amount -= 2; }
    	else if (this.amount > 0) { this.amount--; }
    	else 
    	{ 
    		if (AbstractDungeon.player.hasPower(TributeToonPower.POWER_ID)) 
    		{
    			AbstractPower instance = AbstractDungeon.player.getPower(TributeToonPower.POWER_ID);
    			DuelistCard.removePower(instance, AbstractDungeon.player);
    		}
    	}
    	
    	updateDescription();
	}
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	updateDescription();
    }
    

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
