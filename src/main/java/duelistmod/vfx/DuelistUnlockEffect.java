package duelistmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DuelistUnlockEffect extends AbstractGameEffect {

    private static final float START_Y;
    private float y;
    private static final float X;
    private static final float TARGET_Y;
    private final String label;

    public DuelistUnlockEffect(String label) {
        CardCrawlGame.sound.play("UNLOCK_PING");
        this.duration = 3.0F;
        this.startingDuration = 3.0F;
        this.y = START_Y;
        this.color = Settings.BLUE_TEXT_COLOR.cpy();
        this.label = "New Unlock: " + label + "!";
    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.duration = 0.0F;
        }

        if (this.duration > 2.5F) {
            this.y = Interpolation.elasticIn.apply(TARGET_Y, START_Y, (this.duration - 2.5F) * 2.0F);
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, (this.duration - 2.5F) * 2.0F);
        } else if (this.duration < 0.5F) {
            this.color.a = Interpolation.pow2In.apply(0.0F, 1.0F, this.duration * 2.0F);
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.label, X, this.y, this.color);
    }

    @Override
    public void dispose() {
    }

    static {
        X = 1350.0F * Settings.scale;
        START_Y = (float)Settings.HEIGHT / 2.0F - 440.0F * Settings.scale;
        TARGET_Y = (float)Settings.HEIGHT / 2.0F - 270.0F * Settings.scale;
    }
}
