package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class NileToken extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("NileToken");
	public static final String IMG = DuelistMod.makeRelicPath("AquaRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("AquaRelic.png");

	public NileToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public void atBattleStart()
	{
		ArrayList<AbstractCard> randOv = DuelistCard.findAllOfType(Tags.IS_OVERFLOW, 3);
		for (AbstractCard c : randOv)
		{
			if (c.canUpgrade()) { c.upgrade(); }
			this.addToBot(new MakeTempCardInDiscardAction(c, 1));
		}
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
		return new NileToken();
	}
}
