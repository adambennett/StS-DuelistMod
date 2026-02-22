package duelistmod.dto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.abstracts.GuardedMagnetCard;

public class AnyGuardedCard {

    private final GuardedDuelistCard duelistCard;
    private final GuardedMagnetCard magnetCard;

    public AnyGuardedCard(GuardedDuelistCard duelistCard) {
        this.duelistCard = duelistCard;
        this.magnetCard = null;
    }

    public AnyGuardedCard(GuardedMagnetCard magnetCard) {
        this.duelistCard = null;
        this.magnetCard = magnetCard;
    }

    public Object getCard() {
        return this.duelistCard != null ? this.duelistCard : this.magnetCard;
    }

    public AbstractCard get() {
        Object c = this.getCard();
        if (c instanceof AbstractCard) {
            return (AbstractCard) c;
        }
        return null;
    }

    public static AnyGuardedCard from(GuardedDuelistCard card) {
        return new AnyGuardedCard(card);
    }

    public static AnyGuardedCard from(GuardedMagnetCard magnetCard) {
        return new AnyGuardedCard(magnetCard);
    }

    public static void incGuardedCheckForTurn(AbstractCard c, int add) {
        if (c instanceof GuardedDuelistCard) {
            GuardedDuelistCard guardedCard = (GuardedDuelistCard) c;
            int v = guardedCard.getGuardedCheckForTurn() + add;
            guardedCard.setGuardedCheckForTurn(Math.max(0, v));
            if (add != 0) {
                guardedCard.setGuardedCheckModifiedForTurn(true);
            }
        } else if (c instanceof GuardedMagnetCard) {
            GuardedMagnetCard guardedCard = (GuardedMagnetCard) c;
            int v = guardedCard.getGuardedCheckForTurn() + add;
            guardedCard.setGuardedCheckForTurn(Math.max(0, v));
            if (add != 0) {
                guardedCard.setGuardedCheckModifiedForTurn(true);
            }
        }
    }
}
