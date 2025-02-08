package duelistmod.actions.common;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import duelistmod.helpers.Util;
import duelistmod.powers.SummonPower;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MegalosmasherDeathCheckActionPlayer extends AbstractGameAction {
    public int[] damage;
    private int baseDamage;
    private boolean firstFrame;
    private boolean utilizeBaseDamage;
    private final boolean upgraded;

    public MegalosmasherDeathCheckActionPlayer(AbstractCreature source, int[] dmgArray, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect, boolean upgraded) {
        this.firstFrame = true;
        this.utilizeBaseDamage = false;
        this.source = source;
        this.damage = dmgArray;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {

        if (this.firstFrame) {
            boolean playedMusic = false;
            final int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            if (this.utilizeBaseDamage) {
                this.damage = DamageInfo.createDamageMatrix(this.baseDamage);
            }
            for (int i = 0; i < temp; ++i) {
                if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDying && AbstractDungeon.getCurrRoom().monsters.monsters.get(i).currentHealth > 0 && !AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect, true));
                    }
                    else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect));
                    }
                }
            }
            this.firstFrame = false;
        }
        this.tickDuration();
        if (this.isDone) {
            for (final AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }
            ArrayList<AbstractMonster> damaged = new ArrayList<>();
            ArrayList<AbstractMonster> damagedAndAlive = new ArrayList<>();
            for (int temp2 = AbstractDungeon.getCurrRoom().monsters.monsters.size(), j = 0; j < temp2; ++j) {
                if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(j).isDeadOrEscaped()) {
                    if (this.attackEffect == AttackEffect.POISON) {
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.color.set(Color.CHARTREUSE);
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.changeColor(Color.WHITE.cpy());
                    }
                    else if (this.attackEffect == AttackEffect.FIRE) {
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.color.set(Color.RED);
                        AbstractDungeon.getCurrRoom().monsters.monsters.get(j).tint.changeColor(Color.WHITE.cpy());
                    }
                    DamageInfo info = new DamageInfo(this.source, this.damage[j], this.damageType);
                    AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(j);
                    if (this.damage[j] > target.currentBlock) {
                        damaged.add(target);
                    }
                    target.damage(info);
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                for (AbstractMonster targ : damaged) {
                    if (!targ.isDying && !targ.isDead && !targ.isDeadOrEscaped() && !targ.halfDead) {
                        damagedAndAlive.add(targ);
                    }
                }
            }
            for (AbstractMonster m : damagedAndAlive) {
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
        return this.upgraded && roll < 75 || roll < 65;
    }

    private ArrayList<AbstractPower> randomBuffRemoval(ArrayList<AbstractPower> powers) {
        List<AbstractPower> buffs = powers.stream().filter(p -> p.type == AbstractPower.PowerType.BUFF && !p.ID.equals(SummonPower.POWER_ID) && !(p instanceof ShiftingPower) && !(p instanceof FadingPower)).collect(Collectors.toList());
        if (buffs.isEmpty()) {
            return powers;
        }

        ArrayList<AbstractPower> output = powers.stream().filter(p -> p.type != AbstractPower.PowerType.BUFF && !p.ID.equals(SummonPower.POWER_ID) && !(p instanceof ShiftingPower) && !(p instanceof FadingPower)).collect(Collectors.toCollection(ArrayList::new));
        if (buffs.size() > 1) {
            int removeIndex = AbstractDungeon.cardRandomRng.random(0, buffs.size() - 1);
            try {
                buffs.remove(removeIndex);
            } catch (Exception ex) {
                Util.log("Error removing buff from list for Megalosmasher\n" + ExceptionUtils.getStackTrace(ex));
            }
            output.addAll(buffs);
        }
        return output;
    }
}
