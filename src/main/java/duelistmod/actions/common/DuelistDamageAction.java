package duelistmod.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import duelistmod.helpers.Util;

public class DuelistDamageAction extends AbstractGameAction
{
	private final DamageInfo info;
	private int goldAmount;
	private final boolean skipWait;
	private final boolean muteSfx;
	private final boolean applyPowers;

	public DuelistDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, int goldAmount, boolean superFast, boolean muteSfx, boolean applyPowers) {
		this.info = info;
		setValues(target, info);
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.attackEffect = effect;
		this.duration = 0.1F;
		this.applyPowers = applyPowers;
		this.muteSfx = muteSfx;
		this.skipWait = superFast;
		this.goldAmount = goldAmount;
	}

	public DuelistDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect, boolean applyPowers) {
		this(target, info, effect, 0, false, false, applyPowers);
	}

	public void update() {
		if ((shouldCancelAction()) && (this.info.type != DamageInfo.DamageType.THORNS)) {
			this.isDone = true;
			return;
		}

		if (this.duration == 0.1F) {
			if ((this.info.type != DamageInfo.DamageType.THORNS) && ((this.info.owner.isDying) || (this.info.owner.halfDead))) {
				this.isDone = true;
				return;
			}

			AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
			stealGold();
		}

		tickDuration();

		if (this.isDone) {
			if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) {
				this.target.tint.color.set(Color.CHARTREUSE.cpy());
				this.target.tint.changeColor(Color.WHITE.cpy());
			} else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
				this.target.tint.color.set(Color.RED);
				this.target.tint.changeColor(Color.WHITE.cpy());
			}

			if (this.applyPowers) {
				this.info.applyPowers(AbstractDungeon.player, this.target);
			}

			Util.log(this.getClass().getName() + ": this.info.base=" + this.info.base);

			if (this.info.base > 0) {
				this.target.damage(this.info);
			}

			if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
				AbstractDungeon.actionManager.clearPostCombatActions();
			}

			if ((!this.skipWait) && (!Settings.FAST_MODE)) {
				AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
			}
		}
	}

	private void stealGold() {
		if (this.goldAmount <= 0 || this.target.gold <= 0){
			return;
		}

		CardCrawlGame.sound.play("GOLD_JINGLE");
		if (this.target.gold < this.goldAmount) {
			this.goldAmount = this.target.gold;
		}

		this.target.gold -= this.goldAmount;
		for (int i = 0; i < this.goldAmount; i++) {
			if (this.source.isPlayer) {
				AbstractDungeon.effectList.add(new GainPennyEffect(this.target.hb.cX, this.target.hb.cY));
			} else {
				AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, false));
			}
		}
	}
}
