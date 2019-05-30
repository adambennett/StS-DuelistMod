package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.cards.tokens.Token;
import duelistmod.interfaces.*;
import duelistmod.powers.SummonPower;


@SuppressWarnings("unused")
public class PoseidonWavePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("PoseidonWavePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
	private boolean finished = false;
	
	public PoseidonWavePower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = 0;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		 this.description = DESCRIPTIONS[0];
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
    			if (pow.getNumberOfTypeSummoned(Tags.AQUA) > 0)
    			{
    				int tokens = 0;
    		    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
    		    	ArrayList<String> summonsList = summonsInstance.summonList;
    		    	ArrayList<String> newSummonList = new ArrayList<String>();
    		    	for (String s : summonsList)
    		    	{
    		    		if (DuelistMod.summonMap.get(s).hasTag(Tags.AQUA))
    		    		{
    		    			tokens++;
    		    		}
    		    		else
    		    		{
    		    			newSummonList.add(s);
    		    		}
    		    	}
    		    	
    		    	DuelistCard.tributeChecker(p, tokens, new Token(), true);
    		    	summonsInstance.summonList = newSummonList;
    		    	summonsInstance.amount -= tokens;
    		    	summonsInstance.updateDescription();
    		    	return 0;
    			}
    		}
    	}
    	return damageAmount;
    }
}
