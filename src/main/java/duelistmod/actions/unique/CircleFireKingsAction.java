package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.actions.common.EvokeSpecificOrbAction;
import duelistmod.orbs.Lava;

public class CircleFireKingsAction extends AbstractGameAction 
{
	public CircleFireKingsAction()
	{
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
	}
	
	@Override
	public void update() 
	{
		ArrayList<Lava> lavas = new ArrayList<Lava>();
		for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Lava) { lavas.add((Lava) o); }}
		if (lavas.size() > 0)
		{ 
			AbstractOrb randLava = lavas.get(AbstractDungeon.cardRandomRng.random(lavas.size() - 1)); 
			AbstractDungeon.actionManager.addToBottom(new EvokeSpecificOrbAction(randLava, 1));
		}
		this.isDone = true;
	}
	
}
