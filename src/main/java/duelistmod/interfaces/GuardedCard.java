package duelistmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyGuardedCard;
import duelistmod.powers.warrior.CubicKarmaPower;

import java.util.List;

public interface GuardedCard {

    default int getEffectiveGuardedRequirement(DuelistCard card, int requiredBlock) {
        AnyDuelist duelist = AnyDuelist.from(card);
        int required = requiredBlock;

        if (card.type == AbstractCard.CardType.ATTACK && duelist.hasPower(CubicKarmaPower.POWER_ID)) {
            CubicKarmaPower pow = (CubicKarmaPower) duelist.getPower(CubicKarmaPower.POWER_ID);
            if (pow != null && pow.canTrigger()) {
                required = 0;
            }
        }

        if (duelist.relics() != null) {
            for (AbstractRelic r : duelist.relics()) {
                if (r instanceof DuelistRelic) {
                    required = ((DuelistRelic) r).modifyGuardedRequirement(required);
                }
            }
        }

        if (duelist.powers() != null) {
            for (AbstractPower p : duelist.powers()) {
                if (p instanceof DuelistPower) {
                    required = ((DuelistPower) p).modifyGuardedRequirement(required);
                }
            }
        }

        return Math.max(0, required);
    }

    default boolean isGuardedActive(DuelistCard card, int requiredBlock) {
        AnyDuelist duelist = AnyDuelist.from(card);
        int required = getEffectiveGuardedRequirement(card, requiredBlock);
        return duelist.creature() != null && duelist.creature().currentBlock >= required;
    }

    default void triggerGuarded(AnyGuardedCard caller, AnyDuelist duelist, List<AbstractCreature> targets) {
        if (duelist.powers() != null) {
            for (AbstractPower power : duelist.powers()) {
                if (power instanceof DuelistPower) {
                    DuelistPower dp = (DuelistPower) power;
                    dp.onGuardedTrigger(caller, targets);
                }
            }
        }
        if (duelist.relics() != null) {
            for (AbstractRelic relic : duelist.relics()) {
                if (relic instanceof DuelistRelic) {
                    DuelistRelic duelistRelic = (DuelistRelic) relic;
                    duelistRelic.onGuardedTrigger(caller, targets);
                }
            }
        }
    }
}
