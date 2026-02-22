package duelistmod.interfaces;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.helpers.Util;

public interface RevengeCard {

    default boolean isRevengeActive(DuelistCard card) {
        return Util.revengeActive(card);
    }

    default void trigger(AnyRevengeCard card, AnyDuelist duelist) {
        boolean noPowersTriggering = !DuelistMod.triggeringRemoteRevengeEffect && !DuelistMod.triggeringCombinationAttackRevengeEffect;
        if (duelist.powers() != null && noPowersTriggering) {
            for (AbstractPower power : duelist.powers()) {
                if (power instanceof DuelistPower) {
                    DuelistPower duelistPower = (DuelistPower) power;
                    duelistPower.onRevengeTriggered(card);
                }
            }
        }
        if (duelist.relics() != null) {
            for (AbstractRelic relic : duelist.relics()) {
                if (relic instanceof DuelistRelic) {
                    DuelistRelic duelistRelic = (DuelistRelic) relic;
                    duelistRelic.onRevengeTriggered(card);
                }
            }
        }

        if (duelist.player()) {
            if (!DuelistMod.triggeringRemoteRevengeEffect && !DuelistMod.triggeringCombinationAttackRevengeEffect) {
                DuelistMod.revengeCardsTriggeredThisCombat.add((DuelistCard) card.getCard());
            }
            DuelistMod.revengeTriggersThisTurn++;
            DuelistMod.revengeTriggersThisCombat++;
            DuelistMod.revengeTriggersThisRun++;
        } else if (duelist.getEnemy() != null) {
            if (!DuelistMod.triggeringRemoteRevengeEffect && !DuelistMod.triggeringCombinationAttackRevengeEffect) {
                duelist.getEnemy().revengeCardsTriggeredThisCombat.add((DuelistCard) card.getCard());
            }
            duelist.getEnemy().revengeTriggersThisTurn++;
            duelist.getEnemy().revengeTriggersThisCombat++;
        }
    }
}
