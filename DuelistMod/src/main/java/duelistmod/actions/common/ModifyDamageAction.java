package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.patches.DuelistCard;

public class ModifyDamageAction extends AbstractGameAction {
	DuelistCard cardToModify;
	
	
	public ModifyDamageAction(DuelistCard card, int addAmount) {
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		this.cardToModify.damage = this.amount;
		this.cardToModify.isDamageModified = true;
		this.isDone = true;
	}
	
}
