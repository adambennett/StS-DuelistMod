package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

public class SummonAnchor extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("SummonAnchor");
	public static final String IMG = DuelistMod.makeRelicPath("AnchorRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("AnchorRelic.png");

	public SummonAnchor() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onEquip()
	{
		if (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID))
		{
			MillenniumPuzzle mp = (MillenniumPuzzle) AbstractDungeon.player.getRelic(MillenniumPuzzle.ID);
			mp.setExtra(mp.extra + 1);
		}
	}
	
	@Override
	public void onUnequip()
	{
		if (AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID))
		{
			MillenniumPuzzle mp = (MillenniumPuzzle) AbstractDungeon.player.getRelic(MillenniumPuzzle.ID);
			mp.setExtra(mp.extra - 1);
		}
	}
	
	@Override
	public void atBattleStart() 
	{
		if (!AbstractDungeon.player.hasRelic(MillenniumPuzzle.ID)) { DuelistCard.powerSummon(AbstractDungeon.player, 1, "Relic Token", false); }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new SummonAnchor();
	}
}
