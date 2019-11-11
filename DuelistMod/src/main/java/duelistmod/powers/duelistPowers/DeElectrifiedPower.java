package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class DeElectrifiedPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DeElectrifiedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DeElectrifiedPower.png");
	
	public DeElectrifiedPower(int electricLoss, int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, electricLoss, turns);
	}
	
	public DeElectrifiedPower(AbstractCreature owner, AbstractCreature source, int electricLoss) 
	{ 
		this(owner, source, electricLoss, 1);
	}
	
	public DeElectrifiedPower(AbstractCreature owner, AbstractCreature source, int electricLoss, int turns) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = true;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = electricLoss;
        this.amount2 = turns;
		updateDescription();
	}

	@Override
	public void atStartOfTurn()
	{
		if (this.amount2 > 0)
		{
			if (owner.hasPower(ElectricityPower.POWER_ID))
			{
				DuelistCard.reducePower(owner.getPower(ElectricityPower.POWER_ID), owner, this.amount);
				this.amount2--;
				if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
			}
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
		}
	}
	
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
