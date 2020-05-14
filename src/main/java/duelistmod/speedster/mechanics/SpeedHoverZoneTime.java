package duelistmod.speedster.mechanics;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;

import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.general.ButtonConfirmedEffect;
import duelistmod.speedster.speedsterUI.buttons.*;

public class SpeedHoverZoneTime extends AbstractSpeedTime {
    private static Color col = Color.WHITE.cpy();
    protected ArrayList<TimedHoverZone> buttons;
    protected Runnable effectAction;
    protected boolean chainedHover, firstRun;
    protected int repeats, doneRepeats;

    protected static final float MAX_Y = Settings.HEIGHT - (200f * Settings.scale);
    protected static final float MIN_Y = (200f * Settings.scale);
    protected static final float MIN_X = Settings.WIDTH - (300f * Settings.scale);
    protected static final float MAX_X = (300f * Settings.scale);

    public SpeedHoverZoneTime(float duration, Runnable effect, boolean chainedHover, int repeats) {
        super(AbstractSpeedTime.Location.COMPLETE, duration);
        effectAction = effect;
        buttons = new ArrayList<>();
        this.chainedHover = chainedHover;
        this.repeats = repeats;
        doneRepeats = 0;
        firstRun = true;
    }

    public void triggerEffect() {
        effectAction.run();
    }

    @Override
    public void update() {
        super.update();
        if(chainedHover) {
            chainedHoverLogic();
        } else {
            singleHoverLogic();
        }

        int c = 0;
        for (TimedButton b : buttons) {
            b.update(b.ordered ? c++ : 0);
        }
        buttons.removeIf(b -> b.isDone);
    }

    //Since these segments are pretty simple I don't see a reason to code a generator here
    protected void singleHoverLogic() {
        if (buttons.isEmpty() && doneRepeats < repeats) {
            displaceCursor();
            float x = MathUtils.random(MIN_X, MAX_X);
            float y = MathUtils.random(MIN_Y, MAX_Y);
            TimedHoverZone toBeAdded = new TimedHoverZone(x, y, startingDuration/(float)repeats, this::triggerEffect, false);
            buttons.add(toBeAdded);
            doneRepeats++;
        }
    }

    protected void chainedHoverLogic() {
        if(firstRun) {
            while (doneRepeats < repeats) {
                //Better left unseeded or it'd be easy to cheese
                float x = MathUtils.random(MIN_X, MAX_X);
                float y = MathUtils.random(MIN_Y, MAX_Y);
                TimedHoverZone toBeAdded = new TimedHoverZone(x, y, (startingDuration/(float)repeats)*doneRepeats+1, this::triggerEffect, true);
                if (buttons.stream().noneMatch(tb -> toBeAdded.getHb().intersects(tb.getHb()))) {
                    buttons.add(toBeAdded);
                    doneRepeats++;
                }
            }
            firstRun = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        buttons.forEach(b -> {
            b.render(sb, col);
            col.a = 1.0f;
        });
    }

    private void displaceCursor() {
        int x = MathUtils.round(MathUtils.random(MIN_X, MAX_X)), y =MathUtils.round(MathUtils.random(MIN_Y, MAX_Y));
        Mouse.setCursorPosition(x, y);
        UC.doVfx(new ButtonConfirmedEffect(x, y, Color.GOLDENROD, 0.4f, 1.75f));
    }

    @Override
    protected void renderEffects(SpriteBatch sb) {
        //Thinking about bottom fog effect
    }
}