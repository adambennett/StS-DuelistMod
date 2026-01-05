package duelistmod.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.abstracts.GuardedMagnetCard;

public class GuardedNum extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:G";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof GuardedDuelistCard) {
            GuardedDuelistCard c = (GuardedDuelistCard) card;
            int base = c.getBaseGuardedCheck();
            int eff = c.getEffectiveGuardedRequirement(c, c.getGuardedCheck());
            return eff != base || c.isGuardedCheckModifiedForTurn();
        }
        if (card instanceof GuardedMagnetCard) {
            GuardedMagnetCard c = (GuardedMagnetCard) card;
            int base = c.getBaseGuardedCheck();
            int eff = c.getEffectiveGuardedRequirement(c, c.getGuardedCheck());
            return eff != base || c.isGuardedCheckModifiedForTurn();
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof GuardedDuelistCard) {
            GuardedDuelistCard c = (GuardedDuelistCard) card;
            return c.getEffectiveGuardedRequirement(c, c.getGuardedCheck());
        }
        if (card instanceof GuardedMagnetCard) {
            GuardedMagnetCard c = (GuardedMagnetCard) card;
            return c.getEffectiveGuardedRequirement(c, c.getGuardedCheck());
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof GuardedDuelistCard ? ((GuardedDuelistCard) card).getBaseGuardedCheck() : card instanceof GuardedMagnetCard ? ((GuardedMagnetCard) card).getBaseGuardedCheck() : -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return (card instanceof GuardedDuelistCard && ((GuardedDuelistCard) card).getUpgradedGuardedCheck()) || (card instanceof GuardedMagnetCard && ((GuardedMagnetCard) card).getUpgradedGuardedCheck());
    }

    @Override
    public Color getIncreasedValueColor() {
        return Settings.RED_TEXT_COLOR;
    }

    @Override
    public Color getDecreasedValueColor()
    {
        return Settings.GREEN_TEXT_COLOR;
    }

    @Override
    public Color getUpgradedColor(AbstractCard card) {
        return Settings.GREEN_TEXT_COLOR;
    }
}
