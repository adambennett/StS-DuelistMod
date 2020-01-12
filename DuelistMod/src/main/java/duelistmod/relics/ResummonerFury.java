package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;

public class ResummonerFury extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ResummonerFury");
	public static final String IMG = DuelistMod.makeRelicPath("ZombieRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("ZombieRelic.png");
	
	public ResummonerFury() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Zombie Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onResummon(DuelistCard res)
	{
		if (DuelistMod.currentZombieSouls > 0)
		{
			DuelistCard.damageAllEnemiesThornsNormal(DuelistMod.currentZombieSouls);
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ResummonerFury();
	}
}
