package duelistmod.aspects;

import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RevengeAspect {

    @Pointcut("execution(void duelistmod.interfaces.RevengeCard.triggerRevenge(..)) && target(revengeCard)")
    public void triggerRevengePointcut(RevengeCard revengeCard) {}

    @After("triggerRevengePointcut(revengeCard)")
    public void afterTriggerRevenge(RevengeCard revengeCard) {

        if (DuelistMod.triggeringRemoteRevenge) {
            DuelistMod.triggeringRemoteRevenge = false;
            return;
        }

        if (revengeCard instanceof DuelistCard) {
            DuelistCard duelistCard = (DuelistCard)revengeCard;
            AnyDuelist duelist = AnyDuelist.from(duelistCard);

            if (!DuelistMod.triggeringRemoteRevengeEffect) {
                for (AbstractPower power : duelist.powers()) {
                    if (power instanceof DuelistPower) {
                        DuelistPower duelistPower = (DuelistPower)power;
                        duelistPower.onRevengeTriggered(revengeCard, duelistCard);
                    }
                }
            }
            DuelistMod.triggeringRemoteRevengeEffect = false;

            if (duelist.player()) {
                DuelistMod.revengeCardsTriggeredThisCombat.add(duelistCard);
                DuelistMod.revengeTriggersThisTurn++;
                DuelistMod.revengeTriggersThisCombat++;
                DuelistMod.revengeTriggersThisRun++;
            } else if (duelist.getEnemy() != null) {
                duelist.getEnemy().revengeCardsTriggeredThisCombat.add(duelistCard);
                duelist.getEnemy().revengeTriggersThisTurn++;
                duelist.getEnemy().revengeTriggersThisCombat++;
            }
        }

    }

}
