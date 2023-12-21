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

public class EnemyDivinityParticleEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private final float dur_div2;
    private TextureAtlas.AtlasRegion img;

    public EnemyDivinityParticleEffect() {
        this.scale = Settings.scale;
        this.img = ImageMaster.EYE_ANIM_0;
        this.scale = MathUtils.random(1.0f, 1.5f);
        this.startingDuration = this.scale + 0.8f;
        this.duration = this.startingDuration;
        this.scale *= Settings.scale;
        this.dur_div2 = this.duration / 2.0f;
        this.color = new Color(MathUtils.random(0.8f, 1.0f), MathUtils.random(0.5f, 0.7f), MathUtils.random(0.8f, 1.0f), 0.0f);
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            this.x = AbstractEnemyDuelist.enemyDuelist.hb.cX + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.width / 2.0f - 50.0f * Settings.scale, AbstractEnemyDuelist.enemyDuelist.hb.width / 2.0f + 50.0f * Settings.scale);
            this.y = AbstractEnemyDuelist.enemyDuelist.hb.cY + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.height / 2.0f + 10.0f * Settings.scale, AbstractEnemyDuelist.enemyDuelist.hb.height / 2.0f - 20.0f * Settings.scale);
        }
        this.renderBehind = MathUtils.randomBoolean();
        this.rotation = MathUtils.random(12.0f, 6.0f);
        if (AbstractEnemyDuelist.enemyDuelist != null && this.x > AbstractEnemyDuelist.enemyDuelist.hb.cX) {
            this.rotation = -this.rotation;
        }
        this.x -= this.img.packedWidth / 2.0f;
        this.y -= this.img.packedHeight / 2.0f;
    }

    public void update() {
        if (this.duration > this.dur_div2) {
            this.color.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - this.dur_div2) / this.dur_div2);
        }
        else {
            this.color.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / this.dur_div2);
        }
        if (this.duration > this.startingDuration * 0.85f) {
            this.vY = 12.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_0;
        }
        else if (this.duration > this.startingDuration * 0.8f) {
            this.vY = 8.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_1;
        }
        else if (this.duration > this.startingDuration * 0.75f) {
            this.vY = 4.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_2;
        }
        else if (this.duration > this.startingDuration * 0.7f) {
            this.vY = 3.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_3;
        }
        else if (this.duration > this.startingDuration * 0.65f) {
            this.img = ImageMaster.EYE_ANIM_4;
        }
        else if (this.duration > this.startingDuration * 0.6f) {
            this.img = ImageMaster.EYE_ANIM_5;
        }
        else if (this.duration > this.startingDuration * 0.55f) {
            this.img = ImageMaster.EYE_ANIM_6;
        }
        else if (this.duration > this.startingDuration * 0.38f) {
            this.img = ImageMaster.EYE_ANIM_5;
        }
        else if (this.duration > this.startingDuration * 0.3f) {
            this.img = ImageMaster.EYE_ANIM_4;
        }
        else if (this.duration > this.startingDuration * 0.25f) {
            this.vY = 3.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_3;
        }
        else if (this.duration > this.startingDuration * 0.2f) {
            this.vY = 4.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_2;
        }
        else if (this.duration > this.startingDuration * 0.15f) {
            this.vY = 8.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_1;
        }
        else {
            this.vY = 12.0f * Settings.scale;
            this.img = ImageMaster.EYE_ANIM_0;
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
            sb.draw((TextureRegion)this.img, this.x, this.y + this.vY, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }
}
