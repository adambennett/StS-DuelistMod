package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistCard;

public class IncrementAction extends AbstractGameAction {

	
	
	public IncrementAction(int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
	}
	
	@Override
	public void update() {
		DuelistCard.incMaxSummons(this.amount);		
		this.isDone = true;
	}
	
}
