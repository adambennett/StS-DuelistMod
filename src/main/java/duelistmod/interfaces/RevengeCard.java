package duelistmod.interfaces;

import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public interface RevengeCard  {

    default boolean isRevengeActive(DuelistCard card) {
        return Util.revengeActive(card);
    }

    void triggerRevenge(AnyDuelist duelist);
}
