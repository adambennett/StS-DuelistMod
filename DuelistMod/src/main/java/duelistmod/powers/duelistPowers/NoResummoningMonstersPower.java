package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class NoResummoningMonstersPower extends DuelistPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("NoResummoningMonstersPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("NoResummonPower.png");

    public NoResummoningMonstersPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
    public boolean allowResummon(AbstractCard resummoningCard) { if (resummoningCard.hasTag(Tags.MONSTER)) { return false; } return true; }
	
    
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
        this.description = DESCRIPTIONS[0] + this.amount;
    }
}
