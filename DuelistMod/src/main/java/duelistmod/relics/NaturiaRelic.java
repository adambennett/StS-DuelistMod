package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.Strings;

public class NaturiaRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NaturiaRelic");
	public static final String IMG = DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("NatureRelic.png");

	public NaturiaRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Naturia Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.naturiaVines = 2;
	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.naturiaVines = 1;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new NaturiaRelic();
	}
}
