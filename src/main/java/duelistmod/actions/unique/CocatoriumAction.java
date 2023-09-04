package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.duelistPowers.CocatoriumPower;
import duelistmod.variables.Tags;

public class CocatoriumAction extends AbstractGameAction {
	private final CocatoriumPower power;
	private final AnyDuelist duelist;

	public CocatoriumAction(AnyDuelist duelist, CocatoriumPower power) {
		this.duelist = duelist;
		this.power = power;
	}
	
	public void update() {
		int beasts = (int) duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
		if (beasts > this.amount) {
			this.power.setDamageBoostActive(true);
		}
		this.isDone = true;
	}
}
