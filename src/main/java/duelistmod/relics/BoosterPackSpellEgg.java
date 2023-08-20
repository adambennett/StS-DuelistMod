package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.interfaces.BoosterRewardRelic;

public class BoosterPackSpellEgg extends DuelistRelic implements BoosterRewardRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterPackSpellEgg");
	public static final String IMG = DuelistMod.makeRelicPath("BoosterPackSpellEgg.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Egg_Outline.png");

	public BoosterPackSpellEgg() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return DuelistMod.persistentDuelistData.CardPoolSettings.getAnyBoosterOption() && Util.notHasBoosterRewardRelic();
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new BoosterPackSpellEgg();
	}
}
