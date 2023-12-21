package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.TokenCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyDuelistCard;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.WonderGaragePower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyDrawActualCardsAction extends AbstractGameAction {

    private final AbstractEnemyDuelist enemy;
    private final Integer drawSize;
    private final List<EnemyDuelistCard> cardsToAdd;

    public EnemyDrawActualCardsAction(AbstractEnemyDuelist enemy) {
        this(enemy, enemy.drawSize, new ArrayList<>());
    }

    public EnemyDrawActualCardsAction(AbstractEnemyDuelist enemy, int amt) {
        this(enemy, amt, new ArrayList<>());
    }

    public EnemyDrawActualCardsAction(AbstractEnemyDuelist enemy, List<EnemyDuelistCard> cards) {
        this(enemy, null, cards);
    }

    public EnemyDrawActualCardsAction(AbstractEnemyDuelist enemy, Integer size, List<EnemyDuelistCard> cardsToAdd) {
        this.enemy = enemy;
        this.drawSize = size;
        this.cardsToAdd = cardsToAdd;
    }

    @Override
    public void update() {
        this.isDone = true;
        if (this.drawSize != null) {
            this.enemy.draw(this.drawSize);
        } else if (this.cardsToAdd != null && this.cardsToAdd.size() > 0) {
            for (EnemyDuelistCard card : this.cardsToAdd) {
                AbstractCard c = card.cardBase;
                if ((c instanceof TokenCard || c.hasTag(Tags.TOKEN)) && !c.upgraded && this.enemy.hasPower(WonderGaragePower.POWER_ID) && c.canUpgrade()) {
                    c.upgrade();
                }
                this.enemy.addCardToHand(card);
            }
        }
        ArrayList<EnemyDuelistCard> handAsBoss = new ArrayList<>();
        for (AbstractCard c : this.enemy.hand.group) {
            handAsBoss.add(AbstractEnemyDuelist.fromCard(c));
        }
        Collections.sort(handAsBoss);
        ArrayList<EnemyDuelistCard> sortedHand = this.enemy.manualHandEvaluation(handAsBoss);
        Util.log("Hand fully evaluated: " + sortedHand);
        ArrayList<AbstractCard> newHand = new ArrayList<>();
        for (EnemyDuelistCard c2 : sortedHand) {
            newHand.add(c2.cardBase);
            c2.applyPowers();
            c2.cardBase.stopGlowing();
        }
        this.enemy.hand.group = newHand;
        this.enemy.hand.refreshHandLayout();
        this.enemy.applyPowers();
    }
}
