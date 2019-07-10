package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;


public class RadiantMirrorPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("RadiantMirrorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.RADIANT_POWER);
    
    public boolean upgrade = false;

    public RadiantMirrorPower(AbstractCreature owner, AbstractCreature source, int increments) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.amount = increments;
        this.source = source;
        this.canGoNegative = true;
        this.updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount)
    {
    	if (this.amount > 0)
    	{
    		DuelistCard.incMaxSummons(AbstractDungeon.player, this.amount);
    	}
    	else if (this.amount < 0)
    	{
    		DuelistCard.decMaxSummons(AbstractDungeon.player, -this.amount);
    	}
    	return damageAmount;
    }
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; 
    }
}
