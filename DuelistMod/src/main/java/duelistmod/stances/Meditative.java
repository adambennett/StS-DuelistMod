package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.*;
import duelistmod.patches.AbstractStanceEnum;

public class Meditative extends DuelistStance
{
	private static long sfxId;

	public Meditative()
	{
		this.ID = "theDuelist:Meditative";
		this.name = "Meditative";
		this.stanceName = AbstractStanceEnum.MEDITATIVE;
		this.updateDescription();
	}

	static { Meditative.sfxId = -1L; }

	@Override
	public void updateDescription() 
	{
		this.description = "While in this stance, you receive #b20% more damage. Upon exiting this stance, gain #yVigor equal to twice your #yDexterity.";
	}
	
	public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {
		if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage * 1.2f;
        }
        return damage;
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
		if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
		{
			int blk = AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
			if (blk > 0) { DuelistCard.applyPowerToSelf(new VigorPower(AbstractDungeon.player, blk * 2)); }
		}
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		if (Meditative.sfxId != -1L) 
		{
			CardCrawlGame.sound.stop("STANCE_LOOP_CALM", Meditative.sfxId);
			Meditative.sfxId = -1L;
		}
	}

}
