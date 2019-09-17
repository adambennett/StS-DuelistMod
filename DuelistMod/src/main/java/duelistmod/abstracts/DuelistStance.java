package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.stances.*;

import duelistmod.helpers.Util;
import duelistmod.stances.*;

public abstract class DuelistStance extends AbstractStance 
{
    public DuelistStance() {
        this.tips = new ArrayList<PowerTip>();
        this.c = Color.WHITE.cpy();
        this.img = null;
        this.particleTimer = 0.0f;
        this.particleTimer2 = 0.0f;
    }
    
    public abstract void updateDescription();
    
    public void onShuffle() { }
    
    public void onAddCardToHand(AbstractCard c) { }
    
    public void atStartOfTurn() {
    }
    
    public void onEndOfTurn() {
    }
    
    public void onEnterStance() {
    }
    
    public void onDrawCard(AbstractCard drawnCard) { }
    
    public void onLoseBlock(int amt) { }
    
    public void onExitStance() {
    }
    
    public void onGainDex(int amount) { }
    
    public void onExhaust(final AbstractCard c) { }
    
    public float atDamageGive(final float damage, final DamageInfo.DamageType type) {
        return damage;
    }
    
    public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {
        return damage;
    }
    
    public float modifyBlock(final float blockAmount) {
        return blockAmount;
    }
    
    public void onPlayCard(final AbstractCard card) {
    }
    
    public void update() {
        this.updateAnimation();
    }
    
    public void updateAnimation() {
    }
    
    public void render(final SpriteBatch sb) 
    {
        if (this.img == null) {
            return;
        }
        sb.setColor(this.c);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, AbstractDungeon.player.drawX - 256.0f + AbstractDungeon.player.animX, AbstractDungeon.player.drawY - 256.0f + AbstractDungeon.player.animY + AbstractDungeon.player.hb_h / 2.0f, 256.0f, 256.0f, 512.0f, 512.0f, Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false, false);
        sb.setBlendFunction(770, 771);
    }
    
    public void stopIdleSfx() {
    }
    
    public static AbstractStance getStanceFromName(String name) {
        switch (name) {
            case "Calm": {
                return new CalmStance();
            }
            case "Divinity": {
                return new DivinityStance();
            }
            case "Neutral": {
                return new NeutralStance();
            }
            case "None": {
            	return new NeutralStance();
            }
            case "Wrath": {
                return new WrathStance();
            }
            case "Samurai": {
            	return new Samurai();
            }
            case "Forsaken": {
            	return new Forsaken();
            }
            case "Guarded": {
            	return new Guarded();
            }
            case "Meditative": {
            	return new Meditative();
            }
            case "Spectral": {
            	return new Spectral();
            }
            case "Chaotic": {
            	return new Chaotic();
            }            
            case "Entrenched": {
            	return new Entrenched();
            }
            case "Nimble": {
            	return new Nimble();
            }
            default: {
                Util.log("[ERROR] Unknown stance: " + name + " called for in getStanceFromName()");
                return new DivinityStance();
            }
        }
    }
  
    public enum StanceName
    {
        CALM, 
        DIVINITY, 
        WRATH, 
        SAMURAI,
        GUARDED,
        FORSAKEN,
        CHAOTIC,
        SPECTRAL,
        MEDITATIVE,
        ENTRENCHED,
        NIMBLE,
        NONE;
    }
}
