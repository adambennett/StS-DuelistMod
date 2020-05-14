package duelistmod.speedster.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DamageAllAction extends AbstractGameAction {
    public int damage;
    private boolean firstFrame;
    private boolean isPureDamage;

    public DamageAllAction(AbstractCreature source, int baseDamage, boolean isPureDamage, DamageInfo.DamageType type, AttackEffect effect, boolean isFast) {
        this.firstFrame = true;
        this.setValues(null, source, baseDamage);
        this.damage = baseDamage;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.isPureDamage = isPureDamage;
        if (isFast) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FAST;
        }
    }

    private int[] createDamageMatrix(int baseDamage, boolean isPureDamage) {
        int[] retVal = new int[AbstractDungeon.getMonsters().monsters.size()];
        for (int i = 0; i < retVal.length; i++) {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, baseDamage);

            if (!isPureDamage) {
                info.applyEnemyPowersOnly(AbstractDungeon.getMonsters().monsters.get(i));
            }

            retVal[i] = info.output;
        }
        return retVal;
    }

    public void update() {
        if (this.firstFrame) {
            boolean playedMusic = false;

            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if(!m.isDeadOrEscaped()) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, this.attackEffect));
                    }
                }
            }

            this.firstFrame = false;
        }

        this.tickDuration();
        if (this.isDone) {
            int[] damageMatrix = createDamageMatrix(this.damage, this.isPureDamage);
            AbstractDungeon.player.powers.forEach(power -> power.onDamageAllEnemies(damageMatrix));

            int i = 0;
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped()) {
                    if (this.attackEffect == AttackEffect.POISON) {
                        m.tint.color = Color.CHARTREUSE.cpy();
                        m.tint.changeColor(Color.WHITE.cpy());
                    } else if (this.attackEffect == AttackEffect.FIRE) {
                        m.tint.color = Color.RED.cpy();
                        m.tint.changeColor(Color.WHITE.cpy());
                    }

                    m.damage(new DamageInfo(this.source, damageMatrix[i], this.damageType));
                }
                i++;
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }
        }

    }
}
