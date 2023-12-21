package duelistmod.stances;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;

import java.util.ArrayList;

public class Guarded extends DuelistStance
{
	public Guarded()
	{
		this.ID = "theDuelist:Guarded";
		this.name = "Guarded";
		this.updateDescription();
	}

	@Override
	public DuelistConfigurationData getConfigurations() {
		ArrayList<IUIElement> settingElements = new ArrayList<>();
		RESET_Y();
		LINEBREAK();
		LINEBREAK();
		LINEBREAK();
		LINEBREAK();
		settingElements.add(new ModLabel("Configurations for " + this.name + " not setup yet.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
		return new DuelistConfigurationData(this.name, settingElements, this);
	}

	@Override
	public void updateDescription() 
	{
		this.description = "When you enter this stance, gain #b1 #yBlur. Upon exiting this stance, gain #yBlock equal to your #yDexterity.";
	}

	@Override
	public void updateAnimation() {
		if (!Settings.DISABLE_EFFECTS) 
		{
			this.particleTimer -= Gdx.graphics.getDeltaTime();
			if (this.particleTimer < 0.0f) 
			{
				this.particleTimer = 0.04f;
				AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
			}
		}
		this.particleTimer2 -= Gdx.graphics.getDeltaTime();
		if (this.particleTimer2 < 0.0f) 
		{
			this.particleTimer2 = MathUtils.random(0.45f, 0.55f);
			AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
		}
	}

	@Override
	public void onEnterStance() 
	{
		AbstractDungeon.player.stance = this;
		AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
		DuelistCard.applyPowerToSelf(new BlurPower(AbstractDungeon.player, 1));
	}

	@Override
	public void onExitStance() 
	{
		if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
		{
			int blk = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
			if (blk > 0) { DuelistCard.staticBlock(blk); }
		}
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		
	}

}
