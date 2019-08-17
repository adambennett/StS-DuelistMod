package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.tokens.RelicToken;

public class SummonAnchorAction extends AbstractGameAction 
{
	int summons = 1;
	
	public SummonAnchorAction(int summons) 
	{
		this.setValues(this.target, this.source, summons);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.summons = summons;
	}
	
	@Override
	public void update() {
		if (this.summons > 0)
		{
			DuelistCard.summon(AbstractDungeon.player, this.summons, new RelicToken());			
		}
		this.isDone = true;
	}
	
}
