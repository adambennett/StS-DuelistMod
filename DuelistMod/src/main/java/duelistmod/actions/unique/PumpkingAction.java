package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class PumpkingAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade = false;
	private UUID callingCard;
	private AbstractMonster m;

	public PumpkingAction(int amount, boolean upgraded, AbstractMonster m, UUID callingCard)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.m = m;
		this.callingCard = callingCard;
	}

	public void update() 
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) 
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : this.p.discardPile.group) 
			{
				if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.EXEMPT) && !c.uuid.equals(callingCard))
				{
					tmp.addToRandomSpot(c);
				}
			}

			// No appropriate monsters in discard pile
			if (tmp.size() == 0) 
			{
				this.isDone = true;
				return; 
			}
			
			// At least 1 appropriate monster, so lets get a random one and Resummon it this.amount times
			else
			{
				
				AbstractCard card = tmp.getRandomCard(true);
				for (int i = 0; i < this.amount; i++)
				{
					DuelistCard dc = (DuelistCard)card;
					DuelistCard cardCopy = (DuelistCard) dc.makeStatEquivalentCopy();
	    			if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
	    			{
	    				DuelistCard.fullResummon(cardCopy, this.upgrade, m, false);
	    			}
				}
				this.isDone = true;
				return;
			}
		}
		tickDuration();
	}
}


