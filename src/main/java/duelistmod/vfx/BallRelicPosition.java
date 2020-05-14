package duelistmod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BallRelicPosition {
    public float x;
    public float y;

    public float rotation;

    public BallRelicPosition(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public BallRelicPosition(float x, float y) {
        this(x, y, 0);
    }

    public void render(SpriteBatch sb, AbstractRelic ar) {
        sb.draw(ar.img, this.x - 64.0F, this.y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.rotation, 0, 0, 128, 128, false, false);
    }
}