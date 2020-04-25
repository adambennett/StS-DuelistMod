package duelistmod.vfx;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ResummonOrbEffect extends AbstractGameEffect
{
    private TextureAtlas.AtlasRegion img;
    private CatmullRomSpline<Vector2> crs;
    private ArrayList<Vector2> controlPoints;
    @SuppressWarnings("unused")
	private static final int TRAIL_ACCURACY = 60;
    private Vector2[] points;
    private Vector2 pos;
    private Vector2 target;
    private float currentSpeed;
    private static final float START_VELOCITY;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private float rotationRate;
    
    public ResummonOrbEffect(final float x, final float y, AbstractMonster target) {
        this.crs = new CatmullRomSpline<Vector2>();
        this.controlPoints = new ArrayList<Vector2>();
        this.points = new Vector2[60];
        this.currentSpeed = 0.0f;
        this.rotateClockwise = true;
        this.stopRotating = false;
        this.img = ImageMaster.GLOW_SPARK_2;
        this.pos = new Vector2(x, y);
        this.target = new Vector2(target.hb.cX - ResummonOrbEffect.DST_THRESHOLD / 3.0f - 100.0f * Settings.scale, target.hb.cY + MathUtils.random(-50.0f, 50.0f) * Settings.scale);
        this.crs.controlPoints = new Vector2[1];
        this.rotateClockwise = MathUtils.randomBoolean();
        this.rotation = (float)MathUtils.random(0, 359);
        this.controlPoints.clear();
        this.rotationRate = MathUtils.random(300.0f, 350.0f) * Settings.scale;
        this.currentSpeed = ResummonOrbEffect.START_VELOCITY * MathUtils.random(0.2f, 1.0f);
        this.color = new Color(0.57f, 0.14f, 1.0f, 0.4f);
        this.duration = 1.3f;
    }
    
    @Override
    public void update() {
        this.updateMovement();
    }
    
    private void updateMovement() {
        final Vector2 tmp = new Vector2(this.pos.x - this.target.x, this.pos.y - this.target.y);
        tmp.nor();
        final float targetAngle = tmp.angle();
        this.rotationRate += Gdx.graphics.getDeltaTime() * 700.0f;
        if (!this.stopRotating) {
            if (this.rotateClockwise) {
                this.rotation += Gdx.graphics.getDeltaTime() * this.rotationRate;
            }
            else {
                this.rotation -= Gdx.graphics.getDeltaTime() * this.rotationRate;
                if (this.rotation < 0.0f) {
                    this.rotation += 360.0f;
                }
            }
            this.rotation %= 360.0f;
            if (!this.stopRotating) {
                if (this.target.dst(this.pos) < ResummonOrbEffect.HOME_IN_THRESHOLD) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                }
                else if (Math.abs(this.rotation - targetAngle) < Gdx.graphics.getDeltaTime() * this.rotationRate) {
                    this.rotation = targetAngle;
                    this.stopRotating = true;
                }
            }
        }
        tmp.setAngle(this.rotation);
        final Vector2 vector2 = tmp;
        vector2.x *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        final Vector2 vector3 = tmp;
        vector3.y *= Gdx.graphics.getDeltaTime() * this.currentSpeed;
        this.pos.sub(tmp);
        if (this.stopRotating) {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * ResummonOrbEffect.VELOCITY_RAMP_RATE * 3.0f;
        }
        else {
            this.currentSpeed += Gdx.graphics.getDeltaTime() * ResummonOrbEffect.VELOCITY_RAMP_RATE * 1.5f;
        }
        if (this.currentSpeed > ResummonOrbEffect.MAX_VELOCITY) {
            this.currentSpeed = ResummonOrbEffect.MAX_VELOCITY;
        }
        if ((this.target.x < Settings.WIDTH / 2.0f && this.pos.x < 0.0f) || (this.target.x > Settings.WIDTH / 2.0f && this.pos.x > Settings.WIDTH) || this.target.dst(this.pos) < ResummonOrbEffect.DST_THRESHOLD) {
            this.isDone = true;
            return;
        }
        if (!this.controlPoints.isEmpty()) {
            if (!this.controlPoints.get(0).equals(this.pos)) {
                this.controlPoints.add(this.pos.cpy());
            }
        }
        else {
            this.controlPoints.add(this.pos.cpy());
        }
        if (this.controlPoints.size() > 3) {
            final Vector2[] vec2Array = new Vector2[0];
            this.crs.set(this.controlPoints.toArray(vec2Array), false);
            for (int i = 0; i < 60; ++i) {
                this.points[i] = new Vector2();
                this.crs.valueAt(this.points[i], i / 59.0f);
            }
        }
        if (this.controlPoints.size() > 10) {
            this.controlPoints.remove(0);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        if (!this.isDone) {
            sb.setBlendFunction(770, 1);
            sb.setColor(this.color);
            float scale = Settings.scale * 1.5f;
            for (int i = this.points.length - 1; i > 0; --i) {
                if (this.points[i] != null) {
                    sb.draw(this.img, this.points[i].x - this.img.packedWidth / 2, this.points[i].y - this.img.packedHeight / 2, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, scale, scale, this.rotation);
                    scale *= 0.975f;
                }
            }
            sb.setBlendFunction(770, 771);
        }
    }
    
    @Override
    public void dispose() {
    }
    
    static {
        START_VELOCITY = 100.0f * Settings.scale;
        MAX_VELOCITY = 5000.0f * Settings.scale;
        VELOCITY_RAMP_RATE = 2000.0f * Settings.scale;
        DST_THRESHOLD = 36.0f * Settings.scale;
        HOME_IN_THRESHOLD = 36.0f * Settings.scale;
    }
}
