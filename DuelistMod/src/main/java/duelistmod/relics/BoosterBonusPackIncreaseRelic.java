package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.BoosterPackHelper;
import duelistmod.variables.Strings;

public class BoosterBonusPackIncreaseRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterBonusPackIncreaseRelic");
	 public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public BoosterBonusPackIncreaseRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
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
		BoosterPackHelper.bonusPackSize = 5;
		DuelistMod.hasBoosterRewardRelic = true;
	}
	
	@Override
	public void onUnequip()
	{
		BoosterPackHelper.bonusPackSize = 3;
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
		return new BoosterBonusPackIncreaseRelic();
	}
}
