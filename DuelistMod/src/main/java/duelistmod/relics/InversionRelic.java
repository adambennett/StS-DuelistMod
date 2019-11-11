package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class InversionRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("InversionRelic");
	public static final String IMG = DuelistMod.makeRelicPath("InvertRelicB.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("InvertRelicB.png");

	public InversionRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Spellcaster Deck")) { return true; }
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
		return new InversionRelic();
	}
}
