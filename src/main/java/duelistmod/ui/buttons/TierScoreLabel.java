package duelistmod.ui.buttons;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;

public class TierScoreLabel {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int W = 512;
    private static final int H = 256;

    private float showTimer;
    private float current_x;
    private float target_x;
    private boolean isHidden;
    private Color textColor;
    private Color btnColor;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    private final float HIDE_X;
    private final float SHOW_X;
    private final float SHOW_Y;
    public AbstractCard card;
    public int tierScore;

    public TierScoreLabel(DuelistCard card, int score, int rewardIndex) {
        this.showTimer = -1;
        this.card = card;
        switch (rewardIndex) {
            case 0:
                this.SHOW_X = card.target_x + (Settings.scale * 280);
                break;
            case 1:
                this.SHOW_X = card.target_x + (rewardIndex * Settings.scale * 330) + (Settings.scale * 280) + (Settings.scale * 10);
                break;
            case 2:
                this.SHOW_X = card.target_x + (rewardIndex * Settings.scale * 330) + (Settings.scale * 280) + (Settings.scale * 20);
                break;
            case 3:
                this.SHOW_X = card.target_x + (rewardIndex * Settings.scale * 330) + (Settings.scale * 280) + (Settings.scale * 35);
                break;
            default:
                this.SHOW_X = card.target_x + (rewardIndex * Settings.scale * 330) + (Settings.scale * 280) + (Settings.scale * 45);
                break;
        }
        this.SHOW_Y = card.target_y + (Settings.scale * 280);
        this.HIDE_X = (float) Settings.WIDTH / 2.0F;
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.isHidden = true;
        this.textColor = Color.WHITE.cpy();
        this.btnColor = Color.RED.cpy();
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move(SHOW_X, SHOW_Y);
        Util.log("Label for " + card.cardID + ", SHOWX:" + SHOW_X + ", SHOWY:" + SHOW_Y);
        this.tierScore = score;
    }

    public void update() {
        if (showTimer >= 0)
        {
            showTimer -= Gdx.graphics.getDeltaTime();
            if (showTimer < 0)
            {
                this.isHidden = false;
            }
        }
        else if (!this.isHidden)
        {
            this.hb.update();
            if (this.hb.justHovered)
            {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.hovered && InputHelper.justClickedLeft)
            {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                Util.log("Clicked tier score label");
            }

            if (this.hb.clicked || InputActionSet.cancel.isJustPressed() || CInputActionSet.cancel.isJustPressed())
            {
                this.hb.clicked = false;
            }

            if (this.current_x != this.target_x)
            {
                this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
                if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD)
                {
                    this.current_x = this.target_x;
                    this.hb.move(this.current_x, SHOW_Y);
                }
            }

            this.textColor.a = MathHelper.fadeLerpSnap(this.textColor.a, 1.0F);
            this.btnColor.a = this.textColor.a;
        }
    }

    public void hideInstantly() {
        this.current_x = HIDE_X;
        this.target_x = HIDE_X;
        this.isHidden = true;
        this.textColor.a = 0.0F;
        this.btnColor.a = 0.0F;
    }

    public void hide() { this.isHidden = true; }

    public void show() {
        this.showTimer = 0.5f;
        this.textColor.a = 0.0F;
        this.btnColor.a = 0.0F;
        this.current_x = SHOW_X;
        this.target_x = SHOW_X;
        this.hb.move(SHOW_X, SHOW_Y);
        this.isHidden = false;
    }

    public void render(SpriteBatch sb) {
        if (!this.isHidden)
        {
            this.renderButton(sb);
            if (FontHelper.getSmartWidth(FontHelper.smallDialogOptionFont, "" + this.tierScore, 9999.0F, 0.0F) > 200.0F * Settings.scale)
            {
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, "" + this.tierScore, this.current_x, SHOW_Y, this.textColor, 0.8F);
            }
            else
            {
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, "" + this.tierScore, this.current_x, SHOW_Y, this.textColor);
            }
        }
    }

    private void renderButton(SpriteBatch sb) {
        float width   = 512f;
        float height  = 256f;
        float originX = 256f;
        float originY = 128f;
        float scale = Settings.scale * 0.6f;

        sb.setColor(this.btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - originX, SHOW_Y - originY, originX, originY, width, height,
                scale, scale, 0.0F, 0, 0, (int)width, (int)height, false, false);

        if (this.hb.hovered && !this.hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, this.current_x - originX, SHOW_Y - originY, originX, originY, width, height,
                    scale, scale, 0.0F, 0, 0, (int)width, (int)height, false, false);

            sb.setBlendFunction(770, 771);
        }

        this.hb.render(sb);
    }

    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("theDuelist:TierScoreLabels");
        TEXT = uiStrings.TEXT;
//        SHOW_Y = 220.0F * Settings.scale;
//        SHOW_X = (float) Settings.WIDTH / 2.0F;
//        HIDE_X = (float) Settings.WIDTH / 2.0F;
        HITBOX_W = 200.0F * Settings.scale;
        HITBOX_H = 50.0F * Settings.scale;
    }

}
