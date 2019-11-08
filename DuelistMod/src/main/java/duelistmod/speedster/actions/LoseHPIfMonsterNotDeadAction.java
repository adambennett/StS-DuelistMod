package duelistmod.speedster.actions;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LoseHPIfMonsterNotDeadAction extends LoseHPAction {
    private AbstractMonster m;
    private int x = 0;
    public LoseHPIfMonsterNotDeadAction(AbstractCreature target, AbstractCreature source, int amount, AbstractMonster m) {
        super(target, source, amount);
        this.m = m;
    }

    @Override
    public void update() {
        if(m.isDeadOrEscaped() || m.isDying) {
            isDone = true;
            return;
        }
        super.update();
    }
}
