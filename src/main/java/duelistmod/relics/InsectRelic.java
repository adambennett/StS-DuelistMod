package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;

public class InsectRelic extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("InsectRelic");
	public static final String IMG = DuelistMod.makeRelicPath("InsectRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("InsectRelic_Outline.png");
	
	public InsectRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return Util.deckIs("Insect Deck");
	}
	
	@Override
	public void onEquip() {
		setDescription();
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
		return new InsectRelic();
	}
}
