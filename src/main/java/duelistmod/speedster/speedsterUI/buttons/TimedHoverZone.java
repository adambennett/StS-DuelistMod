package duelistmod.speedster.speedsterUI.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.general.ButtonConfirmedEffect;

public class TimedHoverZone extends TimedButton {
    public boolean inZone;
    public Runnable clickEffect;
    private static Color rightCol = Color.CYAN.cpy(), wrongCol = Color.FIREBRICK.cpy();

    public TimedHoverZone(float x, float y, float timer, Runnable clickEffect, boolean ordered) {
        super(ImageMaster.MAP_CIRCLE_5, x, y, timer, null, ordered);
        this.clickEffect = clickEffect;
        inZone = false;
        setClickable(false);
    }

    @Override
    protected void onHover() {
        super.onHover();
        inZone = true;
    }

    @Override
    protected void onUnhover() {
        super.onUnhover();
        inZone = false;
    }

    @Override
    public void update(int order) {
        this.order = order;
        super.update();

        duration -= UC.gt();
        if(duration < 0) {
            if(inZone) {
                UC.doVfx(new ButtonConfirmedEffect(x, y, Color.WHITE));
                clickEffect.run();
            }
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch sb, Color col) {
        if(ordered) {
            if(order != 0) {
                col = wrongCol;
            } else {
                col = rightCol;
            }
        }

        super.render(sb, col);
    }

    @Override
    protected void onClick() {
    }

    @Override
    public void renderParticles(SpriteBatch sb) {
    }
}
