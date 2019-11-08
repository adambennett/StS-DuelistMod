package duelistmod.speedster.SpeedsterVFX.general;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import duelistmod.speedster.SpeedsterUtil.UC;

public class TextEffect extends AbstractGameEffect {
    public String msg;

    public TextEffect(Color col, String msg, float duration) {
        this.startingDuration = this.duration = duration;
        this.msg = msg;
        this.color = col.cpy();
    }

    public void update() {
        super.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone)
            UC.displayTimer(sb, msg, Settings.HEIGHT - (180.0f * Settings.scale), color);
    }

    public void dispose() {}
}