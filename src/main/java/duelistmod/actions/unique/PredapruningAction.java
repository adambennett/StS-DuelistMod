package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.variables.Tags;

public class PredapruningAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private AbstractMonster m;

	public PredapruningAction(int amount)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = AbstractDungeon.getRandomMonster();
	}
	
	public PredapruningAction(int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = m;
	}
	
	public PredapruningAction(int amount, boolean upgraded, AbstractMonster m)
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
				if (c.hasTag(Tags.PLANT) && DuelistCard.allowResummonsWithExtraChecks(c))
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
			else if (tmp.size() == 1) 
			{
				AbstractCard card = tmp.getTopCard();
				// Play card
				DuelistCard cardCopy = (DuelistCard)card;
    			if (cardCopy != null && m != null)
    			{
    				DuelistCard.fullResummon(cardCopy, this.upgrade, m, false);
    				/*if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
    				if (this.upgrade) { cardCopy.upgrade(); }
    				cardCopy.freeToPlayOnce = true;
    				cardCopy.applyPowers();
    				cardCopy.purgeOnUse = true;
    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
    				cardCopy.checkResummon();*/
    				if (cardCopy.hasTag(Tags.PREDAPLANT)) { DuelistCard.addCardToHand(cardCopy.makeStatEquivalentCopy()); }
    			}
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in discard to satisfy requested # cards, but some cards in discard
			else if (tmp.size() <= this.amount) 
			{
				for (int i = 0; i < tmp.size(); i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
					// Play card
					DuelistCard cardCopy = (DuelistCard)card;
	    			if (cardCopy != null && m != null)
	    			{
	    				DuelistCard.fullResummon(cardCopy, this.upgrade, m, false);
	    				/*if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (this.upgrade) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    				cardCopy.checkResummon();*/
	    				if (cardCopy.hasTag(Tags.PREDAPLANT)) { DuelistCard.addCardToHand(cardCopy.makeStatEquivalentCopy()); }
	    			}
				}
				this.isDone = true;
				return;
			}
			
			// Open card selection window
			if (this.amount == 1) 
			{
				SelectScreenHelper.open(tmp, this.amount, "Special Summon a Plant");
			} 
			else 
			{
				SelectScreenHelper.open(tmp, this.amount, "Special Summon a Plant");
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
    				/*if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
    				if (this.upgrade) { cardCopy.upgrade(); }
    				cardCopy.freeToPlayOnce = true;
    				cardCopy.applyPowers();
    				cardCopy.purgeOnUse = true;
    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
    				cardCopy.checkResummon();*/
    				if (cardCopy.hasTag(Tags.PREDAPLANT)) { DuelistCard.addCardToHand(cardCopy.makeStatEquivalentCopy()); }
    			}
			
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		tickDuration();
	}
}


