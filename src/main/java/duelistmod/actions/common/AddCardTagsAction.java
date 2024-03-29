package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

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
		if (!cardToModify.hasTag(tagSave))
		{
			this.cardToModify.tags.add(tagSave);
            this.cardToModify.rawDescription = DuelistMod.typeCardMap_NAME.get(tagSave) + " NL " + this.cardToModify.rawDescription;
			if (cardToModify instanceof DuelistCard)
			{
				DuelistCard cm = (DuelistCard)cardToModify;
				if (cm.notAddedTagToDescription(DuelistMod.typeCardMap_NAME.get(tagSave))) {
					cm.originalDescription = DuelistMod.typeCardMap_NAME.get(tagSave) + " NL " + cm.originalDescription;
					cm.isTypeAddedPerm = true;
					ArrayList<String> types = new ArrayList<>();
					for (String s : cm.savedTypeMods) { if (!(s.equals("default"))) { types.add(s); }}
					cm.savedTypeMods = types;
					cm.savedTypeMods.add(DuelistMod.typeCardMap_NAME.get(tagSave));
					cm.addTagToAddedTypeMods(DuelistMod.typeCardMap_NAME.get(tagSave));
				}
			}
			if (tagSave.equals(Tags.MEGATYPED) && cardToModify instanceof DuelistCard)
			{
				DuelistCard cm = (DuelistCard)cardToModify;
				cm.makeMegatyped();
			}
			if (this.cardToModify instanceof DuelistCard) {
				((DuelistCard)this.cardToModify).fixUpgradeDesc();
			}
			this.cardToModify.initializeDescription();
			
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower summonsInstance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);    			
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();    
			}
		}
		this.isDone = true;
	}	
}
