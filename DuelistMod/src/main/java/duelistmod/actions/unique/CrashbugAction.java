package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.patches.DuelistCard;

public class CrashbugAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private AbstractMonster m;
	private DuelistCard callingCard;

	public CrashbugAction(int amount, DuelistCard callCard)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = AbstractDungeon.getRandomMonster();
		this.callingCard = callCard;
	}
	
	public CrashbugAction(int amount, AbstractMonster m, DuelistCard callCard)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.m = m;
		this.callingCard = callCard;
	}
	
	public CrashbugAction(int amount, boolean upgraded, AbstractMonster m, DuelistCard callCard)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.m = m;
		this.callingCard = callCard;
	}


	public void update() 
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) 
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : this.p.drawPile.group) 
			{
				if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.CRASHBUG) && !c.originalName.equals(callingCard.originalName))
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
				// Play card
				DuelistCard cardCopy = DuelistCard.newCopyOfMonster(card.originalName);
    			if (cardCopy != null)
    			{
    				//if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
    				if (this.upgrade) { cardCopy.upgrade(); }
    				cardCopy.freeToPlayOnce = true;
    				cardCopy.applyPowers();
    				cardCopy.purgeOnUse = true;
    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
    				//cardCopy.onResummon(1);
    				//cardCopy.checkResummon();
    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
    			}
    			AbstractDungeon.player.drawPile.moveToDiscardPile(card);
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
					DuelistCard cardCopy = DuelistCard.newCopyOfMonster(card.originalName);
	    			if (cardCopy != null)
	    			{
	    				//if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (this.upgrade) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    				//cardCopy.onResummon(1);
	    				//cardCopy.checkResummon();
	    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
	    			}
	    			AbstractDungeon.player.drawPile.moveToDiscardPile(card);
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
	    			if (cardCopy != null)
	    			{
	    				//if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (this.upgrade) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    				//cardCopy.onResummon(1);
	    				//cardCopy.checkResummon();
	    				//cardCopy.summonThis(cardCopy.summons, cardCopy, 0, m);
	    			}
	    			AbstractDungeon.player.drawPile.moveToDiscardPile(card);
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


