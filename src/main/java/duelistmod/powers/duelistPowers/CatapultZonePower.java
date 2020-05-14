package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.naturia.CatapultZone;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class CatapultZonePower extends NoStackDuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("CatapultZonePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("CatapultZonePower.png");
	private boolean finished = false;
	
	public CatapultZonePower() 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurn()
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower) p.getPower(SummonPower.POWER_ID);
			int rocks = pow.getNumberOfTypeSummoned(Tags.ROCK);
			if (rocks > 0) { DuelistCard.damageAllEnemiesThornsNormal(rocks); }
		}
	}

	@Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
		AbstractPlayer p = AbstractDungeon.player;
    	if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (damageAmount > 0) && (!this.owner.hasPower("Buffer")))
    	{
    		int x = DuelistCard.xCostTributeStatic(Tags.ROCK, new CatapultZone());
    		if (x > 0) { return 0; }
    	}
    	return damageAmount;
    }
}
