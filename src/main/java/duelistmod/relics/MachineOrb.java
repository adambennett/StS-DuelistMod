package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class MachineOrb extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MachineOrb");
	public static final String IMG = DuelistMod.makeRelicPath("MachineOrb.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MachineOrb_Outline.png");

	public MachineOrb() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Machine Deck");
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MachineOrb();
	}
}
