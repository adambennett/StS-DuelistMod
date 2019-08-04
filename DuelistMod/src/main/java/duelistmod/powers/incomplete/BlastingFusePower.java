package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.tokens.*;
import duelistmod.interfaces.IShufflePower;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;


@SuppressWarnings("unused")
public class BlastingFusePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("BlastingFusePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("BlastingFusePower.png");
	
	public BlastingFusePower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		// Description Layout: Effect, singular, plural
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
	}

	@Override
	public void atStartOfTurn() 
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (GameActionManager.turn % 2 == 0) 
		{
			DuelistCard.summon(p, this.amount, new ExplosiveToken());
		}
		else if (p.hasPower(SummonPower.POWER_ID))
		{
			int tokens = 0;
			int allTokens = 0;
			int sTokens = 0;
	    	SummonPower summonsInstance = (SummonPower) p.getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (s.hasTag(Tags.EXPLODING_TOKEN))
	    		{
	    			tokens++;
	    			allTokens++;	    			
	    		}
	    		else if (s.hasTag(Tags.SUPER_EXPLODING_TOKEN))
	    		{
	    			sTokens++;
	    			allTokens++;
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}
	    	}
	    	
	    	DuelistCard.tributeChecker(p, allTokens, new Token(), false);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= allTokens;
	    	for (int i = 0; i < tokens; i++)
	    	{
	    		int roll = AbstractDungeon.cardRandomRng.random(DuelistMod.explosiveDmgLow, DuelistMod.explosiveDmgHigh);
	    		DuelistCard.attackAllEnemiesFireThorns(roll);
	    	}
	    	for (int i = 0; i < sTokens; i++)
	    	{
	    		int roll = AbstractDungeon.cardRandomRng.random(DuelistMod.explosiveDmgLow * 2, DuelistMod.explosiveDmgHigh * 2);
	    		DuelistCard.attackAllEnemiesFireThorns(roll);
	    	}
	    	
	    	DuelistCard.summon(p, 0, new Token());
		}
	}
}
