package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TsunamiAction extends AbstractGameAction 
{
	
	public TsunamiAction(int cardsToDraw) 
	{
		this.amount = cardsToDraw;
		this.setValues(this.target, this.source, this.amount);
		this.actionType = AbstractGameAction.ActionType.DRAW;
	}
	
	@Override
	public void update() 
	{
		if (this.amount < 1)
		{
			this.isDone = true;
			return;
		}
		
		ArrayList<AbstractCard> handRef = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.hand.group) { handRef.add(c); }
		this.addToBot(new DrawCardAction(AbstractDungeon.player, this.amount));
		this.addToBot(new TsunamiHelperAction(handRef));	
		this.isDone = true;
	}
	
}
