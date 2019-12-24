package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.DuelistMod;
import duelistmod.abstracts.NoStackDuelistPower;
import duelistmod.orbs.Shadow;
import duelistmod.variables.Tags;

public class VampireDomainPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("VampireDomainPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DuelistMod.makePowerPath("DemiseLandPower.png");
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    
	public VampireDomainPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public VampireDomainPower(AbstractCreature source, AbstractCreature owner) 
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
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}

	@Override
	public void onPlayCard(final AbstractCard card, final AbstractMonster m) 
	{
		if (card.hasTag(Tags.VAMPIRE) && AbstractDungeon.player.hasOrb())
		{
			for (AbstractOrb o : AbstractDungeon.player.orbs)
			{
				if (o instanceof Shadow)
				{
					((Shadow)o).triggerPassiveEffect();
				}
			}
		}
    }
	
	@Override
    public void onEnemyUseCard(AbstractCard card)
    {
		if (card.hasTag(Tags.VAMPIRE) && AbstractDungeon.player.hasOrb())
		{
			for (AbstractOrb o : AbstractDungeon.player.orbs)
			{
				if (o instanceof Shadow)
				{
					((Shadow)o).triggerPassiveEffect();
				}
			}
		}
    }
}
