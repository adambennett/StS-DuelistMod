package duelistmod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BallRelicData {
    public int frames;
    public int delay;
    public float targetX;
    public float targetY;
    public int targetrotation;
    AbstractRelic ar;
    BallRelicPosition pos;
    AbstractCreature target;

    private float phase;
    private float amplitude;


    private float ballvarianceX;
    private float ballvarianceY;

    private float opacity;

    public BallRelicData(AbstractRelic ar, AbstractCreature target) {
        this.target = target;
        pos = new BallRelicPosition(ar.currentX, ar.currentY);

        frames = -HolyMoleyGreatBallOfRelics.gatherspeed;

        targetrotation = MathUtils.random(-720, 720);

        this.delay = MathUtils.random(HolyMoleyGreatBallOfRelics.maxdelay);

        this.phase = MathUtils.random((float) Math.PI * 2);
        this.amplitude = MathUtils.random(1F);

        this.ar = ar;

        this.opacity = 1F;
    }

    public void setBallradius(float ballradius) {
        ballvarianceX = (float) Math.sin(phase) * ballradius * amplitude;
        ballvarianceY = (float) Math.cos(phase) * ballradius * amplitude;
        targetX = AbstractDungeon.player.hb.cX + ballvarianceX;
        targetY = AbstractDungeon.player.hb.y + AbstractDungeon.player.hb.height + ballvarianceY;
    }

    public void setupDispersal() {
        this.targetX = (ballvarianceX - 0.5F) * HolyMoleyGreatBallOfRelics.CHAOS;
        this.targetY = (ballvarianceY + 0.25F) * HolyMoleyGreatBallOfRelics.CHAOS;
        this.amplitude = MathUtils.random(HolyMoleyGreatBallOfRelics.bounceplanemin, HolyMoleyGreatBallOfRelics.bounceplanemax);
        this.targetrotation = MathUtils.random(30);

        this.frames = 0;
        this.delay = 1;
    }

    public void render(SpriteBatch sb) {
        Color color = new Color(1F, 1F, 1F, opacity);
        sb.setColor(color);
        pos.render(sb, ar);
    }

    public boolean updateOne() {

        if (delay > 0) {
            delay--;
        } else if (frames < HolyMoleyGreatBallOfRelics.gatherspeed) {
            pos.x = ar.currentX + sigmoid(targetX - ar.currentX, 6F / HolyMoleyGreatBallOfRelics.gatherspeed, frames);
            pos.y = ar.currentY + sigmoid(targetY - ar.currentY, 6F / HolyMoleyGreatBallOfRelics.gatherspeed, frames);
            pos.rotation = sigmoid(targetrotation, 6F / HolyMoleyGreatBallOfRelics.gatherspeed, frames++);
        } else {
            return true;
        }
        return false;
    }

    public void updateTwo() {
        pos.x += ((target.hb.x - ballvarianceX) - targetX) / HolyMoleyGreatBallOfRelics.flighttime;
        pos.y += ((target.hb.cY - ballvarianceY) - targetY) / HolyMoleyGreatBallOfRelics.flighttime;
    }

    public boolean updateThree() {
        //The variablenames here are fucked up, because *see comment in setupDispersal()*
        this.targetX += (this.targetX > 0 ? -HolyMoleyGreatBallOfRelics.frictionX : HolyMoleyGreatBallOfRelics.frictionX);

        if (this.pos.y + this.targetY <= amplitude) {
            this.targetY = Math.abs(this.targetY);
            this.targetrotation = MathUtils.random(-30, 30);
            delay = 0; //Once they hit the floor once, I want them to start fading out.
        } else {
            this.targetY -= (this.targetY > 0 ? HolyMoleyGreatBallOfRelics.frictionY : -HolyMoleyGreatBallOfRelics.frictionY);
            this.targetY -= HolyMoleyGreatBallOfRelics.gravity;
        }
        pos.x += targetX;
        pos.y += targetY;

        if (delay == 0) {
            opacity -= HolyMoleyGreatBallOfRelics.dispersalspeed / 300F;
            return opacity <= 0F;
        }
        return false;
    }

    private float sigmoid(float endvalue, float steepness, float curval) {
        return endvalue / (1 + (float) Math.pow(Math.E, -steepness * curval));
    }
}

