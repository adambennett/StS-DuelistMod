package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.actions.common.*;
import defaultmod.patches.DuelistCard;

public class MillenniumRod extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = defaultmod.DefaultMod.makeID("MillenniumRod");
	public static final String IMG = DefaultMod.makePath(DefaultMod.M_ROD_RELIC);
	public static final String OUTLINE = DefaultMod.makePath(DefaultMod.M_ROD_RELIC_OUTLINE);

	public MillenniumRod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		
	}
	
	@Override
	public void atTurnStart()
	{
		flash();
		DuelistCard randomCard = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.SPELL);
		AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomCard, false, true, true, false, 1, 4));
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumRod();
	}
}
