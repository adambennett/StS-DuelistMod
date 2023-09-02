package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DuelistRestoreRetainedCardsAction extends AbstractGameAction {

    private final CardGroup group;

    public DuelistRestoreRetainedCardsAction(final CardGroup group) {
        this.setValues(AbstractDungeon.player, this.source, -1);
        this.group = group;
    }

    @Override
    public void update() {
        this.isDone = true;
        final Iterator<AbstractCard> c = this.group.group.iterator();
        while (c.hasNext()) {
            final AbstractCard e = c.next();
            if (DuelistDiscardAtEndOfTurnAction.isRetain(e)) {
                e.onRetained();
                AbstractDungeon.player.hand.addToTop(e);
                e.retain = false;
                c.remove();
            }
        }
        AbstractDungeon.player.hand.refreshHandLayout();
    }
}
