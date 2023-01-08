package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.variables.Tags;

public class OjamaPajamaAction extends AbstractGameAction 
{

	private boolean up;
	public OjamaPajamaAction(boolean upgrade) 
	{
		this.setValues(this.target, this.source, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;	
		this.up = upgrade;
	}
	
	@Override
	public void update() 
	{		
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) { modify(c); }
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) { modify(c); }
		for (AbstractCard c : AbstractDungeon.player.hand.group) { modify(c); }
		this.isDone = true;
	}
	
	private void modify(AbstractCard c)
	{
		if (!c.hasTag(Tags.ALLOYED))
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 2);
			if (this.up && AbstractDungeon.cardRandomRng.random(1, 4) == 1)
			{
				roll += AbstractDungeon.cardRandomRng.random(1, 2);
			}
			if (c.hasTag(Tags.BAD_MAGIC)) {
				roll *= -1;
			}
			c.baseMagicNumber += roll;			
			c.magicNumber = c.baseMagicNumber;
		}
	}
	
}
