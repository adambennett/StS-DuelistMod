package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.MillenniumItem;

public class MillenniumRing extends DuelistRelic implements MillenniumItem {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumRing");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumRingRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumRingRelic_Outline.png");

	public MillenniumRing() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic(MillenniumKey.ID);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		this.grayscale = true;
	}
	
	@Override
    public void onVictory() 
    {
		this.grayscale = false;
    }
	
	@Override
	public void onEquip()
	{
		DuelistMod.defaultMaxSummons+= 3;
	}

	@Override
	public void onUnequip()
	{
		DuelistMod.defaultMaxSummons-= 3;
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
