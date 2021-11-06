package duelistmod.subscribers;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.powers.*;

public class PostPowerApply {

    // Called when powers are applied (but not modified)
    // i.e. when ApplyPowerAction is used
    // Used to allow powers to increase their counters without triggering (ex: Deva Form)
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {

    }

}
