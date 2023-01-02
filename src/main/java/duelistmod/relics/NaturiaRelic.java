package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class NaturiaRelic extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("NaturiaRelic");
	public static final String IMG = DuelistMod.makeRelicPath("NatureRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("NatureRelic.png");

	public NaturiaRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		return Util.deckIs("Naturia Deck");
	}
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new NaturiaRelic();
	}
}
