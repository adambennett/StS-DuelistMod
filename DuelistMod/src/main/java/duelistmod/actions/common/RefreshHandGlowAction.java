package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class RefreshHandGlowAction extends AbstractGameAction 
{
	AbstractPlayer p;

	public RefreshHandGlowAction(AbstractPlayer player) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.p = player;
	}
	
	@Override
	public void update() {
		this.p.hand.glowCheck();
		this.isDone = true;
	}
	
}
