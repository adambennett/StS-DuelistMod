package duelistmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.cards.pools.toon.StanleysSketchbook;

public class SketchbookUsesNumber extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:USES";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof StanleysSketchbook)) return 0;
        StanleysSketchbook dc = (StanleysSketchbook)card;
        return dc.getUsesRemaining();
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof StanleysSketchbook)) return 0;
        StanleysSketchbook dc = (StanleysSketchbook)card;
        return dc.getUsesRemaining();
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
