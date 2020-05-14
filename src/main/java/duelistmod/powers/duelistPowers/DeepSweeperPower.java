package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.TsunamiAction;

public class DeepSweeperPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DeepSweeperPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public DeepSweeperPower(int tsunamis, int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, tsunamis, turns);
	}
	
	public DeepSweeperPower(AbstractCreature owner, AbstractCreature source, int tsunamis) 
	{ 
		this(owner, source, tsunamis, 1);
	}
	
	public DeepSweeperPower(AbstractCreature owner, AbstractCreature source, int tsunamis, int turns) 
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
        this.amount = tsunamis;
        this.amount2 = turns;
		updateDescription();
	}

	@Override
	public void atStartOfTurn()
	{
		if (this.amount2 > 0)
		{
			this.addToBot(new TsunamiAction(this.amount));
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
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
