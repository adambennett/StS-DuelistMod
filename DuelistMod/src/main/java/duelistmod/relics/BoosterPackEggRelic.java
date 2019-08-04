package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Strings;

public class BoosterPackEggRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterPackEggRelic");
	 public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public BoosterPackEggRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if ((DuelistMod.allowBoosters || DuelistMod.alwaysBoosters || DuelistMod.removeCardRewards) && !DuelistMod.hasBoosterRewardRelic) { return true; }
		else { return false; }
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.hasBoosterRewardRelic = true;
	}

	@Override
	public void onUnequip()
	{
		DuelistMod.hasBoosterRewardRelic = false;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new BoosterPackEggRelic();
	}
}
