package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MillAndCheckAction extends AbstractGameAction {

    private final Predicate<ArrayList<AbstractCard>> checkCondition;
    private final Consumer<ArrayList<AbstractCard>> performAction;
    private final ArrayList<AbstractCard> milledCards = new ArrayList<>();
    private final AnyDuelist duelist;

    public MillAndCheckAction(AnyDuelist duelist, int amount, Predicate<ArrayList<AbstractCard>> check, Consumer<ArrayList<AbstractCard>> behavior) {
        this.setValues(null, source, amount);
        this.checkCondition = check;
        this.performAction = behavior;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duelist = duelist;
    }

    public MillAndCheckAction(AnyDuelist duelist, int amount, Consumer<ArrayList<AbstractCard>> behavior) {
        this(duelist, amount, null, behavior);
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {
            int numCardsToBeMilled = Math.min(duelist.drawPile().size(), this.amount);
            for(int i = 0; i < numCardsToBeMilled; i++) {
                AbstractCard milledCard = duelist.drawPileGroup().getTopCard();
                this.milledCards.add(milledCard);
                duelist.drawPileGroup().moveToDiscardPile(milledCard);
            }

            if (this.checkCondition == null) {
                performAction.accept(milledCards);
            } else if (this.checkCondition.test(this.milledCards)) {
                performAction.accept(milledCards);
            }
        }
        this.tickDuration();
    }
}
