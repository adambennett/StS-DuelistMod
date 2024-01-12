package duelistmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GustoCodorAction extends AbstractGameAction {

    private static final float DURATION = 0.1F;
    private final AbstractCard self;

    public GustoCodorAction(AbstractCard target) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = DURATION;
        self = target;
    }

    @Override
    public void update() {
        if (duration == DURATION)
        {
            addToBot(new FetchAction(AbstractDungeon.player.discardPile, c -> c == self, 1));
        }
        tickDuration();
    }
}
