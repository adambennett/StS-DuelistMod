package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.StarterDeckSetup;

public class Leafblower extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Leafblower");
	public static final String IMG =  DuelistMod.makeRelicPath("AeroRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("AeroRelic_Outline.png");

	public Leafblower() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) { return true; }
		else { return false; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new Leafblower();
	}
}
