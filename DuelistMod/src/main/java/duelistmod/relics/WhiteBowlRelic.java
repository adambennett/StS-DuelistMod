package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.WhiteOrb;

public class WhiteBowlRelic extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("WhiteBowlRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("WhiteBowlRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("WhiteBowlRelic_Outline.png");

	public WhiteBowlRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public void atPreBattle()
	{
		DuelistCard.channel(new WhiteOrb(), 1);
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new WhiteBowlRelic();
	}
}
