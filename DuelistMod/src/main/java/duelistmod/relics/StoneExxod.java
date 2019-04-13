package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class StoneExxod extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("StoneExxod");
	public static final String IMG = DuelistMod.makePath(Strings.EXXOD_STONE_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.EXXOD_STONE_RELIC_OUTLINE);

	public StoneExxod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}

	@Override
	public void onShuffle() 
	{
		flash();
		DuelistCard randomCard = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.EXODIA);
		DuelistCard.addCardToHand(randomCard);
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new StoneExxod();
	}
}
