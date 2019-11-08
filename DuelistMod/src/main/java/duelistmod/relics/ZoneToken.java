package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Strings;

public class ZoneToken extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ZoneToken");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public ZoneToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}

	@Override
	public void onCardDraw(final AbstractCard drawnCard)
	{
		if (drawnCard instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)drawnCard;
			if (dc.tributes > 4) { DuelistCard.incMaxSummons(2); }
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
		return new ZoneToken();
	}
}
