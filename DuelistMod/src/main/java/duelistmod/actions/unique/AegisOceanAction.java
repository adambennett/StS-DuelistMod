package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AegisOceanAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int amount = 0;

    public AegisOceanAction(int amt)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        amount = amt;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
        	if (AbstractDungeon.player.discardPile.group.size() > 0)
			{
				ArrayList<AbstractCard> allDiscardCards = new ArrayList<AbstractCard>();
				ArrayList<AbstractCard> playableCards = new ArrayList<AbstractCard>();
				AbstractMonster refMon = AbstractDungeon.getRandomMonster();
				for (AbstractCard c : AbstractDungeon.player.discardPile.group)
				{
					if (!c.type.equals(CardType.STATUS) && !c.type.equals(CardType.CURSE))
					{
						allDiscardCards.add(c);
						if (refMon != null)
						{
							if (c.canUse(AbstractDungeon.player, refMon))
							{
								playableCards.add(c);
							}
						}
					}
				}
				
				if (playableCards.size() >= this.amount)
				{
					while (playableCards.size() > this.amount)
					{
						playableCards.remove(AbstractDungeon.cardRandomRng.random(playableCards.size() - 1));
					}
					
					for (AbstractCard c : playableCards)
					{
						this.addToBot(new DiscardToHandAction(c));
					}
				}

				else if (playableCards.size() > 0 && allDiscardCards.size() > 0)
				{
					ArrayList<AbstractCard> newSet = new ArrayList<>();
					newSet.addAll(playableCards);
					int needed = this.amount - newSet.size();
					if (needed > 0)
					{
						while (allDiscardCards.size() > needed)
						{
							allDiscardCards.remove(AbstractDungeon.cardRandomRng.random(allDiscardCards.size() - 1));
						}
						
						newSet.addAll(allDiscardCards);
					}
					
					for (AbstractCard c : newSet)
					{
						this.addToBot(new DiscardToHandAction(c));
					}
				}
				
				else if (allDiscardCards.size() > 0)
				{
					if (allDiscardCards.size() <= this.amount)
					{
						for (AbstractCard c : allDiscardCards)
						{
							this.addToBot(new DiscardToHandAction(c));
						}
					}
					else 
					{
						while (allDiscardCards.size() > this.amount)
						{
							allDiscardCards.remove(AbstractDungeon.cardRandomRng.random(allDiscardCards.size() - 1));
						}
						
						for (AbstractCard c : allDiscardCards)
						{
							this.addToBot(new DiscardToHandAction(c));
						}
					}
				}
			}
        }
        tickDuration();
    }
}
