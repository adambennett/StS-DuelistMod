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

public class EnemyWrathStanceChangeParticle extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float delayTimer;

    public EnemyWrathStanceChangeParticle(final float playerX) {
        this.img = ImageMaster.STRIKE_LINE;
        this.startingDuration = 1.0f;
        this.duration = this.startingDuration;
        this.color = new Color(1.0f, MathUtils.random(0.1f, 0.3f), 0.1f, 0.0f);
        this.x = MathUtils.random(-30.0f, 30.0f) * Settings.scale - this.img.packedWidth / 2.0f;
        this.y = Settings.HEIGHT / 2.0f + MathUtils.random(-150.0f, 150.0f) * Settings.scale - this.img.packedHeight / 2.0f;
        this.scale = MathUtils.random(2.2f, 2.5f) * Settings.scale;
        this.delayTimer = MathUtils.random(0.5f);
        this.rotation = MathUtils.random(89.0f, 91.0f);
        this.renderBehind = MathUtils.randomBoolean(0.9f);
    }

    public void update() {
        if (this.delayTimer > 0.0f) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        }
        else {
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0f) {
                this.isDone = true;
            }
            else if (this.duration > this.startingDuration / 2.0f) {
                this.color.a = Interpolation.pow3In.apply(0.6f, 0.0f, (this.duration - this.startingDuration / 2.0f) / (this.startingDuration / 2.0f));
            }
            else {
                this.color.a = Interpolation.fade.apply(0.0f, 0.6f, this.duration / (this.startingDuration / 2.0f));
            }
        }
    }

    public void render(final SpriteBatch sb) {
        if (this.delayTimer <= 0.0f) {
            sb.setColor(this.color);
            if (AbstractEnemyDuelist.enemyDuelist != null) {
                sb.setBlendFunction(770, 1);
                sb.draw((TextureRegion)this.img, AbstractEnemyDuelist.enemyDuelist.hb.cX + this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(2.9f, 3.1f), this.scale * MathUtils.random(0.95f, 1.05f), this.rotation);
                sb.setBlendFunction(770, 771);
            }
        }
    }

    public void dispose() {
    }
}
