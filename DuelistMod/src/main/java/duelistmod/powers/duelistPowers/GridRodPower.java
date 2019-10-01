package duelistmod.powers.duelistPowers;

import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class GridRodPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GridRodPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean ready;
    
	public GridRodPower(int turns) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.source = AbstractDungeon.player;
        this.amount = turns;
        this.loadRegion("hymn");
		updateDescription(); 
	}
	
	public void trigger(final AbstractCard c) {
        this.flash();
        this.addToTop(new ReduceCostForTurnAction(c, this.amount));
        c.superFlash();
        this.ready = false;
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (isPlayer) {
            this.ready = true;
        }
    }
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
