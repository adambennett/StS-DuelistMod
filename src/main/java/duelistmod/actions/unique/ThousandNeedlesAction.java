package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

public class ThousandNeedlesAction extends AbstractGameAction {
	private final AnyDuelist duelist;
	private final DuelistCard caller;
	private final int lowDamage;
	private final int highDamage;

	public ThousandNeedlesAction(DuelistCard thousandNeedles, int amount, int lowDamage, int highDamage) {
		this.caller = thousandNeedles;
		this.duelist = AnyDuelist.from(thousandNeedles);
		this.amount = amount;
		this.lowDamage = lowDamage;
		this.highDamage = highDamage;
	}
	
	public void update() {
		if (this.amount < 1) {
			this.isDone = true;
			return;
		}

		this.amount--;
		AbstractCreature target = duelist.getEnemy() != null ? AbstractDungeon.player : duelist.player() ? AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng) : null;
		if (target != null) {
			int dmgRoll = AbstractDungeon.cardRandomRng.random(this.lowDamage, this.highDamage);
			this.caller.attack(target, caller.baseAFX, dmgRoll);
		}

		if (this.amount > 0) {
			this.addToBot(new ThousandNeedlesAction(this.caller, this.amount, this.lowDamage, this.highDamage));
			this.isDone = true;
		}
	}
}
