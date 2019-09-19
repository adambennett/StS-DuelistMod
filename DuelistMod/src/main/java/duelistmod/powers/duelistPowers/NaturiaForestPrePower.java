package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class NaturiaForestPrePower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("NaturiaForestPrePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DuelistMod.makePowerPath("NaturiaForestPower.png");
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	private boolean upgraded = false;
    
	public NaturiaForestPrePower(boolean upgraded)
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.upgraded = upgraded;
        if (upgraded) { this.amount = 2; }
        else { this.amount = 1; }
		updateDescription(); 
	}
	
	@Override
	public void atStartOfTurn()
	{
		if (upgraded) { DuelistCard.applyPowerToSelf(new NaturiaForestPower(2)); DuelistCard.removePower(this, this.owner);  }
		else { DuelistCard.applyPowerToSelf(new NaturiaForestPower(1)); DuelistCard.removePower(this, this.owner); }
	}

	@Override
	public void updateDescription()
	{
		if (this.amount > 2 || this.amount < 1) { this.amount = 1; }
		if (upgraded) { this.description = DESCRIPTIONS[0]; }
		else { this.description = DESCRIPTIONS[1]; }
	}
}
