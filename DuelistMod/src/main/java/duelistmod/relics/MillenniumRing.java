package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.variables.Strings;

public class MillenniumRing extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumRing");
	public static final String IMG = DuelistMod.makePath(Strings.M_RING_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_RING_RELIC_OUTLINE);

	public MillenniumRing() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		
	}
	
	@Override
	public void onEquip()
	{
		DuelistMod.defaultMaxSummons+= 3;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt("defaultMaxSummons", DuelistMod.defaultMaxSummons);
			config.save();
		} catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void onUnequip()
	{
		DuelistMod.defaultMaxSummons-= 3;
		try 
		{
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt("defaultMaxSummons", DuelistMod.defaultMaxSummons);
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
		return new MillenniumRing();
	}
}
