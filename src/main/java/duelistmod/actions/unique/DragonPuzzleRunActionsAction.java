package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static duelistmod.helpers.PuzzleHelper.dragonEffects;

public class DragonPuzzleRunActionsAction extends AbstractGameAction {

    @Override
    public void update() {
        dragonEffects();
        this.isDone = true;
    }
}
