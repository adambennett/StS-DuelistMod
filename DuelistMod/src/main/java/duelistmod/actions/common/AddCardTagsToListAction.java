package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.powers.SummonPower;

public class AddCardTagsToListAction extends AbstractGameAction 
{
	ArrayList<AbstractCard> cardsToModify;
	private CardTags tagSave;
	
	public AddCardTagsToListAction(ArrayList<AbstractCard> cards, CardTags tag) 
	{
		this.setValues(this.target, this.source);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardsToModify = cards;
		this.tagSave = tag;
	}
	
	@Override
	public void update() 
	{
		for (AbstractCard c : this.cardsToModify)
		{
			c.tags.add(tagSave);
			c.rawDescription =  c.rawDescription + " NL " + DuelistMod.typeCardMap_NAME.get(tagSave);
			c.initializeDescription();
			if (DuelistMod.debug) { DuelistMod.logger.info("Gave " + DuelistMod.typeCardMap_NAME.get(tagSave) + " type to " + c.originalName); }
		}		
		
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);    			
			//DuelistCard.summon(AbstractDungeon.player, 0, new Token());
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();    
		}
		
		
		this.isDone = true;
	}	
}
