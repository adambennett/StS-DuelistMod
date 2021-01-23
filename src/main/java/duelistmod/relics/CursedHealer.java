package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;

public class CursedHealer extends DuelistRelic implements VisitFromAnubisRemovalFilter {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CursedHealer");
    public static final String IMG = DuelistMod.makeRelicPath("NamelessRelic.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("NamelessRelic_Outline.png");

	public CursedHealer() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.HEAVY);
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CursedHealer();
	}
}
