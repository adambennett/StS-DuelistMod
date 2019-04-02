package defaultmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import defaultmod.patches.DuelistCard;

public class ModifyTributeAction extends AbstractGameAction {
	DuelistCard cardToModify;
	boolean forTurn = true;
	
	
	public ModifyTributeAction(DuelistCard card, int addAmount, boolean combat) {
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.forTurn = combat;
	}
	
	@Override
	public void update() {
		if (forTurn) { this.cardToModify.modifyTributes(this.amount); }
		else { this.cardToModify.modifyTributesForTurn(this.amount); }
		this.isDone = true;
	}
	
}
