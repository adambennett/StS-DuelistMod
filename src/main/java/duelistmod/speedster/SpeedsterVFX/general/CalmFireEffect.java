package duelistmod.speedster.SpeedsterVFX.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CalmFireEffect extends AbstractGameEffect {
    private Texture img = this.getImg();
    private float x;
    private float y;
    private float vX;
    private float vY;
    private boolean flippedX = MathUtils.randomBoolean();
    private static final float DUR = 0.7F;

    public CalmFireEffect(float x, float y, Color col) {
        this.x = x + MathUtils.random(-5.0F, 5.0F) * Settings.scale;
        this.y = y + MathUtils.random(-5.0F, 5.0F) * Settings.scale;
        this.vX = MathUtils.random(-30.0F, 30.0F) * Settings.scale;
        this.vY = MathUtils.random(20.0F, 60.0F) * Settings.scale;
        this.duration = DUR;
        this.color = col.cpy();
        this.color.a = 0.0F;
        this.scale = MathUtils.random(0.8F, 0.9F) * Settings.scale;
        this.renderBehind = MathUtils.randomBoolean();
    }

    public CalmFireEffect(float x, float y) {
        this(x, y, Color.ORANGE);
    }

    private Texture getImg() {
        return MathUtils.randomBoolean() ? ImageMaster.GHOST_ORB_1 : ImageMaster.GHOST_ORB_2;
    }

    public void update() {
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = this.duration / 2.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, this.scale * 1.2F, this.scale, 0.0F, 0, 0, 128, 128, this.flippedX, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}