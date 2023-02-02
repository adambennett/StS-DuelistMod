package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class EnemyDrawActualCardsAction extends AbstractGameAction {

    private final AbstractEnemyDuelist enemy;

    public EnemyDrawActualCardsAction(AbstractEnemyDuelist enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update() {
        this.isDone = true;
        this.enemy.draw(this.enemy.drawSize);
        final ArrayList<AbstractEnemyDuelistCard> handAsBoss = new ArrayList<>();
        for (final AbstractCard c : this.enemy.hand.group) {
            handAsBoss.add(AbstractEnemyDuelist.fromCard(c));
        }
        handAsBoss.sort(new AbstractEnemyDuelist.sortByNewPrio());
        final ArrayList<AbstractEnemyDuelistCard> sortedHand = this.enemy.manualHandEvaluation(handAsBoss);
        final ArrayList<AbstractCard> newHand = new ArrayList<>();
        for (final AbstractEnemyDuelistCard c2 : sortedHand) {
            newHand.add(c2.cardBase);
            c2.applyPowers();
        }
        this.enemy.hand.group = newHand;
        this.enemy.hand.refreshHandLayout();
        this.enemy.applyPowers();
        Util.log("Enemy hand size after drawing: " + this.enemy.hand.group.size());
    }
}
