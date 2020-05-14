package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.GridSort;

public class CardSelectScreenNaturiaShuffleAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<DuelistCard> cards;
	private boolean canCancel = false;
  
	// Cards: 	Dark Paladin, Gemini Elf, Rainbow Jar, Shard of Greed, Toon Masked Sorcerer, Winged Kuriboh Lv 9 & Lv10, Rainbow Gravity, Orb Token
	// Relics: 	Millennium Puzzle orb deck effect
	// Potions: Big Orb Bottle
	public CardSelectScreenNaturiaShuffleAction(ArrayList<DuelistCard> cardsToChooseFrom, int amount, boolean canCancel)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.canCancel = canCancel;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				AbstractCard gridCard = card.makeStatEquivalentCopy();
		        gridCard.initializeDescription();
				tmp.addToTop(gridCard);
				if (DuelistMod.debug) { System.out.println("theDuelist:CardSelectScreenResummonAction:update() ---> added " + gridCard.originalName + " into grid selection pool"); }
			}
			
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose a card for Naturia Antjaw", false, false, false, false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " cards for Naturia Antjaw", false, false, false, false); }
			tickDuration();
			return;
			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, true));
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
