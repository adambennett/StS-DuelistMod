package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.vfx.HolyMoleyGreatBallOfRelics;

public class RelicBallAction extends AbstractGameAction {

    private HolyMoleyGreatBallOfRelics rrfe;

    public RelicBallAction(AbstractCreature target, boolean vexOnly) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;

        rrfe = new HolyMoleyGreatBallOfRelics(target, vexOnly);
        AbstractDungeon.effectList.add(rrfe);
    }

    public RelicBallAction(AbstractCreature target, boolean vexOnly, float CHAOS, int gatherspeed, int flighttime) {
        this.target = target;
        this.actionType = ActionType.DAMAGE;

        rrfe = new HolyMoleyGreatBallOfRelics(target, vexOnly, CHAOS, gatherspeed, flighttime);
        AbstractDungeon.effectList.add(rrfe);
    }

    public void update() {
        if (rrfe.finishedAction)
            this.isDone = true;
    }
}