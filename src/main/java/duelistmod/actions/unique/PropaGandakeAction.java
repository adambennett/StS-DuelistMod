package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class PropaGandakeAction extends AbstractGameAction {
	private final AnyDuelist duelist;

	public PropaGandakeAction(AnyDuelist duelist, int amount) {
		this.duelist = duelist;
		this.amount = amount;
	}
	
	public void update() {
		int feralOrTerritorial = (int) this.duelist.hand().stream().filter(c -> c.hasTag(Tags.FERAL) || c.hasTag(Tags.TERRITORIAL)).count();
		if (feralOrTerritorial > 0 && this.amount > 0) {
			DuelistCard.vulnAllEnemies(this.amount);
			DuelistCard.weakAllEnemies(this.amount, this.duelist);
		}
		this.isDone = true;
	}
}
