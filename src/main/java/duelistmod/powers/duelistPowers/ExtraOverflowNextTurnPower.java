package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class ExtraOverflowNextTurnPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ExtraOverflowNextTurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("ExtraOverflowNextTurnPower.png");
	
	public ExtraOverflowNextTurnPower(int extraOverflows, int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, extraOverflows, turns);
	}
	
	public ExtraOverflowNextTurnPower(AbstractCreature owner, AbstractCreature source, int extraOverflows) 
	{ 
		this(owner, source, extraOverflows, 1);
	}
	
	public ExtraOverflowNextTurnPower(AbstractCreature owner, AbstractCreature source, int extraOverflows, int turns) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = extraOverflows;
        this.amount2 = turns;
		updateDescription();
	}

	@Override
	public void atStartOfTurn()
	{
		if (this.amount2 > 0)
		{
			DuelistCard.applyPowerToSelf(new MakoBlessingPower(this.amount));
			this.amount2--;
			if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
	}
	
	
	@Override
	public void updateDescription()
	{
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		
	}
}
