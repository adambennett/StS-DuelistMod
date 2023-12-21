package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.GridSort;
import duelistmod.variables.Tags;

public class MillenniumSpellbookAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<DuelistCard> cards;
	
  
	public MillenniumSpellbookAction(ArrayList<DuelistCard> cardsToChooseFrom, int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				AbstractCard gridCard = card;
		        gridCard.initializeDescription();
				tmp.addToTop(gridCard);
				if (DuelistMod.debug) { System.out.println("theDuelist:CardSelectScreenMakeMegatypedAction:update() ---> added " + gridCard.originalName + " into grid selection pool"); }
			}
			
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " card to make Megatyped", false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " cards to make Megatyped", false); }
			tickDuration();
			return;
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			AbstractPlayer pl = AbstractDungeon.player;
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				// Find copy of card in deck and make megatyped
				processCard(c);
				
				// Find copy of card in combat and make megatyped
				boolean foundCopy = false;
				for (AbstractCard card : pl.drawPile.group)
				{
					if (card.uuid.equals(c.uuid))
					{
						foundCopy = true;
						processCard(card);
					}
					
					if (foundCopy) { break; }
				}
				
				if (!foundCopy)
				{
					for (AbstractCard card : pl.discardPile.group)
					{
						if (card.uuid.equals(c.uuid))
						{
							foundCopy = true;
							processCard(card);
						}
						
						if (foundCopy) { break; }
					}
				}
				
				if (!foundCopy)
				{
					for (AbstractCard card : pl.exhaustPile.group)
					{
						if (card.uuid.equals(c.uuid))
						{
							foundCopy = true;
							processCard(card);
						}
						
						if (foundCopy) { break; }
					}
				}
					
				if (!foundCopy)
				{
					for (AbstractCard card : pl.hand.group)
					{
						if (card.uuid.equals(c.uuid))
						{
							foundCopy = true;
							processCard(card);
						}
						
						if (foundCopy) { break; }
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}

	private void processCard(AbstractCard c) {
		if (c instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)c;
			if (dc.notAddedTagToDescription(DuelistMod.typeCardMap_NAME.get(Tags.MEGATYPED))) {
				dc.makeMegatyped();
				dc.rawDescription = DuelistMod.typeCardMap_NAME.get(Tags.MEGATYPED) + " NL " + dc.rawDescription;
				dc.originalDescription = DuelistMod.typeCardMap_NAME.get(Tags.MEGATYPED) + " NL " + dc.originalDescription;
				dc.isTypeAddedPerm = true;
				ArrayList<String> types = new ArrayList<>();
				for (String s : dc.savedTypeMods) {
					if (!(s.equals("default"))) {
						types.add(s);
					}
				}
				dc.savedTypeMods = types;
				dc.savedTypeMods.add(DuelistMod.typeCardMap_NAME.get(Tags.MEGATYPED));
				dc.addTagToAddedTypeMods(DuelistMod.typeCardMap_NAME.get(Tags.MEGATYPED));
			}
		}
		c.initializeDescription();
	}
}
