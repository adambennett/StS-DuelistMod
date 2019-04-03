package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.Tags;

public class SarraceniantAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private CardTags searchTag;
	private CardGroup source;


	public SarraceniantAction(int amount, CardGroup source, CardTags tag)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.source = source;
		this.searchTag = tag;
	}

	
	public SarraceniantAction(int amount, CardGroup source, CardTags tag, boolean upgraded)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.source = source;
		this.searchTag = tag;
	}
	

	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) 
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : source.group) 
			{
				if (c.hasTag(searchTag))
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
    			if (card != null)
    			{
    				if (!card.tags.contains(Tags.TRIBUTE)) { card.misc = 52; }
    				if (this.upgrade) { card.upgrade(); }
    				card.freeToPlayOnce = true;
    				card.applyPowers();
    			}
				source.moveToHand(card, source);
				this.isDone = true;
				return; 
			}
			
			// Not enough cards in discard to satisfy requested # cards, but some cards in discard
			else if (tmp.size() <= this.amount) 
			{
				for (int i = 0; i < tmp.size(); i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));				
	    			if (card != null)
	    			{
	    				if (!card.tags.contains(Tags.TRIBUTE)) { card.misc = 52; }
	    				if (this.upgrade) { card.upgrade(); }
	    				card.freeToPlayOnce = true;
	    				card.applyPowers();	    				
	    			}
	    			source.moveToHand(card, source);
				}
				this.isDone = true;
				return;
			}
			
			else if (tmp.size() >= this.amount)
			{
				for (int i = 0; i < this.amount; i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
					if (card != null)
	    			{
	    				if (!card.tags.contains(Tags.TRIBUTE)) { card.misc = 52; }
	    				if (this.upgrade) { card.upgrade(); }
	    				card.freeToPlayOnce = true;
	    				card.applyPowers();	    				
	    			}
					tmp.removeCard(card);
					source.moveToHand(card, source);
				}
				this.isDone = true;
				return;
			}

			tickDuration();
			return;
		}
		tickDuration();
	}
}


