package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
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
		boolean allowCard = false;
		if (DuelistMod.magicNumberCards.containsKey(this.cardToModify.cardID)) { allowCard = true; }
		if ((this.cardToModify.magicNumber > 0 || allowCard) && !this.cardToModify.hasTag(Tags.ALLOYED) && !this.cardToModify.type.equals(CardType.CURSE) && !this.cardToModify.type.equals(CardType.STATUS))
		{
			if (this.cardToModify.baseMagicNumber != 7) { this.cardToModify.isMagicNumberModified = true; }
			this.cardToModify.baseMagicNumber = 7;
			this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
			this.cardToModify.applyPowers();
			AbstractDungeon.player.hand.glowCheck();
			this.isDone = true;
		}
	}
}
