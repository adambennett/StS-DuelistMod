package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class Wirebundle extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("Wirebundle");
	public static final String IMG = DuelistMod.makeRelicPath("WireBundle.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("WireBundle_Outline.png");

	public Wirebundle() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);
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
		return new Wirebundle();
	}
}
