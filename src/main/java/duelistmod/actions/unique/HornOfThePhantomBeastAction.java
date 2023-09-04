package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.StrengthUpPower;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class HornOfThePhantomBeastAction extends AbstractGameAction {
	private final AnyDuelist duelist;

	public HornOfThePhantomBeastAction(AnyDuelist duelist) {
		this.duelist = duelist;
	}
	
	public void update() {
		if (duelist.hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)duelist.getPower(SummonPower.POWER_ID);
			int beasts = (int) pow.getCardsSummoned().stream().filter(c -> c.hasTag(Tags.BEAST)).count();
			if (beasts > 0) {
				duelist.applyPowerToSelf(new StrengthUpPower(duelist.creature(), duelist.creature(), this.amount * beasts));
			}
		}
		this.isDone = true;
	}
}
