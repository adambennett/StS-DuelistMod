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
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class EnemyHandPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemyHandPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("EnemyHandPower.png");
	private boolean finished = false;
	public ArrayList<String> handCards = new ArrayList<String>();
	
	public EnemyHandPower(final AbstractCreature owner, final AbstractCreature source) 
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
	
	public EnemyHandPower(AbstractCreature enemy, ArrayList<String> startingCards)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = enemy;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = enemy;
		this.amount = 0;
		fillHand(startingCards);
		updateDescription();
	}
	
	public void addCardToHand(String card)
	{
		this.handCards.add(card);
		this.amount = this.handCards.size();
		updateDescription();
	}
	
	public void fillHand(ArrayList<String> cards)
	{
		this.handCards = new ArrayList<String>();
		this.handCards.addAll(cards);
		this.amount = this.handCards.size();
		updateDescription();
	}
	
	public void removeCard(String card)
	{
		ArrayList<String> temp = new ArrayList<String>();
		boolean removedOne = false;
		for (String s : this.handCards)
		{
			if (s.equals(card) && !removedOne)
			{
				removedOne = true;
			}
			else
			{
				temp.add(s);
			}
		}
		fillHand(temp);		
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

		if (this.handCards.size() > 0) { this.description = DESCRIPTIONS[0] + handCards; }
		else { this.description = DESCRIPTIONS[1]; }
	}
}
