package defaultmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import defaultmod.patches.DuelistCard;

public class ModifySummonAction extends AbstractGameAction {
	DuelistCard cardToModify;
	boolean forTurn = true;
	
	
	public ModifySummonAction(DuelistCard card, int addAmount, boolean turnOnly) {
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.forTurn = turnOnly;
	}
	
	@Override
	public void update() {
		if (forTurn) { this.cardToModify.modifySummons(this.amount); }
		else { this.cardToModify.modifySummonsForTurn(this.amount); }
		this.isDone = true;
	}
	
}
