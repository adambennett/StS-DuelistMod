package duelistmod.ui.buttons;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;

public class TestNewSelectButton
{
    private static final int W = 512;
    private static final int H = 256;
    private static final Color HOVER_BLEND_COLOR;
    private static final Color TEXT_DISABLED_COLOR;
    private static final float SHOW_X;
    private static final float DRAW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private float controller_offset_x;
    private boolean isHidden;
    public boolean isDisabled;
    public boolean isHovered;
    private float glowAlpha;
    private Color glowColor;
    private String buttonText;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;
    
    public TestNewSelectButton(final String label) {
        this.current_x = TestNewSelectButton.HIDE_X;
        this.target_x = this.current_x;
        this.controller_offset_x = 0.0f;
        this.isHidden = true;
        this.isDisabled = true;
        this.isHovered = false;
        this.glowAlpha = 0.0f;
        this.glowColor = Color.WHITE.cpy();
        this.buttonText = "NOT_SET";
        this.hb = new Hitbox(0.0f, 0.0f, TestNewSelectButton.HITBOX_W, TestNewSelectButton.HITBOX_H);
        this.updateText(label);
        this.hb.move(TestNewSelectButton.SHOW_X + 106.0f * Settings.scale, TestNewSelectButton.DRAW_Y + 120.0f * Settings.scale);
    }
    
    public void updateText(final String label) {
        this.buttonText = label;
        this.controller_offset_x = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, label, 99999.0f, 0.0f) / 2.0f;
    }
    
    public void update() {
        if (!this.isHidden) {
            this.updateGlow();
            this.hb.update();
            if (InputHelper.justClickedLeft && this.hb.hovered && !this.isDisabled) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if (this.hb.justHovered && !this.isDisabled) {
                CardCrawlGame.sound.play("UI_HOVER");
            }
            this.isHovered = this.hb.hovered;
            if (CInputActionSet.proceed.isJustPressed()) {
                CInputActionSet.proceed.unpress();
                this.hb.clicked = true;
            }
        }
        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0f);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }
    }
    
    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0f;
        if (this.glowAlpha < 0.0f) {
            this.glowAlpha *= -1.0f;
        }
        final float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0f) {
            this.glowColor.a = -tmp / 2.0f + 0.3f;
        }
        else {
            this.glowColor.a = tmp / 2.0f + 0.3f;
        }
    }
    
    public void hideInstantly() {
        this.current_x = TestNewSelectButton.HIDE_X;
        this.target_x = TestNewSelectButton.HIDE_X;
        this.isHidden = true;
    }
    
    public void hide() {
        if (!this.isHidden) {
            this.target_x = TestNewSelectButton.HIDE_X;
            this.isHidden = true;
        }
    }
    
    public void show() {
        if (this.isHidden) {
            this.glowAlpha = 0.0f;
            this.target_x = TestNewSelectButton.SHOW_X;
            this.isHidden = false;
        }
    }
    
    public void render(final SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        this.renderShadow(sb);
        sb.setColor(this.glowColor);
        this.renderOutline(sb);
        sb.setColor(Color.WHITE);
        this.renderButton(sb);
        if (this.hb.hovered && !this.isDisabled && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(TestNewSelectButton.HOVER_BLEND_COLOR);
            this.renderButton(sb);
            sb.setBlendFunction(770, 771);
        }
        if (this.isDisabled) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TestNewSelectButton.TEXT_OFFSET_X, TestNewSelectButton.DRAW_Y + TestNewSelectButton.TEXT_OFFSET_Y, TestNewSelectButton.TEXT_DISABLED_COLOR);
        }
        else if (this.hb.clickStarted) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TestNewSelectButton.TEXT_OFFSET_X, TestNewSelectButton.DRAW_Y + TestNewSelectButton.TEXT_OFFSET_Y, Color.LIGHT_GRAY);
        }
        else if (this.hb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TestNewSelectButton.TEXT_OFFSET_X, TestNewSelectButton.DRAW_Y + TestNewSelectButton.TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        }
        else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TestNewSelectButton.TEXT_OFFSET_X, TestNewSelectButton.DRAW_Y + TestNewSelectButton.TEXT_OFFSET_Y, Settings.LIGHT_YELLOW_COLOR);
        }
        this.renderControllerUi(sb);
        if (!this.isHidden) {
            this.hb.render(sb);
        }
    }
    
    private void renderShadow(final SpriteBatch sb) {
        sb.draw(ImageMaster.CONFIRM_BUTTON_SHADOW, this.current_x - 256.0f, TestNewSelectButton.DRAW_Y - 128.0f, 256.0f, 128.0f, 512.0f, 256.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 512, 256, false, false);
    }
    
    private void renderOutline(final SpriteBatch sb) {
        sb.draw(ImageMaster.CONFIRM_BUTTON_OUTLINE, this.current_x - 256.0f, TestNewSelectButton.DRAW_Y - 128.0f, 256.0f, 128.0f, 512.0f, 256.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 512, 256, false, false);
    }
    
    private void renderButton(final SpriteBatch sb) {
        sb.draw(ImageMaster.CONFIRM_BUTTON, this.current_x - 256.0f, TestNewSelectButton.DRAW_Y - 128.0f, 256.0f, 128.0f, 512.0f, 256.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 512, 256, false, false);
    }
    
    private void renderControllerUi(final SpriteBatch sb) {
        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.proceed.getKeyImg(), this.current_x - 32.0f - this.controller_offset_x + 96.0f * Settings.scale, TestNewSelectButton.DRAW_Y - 32.0f + 57.0f * Settings.scale, 32.0f, 32.0f, 64.0f, 64.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        }
    }
    
    static {
        HOVER_BLEND_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.3f);
        TEXT_DISABLED_COLOR = new Color(0.6f, 0.6f, 0.6f, 1.0f);
        SHOW_X = Settings.WIDTH - 256.0f * Settings.scale;
        DRAW_Y = 128.0f * Settings.scale;
        HIDE_X = TestNewSelectButton.SHOW_X + 400.0f * Settings.scale;
        TEXT_OFFSET_X = 136.0f * Settings.scale;
        TEXT_OFFSET_Y = 57.0f * Settings.scale;
        HITBOX_W = 300.0f * Settings.scale;
        HITBOX_H = 100.0f * Settings.scale;
    }
}
