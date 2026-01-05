package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EndTurnIfPlayerAction extends AbstractGameAction {
    @Override
    public void update() {
        this.isDone = true;
        if (AbstractDungeon.player != null) {
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
        }
    }
}
