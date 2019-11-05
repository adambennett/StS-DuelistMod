package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.BoosterPackHelper;
import duelistmod.variables.Strings;

public class BoosterBonusPackIncreaseRelic extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterBonusPackIncreaseRelic");
	public static final String IMG = DuelistMod.makeRelicPath("BoosterRelic.png");
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
		BoosterPackHelper.bonusPackSize += 2;
		DuelistMod.hasBoosterRewardRelic = true;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_BOOSTER_REWARD_RELIC, DuelistMod.hasBoosterRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public void onUnequip()
	{
		BoosterPackHelper.bonusPackSize -= 2;
		if (BoosterPackHelper.bonusPackSize < 1) { BoosterPackHelper.bonusPackSize = 1; }
		DuelistMod.hasBoosterRewardRelic = false;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setBool(DuelistMod.PROP_BOOSTER_REWARD_RELIC, DuelistMod.hasBoosterRewardRelic);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
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
