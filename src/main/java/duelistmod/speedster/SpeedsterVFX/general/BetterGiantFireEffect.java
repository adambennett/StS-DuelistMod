package duelistmod.speedster.SpeedsterVFX.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BetterGiantFireEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float brightness;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float startingDuration;
    private boolean flipX;
    private float delayTimer;

    public BetterGiantFireEffect(float startingDuration) {
        this.flipX = MathUtils.randomBoolean();
        this.delayTimer = MathUtils.random(0.1F);
        setImg();
        this.startingDuration = startingDuration;
        this.duration = this.startingDuration;
        this.x = MathUtils.random(0.0F, Settings.WIDTH) - this.img.packedWidth / 2.0F;
        this.y = MathUtils.random(-200.0F, -400.0F) * Settings.scale - this.img.packedHeight / 2.0F;
        this.vX = MathUtils.random(-70.0F, 70.0F) * Settings.scale;
        this.vY = MathUtils.random(500.0F, 1700.0F) * Settings.scale;
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.color.g -= MathUtils.random(0.5F);
        this.color.b -= this.color.g - MathUtils.random(0.0F, 0.2F);
        this.rotation = MathUtils.random(-10.0F, 10.0F);
        this.scale = MathUtils.random(0.5F, 7.0F);
        this.brightness = MathUtils.random(0.2F, 0.6F);
    }

    public void update() {
        if (this.delayTimer > 0.0F) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.scale *= MathUtils.random(0.95F, 1.05F);
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.startingDuration - this.duration < 0.75F) {
            this.color.a = Interpolation.fade.apply(0.0F, this.brightness, (this.startingDuration - this.duration) / 0.75F);
        } else if (this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(0.0F, this.brightness, this.duration / 1.0F);
        }
    }

    private void setImg() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            this.img = ImageMaster.FLAME_1;
        } else if (roll == 1) {
            this.img = ImageMaster.FLAME_2;
        } else {
            this.img = ImageMaster.FLAME_3;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.flipX && !this.img.isFlipX()) {
            this.img.flip(true, false);
        } else if (!this.flipX && this.img.isFlipX()) {
            this.img.flip(true, false);
        }
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale * Settings.scale, this.scale * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}
