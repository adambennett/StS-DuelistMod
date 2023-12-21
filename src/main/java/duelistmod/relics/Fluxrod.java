package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class Fluxrod extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("Fluxrod");
	public static final String IMG = DuelistMod.makeRelicPath("Fluxrod.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Fluxrod_Outline.png");

	public Fluxrod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Machine Deck")) { return true; }
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
		return new Fluxrod();
	}
}
