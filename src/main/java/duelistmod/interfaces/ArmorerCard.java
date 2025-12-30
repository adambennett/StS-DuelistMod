package duelistmod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

import java.util.List;

public interface ArmorerCard {

    default boolean isArmorerActive(DuelistCard card) {
        AnyDuelist duelist = AnyDuelist.from(card);
        return duelist.isArmorer();
    }

    void triggerArmorer(AnyDuelist duelist, List<AbstractCreature> targets);
}
