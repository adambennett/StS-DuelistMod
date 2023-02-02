package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class ResummonFromDiscardAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private AbstractMonster m;

	public ResummonFromDiscardAction(int amount)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = AbstractDungeon.getRandomMonster();
	}
	
	public ResummonFromDiscardAction(int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = m;
	}
	
	public ResummonFromDiscardAction(int amount, boolean upgraded, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.m = m;
	}


	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) 
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : this.p.discardPile.group) 
			{
				if (c.hasTag(Tags.MONSTER) && DuelistCard.allowResummonsWithExtraChecks(c))
				{
					tmp.addToRandomSpot(c);
				}
			}

			// No cards in discard pile
			if (tmp.size() == 0) 
			{
				this.isDone = true;
				return; 
			}
			
			// Only 1 card in discard pile
			if (tmp.size() == 1) 
			{
				AbstractCard card = tmp.getTopCard();
				if (card instanceof DuelistCard && m != null)
				{
					DuelistCard cardCopy = (DuelistCard)card;
					DuelistCard.resummon(cardCopy, m, false, this.upgrade);
				}
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in discard to satisfy requested # cards, but some cards in discard
			if (tmp.size() <= this.amount) 
			{
				for (int i = 0; i < tmp.size(); i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(i);
					// Play card
					DuelistCard cardCopy = (DuelistCard)card;
	    			if (cardCopy != null && m != null)
	    			{
	    				DuelistCard.resummon(cardCopy, m, false, this.upgrade);
	    			}
					//this.p.discardPile.removeCard(card);
				}
				this.isDone = true;
				return;
			}

			// Open card selection window
			if (this.amount == 1) 
			{
				AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Resummon a Monster", false);
			} 
			else 
			{
				AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Resummon a Monster", false);
			}
			tickDuration();
			return;
		}



		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) 
			{
				c.unhover();
				c.stopGlowing();
				// Play card
				DuelistCard cardCopy = (DuelistCard)c;
    			if (cardCopy != null && m != null)
    			{
    				DuelistCard.fullResummon(cardCopy, this.upgrade, m, false);
    			}
				//this.p.discardPile.removeCard(c);			
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}
}


