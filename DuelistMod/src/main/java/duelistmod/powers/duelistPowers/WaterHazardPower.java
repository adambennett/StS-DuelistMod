package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class WaterHazardPower extends NoStackDuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("WaterHazardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public WaterHazardPower() 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player);
	}
	
	public WaterHazardPower(AbstractCreature owner, AbstractCreature source) 
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
	public void atStartOfTurn()
	{
		SummonPower summonsInstance = DuelistCard.getSummonPower();
		if (summonsInstance != null)
		{
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : summonsInstance.actualCardSummonList)
	    	{	    	
	    		if (s.hasTag(Tags.MONSTER))
	    		{
	    			AbstractCard randRareAqua = DuelistCard.findWaterHazCard();
	    			if (randRareAqua instanceof DuelistCard)
	    			{
	    				DuelistCard dc = (DuelistCard)randRareAqua;
	    				newSummonList.add(dc.originalName);
		    			aNewSummonList.add(dc);
	    			}
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}	
	    	}
	    	
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
		}
	}

	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
}
