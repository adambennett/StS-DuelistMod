package duelistmod.actions.bookOfLife;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.bookOfLifeOptions.*;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class StepSeven extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private boolean sendHand = false;
    private boolean sendDeck = false;
    private boolean randomCard = false;
    private boolean randomTarg = false;
    private boolean upgrade = false;
    private boolean entomb = false;
    private int noCards = 0;
    private int manaCost = 0;
    private int location = -1;
    private CardTags restrict = Tags.ALL;

	public StepSeven(boolean entomb, boolean randomTarget, int locationIndex, CardTags restrictType, boolean randomCardChoice, int noOfCards, int manaCostSoFar, boolean sendToHand, boolean sendToDeck, boolean upGrade)
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
		this.randomTarg = randomTarget;
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
				tmp.addToTop(new CustomCardOption(this.manaCost, 30, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 31, 2));
				tmp.addToTop(new CustomCardOption(this.manaCost, 32, 2));
			}
			
			else
			{
				tmp.addToTop(new CustomCardOption(this.manaCost, 30, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 31, 1));
				tmp.addToTop(new CustomCardOption(this.manaCost, 32, 1));
			}

			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Final Step - Choose additional card modifiers for the custom card", false, false, false, false); 	
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
					boolean exh = false;
					boolean eth = false;
					boolean cardinal = false;
					CustomCardOption ref = (CustomCardOption)c;
					if (ref.descInd == 30 || ref.descInd == 31 || ref.descInd == 32)
					{
						if (ref.descInd == 30) { exh = true; }
						else if (ref.descInd == 31) { eth = true; }
						else if (ref.descInd == 32) { cardinal = true; }
						int cost = this.manaCost - ref.magicNumber;
						if (cost < 0) { cost = 0; }
						CustomResummonCard customCard = new CustomResummonCard(cost, this.noCards, this.randomCard, this.restrict, this.location, this.randomTarg, exh, eth, cardinal);
						if (upgrade) { customCard.upgrade(); }
						if (sendHand)
						{
							DuelistCard.addCardToHand(customCard);
						}
						
						if (sendDeck)
						{
							AbstractDungeon.player.masterDeck.addToTop(customCard);
						}
						
						if (entomb)
						{
							Util.entombCard(customCard);
						}
						
						if (!sendHand && !sendDeck && !entomb)
						{
							Util.log("Created custom card, but for some reason we intend to send it nowhere? Defaulting to send to hand to prevent the player from getting NOTHING after 7 selection screens...");
						}
					
					}
					else
					{
						Util.log("Step 7: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd);
					}
				}	
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
