package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.WorldTreeAction;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;


@SuppressWarnings("unused")
public class WorldTreePower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("WorldTreePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("WorldTreePower.png");
	private boolean finished = false;
	
	public WorldTreePower(final AbstractCreature owner, final AbstractCreature source, int amount, int maxTempHP) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.amount2 = maxTempHP;
		updateDescription();
	}
	
	public WorldTreePower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this(owner, source, amount, 1);
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
	}
	
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	onPlayCard(card, null);
    }

	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) 
	{
		int ref = this.amount;
		if (this.amount2 > 0 && this.amount2 >= this.amount)
		{
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower power = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				if (card.type.equals(CardType.SKILL) && power.isEveryMonsterCheck(Tags.PLANT, false))
				{
					AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
					this.amount2 -= this.amount;
					updateDescription();
					if (this.amount2 < 1) 
					{ 
						DuelistCard.removePower(this, this.owner); 
						AbstractDungeon.actionManager.addToBottom(new WorldTreeAction(ref));		
					}
				}
			}
		}
		else if (this.amount2 > 0 && this.amount2 < this.amount)
		{
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower power = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				if (card.type.equals(CardType.SKILL) && power.isEveryMonsterCheck(Tags.PLANT, false))
				{
					AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.amount2));
					DuelistCard.removePower(this, this.owner); 
					AbstractDungeon.actionManager.addToBottom(new WorldTreeAction(ref));					
				}
			}
		}
		else
		{
			DuelistCard.removePower(this, this.owner);
			AbstractDungeon.actionManager.addToBottom(new WorldTreeAction(ref));	
		}
		
	}
}
