package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

public class MakeCardGroupEthereal extends AbstractGameAction 
{
	ArrayList<AbstractCard> cardsToMod;
	
	public MakeCardGroupEthereal(ArrayList<AbstractCard> card) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardsToMod = card;
	}
	
	@Override
	public void update() 
	{
		for (AbstractCard cardToModify : cardsToMod)
		{
		if (!cardToModify.isEthereal) 
			{
				cardToModify.isEthereal = true;
				cardToModify.rawDescription = Strings.etherealForCardText + cardToModify.rawDescription;
				if (cardToModify instanceof DuelistCard) {
					((DuelistCard)cardToModify).fixUpgradeDesc();
				}
				cardToModify.initializeDescription();
			}
		}
		this.isDone = true;
	}
	
}
