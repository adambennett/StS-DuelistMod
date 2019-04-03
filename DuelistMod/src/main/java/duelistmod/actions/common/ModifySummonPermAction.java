package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.patches.DuelistCard;

public class ModifySummonPermAction extends AbstractGameAction 
{
	DuelistCard cardToModify;	
	
	public ModifySummonPermAction(DuelistCard card, int addAmount) 
	{
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		this.cardToModify.modifySummonsPerm(this.amount);		
		this.isDone = true;
	}
	
}
