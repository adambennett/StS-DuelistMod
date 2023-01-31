package duelistmod.vfx.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyCalmParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vX;
    private float vY;
    private final float dur_div2;
    private final float dvy;
    private final float dvx;

    public EnemyCalmParticleEffect() {
        this.duration = MathUtils.random(0.6f, 1.0f);
        this.scale = MathUtils.random(0.6f, 1.2f) * Settings.scale;
        this.dur_div2 = this.duration / 2.0f;
        this.color = new Color(MathUtils.random(0.2f, 0.3f), MathUtils.random(0.65f, 0.8f), 1.0f, 0.0f);
        this.vX = MathUtils.random(-300.0f, -50.0f) * Settings.scale;
        this.vY = MathUtils.random(-200.0f, -100.0f) * Settings.scale;
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            this.x = AbstractEnemyDuelist.enemyDuelist.hb.cX + MathUtils.random(100.0f, 160.0f) * Settings.scale - 32.0f;
            this.y = AbstractEnemyDuelist.enemyDuelist.hb.cY + MathUtils.random(-50.0f, 220.0f) * Settings.scale - 32.0f;
        }
        this.renderBehind = MathUtils.randomBoolean(0.2f + (this.scale - 0.5f));
        this.dvx = 400.0f * Settings.scale * this.scale;
        this.dvy = 100.0f * Settings.scale;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.vY += Gdx.graphics.getDeltaTime() * this.dvy;
        this.vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        this.rotation = -(57.295776f * MathUtils.atan2(this.vX, this.vY)) - 0.0f;
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - this.dur_div2) / this.dur_div2);
        }
        else {
            this.color.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / this.dur_div2);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.FROST_ACTIVATE_VFX_1, this.x, this.y, 32.0f, 32.0f, 25.0f, 128.0f, this.scale, this.scale + (this.dur_div2 * 0.4f - this.duration) * Settings.scale, this.rotation, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }
}
