package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.Token;
import duelistmod.cards.pools.insects.CorrosiveScales;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class CorrosiveScalesPower extends NoStackDuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("CorrosiveScalesPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	//public static final String IMG = DuelistMod.makePowerPath("CorrosiveScalesPower.png");
	public static final String IMG = DuelistMod.makePowerPath("CorrosiveScalesPower.png");
	private boolean upgraded = false;
	
	public CorrosiveScalesPower(boolean upgraded) 
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
		this.upgraded = upgraded;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		if (upgraded) { this.description = DESCRIPTIONS[1]; }
		else {  this.description = DESCRIPTIONS[0]; }
	}

	@Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
		AbstractPlayer p = AbstractDungeon.player;
    	if ((info.type != DamageInfo.DamageType.THORNS) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.owner != null) && (info.owner != this.owner) && (damageAmount > 0) && (!this.owner.hasPower("Buffer")))
    	{
    		int x = DuelistCard.xCostTributeStatic(Tags.INSECT, new CorrosiveScales());
    		if (x > 0)
    		{
    			if (upgraded) { DuelistCard.poisonAllEnemies(p, x * 3); }
		    	else { DuelistCard.poisonAllEnemies(p, x * 2); }
    		}
    	}
    	return damageAmount;
    }
}
