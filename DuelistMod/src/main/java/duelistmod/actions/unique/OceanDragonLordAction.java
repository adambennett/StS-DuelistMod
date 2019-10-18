package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.powers.duelistPowers.Dragonscales;

public class OceanDragonLordAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public OceanDragonLordAction() {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.POWER;
	}
	
	@Override
	public void update() {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(Dragonscales.POWER_ID)) { Dragonscales pow = (Dragonscales)p.getPower(Dragonscales.POWER_ID); pow.convertToThorns(); }
		this.isDone = true;
	}
	
}
