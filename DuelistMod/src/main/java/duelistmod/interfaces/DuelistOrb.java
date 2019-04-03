package duelistmod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.DuelistMod;

public class DuelistOrb extends AbstractOrb {

	protected static int originalPassive = 0;
	protected static int originalEvoke = 0;
	
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
		originalPassive = passive;
		originalEvoke = evoke;
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
	
	
	public int getOriginalEvoke()
	{
		return originalEvoke;
	}

	public void checkFocus() 
	{
		if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
		{
			this.basePassiveAmount = originalPassive + AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
		}
		else
		{
			this.basePassiveAmount = originalPassive;
		}
		if (DuelistMod.debug)
		{
			System.out.println("theDuelist:DuelistOrb:checkFocus() ---> Orb: " + this.name + " originalPassive: " + originalPassive + " :: new passive amount: " + this.basePassiveAmount);
		}
		applyFocus();
		updateDescription();
	}

}
