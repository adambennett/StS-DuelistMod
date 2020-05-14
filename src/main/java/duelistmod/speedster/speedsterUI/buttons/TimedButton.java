package duelistmod.speedster.speedsterUI.buttons;

import java.util.function.Consumer;

import org.apache.commons.lang3.math.NumberUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.RarePotionParticleEffect;

import basemod.ClickableUIElement;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.SpeedsterVFX.general.ButtonConfirmedEffect;
import duelistmod.ui.TextureLoader;

public class TimedButton extends ClickableUIElement {
    public boolean isDone, ordered, lockDuration;
    public int order, totalOrder;

    protected float startingDuration, duration, sparkleTimer;
    protected Consumer<TimedButton> clickEffect;

    public TimedButton(Texture texture, float x, float y, float timer, Consumer<TimedButton> clickEffect, boolean ordered) {
        super(texture, x, y, texture.getWidth(), texture.getHeight());
        startingDuration = duration = timer;
        this.ordered = ordered;
        totalOrder = -1;
        this.clickEffect = clickEffect;
        if (ordered) {
            this.tint = Color.FIREBRICK.cpy();
        } else {
            this.tint = Color.FOREST.cpy();
        }
        isDone = false;
    }

    public TimedButton(float x, float y, float timer, Consumer<TimedButton> clickEffect, boolean ordered) {
        this(TextureLoader.getTexture(DuelistMod.makeSpeedsterPath("NormalButton.png")), x, y, timer, clickEffect, ordered);
    }

    @Override
    protected void onHover() {
        if (!ordered || order != 0) {
            tint.a = 1f;
        }
    }

    @Override
    protected void onUnhover() {
        tint.a = 0;
    }

    @Override
    protected void onClick() {
        if (!ordered || order == 0) {
            UC.doVfx(new ButtonConfirmedEffect(x, y, Color.CYAN));
            clickEffect.accept(this);
            isDone = true;
            setClickable(false);
            lockDuration = true;
        }
    }

    public float getPercentageTimeLeft() {
        return NumberUtils.max(duration / startingDuration, 0f);
    }

    public void update(int order) {
        this.order = order;
        super.update();

        if (!lockDuration) {
            duration -= UC.gt();
        }
        if (duration < 0 && !lockDuration) {
            isDone = true;
            setClickable(false);
            lockDuration = true;
        }
    }

    @Override
    public void render(SpriteBatch sb, Color col) {
        col.a = NumberUtils.min(getPercentageTimeLeft() + 0.25f, 1f);
        super.render(sb, col);

        if (ordered) {
            FontHelper.renderFontCentered(sb, FontHelper.damageNumberFont, Integer.toString(totalOrder + 1), x + (hb_w / 2f), y + (hb_h / 2f));
        }

        if (!ordered || order == 0) {
            renderParticles(sb);
        }
    }

    public void renderParticles(SpriteBatch sb) {
        this.sparkleTimer -= Gdx.graphics.getDeltaTime();
        if (this.sparkleTimer < 0.0F) {
            float xOffset = MathUtils.random(0, hb_w);
            float yOffset = MathUtils.random(0, hb_h);
            AbstractDungeon.topLevelEffects.add(new RarePotionParticleEffect(x + xOffset, y + yOffset));
            this.sparkleTimer = MathUtils.random(0.1F, 0.2F);
        }
    }

    public Hitbox getHb() {
        return this.hitbox;
    }

    public void setTotalOrder(int totalOrder) {
        if (this.totalOrder > -1 && totalOrder != this.totalOrder) {
            Util.log("Tried to assign new total order to button. From: " + this.totalOrder + " to " + totalOrder);
            return;
        }
        this.totalOrder = totalOrder;
    }
}
