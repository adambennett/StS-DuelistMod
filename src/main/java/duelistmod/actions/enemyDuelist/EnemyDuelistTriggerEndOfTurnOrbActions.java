package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.orbs.enemy.EnemyEmptyOrbSlot;

public class EnemyDuelistTriggerEndOfTurnOrbActions  extends AbstractGameAction
{
    public void update() {
        if (!AbstractEnemyDuelist.enemyDuelist.orbs.isEmpty()) {
            for (final AbstractOrb o : AbstractEnemyDuelist.enemyDuelist.orbs) {
                o.onEndOfTurn();
            }
            if (AbstractEnemyDuelist.enemyDuelist.hasRelic("Cables") && !(AbstractEnemyDuelist.enemyDuelist.orbs.get(0) instanceof EnemyEmptyOrbSlot)) {
                AbstractEnemyDuelist.enemyDuelist.orbs.get(0).onEndOfTurn();
            }
        }
        for (final AbstractRelic r : AbstractEnemyDuelist.enemyDuelist.relics) {
            r.onPlayerEndTurn();
            r.onEnergyRecharge();
        }
        for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
            if (!AbstractEnemyDuelist.enemyDuelist.isPlayer) {
                p.atEndOfTurnPreEndTurnCards(false);
            }
            p.atEndOfTurn(AbstractEnemyDuelist.enemyDuelist.isPlayer);
        }
        this.isDone = true;
    }
}
