package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;

public class FlameMedallion extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("FlameMedallion");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public FlameMedallion() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Dragon Deck");
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
		return new FlameMedallion();
	}
}
