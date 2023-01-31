package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyLightningOrbEvokeAction extends AbstractGameAction
{
    private final DamageInfo info;
    private boolean hitAll;

    public EnemyLightningOrbEvokeAction(final DamageInfo info, final boolean hitAll) {
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AbstractGameAction.AttackEffect.NONE;
        this.hitAll = hitAll;
    }

    public void update() {
        final AbstractCreature t =AbstractDungeon.player;
        float speedTime = 0.1f;
        if (!AbstractEnemyDuelist.enemyDuelist.orbs.isEmpty()) {
            speedTime = 0.2f / AbstractEnemyDuelist.enemyDuelist.orbs.size();
        }
        if (Settings.FAST_MODE) {
            speedTime = 0.0f;
        }
        this.info.output = AbstractOrb.applyLockOn(t, this.info.base);
        this.addToTop(new DamageAction(t, this.info, AbstractGameAction.AttackEffect.NONE, true));
        this.addToTop(new VFXAction(new LightningEffect(t.drawX, t.drawY), speedTime));
        this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
        this.isDone = true;
    }
}
