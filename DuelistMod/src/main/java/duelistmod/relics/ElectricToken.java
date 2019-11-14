package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.duelistPowers.ElectricityPower;

public class ElectricToken extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ElectricToken");
	public static final String IMG =  DuelistMod.makeRelicPath("ElectricToken.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ElectricToken_Outline.png");

	public ElectricToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (AbstractDungeon.player.hasRelic(ElectricBurst.ID)) { return false; }
		return true;
	}
	
	@Override
	public void atPreBattle()
	{
		DuelistCard.applyPowerToSelf(new ElectricityPower(2));
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ElectricToken();
	}
}
