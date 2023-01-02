package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class DragonRelic extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("DragonRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("DragonStatue.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DragonStatue_Outline.png");
	
	public DragonRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn() {
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
		return new DragonRelic();
	}
}
