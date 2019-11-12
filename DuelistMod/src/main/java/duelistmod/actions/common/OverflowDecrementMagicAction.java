package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.powers.duelistPowers.SeaDwellerPower;

public class OverflowDecrementMagicAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public OverflowDecrementMagicAction(AbstractCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() 
	{
		if (AbstractDungeon.player.hasPower(SeaDwellerPower.POWER_ID)) { this.isDone = true; }
		else
		{
			this.cardToModify.baseMagicNumber += this.amount;
			if (this.cardToModify.baseMagicNumber < 0) {
				this.cardToModify.baseMagicNumber = 0;
			}
			this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
			this.isDone = true;
		}
	}
	
}
