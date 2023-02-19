package duelistmod.abstracts.enemyDuelist;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class EnemyCardGroup extends CardGroup {

    public AbstractEnemyDuelist owner;
    public static AbstractEnemyDuelistCard hov2holder;

    public EnemyCardGroup(CardGroupType type, AbstractEnemyDuelist enemyDuelist) {
        super(type);
        this.owner = enemyDuelist;
    }

    public void moveToDiscardPile(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        this.owner.discardPile.addToTop(c);
        this.owner.onCardDrawOrDiscard();
    }

    public void moveToExhaustPile(final AbstractCard c) {
        for (final AbstractRelic r : this.owner.relics) {
            r.onExhaust(c);
        }
        for (final AbstractPower p : this.owner.powers) {
            p.onExhaust(c);
        }
        c.triggerOnExhaust();
        this.resetCardBeforeMoving(c);
        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        this.owner.exhaustPile.addToTop(c);
        this.owner.onCardDrawOrDiscard();
    }

    public void moveToHand(final AbstractCard c, final CardGroup group) {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0f);
        c.drawScale = 0.12f;
        c.targetDrawScale = 0.35f;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        group.removeCard(c);
        this.owner.hand.addToTop(c);
        this.owner.hand.refreshHandLayout();
        this.owner.hand.applyPowers();
    }

    public void moveToHand(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0f);
        c.drawScale = 0.12f;
        c.targetDrawScale = 0.35f;
        c.current_x = CardGroup.DRAW_PILE_X;
        c.current_y = CardGroup.DRAW_PILE_Y;
        this.owner.hand.addToTop(c);
        this.owner.hand.refreshHandLayout();
        this.owner.hand.applyPowers();
    }

    public void moveToDeck(final AbstractCard c, final boolean randomSpot) {
        this.resetCardBeforeMoving(c);
        if (randomSpot && this.owner.drawPile.size() > 0) {
            this.owner.drawPile.addToRandomSpot(c);
        } else {
            this.owner.drawPile.addToTop(c);
        }
    }

    public void moveToBottomOfDeck(final AbstractCard c) {
        this.resetCardBeforeMoving(c);
        this.owner.drawPile.addToBottom(c);
    }

    private void resetCardBeforeMoving(final AbstractCard c) {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }
        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        this.group.remove(c);
    }

    public void initializeDeck(final CardGroup masterDeck) {
        this.clear();
        final CardGroup copy = new CardGroup(masterDeck, CardGroup.CardGroupType.DRAW_PILE);
        copy.shuffle(AbstractDungeon.shuffleRng);
        final ArrayList<AbstractCard> placeOnTop = new ArrayList<AbstractCard>();
        for (final AbstractCard c : copy.group) {
            if (c.isInnate) {
                placeOnTop.add(c);
            }
            else if (c.inBottleFlame || c.inBottleLightning || c.inBottleTornado) {
                placeOnTop.add(c);
            }
            else {
                c.target_x = CardGroup.DRAW_PILE_X;
                c.target_y = CardGroup.DRAW_PILE_Y;
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                this.addToTop(c);
            }
        }
        for (final AbstractCard c : placeOnTop) {
            this.addToTop(c);
        }
        if (placeOnTop.size() > AbstractDungeon.player.masterHandSize) {}
        placeOnTop.clear();
    }

    public void triggerOnOtherCardPlayed(final AbstractCard usedCard) {
        for (final AbstractCard c : this.group) {
            if (c != usedCard) {
                c.triggerOnOtherCardPlayed(usedCard);
            }
        }
        for (final AbstractPower p : AbstractEnemyDuelist.enemyDuelist.powers) {
            p.onAfterCardPlayed(usedCard);
        }
    }

    public void removeCard(final AbstractCard c) {
        this.group.remove(c);
        if (this.type == CardGroup.CardGroupType.MASTER_DECK) {
            c.onRemoveFromMasterDeck();
        }
    }

    public void refreshHandLayout() {
        if (AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            return;
        }
        for (final AbstractOrb o : AbstractEnemyDuelist.enemyDuelist.orbs) {
            o.hideEvokeValues();
        }
        for (final AbstractRelic r : AbstractEnemyDuelist.enemyDuelist.relics) {
            r.onRefreshHand();
        }
        AbstractCard hoveredcard = null;
        for (int i = 0; i < this.group.size(); ++i) {
            final AbstractCard c = this.group.get(i);
            final AbstractEnemyDuelistCard holder = AbstractEnemyDuelist.fromCard(c);
            if (EnemyCardGroup.hov2holder == null || !EnemyCardGroup.hov2holder.equals(holder)) {
                holder.cardBase.targetDrawScale = 0.35f;
                Util.log("Resetting cardBase draw scale to 0.35f - B");
            }
            final int cardsinrow = Math.min(this.group.size() - 10 * (int)Math.floor(i / 10.0f), 10);
            final float widthspacing = AbstractCard.IMG_WIDTH_S + 100.0f * Settings.scale;
            holder.cardBase.target_x = Settings.WIDTH * 0.9f - (cardsinrow + 0.5f) * (widthspacing * 0.35f) + widthspacing * 0.35f * (i % 10);
            holder.cardBase.target_y = Settings.HEIGHT * 0.56f + AbstractCard.IMG_HEIGHT_S * 0.35f * ((float)Math.floor(i / 10.0f) + ((this.group.size() > 10) ? 0.0f : 1.0f));
            if (holder.hov2 && holder.cardBase.hb.hovered && (hoveredcard == null || holder.equals(EnemyCardGroup.hov2holder))) {
                hoveredcard = holder.cardBase;
            }
        }
        if (hoveredcard != null) {
            this.hoverCardPush(hoveredcard);
        }
    }

    public void glowCheck() {
        for (final AbstractCard c : this.group) {
            if (c.canUse(AbstractDungeon.player, AbstractEnemyDuelist.enemyDuelist) && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.HAND_SELECT) {
                c.beginGlowing();
            }
            else {
                c.stopGlowing();
            }
            c.triggerOnGlowCheck();
        }
        AbstractDungeon.actionManager.addToTop(new EnemyDrawActualCardsAction(this.owner, new ArrayList<>()));
    }

    public int getCardNumber(final AbstractCard c) {
        for (int i = 0; i < this.group.size(); ++i) {
            if (c.equals(this.group.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<AbstractCard> getCardRow(final AbstractCard c) {
        final int cardNum = this.getCardNumber(c);
        final ArrayList<AbstractCard> cardrow = new ArrayList<AbstractCard>();
        for (int i = 10 * (int)Math.floor(cardNum / 10.0f); i < this.group.size() && i < 10 * (1 + (int)Math.floor(i / 10.0f)); ++i) {
            cardrow.add(this.group.get(i));
        }
        return cardrow;
    }

    public void hoverCardPush(final AbstractCard c) {
        final int cardNum = this.getCardNumber(c) % 10;
        final ArrayList<AbstractCard> cardrow = this.getCardRow(c);
        if (cardrow.size() > 1) {
            float pushAmt = 0.4f;
            if (cardrow.size() == 2) {
                pushAmt = 0.2f;
            }
            else if (cardrow.size() == 3 || cardrow.size() == 4) {
                pushAmt = 0.27f;
            }
            pushAmt *= 0.46666667f;
            for (int currentSlot = cardNum + 1; currentSlot < cardrow.size(); ++currentSlot) {
                final AbstractCard abstractCard3;
                final AbstractCard abstractCard = abstractCard3 = cardrow.get(currentSlot);
                abstractCard3.target_x += AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25f;
            }
            pushAmt = 0.4f;
            if (cardrow.size() == 2) {
                pushAmt = 0.2f;
            }
            else if (cardrow.size() == 3 || cardrow.size() == 4) {
                pushAmt = 0.27f;
            }
            pushAmt *= 0.46666667f;
            for (int currentSlot = cardNum - 1; currentSlot > -1 && currentSlot < cardrow.size(); --currentSlot) {
                final AbstractCard abstractCard4;
                final AbstractCard abstractCard2 = abstractCard4 = cardrow.get(currentSlot);
                abstractCard4.target_x -= AbstractCard.IMG_WIDTH_S * pushAmt;
                pushAmt *= 0.25f;
            }
        }
    }

    public AbstractEnemyDuelistCard getHighestValueCard() {
        AbstractEnemyDuelistCard r = null;
        int record = -99;
        for (final AbstractCard c : this.group) {
            final AbstractEnemyDuelistCard cc = AbstractEnemyDuelist.fromCard(c);
            if (cc.getValue() > record) {
                r = cc;
                record = cc.getValue();
            }
        }
        return r;
    }

    public AbstractEnemyDuelistCard getHighestValueCard(final AbstractCard.CardType type) {
        AbstractEnemyDuelistCard r = null;
        int record = -99;
        for (final AbstractCard c : this.group) {
            if (c.type == type) {
                final AbstractEnemyDuelistCard cc = AbstractEnemyDuelist.fromCard(c);
                if (cc.getValue() <= record) {
                    continue;
                }
                r = cc;
                record = cc.getValue();
            }
        }
        return r;
    }

    public AbstractEnemyDuelistCard getHighestUpgradeValueCard() {
        AbstractEnemyDuelistCard r = null;
        int record = -99;
        for (final AbstractCard c : this.group) {
            final AbstractEnemyDuelistCard cc = AbstractEnemyDuelist.fromCard(c);
            if (cc.getUpgradeValue() > record) {
                r = cc;
                record = cc.getValue();
            }
        }
        return r;
    }

    static {
        EnemyCardGroup.hov2holder = null;
    }
}
