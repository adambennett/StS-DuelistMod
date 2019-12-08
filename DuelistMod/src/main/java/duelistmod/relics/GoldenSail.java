package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.SeafaringPower;

public class GoldenSail extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("GoldenSail");
	public static final String IMG = DuelistMod.makeRelicPath("AquaRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("AquaRelic.png");

	public GoldenSail() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void atPreBattle()
	{
		DuelistCard.applyPowerToSelf(new SeafaringPower(8));
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Aqua Deck")) { return true; }
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
		return new GoldenSail();
	}
}
