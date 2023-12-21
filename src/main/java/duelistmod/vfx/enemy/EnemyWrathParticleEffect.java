package duelistmod.vfx.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyWrathParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private final float dur_div2;
    private final TextureAtlas.AtlasRegion img;

    public EnemyWrathParticleEffect() {
        this.img = ImageMaster.GLOW_SPARK;
        this.duration = MathUtils.random(1.3f, 1.8f);
        this.scale = MathUtils.random(0.6f, 1.0f) * Settings.scale;
        this.dur_div2 = this.duration / 2.0f;
        this.color = new Color(MathUtils.random(0.5f, 1.0f), 0.0f, MathUtils.random(0.0f, 0.2f), 0.0f);
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            this.x = AbstractEnemyDuelist.enemyDuelist.hb.cX + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.width / 2.0f - 30.0f * Settings.scale, AbstractEnemyDuelist.enemyDuelist.hb.width / 2.0f + 30.0f * Settings.scale);
            this.y = AbstractEnemyDuelist.enemyDuelist.hb.cY + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.height / 2.0f - -10.0f * Settings.scale, AbstractEnemyDuelist.enemyDuelist.hb.height / 2.0f - 10.0f * Settings.scale);
        }
        this.x -= this.img.packedWidth / 2.0f;
        this.y -= this.img.packedHeight / 2.0f;
        this.renderBehind = MathUtils.randomBoolean(0.2f + (this.scale - 0.5f));
        this.rotation = MathUtils.random(-8.0f, 8.0f);
    }

    public void update() {
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - this.dur_div2) / this.dur_div2);
        }
        else {
            this.color.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / this.dur_div2);
        }
        this.vY += Gdx.graphics.getDeltaTime() * 40.0f * Settings.scale;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            sb.setBlendFunction(770, 1);
            sb.draw((TextureRegion)this.img, this.x, this.y + this.vY, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 0.8f, (0.1f + (this.dur_div2 * 2.0f - this.duration) * 2.0f * this.scale) * Settings.scale, this.rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }
}

