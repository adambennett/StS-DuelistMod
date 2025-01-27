package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class NoStackDuelistPower extends DuelistPower implements NonStackablePower {
	public NoStackDuelistPower(final AbstractCreature owner, final AbstractCreature source) {}
    public NoStackDuelistPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this(owner, source);
    }
}
