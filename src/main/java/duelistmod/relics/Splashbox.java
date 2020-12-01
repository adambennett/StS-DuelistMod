package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.orbs.Splash;

public class Splashbox extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Splashbox");
	public static final String IMG = DuelistMod.makeRelicPath("SplashBox.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MonsterBox_Outline.png");

	public Splashbox() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void atBattleStart()
	{
		DuelistCard.channel(new Splash());
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Aqua Deck")) { return true; }
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
		return new Splashbox();
	}
}
