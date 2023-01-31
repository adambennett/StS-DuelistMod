package duelistmod.actions.enemyDuelist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyUseCardAction extends AbstractGameAction {
    public AbstractCreature target;
    public boolean exhaustCard;
    public boolean reboundCard;
    private final AbstractCard targetCard;

    public EnemyUseCardAction(final AbstractCard card, final AbstractCreature target) {
        this.reboundCard = false;
        this.targetCard = card;
        this.target = target;
        if (card.exhaustOnUseOnce || card.exhaust) {
            this.exhaustCard = true;
        }
        this.setValues((AbstractCreature) AbstractEnemyDuelist.enemyDuelist, (AbstractCreature)null, 1);
        this.duration = 0.15f;
        for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
            if (!card.dontTriggerOnUseCard && p.type != AbstractPower.PowerType.DEBUFF) {
                p.onUseCard(card, this.makeNormalCardAction());
            }
        }
        for (final AbstractRelic r : AbstractEnemyDuelist.enemyDuelist.relics) {
            if (!card.dontTriggerOnUseCard) {
                r.onUseCard(card, this.makeNormalCardAction());
            }
        }
        for (final AbstractCard c : AbstractEnemyDuelist.enemyDuelist.hand.group) {
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        if (this.exhaustCard) {
            this.actionType = AbstractGameAction.ActionType.EXHAUST;
        }
        else {
            this.actionType = AbstractGameAction.ActionType.USE;
        }
    }

    public EnemyUseCardAction(final AbstractCard targetCard) {
        this(targetCard, null);
    }

    public UseCardAction makeNormalCardAction() {
        final AbstractCard cc = this.targetCard.makeStatEquivalentCopy();
        cc.dontTriggerOnUseCard = true;
        return new UseCardAction(cc, (AbstractCreature)AbstractEnemyDuelist.enemyDuelist);
    }

    public void update() {
        if (this.duration == 0.15f && AbstractEnemyDuelist.enemyDuelist != null) {
            for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
                if (!this.targetCard.dontTriggerOnUseCard && p.type != AbstractPower.PowerType.DEBUFF) {
                    p.onAfterUseCard(this.targetCard, this.makeNormalCardAction());
                }
            }
            this.targetCard.freeToPlayOnce = false;
            this.targetCard.isInAutoplay = false;
            if (this.targetCard.purgeOnUse) {
                this.addToTop((AbstractGameAction)new EnemyShowCardAndPoofAction(this.targetCard));
                this.isDone = true;
                AbstractEnemyDuelist.enemyDuelist.cardInUse = null;
                return;
            }
            if (this.targetCard.type == AbstractCard.CardType.POWER) {
                this.addToTop((AbstractGameAction)new EnemyShowCardAction(this.targetCard));
                if (Settings.FAST_MODE) {
                    this.addToTop((AbstractGameAction)new WaitAction(0.1f));
                }
                else {
                    this.addToTop((AbstractGameAction)new WaitAction(0.7f));
                }
                AbstractEnemyDuelist.enemyDuelist.hand.empower(this.targetCard);
                this.isDone = true;
                AbstractEnemyDuelist.enemyDuelist.hand.applyPowers();
                AbstractEnemyDuelist.enemyDuelist.hand.glowCheck();
                AbstractEnemyDuelist.enemyDuelist.cardInUse = null;
                return;
            }
            AbstractEnemyDuelist.enemyDuelist.cardInUse = null;
            boolean spoonProc = false;
            if (this.exhaustCard && AbstractEnemyDuelist.enemyDuelist.hasRelic("Strange Spoon") && this.targetCard.type != AbstractCard.CardType.POWER) {
                spoonProc = AbstractDungeon.cardRandomRng.randomBoolean();
            }
            if (!this.exhaustCard || spoonProc) {
                if (spoonProc) {
                    AbstractEnemyDuelist.enemyDuelist.getRelic("Strange Spoon").flash();
                }
                if (this.reboundCard) {
                    AbstractEnemyDuelist.enemyDuelist.hand.moveToDeck(this.targetCard, false);
                }
                else if (this.targetCard.shuffleBackIntoDrawPile) {
                    AbstractEnemyDuelist.enemyDuelist.hand.moveToDeck(this.targetCard, true);
                }
                else if (this.targetCard.returnToHand) {
                    AbstractEnemyDuelist.enemyDuelist.hand.moveToHand(this.targetCard);
                    AbstractEnemyDuelist.enemyDuelist.onCardDrawOrDiscard();
                }
                else {
                    AbstractEnemyDuelist.enemyDuelist.hand.moveToDiscardPile(this.targetCard);
                }
            }
            else {
                AbstractEnemyDuelist.enemyDuelist.hand.moveToExhaustPile(this.targetCard);
            }
            this.targetCard.exhaustOnUseOnce = false;
            this.targetCard.dontTriggerOnUseCard = false;
            this.addToBot(new EnemyHandCheckAction());
        }
        this.tickDuration();
    }
}
