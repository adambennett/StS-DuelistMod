package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

public class ModifyEtherealAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public ModifyEtherealAction(AbstractCard card) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		if (!this.cardToModify.isEthereal) 
		{
			this.cardToModify.isEthereal = true;
			this.cardToModify.rawDescription = Strings.etherealForCardText + this.cardToModify.rawDescription;
			if (cardToModify instanceof DuelistCard) {
				((DuelistCard)cardToModify).fixUpgradeDesc();
			}
			this.cardToModify.initializeDescription();
		}
		this.isDone = true;
	}
	
}
