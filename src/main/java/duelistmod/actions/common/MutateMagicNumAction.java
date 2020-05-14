package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class MutateMagicNumAction extends AbstractGameAction {
	AbstractCard cardToModify;
	private int magicBonus = 0;
	
	public MutateMagicNumAction(AbstractCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.magicBonus = amount;
	}
	
	@Override
	public void update() {
		if (this.amount == 0) { this.isDone = true; return; }
		if (cardToModify instanceof DuelistCard)
		{
			modify((DuelistCard) cardToModify, this.magicBonus);
		}
		else
		{
			cardToModify.baseMagicNumber += this.magicBonus;
			if (cardToModify.baseMagicNumber < 0) { cardToModify.baseMagicNumber = 0; }
			cardToModify.magicNumber = cardToModify.baseMagicNumber;
		}
		this.isDone = true;
	}
	
	private void modify(DuelistCard original, int magic)
	{
		if (magic == 0) { return; }
		if (original.hasTag(Tags.MAGIC_NUM_SCALE_BY_10)) { magic = magic * 10; }
		if (original.hasTag(Tags.BAD_MAGIC))
		{
			original.baseMagicNumber -= magic;
			if (original.baseMagicNumber < 0) { original.baseMagicNumber = 0; }
			original.magicNumber = original.baseMagicNumber;
			original.isMagicNumberModified = true;
		}
		else
		{
			original.baseMagicNumber += magic;
			if (original.baseMagicNumber < 0) { original.baseMagicNumber = 0; }
			original.magicNumber = original.baseMagicNumber;
			original.isMagicNumberModified = true;
		}
	}
	
}
