package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OjamaPajamaAction extends AbstractGameAction 
{
	private int magicOfPajamaCard = 0;
	public OjamaPajamaAction(int magic) 
	{
		this.setValues(this.target, this.source, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;	
		this.magicOfPajamaCard = magic;
	}
	
	@Override
	public void update() 
	{		
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, magicOfPajamaCard);
			c.baseMagicNumber += roll;			
			c.magicNumber = c.baseMagicNumber;
		}
		
		for (AbstractCard c : AbstractDungeon.player.discardPile.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, magicOfPajamaCard);
			c.baseMagicNumber += roll;			
			c.magicNumber = c.baseMagicNumber;
		}
		
		for (AbstractCard c : AbstractDungeon.player.hand.group)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, magicOfPajamaCard);
			c.baseMagicNumber += roll;			
			c.magicNumber = c.baseMagicNumber;
		}		
		this.isDone = true;
	}
	
}
