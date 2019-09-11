package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.DuelistStance;
import duelistmod.patches.AbstractStanceEnum;

public class Spectral extends DuelistStance
{
	private static long sfxId;

	public Spectral()
	{
		this.ID = "theDuelist:Spectral";
		this.name = "Spectral";
		this.stanceName = AbstractStanceEnum.SPECTRAL;
		this.updateDescription();
	}

	static { Spectral.sfxId = -1L; }

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
	public float modifyBlock(final float blockAmount) { return 0; }

	@Override
	public void onExitStance() 
	{
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		if (Spectral.sfxId != -1L) 
		{
			CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Spectral.sfxId);
			Spectral.sfxId = -1L;
		}
	}

}
