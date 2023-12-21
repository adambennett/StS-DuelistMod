package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;

public class TokenArmor extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("TokenArmor");
	public static final String IMG = DuelistMod.makeRelicPath("MachineRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("MachineRelic.png");

	public TokenArmor() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Machine Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onPassRoulette()
	{
		DuelistCard.staticBlock(7);
		this.flash();
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new TokenArmor();
	}
}
