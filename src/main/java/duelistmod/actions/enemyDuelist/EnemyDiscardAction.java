package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

import java.util.ArrayList;

public class EnemyDiscardAction extends AbstractGameAction
{
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    private static final float DURATION;
    private final AbstractEnemyDuelist p;
    private final boolean endTurn;
    private final boolean isRandom;

    public EnemyDiscardAction(AbstractCreature target, AbstractCreature source, int amount,  boolean isRandom) {
        this(target, source, amount, isRandom, false);
    }

    public EnemyDiscardAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean endTurn) {
        this.p = (AbstractEnemyDuelist)target;
        this.setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DISCARD;
        this.endTurn = endTurn;
        this.duration = EnemyDiscardAction.DURATION;
        this.isRandom = isRandom;
    }

    public void update() {
        if (this.duration == EnemyDiscardAction.DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                for (int tmp = this.p.hand.size(), i = 0; i < tmp; ++i) {
                    final AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToDiscardPile(c);
                    if (!this.endTurn) {
                        c.triggerOnManualDiscard();
                    }
                }
                this.p.hand.applyPowers();
                this.tickDuration();
                return;
            }
            if (!this.isRandom) {
                if (this.amount < 0) {
                    this.tickDuration();
                    return;
                }

                if (this.p.hand.size() > this.amount) {
                    int remaining = this.amount;
                    while (remaining > 0) {
                        final AbstractCard c = this.p.hand.getBottomCard();
                        this.p.hand.moveToDiscardPile(c);
                        if (!this.endTurn) {
                            c.triggerOnManualDiscard();
                        }
                        remaining--;
                    }
                }

                this.p.hand.applyPowers();
                this.tickDuration();
                this.addToTop(new EnemyDrawActualCardsAction(this.p, new ArrayList<>()));
                return;
            }
            for (int j = 0; j < this.amount; ++j) {
                final AbstractCard c2 = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.moveToDiscardPile(c2);
                c2.triggerOnManualDiscard();
            }
            this.addToTop(new EnemyDrawActualCardsAction(this.p, new ArrayList<>()));
        }
        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
        TEXT = EnemyDiscardAction.uiStrings.TEXT;
        DURATION = Settings.ACTION_DUR_XFAST;
    }
}
