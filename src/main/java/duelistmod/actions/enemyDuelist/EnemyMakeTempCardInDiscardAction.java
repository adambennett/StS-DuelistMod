package duelistmod.actions.enemyDuelist;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.vfx.enemy.EnemyShowCardAndAddToDiscardEffect;

public class EnemyMakeTempCardInDiscardAction extends AbstractGameAction {
    private final AbstractCard c;
    private final int numCards;
    private boolean sameUUID;
    private final AbstractEnemyDuelist duelist;

    public EnemyMakeTempCardInDiscardAction(AbstractEnemyDuelist duelist, AbstractCard card, int amount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.numCards = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.c = card;
        this.sameUUID = false;
        this.duelist = duelist;
    }

    public EnemyMakeTempCardInDiscardAction(AbstractEnemyDuelist duelist, AbstractCard card, boolean sameUUID) {
        this(duelist, card, 1);
        this.sameUUID = sameUUID;
        if (!sameUUID && this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && this.duelist.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }

    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.numCards < 6) {
                for(int i = 0; i < this.numCards; ++i) {
                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDiscardEffect(this.duelist, this.makeNewCard()));
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }

    private AbstractCard makeNewCard() {
        return this.sameUUID ? this.c.makeSameInstanceOf() : this.c.makeStatEquivalentCopy();
    }
}

