package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.abstracts.FirstStrikeDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.warrior.CubicKarmaPower;

public class FirstStrikeAction extends AbstractGameAction {

    public interface Callback {
        void run(FirstStrikeDuelistCard caller, AnyDuelist duelist, AbstractCreature target);
    }

    private final AnyDuelist duelist;
    private final AbstractCreature target;
    private final int preHp;
    private final boolean wasFullHp;
    private final Callback callback;
    private final FirstStrikeDuelistCard striker;

    public FirstStrikeAction(AnyDuelist duelist, AbstractCreature target, int preHp, boolean wasFullHp, FirstStrikeDuelistCard striker) {
        this.duelist = duelist;
        this.target = target;
        this.preHp = preHp;
        this.wasFullHp = wasFullHp;
        this.striker = striker;
        this.callback = striker != null ? striker::triggerFirstStrike : null;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        this.isDone = true;
        boolean targetGone = this.target == null || this.target.isDeadOrEscaped();
        boolean firstStrikeActive = this.wasFullHp && (targetGone || this.target.currentHealth < this.preHp);

        if (this.striker.type == CardType.ATTACK && this.duelist.hasPower(CubicKarmaPower.POWER_ID)) {
            CubicKarmaPower pow = (CubicKarmaPower) this.duelist.getPower(CubicKarmaPower.POWER_ID);
            if (pow.canTrigger()) {
                firstStrikeActive = true;
                pow.disableTrigger();
            }
        }

        if (firstStrikeActive && this.callback != null) {
            this.callback.run(this.striker, this.duelist, this.target);
        }
    }
}
