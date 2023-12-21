package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;

public class CardSelectScreenEntombAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;
	private Map<UUID, AbstractCard> cardMap;
	
	// Cards: 	Dark Paladin, Gemini Elf, Rainbow Jar, Shard of Greed, Toon Masked Sorcerer, Winged Kuriboh Lv 9 & Lv10, Rainbow Gravity, Orb Token
	// Relics: 	Millennium Puzzle orb deck effect
	// Potions: Big Orb Bottle
	public CardSelectScreenEntombAction(int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = AbstractDungeon.player.masterDeck.group;
		this.cardMap = new HashMap<>();
	}
	
	public CardSelectScreenEntombAction(ArrayList<AbstractCard> cardPool, int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = cardPool;
		this.cardMap = new HashMap<>();
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				if (Util.canEntomb(card))
				{
					AbstractCard ref = card.makeStatEquivalentCopy();
					tmp.addToTop(ref);
					this.cardMap.put(ref.uuid, card);
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.amount == 1) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " card to Entomb"); }
				else { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " cards to Entomb"); }
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (Util.canEntomb(c))
					{
						if (this.cardMap.containsKey(c.uuid))
						{
							AbstractCard deckCard = this.cardMap.get(c.uuid);
							Util.entombCard(deckCard);
						}
						else { Util.log("Entomb selection screen did not find " + c.cardID + " in the map?"); }
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
