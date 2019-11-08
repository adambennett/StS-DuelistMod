package duelistmod.speedster.SpeedsterVFX.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.*;

import basemod.ReflectionHacks;

public class MoveImageWithSparkleEffect extends AbstractGameEffect {
    private Texture img;
    private float sX;
    private float sY;
    private float cX;
    private float cY;
    private float dX;
    private float dY;
    private float yOffset;
    private float bounceHeight;
    private static final float DUR = 0.5F;
    private boolean playedSfx = false;
    private float sparkleTimer = 0.0F;
    private String soundKey;
    private AbstractGameEffect finalEffect;

    public MoveImageWithSparkleEffect(float srcX, float srcY, float destX, float destY, Texture img, Color col, String soundKey, AbstractGameEffect finalEffect) {
        this.img = img;
        this.sX = srcX;
        this.sY = srcY;
        this.cX = this.sX;
        this.cY = this.sY;
        this.dX = destX;
        this.dY = destY;
        this.rotation = 0.0F;
        this.duration = DUR;
        this.color = col;
        this.soundKey = soundKey;
        this.finalEffect = finalEffect;
        if (this.sY > this.dY) {
            this.bounceHeight = 600.0F * Settings.scale;
        } else {
            this.bounceHeight = this.dY - this.sY + 600.0F * Settings.scale;
        }
    }

    public void update() {
        if (!this.playedSfx) {
            this.playedSfx = true;
            if(soundKey != null) {
                CardCrawlGame.sound.playA(soundKey, MathUtils.random(-0.5F, 1.0F));
            }
        }
        this.sparkleTimer -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.4F && this.sparkleTimer < 0.0F) {
            ShineSparkleEffect tmp;
            for (int i = 0; i < MathUtils.random(2, 5); i++) {
                tmp = new ShineSparkleEffect(this.cX, this.cY + this.yOffset);
                Color tmp2 = color.cpy();
                tmp2.a = 0.0f;
                ReflectionHacks.setPrivateInherited(tmp, ShineSparkleEffect.class, "color", tmp2);
                AbstractDungeon.effectsQueue.add(tmp);
            }
            this.sparkleTimer = MathUtils.random(0.005F, 0.01F);
        }
        this.cX = Interpolation.linear.apply(this.dX, this.sX, this.duration / 0.5F);
        this.cY = Interpolation.linear.apply(this.dY, this.sY, this.duration / 0.5F);
        if (this.dX > this.sX) {
            this.rotation -= Gdx.graphics.getDeltaTime() * 1000.0F;
        } else {
            this.rotation += Gdx.graphics.getDeltaTime() * 1000.0F;
        }
        if (this.duration > 0.25F) {
            this.color.a = Interpolation.exp5In.apply(1.0F, 0.0F, (this.duration - 0.25F) / 0.2F) * Settings.scale;
            this.yOffset = Interpolation.circleIn.apply(this.bounceHeight, 0.0F, (this.duration - 0.25F) / 0.25F) * Settings.scale;
        } else {
            this.yOffset = Interpolation.circleOut.apply(0.0F, this.bounceHeight, this.duration / 0.25F) * Settings.scale;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            if(finalEffect != null) {
                AbstractDungeon.effectsQueue.add(finalEffect);
            }
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.4F, 1.0F, 1.0F, this.color.a / 5.0F));
        sb.setColor(this.color);
        sb.draw(img, this.cX, this.cY + this.yOffset);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
