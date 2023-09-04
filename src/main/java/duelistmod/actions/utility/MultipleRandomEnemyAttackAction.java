package duelistmod.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class MultipleRandomEnemyAttackAction extends AbstractGameAction {

	private final AnyDuelist duelist;
	private final DuelistCard attacker;
	private final int amountOfEnemiesToAttack;
	private final int baseDamage;
	private final DamageType dmgType;
	private final Consumer<Integer> followUpAction;
	private AttackEffect afx;

	public MultipleRandomEnemyAttackAction(DuelistCard attacker, int baseDamage, int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType) {
		this(attacker, baseDamage, amountOfEnemiesToAttack, afx, dmgType, null);
	}

	public MultipleRandomEnemyAttackAction(DuelistCard attacker, int baseDamage, int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType, Consumer<Integer> followUpAction) {
		this.attacker = attacker;
		this.duelist = AnyDuelist.from(attacker);
		this.amountOfEnemiesToAttack = amountOfEnemiesToAttack;
		this.afx = afx;
		this.baseDamage = baseDamage;
		this.dmgType = dmgType;
		this.followUpAction = followUpAction;
	}
	
	public void update() {
		if (amountOfEnemiesToAttack < 1) {
			this.isDone = true;
			this.followUp(0);
			return;
		}

		if (this.attacker.hasTag(Tags.DRAGON)) {
			afx = AttackEffect.FIRE;
		}
		if (!duelist.player() && duelist.getEnemy() != null) {
			this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.duelist.creature(), this.baseDamage, dmgType), afx));
			this.isDone = true;
			this.followUp(1);
			return;
		}

		ArrayList<AbstractCreature> allEnemies = new ArrayList<>();
		ArrayList<AbstractCreature> nonTargeted = new ArrayList<>();
		HashMap<AbstractCreature, Integer> positionMap = new HashMap<>();
		int counter = 0;
		for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
			positionMap.put(m, counter);
			counter++;
			if (m != null && !m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead) {
				allEnemies.add(m);
			}
		}

		if (amountOfEnemiesToAttack >= allEnemies.size()) {
			for (AbstractCreature m : allEnemies) {
				int dmg = getDmgForMultiEnemyAttack(this.baseDamage, m, positionMap);
				this.addToBot(new DamageAction(m, new DamageInfo(this.duelist.creature(), dmg, dmgType), afx));
			}
        } else {
			while (allEnemies.size() > amountOfEnemiesToAttack) {
				int index = AbstractDungeon.cardRandomRng.random(allEnemies.size() - 1);
				nonTargeted.add(allEnemies.get(index));
				allEnemies.remove(index);
			}

			for (AbstractCreature m : allEnemies) {
				int dmg = getDmgForMultiEnemyAttack(this.baseDamage, m, positionMap);
				this.addToBot(new DamageAction(m, new DamageInfo(this.duelist.creature(), dmg, dmgType), afx));
			}

			if (nonTargeted.size() > 0) {
				if (this.baseDamage == 0) {
					nonTargeted.addAll(allEnemies);
				}
				this.attacker.stampedeHandler(nonTargeted);
				this.attacker.recklessHandler(nonTargeted);
			}
        }
        this.isDone = true;
		this.followUp(allEnemies.size());
    }

	private int getDmgForMultiEnemyAttack(int damage, AbstractCreature target, HashMap<AbstractCreature, Integer> positionMap) {
		int dmg = damage;
		if (this.attacker.multiDamage != null && this.attacker.multiDamage.length > 0) {
			Integer enemyPosition = positionMap.getOrDefault(target, null);
			if (enemyPosition != null && this.attacker.multiDamage.length > enemyPosition) {
				dmg = this.attacker.multiDamage[enemyPosition];
			}
		}
		return dmg;
	}

	private void followUp(Integer enemiesAttacked) {
		if (this.followUpAction != null) {
			this.followUpAction.accept(enemiesAttacked);
		}
	}
}
