package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;

import duelistmod.DuelistMod;

public class AddCardTagsAction extends AbstractGameAction 
{
	AbstractCard cardToModify;
	private CardTags tagSave;
	
	public AddCardTagsAction(AbstractCard card, CardTags tag) 
	{
		this.setValues(this.target, this.source);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.tagSave = tag;
	}
	
	@Override
	public void update() 
	{
		this.cardToModify.tags.add(tagSave);
		this.cardToModify.rawDescription =  this.cardToModify.rawDescription + " NL " + DuelistMod.typeCardMap_NAME.get(tagSave);
		this.cardToModify.initializeDescription();
		this.isDone = true;
	}	
}
