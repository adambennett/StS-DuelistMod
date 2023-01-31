package duelistmod.vfx.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyStanceAuraEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private final float vY;
    private final TextureAtlas.AtlasRegion img;
    public static boolean switcher;

    public EnemyStanceAuraEffect(final String stanceId) {
        this.img = ImageMaster.EXHAUST_L;
        this.duration = 2.0f;
        this.scale = MathUtils.random(2.7f, 2.5f) * Settings.scale;
        if (stanceId.equals("Wrath")) {
            this.color = new Color(MathUtils.random(0.6f, 0.7f), MathUtils.random(0.0f, 0.1f), MathUtils.random(0.1f, 0.2f), 0.0f);
        }
        else if (stanceId.equals("Calm")) {
            this.color = new Color(MathUtils.random(0.5f, 0.55f), MathUtils.random(0.6f, 0.7f), 1.0f, 0.0f);
        }
        else {
            this.color = new Color(MathUtils.random(0.6f, 0.7f), MathUtils.random(0.0f, 0.1f), MathUtils.random(0.6f, 0.7f), 0.0f);
        }
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            this.x = AbstractEnemyDuelist.enemyDuelist.hb.cX + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.width / 16.0f, AbstractEnemyDuelist.enemyDuelist.hb.width / 16.0f);
            this.y = AbstractEnemyDuelist.enemyDuelist.hb.cY + MathUtils.random(-AbstractEnemyDuelist.enemyDuelist.hb.height / 16.0f, AbstractEnemyDuelist.enemyDuelist.hb.height / 12.0f);
        }
        this.x -= this.img.packedWidth / 2.0f;
        this.y -= this.img.packedHeight / 2.0f;
        EnemyStanceAuraEffect.switcher = !EnemyStanceAuraEffect.switcher;
        this.renderBehind = true;
        this.rotation = MathUtils.random(360.0f);
        if (EnemyStanceAuraEffect.switcher) {
            this.renderBehind = true;
            this.vY = MathUtils.random(0.0f, 40.0f);
        }
        else {
            this.renderBehind = false;
            this.vY = MathUtils.random(0.0f, -40.0f);
        }
    }

    public void update() {
        if (this.duration > 1.0f) {
            this.color.a = Interpolation.fade.apply(0.3f, 0.0f, this.duration - 1.0f);
        }
        else {
            this.color.a = Interpolation.fade.apply(0.0f, 0.3f, this.duration);
        }
        this.rotation += Gdx.graphics.getDeltaTime() * this.vY;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            sb.setBlendFunction(770, 1);
            sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }

    static {
        EnemyStanceAuraEffect.switcher = true;
    }
}

