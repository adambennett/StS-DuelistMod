package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.variables.Tags;

public class ModifyMagicNumberAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public ModifyMagicNumberAction(AbstractCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		if (this.cardToModify.hasTag(Tags.MAGIC_NUM_SCALE_BY_10)) { this.cardToModify.baseMagicNumber += this.amount * 10;}
		else { this.cardToModify.baseMagicNumber += this.amount; }
		if (this.cardToModify.baseMagicNumber < 0) {
			this.cardToModify.baseMagicNumber = 0;
		}
		this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
		this.isDone = true;
	}
	
}
