package duelistmod.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import duelistmod.helpers.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MegalosmasherDeathCheckActionEnemy extends AbstractGameAction {

    private final int baseDamage;
    private boolean firstFrame;
    private final boolean upgraded;

    public MegalosmasherDeathCheckActionEnemy(AbstractCreature source, int dmg, DamageInfo.DamageType type, AttackEffect effect, boolean upgraded) {
        this.firstFrame = true;
        this.source = source;
        this.baseDamage = dmg;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {

        AbstractCreature target = AbstractDungeon.player;
        if (this.firstFrame) {
            if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, this.attackEffect));
            }
            this.firstFrame = false;
        }
        this.tickDuration();
        if (this.isDone) {
            ArrayList<AbstractCreature> damaged = new ArrayList<>();
            ArrayList<AbstractCreature> damagedAndAlive = new ArrayList<>();
            if (!target.isDeadOrEscaped()) {
                if (this.attackEffect == AttackEffect.POISON) {
                    target.tint.color.set(Color.CHARTREUSE);
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                else if (this.attackEffect == AttackEffect.FIRE) {
                    target.tint.color.set(Color.RED);
                    target.tint.changeColor(Color.WHITE.cpy());
                }
                DamageInfo info = new DamageInfo(this.source, this.baseDamage, this.damageType);
                if (this.baseDamage > target.currentBlock) {
                    damaged.add(target);
                }
                target.damage(info);
            }

            for (AbstractCreature targ : damaged) {
                if (!targ.isDying && !targ.isDead && !targ.isDeadOrEscaped() && !targ.halfDead) {
                    damagedAndAlive.add(targ);
                }
            }

            for (AbstractCreature m : damagedAndAlive) {
                if (removeBuffRoll()) {
                    m.powers = randomBuffRemoval(m.powers);
                }
            }
            if (!Settings.FAST_MODE) {
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }

    private boolean removeBuffRoll() {
        int roll = AbstractDungeon.cardRandomRng.random(0, 100);
        return this.upgraded && roll > 75 || roll > 85;
    }

    private ArrayList<AbstractPower> randomBuffRemoval(ArrayList<AbstractPower> powers) {
        List<AbstractPower> buffs = powers.stream().filter(p -> p.type == AbstractPower.PowerType.BUFF).collect(Collectors.toList());
        if (buffs.isEmpty()) {
            return powers;
        }

        ArrayList<AbstractPower> output = powers.stream().filter(p -> p.type != AbstractPower.PowerType.BUFF).collect(Collectors.toCollection(ArrayList::new));
        if (buffs.size() > 1) {
            int removeIndex = AbstractDungeon.cardRandomRng.random(0, buffs.size());
            try {
                buffs.remove(removeIndex);
            } catch (Exception ex) {
                Util.log("Error removing buff from list for Megalosmasher (ENEMY): " + ex.getMessage());
                ex.printStackTrace();
            }
            output.addAll(buffs);
        }
        return output;
    }
}
