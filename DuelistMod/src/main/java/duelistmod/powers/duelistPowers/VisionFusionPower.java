package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.aqua.SevenColoredFish;

public class VisionFusionPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VisionFusionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    
    public AbstractCard resummonCard;
	
	public VisionFusionPower(int turns, int copies, AbstractCard chosen) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns, copies, chosen);
	}
	
	public VisionFusionPower(AbstractCreature owner, AbstractCreature source, int stacks) 
	{ 
		this(owner, source, stacks, 0, new SevenColoredFish());
	}
	
	public VisionFusionPower(AbstractCreature owner, AbstractCreature source, int stacks, int stacks2, AbstractCard chosen) 
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
        this.amount2 = stacks2;
        this.resummonCard = chosen;
		updateDescription();
	}
	
	@Override
	public void atStartOfTurn()
	{
		if (this.amount > 0) { this.amount--; updateDescription(); }
		if (this.amount < 1) 
		{ 
			if (this.resummonCard != null) { DuelistCard.resummon(this.resummonCard, this.amount2); }
			DuelistCard.removePower(this, this.owner); 
		}
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) 
		{
			if (this.amount2 == 1) 
			{
				this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3] + this.resummonCard.originalName + DESCRIPTIONS[5]; 
			}
			else
			{
				this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[4] + this.resummonCard.originalName + DESCRIPTIONS[6];
			}			
		}
		else 
		{ 
			if (this.amount2 == 1) 
			{
				this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + this.resummonCard.originalName + DESCRIPTIONS[5]; 
			}
			else
			{
				this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[4] + this.resummonCard.originalName + DESCRIPTIONS[6];
			}
		}
	}

}
