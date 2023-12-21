package duelistmod.powers.duelistPowers;

import java.util.*;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.characters.TheDuelist;

public class EndlessDecayPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("EndlessDecayPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("EndlessDecayPower.png");
	
	public EndlessDecayPower(int turns) 
	{ 
		this(AbstractDungeon.player, AbstractDungeon.player, turns);
	}
	
	public EndlessDecayPower(AbstractCreature owner, AbstractCreature source, int stacks) 
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
        this.amount = stacks;
		updateDescription();
	}

	@Override
	public void onSummon(DuelistCard sum, int amt)
	{
		if (TheDuelist.resummonPile.group.size() > 0 && this.amount > 0 && amt > 0)
		{
			Map<String, String> mapp = new HashMap<>();
			ArrayList<AbstractCard> cards = new ArrayList<>();
			while (cards.size() < 1)
			{
				int ind = AbstractDungeon.cardRandomRng.random(TheDuelist.resummonPile.group.size() - 1);
				AbstractCard rand = TheDuelist.resummonPile.group.get(ind);
				while (mapp.containsKey(rand.cardID)) 
				{
					ind = AbstractDungeon.cardRandomRng.random(TheDuelist.resummonPile.group.size() - 1);
					rand = TheDuelist.resummonPile.group.get(ind);
				}
				cards.add(rand);
			}
			
			for (AbstractCard c : cards) { runEffect(c); }			
		}
	}
	
	private void runEffect(AbstractCard c)
	{
		int tribCost = 0;					
		if (c instanceof DuelistCard)
		{
			tribCost = ((DuelistCard)c).tributes;
		}
		
		this.addToBot(new ExhaustSpecificCardAction(c, TheDuelist.resummonPile));
		if (tribCost > 0 && this.amount > 0)
		{
			DuelistCard.damageAllEnemiesThornsNormal(tribCost * this.amount);
		}	
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
