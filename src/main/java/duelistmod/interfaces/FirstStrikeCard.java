package duelistmod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.dto.AnyDuelist;

public interface FirstStrikeCard {

    /** Target may be null or dead */
    default void triggerFirstStrike(FirstStrikeDuelistCard caller, AnyDuelist duelist, AbstractCreature target) {
        boolean anyPowersTriggeringNow =
                DuelistMod.triggeringCombinationAttackFirstStrikeEffect ||
                DuelistMod.triggeringDownbeatFirstStrikeEffect ||
                DuelistMod.triggeringEgoBoostFirstStrikeEffect;
        if (duelist.powers() != null && !anyPowersTriggeringNow) {
            for (AbstractPower power : duelist.powers()) {
                if (power instanceof DuelistPower) {
                    DuelistPower dp =  (DuelistPower) power;
                    dp.onFirstStrikeTriggered(caller, target);
                }
            }
        }
        if (duelist.relics() != null) {
            for (AbstractRelic relic : duelist.relics()) {
                if (relic instanceof DuelistRelic) {
                    DuelistRelic  duelistRelic = (DuelistRelic)relic;
                    duelistRelic.onFirstStrikeTriggered(caller, target);
                }
            }
        }
    }
}

