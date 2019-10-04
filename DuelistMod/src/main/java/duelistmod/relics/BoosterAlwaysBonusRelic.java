package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Strings;

public class BoosterAlwaysBonusRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("BoosterAlwaysBonusRelic");
	public static final String IMG = DuelistMod.makeRelicPath("BoosterRelic.png");
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public BoosterAlwaysBonusRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
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
		return new BoosterAlwaysBonusRelic();
	}
	

}
