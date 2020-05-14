package duelistmod.speedster.SpeedsterVFX.combat.unique;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.combat.FireIgniteEffect;
import duelistmod.speedster.SpeedsterVFX.general.RedFireBurstParticleEffect;

public class FlameSlingEffect extends AbstractGameEffect {
    private static final float FIREBALL_INTERVAL = 0.016F;
    private float x;
    private float y;
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private float vfxTimer;

    public FlameSlingEffect(float startX, float startY, float targetX, float targetY) {
        this.vfxTimer = 0.0F;
        this.startingDuration = 0.5F;
        this.duration = 0.5F;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX + MathUtils.random(-20.0F, 20.0F) * Settings.scale;
        this.targetY = targetY + MathUtils.random(-20.0F, 20.0F) * Settings.scale;
        this.x = startX;
        this.y = startY;
    }

    public void update() {
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.duration / this.startingDuration);
        this.y = Interpolation.fade.apply(this.targetY, this.startY, this.duration / this.startingDuration);
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            this.vfxTimer = FIREBALL_INTERVAL;
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, UC.getRandomFireColor()));
            AbstractDungeon.effectsQueue.add(new RedFireBurstParticleEffect(this.x, this.y));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            AbstractDungeon.effectsQueue.add(new FireIgniteEffect(this.x, this.y, 20));
        }
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}

