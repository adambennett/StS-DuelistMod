package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.DuelistStance;
import duelistmod.actions.common.CardSelectScreenModifyMagicNumberAction;
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
		this.description = "In this #yStance, whenever you #yExhaust a card, increase the magic number of a card in your hand by #b1 for the rest of combat. Whenever you #yBlock, randomize the amount of #yBlock you gain.";
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
	public void onExhaust(AbstractCard c)
	{
		AbstractDungeon.actionManager.addToBottom(new CardSelectScreenModifyMagicNumberAction(AbstractDungeon.player.hand.group, 1, 1, true));
	}
	
	@Override
	public float modifyBlock(final float blockAmount) 
	{ 
		int randomVal = (int) AbstractDungeon.cardRandomRng.random(0, 20);
		return randomVal; 
	}

	@Override
	public void atStartOfTurn() 
	{
		
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
