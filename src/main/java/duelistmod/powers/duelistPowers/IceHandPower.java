package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class IceHandPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("IceHandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("IceHandPower.png");
	
	public IceHandPower(int turns, AbstractCreature target)
	{ 
		this(target, AbstractDungeon.player, turns);
	}
	
	public IceHandPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = stacks;
		updateDescription();
	}

	public void trigger() {
		if (this.amount > 0)
		{
			DuelistCard.applyPower(new FrozenDebuff(this.owner, this.source), this.owner);
			this.amount--;
			updateDescription();
		}

		if (this.amount < 1)
		{
			DuelistCard.removePower(this, this.owner);
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0]; }
		else { this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]; }
	}
}
