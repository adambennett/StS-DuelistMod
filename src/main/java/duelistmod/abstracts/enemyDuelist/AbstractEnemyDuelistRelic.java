package duelistmod.abstracts.enemyDuelist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.DuelistRelic;

import java.util.Objects;

public abstract class AbstractEnemyDuelistRelic extends DuelistRelic {

    private static float START_X;
    private static float START_Y;
    private static float PAD_X;
    private AbstractEnemyDuelist owner;

    public AbstractEnemyDuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx, null);
        this.isSeen = true;
        this.refreshDescription();
    }

    public AbstractEnemyDuelistRelic(String id, String imageName, RelicTier tier, LandingSound sfx) {
        super(id, imageName, tier, sfx);
        this.isSeen = true;
        this.refreshDescription();
    }

    public void render(final SpriteBatch sb) {
        super.renderInTopPanel(sb);
        if (this.hb.hovered && !CardCrawlGame.relicPopup.isOpen) {
            if (!this.isSeen) {
                if (InputHelper.mX < 1400.0f * Settings.scale) {
                    TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[1], AbstractRelic.MSG[1]);
                }
                else {
                    TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, AbstractRelic.LABEL[1], AbstractRelic.MSG[1]);
                }
                return;
            }
            this.renderTip(sb);
        }
    }

    public void refreshDescription() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void setTexture(final Texture t) {
        this.img = t;
        this.largeImg = t;
        this.outlineImg = t;
    }

    public void instantObtain(final AbstractEnemyDuelist boss) {
        this.owner = boss;
        this.instantObtain();
    }

    public void instantObtain() {
        if (this.owner == null) {
            return;
        }
        if (Objects.equals(this.relicId, "Circlet") && this.owner.hasRelic("Circlet")) {
            final AbstractRelic circ;
            final AbstractRelic abstractRelic = (circ = this.owner.getRelic("Circlet"));
            ++abstractRelic.counter;
            circ.flash();
        }
        else {
            this.isDone = true;
            this.isObtained = true;
            this.currentX = Settings.WIDTH - AbstractEnemyDuelistRelic.START_X - this.owner.relics.size() * AbstractEnemyDuelistRelic.PAD_X;
            this.currentY = AbstractEnemyDuelistRelic.START_Y;
            this.targetX = this.currentX;
            this.targetY = this.currentY;
            this.flash();
            this.owner.relics.add(this);
            this.hb.move(this.currentX, this.currentY);
            this.onEquip();
            this.relicTip();
        }
    }

    public void obtain() {
        if (Objects.equals(this.relicId, "Circlet") && this.owner.hasRelic("Circlet")) {
            final AbstractRelic circ;
            final AbstractRelic abstractRelic = (circ = this.owner.getRelic("Circlet"));
            ++abstractRelic.counter;
            circ.flash();
            this.hb.hovered = false;
        }
        else {
            this.hb.hovered = false;
            final int row = this.owner.relics.size();
            this.targetX = Settings.WIDTH - AbstractEnemyDuelistRelic.START_X - row * AbstractRelic.PAD_X;
            this.targetY = AbstractEnemyDuelistRelic.START_Y;
            this.owner.relics.add(this);
            this.relicTip();
        }
    }

    public void obtain(final AbstractEnemyDuelist boss) {
        this.owner = boss;
        this.obtain();
    }

    public int getColumn() {
        return this.owner.relics.indexOf(this);
    }

    public void update() {
        if (!this.isDone) {
            super.update();
        }
        else {
            if (this.flashTimer != 0.0f) {
                this.flashTimer -= Gdx.graphics.getDeltaTime();
                if (this.flashTimer < 0.0f) {
                    if (this.pulse) {
                        this.flashTimer = 1.0f;
                    }
                    else {
                        this.flashTimer = 0.0f;
                    }
                }
            }
            this.hb.update();
            if (this.hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden) {
                this.scale = Settings.scale * 1.25f;
                CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            }
            else {
                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
            }
            this.updateDuelistRelicPopupClick();
        }
    }

    private void updateDuelistRelicPopupClick() {
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            this.hb.clickStarted = true;
        }
        if (this.hb.clicked || (this.hb.hovered && CInputActionSet.select.isJustPressed())) {
            CardCrawlGame.relicPopup.open(this);
            CInputActionSet.select.unpress();
            this.hb.clicked = false;
            this.hb.clickStarted = false;
        }
    }

    public void playLandingSFX() {
    }

    public boolean canSpawn() {
        return false;
    }

    public void onEnergyRecharge() {
    }

    static {
        AbstractEnemyDuelistRelic.START_X = 364.0f * Settings.scale;
        AbstractEnemyDuelistRelic.START_Y = Settings.HEIGHT - 174.0f * Settings.scale;
        AbstractEnemyDuelistRelic.PAD_X = 72.0f * Settings.scale;
    }
}
