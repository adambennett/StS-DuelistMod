package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.StartingDeck;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class StoneExxod extends DuelistRelic
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
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		boolean allowSpawn = false;
        if (!DuelistMod.exodiaBtnBool) {
            if (StartingDeck.currentDeck == StartingDeck.SPELLCASTER) {
                allowSpawn = true;
            }
        }
        if (StartingDeck.currentDeck == StartingDeck.EXODIA) { allowSpawn = true; }
        if (DuelistMod.cardPoolType == CardPoolType.DECK_BASIC_2_RANDOM || DuelistMod.cardPoolType == CardPoolType.ALL_CARDS) { allowSpawn = true; }
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
