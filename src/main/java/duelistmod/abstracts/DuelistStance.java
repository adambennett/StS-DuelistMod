package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.stances.*;

import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.stances.*;

public abstract class DuelistStance extends AbstractStance 
{
    public DuelistStance() {
        this.tips = new ArrayList<>();
        this.c = Color.WHITE.cpy();
        this.img = null;
        this.particleTimer = 0.0f;
        this.particleTimer2 = 0.0f;
    }
    
    protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public DuelistConfigurationData getConfigurations() { return null; }

    public void LINEBREAK() {
        DuelistMod.linebreak();
    }

    public void LINEBREAK(int extra) {
        DuelistMod.linebreak(extra);
    }

    public void RESET_Y() {
        DuelistMod.yPos = DuelistMod.startingYPos;
    }
    
	public void onSoulChange(int newSouls, int change) { }
    
    public void onLoseArtifact() { }
    
    public float modifyBlock(final float blockAmount, AbstractCard card) { return blockAmount; }
    
    public void onAddCardToHand(AbstractCard c) { }
    
    public void onDrawCard(AbstractCard drawnCard) { }
    
    public void onExhaust(final AbstractCard c) { }
    
    public int modifyShadowDamage() { return 0; }
    
    public int modifyUndeadDamage() { return 0; }
    
    public void onGainDex(int amount) { }
    
    public void onIncrement(int amount, int newMaxSummons) { }
    
    public void onLoseBlock(int amt) { }
    
    public void onPlayCard(final AbstractCard card) { }
    
    public void onResummon(DuelistCard resummoned) { }
    
	public void onResummon(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummon(resummoned); }}
    
    public int modifyResummonAmt(AbstractCard resummoningCard) { return 0; }
    
	public int modifyReviveCost(ArrayList<AbstractCard> entombedList) { return 0; }
    
    public boolean allowResummon(AbstractCard resummoningCard) { return true; }
    
	public boolean allowRevive() { return true; }
	
	public int modifyReviveListSize() { return 0; }
    
	public boolean upgradeResummon(AbstractCard resummoningCard) { return false; }
   
    public void onShuffle() { }
    
    public void onOverflow(int amt) { }
    
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
    
    public void onSummon(DuelistCard summoned, int amountSummoned) { }
    
    public void onSynergyTribute() { }
   
    public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
    
    public void onEnemyUseCard(final AbstractCard card) { }
    
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }

	public float modifyEntomb(float magicAmt) { return magicAmt; }
	
	public float modifyEntomb(float magicAmt, AbstractCard card) { return this.modifyEntomb(magicAmt); }
	
	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }

    public boolean modifyCanUse(final AbstractCreature p, final DuelistCard card) { return true; }

    public String cannotUseMessage(final AbstractPlayer p, final AbstractMonster m, final DuelistCard card) { return "Cannot use due to current Stance!"; }

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
            case "Unstable": {
                return new Unstable();
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
            case "theDuelist:Unstable": {
            	return new Unstable();
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
