package duelistmod.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractStanceEnum;
import duelistmod.variables.Tags;

public class Entrenched extends DuelistStance
{
	private int inc = 0;

	public Entrenched()
	{
		this.ID = "theDuelist:Entrenched";
		this.name = "Entrenched";
		this.stanceName = AbstractStanceEnum.ENTRENCHED;
		this.updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		this.description = "In this #yStance, #ySuperheavy cards have a low chance to apply #yPlated #yArmor or #yMetallicize when played.";
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
		
	}

	@Override
	public void atStartOfTurn() 
	{
		
    }
	
	@Override
	public void onPlayCard(final AbstractCard card) 
	{
		Util.log("Entrenched: this stance has successfully gained Metallicize or Plated Armor " + inc + " times.");
		if (card.hasTag(Tags.SUPERHEAVY))
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 12 + inc);
			if (roll == 12) { DuelistCard.applyPowerToSelf(new MetallicizePower(AbstractDungeon.player, 1)); inc++;}
			else
			{
				Util.log("Entrenched: missed Metallicize roll, got " + roll + ", needed a 12");
				int roll2 = AbstractDungeon.cardRandomRng.random(1, 11 + inc);
				if (roll2 == 11) { DuelistCard.applyPowerToSelf(new PlatedArmorPower(AbstractDungeon.player, 1)); if (roll%2==0) { inc++; }}
				else { Util.log("Entrenched: missed Plated Armor roll too, got " + roll2 + ", needed an 11"); }
			}
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
