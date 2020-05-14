package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;

public class FlowToken extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("FlowToken");
	public static final String IMG = DuelistMod.makeRelicPath("AquaRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("AquaRelic.png");

	public FlowToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Aqua Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onOverflow(int amt) 
	{ 
		for (int i = 0; i < amt; i++)
		{
			DuelistCard.addCardToHand(DuelistCardLibrary.getRandomTokenForCombat());			
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new FlowToken();
	}
}
