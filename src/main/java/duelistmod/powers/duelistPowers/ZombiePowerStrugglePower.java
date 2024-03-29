package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.characters.TheDuelist;
import duelistmod.variables.Tags;

public class ZombiePowerStrugglePower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("ZombiePowerStrugglePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public ZombiePowerStrugglePower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public ZombiePowerStrugglePower(AbstractCreature owner, AbstractCreature source) 
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
		updateDescription();
	}

	@Override
	public void onTribute(DuelistCard tributed, DuelistCard tributing)
	{
		if (tributed != null && tributing != null && tributed.hasTag(Tags.ZOMBIE) && tributing.hasTag(Tags.ZOMBIE)) {
			DuelistCard.fetch(TheDuelist.resummonPile, false);
			this.flash();
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0];
	}
}
