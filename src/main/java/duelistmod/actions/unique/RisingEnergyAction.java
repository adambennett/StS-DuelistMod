package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.dto.AnyDuelist;

public class RisingEnergyAction extends AbstractGameAction {
    private int cardPlayCount = 0;
    private final AnyDuelist duelist;

    public RisingEnergyAction(int cardPlayCount, AnyDuelist duelist) {
        this.cardPlayCount = cardPlayCount;
        this.duelist = duelist;
    }

    public void update() {
        if (duelist.cardsPlayedThisTurn() - 1 < this.cardPlayCount) {
            duelist.gainEnergy(1);
        }
        this.isDone = true;
    }
}

