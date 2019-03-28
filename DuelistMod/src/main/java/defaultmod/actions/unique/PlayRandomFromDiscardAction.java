package defaultmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class PlayRandomFromDiscardAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private AbstractMonster m;

	public PlayRandomFromDiscardAction(int amount)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = AbstractDungeon.getRandomMonster();
	}
	
	public PlayRandomFromDiscardAction(int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = m;
	}
	
	public PlayRandomFromDiscardAction(int amount, boolean upgraded, AbstractMonster m)
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
				if (c.hasTag(DefaultMod.MONSTER) && !c.hasTag(DefaultMod.EXEMPT))
				//if (c.hasTag(DefaultMod.MONSTER))
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
				DuelistCard cardCopy = DuelistCard.newCopyOfMonster(card.originalName);
    			if (cardCopy != null && !cardCopy.hasTag(DefaultMod.EXEMPT))
    			{
    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
    				if (this.upgrade) { cardCopy.upgrade(); }
    				cardCopy.freeToPlayOnce = true;
    				cardCopy.applyPowers();
    				cardCopy.purgeOnUse = true;
    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
    				cardCopy.onResummon(1);
    				cardCopy.checkResummon();
    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
    			}
				//this.p.discardPile.removeCard(card);
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
					DuelistCard cardCopy = DuelistCard.newCopyOfMonster(card.originalName);
	    			if (cardCopy != null && !cardCopy.hasTag(DefaultMod.EXEMPT))
	    			{
	    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (this.upgrade) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    				cardCopy.onResummon(1);
	    				cardCopy.checkResummon();
	    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
	    			}
					//this.p.discardPile.removeCard(card);
				}
				this.isDone = true;
				return;
			}
			
			else if (tmp.size() >= this.amount)
			{
				for (int i = 0; i < this.amount; i++) 
				{
					AbstractCard card = tmp.getNCardFromTop(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
					// Play card
					DuelistCard cardCopy = DuelistCard.newCopyOfMonster(card.originalName);
	    			if (cardCopy != null && !cardCopy.hasTag(DefaultMod.EXEMPT))
	    			{
	    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (this.upgrade) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    				cardCopy.onResummon(1);
	    				cardCopy.checkResummon();
	    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
	    			}
					//this.p.discardPile.removeCard(card);
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


