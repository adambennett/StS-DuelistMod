package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class ModifyExhaustAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public ModifyExhaustAction(AbstractCard card) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		if (!this.cardToModify.exhaust) 
		{
			this.cardToModify.exhaust = true;
			this.cardToModify.rawDescription = this.cardToModify.rawDescription + DuelistMod.exhaustForCardText;
			if (cardToModify instanceof DuelistCard) {
				((DuelistCard)cardToModify).fixUpgradeDesc();
			}
			this.cardToModify.initializeDescription();
		}
		this.isDone = true;
	}
	
}
