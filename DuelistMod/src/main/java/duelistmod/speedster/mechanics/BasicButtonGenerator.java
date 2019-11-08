package duelistmod.speedster.mechanics;

import com.badlogic.gdx.math.MathUtils;

import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.speedsterUI.buttons.TimedButton;

public class BasicButtonGenerator extends AbstractButtonGenerator{

    public BasicButtonGenerator(float intensity, boolean ordered) {
        super(intensity, null, ordered);
    }

    public void logic() {
        timeSince -= UC.gt();
        if(timeSince < 0) {
            while(true) {
                //Better left unseeded or it'd be easy to cheese
                float x = MathUtils.random(MIN_X, MAX_X);
                float y = MathUtils.random(MIN_Y, MAX_Y);
                TimedButton toBeAdded = new TimedButton(x, y, intensity * 2f, clickEffect, ordered);
                if(buttons.stream().noneMatch(tb -> toBeAdded.getHb().intersects(tb.getHb()))) {
                    addButton(toBeAdded);
                    break;
                }
            }

            timeSince = intensity/2f;
        }
    }
}
