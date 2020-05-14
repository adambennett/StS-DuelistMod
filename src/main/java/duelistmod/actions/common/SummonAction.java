package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;

public class SummonAction extends AbstractGameAction 
{
	private int summons = 1;
	private DuelistCard summonCard;
	
	public SummonAction(int summons, DuelistCard card) 
	{
		this.setValues(this.target, this.source, summons);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.summons = summons;
		this.summonCard = card;
	}
	
	@Override
	public void update() {
		if (this.summons > 0)
		{
			DuelistCard.summon(AbstractDungeon.player, this.summons, this.summonCard);			
		}
		this.isDone = true;
	}
	
}
