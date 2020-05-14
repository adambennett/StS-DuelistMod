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

public class CardSelectScreenModifyOverflowAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<DuelistCard> cards;
	private boolean canCancel = false;
	private boolean anyNumber = false;
	private int overflowInc = 0;
	private DuelistCardSelectScreen dcss;

	public CardSelectScreenModifyOverflowAction(ArrayList<DuelistCard> cardsToChooseFrom, int amount, int overflowInc)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.overflowInc = overflowInc;
		this.dcss = new DuelistCardSelectScreen(false);
		this.setValues(this.p, AbstractDungeon.player, amount);
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				tmp.addToBottom(gridCard);
			}
	
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				if (anyNumber)
				{
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = "Choose " + this.amount + " Overflow Card to Modify";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Overflow Cards to Modify"; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					for (AbstractCard c : tmp.group)
					{
						if (!(c instanceof CancelCard))
						{
							this.addToBot(new OverflowModifyAction(c, this.overflowInc));
							this.p.hand.refreshHandLayout();
							this.p.hand.applyPowers();
						}					
					}
				}
				
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.p.hand.refreshHandLayout();
			}
			
			else
			{				
				if (this.anyNumber)
				{		
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = "Choose " + this.amount + " Overflow Card to Modify";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Overflow Cards to Modify"; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Overflow Card to Modify", false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Overflow Cards to Modify", false); }
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
						this.addToBot(new OverflowModifyAction(c, this.overflowInc));
						this.p.hand.refreshHandLayout();
						this.p.hand.applyPowers();
					}				
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();			
				this.p.hand.refreshHandLayout();
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
					this.addToBot(new OverflowModifyAction(c, this.overflowInc));
					this.p.hand.refreshHandLayout();
					this.p.hand.applyPowers();
				}				
			}
			this.dcss.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}

}
