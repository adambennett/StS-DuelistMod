package duelistmod.speedster.SpeedsterVFX.combat.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.combat.FireIgniteEffect;
import duelistmod.speedster.SpeedsterVFX.general.RedFireBurstParticleEffect;

public class ImperishableShootingEffect extends AbstractGameEffect {
    private static final float FREE_TIME = Settings.FAST_MODE ?1.5f:2.0f;

    private static final float LOCK_ON_TIME = 0.4f;
    private static final float FAST_LOCK_ON_TIME = 0.2f;
    private static final float FLAME_EFFECT_TIMER = 0.03f;
    private static final float LOCKEDON_FLAME_EFFECT_TIMER = 0.005f;

    private float x;
    private float y;
    private float sX;
    private float sY;
    private float vX;
    private float vY;
    private float aX;
    private float aY;
    private float tX;
    private float tY;

    public AbstractCreature target;
    public DamageInfo dmg;

    private float freeDuration;
    private boolean lockedOn;
    private boolean finalApproach;
    private float vfxTimer;

    public ImperishableShootingEffect(float x, float y, AbstractCreature target, DamageInfo info) {
        //start position
        this.x = x;
        this.y = y;

        float mult = Settings.FAST_MODE ? 2.0f : 1.0f;

        this.vX = MathUtils.random(0 * Settings.scale, 300 * Settings.scale) * (MathUtils.randomBoolean() ? 1 : -1) * mult;
        this.vY = MathUtils.random(300 * Settings.scale, 600 * Settings.scale) * mult;

        this.aX = vX * MathUtils.random(0.2f, 0.8f);
        this.aY = vY * MathUtils.random(0.2f, 0.8f);

        freeDuration = FREE_TIME / mult;
        duration = freeDuration;
        lockedOn = false;
        finalApproach = false;

        this.target = target;
        this.dmg = info;
        this.scale = 0.2f;
        vfxTimer = 0;
    }

    @Override
    public void update() {
        if (target.isDeadOrEscaped()) {
            if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() && duration > 0.0f) {
                target = AbstractDungeon.getRandomMonster();
            } else {
                target = null;
            }

            if (target == null) {
                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(x, y, AbstractGameAction.AttackEffect.FIRE, false));

                this.isDone = true;
                return;
            } else //new target.
            {
                if (lockedOn) {
                    this.sX = this.x;
                    this.sY = this.y;
                    this.tX = target.hb.cX;
                    this.tY = this.y + Math.abs(tX - sX) * MathUtils.random(0.4f, 0.5f);

                    this.startingDuration = this.duration;
                    finalApproach = false;
                }
            }
        }

        if (lockedOn) {
            float lastX = x;
            float lastY = y;
            if (this.duration > 0) {
                this.x = Interpolation.pow3Out.apply(tX, sX, this.duration / this.startingDuration);
                if (this.duration > startingDuration / 2.0f) {
                    this.y = Interpolation.linear.apply(tY, sY, this.duration / this.startingDuration);
                } else {
                    if (!finalApproach) {
                        finalApproach = true;
                        this.sY = y;
                        this.tY = target.hb.cY;
                    }

                    this.y = Interpolation.pow3Out.apply(tY, sY, this.duration / (this.startingDuration / 2.0f));
                }
                this.duration -= Gdx.graphics.getDeltaTime();
            } else if (!this.isDone) {
                this.x = tX;
                this.y = tY;
                this.isDone = true;

                AbstractDungeon.effectsQueue.add(new FlashAtkImgEffect(tX, tY, AbstractGameAction.AttackEffect.FIRE, MathUtils.randomBoolean(0.33f)));
                this.target.tint.color = Color.RED.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
            }

            this.rotation = MathUtils.atan2(y - lastY, x - lastX) * 180.0f / MathUtils.PI;
        }

        if (freeDuration > 0) {
            this.x += vX * Gdx.graphics.getDeltaTime();
            if(MathUtils.randomBoolean()) {
                this.y += vY * Gdx.graphics.getDeltaTime();
            } else {
                this.y -= vY * Gdx.graphics.getDeltaTime();
            }

            this.rotation = MathUtils.atan2(vY, vX) * 180.0f / MathUtils.PI;

            this.vX += aX * Gdx.graphics.getDeltaTime();

            if(MathUtils.randomBoolean()) {
                this.vY += aY * Gdx.graphics.getDeltaTime();
            } else {
                this.vY -= aY * Gdx.graphics.getDeltaTime();
            }

            freeDuration -= Gdx.graphics.getDeltaTime();
        } else if (!lockedOn) {
            lockedOn = true;
            this.sX = this.x;
            this.sY = this.y;
            this.tX = target.hb.cX;
            this.tY = this.y + Math.abs(tX - sX) * MathUtils.random(0.4f, 0.5f);

            this.duration = this.startingDuration = Settings.FAST_MODE ? FAST_LOCK_ON_TIME : LOCK_ON_TIME;
        }

        if (!this.isDone) {
            if(vfxTimer<0) {
                AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, UC.getRandomFireColor()));
                AbstractDungeon.effectsQueue.add(new RedFireBurstParticleEffect(this.x, this.y));
                if(lockedOn) {
                    vfxTimer = LOCKEDON_FLAME_EFFECT_TIMER;
                } else {
                    vfxTimer = FLAME_EFFECT_TIMER;
                }
            }
            vfxTimer -= Gdx.graphics.getDeltaTime();
        } else {
            AbstractDungeon.effectsQueue.add(new FireIgniteEffect(this.x, this.y, 15));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
