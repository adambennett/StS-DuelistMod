package duelistmod.variables;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import basemod.abstracts.DynamicVariable;
import duelistmod.abstracts.DuelistCard;

public class SummonMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:SUMM";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof DuelistCard && ((DuelistCard) card).isSummonsModified;

    }

    @Override
    public int value(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        return dc.summons;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (!(card instanceof DuelistCard)) return 0;
        DuelistCard dc = (DuelistCard)card;
        dc = (DuelistCard)dc.makeCopy();
        for (int i = 0; i < card.timesUpgraded; i++) {
            dc.upgrade();
        }
        return dc.baseSummons;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof DuelistCard && ((DuelistCard) card).upgradedSummons;
    }
    
    @Override
    public Color getIncreasedValueColor()
    {
    	 return Settings.GREEN_TEXT_COLOR;
    }

    @Override
    public Color getDecreasedValueColor()
    {
        return Settings.RED_TEXT_COLOR;
    }

    @Override
    public Color getUpgradedColor(AbstractCard card) {
        if (card instanceof DuelistCard) {
            DuelistCard d = (DuelistCard)card;
            if (d.isBadSummonUpgrade) {
                return Settings.RED_TEXT_COLOR;
            }
        }
        return Settings.GREEN_TEXT_COLOR;
    }
}
