package duelistmod.speedster.SpeedsterVFX.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import duelistmod.DuelistMod;
import duelistmod.ui.TextureLoader;

//Thanks Blank :)
public class ButtonConfirmedEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 0.75f;
    private float scale, scaleMult;
    private Color color;
    private Texture texture;
    private float x, y;

    public ButtonConfirmedEffect(float x, float y, Color color, float duration, float scaleMult) {
        this.scale = Settings.scale;
        this.color = color.cpy();
        this.texture = TextureLoader.getTexture(DuelistMod.makeSpeedsterPath("buttonDoneConfirmation.png"));
        this.x = x;
        this.y = y;
        this.startingDuration = this.duration = duration;
        this.scaleMult = scaleMult;
    }

    public ButtonConfirmedEffect(float x, float y, Color color) {
        this(x, y, color, EFFECT_DUR, 4);
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.scale *= 1.0f + Gdx.graphics.getDeltaTime() * scaleMult;
        this.color.a = Interpolation.fade.apply(0.0f, 0.75f, this.duration / startingDuration);
        if (this.color.a < 0.0f)
            this.color.a = 0.0f;
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 771);
        sb.draw(this.texture, x, y, 64f, 64f, 128f, 128f, scale, scale, 0.0f, 0, 0, 128, 128, false, false);
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
