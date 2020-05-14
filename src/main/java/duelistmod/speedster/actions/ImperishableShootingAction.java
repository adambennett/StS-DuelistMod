package duelistmod.speedster.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.speedster.SpeedsterVFX.combat.unique.ImperishableShootingEffect;

public class ImperishableShootingAction extends AbstractGameAction {
    private DamageInfo info;

    private float x;
    private float y;
    private int counter = 0;

    private static final float MAX_DURATION = 10.0f;

    private ArrayList<ImperishableShootingEffect> missileEffects = new ArrayList<>();

    public ImperishableShootingAction(float x, float y, int amount, DamageInfo info) {
        this.info = info;
        setValues(null, info.owner, amount);
        this.actionType = ActionType.DAMAGE;
        this.duration = MAX_DURATION;
        this.x = x;
        this.y = y;
    }

    public ImperishableShootingAction(float x, float y, AbstractCreature target, int amount, DamageInfo info) {
        this.info = info;
        setValues(target, info.owner, amount);
        this.actionType = ActionType.DAMAGE;
        this.duration = MAX_DURATION;
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (this.amount-- > 0) {
            if (target == null) {
                AbstractCreature t = AbstractDungeon.getRandomMonster();
                if (t != null) {
                    if(counter%3 == 0) {
                        CardCrawlGame.sound.play("CARD_EXHAUST", 0.1F);
                    }
                    counter++;
                    ImperishableShootingEffect e = new ImperishableShootingEffect(x, y, t, info);
                    AbstractDungeon.effectsQueue.add(e);
                    missileEffects.add(e);
                }
            } else {
                if (!target.isDeadOrEscaped()) {
                    if(counter%3 == 0) {
                        CardCrawlGame.sound.play("CARD_EXHAUST", 0.1F);
                    }
                    counter++;
                    ImperishableShootingEffect e = new ImperishableShootingEffect(x, y, target, info);
                    AbstractDungeon.effectsQueue.add(e);
                    missileEffects.add(e);
                }
            }
        } else {
            missileEffects.removeIf((e) -> {
                if (e.isDone && e.target != null) {
                    e.target.damage(e.dmg);
                }
                return e.isDone;
            });
            if (missileEffects.size() == 0) {
                this.isDone = true;

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
            this.tickDuration(); //For backup.
        }
    }
}