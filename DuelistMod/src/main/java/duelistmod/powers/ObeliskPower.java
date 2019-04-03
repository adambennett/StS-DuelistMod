package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;

public class ObeliskPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ObeliskPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.OBELISK_POWER);
    public int DAMAGE = 6;

    public ObeliskPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    	if (this.amount != DAMAGE) { DAMAGE = this.amount; }
    	updateDescription();
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount != DAMAGE) { DAMAGE = this.amount; }
    	updateDescription();
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != DAMAGE) { DAMAGE = this.amount; }
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount != DAMAGE) { DAMAGE = this.amount; }
    	updateDescription();
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount != DAMAGE) { DAMAGE = this.amount; }
        this.description = DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }
}
