package duelistmod.speedster.mechanics;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.megacrit.cardcrawl.core.Settings;

import duelistmod.speedster.speedsterUI.buttons.TimedButton;

public abstract class AbstractButtonGenerator {
    protected float intensity, timeSince;
    protected boolean ordered;
    protected int buttonsCreated;
    protected ArrayList<TimedButton> buttons;
    protected Consumer<TimedButton> clickEffect;
    protected AbstractSpeedTime instance;

    protected static final float MAX_Y = Settings.HEIGHT - (200f*Settings.scale);
    protected static final float MIN_Y = (200f*Settings.scale);
    protected static final float MIN_X = Settings.WIDTH - (300f*Settings.scale);
    protected static final float MAX_X = (300f*Settings.scale);

    public AbstractButtonGenerator(float intensity, Consumer<TimedButton> clickEffect, boolean ordered) {
        this.intensity = intensity; //Somewhere between 0.5 and 1
        this.clickEffect = clickEffect;
        if(this.clickEffect == null) {
            this.clickEffect = tb -> instance.triggerEffect();
        }
        this.ordered = ordered;
        timeSince = 0f;
    }

    public void setButtons(ArrayList<TimedButton> buttons) {
        this.buttons = buttons;
    }

    public void setInstance(AbstractSpeedTime instance) {
        this.instance = instance;
    }

    public abstract void logic();

    protected void addButton(TimedButton tb) {
        tb.setTotalOrder(buttonsCreated++);
        buttons.add(tb);
    }

    public int getButtonsCreated() {
        return buttonsCreated;
    }
}
