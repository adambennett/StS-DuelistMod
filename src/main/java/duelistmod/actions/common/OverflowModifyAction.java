package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.variables.Tags;

public class OverflowModifyAction extends AbstractGameAction {
	DuelistCard cardToModify;
	
	
	public OverflowModifyAction(DuelistCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	public OverflowModifyAction(AbstractCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		if (card instanceof DuelistCard) { this.cardToModify = (DuelistCard)card; }
		else { this.cardToModify = null; }
	}
	
	@Override
	public void update() 
	{
		if (this.amount == 0 ||!this.cardToModify.hasTag(Tags.IS_OVERFLOW) || this.cardToModify == null) { this.isDone = true; return; }
		this.cardToModify.baseMagicNumber += this.amount;
		if (this.cardToModify.baseMagicNumber < 0) {
			this.cardToModify.baseMagicNumber = 0;
		}
		this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
		this.isDone = true;
	}
	
}
