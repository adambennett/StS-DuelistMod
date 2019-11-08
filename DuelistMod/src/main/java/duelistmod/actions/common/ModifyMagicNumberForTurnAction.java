package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class ModifyMagicNumberForTurnAction extends AbstractGameAction 
{
	DuelistCard cardToModify;

	public ModifyMagicNumberForTurnAction(DuelistCard card, int addAmount) {
		this.setValues(this.target, this.source, addAmount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() 
	{
		if (this.cardToModify.hasTag(Tags.MAGIC_NUM_SCALE_BY_10)) { this.amount = this.amount * 10; }
		if (this.cardToModify.hasTag(Tags.BAD_MAGIC))
		{
			this.cardToModify.originalMagicNumber = this.cardToModify.baseMagicNumber;
			this.cardToModify.baseMagicNumber -= this.amount;
			if (this.cardToModify.baseMagicNumber < 0) {
				this.cardToModify.baseMagicNumber = 0;
			}
			this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
			this.cardToModify.isMagicNumModifiedForTurn = true;
			this.cardToModify.isMagicNumberModified = true;
		}
		else
		{
			this.cardToModify.originalMagicNumber = this.cardToModify.baseMagicNumber;
			this.cardToModify.baseMagicNumber += this.amount;
			if (this.cardToModify.baseMagicNumber < 0) {
				this.cardToModify.baseMagicNumber = 0;
			}
			this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
			this.cardToModify.isMagicNumModifiedForTurn = true;
			this.cardToModify.isMagicNumberModified = true;
		}
		this.isDone = true;
	}
	
}
