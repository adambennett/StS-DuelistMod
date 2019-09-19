package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    
    public float modifyBlock(final float blockAmount) { return blockAmount; }
    
    public void onAddCardToHand(AbstractCard c) { }
    
    public void onDrawCard(AbstractCard drawnCard) { }
    
    public void onExhaust(final AbstractCard c) { }
    
    public void onGainDex(int amount) { }
    
    public void onIncrement(int amount, int newMaxSummons) { }
    
    public void onLoseBlock(int amt) { }
    
    public void onPlayCard(final AbstractCard card) { }
    
    public void onResummon(DuelistCard resummoned) { }
   
    public void onShuffle() { }
    
    public void onSummon(DuelistCard summoned, int amountSummoned) { }
    
    public void onSynergyTribute() { }
   
    public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }

    public static AbstractStance getStanceFromName(final String name) 
    {
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
            case "theDuelist:Samurai": {
            	return new Samurai();
            }
            case "theDuelist:Forsaken": {
            	return new Forsaken();
            }
            case "theDuelist:Guarded": {
            	return new Guarded();
            }
            case "theDuelist:Meditative": {
            	return new Meditative();
            }
            case "theDuelist:Spectral": {
            	return new Spectral();
            }
            case "theDuelist:Chaotic": {
            	return new Chaotic();
            }            
            case "theDuelist:Entrenched": {
            	return new Entrenched();
            }
            case "theDuelist:Nimble": {
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
