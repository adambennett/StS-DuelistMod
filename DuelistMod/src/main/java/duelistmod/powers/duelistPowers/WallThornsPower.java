package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class WallThornsPower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("WallThornsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("WallThornsPower.png");
	private boolean finished = false;
	
	public WallThornsPower(int amt) 
	{
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = amt;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
		AbstractPlayer p = AbstractDungeon.player;
    	if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (damageAmount > 0) && (!this.owner.hasPower("Buffer")))
    	{
    		if (p.hasPower(SummonPower.POWER_ID))
    		{
    			SummonPower pow = (SummonPower) p.getPower(SummonPower.POWER_ID);
    			if (pow.getNumberOfTypeSummoned(Tags.INSECT) < 1 && this.amount > 0)
    			{
    				DuelistCard.applyPowerToSelf(new ThornsPower(p, this.amount));
    			}
    		}
    	}
    	return damageAmount;
    }
}
