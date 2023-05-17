package duelistmod.vfx.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class EnemyShowCardAndAddToDrawPileEffect extends AbstractGameEffect {
    private final AbstractCard card;
    private static final float PADDING;
    private boolean cardOffset;

    public EnemyShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset, boolean toBottom) {
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.cardOffset = cardOffset;
        this.duration = 1.5F;
        if (cardOffset) {
            this.identifySpawnLocation(x, y);
        } else {
            this.card.target_x = x;
            this.card.target_y = y;
        }

        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        if (this.card.type != AbstractCard.CardType.CURSE && this.card.type != AbstractCard.CardType.STATUS && AbstractEnemyDuelist.enemyDuelist.hasPower("MasterRealityPower")) {
            this.card.upgrade();
        }

        CardCrawlGame.sound.play("CARD_OBTAIN");
        if (toBottom) {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToBottom(this.card);
        } else if (randomSpot) {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToRandomSpot(this.card);
        } else {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToTop(this.card);
        }

    }

    public EnemyShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot, boolean cardOffset) {
        this(srcCard, x, y, randomSpot, cardOffset, false);
    }

    public EnemyShowCardAndAddToDrawPileEffect(AbstractCard srcCard, float x, float y, boolean randomSpot) {
        this(srcCard, x, y, randomSpot, false);
    }

    public EnemyShowCardAndAddToDrawPileEffect(AbstractCard srcCard, boolean randomSpot, boolean toBottom) {
        this.cardOffset = false;
        this.card = srcCard.makeStatEquivalentCopy();
        this.duration = 1.5F;
        this.card.target_x = MathUtils.random((float) Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
        this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.8F, (float)Settings.HEIGHT * 0.2F);
        this.card.drawScale = 0.01F;
        this.card.targetDrawScale = 1.0F;
        if (toBottom) {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToBottom(srcCard);
        } else if (randomSpot) {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToRandomSpot(srcCard);
        } else {
            AbstractEnemyDuelist.enemyDuelist.drawPile.addToTop(srcCard);
        }

    }

    private void identifySpawnLocation(float x, float y) {
        int effectCount = 0;
        if (this.cardOffset) {
            effectCount = 1;
        }

        for (AbstractGameEffect e : AbstractDungeon.effectList) {
            if (e instanceof ShowCardAndAddToDrawPileEffect) {
                ++effectCount;
            }
        }

        this.card.current_x = x;
        this.card.current_y = y;
        this.card.target_y = (float)Settings.HEIGHT * 0.5F;
        switch (effectCount) {
            case 0:
                this.card.target_x = (float)Settings.WIDTH * 0.5F;
                break;
            case 1:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH;
                break;
            case 2:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH;
                break;
            case 3:
                this.card.target_x = (float)Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            case 4:
                this.card.target_x = (float)Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F;
                break;
            default:
                this.card.target_x = MathUtils.random((float)Settings.WIDTH * 0.1F, (float)Settings.WIDTH * 0.9F);
                this.card.target_y = MathUtils.random((float)Settings.HEIGHT * 0.2F, (float)Settings.HEIGHT * 0.8F);
        }

    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.card.shrink();
        }

    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        PADDING = 30.0F * Settings.scale;
    }
}
