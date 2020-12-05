package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.variables.*;

public class NoTrapsPower extends DuelistPower
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("NoTrapsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("NoResummonPower.png");

    public NoTrapsPower(final AbstractCreature owner, final AbstractCreature source, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public boolean modifyCanUse(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) {
        return !card.hasTag(Tags.TRAP);
    }

    @Override
    public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot play Traps for the next " + (amount == 1 ? " turn" : amount + " turns"); }
    
    @Override
	public void atEndOfRound()
	{
		if (this.amount > 0) { this.amount--; }
		if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
		updateDescription();
	}
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }    
    }
}
