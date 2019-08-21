package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.actions.common.EvokeSpecificOrbAction;

public class DarkHunterAction extends AbstractGameAction 
{
	public DarkHunterAction(int timesToEvoke) 
	{
		this.setValues(this.target, this.source, timesToEvoke);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
	}
	
	@Override
	public void update() 
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasOrb())
		{
			AbstractOrb o = p.orbs.get(AbstractDungeon.cardRandomRng.random(p.orbs.size() - 1));
			AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(o, this.amount));
		}
		this.isDone = true;
	}
	
}
