package duelistmod.powers;

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
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.interfaces.*;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class RainbowRefractionPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("RainbowRefractionPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("RainbowRefractionPower.png");
	private boolean finished = false;
	
	public RainbowRefractionPower(final AbstractCreature owner, final AbstractCreature source) 
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
    			if (pow.getNumberOfTypeSummoned(Tags.MEGATYPED) > 0)
    			{
    				int tokens = 0;
    		    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
    		    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
    		    	ArrayList<String> newSummonList = new ArrayList<String>();
    		    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
    		    	for (DuelistCard s : aSummonsList)
    		    	{
    		    		if (s.hasTag(Tags.MEGATYPED))
    		    		{
    		    			tokens++;
    		    		}
    		    		else
    		    		{
    		    			newSummonList.add(s.originalName);
    		    			aNewSummonList.add(s);
    		    		}
    		    	}
    		    	
    		    	DuelistCard.tributeChecker(p, tokens, new Token(), true);
    		    	summonsInstance.summonList = newSummonList;
    		    	summonsInstance.actualCardSummonList = aNewSummonList;
    		    	summonsInstance.amount -= tokens;
    		    	summonsInstance.updateDescription();
    		    	return 0;
    			}
    		}
    	}
    	return damageAmount;
    }
}
