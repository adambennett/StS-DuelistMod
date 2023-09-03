package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.enemy.EnemyDark;
import duelistmod.variables.Tags;

public class ManticoreDarknessAction extends AbstractGameAction {
	private final int darkOrbs;
	private final AnyDuelist duelist;

	public ManticoreDarknessAction(AnyDuelist duelist, int darkOrbs) {
		this.duelist = duelist;
		this.darkOrbs = darkOrbs;
	}
	
	public void update() {
		int beasts = (int) this.duelist.hand().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
		if (beasts > 0 && this.darkOrbs > 0) {
			AbstractOrb dark = this.duelist.player() ? new Dark() : this.duelist.getEnemy() != null ? new EnemyDark() : null;
			if (dark != null) {
				this.duelist.channel(dark, this.darkOrbs * beasts);
			}
		}
		this.isDone = true;
	}
}
