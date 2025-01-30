package duelistmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.DuelistCard;

public class OddTributeTurnCost extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:OddTributeTurnCost";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        return -1 * dc.oddTurnTributeChange;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        return -1 * dc.oddTurnTributeChange;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }

}
