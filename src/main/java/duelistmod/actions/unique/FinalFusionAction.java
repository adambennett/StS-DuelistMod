package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;

public class FinalFusionAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;
	private AbstractMonster target;
	private int copies = 1;
	private int amtToChoose = 1;
	
	// Cards: 	Dark Paladin, Gemini Elf, Rainbow Jar, Shard of Greed, Toon Masked Sorcerer, Winged Kuriboh Lv 9 & Lv10, Rainbow Gravity, Orb Token
	// Relics: 	Millennium Puzzle orb deck effect
	// Potions: Big Orb Bottle
	public FinalFusionAction(ArrayList<AbstractCard> cardsToChooseFrom, int copies)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.copies = copies;
		this.amtToChoose = 1;
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
				if (DuelistCard.allowResummonsWithExtraChecks(card))
				{
					AbstractCard gridCard = card.makeStatEquivalentCopy();
					if (this.target == null) { 
						Util.log("Is this it? Big bug guy? E");
						this.target = AbstractDungeon.getRandomMonster(); 
					}
					tmp.addToTop(gridCard);					
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				for (int i = 0; i < this.amtToChoose; i++) { tmp.addToTop(new CancelCard()); }
				if (this.amtToChoose == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amtToChoose, Strings.configChooseString + this.amtToChoose + " Card for Final Fusion", false, false, false, false); }
				else { AbstractDungeon.gridSelectScreen.open(tmp, this.amtToChoose, Strings.configChooseString + this.amtToChoose + " Cards for Final Fusion", false, false, false, false); }
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> exhList = new ArrayList<>();
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (this.target != null)
					{
						DuelistCard.resummon(c, this.target, this.copies);
						exhList.add(c);
					}
					else
					{						
						AbstractMonster rand = AbstractDungeon.getRandomMonster();
						if (rand != null)
						{
							Util.log("Bad thing happen for Final Fusion.. bad thing.");
							DuelistCard.resummon(c, rand, this.copies);
							exhList.add(c);
						}
					}
				}
			}			
			exhaustAll(exhList);			
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}

	private void exhaustAll(ArrayList<AbstractCard> card)
	{
		ArrayList<AbstractCard> draw = new ArrayList<>();
		ArrayList<AbstractCard> discard = new ArrayList<>();
		ArrayList<AbstractCard> hand = new ArrayList<>();
		ArrayList<AbstractCard> grave = new ArrayList<>();
		for (AbstractCard c : this.p.drawPile.group)
		{
			for (AbstractCard c2 : card)
			{
				if (c.cardID.equals(c2.cardID)) { draw.add(c); }
			}
		}
		
		for (AbstractCard c : this.p.discardPile.group)
		{
			for (AbstractCard c2 : card)
			{
				if (c.cardID.equals(c2.cardID)) { discard.add(c); }
			}
		}
		
		for (AbstractCard c : this.p.hand.group)
		{
			for (AbstractCard c2 : card)
			{
				if (c.cardID.equals(c2.cardID)) { hand.add(c); }
			}
		}
		
		for (AbstractCard c : TheDuelist.resummonPile.group)
		{
			for (AbstractCard c2 : card)
			{
				if (c.cardID.equals(c2.cardID)) { grave.add(c); }
			}
		}
		
		for (AbstractCard c : draw) { this.addToBot(new ExhaustSpecificCardAction(c, p.drawPile)); Util.log("Final Fusion is exhausting " + c.cardID + " from draw pile"); }
		for (AbstractCard c : discard) { this.addToBot(new ExhaustSpecificCardAction(c, p.discardPile)); Util.log("Final Fusion is exhausting " + c.cardID + " from discard pile");}
		for (AbstractCard c : hand) { this.addToBot(new ExhaustSpecificCardAction(c, p.hand)); Util.log("Final Fusion is exhausting " + c.cardID + " from hand");}
		for (AbstractCard c : grave) { this.addToBot(new ExhaustSpecificCardAction(c, TheDuelist.resummonPile)); Util.log("Final Fusion is exhausting " + c.cardID + " from Graveyard");}
	}
}
