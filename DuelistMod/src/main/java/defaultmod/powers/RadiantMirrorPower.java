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


public class RadiantMirrorPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DefaultMod.makeID("RadiantMirrorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.RADIANT_POWER);
    
    public boolean upgrade = false;
    public int increment = 2;

    public RadiantMirrorPower(AbstractCreature owner, boolean upgrade, int increments) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.upgrade = upgrade;
        this.type = PowerType.BUFF;
        increment = increments;
        this.amount = increments;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
	}

    @Override
    public int onLoseHp(int damageAmount)
    {
    	DuelistCard.incMaxSummons(AbstractDungeon.player, increment);
    	return damageAmount;
    }
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + increment + DESCRIPTIONS[1]; 
    }
}
