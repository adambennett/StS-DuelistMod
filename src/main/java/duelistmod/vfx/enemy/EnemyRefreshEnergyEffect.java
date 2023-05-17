package duelistmod.vfx.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class EnemyRefreshEnergyEffect extends AbstractGameEffect {
    private float scale;
    private final Color color;
    private final TextureAtlas.AtlasRegion img;
    private final float x;
    private final float y;

    public EnemyRefreshEnergyEffect() {
        this.scale = Settings.scale / 1.2f;
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.img = ImageMaster.WHITE_RING;
        this.x = 1550.0f * Settings.scale + this.img.packedWidth / 2.0f;
        this.y = 720.0f * Settings.scale + this.img.packedHeight / 2.0f;
        this.duration = 0.4f;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.scale *= 1.0f + Gdx.graphics.getDeltaTime() * 2.5f;
        this.color.a = Interpolation.fade.apply(0.0f, 0.75f, this.duration / 0.4f);
        if (this.color.a < 0.0f) {
            this.color.a = 0.0f;
        }
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw((TextureRegion)this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * 1.5f, this.scale * 1.5f, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
