package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.variables.Tags;

public class SetMagicNumberToSevenAction extends AbstractGameAction 
{
	AbstractCard cardToModify;

	public SetMagicNumberToSevenAction(AbstractCard card) 
	{
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() 
	{
		if (!this.cardToModify.hasTag(Tags.ALLOYED))
		{
			if (this.cardToModify.baseMagicNumber != 7) { this.cardToModify.isMagicNumberModified = true; }
			this.cardToModify.baseMagicNumber = 7;
			this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
			this.isDone = true;
		}
	}
}
