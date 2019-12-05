package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Tags;

public class CardSelectScreenTriggerOverflowAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;
	private boolean canCancel = false;
	private boolean anyNumber = false;
	private int overflowsToTrigger = 0;
	private DuelistCardSelectScreen dcss;

	public CardSelectScreenTriggerOverflowAction(ArrayList<AbstractCard> cardsToChooseFrom, int cardsToChoose, int overflows)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = cardsToChoose;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.overflowsToTrigger = overflows;
		this.dcss = new DuelistCardSelectScreen(false);
		this.setValues(this.p, AbstractDungeon.player, cardsToChoose);
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				if (gridCard.hasTag(Tags.IS_OVERFLOW) && gridCard instanceof DuelistCard)
				{ 
					tmp.addToBottom(gridCard); 
				}				
			}
	
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				if (anyNumber)
				{
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = "Choose " + this.amount + " Card to Overflow";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Overflow"; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					for (AbstractCard c : tmp.group)
					{
						if (!(c instanceof CancelCard))
						{
							if (c instanceof DuelistCard)
							{
								for (int i = 0; i < this.overflowsToTrigger; i++)
								{
									((DuelistCard)c).triggerOverflowEffect();
								}								
							}
						}					
					}
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
			}
			
			else
			{				
				if (this.anyNumber)
				{		
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = "Choose " + this.amount + " Card to Overflow";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Overflow"; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Card to Overflow", false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Cards to Overflow", false); }
				}
				
			}
			tickDuration();
			return;
		}

		if (!anyNumber)
		{
			// If there are more cards
			if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
			{
				for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
				{
					Util.log("CardSelectScreenIntoHandAction found " + c.name + " in selection");
					c.unhover();
					if (!(c instanceof CancelCard))
					{
						if (c instanceof DuelistCard)
						{
							for (int i = 0; i < this.overflowsToTrigger; i++)
							{
								((DuelistCard)c).triggerOverflowEffect();
							}								
						}
					}				
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();		
			}
		}
		else if (this.dcss != null && this.dcss.selectedCards.size() != 0)
		{
			for (AbstractCard c : this.dcss.selectedCards)
			{
				Util.log("CardSelectScreenIntoHandAction found " + c.name + " in this.dcss");
				c.unhover();
				if (!(c instanceof CancelCard))
				{
					if (c instanceof DuelistCard)
					{
						for (int i = 0; i < this.overflowsToTrigger; i++)
						{
							((DuelistCard)c).triggerOverflowEffect();
						}								
					}
				}				
			}
			this.dcss.selectedCards.clear();
		}
		tickDuration();
	}

}
