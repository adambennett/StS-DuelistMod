package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.interfaces.RandomActionHelper;
import defaultmod.patches.DuelistCard;

public class TimeWizardPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("TimeWizardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.TIME_WIZARD_POWER);
    
    private String lastAction = "None.";

    public TimeWizardPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	lastAction = RandomActionHelper.triggerRandomAction(this.amount, false);
    	updateDescription();
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + lastAction; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + lastAction;}
    }
}
