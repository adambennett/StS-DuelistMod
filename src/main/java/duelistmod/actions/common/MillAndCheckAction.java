package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MillAndCheckAction extends AbstractGameAction {

    private final Predicate<ArrayList<AbstractCard>> checkCondition;
    private final Consumer<ArrayList<AbstractCard>> performAction;
    private final ArrayList<AbstractCard> milledCards = new ArrayList<>();
    public MillAndCheckAction(AbstractCreature source, int amount, Predicate<ArrayList<AbstractCard>> check, Consumer<ArrayList<AbstractCard>> behavior)
    {
        this.setValues(null, source, amount);
        this.checkCondition = check;
        this.performAction = behavior;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public MillAndCheckAction(AbstractCreature source, int amount, Consumer<ArrayList<AbstractCard>> behavior)
    {
        this.setValues(null, source, amount);
        this.performAction = behavior;
        this.checkCondition = null;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {
            AbstractPlayer player = AbstractDungeon.player;
            int numCardsToBeMilled = Math.min(player.drawPile.size(), this.amount);
            for(int i = 0; i < numCardsToBeMilled; i++)
            {
                AbstractCard milledCard = player.drawPile.getTopCard();
                this.milledCards.add(milledCard);
                player.drawPile.moveToDiscardPile(milledCard);
            }

            if (this.checkCondition == null)
            {
                performAction.accept(milledCards);
            }
            else if (this.checkCondition.test(this.milledCards))
            {
                performAction.accept(milledCards);
            }
        }

        this.tickDuration();
    }
}
