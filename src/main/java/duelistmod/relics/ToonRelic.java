package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.CardPoolType;
import duelistmod.helpers.*;

public class ToonRelic extends DuelistRelic 
{
	public static final String ID = DuelistMod.makeID("ToonRelic");
	public static final String IMG = DuelistMod.makeRelicPath("ToonRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("ToonRelic.png");
	
	public ToonRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Toon Deck") || DuelistMod.cardPoolType == CardPoolType.DECK_BASIC_2_RANDOM || DuelistMod.cardPoolType == CardPoolType.ALL_CARDS;
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription() {
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ToonRelic();
	}
}
