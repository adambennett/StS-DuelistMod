package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class FearFromDarkPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("FearFromDarkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("FearFromDarkPower.png");
	
	public FearFromDarkPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public FearFromDarkPower(AbstractCreature owner, AbstractCreature source) 
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
	
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) 
	{
		if (card.hasTag(Tags.ZOMBIE))
		{
			int trib = card.cost;
			if (trib > -1) 
			{
				if (trib > 0) { Util.modifySouls(trib); }
				DuelistCard.removePower(this, this.owner);
			}
		}
    }

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0]; 
	}
}
