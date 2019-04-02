package defaultmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QueueCardSuperFastAction extends AbstractGameAction
{
	private AbstractCard card;
	private boolean isSetDuration = false;

	public QueueCardSuperFastAction()
	{
		this.duration = 0.001F;
		this.isSetDuration = false;
	}

	public QueueCardSuperFastAction(AbstractCard card, AbstractCreature target) {
		this.duration = 0.001F;
		this.card = card;
		this.target = target;
		this.isSetDuration = false;
	}
	
	public QueueCardSuperFastAction(AbstractCard card, AbstractCreature target, float setDuration) {
		this.duration = setDuration;
		this.card = card;
		this.target = target;
		this.isSetDuration = true;
	}

	public void update()
	{
		if (this.duration == 0.001F || isSetDuration) 
		{
			if (this.card == null)
			{
				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem());

			}
			else if (!queueContains(this.card)) {
				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this.card, (AbstractMonster)this.target));
			}

			this.isDone = true;
			this.isSetDuration = false;
		}
	}

	private boolean queueContains(AbstractCard card) 
	{
		for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) 
		{
			if (i.card == card) 
			{
				return true;
			}
		}
		return false;
	}
}