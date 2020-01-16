package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;

import duelistmod.DuelistMod;

public class DuelistOrb extends AbstractOrb {

	protected int originalPassive = 0;
	protected int originalEvoke = 0;
	public boolean showInvertValue = false;
	public String inversion = "";
	public boolean triggersOnSpellcasterPuzzle = false;
	
	public boolean renderInvertText(SpriteBatch sb, boolean top)
	{
		if (this.inversion.equals("")) { this.inversion = "???"; }
		if (this.showInvertValue)
		{
			if (top) { FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont_small, this.inversion, this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET + 20.0f * Settings.scale, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale); }
			else { FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont_small, this.inversion, this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, new Color(0.2f, 1.0f, 1.0f, this.c.a), this.fontScale); }
			return true;
		}
		else { return false; }
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    
	public void onSoulChange(int newSouls, int change) { }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onLoseArtifact() { }
	
	public void onExhaust(AbstractCard c) { }
	
	public void onAddCardToHand(AbstractCard c) { }
	
	public void onShuffle() { }
	
    public int modifyShadowDamage() { return 0; }
    
    public int modifyUndeadDamage() { return 0; }
	
	public void onLoseBlock(int amt) { }
	
	public void onGainDex(int amount) { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public void onPowerApplied(AbstractPower pow) { }
	
	public void onChangeStance() { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onResummon(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummon(resummoned); }}
	
	public int modifyResummonAmt(AbstractCard resummoningCard) { return 0; }
	
	public int modifyReviveCost(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public boolean allowResummon(AbstractCard resummoningCard) { return true; }
	
	public boolean allowRevive() { return true; }
	
	public int modifyReviveListSize() { return 0; }
	
	public boolean upgradeResummon(AbstractCard resummoningCard) { return false; }
	
	public void onSynergyTribute() { }
	
	public void onDrawCard(AbstractCard drawnCard) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }

	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void showInvertValue() {
        this.showInvertValue = true;
    }
    
    public void hideInvertValues() {
        this.showInvertValue = false;
    }
    
    @Override
    public void showEvokeValue() {
        this.showEvokeValue = true;
    }
    
    @Override
    public void hideEvokeValues() {
        this.showEvokeValue = false;
    }
	
	public void lightOrbEnhance(int amt)
	{
		this.passiveAmount = this.originalPassive = this.originalPassive + amt;
		this.evokeAmount = this.originalEvoke = this.originalEvoke + amt;
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) 
		{
			this.passiveAmount += AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
	}
	
	public boolean gpcCheck()
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (p.orbs.get(0).equals(this) && p.hasRelic(GoldPlatedCables.ID)) { return true; }
		else { return false; }		
	}
	
	public boolean hasNegativeFocus()
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			if (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount < 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public int getCurrentFocus()
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			return AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public AbstractOrb makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onEvoke() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playChannelSFX() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDescription() {
		// TODO Auto-generated method stub

	}
	
	public void setOriginalValues(int passive, int evoke)
	{
		this.originalPassive = passive;
		this.originalEvoke = evoke;
	}
	
	public int getOriginalEvoke()
	{
		return originalEvoke;
	}

	public void checkFocus(boolean allowNegativeFocus) 
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalPassive > 0) || allowNegativeFocus)
			{
				this.basePassiveAmount = this.originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.basePassiveAmount = 0;
			}
			
			/*
			if ((AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) || (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount + this.originalEvoke > 0))
			{
				this.baseEvokeAmount = this.originalEvoke + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
			}
			
			else
			{
				this.baseEvokeAmount = 0;
			}
			*/
		}
		else
		{
			this.basePassiveAmount = this.originalPassive;
			//this.baseEvokeAmount = this.originalEvoke;
		}
		if (DuelistMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
			//System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalEvoke: " + originalEvoke + " :: new evoke amount: " + this.baseEvokeAmount);
		}
		applyFocus();
		updateDescription();
	}
	
	/*
	private void animationCode()
	{
		// Frost
		
			// Fields
			private float vfxTimer = 1.0F; 
			private float vfxIntervalMin = 0.15F; 
			private float vfxIntervalMax = 0.8F;	
			
			// updateAnimation()	
			applyFocus();			
			super.updateAnimation();
			this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
			this.vfxTimer -= Gdx.graphics.getDeltaTime();
			if (this.vfxTimer < 0.0F) 
			{
				AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
				if (MathUtils.randomBoolean()) {
					AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
				}
				this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
			}


		// Lightning
		
			// Fields
			private float vfxTimer = 1.0F; 
			private float vfxIntervalMin = 0.15F; 
			private float vfxIntervalMax = 0.8F;
			
			// updateAnimation()		
			applyFocus();
			super.updateAnimation();
			this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
			this.vfxTimer -= Gdx.graphics.getDeltaTime();
			if (this.vfxTimer < 0.0F) 
			{
				AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
				if (MathUtils.randomBoolean()) {
					AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY));
				}
				this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
			}
		
		
		// Dark
		 
			// Fields
			private float vfxTimer = 0.5F; 	
			protected static final float VFX_INTERVAL_TIME = 0.25F;
		 
		 	// updateAnimation()
			applyFocus();
			super.updateAnimation();
			this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
			this.vfxTimer -= Gdx.graphics.getDeltaTime();
			if (this.vfxTimer < 0.0F) 
			{
				AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
				this.vfxTimer = 0.25F;
			}	
		
			
		// Plasma
		
			// Fields
			private float vfxTimer = 1.0F;
			private float vfxIntervalMin = 0.1F;
			private float vfxIntervalMax = 0.4F;
			
			// updateAnimation()
			applyFocus();
			super.updateAnimation();
			this.angle += Gdx.graphics.getDeltaTime() * 45.0F;
	
			this.vfxTimer -= Gdx.graphics.getDeltaTime();
			if (this.vfxTimer < 0.0F) 
			{
				AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
				this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
			}
	*/
	
	
	

}
