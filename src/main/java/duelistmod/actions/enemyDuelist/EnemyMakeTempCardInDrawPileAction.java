package duelistmod.actions.enemyDuelist;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.vfx.enemy.EnemyShowCardAndAddToDrawPileEffect;

public class EnemyMakeTempCardInDrawPileAction extends AbstractGameAction {
    private final AbstractCard cardToMake;
    private final boolean randomSpot;
    private final boolean autoPosition;
    private final boolean toBottom;
    private final float x;
    private final float y;

    public EnemyMakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom, float cardX, float cardY) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.autoPosition = autoPosition;
        this.toBottom = toBottom;
        this.x = cardX;
        this.y = cardY;
    }

    public EnemyMakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean randomSpot, boolean autoPosition, boolean toBottom) {
        this(card, amount, randomSpot, autoPosition, toBottom, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
    }

    public EnemyMakeTempCardInDrawPileAction(AbstractCard card, int amount, boolean shuffleInto, boolean autoPosition) {
        this(card, amount, shuffleInto, autoPosition, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractCard c;
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractEnemyDuelist.enemyDuelist.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }

                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDrawPileEffect(c, this.x, this.y, this.randomSpot, this.autoPosition, this.toBottom));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    if (c.type != AbstractCard.CardType.CURSE && c.type != AbstractCard.CardType.STATUS && AbstractEnemyDuelist.enemyDuelist.hasPower("MasterRealityPower")) {
                        c.upgrade();
                    }

                    AbstractDungeon.effectList.add(new EnemyShowCardAndAddToDrawPileEffect(c, this.randomSpot, this.toBottom));
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
