package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustSpecificCardListSuperFastAction extends AbstractGameAction
{
	private ArrayList<AbstractCard> targetCard;
	private CardGroup group;
	private float startingDuration;

	public ExhaustSpecificCardListSuperFastAction(ArrayList<AbstractCard> targetCard, CardGroup group, boolean isFast)
	{
		this.targetCard = new ArrayList<AbstractCard>();
		this.targetCard.addAll(targetCard);
		setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
		this.actionType = AbstractGameAction.ActionType.EXHAUST;
		this.group = group;
		this.startingDuration = 0.001F;
		this.duration = 0.001F;
	}

	public ExhaustSpecificCardListSuperFastAction(ArrayList<AbstractCard> targetCard, CardGroup group) 
	{
		this(targetCard, group, false);
	}

	public void update()
	{
		if ((this.duration == this.startingDuration)) 
		{
			for (AbstractCard c : this.targetCard)
			{
				if (this.group.contains(c))
				{
					this.group.moveToExhaustPile(c);
					CardCrawlGame.dungeon.checkForPactAchievement();
					c.exhaustOnUseOnce = false;
					c.freeToPlayOnce = false;
				}
			}	
		}
		tickDuration();
	}
}

