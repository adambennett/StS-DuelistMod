package defaultmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

public class StoneExxod extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = defaultmod.DefaultMod.makeID("StoneExxod");
	public static final String IMG = DefaultMod.makePath(DefaultMod.EXXOD_STONE_RELIC);
	public static final String OUTLINE = DefaultMod.makePath(DefaultMod.EXXOD_STONE_RELIC_OUTLINE);

	public StoneExxod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public void onShuffle() 
	{
		flash();
		DuelistCard randomCard = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(DefaultMod.EXODIA);
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
