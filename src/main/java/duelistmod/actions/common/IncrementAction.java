package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

public class IncrementAction extends AbstractGameAction {

	private AnyDuelist duelist;
	
	public IncrementAction(int amount, AnyDuelist duelist) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.duelist = duelist;
	}
	
	@Override
	public void update() {
		DuelistCard.incMaxSummons(this.amount, duelist);
		this.isDone = true;
	}
	
}
