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
import duelistmod.variables.Tags;

public class StepFour extends AbstractGameAction
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

	public StepFour(boolean entomb, boolean randomCardChoice, int noOfCards, int manaCostSoFar, boolean sendToHand, boolean sendToDeck, boolean upGrade)
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
		this.upgrade = upGrade;
		this.entomb = entomb;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			tmp.addToTop(new CustomCardOption(this.manaCost, 8, 2));
			tmp.addToTop(new CustomCardOption(this.manaCost, 9, 3));
			tmp.addToTop(new CustomCardOption(this.manaCost, 10, 1));
			tmp.addToTop(new CustomCardOption(this.manaCost, 11, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 12, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 13, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 14, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 15, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 16, 0));
			tmp.addToTop(new CustomCardOption(this.manaCost, 17, 0));
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 4 - Choose which types of cards can be Resummoned by the custom card", false, false, false, false); 	
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
					if (ref.descInd == 8) { this.addToBot(new StepFive(this.entomb, Tags.MONSTER, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 9) { this.addToBot(new StepFive(this.entomb, null, this.randomCard, this.noCards, this.manaCost - ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 10) { this.addToBot(new StepFive(this.entomb, Tags.ZOMBIE, this.randomCard, this.noCards, this.manaCost, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 11) { this.addToBot(new StepFive(this.entomb, Tags.UNDEAD, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 12) { this.addToBot(new StepFive(this.entomb, Tags.POSSESSED, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 13) { this.addToBot(new StepFive(this.entomb, Tags.MAYAKASHI, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 14) { this.addToBot(new StepFive(this.entomb, Tags.VAMPIRE, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 15) { this.addToBot(new StepFive(this.entomb, Tags.SHIRANUI, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 16) { this.addToBot(new StepFive(this.entomb, Tags.GHOSTRICK, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else if (ref.descInd == 17) { this.addToBot(new StepFive(this.entomb, Tags.VENDREAD, this.randomCard, this.noCards, this.manaCost + ref.magicNumber, this.sendHand, this.sendDeck, this.upgrade)); }
					else { Util.log("Step 4: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}	
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
