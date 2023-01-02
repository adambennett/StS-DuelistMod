package duelistmod.stances;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistStance;
import duelistmod.dto.DuelistConfigurationData;

import java.util.ArrayList;

public class Spectral extends DuelistStance
{
	
	public Spectral()
	{
		this.ID = "theDuelist:Spectral";
		this.name = "Spectral";
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
		this.description = "In this #yStance, #yWarriors deal double damage and you cannot #yBlock.";
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
	}
	
	@Override
	public float modifyBlock(final float blockAmount, AbstractCard card) { return 0; }

	@Override
	public void onExitStance() 
	{
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		
	}

}
