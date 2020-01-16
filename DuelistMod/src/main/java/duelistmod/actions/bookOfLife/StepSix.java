package duelistmod.actions.bookOfLife;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.cards.other.bookOfLifeOptions.CustomCardOption;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class StepSix extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private boolean sendHand = false;
    private boolean sendDeck = false;
    private boolean randomCard = false;
    private boolean upgrade = false;
    private boolean entomb = false;
    private int noCards = 0;
    private int manaCost = 0;
    private int location = -1;
    private CardTags restrict = Tags.ALL;

	public StepSix(boolean entomb, int locationIndex, CardTags restrictType, boolean randomCardChoice, int noOfCards, int manaCostSoFar, boolean sendToHand, boolean sendToDeck, boolean upGrade)
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
		this.randomCard = randomCardChoice;
		this.restrict = restrictType;
		this.location = locationIndex;
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
				tmp.addToTop(new CustomCardOption(this.manaCost, 28, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 29, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 39, 2));
			}
			else
			{*/
				tmp.addToTop(new CustomCardOption(this.manaCost, 28, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 29, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 39, 3));
			//}
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 6 - Choose if the custom card will target an enemy for Resummoning, or target a random enemy", false, false, false, false); 	
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
					if (ref.descInd == 28) { this.addToBot(new StepSeven(false, this.entomb, true, this.location, this.restrict, this.randomCard, this.noCards, this.manaCost, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 29) { this.addToBot(new StepSeven(false, this.entomb, false, this.location, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 39) { this.addToBot(new StepSeven(true, this.entomb, false, this.location, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else { Util.log("Step 6: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}	
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
