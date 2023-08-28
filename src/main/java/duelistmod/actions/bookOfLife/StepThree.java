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

public class StepThree extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private boolean sendHand = false;
    private boolean sendDeck = false;
    private boolean upgrade = false;
    private boolean entomb = false;
    private int noCards = 0;
    private int manaCost = 0;

	public StepThree(boolean entomb, int noOfCards, int manaCostSoFar, boolean sendToHand, boolean sendToDeck, boolean upGrade)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.sendDeck = sendToDeck;
		this.sendHand = sendToHand;
		this.manaCost += manaCostSoFar;
		this.noCards = noOfCards;      
		this.sendHand = sendToHand;
		this.sendDeck = sendToDeck;
		this.upgrade = upGrade;
		this.entomb = entomb;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			/*if (upgrade)
			{
				tmp.addToTop(new CustomCardOption(this.manaCost, 6, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 7, 0));
			}
			else
			{*/
				tmp.addToTop(new CustomCardOption(this.manaCost, 6, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 7, 0));
			//}
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 3 - Choose if the card will Special Summon a random card, or let you choose a card", false, false, false, false);
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
				c.stopGlowing();
				if (c instanceof CustomCardOption)
				{
					CustomCardOption ref = (CustomCardOption)c;
					if (ref.descInd == 6) { this.addToBot(new StepFour(this.entomb, true, this.noCards, this.manaCost - ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 7) { this.addToBot(new StepFour(this.entomb, false, this.noCards, this.manaCost, this.sendHand, this.sendDeck, this.upgrade)); }
					else { Util.log("Step 3: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}	
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
