package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class WildNatureActionB extends AbstractGameAction {
	private final float startingDuration;

	public WildNatureActionB() {
		this.actionType = ActionType.WAIT;
		this.attackEffect = AttackEffect.POISON;
		this.startingDuration = Settings.ACTION_DUR_FAST;
		this.duration = this.startingDuration;
	}

	@Override
	public void update() {
		if (this.duration == this.startingDuration) 
		{
			AbstractPlayer p = AbstractDungeon.player;
			int nats = 0;
			for (AbstractCard c : p.drawPile.group) {
				if (c.hasTag(Tags.NATURIA)) {
					nats++;
				}
			}

			List<Integer> damages = new ArrayList<>();
			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead) {
					damages.add(nats);
				}
			}
			this.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, damages.stream().mapToInt(i -> i).toArray(), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
		}
		this.tickDuration();
	}
}
