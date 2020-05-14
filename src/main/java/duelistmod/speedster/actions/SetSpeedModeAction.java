package duelistmod.speedster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import duelistmod.DuelistMod;
import duelistmod.speedster.mechanics.AbstractSpeedTime;

public class SetSpeedModeAction extends AbstractGameAction {
    AbstractSpeedTime instance;

    public SetSpeedModeAction(AbstractSpeedTime instance) {
        this.instance = instance;
    }

    public void update() {
        DuelistMod.speedScreen = instance;
        isDone = true;
    }
}
