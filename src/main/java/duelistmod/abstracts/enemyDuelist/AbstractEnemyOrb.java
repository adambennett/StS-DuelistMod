package duelistmod.abstracts.enemyDuelist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.orbs.enemy.EnemyEmptyOrbSlot;

public abstract class AbstractEnemyOrb extends DuelistOrb {

    public boolean showValues;
    public boolean evokeOverride;
    public boolean pretendLockOn;
    public int evokeMult;
    public int pretendFocus;
    public static int masterPretendFocus;

    public AbstractEnemyOrb() {
        this.showValues = true;
        this.evokeOverride = false;
        this.pretendLockOn = false;
        this.evokeMult = 0;
        this.pretendFocus = AbstractEnemyOrb.masterPretendFocus;
    }

    public void update() {
        this.hb.update();
        if (this.hb.hovered) {
            if (InputHelper.mX < 1400.0f * Settings.scale) {
                TipHelper.renderGenericTip(InputHelper.mX + 60.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, this.name, this.description);
            }
            else {
                TipHelper.renderGenericTip(InputHelper.mX - 350.0f * Settings.scale, InputHelper.mY - 50.0f * Settings.scale, this.name, this.description);
            }
        }
        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7f);
    }

    public void setSlot(final int slotNum, final int maxOrbs) {
        final float dist = 160.0f * Settings.scale + maxOrbs * 10.0f * Settings.scale;
        float angle = 100.0f + maxOrbs * 12.0f;
        final float offsetAngle = angle / 2.0f;
        angle *= slotNum / (maxOrbs - 1.0f);
        angle += 90.0f - offsetAngle;
        this.tX = dist * MathUtils.cosDeg(angle) + AbstractEnemyDuelist.enemyDuelist.drawX;
        this.tY = -80.0f * Settings.scale + dist * MathUtils.sinDeg(angle) + AbstractEnemyDuelist.enemyDuelist.drawY + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f;
        if (maxOrbs == 1) {
            this.tX = AbstractEnemyDuelist.enemyDuelist.drawX;
            this.tY = 160.0f * Settings.scale + AbstractEnemyDuelist.enemyDuelist.drawY + AbstractEnemyDuelist.enemyDuelist.hb_h / 2.0f;
        }
        this.hb.move(this.tX, this.tY);
    }

    public void updateAnimation() {
        this.bobEffect.update();
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            this.cX = MathHelper.orbLerpSnap(this.cX, AbstractEnemyDuelist.enemyDuelist.animX + this.tX);
            //noinspection SuspiciousNameCombination
            this.cY = MathHelper.orbLerpSnap(this.cY, AbstractEnemyDuelist.enemyDuelist.animY + this.tY);
            if (this.channelAnimTimer != 0.0f) {
                this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
                if (this.channelAnimTimer < 0.0f) {
                    this.channelAnimTimer = 0.0f;
                }
            }
            this.c.a = Interpolation.pow2In.apply(1.0f, 0.01f, this.channelAnimTimer / 0.5f);
            this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01f, this.channelAnimTimer / 0.5f);
        }
    }

    public void applyFocus() {
        if (AbstractEnemyDuelist.enemyDuelist.hasPower("Focus")) {
            final AbstractPower power = AbstractEnemyDuelist.enemyDuelist.getPower("Focus");
            this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount + this.pretendFocus);
            this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount + this.pretendFocus);
        }
        else {
            this.passiveAmount = this.basePassiveAmount + this.pretendFocus;
            this.evokeAmount = this.baseEvokeAmount + this.pretendFocus;
        }
    }

    protected void renderText(final SpriteBatch sb) {
        if (!(this instanceof EnemyEmptyOrbSlot) && this.showValues) {
            if (this.showEvokeValue || this.evokeOverride) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, (this.evokeMult > 0) ? (this.evokeAmount + "x" + this.evokeMult) : Integer.toString(this.evokeAmount), this.cX + AbstractEnemyOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractEnemyOrb.NUM_Y_OFFSET, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale);
            }
            else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + AbstractEnemyOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractEnemyOrb.NUM_Y_OFFSET, this.c, this.fontScale);
            }
        }
    }

    static {
        AbstractEnemyOrb.masterPretendFocus = 0;
    }
}
