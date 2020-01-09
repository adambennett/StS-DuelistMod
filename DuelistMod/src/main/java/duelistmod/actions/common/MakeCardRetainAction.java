package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MakeCardRetainAction extends AbstractGameAction {
	AbstractCard cardToModify;
	boolean set;
	
	
	public MakeCardRetainAction(AbstractCard card, boolean set) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.set = set;
	}
	
	@Override
	public void update() {
		this.cardToModify.selfRetain = this.set;
		this.isDone = true;
	}
	
}
