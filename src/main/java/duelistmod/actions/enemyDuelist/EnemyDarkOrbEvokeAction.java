package duelistmod.actions.enemyDuelist;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class EnemyDarkOrbEvokeAction extends AbstractGameAction {
    private final DamageInfo info;
    private final boolean muteSfx;

    public EnemyDarkOrbEvokeAction(final DamageInfo info, final AbstractGameAction.AttackEffect effect) {
        this.muteSfx = false;
        this.setValues(AbstractDungeon.player, this.info = info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1f;
    }

    public void update() {
        if ((this.shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS) || this.target == null) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.1f) {
            this.info.output = AbstractOrb.applyLockOn(this.target, this.info.base);
            if (this.info.type != DamageInfo.DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
        }
        this.tickDuration();
        if (this.isDone) {
            if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
                this.target.tint.color = Color.RED.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            this.target.damage(this.info);
            if (!Settings.FAST_MODE) {
                this.addToTop((AbstractGameAction)new WaitAction(0.1f));
            }
        }
    }
}

