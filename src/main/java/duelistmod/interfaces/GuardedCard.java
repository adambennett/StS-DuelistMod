package duelistmod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

import java.util.List;

public interface GuardedCard {

    default boolean isGuardedActive(DuelistCard card, int requiredBlock) {
        AnyDuelist duelist = AnyDuelist.from(card);
        return duelist.creature() != null && duelist.creature().currentBlock >= requiredBlock;
    }

    void triggerGuarded(AnyDuelist duelist, List<AbstractCreature> targets);
}
