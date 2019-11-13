package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistCard;

public class ModifySummonAction extends AbstractGameAction {
	DuelistCard cardToModify;
	boolean forTurn = true;
	
	
	public ModifySummonAction(DuelistCard card, int addAmount, boolean combat) {
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.forTurn = combat;
	}
	
	@Override
	public void update() {
		if (this.amount == 0) { this.isDone = true; return; }
		if (forTurn) { this.cardToModify.modifySummons(this.amount); }
		else { this.cardToModify.originalDescription = this.cardToModify.rawDescription; this.cardToModify.modifySummonsForTurn(this.amount); }
		this.isDone = true;
	}
	
}
