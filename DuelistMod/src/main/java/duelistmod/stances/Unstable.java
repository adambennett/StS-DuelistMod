package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.DuelistStance;
import duelistmod.patches.AbstractStanceEnum;

public class Unstable extends DuelistStance
{
	public Unstable()
	{
		this.ID = "theDuelist:Unstable";
		this.name = "Unstable";
		this.stanceName = AbstractStanceEnum.UNSTABLE;
		this.updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		this.description = "While in this #yStance, you take #b40% more damage.";
	}
	
	
	public float atDamageReceive(final float damage, final DamageInfo.DamageType damageType) {
		if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage * 1.4f;
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
		
	}

}
