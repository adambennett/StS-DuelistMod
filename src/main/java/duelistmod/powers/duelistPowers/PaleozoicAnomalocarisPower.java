package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class PaleozoicAnomalocarisPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("PaleozoicAnomalocarisPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("OldPlaceholderPower.png");
	
	public PaleozoicAnomalocarisPower(int turns)
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public PaleozoicAnomalocarisPower(AbstractCreature owner, AbstractCreature source, int stacks)
	{ 
		//super(owner, source, stacks);
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = stacks;
		updateDescription();
	}
	
	@Override
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) 
	{
		if (tributedMon.hasTag(Tags.AQUA) && tributingMon.hasTag(Tags.AQUA))
		{
			applyPowerToSelf(new FishscalesPower(this.amount));
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

}
