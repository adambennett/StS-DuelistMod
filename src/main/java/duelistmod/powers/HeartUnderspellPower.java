package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class HeartUnderspellPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("HeartUnderspell");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.HEART_UNDERDOG_POWER);

    public HeartUnderspellPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
       // if (owner.hasPower(HeartUnderdogPower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUnderdogPower.POWER_ID), owner); }
      //  if (owner.hasPower(HeartUndertributePower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUndertributePower.POWER_ID), owner); }
      //  if (owner.hasPower(HeartUndertrapPower.POWER_ID)) { DuelistCard.removePower(owner.getPower(HeartUndertrapPower.POWER_ID), owner); }
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount = 0; }
	}

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
