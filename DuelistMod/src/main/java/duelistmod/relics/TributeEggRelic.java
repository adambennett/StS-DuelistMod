package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.variables.Strings;

public class TributeEggRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("TributeEggRelic");
	public static final String IMG = DuelistMod.makeRelicPath("TributeMonsterEggRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Egg_Outline");
	//public boolean cardSelected = false;
	//public DuelistCard cardToAdd = null;

	public TributeEggRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new TributeEggRelic();
	}
}
