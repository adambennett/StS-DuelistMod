package duelistmod.actions.bookOfLife;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.cards.other.bookOfLifeOptions.CustomCardOption;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;

public class StepTwo extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private boolean sendHand = false;
    private boolean sendDeck = false;
    private boolean entomb = false;
    private boolean upgrade = false;

	public StepTwo(boolean entomb, boolean sendToHand, boolean sendToDeck, boolean upGrade)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.sendDeck = sendToDeck;
		this.sendHand = sendToHand;
		this.upgrade = upGrade;
		this.entomb = entomb;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			if (upgrade)
			{
				tmp.addToTop(new CustomCardOption(0, 3, 0));
				tmp.addToTop(new CustomCardOption(0, 4, 1));
				tmp.addToTop(new CustomCardOption(0, 5, 2));
			}
			else
			{
				tmp.addToTop(new CustomCardOption(0, 3, 1));
				tmp.addToTop(new CustomCardOption(0, 4, 2));
				tmp.addToTop(new CustomCardOption(0, 5, 3));
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 2 - Choose how many cards will be Resummoned by the custom card", false, false, false, false); 	
			}	
			
			if (upgrade)
			{
				for (AbstractCard c : tmp.group) { c.upgrade(); }
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> reverseList = new ArrayList<>();
			for (int i = AbstractDungeon.gridSelectScreen.selectedCards.size() - 1; i > -1; i--)
			{
				reverseList.add(AbstractDungeon.gridSelectScreen.selectedCards.get(i));
			}
			for (AbstractCard c : reverseList)
			{
				c.unhover();
				if (c instanceof CustomCardOption)
				{
					CustomCardOption ref = (CustomCardOption)c;
					if (ref.descInd == 3) { this.addToBot(new StepThree(this.entomb, 1, ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 4) { this.addToBot(new StepThree(this.entomb, 2, ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 5) { this.addToBot(new StepThree(this.entomb, 3, ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else { Util.log("Step 2: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}		
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
