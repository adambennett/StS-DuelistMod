package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.incomplete.*;

public class HauntedRemovalPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("HauntedRemovalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("HauntedPower.png");
	
	public HauntedRemovalPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public HauntedRemovalPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		if (AbstractDungeon.player.hasPower(HauntedDebuff.POWER_ID))
		{
			DuelistCard.removePower(AbstractDungeon.player.getPower(HauntedDebuff.POWER_ID), AbstractDungeon.player);
		}
		else if (AbstractDungeon.player.hasPower(HauntedPower.POWER_ID))
		{
			DuelistCard.removePower(AbstractDungeon.player.getPower(HauntedPower.POWER_ID), AbstractDungeon.player);
		}
		
		DuelistCard.removePower(this, this.owner);
	}

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
}
