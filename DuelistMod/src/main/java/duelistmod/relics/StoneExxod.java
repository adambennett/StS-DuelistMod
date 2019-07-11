package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.*;

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
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		boolean allowSpawn = false;
    	if (DuelistMod.exodiaBtnBool) 
    	{ 
    		if (deck.equals("Exodia Deck")) { allowSpawn = true; }
    		if (DuelistMod.setIndex == 6) { allowSpawn = true; }
    	}
    	else
    	{
    		if (deck.equals("Spellcaster Deck")) { allowSpawn = true; }
    		if (deck.equals("Exodia Deck")) { allowSpawn = true; }
    		if (DuelistMod.setIndex == 6) { allowSpawn = true; }
    		if (DuelistMod.archRoll1 == 3 || DuelistMod.archRoll2 == 3) { allowSpawn = true; }
    	}
		return allowSpawn;
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
