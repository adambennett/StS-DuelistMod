package duelistmod.stances;

import basemod.IUIElement;
import basemod.ModLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;

import java.util.ArrayList;

public class Forsaken extends DuelistStance
{
	
	public Forsaken()
	{
		this.ID = "theDuelist:Forsaken";
		this.name = "Forsaken";
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
		return new DuelistConfigurationData(this.name, settingElements);
	}

	@Override
	public void updateDescription() 
	{
		this.description = "In this #yStance, whenever you #yExhaust a card, deal damage equal to your #yDexterity to ALL enemies.";
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
	public void onExhaust(AbstractCard c)
	{
		if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
		{
			int blk = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
			if (blk > 0) { DuelistCard.damageAllEnemiesThornsFire(blk); }
		}
	}
	
	@Override
	public void onExitStance() 
	{
		this.stopIdleSfx();
	}
	
	@Override
	public void onEndOfTurn() 
	{
		
    }

	@Override
	public void stopIdleSfx() 
	{
		
	}

}
