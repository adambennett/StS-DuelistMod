package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistCard;

public class ModifySecondMagicNumberAction extends AbstractGameAction {
	DuelistCard cardToModify;
	
	
	public ModifySecondMagicNumberAction(DuelistCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		this.cardToModify.baseMagicNumber += this.amount;
		if (this.cardToModify.baseSecondMagic < 0) {
			this.cardToModify.baseSecondMagic = 0;
		}
		this.cardToModify.secondMagic = this.cardToModify.baseSecondMagic;
		this.isDone = true;
	}
	
}
