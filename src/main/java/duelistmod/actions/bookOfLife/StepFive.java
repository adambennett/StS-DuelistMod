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

public class StepFive extends AbstractGameAction
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
    private CardTags restrict = Tags.ALL;

	public StepFive(boolean entomb, CardTags restrictType, boolean randomCardChoice, int noOfCards, int manaCostSoFar, boolean sendToHand, boolean sendToDeck, boolean upgrade)
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
		this.upgrade = upgrade;
		this.entomb = entomb;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			if (this.randomCard)
			{
				tmp.addToTop(new CustomCardOption(this.manaCost, 18, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 19, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 20, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 21, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 22, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 23, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 24, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 25, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 26, 0));
				tmp.addToTop(new CustomCardOption(this.manaCost, 27, 1));
			}
			else
			{
				tmp.addToTop(new CustomCardOption(this.manaCost, 18, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 19, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 33, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 21, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 34, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 35, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 24, 3));
				tmp.addToTop(new CustomCardOption(this.manaCost, 25, 3));
				tmp.addToTop(new CustomCardOption(this.manaCost, 36, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 37, 3));
			}
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 5 - Choose where the Special Summoned cards will be generated from", false, false, false, false);
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
					if (ref.descInd == 18) { this.addToBot(new StepSix(this.entomb, 0, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 19) { this.addToBot(new StepSix(this.entomb, 1, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 20) { this.addToBot(new StepSix(this.entomb, 2, this.restrict, this.randomCard, this.noCards, this.manaCost, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 21) { this.addToBot(new StepSix(this.entomb, 3, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 22) { this.addToBot(new StepSix(this.entomb, 4, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 23) { this.addToBot(new StepSix(this.entomb, 5, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 24) { this.addToBot(new StepSix(this.entomb, 6, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 25) { this.addToBot(new StepSix(this.entomb, 7, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 26) { this.addToBot(new StepSix(this.entomb, 8, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 27) { this.addToBot(new StepSix(this.entomb, 9, this.restrict, this.randomCard, this.noCards, this.manaCost - ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					
					else if (ref.descInd == 33) { this.addToBot(new StepSix(this.entomb, 2, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 34) { this.addToBot(new StepSix(this.entomb, 4, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 35) { this.addToBot(new StepSix(this.entomb, 5, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 36) { this.addToBot(new StepSix(this.entomb, 8, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 37) { this.addToBot(new StepSix(this.entomb, 9, this.restrict, this.randomCard, this.noCards, this.manaCost + ref.baseMagicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else { Util.log("Step 5: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
