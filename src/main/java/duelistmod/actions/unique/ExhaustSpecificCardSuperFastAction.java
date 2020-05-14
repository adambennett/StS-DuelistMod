package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustSpecificCardSuperFastAction extends AbstractGameAction
{
	private AbstractCard targetCard;
	private CardGroup group;
	private float startingDuration;

	public ExhaustSpecificCardSuperFastAction(AbstractCard targetCard, CardGroup group, boolean isFast)
	{
		this.targetCard = targetCard;
		setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
		this.actionType = AbstractGameAction.ActionType.EXHAUST;
		this.group = group;
		this.startingDuration = 0.001F;
		this.duration = 0.001F;
	}

	public ExhaustSpecificCardSuperFastAction(AbstractCard targetCard, CardGroup group) 
	{
		this(targetCard, group, false);
	}

	public void update()
	{
		if ((this.duration == this.startingDuration) && (this.group.contains(this.targetCard))) 
		{
			this.group.moveToExhaustPile(this.targetCard);
			CardCrawlGame.dungeon.checkForPactAchievement();
			this.targetCard.exhaustOnUseOnce = false;
			this.targetCard.freeToPlayOnce = false;
		}
		tickDuration();
	}
}

