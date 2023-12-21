package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.interfaces.MillenniumItem;

public class MillenniumScale extends DuelistRelic implements MillenniumItem {

	public static final String ID = duelistmod.DuelistMod.makeID("MillenniumScale");
	public static final String IMG = DuelistMod.makeRelicPath("ScalesRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("Scales_Outline.png");

	public MillenniumScale() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public void onSynergyTribute() {
		DuelistCard.gainTempHP(3);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumScale();
	}
}
