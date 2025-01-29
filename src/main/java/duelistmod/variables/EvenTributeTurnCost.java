package duelistmod.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.DuelistCard;

public class EvenTributeTurnCost extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:EvenTributeTurnCost";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        return -1 * dc.evenTurnTributeChange;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        return -1 * dc.evenTurnTributeChange;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }

}
