package duelistmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

public class GenericCancelButton {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final Color HOVER_BLEND_COLOR;
    private static final float SHOW_X;
    private static final float DRAW_Y;
    public static final float HIDE_X;
    public float current_x;
    private float target_x;
    public boolean isHidden;
    private float glowAlpha;
    private final Color glowColor;
    public String buttonText;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;
    public final CancelButtonLogic closeFunction;

    public GenericCancelButton(CancelButtonLogic closeFunction) {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.isHidden = true;
        this.glowAlpha = 0.0F;
        this.glowColor = Settings.GOLD_COLOR.cpy();
        this.buttonText = "NOT_SET";
        this.closeFunction = closeFunction;
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move(SHOW_X - 106.0F * Settings.scale, DRAW_Y + 60.0F * Settings.scale);
    }

    public void update() {
        if (!this.isHidden) {
            this.updateGlow();
            this.hb.update();
            if (InputHelper.justClickedLeft && this.hb.hovered) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.clicked || (InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed()) && this.current_x != HIDE_X) {

                if (this.closeFunction != null) {
                    this.closeFunction.run();
                }
                InputHelper.pressedEscape = false;
                this.hb.clicked = false;
                this.hide();
            }
        }

        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }

    }

    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }

        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }

    }

    public boolean hovered() {
        return this.hb.hovered;
    }

    public void hide() {
        if (!this.isHidden) {
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.target_x = HIDE_X;
            this.isHidden = true;
        }

    }

    public void show(String buttonText) {
        if (this.isHidden) {
            this.glowAlpha = 0.0F;
            this.current_x = HIDE_X;
            this.target_x = SHOW_X;
            this.isHidden = false;
        } else {
            this.current_x = HIDE_X;
        }
        this.buttonText = buttonText;

        this.hb.hovered = false;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        this.renderShadow(sb);
        sb.setColor(this.glowColor);
        this.renderOutline(sb);
        sb.setColor(Color.WHITE);
        this.renderButton(sb);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            this.renderButton(sb);
            sb.setBlendFunction(770, 771);
        }

        Color tmpColor = Settings.LIGHT_YELLOW_COLOR;
        if (this.hb.clickStarted) {
            tmpColor = Color.LIGHT_GRAY;
        }

        if (Settings.isControllerMode) {
            FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X - 30.0F * Settings.scale, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        }

        this.renderControllerUi(sb);
        if (!this.isHidden) {
            this.hb.render(sb);
        }

    }

    private void renderShadow(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_SHADOW, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderOutline(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_OUTLINE, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderButton(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderControllerUi(SpriteBatch sb) {
        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel.getKeyImg(), this.current_x - 32.0F - 210.0F * Settings.scale, DRAW_Y - 32.0F + 57.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Cancel Button");
        TEXT = uiStrings.TEXT;
        HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);
        SHOW_X = 256.0F * Settings.scale;
        DRAW_Y = 128.0F * Settings.scale;
        HIDE_X = SHOW_X - 400.0F * Settings.scale;
        TEXT_OFFSET_X = -136.0F * Settings.scale;
        TEXT_OFFSET_Y = 57.0F * Settings.scale;
        HITBOX_W = 300.0F * Settings.scale;
        HITBOX_H = 100.0F * Settings.scale;
    }

}
