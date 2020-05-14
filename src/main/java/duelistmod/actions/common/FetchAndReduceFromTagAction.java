package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;

public class FetchAndReduceFromTagAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private CardGroup source;
	private int costReduce;
	private int tribReduce;
	private boolean combat;
	private CardTags search;
	
	public FetchAndReduceFromTagAction(int amount, CardGroup source, int costReduce, int tribReduce, boolean combat, CardTags tag)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.costReduce = costReduce;
		this.tribReduce = tribReduce;
		this.source = source;
		this.combat = combat;
		this.search = tag;
	}

	public FetchAndReduceFromTagAction(int amount, CardGroup source, int costReduce, boolean combat, CardTags tag)
	{
		this(amount, source, costReduce, 0, combat, tag);
	}


	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) 
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : source.group)  
			{				
				if (c.hasTag(this.search)) { tmp.addToRandomSpot(c); } 			
			}

			// No cards in discard pile
			if (tmp.size() == 0) 
			{
				this.isDone = true;
				return; 
			}
			
			// Only 1 card in draw pile
			if (tmp.size() == 1) 
			{
				AbstractCard card = tmp.getTopCard();
				if (this.upgrade) { card.upgrade(); }
				if (this.costReduce > 0) 
				{
					if (this.combat)
					{
						card.modifyCostForCombat(-this.costReduce);
					}
					else
					{
						card.setCostForTurn(-this.costReduce);
					}
				}
				if (this.tribReduce > 0 && card instanceof DuelistCard) 
				{
					DuelistCard dc = (DuelistCard)card;
					if (this.combat)
					{
						dc.modifyTributes(-this.tribReduce);
					}
					else
					{
						dc.modifyTributesForTurn(-this.tribReduce);
					}
				}
				DuelistCard.addCardToHand(card);
				source.removeCard(card);
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in draw to satisfy requested # cards, but some cards in draw
			if (tmp.size() <= this.amount) 
			{
				for (int i = 0; i < tmp.size(); i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(i);
					if (this.upgrade) { card.upgrade(); }
					if (this.costReduce > 0) 
					{
						if (this.combat)
						{
							card.modifyCostForCombat(-this.costReduce);
						}
						else
						{
							card.setCostForTurn(-this.costReduce);
						}
					}
					if (this.tribReduce > 0 && card instanceof DuelistCard) 
					{
						DuelistCard dc = (DuelistCard)card;
						if (this.combat)
						{
							dc.modifyTributes(-this.tribReduce);
						}
						else
						{
							dc.modifyTributesForTurn(-this.tribReduce);
						}
					}
					DuelistCard.addCardToHand(card);
					source.removeCard(card);
				}
				this.isDone = true;
				return;
			}

			// Open card selection window
			if (this.amount == 1) 
			{
				AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Fetch and reduce a Card", false);
			} 
			else 
			{
				AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Fetch and reduce " + this.amount + " Cards", false);
			}
			tickDuration();
			return;
		}



		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) 
			{
				c.unhover();
				if (this.upgrade) { c.upgrade(); }
				if (this.costReduce > 0) 
				{
					if (this.combat)
					{
						c.modifyCostForCombat(-this.costReduce);
					}
					else
					{
						c.setCostForTurn(-this.costReduce);
					}
				}
				if (this.tribReduce > 0 && c instanceof DuelistCard) 
				{
					DuelistCard dc = (DuelistCard)c;
					if (this.combat)
					{
						dc.modifyTributes(-this.tribReduce);
					}
					else
					{
						dc.modifyTributesForTurn(-this.tribReduce);
					}
				}
				DuelistCard.addCardToHand(c);
				source.removeCard(c);
			
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}
}


