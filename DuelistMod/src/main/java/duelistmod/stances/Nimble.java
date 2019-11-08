package duelistmod.stances;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.*;

import duelistmod.abstracts.DuelistStance;
import duelistmod.helpers.Util;

public class Nimble extends DuelistStance
{
	
	public Nimble()
	{
		this.ID = "theDuelist:Nimble";
		this.name = "Nimble";
		this.updateDescription();
	}

	
	@Override
	public void updateDescription() 
	{
		this.description = "In this #yStance, whenever you play a card, have a #b25% chance to reduce the cost of a random card in your hand by #b1 for the turn.";
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
	
	public void onPlayCard(final AbstractCard card) 
	{
		int roll = AbstractDungeon.cardRandomRng.random(1, 4);
		if (roll == 1)
		{
			ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
			for (AbstractCard c : AbstractDungeon.player.hand.group)
			{
				if (!card.uuid.equals(c.uuid) && c.costForTurn > 0)
				{
					cards.add(c);
				}
			}
			
			if (cards.size() > 0)
			{
				AbstractCard rand = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
				rand.setCostForTurn(rand.cost - 1);
				rand.isCostModifiedForTurn = true;
				Util.log("Nimble: reduced the cost of " + rand.name);
				AbstractDungeon.player.hand.glowCheck();
			}
		}
		else { Util.log("Nimble: missed the roll"); }
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
		this.stopIdleSfx();
	}

	@Override
	public void stopIdleSfx() 
	{
		
	}

}
