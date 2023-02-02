package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.helpers.Util;
import duelistmod.powers.SummonPower;

public class EnemyIntentAndBudgetAction extends AbstractGameAction {

    private final AbstractEnemyDuelist enemy;

    public EnemyIntentAndBudgetAction(AbstractEnemyDuelist enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        this.isDone = true;
        int budget = this.enemy.energyPanel.getCurrentEnergy();
        int tributeBudget = this.enemy.hasPower(SummonPower.POWER_ID) ? this.enemy.getPower(SummonPower.POWER_ID).amount : 0;
        Util.log("Hand size while preparing hand cards: " + AbstractEnemyDuelist.enemyDuelist.hand.group.size());
        for (final AbstractCard c : AbstractEnemyDuelist.enemyDuelist.hand.group) {
            AbstractEnemyDuelistCard holder = AbstractEnemyDuelist.fromCard(c);
            DuelistCard dc = c instanceof DuelistCard ? (DuelistCard)c : null;
            boolean tribBudgetCheck = dc == null ? true : dc.tributes <= tributeBudget;
            int tribsToLose = dc == null ? 0 : dc.tributes;
            if (c.costForTurn <= budget && c.costForTurn != -2 && tribBudgetCheck && holder.canUse(AbstractDungeon.player)) {
                holder.createIntent();
                holder.bossLighten();
                tributeBudget -= tribsToLose;
                budget -= c.costForTurn;
                budget += holder.energyGeneratedIfPlayed;
                if (budget >= 0) {
                    continue;
                }
                budget = 0;
            }
            else {
                if (c.costForTurn != -2 || c.type != AbstractCard.CardType.CURSE || c.color != AbstractCard.CardColor.CURSE || !tribBudgetCheck || !holder.canUse(AbstractDungeon.player)) {
                    continue;
                }
                AbstractEnemyDuelist.fromCard(c).bossLighten();
            }
        }
        for (final AbstractCard c : AbstractEnemyDuelist.enemyDuelist.hand.group) {
            final AbstractEnemyDuelistCard cB = AbstractEnemyDuelist.fromCard(c);
            cB.refreshIntentHbLocation();
        }
    }
}
