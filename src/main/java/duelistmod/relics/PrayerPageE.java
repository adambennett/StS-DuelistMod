package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.*;

public class PrayerPageE extends DuelistRelic implements MillenniumPrayerPage {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("PrayerPageE");
	public static final String IMG =  DuelistMod.makeRelicPath("PrayerPageE.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("PrayerPage_Outline.png");

	public PrayerPageE() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumPrayerbook.ID)) { return true; }
		else { return false; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new PrayerPageE();
	}
}
