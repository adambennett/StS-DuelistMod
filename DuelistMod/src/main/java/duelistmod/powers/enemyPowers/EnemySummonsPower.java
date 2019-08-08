package duelistmod.powers.enemyPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.helpers.Util;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class EnemySummonsPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemySummonsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SUMMON_POWER);
	private boolean finished = false;
	public ArrayList<String> handCards = new ArrayList<String>();
	private int maxSummons = 5;
	
	public EnemySummonsPower(final AbstractCreature owner, final AbstractCreature source) 
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
	
	public EnemySummonsPower(AbstractCreature enemy, int startingTokens, String tokenName)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = enemy;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = enemy;
		this.amount = 0;
		for (int i = 0; i < startingTokens; i++) { addCardToSummons(tokenName); }
		updateDescription();
	}
	
	public ArrayList<String> tribute(int amount)
	{
		ArrayList<String> tribList = new ArrayList<String>();
		int i = 0;
		while (this.handCards.size() > 0 && i < amount)
		{
			tribList.add(this.handCards.get(this.handCards.size() - 1));
			this.handCards.remove(this.handCards.size() - 1);
			i++;
		}
		updateDescription();
		return tribList;
	}
	
	public ArrayList<String> tributeAll()
	{
		ArrayList<String> tribList = new ArrayList<String>();
		while (this.handCards.size() > 0)
		{
			tribList.add(this.handCards.get(this.handCards.size() - 1));
			this.handCards.remove(this.handCards.size() - 1);
		}
		updateDescription();
		return tribList;
	}
	
	public boolean addCardToSummons(String card)
	{
		if (this.handCards.size() + 1 <= this.maxSummons)
		{
			this.handCards.add(card);
			Util.log("Enemy summoned " + card);
			this.amount = this.handCards.size();
			updateDescription();
			return true;
		}
		else
		{
			updateDescription();
			return false;
		}
	}
	
	public void fillSummons(ArrayList<String> cards)
	{
		this.handCards = new ArrayList<String>();
		for (String s : cards)
		{
			addCardToSummons(s);
			if (this.handCards.size() == this.maxSummons) { break; }
		}
		this.handCards.addAll(cards);
		this.amount = this.handCards.size();
		updateDescription();
	}
	
	public void incMaxSummons(int amount)
	{
		this.maxSummons += amount;
		updateDescription();
	}
	
	public void decMaxSummons(int amount)
	{
		this.maxSummons -= amount;
		if (this.maxSummons < 1) { this.maxSummons = 1; }
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		String handCards = "";
		for (int i = 0; i < this.handCards.size(); i++)
		{
			if (i + 1 == this.handCards.size()) { handCards += this.handCards.get(i) + "."; }
			else { handCards += this.handCards.get(i) + ", "; }
		}

		if (this.handCards.size() > 0) { this.description = DESCRIPTIONS[0] + this.handCards.size() + DESCRIPTIONS[1] + this.maxSummons + DESCRIPTIONS[2] + handCards; }
		else { this.description = DESCRIPTIONS[3]; }
		this.amount = this.handCards.size();
	}
}
