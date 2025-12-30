package duelistmod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.CubicKarmaPower;

import java.util.ArrayList;
import java.util.List;

public interface FirstStrikeCard {

    default boolean isFirstStrikeActive(AbstractCreature target) {
        if (target == null) return false;
        return !target.isDeadOrEscaped() && target.currentHealth == target.maxHealth;
    }

    void triggerFirstStrike(AnyDuelist duelist, AbstractCreature target);
}

