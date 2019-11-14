package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.variables.Strings;

public class ElectricBurst extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ElectricBurst");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    private static boolean finishedCombat = false;

	public ElectricBurst() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		if (AbstractDungeon.player.hasRelic(ElectricToken.ID)) { return false; }
		return true;
	}
	
	@Override
	public void atPreBattle()
	{
		DuelistCard.applyPowerToSelf(new ElectricityPower(3));		
	}
	
	@Override
	public void atTurnStart()
	{
		if (!finishedCombat)
		{
			DuelistCard.applyPowerToSelf(new DeElectrifiedPower(1, 3));
			finishedCombat = true;
		}
	}
	
	@Override
	public void onVictory()
	{
		finishedCombat = false;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new ElectricBurst();
	}
}
