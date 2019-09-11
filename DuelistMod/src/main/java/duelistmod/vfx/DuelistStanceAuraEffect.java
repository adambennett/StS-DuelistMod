package duelistmod.vfx;

import com.megacrit.cardcrawl.vfx.*;

import duelistmod.patches.AbstractStanceEnum;

import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;

public class DuelistStanceAuraEffect extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private TextureAtlas.AtlasRegion img;
    public static boolean switcher;
    
    public DuelistStanceAuraEffect(final AbstractStance.StanceName name) {
        this.img = ImageMaster.EXHAUST_L;
        this.duration = 2.0f;
        this.scale = MathUtils.random(2.7f, 2.5f) * Settings.scale;
        if (name == AbstractStance.StanceName.WRATH) {
            this.color = new Color(MathUtils.random(0.6f, 0.7f), MathUtils.random(0.0f, 0.1f), MathUtils.random(0.1f, 0.2f), 0.0f);
        }
        else if (name == AbstractStance.StanceName.CALM) {
            this.color = new Color(MathUtils.random(0.5f, 0.55f), MathUtils.random(0.6f, 0.7f), 1.0f, 0.0f);
        }
        else if (name == AbstractStanceEnum.SAMURAI) {
            this.color = Color.FIREBRICK;
        }
        else {
            this.color = new Color(MathUtils.random(0.6f, 0.7f), MathUtils.random(0.0f, 0.1f), MathUtils.random(0.6f, 0.7f), 0.0f);
        }
        this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 16.0f, AbstractDungeon.player.hb.width / 16.0f);
        this.y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 16.0f, AbstractDungeon.player.hb.height / 12.0f);
        this.x -= this.img.packedWidth / 2.0f;
        this.y -= this.img.packedHeight / 2.0f;
        DuelistStanceAuraEffect.switcher = !DuelistStanceAuraEffect.switcher;
        this.renderBehind = true;
        this.rotation = MathUtils.random(360.0f);
        if (DuelistStanceAuraEffect.switcher) {
            this.renderBehind = true;
            this.vY = MathUtils.random(0.0f, 40.0f);
        }
        else {
            this.renderBehind = false;
            this.vY = MathUtils.random(0.0f, -40.0f);
        }
    }
    
    @Override
    public void update() {
        if (this.duration > 1.0f) {
            this.color.a = Interpolation.fade.apply(0.3f, 0.0f, this.duration - 1.0f);
        }
        else {
            this.color.a = Interpolation.fade.apply(0.0f, 0.3f, this.duration);
        }
        this.rotation += Gdx.graphics.getDeltaTime() * this.vY;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }
    
    @Override
    public void dispose() {
    }
    
    static {
        DuelistStanceAuraEffect.switcher = true;
    }
}
