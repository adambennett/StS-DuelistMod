package duelistmod.stances;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.*;

public class Samurai extends DuelistStance
{
	
	public Samurai()
	{
		this.tips = new ArrayList<PowerTip>();
		this.ID = "theDuelist:Samurai";
		this.name = "Samurai";
		this.c = Color.FIREBRICK.cpy();
		this.updateDescription();
	}

	
	@Override
	public void updateDescription() 
	{
		this.description = "In this #yStance, #yBlock from cards is improved by #b3. Upon exiting this #yStance, gain #b1 #yDexterity.";
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
	public float modifyBlock(final float blockAmount, AbstractCard card) 
	{ 
		return blockAmount + 3.0f; 
	}

	@Override
	public void onEnterStance() 
	{
		AbstractDungeon.player.stance = this;
		AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
	}

	@Override
	public void onExitStance() 
	{
		DuelistCard.applyPowerToSelf(new DexterityPower(AbstractDungeon.player, 1));
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		
	}

}
