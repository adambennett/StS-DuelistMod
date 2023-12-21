package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayedActionAction extends AbstractGameAction {
    AbstractGameAction act;

    public DelayedActionAction(final AbstractGameAction a) {
        this.act = a;
    }

    public void update() {
        this.addToBot(this.act);
        this.isDone = true;
    }
}

