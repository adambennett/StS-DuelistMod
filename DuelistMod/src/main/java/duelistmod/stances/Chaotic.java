package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.*;
import duelistmod.patches.AbstractStanceEnum;

public class Chaotic extends DuelistStance
{
	private static long sfxId;

	public Chaotic()
	{
		this.ID = "theDuelist:Chaotic";
		this.name = "Chaotic";
		this.stanceName = AbstractStanceEnum.CHAOTIC;
		this.updateDescription();
	}

	static { Chaotic.sfxId = -1L; }

	@Override
	public void updateDescription() 
	{
		this.description = "In this #yStance, at the start of turn, switch to another random #yStance.";
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
	public void atStartOfTurn() 
	{
		DuelistCard.changeToRandomStance();
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
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		if (Chaotic.sfxId != -1L) 
		{
			CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Chaotic.sfxId);
			Chaotic.sfxId = -1L;
		}
	}

}
