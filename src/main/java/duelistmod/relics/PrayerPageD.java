package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.*;

public class PrayerPageD extends DuelistRelic implements MillenniumPrayerPage {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("PrayerPageD");
	public static final String IMG =  DuelistMod.makeRelicPath("PrayerPageD.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("PrayerPage_Outline.png");

	public PrayerPageD() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(PrayerPageB.ID)) { return true; }
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
		return new PrayerPageD();
	}
}
