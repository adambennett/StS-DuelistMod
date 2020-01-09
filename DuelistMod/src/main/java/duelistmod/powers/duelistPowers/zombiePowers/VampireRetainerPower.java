package duelistmod.powers.duelistPowers.zombiePowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.actions.common.MakeCardRetainAction;
import duelistmod.variables.Tags;

public class VampireRetainerPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VampireRetainerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DuelistMod.makePowerPath("DemiseLandPower.png");
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    
	public VampireRetainerPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public VampireRetainerPower(AbstractCreature source, AbstractCreature owner) 
	{ 
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
	public void atEndOfTurn(boolean isPlayer)
	{
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			if (c.hasTag(Tags.VAMPIRE))
			{
				this.addToTop(new MakeCardRetainAction(c, true));
			}
		}
	}
	
	
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}

}
