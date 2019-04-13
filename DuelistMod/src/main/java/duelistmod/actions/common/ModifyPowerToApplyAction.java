package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.interfaces.BuffCard;

public class ModifyPowerToApplyAction extends AbstractGameAction {
	private BuffCard cardToModify;
	private AbstractPower power;
	
	public ModifyPowerToApplyAction(BuffCard card, AbstractPower p) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
		this.power = p;
	}
	
	@Override
	public void update() 
	{
		this.cardToModify.powerToApply = power;
		this.cardToModify.rawDescription = "Gain !M! " + power.name + ".";
		this.cardToModify.name = power.name;
		this.cardToModify.initializeDescription();
		this.isDone = true;
	}
	
}
