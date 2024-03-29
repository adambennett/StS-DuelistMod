package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyDuelistCard;
import duelistmod.actions.common.DuelistDiscardAtEndOfTurnAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.EndureCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EnemyDiscardAtEndOfTurnAction extends AbstractGameAction
{
    private static final float DURATION;
    private final AbstractEnemyDuelist boss;

    public EnemyDiscardAtEndOfTurnAction(final AbstractEnemyDuelist boss) {
        this.duration = EnemyDiscardAtEndOfTurnAction.DURATION;
        this.boss = boss;
    }

    public EnemyDiscardAtEndOfTurnAction() {
        this(AbstractEnemyDuelist.enemyDuelist);
    }

    public void update() {
        if (this.duration == EnemyDiscardAtEndOfTurnAction.DURATION) {
            final Iterator<AbstractCard> c = this.boss.hand.group.iterator();
            while (c.hasNext()) {
                final AbstractCard e = c.next();
                if (DuelistDiscardAtEndOfTurnAction.isRetain(e)) {
                    this.boss.limbo.addToTop(e);
                    c.remove();
                }
            }
            this.addToTop(new EnemyRestoreRetainedCardsAction(this.boss, this.boss.limbo));
            if (!this.boss.hasRelic("Runic Pyramid") && !this.boss.hasPower("Equilibrium")) {
                for (int tempSize = this.boss.hand.size(), i = 0; i < tempSize; ++i) {
                    this.addToTop(new EnemyDiscardAction(this.boss, null, this.boss.hand.size(), true));
                }
            }
            final ArrayList<AbstractCard> cards = (ArrayList<AbstractCard>)this.boss.hand.group.clone();
            Collections.shuffle(cards);
            for (final AbstractCard c2 : cards) {
                EnemyDuelistCard ac = AbstractEnemyDuelist.fromCard(c2);
                ac.triggerOnEndOfPlayerTurn();
            }
            for (DuelistCard enduring : DuelistMod.enemyDuelistEnduringCards) {
                if (enduring instanceof EndureCard) {
                    ((EndureCard)enduring).onEndure(AnyDuelist.from(this.boss));
                }
            }
            DuelistMod.enemyDuelistEnduringCards.clear();
            this.isDone = true;
        }
    }

    static {
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
