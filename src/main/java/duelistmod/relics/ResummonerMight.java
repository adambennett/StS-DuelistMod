package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;

public class ResummonerMight extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ResummonerMight");
	public static final String IMG = DuelistMod.makeRelicPath("ZombieRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("ZombieRelic.png");
	
	public ResummonerMight() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
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
		DuelistCard.applyPowerToSelf(new StrengthPower(AbstractDungeon.player, 1));
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ResummonerMight();
	}
}
