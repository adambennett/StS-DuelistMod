package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.powers.duelistPowers.OverworkedPower;

public class OverworkedDebuffModAction extends AbstractGameAction 
{
	public OverworkedDebuffModAction() {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
	}
	
	@Override
	public void update() 
	{
		if (AbstractDungeon.player.hasPower(OverworkedPower.POWER_ID))
		{
			OverworkedPower pow = (OverworkedPower)AbstractDungeon.player.getPower(OverworkedPower.POWER_ID);
			pow.setToDebuff();
		}
		this.isDone = true;
	}
	
}
