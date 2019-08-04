package duelistmod.abstracts;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.DuelistMod;

public class DuelistOrb extends AbstractOrb {

	protected int originalPassive = 0;
	protected int originalEvoke = 0;
	public boolean triggersOnSpellcasterPuzzle = false;
	
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
