package duelistmod.stances;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.*;
import duelistmod.patches.AbstractStanceEnum;

public class Samurai extends DuelistStance
{
	private static long sfxId;

	public Samurai()
	{
		this.tips = new ArrayList<PowerTip>();
		this.ID = "theDuelist:Samurai";
		this.name = "Samurai";
		this.stanceName = AbstractStanceEnum.SAMURAI;
		this.c = Color.FIREBRICK.cpy();
		this.updateDescription();
	}

	static { Samurai.sfxId = -1L; }

	@Override
	public void updateDescription() 
	{
		this.description = "Upon exiting this #yStance, gain #b2 #yDexterity.";
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
			AbstractDungeon.effectsQueue.add(new StanceAuraEffect(AbstractStance.StanceName.CALM));
		}
	}

	@Override
	public void onEnterStance() 
	{
		AbstractDungeon.player.stanceName = this.stanceName;
		CardCrawlGame.sound.play("STANCE_ENTER_CALM");
		sfxId = CardCrawlGame.sound.playAndLoop("STANCE_LOOP_CALM");
		AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
	}

	@Override
	public void onExitStance() 
	{
		DuelistCard.applyPowerToSelf(new DexterityPower(AbstractDungeon.player, 2));
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		if (Samurai.sfxId != -1L) 
		{
			CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Samurai.sfxId);
			Samurai.sfxId = -1L;
		}
	}

}
