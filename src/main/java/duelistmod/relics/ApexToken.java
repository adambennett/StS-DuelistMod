package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;

public class ApexToken extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ApexToken");
	public static final String IMG = DuelistMod.makeRelicPath("ApexToken.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("ApexToken_Outline.png");

	public ApexToken() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return Util.deckIs("Beast Deck");
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ApexToken();
	}
}
